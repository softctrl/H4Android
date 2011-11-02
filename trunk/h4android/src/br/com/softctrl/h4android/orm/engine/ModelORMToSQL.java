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
package br.com.softctrl.h4android.orm.engine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.softctrl.h4android.orm.engine.i.IModelORMDDL;
import br.com.softctrl.h4android.orm.exception.NotEntityException;
import br.com.softctrl.h4android.orm.reflection.EntityReflection;
import br.com.softctrl.h4android.orm.reflection.FieldReflection;
import br.com.softctrl.h4android.orm.util.StringUtil;

/**
 * @author <a href="mailto:carlostimoshenkorodrigueslopes@gmail.com">Timoshenko</a>.
 * @version $Revision: 0.0.0.1 $
 */
public class ModelORMToSQL implements IModelORMDDL {

	private Class<?> targetClass;

	private String tableName;
	private List<java.lang.String> columns;

	public ModelORMToSQL(Class<?> targetClass) {
		super();
		if (EntityReflection.isEntity(targetClass)) {
			this.targetClass = targetClass;
			this.tableName = EntityReflection.getTableName(this.targetClass);
		} else {
			new NotEntityException("A classe " + targetClass.toString()
					+ " não corresponde a uma entidade mapeada válida!");
		}

	}

	@Override
	public String createSQL() {

		List<Field> fields = EntityReflection.getEntityFields(targetClass);
		this.columns = new ArrayList<java.lang.String>();
		for (Field field : fields) {
			String column = FieldReflection.getColumnNameDDL(this.targetClass, field);
			if (!column.equals("")) {
				this.columns.add(column);
			}
		}
		return StringUtil.createSQL(this.tableName, this.columns);

	}

	@Override
	public String dropSQL() {
		return StringUtil.dropSQL(this.tableName);
	}
}
