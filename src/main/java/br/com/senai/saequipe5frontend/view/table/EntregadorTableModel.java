package br.com.senai.saequipe5frontend.view.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.saequipe5frontend.dto.Entregador;

public class EntregadorTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final int QTDE_COLUNAS = 2;

	private List<Entregador> entregadores;

	public EntregadorTableModel(List<Entregador> entregadores) {
		this.entregadores = entregadores;
	}

	@Override
	public int getRowCount() {
		return entregadores.size();
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
			return "Nome Completo";
		}

		throw new IllegalArgumentException("Indice inválido");
	}

	public Entregador getPor(int rowIndex) {
		return entregadores.get(rowIndex);
	}

	public void removePor(int rowIndex) {
		this.entregadores.remove(rowIndex);
	}

	public void remover(Entregador entregador) {
		this.entregadores.remove(entregador);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return this.entregadores.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return this.entregadores.get(rowIndex).getNomeCompleto();
		}
		throw new IllegalArgumentException("Índice inválido");
	}

}
