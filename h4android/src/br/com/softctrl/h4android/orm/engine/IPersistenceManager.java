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

import java.util.List;

/**
 * @author <a href="mailto:carlostimoshenkorodrigueslopes@gmail.com">Timoshenko</a>.
 * @version $Revision: 0.0.0.1 $
 */
public interface IPersistenceManager {

	/**
	 * Insere um objeto do tipo T na base de dados
	 * 
	 * @param entity
	 * @return
	 */
	public <T> T insert(T entity);

	/**
	 * Insere um lista de objetos do tipo T na base de dados
	 * 
	 * @param entities
	 */
	public <T> void insertAll(T[] entities);

	/**
	 * Remove um objeto do tipo T da base de dados
	 * 
	 * @param entity
	 */
	public <T> void remove(T entity);

	/**
	 * Remove uma lista de objetos do tipo T da base de dados
	 * 
	 * @param entities
	 */
	public <T> void removeAll(T[] entities);

	/**
	 * Atualiza um objeto do tipo T da base de dados
	 * 
	 * @param entity
	 */
	public <T> void update(T entity);

	/**
	 * Atualiza uma lista de objetos do tipo T da base de dados
	 * 
	 * @param entities
	 */
	public <T> void updateAll(T[] entities);

	/**
	 * Busca um objeto do tipo T na base de dados
	 * 
	 * @param entity
	 * @return
	 */
	public <T> T find(T entity);

	/**
	 * Busca uma lista de objetos do tipo T na base de dados
	 * 
	 * @param entity
	 * @return
	 */
	public <T> List<T> findAll(T entity);

	/**
	 * Informa o nome do arquivo em disco da base de dados
	 * 
	 * @return
	 */
	public String getDataBaseName();

	/**
	 * Informa a versão do bando de dados da aplicação
	 * 
	 * @return
	 */
	public int getDataBaseVersion();

	/**
	 * Executa uma instruçã sql nativa
	 * 
	 * @param sql
	 */
	public void executeNativeSql(String sql);

	/**
	 * Executa um array de sql's nativos
	 * 
	 * @param manySql
	 */
	public void executeManynativeSql(String[] manySql);

	/**
	 * Método responsável por carregar os dados das classes de entidades da
	 * aplicação
	 * 
	 * @param entityClasses
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void loadClasses(List<String> entityClasses)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException;
}
