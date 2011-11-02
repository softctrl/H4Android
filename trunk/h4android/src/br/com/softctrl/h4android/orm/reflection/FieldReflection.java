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
package br.com.softctrl.h4android.orm.reflection;

import java.lang.reflect.Field;

import br.com.softctrl.h4android.orm.annotation.ddl.Column;
import br.com.softctrl.h4android.orm.annotation.ddl.Enumerated;
import br.com.softctrl.h4android.orm.annotation.ddl.Id;
import br.com.softctrl.h4android.orm.annotation.ddl.Transient;
import br.com.softctrl.h4android.orm.annotation.ddl.Version;
import br.com.softctrl.h4android.orm.beavior.column.i.IColumnBeavior;
import br.com.softctrl.h4android.orm.beavior.type.i.ITypeBeavior;
import br.com.softctrl.h4android.orm.enumeration.validation.TypeColumn;
import br.com.softctrl.h4android.orm.util.EnumerateUtils;
import br.com.softctrl.h4android.orm.util.FieldUtils;

/**
 * @author <a
 *         href="mailto:carlostimoshenkorodrigueslopes@gmail.com">Timoshenko</
 *         a>.
 * @version $Revision: 0.0.0.1 $
 */
public class FieldReflection {

	/**
	 * 
	 * @param object
	 * @param field
	 * @return
	 */
	public static Object getValue(Object object, Field field) {

		Object value = null;
		try {
			FieldUtils.setAccessible(field);
			value = field.get(object);
			FieldUtils.unsetAccessible(field);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return value;

	}

	/**
	 * 
	 * @param e
	 * @param targetClass
	 * @param fieldName
	 * @return
	 */
	public static Object getValue(Object e, Class<?> targetClass,
			String fieldName) {

		Field field;
		field = getDeclaredField(targetClass, fieldName);
		return getValue(e, field);

	}

	/**
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object getValue(Object object, String fieldName) {

		Object value = null;
		try {
			Field field = getDeclaredField(object.getClass(), fieldName);
			value = getValue(object, field);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return value;

	}

	public static Field getDeclaredField(Class<?> classTarget, String fieldName) {
		Field field = null;
		try {
			field = classTarget.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return field;
	}

	public static void setValue(Object e, Class<?> targetClass, Field field,
			Object value) {
		try {

			FieldUtils.setAccessible(field);
			if (field.getType().isEnum()) {

				Class<?> classEnum = field.getType();
				Enumerated en = field.getAnnotation(Enumerated.class);
				Object objEnum = EnumerateUtils.getEnum(classEnum, en.value(),
						value);
				field.set(e, objEnum);

			} else {
				field.set(e, value);
			}
			FieldUtils.unsetAccessible(field);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static TypeColumn getTypeColumn(java.lang.reflect.Field field) {

		String packageTypeBeavior = "br.com.softctrl.h4android.orm.beavior.type.TypeBeavior";
		Class<?> classTypeBeavior;
		try {
			classTypeBeavior = Class.forName(packageTypeBeavior
					+ field.getType().toString());
			ITypeBeavior<?, ?> typeBeavior;
			typeBeavior = (ITypeBeavior<?, ?>) classTypeBeavior.newInstance();
			return typeBeavior.getTypeValue();
		} catch (Exception e) {
			return TypeColumn.TEXT;
		}

	}

	private static IColumnBeavior getAnnotationColumn(Field field) {
		String pacote = "br.com.softctrl.h4android.orm.beavior.column.ColumnBeavior";
		if (field.isAnnotationPresent(Id.class)) {
			pacote += Id.class.getSimpleName();
		} else if (field.isAnnotationPresent(Version.class)) {
			pacote += Version.class.getSimpleName();
		} else if (field.isAnnotationPresent(Column.class)) {
			pacote += Column.class.getSimpleName();
		} else if (field.isAnnotationPresent(Enumerated.class)) {
			pacote += Enumerated.class.getSimpleName();
		}
		try {
			return (IColumnBeavior) (Class.forName(pacote)).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	public static String getColumnName(Class<?> targetClass, Field field) {

		try {
			IColumnBeavior columnBeavior = getAnnotationColumn(field);
			return columnBeavior.getNameColumn(field);
		} catch (Exception e) {
			e.printStackTrace();
			return field.getName().toUpperCase();
		}

	}

	public static String getColumnName(Class<?> targetClass, String fieldName) {

		try {
			Field field = getField(targetClass, fieldName);
			return getColumnName(targetClass, field);
		} catch (Exception e) {
			return fieldName.toUpperCase();
		}

	}

	public static Field getField(Class<?> targetClass, String fieldName) {

		try {
			return targetClass.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchFieldException e) {
			return null;
		}

	}

	public static String getColumnNameDDL(Class<?> targetClass, Field field) {

		String columnName = field.getName();
		String column = "";
		if (!field.isAnnotationPresent(Transient.class)) {
			IColumnBeavior columnBeavior = getAnnotationColumn(field);
			if (columnBeavior != null) {
				column = columnBeavior.getDDLColumn(field);
			} else {
				column = columnName.toUpperCase() + " "
						+ FieldReflection.getTypeColumn(field);
			}
		}
		return column;

	}

}
