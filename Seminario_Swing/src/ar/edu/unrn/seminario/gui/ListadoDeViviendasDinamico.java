package ar.edu.unrn.seminario.gui;
import java.awt.BorderLayout;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.utilities.NotEditJTable;
import ar.edu.unrn.seminario.utilities.OrderingPredicate;
import ar.edu.unrn.seminario.utilities.Predicate;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
@SuppressWarnings("serial")
public class ListadoDeViviendasDinamico extends JFrame {
	private 
	IApi api;
	private JTable table;
	private DefaultTableModel modelo;

	private JPanel contentPane;
	private JPanel panel;
	private JPanel panelOrdenamiento;
	private JButton btnSalir;
	private JRadioButton rdbtnDni = new JRadioButton("");
	private JButton btnOrdenarPorBarrio;
	private JButton btnOrdenarPorCodPostal;
	private JButton btnOrdenarPorCodigo;
	private JButton btnOrdenarPorNombreYApellido= new JButton();
	private JLabel lbOrdenarPor;
	private JPanel panelFiltrado;
	private JLabel lbFiltrarPor;
	private JRadioButton rdbtnCodigo = new JRadioButton("");
	private JLabel lbCodigo = new JLabel();
	private JButton btnLimpiarFiltro = new JButton();
	private JTextField tx_barrio_calle ;
	private JLabel lb_barrio_calle;
	private JLabel lbNombre_Apellido = new JLabel();
	private JTextField txNombre_Apellido = new JTextField() ;
	private JLabel lbDni = new JLabel();
	private JTextField txDni = new JTextField();
	private JTextField txCodigo = new JTextField();;
	private ResourceBundle labels = ResourceBundle.getBundle("labels");
	private Comparator <ViviendaDTO> comparator;
	private Predicate <ViviendaDTO> predicate;
	private List<ViviendaDTO> viviendas = null;
	private JRadioButton rdbtnNombre_apellido = new JRadioButton();
	public ListadoDeViviendasDinamico(IApi api, ResourceBundle labels){
		this.api=api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1151, 523);
		contentPane = new JPanel();
		panel = new JPanel();
		panel.setBounds(0, 5, 840, 473);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		contentPane.add(panel);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		setTitle(labels.getString("listado.de.viviendas.titulo"));
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		table = new NotEditJTable();
		String[] titulos = { 
				labels.getString("listado.de.viviendas.titulos.vivienda.barrio"),
				labels.getString("listado.de.viviendas.titulos.vivienda.calle"),
				labels.getString("listado.de.viviendas.titulos.vivienda.altura"),
				labels.getString("listado.de.viviendas.titulos.vivienda.codigo.postal"),
				labels.getString("listado.de.viviendas.titulos.vivienda.latitud"),
				labels.getString("listado.de.viviendas.titulos.vivienda.longitud"),
				labels.getString("listado.de.viviendas.titulos.vivienda.nombre"),
				labels.getString("listado.de.viviendas.titulos.vivienda.apellido"),
				labels.getString("listado.de.viviendas.titulos.vivienda.dni"),
				labels.getString("listado.de.viviendas.titulos.vivienda.correo.electronico"),
				labels.getString("listado.de.viviendas.titulos.vivienda.codigo"),
				
		};
		String[] titulos2 = { 
				labels.getString("listado.de.viviendas.titulos.vivienda.barrio"),
				labels.getString("listado.de.viviendas.titulos.vivienda.calle"),
				labels.getString("listado.de.viviendas.titulos.vivienda.altura"),
				labels.getString("listado.de.viviendas.titulos.vivienda.codigo.postal"),
				labels.getString("listado.de.viviendas.titulos.vivienda.latitud"),
				labels.getString("listado.de.viviendas.titulos.vivienda.longitud"),
		};
		
			
		// Agrega los usuarios en el model
		
		
		

