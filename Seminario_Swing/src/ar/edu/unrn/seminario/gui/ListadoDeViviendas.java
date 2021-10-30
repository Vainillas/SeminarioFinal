package ar.edu.unrn.seminario.gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import utilities.Predicate;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
public class ListadoDeViviendas extends JFrame {
	IApi api;
	private JTable table;
	DefaultTableModel modelo;

	private JPanel contentPane;
	private JPanel panel;
	private JPanel panelOrdenamiento;
	private JButton btnSalir;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnOrdenarPorBarrio;
	private JButton btnOrdenarPorCodPostal;
	private JButton btnOrdenarPorCodigo;
	private JButton btnOrdenarPorNombreYApellido;
	private JLabel lbOrdenarPor;
	private JPanel panelFiltrado;
	private JLabel lbFiltrarPor;
	private JButton btnCalle_Altura;
	private JTextField txCalle_Altura ;
	private JLabel lbCalle_Altura;
	private JLabel lbNombre_Apellido;
	private JTextField txNombre_Apellido ;
	private JLabel lbDni;
	private JTextField txDni;
	private JButton btnDni;
	private JButton btnCodigo;
	private JTextField txCodigo;
	private JLabel lbCodigo;
	private JButton btnLimpiarFiltro;


	public ListadoDeViviendas(IApi api){
		this.api=api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1018, 523);
		contentPane = new JPanel();
		panel = new JPanel();
		panel.setBounds(0, 5, 724, 473);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		contentPane.add(panel);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		table = new JTable();
		String[] titulos = { 
				"BARRIO",
				"CALLE",
				"ALTURA",
				"CODIGO POSTAL",
				"LATITUD",
				"LONGITUD",
				"NOMBRE",
				"APELLIDO",
				"DNI",
				"CORREO ELECTRONICO",
				"CODIGO"
		};
		
		
		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar
		List<ViviendaDTO> viviendas;
		try {
			viviendas = api.obtenerViviendas();
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
			
		} catch (AppException e1)  {
			JOptionPane.showMessageDialog(null,e1.getMessage(),"error",2);
			dispose();
			
		} catch (Exception e1){
			JOptionPane.showMessageDialog(null,e1.getMessage(),"error",2);
			setVisible(false);
			dispose();
		}
		
		// Agrega los usuarios en el model
		
		table.setModel(modelo);
		

		scrollPane.setViewportView(table);
		
		btnSalir = new JButton("SALIR");
		btnSalir.addActionListener((e)->{
			setVisible(false);
			dispose();
			
		});
		panel.add(btnSalir, BorderLayout.SOUTH);
		
