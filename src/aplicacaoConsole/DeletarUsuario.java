package aplicacaoConsole;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import javax.swing.JOptionPane;

import fachada.Fachada;


public class DeletarUsuario {

	public DeletarUsuario(){
		Fachada.inicializar();
		try {
			String nome = JOptionPane.showInputDialog("digite o nome");
			String senha = JOptionPane.showInputDialog(null,"digite a senha","123");
			String id = JOptionPane.showInputDialog("digite o id da mensagem");
			Fachada.login(nome, senha);
			System.out.println("Usuario logado:"+ Fachada.getLogado().getNome());
			Fachada.sairDoGrupo();
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//apagar usuario
		try {
			String nome = JOptionPane.showInputDialog("digite o nome");
			String senha = JOptionPane.showInputDialog("digite a senha");
			Fachada.login(nome, senha);
			System.out.println("Usuario logado:"+ Fachada.getLogado().getNome());
			Fachada.sairDoGrupo();
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new DeletarUsuario();
	}
}

