package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.utilities.NotEditJTable;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListadoDePersonal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JButton btnNewButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
					ListadoDePersonal frame = new ListadoDePersonal(new PersistenceApi(),labels );
					frame.setVisible(true);

			}
		});
	}
	public ListadoDePersonal(IApi api, ResourceBundle labels) {
		setTitle(labels.getString("listado.de.personal.titulo"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 729, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 703, 398);
		panel.add(scrollPane);
		table = new NotEditJTable();
		
		String[] titulos = { 
				labels.getString("listado.de.personal.titulos.nombre"),
				labels.getString("listado.de.personal.titulos.apellido"),
				labels.getString("listado.de.personal.titulos.telefono"),
				labels.getString("listado.de.personal.titulos.dni"),
				labels.getString("listado.de.personal.titulos.correo.electronico")
				
		};
		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar
		List<RecolectorDTO> recolectores;

			try {
				recolectores = api.obtenerRecolectores();
				for (RecolectorDTO r : recolectores) {
					modelo.addRow(new Object[] { 
								r.getNombre(),
								r.getApellido(),
								r.getTelefono(),
								r.getDni(),
								r.getEmail()
					});
				}
				
			} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				
			}
		table.setModel(modelo);
			

		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton(labels.getString("listado.de.personal.button.volver.menu.principal"));
		btnNewButton.setBounds(0, 398, 703, 23);
		btnNewButton.addActionListener((e)->{
			setVisible(false);
			dispose();
			
		});
		panel.add(btnNewButton);
			
		
}
}
