package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;

public class ListadoVivienda extends JFrame {

	IApi api;
	private JTable table;
	DefaultTableModel modelo;
	
	private JPanel contentPane;
	private JButton buttonAtras;


	public ListadoVivienda(IApi api) {
		this.api=api;
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en"));
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		setTitle(labels.getString("listado.viviendas.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		String[] titulos = {
				labels.getString("listado.viviendas.titulos.vivienda.BARRIO"), 
				labels.getString("listado.viviendas.titulos.vivienda.CALLE"),
				labels.getString("listado.viviendas.titulos.vivienda.ALTURA"),
				labels.getString("listado.viviendas.titulos.vivienda.LATITUD"),
				labels.getString("listado.viviendas.titulos.vivienda.LONGITUD"),
				labels.getString("listado.viviendas.titulos.vivienda.NOMBRE"),
				labels.getString("listado.viviendas.titulos.vivienda.APELLIDO"),
				labels.getString("listado.viviendas.titulos.vivienda.DNI"),
				labels.getString("listado.viviendas.titulos.vivienda.EMAIL") };
		
		
		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar
		//List<ViviendaDTO> viviendas = api.obtenerViviendas();
		List<ViviendaDTO> viviendas = api.obtenerViviendas();
		// Agrega los usuarios en el model
		for (ViviendaDTO v : viviendas) {
			modelo.addRow(new Object[] { v.getDireccion().getBarrio(),v.getDireccion().getCalle(),v.getDireccion().getAltura(),v.getDireccion().getLatitud(), v.getDireccion().getLongitud(), 
			v.getDueño().getNombre(),v.getDueño().getApellido(),v.getDueño().getDni(),v.getDueño().getCorreo()});
		}
		table.setModel(modelo);
		
		scrollPane.setViewportView(table);
		
		buttonAtras = new JButton(labels.getString("listado.viviendas.button.atras"));
		buttonAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		contentPane.add(buttonAtras, BorderLayout.SOUTH);
	}
}
