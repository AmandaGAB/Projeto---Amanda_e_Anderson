
package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensagem {
    private int id;
    private String datahora;
    private Usuario criador;
    private String texto;	//formato "yyyyMMdd"
                
    public Mensagem(int id, Usuario criador, String texto) {
		this.id = id;
		this.criador = criador;
		this.texto = texto;
		this.datahora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
	}
                           
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getData() {
        return datahora;
    }
    public void setData(String data) {
        this.datahora = data;
    }
    
	
    public Usuario getCriador() {
		return criador;
	}

	public void setCriador(Usuario criador) {
		this.criador = criador;
	}

	@Override
    public String toString() {
        return  "id=" + id + ", criador=" + criador.getNome() +
                ", datahora=" + datahora +"\ntexto=" + texto ;
    }   
}
