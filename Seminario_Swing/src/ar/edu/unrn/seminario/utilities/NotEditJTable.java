package ar.edu.unrn.seminario.utilities;

import javax.swing.JTable;

public class NotEditJTable extends JTable {

	private static final long serialVersionUID = 1L;

	public boolean isCellEditable(int row, int column) {
		return false;
		};
	
}
