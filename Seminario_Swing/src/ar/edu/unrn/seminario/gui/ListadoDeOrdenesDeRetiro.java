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
	private JButton btnSalir;
	private JButton btnLimpiar;
	private ResourceBundle labels;
	private JLabel lbFiltradoPorCodigoOrden;
	private JTextField tfFiltradoPorCodigoOrden;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnFiltradoPorCodigoOrden;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					ListadoDeOrdenesDeRetiro frame = new ListadoDeOrdenesDeRetiro(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ListadoDeOrdenesDeRetiro(IApi api) {
		//labels = ResourceBundle.getBundle("labels");
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es"));
		setTitle(labels.getString("listado.de.pedidos.de.retiro.titulo"));
		
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
		
		lbFiltradoPorDni = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.por.dni"));
		lbFiltradoPorDni.setBounds(0, 8, 101, 14);
		lbFiltradoPorDni.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfFiltradoPorDni = new JTextField();
		tfFiltradoPorDni.setBounds(100, 5, 86, 20);
		tfFiltradoPorDni.setHorizontalAlignment(SwingConstants.CENTER);
		tfFiltradoPorDni.setColumns(10);
		
		rdbtnFiltradoPorDni = new JRadioButton("");
		rdbtnFiltradoPorDni.addActionListener((e)->{
			if(rdbtnFiltradoPorDni.isSelected()) {
			Predicate <OrdenDeRetiroDTO> predicate = (OrdenDeRetiroDTO o)->
				o.getRecolector().getDni().contains(this.tfFiltradoPorDni.getText());
				
				try {
					this.reloadGrid(api.obtenerOrdenesDeRetiro(predicate));
				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				}
				
				rdbtnFiltradoPorDni.setSelected(false);
			}
			
			
		});
		rdbtnFiltradoPorDni.setBounds(190, 5, 21, 21);
		
		lbFiltradorPorEstado = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.por.estado"));
		lbFiltradorPorEstado.setBounds(0, 31, 101, 14);
		lbFiltradorPorEstado.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfFiltradoPorEstado = new JTextField();
		tfFiltradoPorEstado.setBounds(100, 35, 86, 20);
		tfFiltradoPorEstado.setColumns(10);
		
		rdbtnFiltradoPoEstado = new JRadioButton("");
		rdbtnFiltradoPoEstado.addActionListener((e)->{
			if(rdbtnFiltradoPoEstado.isSelected()) {
				
			Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
				o.getEstado().obtenerEstado().contains(this.tfFiltradoPorEstado.getText());
				try {
					this.reloadGrid(api.obtenerOrdenesDeRetiro(predicado));
				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

				}
				rdbtnFiltradoPoEstado.setSelected(false);
				
			}
			
		});
		rdbtnFiltradoPoEstado.setBounds(190, 29, 21, 21);
		
		lbFiltradoCodigoPedido = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.por.codigo.pedido"));
		lbFiltradoCodigoPedido.setBounds(0, 63, 101, 14);
		lbFiltradoCodigoPedido.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfFiltradoCodigoPedido = new JTextField();
		tfFiltradoCodigoPedido.setBounds(100, 60, 86, 20);
		tfFiltradoCodigoPedido.setColumns(10);
		
		rdbtnFiltradoCodigoPedido = new JRadioButton("");
		rdbtnFiltradoCodigoPedido.addActionListener((e)->{
			if(rdbtnFiltradoCodigoPedido.isSelected()) {
				
			Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
				String.valueOf(o.getPedidoAsociado().getCodigo()).contains(this.tfFiltradoCodigoPedido.getText());
				try {
					this.reloadGrid(api.obtenerOrdenesDeRetiro(predicado));
				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

				}
				rdbtnFiltradoCodigoPedido.setSelected(false);
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
			if(rdbtnFiltradoPorCodigoOrden.isSelected()) {
				
			Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
				String.valueOf(o.getCodigo()).contains(this.tfFiltradoPorCodigoOrden.getText());
				try {
					this.reloadGrid(api.obtenerOrdenesDeRetiro(predicado));
				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

				}
				rdbtnFiltradoPorCodigoOrden.setSelected(false);
			}
			
		});
		rdbtnFiltradoPorCodigoOrden.setBounds(190, 85, 21, 23);
		panel_filtrados.add(rdbtnFiltradoPorCodigoOrden);
		
		tfFiltradoPorCodigoOrden = new JTextField();
		tfFiltradoPorCodigoOrden.setBounds(100, 88, 86, 20);
		panel_filtrados.add(tfFiltradoPorCodigoOrden);
		tfFiltradoPorCodigoOrden.setColumns(10);
		
		panel_ordenamientos = new JPanel();
		panel_ordenamientos.setBounds(25, 219, 155, 106);
		panel.add(panel_ordenamientos);
		
		lbFiltrado = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado"));
		lbFiltrado.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrado.setBounds(52, 11, 143, 14);
		panel.add(lbFiltrado);
		
		lbOrdenamiento = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.ordenamiento"));
		lbOrdenamiento.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenamiento.setBounds(31, 194, 133, 14);
		panel.add(lbOrdenamiento);
		
		panel_botones = new JPanel();
		panel_botones.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_botones.setBounds(304, 367, 441, 37);
		panel.add(panel_botones);
		
		btnLimpiar = new JButton(labels.getString("listado.de.pedidos.de.retiro.button.limpiar"));
		panel_botones.add(btnLimpiar);
		
		btnSalir = new JButton(labels.getString("listado.de.pedidos.de.retiro.button.salir"));
		btnSalir.addActionListener((e)->{
			this.setVisible(false);
			dispose();
			
		});
		panel_botones.add(btnSalir);
		

		
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
