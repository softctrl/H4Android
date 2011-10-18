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

import br.com.softctrl.h4android.orm.engine.criterya.pattern.ElementsQuery;
import br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery;
import br.com.softctrl.h4android.orm.reflection.FieldReflection;
import br.com.softctrl.h4android.orm.util.StringUtil;

/**
 * "Not In"
 * @author <a href="mailto:carlostimoshenkorodrigueslopes@gmail.com">Timoshenko</a>.
 * @version $Revision: 0.0.0.1 $
 */
public class Ni extends ElementsQuery {

	private Object[] values;

	private Ni(String field) {
		super(field);
	}

	public static Ni create(String field, Object[] values) {

		Ni ni = new Ni(field);
		ni.values = values;
		return ni;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery#
	 * get()
	 */
	@Deprecated
	@Override
	public IElementsQuery get() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery#
	 * add(br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery)
	 */
	@Deprecated
	@Override
	public void add(IElementsQuery iElementsQuery) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery#
	 * toSql()
	 */
	@Override
	public String toSql() {

		String sColumn = FieldReflection.getColumnName(getClassEntity(),
				getNameObject());
		String sValues = StringUtil.objectToString(values[0]);
		for (int i = 1; i < values.length; i++) {
			sValues += ", " + StringUtil.objectToString(values[i]);
		}
		return String.format(" AND NOT( %s IN(%s) )", sColumn, sValues);

	}
}
