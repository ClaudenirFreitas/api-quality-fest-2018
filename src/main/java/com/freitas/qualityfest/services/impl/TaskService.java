package com.freitas.qualityfest.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freitas.qualityfest.entities.Task;
import com.freitas.qualityfest.enums.Prioridade;
import com.freitas.qualityfest.exceptions.PrioridadeAltaException;
import com.freitas.qualityfest.repositories.TaskRepository;
import com.freitas.qualityfest.services.ITaskService;

@Service
public class TaskService implements ITaskService {

	private static final Logger LOGGER = Logger.getLogger(TaskService.class.getName());

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Task> listar() {

		List<Task> tasks = taskRepository.findAll();

		LOGGER.info("Recuperadas " + tasks.size() + " tasks.");

		return tasks;
	}

	@Override
	public Task buscar(final Long id) {

		Task task = taskRepository.findOne(id);

		LOGGER.info("Buscando Task de ID: " + id + ". Retorno: " + task);

		return task;

	}

	@Override
	public Task salvar(final Task task) throws PrioridadeAltaException {

		LOGGER.info("Salvando a task: " + task);

		if (Objects.isNull(task.getId())) {
			return criar(task);
		}

		return update(task);

	}

	@Override
	public void excluir(Long id) {

		taskRepository.delete(id);
		
	}
	
	@Override
	public boolean exists(final Long id) {

		boolean exists = taskRepository.exists(id);

		LOGGER.info("Procurando pelo ID: " + id + ". Localizado: " + exists);

		return exists;

	}

	private Task criar(final Task task) throws PrioridadeAltaException {

		validar(task);

		Task t = new Task();
		t.setNome(task.getNome());
		t.setDescricao(task.getDescricao());
		t.setPrioridade(task.getPrioridade());
		t.setDataCriacao(new Date());
		
		taskRepository.save(t);
		
		return t;
	}

	private Task update(final Task task) throws PrioridadeAltaException {

		Task taskDB = taskRepository.findOne(task.getId());

		validar(taskDB);

		taskDB.setNome(task.getNome());
		taskDB.setDescricao(task.getDescricao());
		taskDB.setPrioridade(task.getPrioridade());

		taskRepository.save(taskDB);
		
		return taskDB;

	}

	private void validar(final Task task) throws PrioridadeAltaException {

		if (Objects.nonNull(task.getId()) && Prioridade.ALTA.equals(task.getPrioridade())) {
			throw new PrioridadeAltaException();
		}

	}

}
