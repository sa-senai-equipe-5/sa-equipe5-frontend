package br.com.senai.saequipe5frontend.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.saequipe5frontend.client.EntregaClient;
import br.com.senai.saequipe5frontend.client.EntregadorClient;
import br.com.senai.saequipe5frontend.dto.Entrega;
import br.com.senai.saequipe5frontend.dto.Entregador;
import br.com.senai.saequipe5frontend.exception.CampoVazioException;

@Component
public class TelaCadastroEntrega extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtEnderecoCompleto;
	private JTextField edtDataDeEntrega;
	private JTextPane txtDescricao;
	private JComboBox<String> cbEntregue;
	private JComboBox<Entregador> cbEntregador;
	private List<Entregador> entregadores;
	private Entrega entregaSalva;
	@Autowired
	private EntregadorClient entregadorClient;
	@Autowired
	private EntregaClient client;

	private void carregarOpcoes() {
		this.entregadores = entregadorClient.listarTodos();
		this.cbEntregue.removeAllItems();
		this.cbEntregador.removeAllItems();
		this.cbEntregue.addItem("SIM");
		this.cbEntregue.addItem("NAO");
		for (Entregador e : entregadores) {
			this.cbEntregador.addItem(e);
		}
	}
	
	public void colocarEmEdicao(Entrega entregaSalva) {
		this.carregarOpcoes();
		this.entregaSalva = entregaSalva;
		this.edtEnderecoCompleto.setText(entregaSalva.getEnderecoCompleto());
		this.edtDataDeEntrega.setText(entregaSalva.getStringDataDeEntrega());
		this.txtDescricao.setText(entregaSalva.getDescricao());
		this.cbEntregador.setSelectedItem(entregaSalva.getEntregador());
		this.cbEntregue.setSelectedItem(entregaSalva.getEntregue());
		setVisible(true);
	}

	public void colocarEmInclusao() {
		this.carregarOpcoes();
		this.entregaSalva = new Entrega();
		this.edtEnderecoCompleto.setText("");
		this.edtDataDeEntrega.setText("");
		this.txtDescricao.setText("");
		this.cbEntregador.setSelectedItem(0);
		this.cbEntregue.setSelectedItem(0);
		setVisible(true);
	}

	public TelaCadastroEntrega() {
		try {
		setTitle("Entrega (INSERÇÃO/EDIÇÃO) - SA System 1.5");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JLabel lblNewLabel = new JLabel("Endereço Completo");

		edtEnderecoCompleto = new JTextField();
		edtEnderecoCompleto.setColumns(10);

		JLabel lblEntrega = new JLabel("Entrega");

		edtDataDeEntrega = new JFormattedTextField(new MaskFormatter("##/##/####"));
		edtDataDeEntrega.setColumns(10);

		cbEntregue = new JComboBox<String>();
		cbEntregue.setToolTipText("");
		cbEntregador = new JComboBox<Entregador>();
		cbEntregador.setToolTipText("");

		JLabel lblEntregue = new JLabel("Entregue");

		JLabel lblEntregador = new JLabel("Entregador");

		JLabel lblDescrioDosProdutos = new JLabel("Descrição dos Produtos da Entrega");

		txtDescricao = new JTextPane();

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (entregaSalva == null) {
						entregaSalva = new Entrega();
					}
					entregaSalva.setEnderecoCompleto(edtEnderecoCompleto.getText());
					if (edtEnderecoCompleto.getText().isEmpty()) {
						throw new CampoVazioException("Endereço completo", 'o');
					}
					if (edtDataDeEntrega.getText().equals("  /  /    ")) {
						throw new CampoVazioException("Data de entrega", 'a');
					}
					String[] camposDaData = edtDataDeEntrega.getText().split("/");
					LocalDate data = LocalDate.of(Integer.parseInt(camposDaData[2]), Integer.parseInt(camposDaData[1]),
							Integer.parseInt(camposDaData[0]));

					entregaSalva.setDataDeEntrega(data);

					entregaSalva.setEntregue((String) cbEntregue.getSelectedItem());

					entregaSalva.setEntregador((Entregador) cbEntregador.getSelectedItem());

					entregaSalva.setDescricao(txtDescricao.getText());
					if (txtDescricao.getText().isEmpty()) {
						throw new CampoVazioException("Descrição", 'a');
					}
					if (entregaSalva.getId() == null) {
						client.inserir(entregaSalva);
						JOptionPane.showMessageDialog(contentPane, "Entrega inserida com sucesso");
						entregaSalva = client.buscarMaisRecente();
					} else {
						client.editar(entregaSalva);
						JOptionPane.showMessageDialog(contentPane, "Entrega alterada com sucesso");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel)
						.addComponent(edtEnderecoCompleto, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE))
				.addGap(4)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblEntrega, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblEntregue, GroupLayout.PREFERRED_SIZE, 59,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnConsultar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(edtDataDeEntrega, GroupLayout.PREFERRED_SIZE, 123,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cbEntregue, 0, 88, Short.MAX_VALUE))))
				.addComponent(cbEntregador, 0, 424, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblDescrioDosProdutos, GroupLayout.PREFERRED_SIZE, 209,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(215, Short.MAX_VALUE))
				.addComponent(txtDescricao, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(btnSalvar,
						GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.LEADING,
						gl_contentPane.createSequentialGroup()
								.addComponent(lblEntregador, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addComponent(btnConsultar).addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
						.addComponent(lblEntrega).addComponent(lblEntregue))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtEnderecoCompleto, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtDataDeEntrega, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbEntregue, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblEntregador)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(cbEntregador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblDescrioDosProdutos)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtDescricao, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addComponent(btnSalvar)));
		contentPane.setLayout(gl_contentPane);
		} catch (Exception e) {
			
		}
		setLocationRelativeTo(null);
	}
}
