package br.com.senai.saequipe5frontend.dto;

import br.com.senai.saequipe5frontend.enums.Perfil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
	
	@EqualsAndHashCode.Include
	private Integer id;
	private String nomeCompleto;
	private String login;
	private String senha;
	private Perfil perfil;
}
