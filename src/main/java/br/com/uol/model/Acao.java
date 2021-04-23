package br.com.uol.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Acao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String codigo;
	private String acao;
	private Double quantidadeTeorica;	
	private Double participacao;
	

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Double getQuantidadeTeorica() {
		return quantidadeTeorica;
	}
	public void setQuantidadeTeorica(Double quantidadeTeorica) {
		this.quantidadeTeorica = quantidadeTeorica;
	}
	public Double getParticipacao() {
		return participacao;
	}
	public void setParticipacao(Double participacao) {
		this.participacao = participacao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
