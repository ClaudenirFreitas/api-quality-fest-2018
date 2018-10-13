package com.freitas.qualityfest.exceptions;

public class PrioridadeAltaException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "Prioridade com status alta";

	public PrioridadeAltaException() {
		super(MESSAGE);
	}

}