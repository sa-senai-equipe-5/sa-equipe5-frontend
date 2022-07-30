package br.com.senai.saequipe5frontend.exception;

public class LoginInvalidoException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public LoginInvalidoException() {
		super("Login ou senha inválidos");
	}
	
}
