package aplicacaoConsole;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */


import fachada.Fachada;
import modelo.Log;
import modelo.Mensagem;
import modelo.Usuario;

public class Listar {

	public Listar(){
		Fachada.inicializar();
		try {
			System.out.println("\nTodos os Usuarios do sistema:");
			for(Usuario u : Fachada.listarUsuarios())		
				System.out.println(u);
			
			System.out.println("\nTodas as Mensagens do sistema:");
			for(Mensagem m : Fachada.listarMensagens())		
				System.out.println(m);
			
			System.out.println("\nHistorico de logs do sistema:");
			for(Log log : Fachada.listarLogs())		
				System.out.println(log);

		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Fachada.finalizar();
	}


	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}

