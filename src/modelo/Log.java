package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
	private String datahora;
	private String nome;
	
	public Log(String nome) {
		this.nome = nome;
		this.datahora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyMMdd HH:mm:ss"));
	}

	

	public String getDatahora() {
		return datahora;
	}



	public String getNome() {
		return nome;
	}



	@Override
	public String toString() {
		return "Log [datahora=" + datahora + ", nome=" + nome + "]";
	}
	
	
	
	
}
