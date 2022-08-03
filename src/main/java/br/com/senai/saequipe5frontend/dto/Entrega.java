package br.com.senai.saequipe5frontend.dto;

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
	
	public String getStringDataDeEntrega() {
		String[] c = dataDeEntrega.toString().split("-");
		return c[2] + "/" + c[1] + "/" + c[0];
	}
	
}
