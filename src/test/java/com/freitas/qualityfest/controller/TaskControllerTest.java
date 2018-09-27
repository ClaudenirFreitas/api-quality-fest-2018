package com.freitas.qualityfest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freitas.qualityfest.dtos.TaskRequestDTO;
import com.freitas.qualityfest.entities.Task;
import com.freitas.qualityfest.enums.Prioridade;
import com.freitas.qualityfest.services.ITaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ITaskService taskService;

	@Test
	public void validandoBuscaComSucesso() throws Exception {

		Task task01 = new Task().nome("nome task01")
								.descricao("descricao task01")
								.prioridade(Prioridade.BAIXA);
		
		Task task02 = new Task().nome("nome task02")
								.descricao("descricao task02")
								.prioridade(Prioridade.MEDIA);
		
		given(taskService.listar()).willReturn(Arrays.asList(task01, task02));

		mvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON))
		                         .andExpect(status().isOk())
				                 .andExpect(jsonPath("$", hasSize(2)))
				                 .andExpect(jsonPath("$[0].nome", is("nome task01")))
				                 .andExpect(jsonPath("$[0].descricao", is("descricao task01")))
				                 .andExpect(jsonPath("$[0].prioridade", is("BAIXA")))
				                 .andExpect(jsonPath("$[1].nome", is("nome task02")))
				                 .andExpect(jsonPath("$[1].descricao", is("descricao task02")))
				                 .andExpect(jsonPath("$[1].prioridade", is("MEDIA")));
	}
	
	@Test
	public void validandoCriacaoComSucesso() throws Exception {

		Task taskMock01 = new Task().id(1L)
				                    .nome("nome task01")
								    .descricao("descricao task01")
								    .prioridade(Prioridade.BAIXA)
								    .dataCriacao(new Date());
		
		when(taskService.salvar(eq(taskMock01))).thenReturn(taskMock01);
		
		TaskRequestDTO task = new TaskRequestDTO().nome("nome task01")
				                                  .descricao("descricao task01")
				                                  .prioridade(Prioridade.BAIXA);

		String payload = objectMapper.writeValueAsString(task);
		
		mvc.perform(post("/tasks").content(payload)
				                  .contentType(MediaType.APPLICATION_JSON))
		                          .andExpect(status().isCreated())
				                  .andExpect(header().string("location", containsString("/tasks/1")))
				                  .andExpect(jsonPath("$.nome", is("nome task01")))
				                  .andExpect(jsonPath("$.descricao", is("descricao task01")))
				                  .andExpect(jsonPath("$.prioridade", is("BAIXA")));

	}
	
	@Test
	public void validandoCriacaoComPayloadInvalidoSemDescricao() throws Exception {

		TaskRequestDTO task = new TaskRequestDTO().nome("nome task01")
				                                  .prioridade(Prioridade.BAIXA);

		String payload = objectMapper.writeValueAsString(task);
		
		mvc.perform(post("/tasks").content(payload)
				                  .contentType(MediaType.APPLICATION_JSON))
		                          .andExpect(status().isUnprocessableEntity())
				                  .andExpect(jsonPath("$.errors[0]", is("Descrição obrigatória")));

	}

}
