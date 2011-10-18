/**
 * Copyright (c) 2011 Carlos Timoshenko Rodrigues Lopes
 * 
 * I thank GOD for the insatiable desire to acquire knowledge that was given to
 * me. The search for knowledge must be one of our main purposes as human beings.
 * I sincerely hope that this simple tool is in any way useful to the community
 * in general.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */
package br.com.softctrl.h4android.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.softctrl.h4android.orm.engine.GenerateModel;
import br.com.softctrl.h4android.orm.engine.IPersistenceManager;
import br.com.softctrl.h4android.orm.engine.criterya.QuerySample;
import br.com.softctrl.h4android.orm.enumeration.ModelBeavior;
import br.com.softctrl.h4android.orm.reflection.EntityReflection;
import br.com.softctrl.h4android.orm.util.FieldValue;
import br.com.softctrl.h4android.orm.util.content.ContentValueUtil;
import br.com.softctrl.h4android.orm.util.content.CursorUtil;
import br.com.softctrl.h4android.orm.util.log.Log;

/**
 * 
 * @author <a
 *         href="mailto:carlostimoshenkorodrigueslopes@gmail.com">Timoshenko</
 *         a>.
 * @version $Revision: 0.0.0.1 $
 */
public class PersistenceManagerA22 extends SQLiteOpenHelper implements
		IPersistenceManager {

	private static GenerateModel GEN_MODEL;

	private static String DATABASE_NAME;
	private static int DATABASE_VERSION;

	private SQLiteDatabase sqLiteDatabase;

	private Log log;

	public SQLiteDatabase getDbSQLite() {
		if (this.sqLiteDatabase == null) {
			this.sqLiteDatabase = getReadableDatabase();
		}
		return this.sqLiteDatabase;
	}

	/**
	 * 
	 * @param context
	 * @param dbName
	 * @param dbVersion
	 * @param mapedClass
	 * @param mode
	 * @param modelBeavior
	 * @param typeLog
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public PersistenceManagerA22(Context context, int dbName, int dbVersion,
			int mapedClass, int mode, ModelBeavior modelBeavior, int typeLog)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {

		super(context, context.getString(dbName).trim(), null, new Integer(
				context.getString(dbVersion)));
		log = new Log(typeLog);
		if ((GEN_MODEL == null) || modelBeavior.equals(ModelBeavior.RENEW)) {
			GEN_MODEL = new GenerateModel();
			GEN_MODEL.loadClasses(context.getString(mapedClass).trim()
					.split(";"));
		}

	}

	/**
	 * 
	 * @param context
	 * @param dataBaseName
	 * @param dataBaseVersion
	 * @param mapedClass
	 * @param mode
	 * @param modelBeavior
	 * @param typeLog
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public PersistenceManagerA22(Context context, String dataBaseName,
			int dataBaseVersion, List<String> mapedClass, int mode,
			ModelBeavior modelBeavior, int typeLog)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		super(context, dataBaseName.trim(), null, dataBaseVersion);
		log = new Log(typeLog);
		if ((GEN_MODEL == null) || modelBeavior.equals(ModelBeavior.RENEW)) {
			GEN_MODEL = new GenerateModel();
			GEN_MODEL.loadClasses(mapedClass);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.IPersistenceManager#loadClasses(
	 * java.util.List)
	 */
	@Override
	public void loadClasses(List<String> entityClasses)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		GEN_MODEL.loadClasses(entityClasses);
	}

	/*
	 * public void loadClasses(String[] entityClasses) throws
	 * ClassNotFoundException, InstantiationException, IllegalAccessException {
	 * GEN_MODEL.loadClasses(entityClasses); }
	 */
	@Override
	public <T> T insert(T entity) {

		Class<?> entityClass = entity.getClass();
		String tableName = EntityReflection.getTableName(entityClass);
		HashMap<String, FieldValue> fieldValues;
		fieldValues = EntityReflection.getFieldValues(entity);
		ContentValues cv = ContentValueUtil.putAll(fieldValues);
		getDbSQLite().insert(tableName, null, cv);
		log.i("h4Android", "Object-[" + entityClass.getSimpleName()
				+ "]-INSERT.");
		return entity;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.IPersistenceManager#insertAll(T[])
	 */
	@Override
	public <T> void insertAll(T[] entities) {

		Class<?> entityClass = entities[0].getClass();
		String tableName = EntityReflection.getTableName(entityClass);
		List<ContentValues> cValues = new ArrayList<ContentValues>();
		for (int i = 0; i < entities.length; i++) {
			cValues.add(ContentValueUtil.putAll(EntityReflection
					.getFieldValues(entities[i])));
		}
		for (ContentValues cv : cValues) {
			getDbSQLite().insert(tableName, null, cv);
		}
		log.i("h4Android", "Object-[" + entityClass.getSimpleName()
				+ "]-INSERT_ALL.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.IPersistenceManager#remove(java.
	 * lang.Object)
	 */
	@Override
	public <T> void remove(T entity) {

		Class<?> entityClass = entity.getClass();
		String tableName = EntityReflection.getTableName(entityClass);
		String[] whereArgs = new String[] { EntityReflection.getID(entity)
				.toString() };
		getDbSQLite().delete(tableName, "_ID=?", whereArgs);
		log.i("h4Android", "Object id[" + entityClass.getSimpleName() + "<"
				+ whereArgs[0] + ">]-REMOVE.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.IPersistenceManager#removeAll(T[])
	 */
	@Override
	public <T> void removeAll(T[] entities) {

		Class<?> entityClass = entities[0].getClass();
		String tableName = EntityReflection.getTableName(entityClass);
		List<String> ids = new ArrayList<String>();
		for (Object e : entities) {
			ids.add(EntityReflection.getID(e).toString());
		}

		for (String where : ids) {
			getDbSQLite().delete(tableName, "_ID=?", (new String[] { where }));
		}
		log.i("h4Android", "Object id[" + entityClass.getSimpleName()
				+ ">]-REMOVE_ALL.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.IPersistenceManager#update(java.
	 * lang.Object)
	 */
	@Override
	public <T> void update(T entity) {

		Class<?> entityClass = entity.getClass();
		String tableName = EntityReflection.getTableName(entityClass);
		HashMap<String, FieldValue> fieldValues;
		fieldValues = EntityReflection.getFieldValues(entity);
		ContentValues cv = ContentValueUtil.putAll(fieldValues);
		getDbSQLite().update(tableName, cv, "_ID=?",
				(new String[] { cv.get("_ID").toString() }));
		log.i("h4Android", "Object-[" + entityClass.getSimpleName()
				+ "]-UPDATE.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.IPersistenceManager#updateAll(T[])
	 */
	@Override
	public <T> void updateAll(T[] entities) {
		new Exception("no implemented yet.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.IPersistenceManager#find(java.lang
	 * .Object)
	 */
	@Override
	public <T> T find(T entity) {

		SQLiteDatabase db = getDbSQLite();
		Cursor c = db.rawQuery((new QuerySample(entity)).toSql(), null);

		if (c.getCount() == 1) {
			c.moveToFirst();
			CursorUtil.loadFieldsInCursor(c, entity);
			c.close();
			log.i("h4Android", "Object-[" + entity.getClass().getSimpleName()
					+ "]-FIND.");
			return entity;
		} else {
			c.close();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.IPersistenceManager#findAll(java
	 * .lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAll(T entity) {

		SQLiteDatabase db = getDbSQLite();
		Cursor c = db.rawQuery((new QuerySample(entity)).toSql(), null);
		if (c.moveToFirst()) {
			Class<?> classEntity = entity.getClass();
			List<T> lEntity = new ArrayList<T>();
			do {
				try {
					Object e = null;
					e = classEntity.newInstance();
					lEntity.add((T) e);
					CursorUtil.loadFieldsInCursor(c, e);
				} catch (InstantiationException e1) {
				} catch (IllegalAccessException e1) {
				}
			} while (c.moveToNext());
			c.close();
			return lEntity;
		} else {
			c.close();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.softctrl.db.orm.engine.IPersistenceManager#getDataBaseName()
	 */
	@Override
	public String getDataBaseName() {
		return DATABASE_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.db.orm.engine.IPersistenceManager#getDataBaseVersion()
	 */
	@Override
	public int getDataBaseVersion() {
		return DATABASE_VERSION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		try {
			this.sqLiteDatabase = db;
			this.sqLiteDatabase.beginTransaction();
			this.executeManynativeSql(GEN_MODEL.getSQLModel());
			this.sqLiteDatabase.setTransactionSuccessful();
		} catch (SQLException e) {
			log.e("[STDERR]-<SQLException>", e.toString());
			throw e;
		} finally {
			if (this.sqLiteDatabase.inTransaction()) {
				this.sqLiteDatabase.endTransaction();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		new Exception("Not implemented yet.");
	}

	public void executeNativeSql(String sql) {

		if (sql.trim().length() > 0) {
			this.sqLiteDatabase.execSQL(sql);
			System.out.println("[STDOUT]-EXEC SQL- " + sql);
		}

	}

	public void executeManynativeSql(String[] manySql) {

		if (manySql.length > 0) {
			for (String sql : manySql) {
				this.executeNativeSql(sql);
			}
		}

	}

}