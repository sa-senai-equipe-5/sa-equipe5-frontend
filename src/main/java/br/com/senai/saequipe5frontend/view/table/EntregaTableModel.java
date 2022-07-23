package br.com.senai.saequipe5frontend.view.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.saequipe5frontend.dto.Entrega;

public class EntregaTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final int QTDE_COLUNAS = 3;

	private List<Entrega> entregas;

	public EntregaTableModel(List<Entrega> entregas) {
		this.entregas = entregas;
	}

	@Override
	public int getRowCount() {
		return entregas.size();
	}

	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "ID";
		} else if (column == 1) {
			return "Entrega";
		} else if (column == 2) {
			return "Endereço";
		}

		throw new IllegalArgumentException("Indice inválido");
	}

	public Entrega getPor(int rowIndex) {
		return entregas.get(rowIndex);
	}

	public void removePor(int rowIndex) {
		this.entregas.remove(rowIndex);
	}

	public void remover(Entrega entrega) {
		this.entregas.remove(entrega);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return this.entregas.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return this.entregas.get(rowIndex).getStringDataDeEntrega();
		} else if (columnIndex == 2) {
			return this.entregas.get(rowIndex).getEnderecoCompleto();
		}
		throw new IllegalArgumentException("Índice inválido");
	}

}
