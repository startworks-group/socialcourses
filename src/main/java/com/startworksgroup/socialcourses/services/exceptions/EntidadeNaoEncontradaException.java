package com.startworksgroup.socialcourses.services.exceptions;

public class EntidadeNaoEncontradaException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1343251115485062764L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public EntidadeNaoEncontradaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
