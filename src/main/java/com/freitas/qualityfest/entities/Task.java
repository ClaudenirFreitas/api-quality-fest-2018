package com.freitas.qualityfest.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.freitas.qualityfest.enums.Prioridade;

@Entity
@Table(name = "T_TASK")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "TXT_NAME", nullable = false)
	private String nome;

	@NotNull
	@Column(name = "TXT_DESCRIPTION", nullable = false)
	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TXT_PRIORITY", nullable = false)
	private Prioridade prioridade;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CREATE", nullable = false)
	private Date dataCriacao;

	public Task() {

	}

	public Task(final Task other) {
		this.id = other.id;
		this.nome = other.nome;
		this.descricao = other.descricao;
		this.prioridade = other.prioridade;
		this.dataCriacao = other.dataCriacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task id(Long id) {
		this.id = id;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Task nome(String nome) {
		this.nome = nome;
		return this;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Task descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public Task prioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
		return this;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Task dataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", nome=" + nome + ", prioridade=" + prioridade + "]";
	}

}