		panelOrdenamiento = new JPanel();
		panelOrdenamiento.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, null));
		panelOrdenamiento.setBounds(758, 5, 221, 186);
		contentPane.add(panelOrdenamiento);
		panelOrdenamiento.setLayout(null);
		
		btnOrdenarPorBarrio = new JButton(" BARRIO");
		btnOrdenarPorBarrio.setBounds(46, 35, 141, 23);
		btnOrdenarPorBarrio.addActionListener((e)->{
			this.reloadGrid("barrio");
			
		});
		
		panelOrdenamiento.add(btnOrdenarPorBarrio);
		
		btnOrdenarPorCodPostal = new JButton(" CODIGO POSTAL");
		btnOrdenarPorCodPostal.setBounds(25, 63, 183, 23);
		btnOrdenarPorCodPostal.addActionListener((e)->{
			this.reloadGrid("codigoPostal");

			
			
		});
		panelOrdenamiento.add(btnOrdenarPorCodPostal);
		
		btnOrdenarPorCodigo = new JButton(" CODIGO");
		btnOrdenarPorCodigo.setBounds(45, 91, 143, 23);
		btnOrdenarPorCodigo.addActionListener((e)->{
			this.reloadGrid("codigo");

			
		});
		panelOrdenamiento.add(btnOrdenarPorCodigo);
		
		
		btnOrdenarPorNombreYApellido = new JButton(" NOMBRE Y APELLIDO");
		btnOrdenarPorNombreYApellido.setBounds(15, 119, 203, 23);
		btnOrdenarPorNombreYApellido.addActionListener((e)->{
			this.reloadGrid("nombreApellido");

			
			
		});
		panelOrdenamiento.add(btnOrdenarPorNombreYApellido);
		
		lbOrdenarPor = new JLabel("Ordenar Por:");
		lbOrdenarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenarPor.setBounds(46, 11, 141, 14);
		panelOrdenamiento.add(lbOrdenarPor);
		
		panelFiltrado = new JPanel();
		panelFiltrado.setBounds(726, 202, 266, 222);
		contentPane.add(panelFiltrado);
		panelFiltrado.setLayout(null);
		
		lbFiltrarPor = new JLabel("FIltrar Por:");
		lbFiltrarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrarPor.setBounds(87, 5, 124, 14);
		panelFiltrado.add(lbFiltrarPor);
		
		JButton btnNombre_apellido = new JButton();
		btnNombre_apellido .addActionListener((e)->{
			System.out.println(txNombre_Apellido.getText());
			Predicate <ViviendaDTO> predicate =
					(ViviendaDTO v)->v.getDueño().getNombre().contains(txNombre_Apellido.getText())
					
					|| v.getDueño().getApellido().contains(txNombre_Apellido.getText());
					
			try {
				List<ViviendaDTO> v = api.obtenerViviendas(predicate);
				
				this.reloadGrid(v);
				
			} catch (Exception e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
			
		});
		btnNombre_apellido.setBounds(228, 30, 25, 23);
		panelFiltrado.add(btnNombre_apellido);
		
		btnCalle_Altura = new JButton("New button");
		btnCalle_Altura.addActionListener((e)->{
			Predicate <ViviendaDTO> predicate =
					(ViviendaDTO v)->v.getDireccion().getBarrio().contains(this.txCalle_Altura.getText())
					||v.getDireccion().getCalle().contains(this.txCalle_Altura.getText());
					
			try {
				List<ViviendaDTO> v = api.obtenerViviendas(predicate);
				
				this.reloadGrid(v);
				
			} catch (Exception e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
			
		});
		btnCalle_Altura.setBounds(228, 66, 25, 23);
		panelFiltrado.add(btnCalle_Altura);
		
		txCalle_Altura = new JTextField();
		txCalle_Altura.setBounds(110, 67, 114, 20);
		panelFiltrado.add(txCalle_Altura);
		txCalle_Altura.setColumns(10);
		
		lbCalle_Altura = new JLabel("Nombre y Apellido");
		lbCalle_Altura.setHorizontalAlignment(SwingConstants.CENTER);
		lbCalle_Altura.setBounds(0, 34, 101, 14);
		panelFiltrado.add(lbCalle_Altura);
		
		lbNombre_Apellido = new JLabel("Calle y Altura:");
		lbNombre_Apellido.setHorizontalAlignment(SwingConstants.CENTER);
		lbNombre_Apellido.setBounds(0, 70, 119, 14);
		panelFiltrado.add(lbNombre_Apellido);
		
		txNombre_Apellido = new JTextField();
		
		txNombre_Apellido.setBounds(110, 30, 113, 20);
		panelFiltrado.add(txNombre_Apellido);
		txNombre_Apellido.setColumns(10);
		
		lbDni = new JLabel("Dni: ");
		lbDni.setHorizontalAlignment(SwingConstants.CENTER);
		lbDni.setBounds(0, 106, 77, 14);
		panelFiltrado.add(lbDni);
		
		txDni = new JTextField();
		txDni.setBounds(110, 103, 114, 20);
		panelFiltrado.add(txDni);
		txDni.setColumns(10);
		
		btnDni = new JButton("New button");
		btnDni.addActionListener((e)->{
			Predicate <ViviendaDTO> predicate = (ViviendaDTO v)->v.getDueño().getDni().contains(txDni.getText());
			
			try {
				List<ViviendaDTO> v = api.obtenerViviendas(predicate);
				this.reloadGrid(v);
				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
		});
		btnDni.setBounds(228, 102, 25, 23);
		panelFiltrado.add(btnDni);
		
		btnCodigo = new JButton("New button");
		btnCodigo.addActionListener((E)->{
		Predicate <ViviendaDTO> predicate = (ViviendaDTO v)-> String.valueOf(v.getID()). contains(this.txCodigo.getText());

			
			try {
				List<ViviendaDTO> v = api.obtenerViviendas(predicate);
				this.reloadGrid(v);
				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
		});
		btnCodigo.setBounds(228, 136, 25, 23);
		panelFiltrado.add(btnCodigo);
		
		txCodigo = new JTextField();
		txCodigo.setColumns(10);
		txCodigo.setBounds(110, 137, 114, 20);
		panelFiltrado.add(txCodigo);
		
		lbCodigo = new JLabel("Codigo:");
		lbCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lbCodigo.setBounds(0, 140, 77, 14);
		panelFiltrado.add(lbCodigo);
		
		btnLimpiarFiltro = new JButton("Limpiar FIltro");
		btnLimpiarFiltro.addActionListener((e)->{
			try {
				reloadGrid(api.obtenerViviendas());
			} catch (AppException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
			
		});
		btnLimpiarFiltro.setBounds(87, 188, 89, 23);
		panelFiltrado.add(btnLimpiarFiltro);
		
	}
	private void reloadGrid(List<ViviendaDTO> viviendas) {
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
	private void reloadGrid(String tipoOrdenamiento){
			// Obtiene el model del table
			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			// Obtiene la lista de usuarios a mostrar
			List<ViviendaDTO> viviendas;
			try {
				switch(tipoOrdenamiento) {
			
					case "calle":
						viviendas = api.obtenerViviendasOrdenadasPorCalle();
						break;
					case "barrio":
						viviendas = api.obtenerViviendasOrdenadasPorBarrio();
						break;
					case "altura":
						viviendas = api.obtenerViviendasOrdenadasPorAltura();
						break;
					case "codigo":
						viviendas = api.obtenerViviendasOrdenadasPorCodigo();
						break;
					case "codigoPostal":
						viviendas = api.obtenerViviendasOrdenadasPorCodigoPostal();
						break;
					case "nombreApellido":
						viviendas = api.obtenerViviendasOrdenadasPorNombreYApellido();
						break;
					case "latitudLongitud":
						viviendas = api.obtenerViviendasPorLatitudYLongitud();
					default:
						viviendas = api.obtenerViviendas();
						break;
				}
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
				
			
				}catch (Exception e) {
				setVisible(false);
				JOptionPane.showMessageDialog(null,e.getMessage(), "error",JOptionPane.ERROR_MESSAGE);
			
			
				
			}
		
		}
}
