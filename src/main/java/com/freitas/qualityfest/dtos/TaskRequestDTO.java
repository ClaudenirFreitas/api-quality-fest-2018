package com.freitas.qualityfest.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.freitas.qualityfest.entities.Task;
import com.freitas.qualityfest.enums.Prioridade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Task Request")
public class TaskRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Nome", required = true)
	@NotNull(message = "Nome obrigatório")
	@Length(min = 5, max = 50, message = "Local de Destino deve estar entre 3 e 40 caracteres")
	private String nome;

	@ApiModelProperty(value = "Descrição", required = true)
	@NotNull(message = "Descrição obrigatória")
	@Length(min = 5, max = 50, message = "Local de Destino deve estar entre 3 e 40 caracteres")
	private String descricao;

	@ApiModelProperty(value = "Prioridade", required = true)
	@NotNull(message = "Prioridade obrigatória")
	private Prioridade prioridade;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TaskRequestDTO nome(String nome) {
		this.nome = nome;
		return this;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TaskRequestDTO descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public TaskRequestDTO prioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
		return this;
	}

	public Task toModel() {
		return new Task().nome(nome)
				         .descricao(descricao)
				         .prioridade(prioridade);
	}

	public Task toModel(final Long id) {
		return new Task().id(id)
				         .nome(nome)
				         .descricao(descricao)
				         .prioridade(prioridade);
	}

}