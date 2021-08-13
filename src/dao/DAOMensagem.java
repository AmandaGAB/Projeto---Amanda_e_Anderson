package dao;

import java.util.List;

import com.db4o.query.Query;

import modelo.Mensagem;

public class DAOMensagem extends DAO<Mensagem>{
	public Mensagem read(Object chave) {
		String nome = (String) chave;

		Query q = manager.query();
		q.constrain(Mensagem.class);
		q.descend("nome").constrain(nome);
		List<Mensagem> resultados = q.execute();
		if (resultados.size() > 0)
			return resultados.get(0);
		else
			return null;
	}

	public List<Mensagem> buscarPorTermo(String termo) {
		Query q = manager.query();
		q.constrain(Mensagem.class);
		q.descend("texto").constrain(termo).like();
		List<Mensagem> resultado = q.execute();
		return resultado;
	}

}
