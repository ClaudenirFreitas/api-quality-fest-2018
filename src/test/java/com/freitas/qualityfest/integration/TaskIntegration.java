package com.freitas.qualityfest.integration;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.freitas.qualityfest.entities.Task;
import com.freitas.qualityfest.enums.Prioridade;
import com.freitas.qualityfest.repositories.TaskRepository;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TaskIntegration {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TaskRepository taskRepository;

	@Test
	public void validarCriacaoDaTaskEbuscaPorNome() {
		
		long now = System.currentTimeMillis();

		// given
		Task task01 = new Task().nome("task nome 01")
				                .descricao("task descricao 01")
				                .prioridade(Prioridade.BAIXA)
				                .dataCriacao(new Date(now));

		entityManager.persist(task01);
		entityManager.flush();

		// when
		Task taskDB = taskRepository.findByNome(task01.getNome());

		// then
		Assert.assertNotNull("Task encontrada", taskDB);
		Assert.assertNotNull("Deve conter ID", taskDB.getId());
		Assert.assertEquals("validando nome", "task nome 01", task01.getNome());
		Assert.assertEquals("validando descricao", "task descricao 01", taskDB.getDescricao());
		Assert.assertEquals("validando prioridade", Prioridade.BAIXA, task01.getPrioridade());
		Assert.assertEquals("validando data de criacao", now, task01.getDataCriacao().getTime());
		
	}
	
	@Test
	public void validarExclusao() {
		
		// given
		Task task01 = new Task().nome("task nome 01")
				                .descricao("task descricao 01")
				                .prioridade(Prioridade.BAIXA)
				                .dataCriacao(new Date());

		entityManager.persist(task01);
		entityManager.flush();

		// when
		taskRepository.delete(task01.getId());
		Task taskDB = taskRepository.findOne(task01.getId());
		
		// then
		Assert.assertNull("Task excluida", taskDB);
		
	}
	

}