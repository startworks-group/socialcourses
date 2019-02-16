package com.startworksgroup.socialcourses.services.exceptions;

public class InstituicaoNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5790591015918055473L;

	public InstituicaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public InstituicaoNaoEncontradaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
