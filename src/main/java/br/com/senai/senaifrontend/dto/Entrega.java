package br.com.senai.senaifrontend.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Entrega {

	@EqualsAndHashCode.Include
	private Integer id;
	
	private String enderecoCompleto;
	
	private LocalDate dataDeEntrega;
	
	private String entregue;
	
	private String descricao;
	
	private Entregador entregador;
	
	
}
