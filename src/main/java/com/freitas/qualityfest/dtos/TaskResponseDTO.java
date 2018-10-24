package com.freitas.qualityfest.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.freitas.qualityfest.entities.Task;
import com.freitas.qualityfest.enums.Prioridade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Task Response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Identificador da Task", required = true)
	private Long id;

	@ApiModelProperty(value = "Nome", required = true)
	private String nome;

	@ApiModelProperty(value = "Descrição", required = true)
	private String descricao;

	@ApiModelProperty(value = "Data de criação", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Date dataCriacao;

	@ApiModelProperty(value = "Prioridade", required = true)
	private Prioridade prioridade;
	
	@ApiModelProperty(value = "Lista de erros", hidden = true)
	private Set<String> errors;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public TaskResponseDTO id(Long id) {
		this.id = id;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public TaskResponseDTO nome(String nome) {
		this.nome = nome;
		return this;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public TaskResponseDTO descricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public TaskResponseDTO dataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
		return this;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}
	
	public TaskResponseDTO prioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
		return this;
	}
	
	public Set<String> getErrors() {
		return errors;
	}

	public void setErrors(Set<String> errors) {
		this.errors = errors;
	}
	
	public TaskResponseDTO errors(Set<String> errors) {
		this.errors = errors;
		return this;
	}

	public static TaskResponseDTO create(final Task task) {
		return new TaskResponseDTO().id(task.getId())
				                    .nome(task.getNome())
				                    .descricao(task.getDescricao())
				                    .dataCriacao(task.getDataCriacao())
				                    .prioridade(task.getPrioridade());
	}

}
