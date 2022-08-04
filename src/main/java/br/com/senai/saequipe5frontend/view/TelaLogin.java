package br.com.senai.saequipe5frontend.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saequipe5frontend.client.UsuarioClient;
import br.com.senai.saequipe5frontend.dto.Usuario;
import br.com.senai.saequipe5frontend.enums.Perfil;
import br.com.senai.saequipe5frontend.exception.CampoVazioException;
import br.com.senai.saequipe5frontend.exception.LoginInvalidoException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

@Component
public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtLogin;
	private JPasswordField edtSenha;

	@Autowired
	private UsuarioClient client;

	@Autowired
	private TelaPrincipalGestor telaPrincipalGestor;

	@Autowired
	private TelaPrincipalEntregador telaPrincipalEntregador;

	public void acessar() {
		this.edtLogin.setText("");
		this.edtSenha.setText("");
		this.setVisible(true);
	}
	
	public TelaLogin() {
		setTitle("Login - SA System 1.5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 303, 212);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));

		edtLogin = new JTextField();
		edtLogin.setColumns(10);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 11));

		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					String loginDigitado = edtLogin.getText();
					if (loginDigitado.isEmpty()) {
						throw new CampoVazioException("Login", 'o');
					}
					String senhaDigitada = String.valueOf(edtSenha.getPassword());
					if (senhaDigitada.isEmpty()) {
						throw new CampoVazioException("Senha", 'a');
					}
					navegar(loginDigitado, senhaDigitada);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}

			private void navegar(String login, String senha) {
				Usuario usuarioLogado = client.logar(login);
				if (usuarioLogado != null && usuarioLogado.getSenha().equals(senha)) {
					if (usuarioLogado.getPerfil() == Perfil.GESTOR) {
						telaPrincipalGestor.carregarTelaGestor(usuarioLogado);
					} else if (usuarioLogado.getPerfil() == Perfil.ENTREGADOR) {
						telaPrincipalEntregador.carregarTelaEntregador(usuarioLogado);
					}
					setVisible(false);
				} else {
					throw new LoginInvalidoException();
				}
			}
		});

		edtSenha = new JPasswordField();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(edtSenha,
								GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING,
								gl_contentPane.createSequentialGroup().addGap(101).addComponent(btnLogar))
						.addGroup(Alignment.LEADING,
								gl_contentPane.createSequentialGroup().addGap(10)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(edtLogin, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
												.addComponent(lblNewLabel).addComponent(lblSenha,
														GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(23).addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(edtSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(12).addComponent(btnLogar).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}
}
