package hello.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "candidato")
public class Candidato implements Serializable {

	@Id
	@JsonIgnore
	@Column(name = "id_candidato")
	private int id_candidato;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_evento",referencedColumnName="id_evento")
	private Evento evento;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cpf", nullable = false)
	private String cpf;

	@Column(name = "unidade_federativa", nullable = false)
	private String unidade_federativa;

	@Column(name = "cidade", nullable = false)
	private String cidade;

	@Column(name = "bairro", nullable = false)
	private String bairro;

	@Column(name = "data_nascimento", nullable = false)
	private String data_nascimento;

	@Column(name = "acertos_total", nullable = false)
	private String acertos_total;

	public Candidato(){
	}	
	
	public int getId_candidato() {
		return id_candidato;
	}

	public void setId_candidato(int id_candidato) {
		this.id_candidato = id_candidato;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getUnidade_federativa() {
		return unidade_federativa;
	}

	public void setUnidade_federativa(String unidade_federativa) {
		this.unidade_federativa = unidade_federativa;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getAcertos_total() {
		return acertos_total;
	}

	public void setAcertos_total(String acertos_total) {
		this.acertos_total = acertos_total;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Candidato))
			return false;
		Candidato other = (Candidato) obj;
		if (id_candidato != other.id_candidato)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
				return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_candidato;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Candidato [id=" + id_candidato + ", nome=" + nome + "]";
	}

}