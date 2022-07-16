package br.com.senai.senaifrontend.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.senai.senaifrontend.dto.Entrega;
import br.com.senai.senaifrontend.dto.Entregador;

@Component
public class EntregaClient {

	@Value("${endpoint}")
	private String urlEndpoint;
	
	@Autowired
	private ObjectMapper mapper;
	
	private final String resource = "/entrega";
	
	@Autowired
	private RestTemplateBuilder builder;
	
	public Entrega inserir(Entrega novoEntrega) {
		
		RestTemplate httpClient = builder.build();
		
		URI uri = httpClient.postForLocation(
				urlEndpoint + resource, novoEntrega);
		
		Entrega entregaSalva = httpClient
				.getForObject(urlEndpoint + uri.getPath(), 
						Entrega.class);
		
		return entregaSalva;
		
	}
	
	public void editar(Entrega entregaSalva) {
		RestTemplate httpClient = builder.build();
		httpClient.put(urlEndpoint + resource, 
				entregaSalva);
	}
	
	public void remover(Entrega entregaSalva) {
		RestTemplate httpClient = builder.build();
		httpClient.delete(urlEndpoint + resource 
				+ "/id/" + entregaSalva.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<Entrega> listarPor(String enderecoCompleto){
		
		RestTemplate httpClient = builder.build();
		
		List<LinkedHashMap<String, Object>> response = httpClient.getForObject(
				urlEndpoint + resource + "/endereco/" + enderecoCompleto, List.class);
		
		List<Entrega> entregas = new ArrayList<Entrega>();
		
		for (LinkedHashMap<String, Object> item : response) {
			Entrega entrega = mapper.convertValue(item, Entrega.class);
			entregas.add(entrega);
		}
		
		return entregas;
	}
	
	
}
