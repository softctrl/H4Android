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
package br.com.softctrl.h4android.orm.util;

import java.lang.reflect.Method;

import br.com.softctrl.h4android.orm.enumeration.validation.TypeEnum;

/**
 * @author <a href="mailto:carlostimoshenkorodrigueslopes@gmail.com">Timoshenko</a>.
 * @version $Revision: 0.0.0.1 $
 */
public class EnumerateUtils {

	/**
	 * 
	 * @param enumObj
	 * @param typeEnum
	 * @return
	 */
	public static Object getValueInEnum(Object value, TypeEnum typeEnum) {

		if (typeEnum.equals(TypeEnum.STRING)) {
			return value.toString();
		} else {
			Class<?> classEnum = value.getClass();
			Object[] fields = classEnum.getEnumConstants();
			for (int i = 0; i < fields.length; i++) {
				if (value == fields[i]) {
					return i;
				}
			}
		}
		return null;
	}

	public static Object getEnum(Class<?> classEnum, TypeEnum typeEnum, Object value) {

		Object[] enums = classEnum.getEnumConstants();
		if (typeEnum == TypeEnum.ORDINAL) {
			enums = classEnum.getEnumConstants();
			return enums[(Integer) value];
		} else {
			Method valueOf = MethodUtils.getDeclaredMethod(classEnum,
					"valueOf", String.class);
			return MethodUtils.invoke(valueOf, enums[0], value);

		}

	}

}
