package aplicacaoConsole;

import fachada.Fachada;

public class EsvaziarBanco {
	public EsvaziarBanco(){
		Fachada.inicializar();
		try {
			Fachada.esvaziar();
			System.out.println("apagou todos os objetos do banco");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Fachada.finalizar();
		System.out.println("fim do programa");
	}


	//=================================================
	public static void main(String[] args) {
		new EsvaziarBanco();
	}


}
