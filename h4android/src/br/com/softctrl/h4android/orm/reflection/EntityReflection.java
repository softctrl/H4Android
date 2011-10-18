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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.com.softctrl.h4android.orm.annotation.ddl.Entity;
import br.com.softctrl.h4android.orm.annotation.ddl.Table;
import br.com.softctrl.h4android.orm.util.FieldValue;

public final class EntityReflection {

	public static boolean isEntity(Class<?> targetClass) {
		return targetClass.isAnnotationPresent(Entity.class);
	}

	public static String getTableName(Class<?> targetClass) {

		return (targetClass.isAnnotationPresent(Table.class) ? targetClass
				.getAnnotation(Table.class).value() : targetClass
				.getSimpleName().toUpperCase());

	}

	public static List<Field> getEntityFields(java.lang.Class<?> entityClass) {

		List<Field> fields = new ArrayList<Field>();
		if (!entityClass.equals(Object.class)) {
			for (Field field : Arrays.asList(entityClass.getDeclaredFields())) {
				if (!field.getName().equals("serialVersionUID")) {
					fields.add(field);
				}
			}
			List<Field> fields2 = getEntityFields(entityClass.getSuperclass());
			if (fields2.size() > 0) {
				fields.addAll(fields2);
			}
		} else {
			List<Field> lista = new ArrayList<Field>();
			for (Field field : Arrays.asList(entityClass.getDeclaredFields())) {
				if (!field.getName().equals("serialVersionUID")) {
					lista.add(field);
				}
			}
		}
		return fields;
	}

	public static Object getID(Object entity) {
		return FieldReflection.getValue(entity, entity.getClass(), "id");
	}

	public static HashMap<String, FieldValue> getFieldValues(Object entity) {

		HashMap<String, FieldValue> fields = new HashMap<String, FieldValue>();
		for (Field field : Arrays.asList(entity.getClass().getDeclaredFields())) {
			if (!field.getName().equals("serialVersionUID")) {
				FieldValue fv = new FieldValue();
				fv.setField(field);
				fv.setValue(FieldReflection.getValue(entity, entity.getClass(),
						field.getName()));
				String columnName = FieldReflection.getColumnName(
						entity.getClass(), field.getName());
				fields.put(columnName, fv);
			}
		}
		return fields;

	}
}
