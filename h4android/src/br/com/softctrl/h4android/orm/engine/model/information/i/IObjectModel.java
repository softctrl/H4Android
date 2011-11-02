/**
 * 
 */
package br.com.softctrl.h4android.orm.engine.model.information.i;

/**
 * 
 * @author timoshenko
 * 
 */
public interface IObjectModel {

	public Boolean isSimple();
	
	public java.util.List<IObjectModel> getFields();

	public String getName();

	public <T> Class<T> getClass2();

	//public <T> Class<T> getClassObject();

	public <T> T get();

}
