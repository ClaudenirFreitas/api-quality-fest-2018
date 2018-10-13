package com.freitas.qualityfest.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.freitas.qualityfest.dtos.TaskRequestDTO;
import com.freitas.qualityfest.dtos.TaskResponseDTO;
import com.freitas.qualityfest.entities.Task;
import com.freitas.qualityfest.exceptions.PrioridadeAltaException;
import com.freitas.qualityfest.services.ITaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = { "Tasks" })
@RequestMapping("tasks")
public class TaskController {

	@Autowired
	private ITaskService taskService;

	@GetMapping
	@ApiOperation(value = "Retorna todas as tasks cadastradas", response = TaskResponseDTO.class, responseContainer = "List", nickname = "listar")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Tasks cadastradas") 
	})
	public ResponseEntity<List<TaskResponseDTO>> listar() {
		
		List<TaskResponseDTO> tasks = taskService.listar()
										         .stream()
										         .map(TaskResponseDTO::create)
										         .collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(tasks);
	}
	
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Retorna a task com base no identificador", response = TaskResponseDTO.class, nickname = "buscar")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Task encontrada com sucesso"),
		@ApiResponse(code = 404, message = "Task não localizada") 
	})
	public ResponseEntity<TaskResponseDTO> buscar(@ApiParam(value = "ID da task", required = true) @PathVariable("id") Long id) {

		Task task = taskService.buscar(id);
		
		if (Objects.isNull(task)) {
			return ResponseEntity.notFound().build();
		}

		TaskResponseDTO taskDTO = TaskResponseDTO.create(task);
		return ResponseEntity.status(HttpStatus.OK).body(taskDTO);

	}

	@PostMapping
	@ApiOperation(value = "Criar uma nova task", response = TaskResponseDTO.class, nickname = "criarNovaTask")
	@ApiResponses(value = { 
		@ApiResponse(code = 201, message = "Task criada com sucesso"),
		@ApiResponse(code = 422, message = "Task inválida") 
	})
	public ResponseEntity<TaskResponseDTO> criar(@Valid @RequestBody TaskRequestDTO taskDTO, BindingResult result) throws PrioridadeAltaException {

		if (result.hasErrors()) {
			Set<String> erros = result.getAllErrors()
				  .stream()
				  .map(ObjectError::getDefaultMessage)
				  .collect(Collectors.toSet());
			
			TaskResponseDTO response = new TaskResponseDTO().errors(erros);
			return ResponseEntity.unprocessableEntity().body(response);
		}

		Task task = taskService.salvar(taskDTO.toModel());

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				                                  .path("/{id}")
				                                  .buildAndExpand(task.getId())
				                                  .toUri();

		TaskResponseDTO taskResponse = TaskResponseDTO.create(task);
		
		return ResponseEntity.created(location).body(taskResponse);

	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Atualiza a task com base no identificador", response = TaskResponseDTO.class, nickname = "atualizar")
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Task atualizada com sucesso"),
		@ApiResponse(code = 404, message = "Task não localizada"),
		@ApiResponse(code = 422, message = "Task inválida") 
	})
	public ResponseEntity<TaskResponseDTO> atualizar(@ApiParam(value = "ID da task", required = true) @PathVariable("id") Long id,
			                                         @Valid @RequestBody TaskRequestDTO taskDTO, 
			                                         BindingResult result) throws PrioridadeAltaException {
		
		if (result.hasErrors()) {
			Set<String> erros = result.getAllErrors()
				  .stream()
				  .map(ObjectError::getDefaultMessage)
				  .collect(Collectors.toSet());
			
			TaskResponseDTO response = new TaskResponseDTO().errors(erros);
			return ResponseEntity.unprocessableEntity().body(response);
		}

		boolean exists = taskService.exists(id);

		if (exists) {

			Task task = taskDTO.toModel(id);
			task = taskService.salvar(task);
			TaskResponseDTO response = TaskResponseDTO.create(task);
			return ResponseEntity.status(HttpStatus.OK).body(response);

		}

		return ResponseEntity.notFound().build();

	}
	
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Remove a task com base no identificador", response = Void.class, nickname = "excluir")
	@ApiResponses(value = { 
		@ApiResponse(code = 204, message = "Task removida com sucesso"),
		@ApiResponse(code = 404, message = "Task não localizada") 
	})
	public ResponseEntity<Void> excluir(@ApiParam(value = "ID da task", required = true) @PathVariable("id") Long id) {

		boolean exists = taskService.exists(id);

		if (exists) {

			taskService.excluir(id);
			ResponseEntity.noContent().build();
		
		}

		return ResponseEntity.notFound().build();

	}

}