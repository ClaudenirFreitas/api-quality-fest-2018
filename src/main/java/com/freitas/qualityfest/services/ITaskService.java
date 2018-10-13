package com.freitas.qualityfest.services;

import java.util.List;

import com.freitas.qualityfest.entities.Task;
import com.freitas.qualityfest.exceptions.PrioridadeAltaException;

public interface ITaskService {

	List<Task> listar();

	Task salvar(final Task task) throws PrioridadeAltaException;

	Task buscar(final Long id);
	
	void excluir(final Long id);

	boolean exists(final Long id);

}
