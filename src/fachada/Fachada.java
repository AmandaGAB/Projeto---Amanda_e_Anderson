package fachada;

import java.util.List;

import dao.DAO;
import dao.DAOLog;
import dao.DAOMensagem;
import dao.DAOUsuario;
import modelo.Log;
import modelo.Mensagem;
import modelo.Usuario;

public class Fachada {
	private static DAOUsuario daousuario = new DAOUsuario();  
	private static DAOMensagem daomensagem = new DAOMensagem();  
	private static DAOLog daolog = new DAOLog();  

	private static Usuario usuariologado=null;


	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar(){
		DAO.close();
	}

	public static List<Usuario> listarUsuarios() {
		// nao precisa estar logado
		return daousuario.readAll();	
	}
	public static List<Mensagem> listarMensagens() {
		// nao precisa estar logado
		return daomensagem.readAll();	
	}

	public static List<Log> listarLogs() {
		// nao precisa estar logado
		return daolog.readAll();	
	}
	public static List<Mensagem> buscarMensagens(String termo) throws  Exception{
		/*
		 * nao precisa estar logado
		 * query no banco para obter mensagens do grupo que contenha
		 *  o termo (considerar case insensitive)
		 * 
		 */
		if (termo.isEmpty()) {
			return daomensagem.readAll();
		} else {
			return daomensagem.buscarPorTermo(termo);
		}

		
	}

	public static Usuario criarUsuario(String nome, String senha) throws  Exception{
		// nao precisa estar logado
		DAO.begin();	
		Usuario u = daousuario.read(nome+"/"+senha);	
		if(u != null) {
			DAO.rollback();	
			throw new Exception("criar usuario - usuario existente:" + nome);
		}

		u = new Usuario(nome+"/"+senha);
		daousuario.create(u);		
		DAO.commit();
		return u;
	}


	public static void login(String nome, String senha) throws Exception{		
		//verificar se ja existe um usuario logada
		if(usuariologado!=null)
			throw new Exception ("ja existe um usuario logado"+usuariologado.getNome());

		DAO.begin();	
		Usuario u = daousuario.read(nome+"/"+senha);	
		if(u == null) {
			DAO.rollback();	
			throw new Exception("login - usuario inexistente:" + nome);
		}
		if(!u.ativo()) {
			DAO.rollback();	
			throw new Exception("login - usuario nao ativo:" + nome);
		}
		usuariologado = u;		//altera o logado na fachada

		Log log = new Log(usuariologado.getNome());
		daolog.create(log);
		DAO.commit();
	}
	public static void logoff() {		
		usuariologado = null; 		//altera o logado na fachada
	}

	public static Usuario getLogado() {
		return usuariologado;
	}



	public static Mensagem criarMensagem(String texto) throws Exception{
		/*
		 * tem que esta logado
		 * criar a mensagem, onde o criador é a usuario logada
		 * adicionar esta mensagem na lista de mensagens de cada usuario do grupo,
		 * incluindo a do criador
		 * retornar mensagem criada
		 */

		//para gerar o novo id da mensagem utilize:
		//		int id = daomensagem.obterUltimoId();
		//		id++;
		//		Mensagem m = new Mensagem(id, usuariologado, texto);


		DAO.begin();

		if (usuariologado == null) {
			DAO.rollback();
			throw new Exception("O usuário precisa estar logado");
		}

		int id = daomensagem.obterUltimoId();

		Usuario criador = usuariologado;

		Mensagem mensagem = new Mensagem(id + 1, criador, texto);
		criador.adicionar(mensagem);

		daomensagem.create(mensagem);
		DAO.commit();
		return mensagem;
		
		
	}



	public static List<Mensagem> listarMensagensUsuario() throws Exception{
		/*
		 * tem que esta logado
		 * retorna todas as mensagens do usuario logado
		 * 
		 */
		

		if (usuariologado == null) {
			throw new Exception("O usuário precisa estar logado");
		}

		Usuario usuario = usuariologado;

		return usuario.getMensagens();
	}

	//public static void apagarMensagens(int... ids) throws  Exception{
	public static void apagarMensagens(int id) throws  Exception{
		/*
		 * tem que esta logado
		 * recebe uma lista de numeros de id 
		 * (id é um numero entre 1 a N, onde N é a quatidade atual de mensagens do grupo)
		 * validar se ids são de mensagens criadas pelo usuario logado
		 * (um usuario nao pode apagar mensagens de outros usuarios)
		 * 
		 * remover cada mensagem da lista de mensagens do usuario logado
		 * apagar cada mensagem do banco 
		 */

		if (usuariologado == null) {
			throw new Exception("O usuário precisa estar logado");
		}

		List<Mensagem> mensagens = usuariologado.getMensagens();

		DAO.begin();

		Mensagem mensagemParaExcluir = null;

		for (Mensagem m : mensagens) {
			if (m.getId() == id) {
				mensagemParaExcluir = m;
			}
		}

		if (mensagemParaExcluir == null) {
			DAO.rollback();
			throw new Exception("Mensagem não encontrada");
		}

		usuariologado.remover(mensagemParaExcluir);
		daousuario.update(usuariologado);
		DAO.commit();

	}

	public static void sairDoGrupo() throws  Exception{
		/*
		 * tem que esta logado
		 * 
		 * criar a mensagem "fulano saiu do grupo"
		 * desativar o usuario logado e fazer logoff dele
		 */

		if (usuariologado == null) {
			throw new Exception("O usuário precisa estar logado");
		}

		criarMensagem(usuariologado.getNome() + "saiu do grupo.");

		DAO.begin();
		usuariologado.desativar();
		daousuario.update(usuariologado);
		DAO.commit();
		logoff();
	}


//	public static int totalMensagensUsuario() throws Exception{
//		/*
//		 * tem que esta logado
//		 * retorna total de mensagens criadas pelo usuario logado
//		 * 
//		 */
//	}

	public static void esvaziar() throws Exception{
		DAO.clear();
	}

}

