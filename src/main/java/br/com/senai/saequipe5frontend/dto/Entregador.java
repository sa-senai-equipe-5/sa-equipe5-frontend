package br.com.senai.saequipe5frontend.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Entregador {

	@EqualsAndHashCode.Include
	private Integer id;
	
	private String nomeCompleto;
	
	private String cpf;
	
	private LocalDate dataDeNascimento;
	
	private String rg;
	
	private Usuario usuario;
	
	public String getStringDataDeNascimento() {
		return dataDeNascimento.getDayOfMonth() + "/" + dataDeNascimento.getMonthValue() + "/" + dataDeNascimento.getYear();
	}
	
}
