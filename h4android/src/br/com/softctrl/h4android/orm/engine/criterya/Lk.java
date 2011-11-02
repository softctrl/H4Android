/**
 * 
 */
package br.com.softctrl.h4android.orm.engine.criterya;

import java.util.List;

import br.com.softctrl.h4android.orm.engine.criterya.pattern.ElementsQueryModel1;
import br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery;
import br.com.softctrl.h4android.orm.reflection.FieldReflection;
import br.com.softctrl.h4android.orm.util.StringUtil;

/**
 * @author timoshenko
 * 
 */
public class Lk extends ElementsQueryModel1 {

	private Lk(String nameObject) {
		super(nameObject);
	}

	public static Lk create(String field, String value) {

		Lk lk = new Lk(field);
		lk.value = value;
		lk.SIGNAL_OPERATOR = "LIKE";
		return lk;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery#
	 * add(br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery)
	 */
	@Override
	public IElementsQuery add(IElementsQuery iElementsQuery) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.criterya.pattern.IElementsQuery#
	 * list()
	 */
	@Override
	public <T> List<T> list() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.softctrl.h4android.orm.engine.criterya.pattern.ElementsQueryModel1
	 * #toSql()
	 */
	@Override
	public String toSql() {

		String sValue = StringUtil.objectToString("%" + value + "%");
		String sColumn = FieldReflection.getColumnName(getClassEntity(),
				getName());
		return " AND ( " + sColumn + " " + SIGNAL_OPERATOR + " " + sValue
				+ " )";

	}

}
