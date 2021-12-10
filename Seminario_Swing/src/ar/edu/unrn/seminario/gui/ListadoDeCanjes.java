package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CanjeDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.utilities.NotEditJTable;
import ar.edu.unrn.seminario.utilities.Predicate;

import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ListadoDeCanjes extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modelo;
	private JTable table;
	private JTextField tfFiltrarPorNombreCampaña;
	private JTextField tfFiltrarPorCodigoCampaña;
	private JTextField tfFiltrarDescripcionBeneficio;
	private JTextField tfFiltrarPorUsuarioAsociado;
	private JTextField tfFiltrarPorCodigoBeneficio;

	private String rolUsuarioActivo;
	private String [] titulos;
	private JLabel filtrarPorUsuarioAsociado ;
	private JRadioButton rdbtnFiltrarPorUsuarioAsociado;
	private JRadioButton rdbtnUsuarioAsociado;


	public ListadoDeCanjes(IApi api, ResourceBundle labels) {
		this.setTitle(labels.getString("listado.de.canjes.titulo"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		setBounds(100, 100, 1139, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);



		String []titulos = { 
				labels.getString("listado.de.canjes.titulo.nombre.campaña"),
				labels.getString("listado.de.canjes.titulo.codigo.campaña"), 
				labels.getString("listado.de.canjes.titulo.descripcion.beneficio"), 
				labels.getString("listado.de.canjes.titulo.codigo.beneficio"), 
				labels.getString("listado.de.canjes.titulo.dueño.asociado"),
				labels.getString("listado.de.canjes.titulo.dni.dueño") 	
		};
		String []titulos2 = { 
				labels.getString("listado.de.canjes.titulo2.nombre.campaña"),
				labels.getString("listado.de.canjes.titulo2.codigo.campaña"), 
				labels.getString("listado.de.canjes.titulo2.descripcion.beneficio"), 
				labels.getString("listado.de.canjes.titulo2.codigo.beneficio"), 
		};
			
			try {
				if(api.obtenerRolUsuarioActivo().equalsIgnoreCase("ADMIN")) {
					modelo = new DefaultTableModel(new Object[][] {},titulos);
					
					rolUsuarioActivo = "ADMINISTRADOR";
					this.reloadGridAdministrador(api.obtenerCanjesPorUsuario());
					this.titulos = titulos;
				}
				else {
					modelo = new DefaultTableModel(new Object[][] {},titulos2);
					this.titulos = titulos;
					rolUsuarioActivo = "COMUNIDAD";
					System.out.println("entro aca");
					this.reloadGrid(api.obtenerCanjesPorUsuario());
				}
			} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
			}
		JButton cerrarButton = new JButton(labels.getString("listado.de.canjes.boton.cerrar"));

		cerrarButton.addActionListener((e)->{
			setVisible(false);
			dispose();
		});
		contentPane.setLayout(null);

		JPanel pnlBotonesOperaciones = new JPanel();
		pnlBotonesOperaciones.setBounds(5, 322, 841, 37);
		pnlBotonesOperaciones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(pnlBotonesOperaciones);
		pnlBotonesOperaciones.setLayout(new BorderLayout(0, 0));
	
		pnlBotonesOperaciones.add(cerrarButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 835, 317);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		table = new NotEditJTable();
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		JPanel panel_Filtrados = new JPanel();
		panel_Filtrados.setBounds(848, 5, 265, 203);
		contentPane.add(panel_Filtrados);
		panel_Filtrados.setLayout(null);
		
		JLabel lbFiltrarPor = new JLabel(labels.getString("listado.de.canjes.label.filtrar.por"));
		lbFiltrarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrarPor.setBounds(69, 11, 110, 14);
		panel_Filtrados.add(lbFiltrarPor);
		
		JLabel filtrarPorNombreCampaña = new JLabel(labels.getString("listado.de.canjes.label.nombre.de.campaña"));
		filtrarPorNombreCampaña.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorNombreCampaña.setBounds(0, 39, 139, 14);
		panel_Filtrados.add(filtrarPorNombreCampaña);
		
		tfFiltrarPorNombreCampaña = new JTextField();
		tfFiltrarPorNombreCampaña.setBounds(149, 36, 86, 20);
		panel_Filtrados.add(tfFiltrarPorNombreCampaña);
		tfFiltrarPorNombreCampaña.setColumns(10);
		
		JRadioButton rdbtnFiltrarPorNombreCampaña = new JRadioButton("");
		rdbtnFiltrarPorNombreCampaña.addActionListener((e)->{
			rdbtnFiltrarPorNombreCampaña.setSelected(false);
			if(!this.tfFiltrarPorNombreCampaña.getText().equals("")) {
				Predicate <CanjeDTO> predicate = (CanjeDTO c)->
				c.getCampaña().getNombreCampaña().toLowerCase().contains(this.tfFiltrarPorNombreCampaña.getText().toLowerCase());
				try {
					
					if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
						this.reloadGrid(api.obtenerCanjesDeUsuario(predicate));
					}
					else {
						this.reloadGridAdministrador(api.obtenerCanjes(predicate));
					}
					
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		rdbtnFiltrarPorNombreCampaña.setBounds(238, 36, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorNombreCampaña);
		
		JLabel filtrarPorCodigoCampaña = new JLabel(labels.getString("listado.de.canjes.label.codigo.de.campaña"));

		filtrarPorCodigoCampaña.setHorizontalAlignment(SwingConstants.CENTER);
		
		filtrarPorCodigoCampaña.setBounds(0, 64, 139, 14);
		panel_Filtrados.add(filtrarPorCodigoCampaña);
		
		JLabel filtrarPorDescripcionBeneficio = new JLabel(labels.getString("listado.de.canjes.label.descripcion.del.beneficio"));
		filtrarPorDescripcionBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorDescripcionBeneficio.setBounds(0, 89, 139, 14);
		panel_Filtrados.add(filtrarPorDescripcionBeneficio);
		
		JLabel filtrarPorCodigoBeneficio = new JLabel(labels.getString("listado.de.canjes.label.codigo.del.beneficio"));
		filtrarPorCodigoBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorCodigoBeneficio.setBounds(0, 114, 139, 14);
		panel_Filtrados.add(filtrarPorCodigoBeneficio);
		
		filtrarPorUsuarioAsociado = new JLabel(labels.getString("listado.de.canjes.label.usuario.asociado"));
		filtrarPorUsuarioAsociado.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorUsuarioAsociado.setBounds(0, 139, 139, 14);
		panel_Filtrados.add(filtrarPorUsuarioAsociado);
		
		tfFiltrarPorCodigoCampaña = new JTextField();
		tfFiltrarPorCodigoCampaña.setColumns(10);
		tfFiltrarPorCodigoCampaña.setBounds(149, 61, 86, 20);
		panel_Filtrados.add(tfFiltrarPorCodigoCampaña);
		
		tfFiltrarDescripcionBeneficio = new JTextField();
		tfFiltrarDescripcionBeneficio.setColumns(10);
		tfFiltrarDescripcionBeneficio.setBounds(149, 86, 86, 20);
		panel_Filtrados.add(tfFiltrarDescripcionBeneficio);
		
		tfFiltrarPorUsuarioAsociado = new JTextField();
		tfFiltrarPorUsuarioAsociado.setColumns(10);
		tfFiltrarPorUsuarioAsociado.setBounds(149, 136, 86, 20);
		panel_Filtrados.add(tfFiltrarPorUsuarioAsociado);
		
		tfFiltrarPorCodigoBeneficio = new JTextField();
		tfFiltrarPorCodigoBeneficio.setColumns(10);
		tfFiltrarPorCodigoBeneficio.setBounds(149, 111, 86, 20);
		panel_Filtrados.add(tfFiltrarPorCodigoBeneficio);
		
		JRadioButton rdbtnFiltrarPorCodigoCampaña = new JRadioButton("");
		rdbtnFiltrarPorCodigoCampaña.addActionListener((e)->{
			rdbtnFiltrarPorCodigoCampaña.setSelected(false);
			if(!this.tfFiltrarPorCodigoCampaña.getText().equals("")) {
				Predicate <CanjeDTO> predicate = (CanjeDTO c)->
				String.valueOf( c.getCampaña().getCodigo()).contains(this.tfFiltrarPorCodigoCampaña.getText());
				try {
					if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
						this.reloadGrid(api.obtenerCanjesDeUsuario(predicate));
					}
					else {
						this.reloadGridAdministrador(api.obtenerCanjes(predicate));
					}

				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		rdbtnFiltrarPorCodigoCampaña.setBounds(238, 60, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorCodigoCampaña);
		
		JRadioButton rdbtnFiltrarPorDescripcionBeneficio = new JRadioButton("");
		rdbtnFiltrarPorDescripcionBeneficio.addActionListener((e)->{
			rdbtnFiltrarPorDescripcionBeneficio.setSelected(false);
			if(!this.tfFiltrarDescripcionBeneficio.getText().equals("")) {
				Predicate <CanjeDTO> predicate = (CanjeDTO c)->
				c.getBeneficioCanjeado().getDescripcion().toLowerCase().contains(this.tfFiltrarDescripcionBeneficio.getText().toLowerCase());
				
				try {
					
					if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
						this.reloadGrid(api.obtenerCanjesDeUsuario(predicate));
						
					}
					else {
						reloadGridAdministrador(api.obtenerCanjes(predicate));
						
					}
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		rdbtnFiltrarPorDescripcionBeneficio.setBounds(238, 85, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorDescripcionBeneficio);
		
		JRadioButton rdbtnFiltrarPorCodigoBeneficio = new JRadioButton("");
		rdbtnFiltrarPorCodigoBeneficio.addActionListener((e)->{
			rdbtnFiltrarPorCodigoBeneficio.setSelected(false);
			if(!this.tfFiltrarPorCodigoBeneficio.getText().equals("")) {
				Predicate <CanjeDTO> predicate = (CanjeDTO c)->
			String.valueOf(	c.getBeneficioCanjeado().getCodigo()).contains(this.tfFiltrarPorCodigoBeneficio.getText());

				try {
					
					if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
						this.reloadGrid(api.obtenerCanjesDeUsuario(predicate));
						
					}
					else {
						reloadGridAdministrador(api.obtenerCanjes(predicate));
						
					}
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		rdbtnFiltrarPorCodigoBeneficio.setBounds(238, 110, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorCodigoBeneficio);
		
		rdbtnFiltrarPorUsuarioAsociado = new JRadioButton("");
		rdbtnFiltrarPorUsuarioAsociado.addActionListener((e)->{
			rdbtnFiltrarPorUsuarioAsociado.setSelected(false);

			if(!this.tfFiltrarPorUsuarioAsociado.getText().equals("")) {
				Predicate <CanjeDTO> predicate = (CanjeDTO c)->
			String.valueOf(c.getDueñoCanjeador().getNombre() + " "+ c.getDueñoCanjeador().getApellido()).toLowerCase().contains(this.tfFiltrarPorUsuarioAsociado.getText().toLowerCase());
				try {
					
					if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
						this.reloadGrid(api.obtenerCanjesDeUsuario(predicate));
					}
					else {
						reloadGridAdministrador(api.obtenerCanjes(predicate));
						
					}
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		rdbtnFiltrarPorUsuarioAsociado.setBounds(238, 135, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorUsuarioAsociado);
		
		JButton btnLimpiarFiltrado = new JButton(labels.getString("listado.de.canjes.button.limpiar.filtro"));


		btnLimpiarFiltrado.addActionListener((e)->{
			try {
				if(this.rolUsuarioActivo.equals("COMUNIDAD")) {
					
						reloadGrid(api.obtenerCanjesPorUsuario());
					
				}
				else {
					reloadGridAdministrador(api.obtenerCanjes());
				}
			}
			catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
		});
		
		btnLimpiarFiltrado.setBounds(44, 169, 191, 23);
		panel_Filtrados.add(btnLimpiarFiltrado);
		
		JPanel panel_Ordenamiento = new JPanel();
		panel_Ordenamiento.setBounds(848, 210, 265, 178);
		contentPane.add(panel_Ordenamiento);
		panel_Ordenamiento.setLayout(null);
		
		JLabel lbOrdenarPor = new JLabel(labels.getString("listado.de.canjes.label.ordenar.por"));
		lbOrdenarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenarPor.setBounds(70, 0, 119, 14);
		panel_Ordenamiento.add(lbOrdenarPor);
		
		JRadioButton rdbtnOrdenarNombreCampaña = new JRadioButton(labels.getString("listado.de.canjes.label.nombre.de.campaña"));
		rdbtnOrdenarNombreCampaña.addActionListener((e)->{
			rdbtnOrdenarNombreCampaña.setSelected(false);
			
				try {
					Comparator <CanjeDTO> comparator = (CanjeDTO c1, CanjeDTO c2)->c1.getCampaña().getNombreCampaña().compareToIgnoreCase(c2.getCampaña().getNombreCampaña());
					if(this.rolUsuarioActivo.equals("COMUNIDAD")) {
						reloadGrid(api.obtenerCanjesPorUsuario(comparator));
					}
					else {
						reloadGridAdministrador(api.obtenerCanjes(comparator));
					}
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"error",0);
				}
			
		});
		rdbtnOrdenarNombreCampaña.setBounds(24, 21, 165, 25);
		panel_Ordenamiento.add(rdbtnOrdenarNombreCampaña);
		
		JRadioButton rdbtnOrdenarCodigoCampaña = new JRadioButton(labels.getString("listado.de.canjes.label.codigo.de.campaña"));
		rdbtnOrdenarCodigoCampaña.addActionListener((e)->{
			rdbtnOrdenarCodigoCampaña.setSelected(false);
			try {
				Comparator <CanjeDTO> comparator = (CanjeDTO c1, CanjeDTO c2)->
				String.valueOf( c1.getCampaña().getCodigo()).compareToIgnoreCase(String.valueOf( c2.getCampaña().getCodigo()));
				if(this.rolUsuarioActivo.equals("COMUNIDAD")) {
						reloadGrid(api.obtenerCanjesPorUsuario(comparator));
				}
				else {
				reloadGridAdministrador(api.obtenerCanjes(comparator));
				}
			}catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
		});
		rdbtnOrdenarCodigoCampaña.setBounds(111, 49, 148, 23);
		panel_Ordenamiento.add(rdbtnOrdenarCodigoCampaña);
		
		JRadioButton rdbtnOrdenarDescripcionBeneficio = new JRadioButton(labels.getString("listado.de.canjes.label.descripcion.del.beneficio"));
		rdbtnOrdenarDescripcionBeneficio.addActionListener((e)->{
			rdbtnOrdenarDescripcionBeneficio.setSelected(false);
			try {
				Comparator <CanjeDTO> comparator = (CanjeDTO c1, CanjeDTO c2)->
				 c1.getBeneficioCanjeado().getDescripcion().compareToIgnoreCase(c2.getBeneficioCanjeado().getDescripcion());
				if(this.rolUsuarioActivo.equals("COMUNIDAD")) {
						reloadGrid(api.obtenerCanjesPorUsuario(comparator));
				}
				else {
					reloadGridAdministrador(api.obtenerCanjes(comparator));
				}
			}catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",1);
			}
		});
		rdbtnOrdenarDescripcionBeneficio.setBounds(24, 75, 184, 23);
		panel_Ordenamiento.add(rdbtnOrdenarDescripcionBeneficio);
		
		JRadioButton rdbtnOrdenarCodigoBeneficio = new JRadioButton(labels.getString("listado.de.canjes.label.codigo.del.beneficio"));
		rdbtnOrdenarCodigoBeneficio.addActionListener((e)->{
			rdbtnOrdenarCodigoBeneficio.setSelected(false);
			try {
				Comparator <CanjeDTO> comparator = (CanjeDTO c1, CanjeDTO c2)->
				String.valueOf(c1.getBeneficioCanjeado().getCodigo()).compareToIgnoreCase(String.valueOf(c2.getBeneficioCanjeado().getCodigo()));
				if(this.rolUsuarioActivo.equals("COMUNIDAD")) {
						reloadGrid(api.obtenerCanjesPorUsuario(comparator));
				}
				else {
					reloadGridAdministrador(api.obtenerCanjes(comparator));
				}
			}catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
		});
		rdbtnOrdenarCodigoBeneficio.setBounds(111, 101, 148, 23);
		panel_Ordenamiento.add(rdbtnOrdenarCodigoBeneficio);
		
		rdbtnUsuarioAsociado = new JRadioButton(labels.getString("listado.de.canjes.label.usuario.asociado"));
		rdbtnUsuarioAsociado.addActionListener((e)->{
			try {
				Comparator <CanjeDTO> comparator = (CanjeDTO c1, CanjeDTO c2)->
				(c2.getDueñoCanjeador().getNombre() + " " + c2.getDueñoCanjeador().getApellido()).compareToIgnoreCase(c2.getDueñoCanjeador().getNombre() + " " + c2.getDueñoCanjeador().getApellido());
				if(this.rolUsuarioActivo.equals("COMUNIDAD")) {
						reloadGrid(api.obtenerCanjesPorUsuario(comparator));
				}
				else {
					reloadGridAdministrador(api.obtenerCanjes(comparator));
				}
			}catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
			rdbtnUsuarioAsociado.setSelected(false);
		});
		
		rdbtnUsuarioAsociado.setBounds(24, 118, 154, 25);
		panel_Ordenamiento.add(rdbtnUsuarioAsociado);
		
		JButton btnLimpiarOrdenamiento = new JButton(labels.getString("listado.de.canjes.button.limpiar.ordenamiento"));
		btnLimpiarOrdenamiento.addActionListener((e)->{
			try {
				if(this.rolUsuarioActivo.equals("COMUNIDAD")) {
					
						reloadGrid(api.obtenerCanjesPorUsuario());
				}
				else {
					reloadGridAdministrador(api.obtenerCanjes());
				}
			}
			catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),0);
			}
		});
		btnLimpiarOrdenamiento.setBounds(40, 144, 195, 23);
		panel_Ordenamiento.add(btnLimpiarOrdenamiento);
		if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
			this.filtrarPorUsuarioAsociado.setVisible(false);
			this.rdbtnFiltrarPorUsuarioAsociado.setVisible(false);
			tfFiltrarPorUsuarioAsociado.setVisible(false);
			this.rdbtnUsuarioAsociado.setVisible(false);
			
		}
	}
	
	private void reloadGrid(List <CanjeDTO> canje) {
		
		modelo.setRowCount(0);


			for (CanjeDTO c : canje) {
				modelo.addRow(new Object[] { 
							c.getCampaña().getNombreCampaña(),
							c.getCampaña().getCodigo(),
							c.getBeneficioCanjeado().getDescripcion(),
							c.getBeneficioCanjeado().getCodigo(),
				});
			}
		
		

	}
	private void reloadGridAdministrador(List<CanjeDTO> canje) {
		modelo.setRowCount(0);
		for (CanjeDTO c : canje) {
			modelo.addRow(new Object[] { 
						c.getCampaña().getNombreCampaña(),
						c.getCampaña().getCodigo(),
						c.getBeneficioCanjeado().getDescripcion(),
						c.getBeneficioCanjeado().getCodigo(),
						c.getDueñoCanjeador().getNombre()+" "+ c.getDueñoCanjeador().getApellido(),
						c.getDueñoCanjeador().getDni(),
			});
		}
	}
}