		scrollPane.setViewportView(table);
		try {
			if(api.obtenerRolUsuarioActivo().equals("ADMIN")) {
					modelo = new DefaultTableModel(new Object[][] {}, titulos);		
					btnLimpiarFiltro.setBounds(73, 174, 124, 23);
					reloadGridAdmin( api.obtenerViviendas());
			}
			if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
				modelo = new DefaultTableModel(new Object[][] {}, titulos2);	
				System.out.println("entro a comunidad");
				this.btnOrdenarPorNombreYApellido.setVisible(false);
				this.lbNombre_Apellido.setVisible(false);
				this.lbDni.setVisible(false);
				this.txDni.setVisible(false);
				this.txNombre_Apellido.setVisible(false);
				this.rdbtnDni.setVisible(false);
				this.rdbtnNombre_apellido.setVisible(false);
				this.txCodigo.setVisible(false);
				this.lbCodigo.setVisible(false);
				this.rdbtnCodigo.setVisible(false);
				reloadGridDueño( api.obtenerViviendasDeUsuario());
				btnLimpiarFiltro.setBounds(73, 70, 124, 23);
			}
			}catch(AppException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		table.setModel(modelo);
		btnSalir = new JButton(labels.getString("listado.de.viviendas.button.salir"));
		btnSalir.addActionListener((e)->{
			setVisible(false);
			dispose();
			
		});
		panel.add(btnSalir, BorderLayout.SOUTH);
		
		panelOrdenamiento = new JPanel();
		panelOrdenamiento.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, null));
		panelOrdenamiento.setBounds(850, 5, 221, 161);
		contentPane.add(panelOrdenamiento);
		panelOrdenamiento.setLayout(null);
		
		btnOrdenarPorBarrio = new JButton(labels.getString("listado.de.viviendas.button.ordenar.por.barrio"));
		btnOrdenarPorBarrio.setBounds(46, 35, 141, 23);
		btnOrdenarPorBarrio.addActionListener((e)->{
			Comparator <ViviendaDTO> comparator = (ViviendaDTO v1, ViviendaDTO v2)->
			(v1.getDireccion().getBarrio().compareToIgnoreCase(v2.getDireccion().getBarrio()));
			try {
				if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
					this.reloadGridDueño(api.obtenerViviendasDeUsuario(comparator));
					//api.obtenerViviendasDeUsuario();
				
				}
				else {
					this.reloadGridAdmin(api.obtenerViviendas(comparator));
				}
				
				
			} catch (AppException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
		});
		
		panelOrdenamiento.add(btnOrdenarPorBarrio);
		
		btnOrdenarPorCodPostal = new JButton(labels.getString("listado.de.viviendas.button.ordenar.por.codigo.postal"));
		btnOrdenarPorCodPostal.setBounds(25, 63, 183, 23);
		btnOrdenarPorCodPostal.addActionListener((e)->{
			comparator = (ViviendaDTO v1, ViviendaDTO v2)->
			(v1.getDireccion().getCodPostal().compareToIgnoreCase(v2.getDireccion().getCodPostal()));
			
			try {
				if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
					reloadGridDueño(api.obtenerViviendasDeUsuario(comparator));

				
				}
				else {
					reloadGridAdmin(api.obtenerViviendas(comparator));
				}
			} catch (AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
			
			

			
			
		});
		panelOrdenamiento.add(btnOrdenarPorCodPostal);
		
		btnOrdenarPorCodigo = new JButton(labels.getString("listado.de.viviendas.button.ordenar.por.codigo"));
		btnOrdenarPorCodigo.setBounds(45, 91, 143, 23);
		btnOrdenarPorCodigo.addActionListener((e)->{
			comparator = (ViviendaDTO v1, ViviendaDTO v2)->
			(String.valueOf(v1.getID()).compareToIgnoreCase(String.valueOf(v2.getID())));
			try {
				if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
					this.reloadGridDueño(api.obtenerViviendasDeUsuario(comparator));
					//api.obtenerViviendasDeUsuario();
				
				}
				else {
					this.reloadGridAdmin(api.obtenerViviendas(comparator));
				}
				
				
			} catch (AppException e1) {
				
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

			}

			
		});
		panelOrdenamiento.add(btnOrdenarPorCodigo);
		
		
		btnOrdenarPorNombreYApellido.setText( labels.getString("listado.de.viviendas.button.ordenar.por.nombre.y.apellido"));
		btnOrdenarPorNombreYApellido.setBounds(15, 119, 203, 23);
		btnOrdenarPorNombreYApellido.addActionListener((e)->{
			
			comparator = (ViviendaDTO v1, ViviendaDTO v2)->
			(  v1.getDueño().getNombre().compareToIgnoreCase(v2.getDueño().getNombre()) +
			 v1.getDueño().getApellido().compareToIgnoreCase(v2.getDueño().getApellido())

			);
			
			try {
				if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
					this.reloadGridDueño(api.obtenerViviendasDeUsuario(comparator));
					//api.obtenerViviendasDeUsuario();
				
				}
				else {
					this.reloadGridAdmin(api.obtenerViviendas(comparator));
				}
				
				
			} catch (AppException e1) {
				
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

			}
			
		});
		panelOrdenamiento.add(btnOrdenarPorNombreYApellido);
		
		lbOrdenarPor = new JLabel(labels.getString("listado.de.viviendas.button.ordenar.por") );
		lbOrdenarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenarPor.setBounds(46, 11, 141, 14);
		panelOrdenamiento.add(lbOrdenarPor);
		
		panelFiltrado = new JPanel();
		panelFiltrado.setBounds(850, 202, 266, 222);
		contentPane.add(panelFiltrado);
		panelFiltrado.setLayout(null);
		
		lbFiltrarPor = new JLabel(labels.getString("listado.de.viviendas.button.filtrar.por"));
		lbFiltrarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrarPor.setBounds(87, 5, 124, 14);
		panelFiltrado.add(lbFiltrarPor);

		rdbtnNombre_apellido.setBounds(228, 131, 21, 14);
		panelFiltrado.add(rdbtnNombre_apellido);
		rdbtnNombre_apellido .addActionListener((e)->{
			if(rdbtnNombre_apellido.isSelected()) {

				try {
					if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
						this.reloadGridDueño(api.obtenerViviendasDeUsuario(predicate));
						//api.obtenerViviendasDeUsuario();
					
					}
					else {
						this.reloadGridAdmin(api.obtenerViviendas(predicate));
					}
					
					
				} catch (AppException e1) {
					
					JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

				}
					rdbtnNombre_apellido.setSelected(false);	
		}
			
		});
		tx_barrio_calle = new JTextField();
		tx_barrio_calle.setBounds(108, 28, 114, 20);
		panelFiltrado.add(tx_barrio_calle);
		tx_barrio_calle.setColumns(10);
		
		JRadioButton rdbtn_barrio_calle = new JRadioButton("");

		rdbtn_barrio_calle.addActionListener((e)->{
			if(rdbtn_barrio_calle.isSelected()) {
				 predicate =
						(ViviendaDTO v)->v.getDireccion().getBarrio().toLowerCase().contains(this.tx_barrio_calle.getText().toLowerCase())
						||v.getDireccion().getCalle().toLowerCase().contains(this.tx_barrio_calle.getText().toLowerCase());
						try {
							if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
								this.reloadGridDueño(api.obtenerViviendasDeUsuario(predicate));
								//api.obtenerViviendasDeUsuario();
							}
							else {
								this.reloadGridAdmin(api.obtenerViviendas(predicate));
							}

						} catch (AppException e1) {
							
							JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

						}
						rdbtn_barrio_calle.setSelected(false);			
		}
		});
		rdbtn_barrio_calle.setBounds(228, 28, 25, 23);
		panelFiltrado.add(rdbtn_barrio_calle);
		

		
		lb_barrio_calle = new JLabel(labels.getString("listado.de.viviendas.label.calle.y.altura"));
		lb_barrio_calle.setHorizontalAlignment(SwingConstants.CENTER);
		lb_barrio_calle.setBounds(0, 34, 101, 14);
		panelFiltrado.add(lb_barrio_calle);
		
		lbNombre_Apellido.setText(labels.getString("listado.de.viviendas.label.nombre.y.apellido"));
		lbNombre_Apellido.setHorizontalAlignment(SwingConstants.CENTER);
		lbNombre_Apellido.setBounds(0, 134, 119, 14);
		panelFiltrado.add(lbNombre_Apellido);		
		txNombre_Apellido.setBounds(109, 131, 113, 20);
		panelFiltrado.add(txNombre_Apellido);
		txNombre_Apellido.setColumns(10);
		
		lbDni.setText(labels.getString("listado.de.viviendas.label.dni"));
		lbDni.setHorizontalAlignment(SwingConstants.CENTER);
		lbDni.setBounds(10, 106, 77, 14);
		panelFiltrado.add(lbDni);
		
		txDni.setBounds(110, 103, 114, 20);
		panelFiltrado.add(txDni);
		txDni.setColumns(10);
		
		rdbtnDni.addActionListener((e)->{
			if(rdbtnDni.isSelected()) {
				 predicate = (ViviendaDTO v)->v.getDueño().getDni().contains(txDni.getText());
			
				 try {
						if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
							this.reloadGridDueño(api.obtenerViviendasDeUsuario(predicate));
							//api.obtenerViviendasDeUsuario();
						
						}
						else {
							this.reloadGridAdmin(api.obtenerViviendas(predicate));
						}
						
						
					} catch (AppException e1) {
						
						JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

					}
				rdbtnDni.setSelected(false);
		}
		});
		rdbtnDni.setBounds(228, 102, 25, 23);
		panelFiltrado.add(rdbtnDni);
		

		rdbtnCodigo.addActionListener((E)->{
		if(rdbtnCodigo.isSelected()) {
			Predicate <ViviendaDTO> predicate = (ViviendaDTO v)-> String.valueOf(v.getID()).contains(this.txCodigo.getText());
			try {
				if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
					this.reloadGridDueño(api.obtenerViviendasDeUsuario(predicate));
					//api.obtenerViviendasDeUsuario();
				
				}
				else {
					this.reloadGridAdmin(api.obtenerViviendas(predicate));
				}
				
				
			} catch (AppException e1) {
				
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

			}
		}
		});
		rdbtnCodigo.setBounds(228, 70, 25, 23);
		panelFiltrado.add(rdbtnCodigo);
		

		txCodigo.setColumns(10);
		txCodigo.setBounds(108, 70, 114, 20);
		panelFiltrado.add(txCodigo);
		
		//lbCodigo = new JLabel(labels.getString("listado.de.viviendas.label.codigo"));
		lbCodigo.setText(  labels.getString("listado.de.viviendas.label.codigo"));
		lbCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lbCodigo.setBounds(0, 70, 101, 14);
		panelFiltrado.add(lbCodigo);
		
		btnLimpiarFiltro.setText(labels.getString("listado.de.viviendas.button.filtrado.limpiar"));
		btnLimpiarFiltro.addActionListener((e)->{
			try {
				if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
					this.reloadGridDueño(api.obtenerViviendasDeUsuario());
					//api.obtenerViviendasDeUsuario();
				
				}
				else {
					this.reloadGridAdmin(api.obtenerViviendas());
				}
				
				
			} catch (AppException e1) {
				
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

			}
			
			
		});
				panelFiltrado.add(btnLimpiarFiltro);
		
	}
	private void reloadGridAdmin(List<ViviendaDTO> viviendas){
		modelo.setRowCount(0);
		// Agrega los usuarios en el model

			for (ViviendaDTO v : viviendas) {
				modelo.addRow(new Object[] { v.getDireccion().getBarrio(),
								v.getDireccion().getCalle(),
								v.getDireccion().getAltura(),
								v.getDireccion().getCodPostal(),
								v.getDireccion().getLatitud(),
								v.getDireccion().getLongitud(),
								v.getDueño().getNombre(),
								v.getDueño().getApellido(),
								v.getDueño().getDni(),
								v.getDueño().getCorreo(),
								v.getID()
								});
			}
	}
	private void reloadGridDueño(List<ViviendaDTO> viviendas) {
		modelo.setRowCount(0);
		// Agrega los usuarios en el model

			for (ViviendaDTO v : viviendas) {
				modelo.addRow(new Object[] { 
								v.getDireccion().getBarrio(),
								v.getDireccion().getCalle(),
								v.getDireccion().getAltura(),
								v.getDireccion().getCodPostal(),
								v.getDireccion().getLatitud(),
								v.getDireccion().getLongitud(),
								});
			}
			
			
	}

}
