package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;




import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;

import ar.edu.unrn.seminario.dto.DueñoDTO;

import ar.edu.unrn.seminario.exceptions.AppException;

import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.utilities.Predicate;

import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import ar.edu.unrn.seminario.utilities.NotEditJTable;
public class ListadoDeDueños extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2452447123893203415L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JPanel panelFiltrado;
	private JPanel panelOrdenamiento;
	private JTextField tfFiltrarPorNombreCompleto;
	private JTextField tfFiltrarPorDni;
	private JTextField tfFiltrarPorCorreoElectronico;
	private JTextField tfFiltrarPorUsername;




	public ListadoDeDueños(IApi api, ResourceBundle labels) {
		
		setTitle(labels.getString("listado.de.dueños.titulo"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 971, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelListado = new JPanel();
		panelListado.setBounds(278, 11, 663, 401);
		contentPane.add(panelListado);
		panelListado.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		panelListado.add(scrollPane);
		table = new NotEditJTable();
		
		String[] titulos = { 
				labels.getString("listado.de.dueños.titulos.nombre.completo"),
				labels.getString("listado.de.dueños.titulos.dni"),
				labels.getString("listado.de.dueños.titulos.correo.electronico"),
				labels.getString("listado.de.dueños.titulos.username"),		
		};
		modelo = new DefaultTableModel(new Object[][] {}, titulos);
			try {
				reloadGrid(api.obtenerDueños());
			} catch (AppException | NotNumberException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);	
			}
		table.setModel(modelo);
			
		scrollPane.setViewportView(table);
		JButton btnVolverMenuPrincipal = new JButton(labels.getString("listado.de.dueños.button.volver.menu.principal")); 
		btnVolverMenuPrincipal.addActionListener((e)->{
			setVisible(false);
			dispose();
		});
		panelListado.add(btnVolverMenuPrincipal, BorderLayout.SOUTH);
		panelFiltrado = new JPanel();
		panelFiltrado.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelFiltrado.setBounds(10, 61, 258, 137);
		contentPane.add(panelFiltrado);
		panelFiltrado.setLayout(null);
		JLabel lbFiltrarPorNombreCompleto = new JLabel(labels.getString("listado.de.dueños.label.filtrar.ordenar.por.nombre.completo"));
		lbFiltrarPorNombreCompleto.setHorizontalAlignment(SwingConstants.TRAILING);
		lbFiltrarPorNombreCompleto.setBounds(0, 8, 120, 14);
		panelFiltrado.add(lbFiltrarPorNombreCompleto);
		
		tfFiltrarPorNombreCompleto = new JTextField();
		tfFiltrarPorNombreCompleto.setBounds(130, 5, 86, 20);
		panelFiltrado.add(tfFiltrarPorNombreCompleto);
		tfFiltrarPorNombreCompleto.setColumns(10);
		JRadioButton rdbtnNFiltrarPorNombreCompleto = new JRadioButton("");
		rdbtnNFiltrarPorNombreCompleto.addActionListener((e)->{
			rdbtnNFiltrarPorNombreCompleto.setSelected(false);
			if(!tfFiltrarPorNombreCompleto.getText().equals("")) {
				Predicate <DueñoDTO> predicate = (DueñoDTO d)->
				d.getNombre().toLowerCase().contains(tfFiltrarPorNombreCompleto.getText().toLowerCase())
				|| d.getApellido().toLowerCase().contains(tfFiltrarPorNombreCompleto.getText().toLowerCase());	
				try {
					reloadGrid(api.obtenerDueños(predicate));
				} catch (AppException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				
				}
			}
		});
		rdbtnNFiltrarPorNombreCompleto.setBounds(220, 5, 21, 21);
		panelFiltrado.add(rdbtnNFiltrarPorNombreCompleto);
		
		JLabel lbFiltrarPorDni = new JLabel(labels.getString("listado.de.dueños.label.filtrar.ordenar.por.dni"));
		lbFiltrarPorDni.setHorizontalAlignment(SwingConstants.TRAILING);
		lbFiltrarPorDni.setBounds(0, 34, 120, 14);
		panelFiltrado.add(lbFiltrarPorDni);
		
		tfFiltrarPorDni = new JTextField();
		tfFiltrarPorDni.setBounds(130, 31, 86, 20);
		panelFiltrado.add(tfFiltrarPorDni);
		tfFiltrarPorDni.setColumns(10);
		
		JRadioButton rdbtnFiltrarPorDni = new JRadioButton("");
		rdbtnFiltrarPorDni.addActionListener((e)->{
			rdbtnFiltrarPorDni.setSelected(false);
			if(!tfFiltrarPorDni.getText().equals("")) {
				Predicate <DueñoDTO> predicate = (DueñoDTO d)->d.getDni().toLowerCase().contains(tfFiltrarPorDni.getText().toLowerCase());
				try {
					reloadGrid(api.obtenerDueños(predicate));
				} catch (AppException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
			
				}
			}
		});
		rdbtnFiltrarPorDni.setBounds(220, 31, 21, 21);
		
		panelFiltrado.add(rdbtnFiltrarPorDni);
		
		JLabel lbFiltrarPorCorreoElectronico = new JLabel(labels.getString("listado.de.dueños.label.filtrar.ordenar.por.correo.electronico"));
		lbFiltrarPorCorreoElectronico.setHorizontalAlignment(SwingConstants.TRAILING);
		lbFiltrarPorCorreoElectronico.setBounds(0, 60, 120, 14);
		panelFiltrado.add(lbFiltrarPorCorreoElectronico);
		
		tfFiltrarPorCorreoElectronico = new JTextField();
		tfFiltrarPorCorreoElectronico.setBounds(130, 57, 86, 20);
		panelFiltrado.add(tfFiltrarPorCorreoElectronico);
		tfFiltrarPorCorreoElectronico.setColumns(10);
		
		JRadioButton rdbtnFiltrarPorCorreoElectronico = new JRadioButton("");
		rdbtnFiltrarPorCorreoElectronico.addActionListener((e)->{
			rdbtnFiltrarPorCorreoElectronico.setSelected(false);
			if(!tfFiltrarPorCorreoElectronico.getText().equals("")) {
				Predicate <DueñoDTO> predicate = (DueñoDTO d)->d.getCorreoElectronico().toLowerCase().contains(tfFiltrarPorCorreoElectronico.getText().toLowerCase());
				try {
					reloadGrid(api.obtenerDueños(predicate));
				} catch (AppException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		rdbtnFiltrarPorCorreoElectronico.setBounds(220, 57, 21, 21);
		panelFiltrado.add(rdbtnFiltrarPorCorreoElectronico);
		
		JLabel lbFiltrarPorUsername = new JLabel(labels.getString("listado.de.dueños.label.filtrar.ordenar.por.username"));
		lbFiltrarPorUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		lbFiltrarPorUsername.setBounds(0, 86, 120, 14);
		panelFiltrado.add(lbFiltrarPorUsername);
		
		tfFiltrarPorUsername = new JTextField();
		tfFiltrarPorUsername.setBounds(130, 83, 86, 20);
		panelFiltrado.add(tfFiltrarPorUsername);
		tfFiltrarPorUsername.setColumns(10);
		
		JRadioButton rdbtnFiltrarPorUsername = new JRadioButton("");
		rdbtnFiltrarPorUsername.addActionListener((e)->{
			rdbtnFiltrarPorUsername.setSelected(false);
			if(!tfFiltrarPorUsername.getText().equals("")) {
				Predicate <DueñoDTO> predicate = (DueñoDTO d)->d.getUser().getUsuario().toLowerCase().contains(tfFiltrarPorUsername.getText().toLowerCase());
				try {
					reloadGrid(api.obtenerDueños(predicate));
				} catch (AppException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		rdbtnFiltrarPorUsername.setBounds(220, 83, 21, 21);
		panelFiltrado.add(rdbtnFiltrarPorUsername);
		
		JButton btnLimpiarFiltro = new JButton(labels.getString("listado.de.dueños.button.limpiar.filtro")); 
		btnLimpiarFiltro.addActionListener((e)->{
			try {
				reloadGrid(api.obtenerDueños());
			} catch (AppException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
			}
		});
		btnLimpiarFiltro.setBounds(66, 111, 150, 23);
		panelFiltrado.add(btnLimpiarFiltro);
		panelOrdenamiento = new JPanel();
		panelOrdenamiento.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelOrdenamiento.setBounds(21, 247, 175, 145);
		contentPane.add(panelOrdenamiento);
		panelOrdenamiento.setLayout(null);
		
		JLabel lbOrdenarPorNombreCompleto = new JLabel(labels.getString("listado.de.dueños.label.filtrar.ordenar.por.nombre.completo"));
		lbOrdenarPorNombreCompleto.setHorizontalAlignment(SwingConstants.TRAILING);
		lbOrdenarPorNombreCompleto.setBounds(13, 10, 121, 14);
		panelOrdenamiento.add(lbOrdenarPorNombreCompleto);
		
		JRadioButton rdbtnOrdenarPorNombreCompleto = new JRadioButton("");
		rdbtnOrdenarPorNombreCompleto.addActionListener((e)->{
			rdbtnOrdenarPorNombreCompleto.setSelected(false);
			Comparator<DueñoDTO> comparator = (DueñoDTO d1, DueñoDTO d2)->
					(d1.getNombre() + " " + d1.getApellido()).compareToIgnoreCase((d2.getNombre()+" "+ d2.getApellido()));
					try {
						reloadGrid(api.obtenerDueños(comparator));
					} catch (AppException | NotNumberException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
						
					}
		});
		rdbtnOrdenarPorNombreCompleto.setBounds(140, 7, 21, 21);
		panelOrdenamiento.add(rdbtnOrdenarPorNombreCompleto);
		
		JLabel lbOrdenarPorUsername = new JLabel(labels.getString("listado.de.dueños.label.filtrar.ordenar.por.username"));
		lbOrdenarPorUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		lbOrdenarPorUsername.setBounds(13, 85, 121, 14);
		panelOrdenamiento.add(lbOrdenarPorUsername);
		
		JRadioButton rdbtnOrdenarPorUsername = new JRadioButton("");
		rdbtnOrdenarPorUsername.addActionListener((e)->{
			rdbtnOrdenarPorUsername.setSelected(false);
			Comparator<DueñoDTO> comparator = (DueñoDTO d1, DueñoDTO d2)->
					d1.getUser().getUsuario().compareToIgnoreCase(d2.getUser().getUsuario());
					try {
						reloadGrid(api.obtenerDueños(comparator));
					} catch (AppException | NotNumberException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
						
					}
		});
		rdbtnOrdenarPorUsername.setBounds(140, 82, 21, 21);
		panelOrdenamiento.add(rdbtnOrdenarPorUsername);
		
		JLabel lbOrdenarPorDni = new JLabel(labels.getString("listado.de.dueños.label.filtrar.ordenar.por.dni"));
		lbOrdenarPorDni.setHorizontalAlignment(SwingConstants.TRAILING);
		lbOrdenarPorDni.setBounds(13, 35, 121, 14);
		panelOrdenamiento.add(lbOrdenarPorDni);
		
		JRadioButton rdbtnOrdenarPorDni = new JRadioButton("");
		rdbtnOrdenarPorDni.addActionListener((e)->{
			rdbtnOrdenarPorDni.setSelected(false);
			Comparator<DueñoDTO> comparator = (DueñoDTO d1, DueñoDTO d2)->
					d1.getDni().compareToIgnoreCase(d2.getDni());
					try {
						reloadGrid(api.obtenerDueños(comparator));
					} catch (AppException | NotNumberException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
						
					}
		});
		rdbtnOrdenarPorDni.setBounds(140, 32, 21, 21);
		panelOrdenamiento.add(rdbtnOrdenarPorDni);
		
		JLabel lbOrdenarPorCorreoElectronico = new JLabel(labels.getString("listado.de.dueños.label.filtrar.ordenar.por.correo.electronico"));
		lbOrdenarPorCorreoElectronico.setHorizontalAlignment(SwingConstants.TRAILING);
		lbOrdenarPorCorreoElectronico.setBounds(13, 60, 121, 14);
		panelOrdenamiento.add(lbOrdenarPorCorreoElectronico);
		
		JRadioButton rdbtnOrdenarPorCorreoElectronico = new JRadioButton("");
		rdbtnOrdenarPorCorreoElectronico.addActionListener((e)->{
			rdbtnOrdenarPorCorreoElectronico.setSelected(false);
			
			Comparator<DueñoDTO> comparator = (DueñoDTO d1, DueñoDTO d2)->
					d1.getCorreoElectronico().compareToIgnoreCase(d2.getCorreoElectronico());
					
					try {
						
						reloadGrid(api.obtenerDueños(comparator));
					} catch (AppException | NotNumberException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
					}
		});
		rdbtnOrdenarPorCorreoElectronico.setBounds(140, 57, 21, 21);
		panelOrdenamiento.add(rdbtnOrdenarPorCorreoElectronico);
		
		JButton btnLimpiarFiltro_1 = new JButton(labels.getString("listado.de.dueños.button.limpiar.ordenamiento"));
		btnLimpiarFiltro_1.addActionListener((e)->{
			try {
				this.reloadGrid(api.obtenerDueños());
			} catch (AppException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
		});
		
		btnLimpiarFiltro_1.setBounds(13, 122, 150, 23);
		panelOrdenamiento.add(btnLimpiarFiltro_1);
		
		JLabel lbOrdenarPor = new JLabel(labels.getString("listado.de.dueños.label.ordenar.por"));
		lbOrdenarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenarPor.setBounds(21, 226, 175, 14);
		contentPane.add(lbOrdenarPor);
		
		JLabel lbFiltrarPor = new JLabel(labels.getString("listado.de.dueños.label.filtrar.por"));
		lbFiltrarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrarPor.setBounds(40, 38, 175, 14);
		contentPane.add(lbFiltrarPor);
		
	}
	private void reloadGrid(List<DueñoDTO> dueños) {
		modelo.setRowCount(0);
			for (DueñoDTO d : dueños) {
				modelo.addRow(new Object[] { 
				 	d.getNombre() + " " +d.getApellido(),
				 	d.getDni(),
				 	d.getCorreoElectronico(),
				 	d.getUser().getUsuario()
					
				});
	}
			
	}
}
