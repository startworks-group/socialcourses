package com.startworksgroup.socialcourses.services.exceptions;

public class CursoNaoEncontradoException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1343251115485062764L;

	public CursoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public CursoNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
