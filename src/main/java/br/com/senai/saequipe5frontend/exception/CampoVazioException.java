package br.com.senai.saequipe5frontend.exception;

public class CampoVazioException extends IllegalArgumentException{
	
	private static final long serialVersionUID = 1L;

	public CampoVazioException(String campo, char genero) {
		super(String.valueOf(genero).toUpperCase() + " " + campo + " não foi informad" + String.valueOf(genero).toLowerCase() + ".");
	}
	
}
