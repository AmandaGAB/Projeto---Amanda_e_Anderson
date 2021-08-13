package aplicacaoConsole;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import javax.swing.JOptionPane;

import fachada.Fachada;


public class ConsultarMensagem {

	public ConsultarMensagem(){
		Fachada.inicializar();
		try {
			String termo = JOptionPane.showInputDialog("digite o termo");
			System.out.println("Buscar mensagens contendo:"+ termo);
			System.out.println(Fachada.buscarMensagens(termo));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			Fachada.finalizar();
		}
		System.out.println("\nfim do programa");
	}


	//=================================================
	public static void main(String[] args) {
		new ConsultarMensagem();
	}
}

