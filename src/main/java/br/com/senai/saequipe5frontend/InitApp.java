package br.com.senai.saequipe5frontend;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.senai.saequipe5frontend.view.TelaLogin;


@SpringBootApplication
public class InitApp {
	
	@Autowired
  	private TelaLogin telaDeLogin;
	
	
	
	public static void main(String[] args) {
		SpringApplicationBuilder builder = 
				new SpringApplicationBuilder(InitApp.class);
		builder.headless(false);
		builder.run(args);
	}
	
	@Bean	
	CommandLineRunner commandLineRunner(ApplicationContext ac) {
		return args -> {
			try {				
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {							
							telaDeLogin.acessar();
						} catch (Exception e) {							
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					}
				});
				
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
		};
	}

}
