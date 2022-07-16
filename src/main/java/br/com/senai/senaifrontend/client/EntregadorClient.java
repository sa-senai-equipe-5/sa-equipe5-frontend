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

import br.com.senai.senaifrontend.dto.Entregador;

@Component
public class EntregadorClient {
	
	@Value("${endpoint}")
	private String urlEndpoint;
	
	@Autowired
	private ObjectMapper mapper;
	
	private final String resource = "/entregador";
	
	@Autowired
	private RestTemplateBuilder builder;
	
	public Entregador inserir(Entregador novoEntregador) {
		
		RestTemplate httpClient = builder.build();
		
		URI uri = httpClient.postForLocation(
				urlEndpoint + resource, novoEntregador);
		
		Entregador entregadorSalvo = httpClient
				.getForObject(urlEndpoint + uri.getPath(), 
						Entregador.class);
		
		return entregadorSalvo;
		
	}
	
	public void editar(Entregador entregadorSalvo) {
		RestTemplate httpClient = builder.build();
		httpClient.put(urlEndpoint + resource, 
				entregadorSalvo);
	}
	
	public void remover(Entregador entregadorSalvo) {
		RestTemplate httpClient = builder.build();
		httpClient.delete(urlEndpoint + resource 
				+ "/id/" + entregadorSalvo.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<Entregador> listarPor(String nomeCompleto){
		
		RestTemplate httpClient = builder.build();
		
		List<LinkedHashMap<String, Object>> response = httpClient.getForObject(
				urlEndpoint + resource + "/nome-completo/" + nomeCompleto, List.class);
		
		List<Entregador> entregadores = new ArrayList<Entregador>();
		
		for (LinkedHashMap<String, Object> item : response) {
			Entregador entregador = mapper.convertValue(item, Entregador.class);
			entregadores.add(entregador);
		}
		
		return entregadores;
	}
	

}
