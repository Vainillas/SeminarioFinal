package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.OrdenDeRetiroDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.utilities.Predicate;

import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListadoDeOrdenesDeRetiro extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JTextField tfFiltradoPorDni;
	private JTextField tfFiltradoPorEstado;
	private JTextField tfFiltradoCodigoPedido;
	private JPanel panel;
	private JButton button;
	private JPanel panel_filtrados;
	private JLabel lbFiltradoPorDni;
	private JRadioButton rdbtnFiltradoPorDni;
	private JLabel lbFiltradorPorEstado;
	private JRadioButton rdbtnFiltradoPoEstado;
	private JLabel lbFiltradoCodigoPedido;
	private JRadioButton rdbtnFiltradoCodigoPedido;
	private JPanel panel_ordenamientos;
	private JLabel lbFiltrado;
	private JLabel lbOrdenamiento;
	private JPanel panel_botones;
	private JButton btnLimpiar;
	private ResourceBundle labels;
	private JLabel lbFiltradoPorCodigoOrden;
	private JTextField tfFiltradoPorCodigoOrden;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnFiltradoPorCodigoOrden;
	private JLabel lbOrdenarPorCodigoPedido;
	private JLabel lbOrdenarPorCodigoOrden;
	private JRadioButton rdbtnOrdenarPorCodigoOrden;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					ResourceBundle labels = ResourceBundle.getBundle("labels");
					ListadoDeOrdenesDeRetiro frame = new ListadoDeOrdenesDeRetiro(api,labels);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param labels 
	 */
	public ListadoDeOrdenesDeRetiro(IApi api, ResourceBundle labels) {

		setTitle(labels.getString("listado.de.ordenes.de.retiro.titulo")); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 836, 464);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane); 
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(252, 11, 548, 345);
		panel.add(scrollPane);
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		//ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en"));
		table = new JTable();
		String[] titulosUsuario = { 
				labels.getString("listado.de.ordenes.de.retiro.titulos.fecha.orden"),  
				
				
				labels.getString("listado.de.ordenes.de.retiro.titulos.codigo.orden"), 
				labels.getString("listado.de.ordenes.de.retiro.titulos.estado"), 

				labels.getString("listado.de.ordenes.de.retiro.titulos.codigo.pedido"), 
				labels.getString("listado.de.ordenes.de.retiro.titulos.dni.recolector"), 
						
				};
		
		modelo = new DefaultTableModel(new Object[][] {}, titulosUsuario);

		// Obtiene la lista de usuarios a mostrar
		try {
			this.reloadGrid(api.obtenerOrdenesDeRetiro());
		} catch (AppException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}


		
		
		table.setModel(modelo);

		scrollPane.setViewportView(table);
		
		
		panel_filtrados = new JPanel();
		panel_filtrados.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), new Color(128, 128, 128), new Color(192, 192, 192), new Color(128, 128, 128)));
		panel_filtrados.setBounds(10, 35, 220, 148);
		panel.add(panel_filtrados);
		
		lbFiltradoPorDni = new JLabel(labels.getString("listado.de.ordenes.de.retiro.label.filtrado.por.dni"));
		lbFiltradoPorDni.setBounds(0, 8, 101, 14);
		lbFiltradoPorDni.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfFiltradoPorDni = new JTextField();
		tfFiltradoPorDni.setBounds(100, 5, 86, 20);
		tfFiltradoPorDni.setHorizontalAlignment(SwingConstants.CENTER);
		tfFiltradoPorDni.setColumns(10);
		
		rdbtnFiltradoPorDni = new JRadioButton("");
		rdbtnFiltradoPorDni.addActionListener((e)->{
			rdbtnFiltradoPorDni.setSelected(false);
			if(!tfFiltradoPorDni.getText().equals("")) {
				Predicate <OrdenDeRetiroDTO> predicate = (OrdenDeRetiroDTO o)->
				o.getRecolector().getDni().contains(this.tfFiltradoPorDni.getText());
				
				try {
					this.reloadGrid(api.obtenerOrdenesDeRetiro(predicate));
				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
			
			
			
		});
		rdbtnFiltradoPorDni.setBounds(190, 5, 21, 21);
		
		lbFiltradorPorEstado = new JLabel(labels.getString("listado.de.ordenes.de.retiro.label.filtrado.por.estado"));
		lbFiltradorPorEstado.setBounds(0, 31, 101, 14);
		lbFiltradorPorEstado.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfFiltradoPorEstado = new JTextField();
		tfFiltradoPorEstado.setBounds(100, 35, 86, 20);
		tfFiltradoPorEstado.setColumns(10);
		
		rdbtnFiltradoPoEstado = new JRadioButton("");
		rdbtnFiltradoPoEstado.addActionListener((e)->{
			rdbtnFiltradoPoEstado.setSelected(false);
			
		if(!tfFiltradoPorEstado.getText().equals("")) {
			
			Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
				o.getEstado().obtenerEstado().contains(this.tfFiltradoPorEstado.getText());
				try {
					this.reloadGrid(api.obtenerOrdenesDeRetiro(predicado));
				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

				}
				
		}
			
		});
		rdbtnFiltradoPoEstado.setBounds(190, 29, 21, 21);
		
		lbFiltradoCodigoPedido = new JLabel(labels.getString("listado.de.ordenes.de.retiro.label.filtrado.por.codigo.pedido"));
		lbFiltradoCodigoPedido.setBounds(0, 63, 101, 14);
		lbFiltradoCodigoPedido.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfFiltradoCodigoPedido = new JTextField();
		tfFiltradoCodigoPedido.setBounds(100, 60, 86, 20);
		tfFiltradoCodigoPedido.setColumns(10);
		
		rdbtnFiltradoCodigoPedido = new JRadioButton("");
		rdbtnFiltradoCodigoPedido.addActionListener((e)->{
			rdbtnFiltradoCodigoPedido.setSelected(false);
			if(!tfFiltradoCodigoPedido.getText().equals("")) {
				
			Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
				String.valueOf(o.getPedidoAsociado().getCodigo()).contains(this.tfFiltradoCodigoPedido.getText());
				try {
					this.reloadGrid(api.obtenerOrdenesDeRetiro(predicado));
				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

				}
				
		}
		});
		rdbtnFiltradoCodigoPedido.setBounds(190, 57, 21, 21);
		panel_filtrados.setLayout(null);
		panel_filtrados.add(lbFiltradoPorDni);
		panel_filtrados.add(tfFiltradoPorDni);
		panel_filtrados.add(rdbtnFiltradoPorDni);
		panel_filtrados.add(lbFiltradorPorEstado);
		panel_filtrados.add(tfFiltradoPorEstado);
		panel_filtrados.add(rdbtnFiltradoPoEstado);
		panel_filtrados.add(lbFiltradoCodigoPedido);
		panel_filtrados.add(tfFiltradoCodigoPedido);
		panel_filtrados.add(rdbtnFiltradoCodigoPedido);
		
		lbFiltradoPorCodigoOrden = new JLabel(labels.getString("listado.de.ordenes.de.retiro.label.filtrado.por.codigo.orden")); 
		lbFiltradoPorCodigoOrden.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltradoPorCodigoOrden.setBounds(10, 91, 93, 14);
		
		panel_filtrados.add(lbFiltradoPorCodigoOrden);
		
		rdbtnFiltradoPorCodigoOrden = new JRadioButton("");
		rdbtnFiltradoPorCodigoOrden.addActionListener((e)->{
			rdbtnFiltradoPorCodigoOrden.setSelected(false);
			if(!tfFiltradoPorCodigoOrden.getText().equals("")) {
				
			Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
			
				String.valueOf(o.getCodigo()).contains(this.tfFiltradoPorCodigoOrden.getText());
				try {
					this.reloadGrid(api.obtenerOrdenesDeRetiro(predicado));
				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

				}
				
		
		}	
		});
		rdbtnFiltradoPorCodigoOrden.setBounds(190, 85, 21, 23);
		panel_filtrados.add(rdbtnFiltradoPorCodigoOrden);
		
		tfFiltradoPorCodigoOrden = new JTextField();
		tfFiltradoPorCodigoOrden.setBounds(100, 88, 86, 20);
		panel_filtrados.add(tfFiltradoPorCodigoOrden);
		tfFiltradoPorCodigoOrden.setColumns(10);
		
		panel_ordenamientos = new JPanel();
		panel_ordenamientos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_ordenamientos.setBounds(10, 219, 220, 98);
		panel.add(panel_ordenamientos);
		panel_ordenamientos.setLayout(null);
		
		JLabel lbOrdenarPorDni = new JLabel(labels.getString("listado.de.ordenes.de.retiro.label.ordenar.por.dni")); 
		lbOrdenarPorDni.setBounds(50, 8, 90, 14);
		lbOrdenarPorDni.setHorizontalAlignment(SwingConstants.CENTER);
		panel_ordenamientos.add(lbOrdenarPorDni);
		
		JRadioButton rdbtnOrdenarPorDni = new JRadioButton("");
		rdbtnOrdenarPorDni.addActionListener((e)->{
			rdbtnOrdenarPorDni.setSelected(false);
			Comparator <OrdenDeRetiroDTO> comparator = (OrdenDeRetiroDTO o1, OrdenDeRetiroDTO o2)->
			o1.getRecolector().getDni().compareToIgnoreCase(o2.getRecolector().getDni());
			try {
				reloadGrid(api.obtenerOrdenesDeRetiro(comparator));
			} catch (AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
		});
		
		rdbtnOrdenarPorDni.setBounds(150, 5, 21, 21);
		panel_ordenamientos.add(rdbtnOrdenarPorDni);
		
		lbOrdenarPorCodigoPedido = new JLabel(labels.getString("listado.de.ordenes.de.retiro.label.ordenar.por.codigo.pedido")); 
		
		lbOrdenarPorCodigoPedido.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenarPorCodigoPedido.setBounds(50, 34, 90, 14);
		panel_ordenamientos.add(lbOrdenarPorCodigoPedido);
		
		JRadioButton rdbtnOrdenarPorCodigoPedido = new JRadioButton(""); 
		rdbtnOrdenarPorCodigoPedido.addActionListener((e)->{
			rdbtnOrdenarPorCodigoPedido.setSelected(false);
			Comparator <OrdenDeRetiroDTO> comparator = (OrdenDeRetiroDTO o1, OrdenDeRetiroDTO o2)->
			(String.valueOf(o1.getPedidoAsociado().getCodigo()).compareToIgnoreCase(String.valueOf(o2.getPedidoAsociado().getCodigo())));
			
			
			try {
				reloadGrid(api.obtenerOrdenesDeRetiro(comparator));
			} catch (AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
		});
		rdbtnOrdenarPorCodigoPedido.setBounds(150, 31, 21, 21);
		panel_ordenamientos.add(rdbtnOrdenarPorCodigoPedido);
		
		lbOrdenarPorCodigoOrden = new JLabel(labels.getString("listado.de.ordenes.de.retiro.label.ordenar.por.codigo.orden")); 
		lbOrdenarPorCodigoOrden.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenarPorCodigoOrden.setBounds(50, 60, 90, 14);
		panel_ordenamientos.add(lbOrdenarPorCodigoOrden);
		
		rdbtnOrdenarPorCodigoOrden = new JRadioButton(""); 
		rdbtnOrdenarPorCodigoOrden.addActionListener((e)->{
			rdbtnOrdenarPorCodigoOrden.setSelected(false);
			Comparator <OrdenDeRetiroDTO> comparator = (OrdenDeRetiroDTO o1, OrdenDeRetiroDTO o2)->
			(String.valueOf(o1.getCodigo()).compareToIgnoreCase(String.valueOf(o2.getCodigo())));
		});
		rdbtnOrdenarPorCodigoOrden.setBounds(150, 57, 21, 21);
		panel_ordenamientos.add(rdbtnOrdenarPorCodigoOrden);
		
		lbFiltrado = new JLabel(labels.getString("listado.de.ordenes.de.retiro.label.filtrado"));
		lbFiltrado.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrado.setBounds(52, 11, 143, 14);
		panel.add(lbFiltrado);
		
		lbOrdenamiento = new JLabel(labels.getString("listado.de.ordenes.de.retiro.label.ordenamiento"));
		lbOrdenamiento.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenamiento.setBounds(52, 194, 143, 14);
		panel.add(lbOrdenamiento);
		
		panel_botones = new JPanel();
		panel_botones.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_botones.setBounds(304, 367, 441, 37);
		panel.add(panel_botones);
		
		JButton btnConcretarOrden = new JButton(labels.getString("listado.de.ordenes.de.retiro.label.concretar.orden"));
		panel_botones.add(btnConcretarOrden);
		btnConcretarOrden.addActionListener((e)->{
			System.out.println(table.getSelectedColumn());
			System.out.println(table.getSelectedRow());
			
			if(!(table.getSelectedRow() == -1)) {
					try {
						api.concretarOrdenDeRetiro((int)table.getValueAt(table.getSelectedRow(),1 ));
						JOptionPane.showMessageDialog(null,labels.getString("listado.de.ordenes.de.retiro.label.concretar.orden"));
						this.reloadGrid(api.obtenerOrdenesDeRetiro());
					} catch (AppException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(),"error",0);
					}
					
			}
					
				
			
			else {
				JOptionPane.showMessageDialog(null,"Debe Seleccionar Un Pedido","error",0);
			}
		});
		
		JButton btnCancelarOrden = new JButton(labels.getString("listado.de.pedidos.de.retiro.button.cancelar.orden"));
		btnCancelarOrden.addActionListener((e)->{
			if(!(table.getSelectedRow() == -1)) {

				try {
					api.cancelarOrdenDeRetiro((int)table.getValueAt(table.getSelectedRow(),1 ));
					
					JOptionPane.showMessageDialog(null,labels.getString("listado.de.ordenes.de.retiro.mensaje.orden.cancelada"));
					this.reloadGrid(api.obtenerOrdenesDeRetiro());

				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",0);
				}
			}
			else {
				JOptionPane.showMessageDialog(null,labels.getString("listado.de.ordenes.de.retiro.mensaje.error"),"error",0);
			}
			
			
		});
		panel_botones.add(btnCancelarOrden);
		
		JButton btnSalir = new JButton(labels.getString("listado.de.pedidos.de.retiro.button.salir"));
		btnSalir.addActionListener((e)->{
			this.setVisible(false);
			dispose();
		});
		panel_botones.add(btnSalir);
		
		btnLimpiar = new JButton(labels.getString("listado.de.pedidos.de.retiro.button.limpiar"));
		btnLimpiar.setBounds(79, 333, 87, 23);
		panel.add(btnLimpiar);
		btnLimpiar.addActionListener((e)->{
			try {
				this.reloadGrid(api.obtenerOrdenesDeRetiro());
			} catch (AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"error",0);
			}
		});
		

		
	}
	private void reloadGrid(List<OrdenDeRetiroDTO> ordenes) {
		modelo.setRowCount(0);
		// Agrega los usuarios en el model

			for (OrdenDeRetiroDTO o : ordenes) {
				modelo.addRow(new Object[] { 
				 		o.getFechaOrden(),
				 		o.getCodigo(),
						o.getEstado().obtenerEstado(),
						o.getPedidoAsociado().getCodigo(),
						o.getRecolector().getDni(),
					
				});
	}
			
	}
}
