package br.com.senai.saequipe5frontend.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.senai.saequipe5frontend.client.EntregadorClient;
import br.com.senai.saequipe5frontend.dto.Entregador;
import br.com.senai.saequipe5frontend.dto.Usuario;
@Component
public class TelaPrincipalEntregador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsurioLogado;
	private JTextField edtUsuarioConectado;
	@Autowired
	private TelaCadastroEntregador cadastro;
	@Autowired
	private EntregadorClient client;
	private Entregador entregadorConectado;
	@Autowired
	@Lazy
	TelaLogin telaLogin;

	public void carregarTelaEntregador(Usuario usuario) {
		edtUsuarioConectado.setText(usuario.getNomeCompleto());
		this.setVisible(true);
		this.entregadorConectado = client.buscarPor(usuario);
	}
	
	public TelaPrincipalEntregador() {
		setTitle("Principal (Acesso Entregador) - SA System 1.5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnEntregador = new JButton("Entregador");
		btnEntregador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastro.colocarEmEdicao(entregadorConectado, entregadorConectado.getUsuario());
			}
		});
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				telaLogin.setVisible(true);
			}
		});
		
		txtUsurioLogado = new JTextField();
		txtUsurioLogado.setEditable(false);
		txtUsurioLogado.setFont(new Font("Dialog", Font.BOLD, 13));
		txtUsurioLogado.setText("Usuário Logado");
		txtUsurioLogado.setColumns(10);
		
		edtUsuarioConectado = new JTextField();
		edtUsuarioConectado.setEditable(false);
		edtUsuarioConectado.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(txtUsurioLogado, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(edtUsuarioConectado, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnEntregador, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSair, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
					.addGap(162))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(80)
					.addComponent(btnEntregador)
					.addGap(12)
					.addComponent(btnSair)
					.addGap(85)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtUsurioLogado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addComponent(edtUsuarioConectado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
