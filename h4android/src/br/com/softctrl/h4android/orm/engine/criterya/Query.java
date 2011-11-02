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
package br.com.softctrl.h4android.orm.engine.criterya;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import br.com.softctrl.h4android.orm.engine.criterya.pattern.ElementsQuery;
import br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery;
import br.com.softctrl.h4android.orm.engine.i.IPersistenceManager;
import br.com.softctrl.h4android.orm.exception.SelectAllQueryException;
import br.com.softctrl.h4android.orm.reflection.EntityReflection;
import br.com.softctrl.h4android.orm.reflection.FieldReflection;

/**
 * @author <a
 *         href="mailto:carlostimoshenkorodrigueslopes@gmail.com">Timoshenko</
 *         a>.
 * @version $Revision: 0.0.0.1 $
 */
public class Query extends ElementsQuery {

	private enum TypeSelect {
		ALL, OTHER
	}

	private IPersistenceManager pm;

	private String select = "*";
	private TypeSelect typeQuery = TypeSelect.ALL;
	private HashMap<Integer, IElementsQuery> elements = new HashMap<Integer, IElementsQuery>();
	private int count=0;

	protected Query(String nameObject) {
		super(nameObject);
	}

	public static <T> Query create(Class<T> classEntity, IPersistenceManager pm) {
		String entityName = EntityReflection.getTableName(classEntity);
		Query q = new Query(entityName);
		q.pm = pm;
		q.setClassEntity(classEntity);
		return q;
	}

	@Override
	public IElementsQuery add(IElementsQuery iElementsQuery) {

		((ElementsQuery) iElementsQuery).setClassEntity(getClassEntity());
		elements.put(++count, iElementsQuery);
		return this;

	}

	@Override
	public String toSql() {
		String sql = "SELECT " + select + " FROM %s WHERE (1=1)%s;";
		String whereClausules = "";
		for (Entry<Integer, IElementsQuery> e : elements.entrySet()) {
			whereClausules += e.getValue().toSql();
		}
		return String.format(sql, getKey(), whereClausules);
	}

	public Query selectCount() {
		select = "COUNT(*)";
		typeQuery = TypeSelect.OTHER;
		return this;
	}

	public Query selectMax(String field) {
		select = "MAX("
				+ FieldReflection.getColumnName(getClassEntity(), field) + ")";
		typeQuery = TypeSelect.OTHER;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery#
	 * list()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> java.util.List<T> list() {
		
		if (typeQuery != TypeSelect.ALL) {
			new SelectAllQueryException("Tipo de consulta não retorna list.");
		}
		String sql = toSql();
		System.out.println(sql);
		return (List<T>) pm.findAll(sql, getClassEntity());
		
	}

	@Override
	public String getKey() {
		return EntityReflection.getTableName(getClassEntity());
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
