package br.com.senai.saequipe5frontend.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
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

import br.com.senai.saequipe5frontend.client.EntregadorClient;
import br.com.senai.saequipe5frontend.dto.Entregador;
import br.com.senai.saequipe5frontend.exception.CampoVazioException;
import br.com.senai.saequipe5frontend.view.table.EntregadorTableModel;

@Component
public class TelaListagemEntregador extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	@Autowired
	private EntregadorClient client;

	@Autowired
	private TelaCadastroEntregador cadastro;

	private JTextField edtFiltro;
	@Autowired
	@Lazy
	private TelaPrincipalGestor telaPrincipal;
	
	private void atualizar(JTable tabela) {
		List<Entregador> entregadores = client.listarPor(edtFiltro.getText());
		EntregadorTableModel model = new EntregadorTableModel(entregadores);
		tabela.setModel(model);
		TableColumnModel cm = tabela.getColumnModel();
		cm.getColumn(0).setPreferredWidth(50);
		cm.getColumn(1).setPreferredWidth(352);
		tabela.updateUI();
	}

	private Entregador getEntregadorSelecionadoNa(JTable tabela, String botao) {
		int linhaSelecionada = tabela.getSelectedRow();
		if (linhaSelecionada < 0) {
			throw new IllegalArgumentException("Selecione um registro na tabela para " + botao);
		}
		EntregadorTableModel model = (EntregadorTableModel) tabela.getModel();
		Entregador itemSelecionado = model.getPor(linhaSelecionada);
		return itemSelecionado;
	}

	private void removerRegistroDa(JTable tabela) {
		try {
			Entregador entregadorSelecionado = getEntregadorSelecionadoNa(tabela, "remoção");

			int opcaoSelecionada = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente remover?!", "Remoção",
					JOptionPane.YES_NO_OPTION);

			if (opcaoSelecionada == JOptionPane.YES_OPTION) {
				this.client.remover(entregadorSelecionado);
				((EntregadorTableModel) tabela.getModel()).remover(entregadorSelecionado);
				tabela.updateUI();
				JOptionPane.showMessageDialog(contentPane, "Entregador removido com sucesso");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}

	private void editarRegistroDa(JTable tabela) {
		try {
			Entregador registroSelecionado = getEntregadorSelecionadoNa(tabela, "edição");
			this.cadastro.colocarEmEdicao(registroSelecionado);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}

	public TelaListagemEntregador() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				telaPrincipal.setVisible(true);
			}
		});
		setTitle("Entregador (LISTAGEM) - SA System 1.5");
		setBounds(100, 100, 449, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastro.colocarEmInclusao();
			}
		});
		
		edtFiltro = new JTextField();
		edtFiltro.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Filtro");
		
		JTable tabela = new JTable();
		tabela.setFillsViewportHeight(true);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if (edtFiltro.getText().isBlank()) {
					throw new CampoVazioException("filtro", 'o');
				}
				atualizar(tabela);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
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
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 305, Short.MAX_VALUE)
							.addComponent(btnAdicionar))
						.addComponent(edtFiltro, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
						.addComponent(btnListar)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(263, Short.MAX_VALUE)
							.addComponent(btnRemover)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEditar)))
					.addGap(2))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(30)
							.addComponent(lblNewLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnAdicionar)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(edtFiltro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnListar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRemover)
						.addComponent(btnEditar))
					.addGap(173))
		);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}
}
