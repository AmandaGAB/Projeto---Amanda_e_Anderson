/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Persistencia de Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

package dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;
import com.db4o.query.Query;

import modelo.Log;
import modelo.Mensagem;
import modelo.Usuario;



public abstract class DAO<T> implements DAOInterface<T> {
	protected static ObjectContainer manager;

	public static void open(){	
		if(manager==null){		
			abrirBancoLocal();
			//abrirBancoServidor();
		}
	}
	public static void abrirBancoLocal(){		
		//new File("banco.db4o").delete();  //apagar o banco
		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0);  // 0,1,2,3...
		config.common().objectClass(Mensagem.class).cascadeOnDelete(true);;
		config.common().objectClass(Mensagem.class).cascadeOnUpdate(true);;
		config.common().objectClass(Mensagem.class).cascadeOnActivate(true);  //sem cascata
		config.common().objectClass(Usuario.class).cascadeOnDelete(true);;
		config.common().objectClass(Usuario.class).cascadeOnUpdate(true);;
		config.common().objectClass(Usuario.class).cascadeOnActivate(true);
		config.common().objectClass(Log.class).cascadeOnDelete(true);;
		config.common().objectClass(Log.class).cascadeOnUpdate(true);;
		config.common().objectClass(Log.class).cascadeOnActivate(true);

		//		profundidade da ativação e atualização do grafo
		config.common().objectClass(Usuario.class).updateDepth(5);
		config.common().objectClass(Mensagem.class).updateDepth(5);
		config.common().objectClass(Mensagem.class).minimumActivationDepth(5);
		config.common().objectClass(Usuario.class).minimumActivationDepth(5);

		// 		indices
		config.common().objectClass(Mensagem.class).objectField("id").indexed(true);  
		config.common().objectClass(Usuario.class).objectField("nomesenha").indexed(true);  

		manager = 	Db4oEmbedded.openFile(config, "banco.db4o");
	}

	public static void abrirBancoServidor(){
		ClientConfiguration config = Db4oClientServer.newClientConfiguration( ) ;
		config.common().objectClass(Mensagem.class).cascadeOnDelete(true);;
		config.common().objectClass(Mensagem.class).cascadeOnUpdate(true);;
		config.common().objectClass(Mensagem.class).cascadeOnActivate(true);  //sem cascata
		config.common().objectClass(Usuario.class).cascadeOnDelete(true);;
		config.common().objectClass(Usuario.class).cascadeOnUpdate(true);;
		config.common().objectClass(Usuario.class).cascadeOnActivate(true);
		config.common().objectClass(Log.class).cascadeOnDelete(true);;
		config.common().objectClass(Log.class).cascadeOnUpdate(true);;
		config.common().objectClass(Log.class).cascadeOnActivate(true);

		//		profundidade da ativação e atualização do grafo
		config.common().objectClass(Usuario.class).updateDepth(5);
		config.common().objectClass(Mensagem.class).updateDepth(5);
		config.common().objectClass(Mensagem.class).minimumActivationDepth(5);
		config.common().objectClass(Usuario.class).minimumActivationDepth(5);

		// 		indices
		config.common().objectClass(Mensagem.class).objectField("id").indexed(true);  
		config.common().objectClass(Usuario.class).objectField("nomesenha").indexed(true);  
		
		manager = Db4oClientServer.openClient(config,"54.94.169.84",34000,"usuario1","senha1");	
		//manager = Db4oClientServer.openClient(config,"localhost",34000,"usuario1","senha1");
	}


	public static void close(){
		if(manager!=null) {
			manager.close();
			manager=null;
		}
	}

	//----------CRUD-----------------------
	public void create(T obj){
		manager.store( obj );
	}

	public abstract T read(Object chave);

	public T update(T obj){
		manager.store(obj);
		return obj;
	}

	public void delete(T obj) {
		manager.delete(obj);
	}

	public void refresh(T obj){
		manager.ext().refresh(obj, Integer.MAX_VALUE);
	}

	@SuppressWarnings("unchecked")
	public List<T> readAll(){
		manager.ext().purge();  	//limpar cache local do manager

		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		Query q = manager.query();
		q.constrain(type);
		return (List<T>) q.execute();
	}

	@SuppressWarnings("unchecked")
	public void deleteAll(){
		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		Query q = manager.query();
		q.constrain(type);
		for (Object t : q.execute()) {
			manager.delete(t);
		}
	}


	//------------transação---------------
	public static void begin(){	
	}		// tem que ser vazio

	public static void commit(){
		manager.commit();
	}
	public static void rollback(){
		manager.rollback();
	}

	public static void clear(){
		Query q = manager.query();
		q.constrain(Object.class);
		for (Object o : q.execute()) 
			manager.delete(o);
		manager.commit();
	}


	//	obtem o id do ultimo objeto gravado 
	//  ordenar decrescentemente e pegar o primeiro resultado
	public int obterUltimoId () {
		@SuppressWarnings("unchecked")
		Class<T> type =(Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		int id;

		//verificar se o banco esta vazio 
		if(manager.query(type).size()==0) {
			id=0;			//nenhum objeto armazenado
		}
		else {
			//consulta ordenada por id
			Query q = manager.query();
			q.constrain(type);
			q.descend("id").orderDescending();
			List<T> resultados =  q.execute();
			T objetomaiorid = null;
			try {
				objetomaiorid =  resultados.get(0);
				Field atributo = type.getDeclaredField("id") ;
				atributo.setAccessible(true);
				id = (Integer) atributo.get(objetomaiorid);  //maior id
			} catch(NoSuchFieldException e) {
				throw new RuntimeException("classe "+type+" nao tem id");
			} catch (IllegalAccessException e) {
				throw new RuntimeException("classe "+type+" id inacessivel");
			}
		}
		return id;
	}

}

