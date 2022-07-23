package br.com.senai.saequipe5frontend.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saequipe5frontend.dto.Usuario;

@Component
public class TelaPrincipalGestor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsurioLogado;
	private JTextField edtUsuarioConectado;
	@Autowired
	private TelaListagemEntregador telaListagemEntregador;
	@Autowired
	private TelaListagemEntrega telaListagemEntrega;

	public void carregarTelaGestor(Usuario usuario) {
		edtUsuarioConectado.setText(usuario.getNomeCompleto());
		this.setVisible(true);
	}
	
	public TelaPrincipalGestor() {
		setTitle("Principal (Acesso GESTOR) - SA System 1.5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnEntregadores = new JButton("Entregadores");
		btnEntregadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaListagemEntregador.setVisible(true);
				setVisible(false);
			}
		});

		JButton btnEntregas = new JButton("Entregas");
		btnEntregas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaListagemEntrega.setVisible(true);
				setVisible(false);
			}
		});

		JButton btnSair = new JButton("Sair");

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
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(txtUsurioLogado, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(edtUsuarioConectado, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(151)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnSair, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(btnEntregas, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
						.addComponent(btnEntregadores, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(162, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(69)
					.addComponent(btnEntregadores)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnEntregas)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSair)
					.addPreferredGap(ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUsurioLogado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtUsuarioConectado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
