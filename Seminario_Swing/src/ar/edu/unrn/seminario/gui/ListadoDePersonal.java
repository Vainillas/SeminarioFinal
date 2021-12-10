package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Comparator;
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
import ar.edu.unrn.seminario.utilities.Predicate;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;

public class ListadoDePersonal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JButton btnNewButton;
	private JTextField tfNombreApellido;
	private JTextField tfDni;
	private JTextField tfCorreo;
	private JRadioButton rdbtnDni;
	private JRadioButton rdbtnCorreo;
	private JRadioButton rdbtnNombreApellido;
	private JLabel lbFiltrado;
	private JLabel lbOrdenamiento;
	private JRadioButton rdbtnNombreApellidoOrdenamiento;
	private JRadioButton rdbtnDniOrdenamiento;
	private JRadioButton rdbtnCorreoOrdenamiento;

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
		setBounds(100, 100, 1173, 470);
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
		
		JPanel panelFiltrado = new JPanel();
		panelFiltrado.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelFiltrado.setBounds(713, 11, 424, 195);
		panel.add(panelFiltrado);
		panelFiltrado.setLayout(null);
		
		JLabel lbNombreApellido = new JLabel(labels.getString("listado.de.personal.label.nombre.y.apellido"));
		lbNombreApellido.setHorizontalAlignment(SwingConstants.TRAILING);
		lbNombreApellido.setBounds(10, 40, 148, 14);
		panelFiltrado.add(lbNombreApellido);
		
		tfNombreApellido = new JTextField();
		tfNombreApellido.setBounds(168, 40, 99, 20);
		panelFiltrado.add(tfNombreApellido);
		tfNombreApellido.setColumns(10);
		
		rdbtnNombreApellido = new JRadioButton("");
		rdbtnNombreApellido.addActionListener((e)->{
			rdbtnNombreApellido.setSelected(false);
			if(!this.tfNombreApellido.getText().equals("")) {
				 Predicate<RecolectorDTO> predicate = (RecolectorDTO r)->(r.getNombre() + " " + r.getApellido()).toLowerCase().contains(this.tfNombreApellido.getText().toLowerCase());
					try {
						reloadGrid( api.obtenerRecolectores(predicate));
					} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),0);
					}
			}
			
			
		});;
		rdbtnNombreApellido.setBounds(272, 40, 21, 21);
		panelFiltrado.add(rdbtnNombreApellido);
		
		JLabel lbDni = new JLabel(labels.getString("listado.de.personal.label.dni"));
		lbDni.setHorizontalAlignment(SwingConstants.TRAILING);
		lbDni.setBounds(10, 70, 148, 14);
		panelFiltrado.add(lbDni);
		
		JLabel lbCorreo = new JLabel(labels.getString("listado.de.personal.label.correo"));
		lbCorreo.setHorizontalAlignment(SwingConstants.TRAILING);
		lbCorreo.setBounds(10, 100, 148, 14);
		panelFiltrado.add(lbCorreo);
		
		tfDni = new JTextField();
		tfDni.setColumns(10);
		tfDni.setBounds(168, 70, 99, 20);
		panelFiltrado.add(tfDni);
		
		tfCorreo = new JTextField();
		tfCorreo.setColumns(10);
		tfCorreo.setBounds(168, 100, 99, 20);
		panelFiltrado.add(tfCorreo);
		
		rdbtnDni = new JRadioButton("");
		rdbtnDni.addActionListener((e)->{
			rdbtnDni.setSelected(false);
			if(!this.tfDni.getText().equals("")) {
				 Predicate<RecolectorDTO> predicate = (RecolectorDTO r)->(r.getDni()).toLowerCase().contains(this.tfDni.getText().toLowerCase());
					try {
						reloadGrid( api.obtenerRecolectores(predicate));
					} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),0);
					}
			}
			
		});
		rdbtnDni.setBounds(272, 70, 21, 21);
		panelFiltrado.add(rdbtnDni);
		
		 rdbtnCorreo = new JRadioButton("");
		 rdbtnCorreo.addActionListener((e)->{
			 rdbtnCorreo.setSelected(false);
			 if(!this.tfCorreo.getText().equals("")) {
				 Predicate<RecolectorDTO> predicate = (RecolectorDTO r)->(r.getEmail()).toLowerCase().contains(this.tfCorreo.getText().toLowerCase());
				try {
					reloadGrid( api.obtenerRecolectores(predicate));
				} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),0);
				}
			 }
			 
		 });
		rdbtnCorreo.setBounds(272, 100, 21, 21);
		panelFiltrado.add(rdbtnCorreo);
		
		lbFiltrado = new JLabel(labels.getString("listado.de.personal.label.filtrar"));
		lbFiltrado.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrado.setBounds(128, 11, 175, 14);
		panelFiltrado.add(lbFiltrado);
		
		JButton btnLimpiarFiltrado = new JButton(labels.getString("listado.de.personal.button.limpiar.filtrado"));
		btnLimpiarFiltrado.addActionListener((e)->{
			try {
				this.tfCorreo.setText("");
				this.tfDni.setText("");
				this.tfNombreApellido.setText("");
				reloadGrid(api.obtenerRecolectores());
			} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
		});
		btnLimpiarFiltrado.setBounds(104, 161, 189, 23);
		panelFiltrado.add(btnLimpiarFiltrado);
		
		JPanel panelOrdenamiento = new JPanel();
		panelOrdenamiento.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelOrdenamiento.setBounds(713, 217, 424, 193);
		panel.add(panelOrdenamiento);
		panelOrdenamiento.setLayout(null);
		
		lbOrdenamiento = new JLabel(labels.getString("listado.de.personal.label.ordenamiento"));
		lbOrdenamiento.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenamiento.setBounds(103, 5, 229, 14);
		panelOrdenamiento.add(lbOrdenamiento);
		
		rdbtnNombreApellidoOrdenamiento = new JRadioButton(labels.getString("listado.de.personal.radio.button.nombre.y.apellido"));
		rdbtnNombreApellidoOrdenamiento.addActionListener((e)->{
			rdbtnNombreApellidoOrdenamiento.setSelected(false);
			try {
				Comparator<RecolectorDTO> comparator = (RecolectorDTO r1,RecolectorDTO r2)->
				(r1.getNombre()+ " "+ r1.getApellido()).compareToIgnoreCase((r2.getNombre() + " " + r2.getApellido()));
				reloadGrid( api.obtenerRecolectores(comparator));
			} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
			
		});
		rdbtnNombreApellidoOrdenamiento.setBounds(68, 26, 172, 20);
		panelOrdenamiento.add(rdbtnNombreApellidoOrdenamiento);
		
		rdbtnDniOrdenamiento = new JRadioButton(labels.getString("listado.de.personal.radio.button.dni"));
		rdbtnDniOrdenamiento.addActionListener((e)->{
			rdbtnDniOrdenamiento.setSelected(false);
			try {
				Comparator<RecolectorDTO> comparator = (RecolectorDTO r1,RecolectorDTO r2)->
				r1.getDni().compareToIgnoreCase(r2.getDni());
				reloadGrid( api.obtenerRecolectores(comparator));
			} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
		});
		rdbtnDniOrdenamiento.setBounds(223, 53, 153, 20);
		panelOrdenamiento.add(rdbtnDniOrdenamiento);
		
		rdbtnCorreoOrdenamiento = new JRadioButton(labels.getString("listado.de.personal.radio.button.correo"));
		rdbtnCorreoOrdenamiento.addActionListener((e)->{
			rdbtnCorreoOrdenamiento.setSelected(false);
			try {
				Comparator<RecolectorDTO> comparator = (RecolectorDTO r1,RecolectorDTO r2)->
				r1.getEmail().compareToIgnoreCase(r2.getEmail());
				reloadGrid( api.obtenerRecolectores(comparator));
			} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
		});
		rdbtnCorreoOrdenamiento.setBounds(68, 80, 172, 20);
		panelOrdenamiento.add(rdbtnCorreoOrdenamiento);
		
		JButton btnLimpiarOrdenamiento = new JButton(labels.getString("listado.de.personal.button.limpiar.ordenamiento"));
		btnLimpiarOrdenamiento.addActionListener((e)->{
			try {
				reloadGrid(api.obtenerRecolectores());
			} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}});
		btnLimpiarOrdenamiento.setBounds(103, 159, 189, 23);
		panelOrdenamiento.add(btnLimpiarOrdenamiento);
			
		
}
	private void reloadGrid(List<RecolectorDTO> recolectores) {
		this.modelo.setRowCount(0);
		for(RecolectorDTO r : recolectores) {
			modelo.addRow(new Object[] { 
					r.getNombre(),
					r.getApellido(),
					r.getTelefono(),
					r.getDni(),
					r.getEmail()
		});
		}
	}
}
