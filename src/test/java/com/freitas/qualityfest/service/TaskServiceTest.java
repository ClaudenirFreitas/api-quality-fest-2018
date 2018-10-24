package com.freitas.qualityfest.service;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.freitas.qualityfest.entities.Task;
import com.freitas.qualityfest.enums.Prioridade;
import com.freitas.qualityfest.exceptions.PrioridadeAltaException;
import com.freitas.qualityfest.repositories.TaskRepository;
import com.freitas.qualityfest.services.ITaskService;
import com.freitas.qualityfest.services.impl.TaskService;

@RunWith(SpringRunner.class)
public class TaskServiceTest {

	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {

		@Bean
		public ITaskService taskService() {
			return new TaskService();
		}

	}

	@Autowired
	private ITaskService taskService;

	@MockBean
	private TaskRepository taskRepositoryMock;

	@Test
	public void criandoTaskComSucesso() throws Exception {

		// create mock
		Task taskMock01 = new Task().id(1L)
				                    .nome("task 01 nome")
				                    .descricao("task 01 descricao")
				                    .prioridade(Prioridade.BAIXA);

		when(taskRepositoryMock.save(eq(taskMock01))).thenReturn(taskMock01);

		// give
		Task task01 = new Task().nome("task 01 nome")
				                .descricao("task 01 descricao")
				                .prioridade(Prioridade.BAIXA);

		// when
		Task taskDB = taskService.salvar(task01);

		// then
		Assert.assertNotNull("Data de criacao deve estar preenchida", taskDB.getDataCriacao());
		Assert.assertEquals("Validando nome", "task 01 nome", taskDB.getNome());
		Assert.assertEquals("Validando descricao", "task 01 descricao", taskDB.getDescricao());
		Assert.assertEquals("Validando prioridade", Prioridade.BAIXA, taskDB.getPrioridade());

	}

	@Test(expected = PrioridadeAltaException.class)
	public void atualizandoTaskComPrioridadeFinalizada() throws Exception {
		
		// create mock
		Task taskMock01 = new Task().id(1L)
				                    .nome("task 01 nome")
				                    .descricao("task 01 descricao")
				                    .prioridade(Prioridade.ALTA);

		when(taskRepositoryMock.findOne(1L)).thenReturn(taskMock01);

		Task task = new Task().id(1L)
				              .descricao("task 01 descricao")
				              .prioridade(Prioridade.ALTA);

		taskService.salvar(task);

	 }
	
	@Test
	public void atualizandoTaskComPrioridadeFinalizadaValidandoException() throws Exception {
		
		// create mock
		Task taskMock01 = new Task().id(1L)
				                    .nome("task 01 nome")
				                    .descricao("task 01 descricao")
				                    .prioridade(Prioridade.ALTA);

		when(taskRepositoryMock.findOne(1L)).thenReturn(taskMock01);

		Task task = new Task().id(1L)
				              .descricao("task 01 descricao")
				              .prioridade(Prioridade.ALTA);

		try {

			taskService.salvar(task);
			Assert.fail("Não caiu na exception esperada");

		} catch (PrioridadeAltaException ex) {

			Assert.assertEquals("Validando mensagem", "Prioridade com status finalizada", ex.getMessage());

		} catch (Exception ex) {

			Assert.fail("Não caiu na exception esperada");

		}		

	 }

}
