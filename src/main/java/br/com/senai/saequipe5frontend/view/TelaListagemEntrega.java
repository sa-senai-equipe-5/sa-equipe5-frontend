package br.com.senai.saequipe5frontend.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.senai.saequipe5frontend.client.EntregaClient;
import br.com.senai.saequipe5frontend.dto.Entrega;
import br.com.senai.saequipe5frontend.dto.Usuario;
import br.com.senai.saequipe5frontend.view.table.EntregaTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
@Component
public class TelaListagemEntrega extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtFiltro;
	private JTable tabela;
	@Autowired
	private EntregaClient client;
	@Autowired
	private TelaCadastroEntrega cadastro;
	@Autowired
	@Lazy
	private TelaPrincipalGestor telaPrincipal;
	private Usuario usuarioConectado;

	public void acessar(Usuario usuario) {
		this.setVisible(true);
		this.usuarioConectado = usuario;
	}
	
	private void atualizar(JTable tabela) {
		List<Entrega> entregas = client.listarPor(edtFiltro.getText());		
		EntregaTableModel model = new EntregaTableModel(entregas);
		tabela.setModel(model);
		TableColumnModel cm = tabela.getColumnModel();
		cm.getColumn(0).setPreferredWidth(50);
		cm.getColumn(1).setPreferredWidth(352);
		tabela.updateUI();
	}
	
	private Entrega getEntregaSelecionadaNa(JTable tabela) {
		int linhaSelecionada = tabela.getSelectedRow();
		if (linhaSelecionada < 0) {
			throw new IllegalArgumentException("Selecione um registro na tabela para edição");
		}
		EntregaTableModel model = (EntregaTableModel) tabela.getModel();
		Entrega itemSelecionado = model.getPor(linhaSelecionada);
		return itemSelecionado;
	}

	private void removerRegistroDa(JTable tabela) {
		try {

			Entrega entregaSelecionada = getEntregaSelecionadaNa(tabela);

			int opcaoSelecionada = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente remover?!", "Remoção",
					JOptionPane.YES_NO_OPTION);

			if (opcaoSelecionada == JOptionPane.YES_OPTION) {
				this.client.remover(entregaSelecionada);
				((EntregaTableModel) tabela.getModel()).remover(entregaSelecionada);
				tabela.updateUI();
				JOptionPane.showMessageDialog(contentPane, "Entrega removida com sucesso");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}

	private void editarRegistroDa(JTable tabela) {
		try {
			Entrega registroSelecionado = getEntregaSelecionadaNa(tabela);
			this.cadastro.colocarEmEdicao(registroSelecionado);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}
	
	public TelaListagemEntrega() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				telaPrincipal.carregarTelaGestor(usuarioConectado);
			}
		});
		setTitle("Entrega (LISTAGEM) - SA System 1.5");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastro.colocarEmInclusao();;
			}
		});
		
		JLabel lblNewLabel = new JLabel("Filtro");
		
		edtFiltro = new JTextField();
		edtFiltro.setColumns(10);
		
		JButton btnNewButton = new JButton("Listar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar(tabela);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerRegistroDa(tabela);
			}
		});
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarRegistroDa(tabela);
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(240)
							.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(lblNewLabel)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
							.addComponent(edtFiltro, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
							.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addContainerGap(335, Short.MAX_VALUE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
							.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addContainerGap(336, Short.MAX_VALUE)
								.addComponent(btnAdicionar))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnAdicionar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(edtFiltro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRemover)
						.addComponent(btnEditar))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		tabela = new JTable();
		tabela.setFillsViewportHeight(true);
		scrollPane.setViewportView(tabela);
		contentPane.setLayout(gl_contentPane);
	}
}
