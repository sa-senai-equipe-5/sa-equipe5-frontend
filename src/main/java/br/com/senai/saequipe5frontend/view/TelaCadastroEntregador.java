package br.com.senai.saequipe5frontend.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saequipe5frontend.client.EntregadorClient;
import br.com.senai.saequipe5frontend.dto.Entregador;
import br.com.senai.saequipe5frontend.dto.Usuario;
import br.com.senai.saequipe5frontend.enums.Perfil;

@Component
public class TelaCadastroEntregador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtNomeCompleto;
	private JTextField edtDataNascimento;
	private JTextField edtCpf;
	private JTextField edtRg;
	private JTextField edtLogin;
	private JTextField edtSenha;
	@Autowired
	private EntregadorClient client;
	private Entregador entregadorSalvo;

	public void colocarEmEdicao(Entregador entregadorSalvo) {
		this.edtNomeCompleto.setText(entregadorSalvo.getNomeCompleto());
		this.edtDataNascimento.setText(entregadorSalvo.getStringDataDeNascimento());
		this.edtCpf.setText(entregadorSalvo.getCpf());
		this.edtRg.setText(entregadorSalvo.getRg());
		this.edtLogin.setText(entregadorSalvo.getUsuario().getLogin());
		this.edtSenha.setText(entregadorSalvo.getUsuario().getSenha());
		this.entregadorSalvo = entregadorSalvo;
		setVisible(true);
	}

	public void colocarEmInclusao() {
		this.entregadorSalvo = new Entregador();
		this.edtNomeCompleto.setText("");
		this.edtDataNascimento.setText("");
		this.edtCpf.setText("");
		this.edtRg.setText("");
		this.edtLogin.setText("");
		this.edtSenha.setText("");
		setVisible(true);
	}

	public TelaCadastroEntregador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 444, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnConsultar = new JButton("Consultar");

		JLabel lblNewLabel = new JLabel("Nome Completo");

		JLabel lblDataDeNascto = new JLabel("Data de Nascimento");

		edtNomeCompleto = new JTextField();
		edtNomeCompleto.setColumns(10);

		edtDataNascimento = new JTextField();
		edtDataNascimento.setColumns(10);

		JLabel lblCpf = new JLabel("CPF");

		edtCpf = new JTextField();
		edtCpf.setColumns(10);

		edtRg = new JTextField();
		edtRg.setColumns(10);

		JLabel lblRg = new JLabel("RG");

		JLabel lblLogin = new JLabel("Login");

		edtLogin = new JTextField();
		edtLogin.setColumns(10);

		JLabel lblSenha = new JLabel("Senha");

		edtSenha = new JTextField();
		edtSenha.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (entregadorSalvo == null) {
					entregadorSalvo = new Entregador();
				}
				entregadorSalvo.setNomeCompleto(edtNomeCompleto.getText());
				String[] camposDaData = edtDataNascimento.getText().split("/");
				LocalDate data = LocalDate.of(Integer.parseInt(camposDaData[2]), Integer.parseInt(camposDaData[1]), Integer.parseInt(camposDaData[0]));
				entregadorSalvo.setDataDeNascimento(data);
				entregadorSalvo.setCpf(edtCpf.getText());
				entregadorSalvo.setRg(edtRg.getText());
				Usuario usuarioSalvo = entregadorSalvo.getUsuario();
				if (usuarioSalvo == null) {
					usuarioSalvo = new Usuario();
					entregadorSalvo.setUsuario(usuarioSalvo);
				}
				usuarioSalvo.setNomeCompleto(entregadorSalvo.getNomeCompleto());
				usuarioSalvo.setPerfil(Perfil.ENTREGADOR);
				usuarioSalvo.setLogin(edtLogin.getText());
				usuarioSalvo.setSenha(edtSenha.getText());
				if (entregadorSalvo.getId() == null) {
					client.inserir(entregadorSalvo);
				} else {
					client.editar(entregadorSalvo);
				}
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(329, Short.MAX_VALUE)
								.addComponent(btnConsultar).addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(edtNomeCompleto, GroupLayout.DEFAULT_SIZE, 292,
														Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblNewLabel)
												.addGap(214)))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(edtDataNascimento, GroupLayout.PREFERRED_SIZE, 114,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDataDeNascto, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
								.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(edtSenha, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
								.createParallelGroup(Alignment.LEADING)
								.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCpf, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 90,
														GroupLayout.PREFERRED_SIZE)
												.addGap(116))
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(lblRg, GroupLayout.PREFERRED_SIZE, 90,
																GroupLayout.PREFERRED_SIZE)
														.addGap(116))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(edtRg, GroupLayout.DEFAULT_SIZE, 194,
																Short.MAX_VALUE)
														.addContainerGap()))))
						.addGroup(
								Alignment.TRAILING, gl_contentPane.createSequentialGroup()
										.addContainerGap(329, Short.MAX_VALUE).addComponent(btnSalvar,
												GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addComponent(btnConsultar)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNewLabel).addComponent(lblDataDeNascto))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(edtNomeCompleto, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(edtDataNascimento, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblCpf).addComponent(lblRg))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(edtRg, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblLogin).addComponent(lblSenha))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(edtSenha, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSalvar)
										.addContainerGap(49, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
