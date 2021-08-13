package aplicacaoConsole;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import fachada.Fachada;
import modelo.Usuario;

public class Cadastrar {

	public Cadastrar(){
		Fachada.inicializar();
		System.out.println("cadastrando...");
		try {
			Usuario u;
			u=Fachada.criarUsuario("joao", "123");
			u=Fachada.criarUsuario("maria", "123");
			u=Fachada.criarUsuario("jose", "123");
			u=Fachada.criarUsuario("paulo", "123");
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}
		
		try {
			Fachada.login("joao", "123");
			System.out.println("Usuario logado:"+ Fachada.getLogado().getNome());
			Fachada.criarMensagem("bom dia grupo");
			Fachada.criarMensagem("vamos combinar um encontro hoje?");
			Fachada.logoff();
			
			Fachada.login("maria", "123");
			System.out.println("Usuario logado:"+ Fachada.getLogado().getNome());
			Fachada.criarMensagem("bom dia galera");
			Fachada.criarMensagem("vamos sim");
			Fachada.logoff();
			
			Fachada.login("jose", "123");
			System.out.println("Usuario logado:"+ Fachada.getLogado().getNome());
			Fachada.criarMensagem("bom dia a todos");
			Fachada.criarMensagem("eu nao posso ir");
			Fachada.criarMensagem("vou pensar");
			Fachada.criarMensagem("eu vou");
			Fachada.logoff();
			
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}

	

		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}


