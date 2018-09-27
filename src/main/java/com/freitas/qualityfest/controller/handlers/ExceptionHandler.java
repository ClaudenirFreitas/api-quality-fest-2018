package com.freitas.qualityfest.controller.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.freitas.qualityfest.TaskApplication;

@RestController
@ControllerAdvice(basePackageClasses = TaskApplication.class)
public class ExceptionHandler extends ResponseEntityExceptionHandler {


}