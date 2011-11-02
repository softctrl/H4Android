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

public class Restriction {

	public static Eq eq(String field, Object value) {

		return Eq.create(field, value);

	}

	public static Gt greaterThan(String field, Object value) {

		return Gt.create(field, value);

	}

	public static In in(String field, Object[] values) {

		return In.create(field, values);

	}

	public static Null isNull(String field) {

		return Null.create(field);

	}

	public static Lt lessThan(String field, Object value) {

		return Lt.create(field, value);

	}

	public static Ne notEq(String field, Object value) {

		return Ne.create(field, value);

	}

}