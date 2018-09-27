package com.freitas.qualityfest.exceptions;

public class PrioridadeFinalizadaException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "Prioridade com status finalizada";

	public PrioridadeFinalizadaException() {
		super(MESSAGE);
	}

}