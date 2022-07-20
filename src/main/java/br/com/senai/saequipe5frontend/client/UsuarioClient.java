package br.com.senai.saequipe5frontend.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.senai.saequipe5frontend.dto.Usuario;
import org.springframework.beans.factory.annotation.Value;

@Component
public class UsuarioClient {

	@Value("${endpoint}")
	private String urlEndpoint;

	private final String resource = "/usuarios";

	@Autowired
	private RestTemplateBuilder builder;

	public Usuario logar(Usuario usuario) {
		RestTemplate httpClient = builder.build();

		Usuario usuarioLogado = httpClient.postForObject(urlEndpoint + resource, usuario, Usuario.class);

		return usuarioLogado;
	}
}