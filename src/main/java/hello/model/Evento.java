package hello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "evento")
public class Evento {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evento")
	private int id_evento;
	
	@Column(name = "descricao_evento", nullable = false)
	String descricao_evento;
	
	@Column(name = "ano_evento", nullable = false)
	int ano_evento;

	@Column(name = "acerto_minimo_exigido", nullable = false)
	int acerto_minimo_exigido;
	
	@Column(name = "media_acertos", nullable = false)
	double media_acertos;
	
	@Column(name = "numero_inscritos", nullable = false)
	double numero_inscritos;
	
	@Column(name = "numero_vagas", nullable = false)
	int numero_vagas;
	
	public int getId_evento() {
		return id_evento;
	}

	public void setId_evento(int id_evento) {
		this.id_evento = id_evento;
	}

	public String getDescricao_evento() {
		return descricao_evento;
	}

	public void setDescricao_evento(String descricao_evento) {
		this.descricao_evento = descricao_evento;
	}

	public int getAno_evento() {
		return ano_evento;
	}

	public void setAno_evento(int ano_evento) {
		this.ano_evento = ano_evento;
	}

	public int getAcerto_minimo_exigido() {
		return acerto_minimo_exigido;
	}

	public void setAcerto_minimo_exigido(int acerto_minimo_exigido) {
		this.acerto_minimo_exigido = acerto_minimo_exigido;
	}

	public double getMedia_acertos() {
		return media_acertos;
	}

	public void setMedia_acertos(double media_acertos) {
		this.media_acertos = media_acertos;
	}

	public double getNumero_inscritos() {
		return numero_inscritos;
	}

	public void setNumero_inscritos(double numero_inscritos) {
		this.numero_inscritos = numero_inscritos;
	}

	public int getNumero_vagas() {
		return numero_vagas;
	}

	public void setNumero_vagas(int numero_vagas) {
		this.numero_vagas = numero_vagas;
	}
	
	public Evento(){
		
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Evento))
            return false;
        Evento other = (Evento) obj;
        if (id_evento != other.id_evento)
            return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id_evento;
        return result;
    }
 
    @Override
    public String toString() {
        return "Evento [id_evento=" + id_evento + "]";
    }
	
}
