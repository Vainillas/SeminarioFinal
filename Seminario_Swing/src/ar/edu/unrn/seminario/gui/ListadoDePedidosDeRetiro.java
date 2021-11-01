package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenDeRetiroDTO;
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;

public class ListadoDePedidosDeRetiro extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JTextField tfFiltradoPoraAño;
	private JTextField tfFiltradoPorDia;
	private JTextField tfFiltradoPorMes;
	private JTextField tfFiltradoCodigo;
	private JPanel panel;
	private JButton button;
	private JPanel panel_filtrados;
	private JLabel lbFiltradoPorAño;
	private JRadioButton rdbtnFiltradoPorAño;
	private JLabel lbFiltradoPorMes;
	private JRadioButton rdbtnFiltradoPorMes;
	private JLabel lbFiltradoPorDia;
	private JRadioButton rdbtnFiltradoPorDia;
	private JLabel lbFiltradorPorCodigo;
	private JRadioButton rdbtnFiltradoPorCodigo;
	private JPanel panel_ordenamientos;
	private JLabel lbFiltrado;
	private JLabel lbOrdenamiento;
	private JPanel panel_botones;
	private JButton btnSalir;
	private JButton btnLimpiar;
	private ResourceBundle labels;
	private JLabel lbFiltradoPorObservacion;
	private JTextField tfFiltradoPorObservacion;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnFiltradoPorObservacion;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public ListadoDePedidosDeRetiro(IApi api) {
		labels = ResourceBundle.getBundle("labels");
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
		ResourceBundle labels = ResourceBundle.getBundle("labels");
		//ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en"));
		table = new JTable();
		String[] titulosUsuario = { 
				labels.getString("listado.de.ordenes.de.retiro.titulos.fecha.orden"),  
				
				
				labels.getString("listado.de.ordenes.de.retiro.titulos.codigo.orden"), 
				labels.getString("listado.de.ordenes.de.retiro.titulos.estado"), 

				labels.getString("listado.de.ordenes.de.retiro.titulos.codigo.pedido"), 
				labels.getString("listado.de.ordenes.de.retiro.titulos.recolector.dni"), 
				
						
						
						
				};
		
		modelo = new DefaultTableModel(new Object[][] {}, titulosUsuario);

		// Obtiene la lista de usuarios a mostrar
		List<OrdenDeRetiroDTO> ordenesDeRetiro = new ArrayList<OrdenDeRetiroDTO>();
		
		try {
			ordenesDeRetiro = api.obtenerOrdenesDeRetiro();
			for (OrdenDeRetiroDTO o : ordenesDeRetiro) {				
				modelo.addRow(new Object[] { 
				 		o.getFechaOrden(),
						o.getEstado().obtenerEstado(),
						o.getPedidoAsociado().getCodigo(),
						o.getRecolector().getDni(),
					
				});
			}
		} catch (Exception e3) {
			JOptionPane.showMessageDialog(null, e3.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		
		
		table.setModel(modelo);

		scrollPane.setViewportView(table);
		
		
		panel_filtrados = new JPanel();
		panel_filtrados.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), new Color(128, 128, 128), new Color(192, 192, 192), new Color(128, 128, 128)));
		panel_filtrados.setBounds(10, 35, 220, 148);
		panel.add(panel_filtrados);
		
		lbFiltradoPorAño = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.por.dni"));
		lbFiltradoPorAño.setBounds(30, 8, 71, 14);
		lbFiltradoPorAño.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfFiltradoPoraAño = new JTextField();
		tfFiltradoPoraAño.setBounds(100, 5, 86, 20);
		tfFiltradoPoraAño.setHorizontalAlignment(SwingConstants.CENTER);
		tfFiltradoPoraAño.setColumns(10);
		
		rdbtnFiltradoPorAño = new JRadioButton("");
		rdbtnFiltradoPorAño.addActionListener((e)->{
			/*if(rdbtnFiltradoPorDni.isSelected()) {
				
			Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
				o.getRecolector().getDni().contains(this.tfFiltradoPorDni.getText());
				//List<OrdenDeRetiroDTO> ordenes = api.obtenerOrdenesDeRetiro(predicado);
			}*/
			
			
		});
		rdbtnFiltradoPorAño.setBounds(190, 5, 21, 21);
		
		lbFiltradoPorMes = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.por.estado"));
		lbFiltradoPorMes.setBounds(30, 31, 71, 14);
		lbFiltradoPorMes.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfFiltradoPorDia = new JTextField();
		tfFiltradoPorDia.setBounds(100, 35, 86, 20);
		tfFiltradoPorDia.setColumns(10);
		
		rdbtnFiltradoPorMes = new JRadioButton("");
		rdbtnFiltradoPorMes.addActionListener((e)->{
			/*if(rdbtnFiltradoPoEstado.isSelected()) {
				
			Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
				o.getEstado().obtenerEstado().contains(this.tfFiltradoPorEstado.getText());
				//List<OrdenDeRetiroDTO> ordenes = api.obtenerOrdenesDeRetiro(predicado);
			}*/
			
		});
		rdbtnFiltradoPorMes.setBounds(190, 29, 21, 21);
		
		lbFiltradoPorDia = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.por.codigo.pedido"));
		lbFiltradoPorDia.setBounds(0, 63, 101, 14);
		lbFiltradoPorDia.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfFiltradoPorMes = new JTextField();
		tfFiltradoPorMes.setBounds(100, 60, 86, 20);
		tfFiltradoPorMes.setColumns(10);
		
		rdbtnFiltradoPorDia = new JRadioButton("");
		rdbtnFiltradoPorDia.addActionListener((e)->{
			/*if(rdbtnFiltradoCodigoPedido.isSelected()) {
				
			Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
				String.valueOf(o.getPedidoAsociado().getCodigo()).contains(this.tfFiltradoCodigoPedido.getText());
				//List<OrdenDeRetiroDTO> ordenes = api.obtenerOrdenesDeRetiro(predicado);
			}*/
		});
		rdbtnFiltradoPorDia.setBounds(190, 57, 21, 21);
		
		lbFiltradorPorCodigo = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.por.visita"));
		lbFiltradorPorCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltradorPorCodigo.setBounds(30, 86, 71, 14);
		
		tfFiltradoCodigo = new JTextField();
		tfFiltradoCodigo.setBounds(100, 85, 86, 20);
		tfFiltradoCodigo.setColumns(10);
		
		rdbtnFiltradoPorCodigo = new JRadioButton("");
		rdbtnFiltradoPorCodigo.setBounds(190, 81, 21, 21);
		panel_filtrados.setLayout(null);
		panel_filtrados.add(lbFiltradoPorAño);
		panel_filtrados.add(tfFiltradoPoraAño);
		panel_filtrados.add(rdbtnFiltradoPorAño);
		panel_filtrados.add(lbFiltradoPorMes);
		panel_filtrados.add(tfFiltradoPorDia);
		panel_filtrados.add(rdbtnFiltradoPorMes);
		panel_filtrados.add(lbFiltradoPorDia);
		panel_filtrados.add(tfFiltradoPorMes);
		panel_filtrados.add(rdbtnFiltradoPorDia);
		panel_filtrados.add(lbFiltradorPorCodigo);
		panel_filtrados.add(tfFiltradoCodigo);
		panel_filtrados.add(rdbtnFiltradoPorCodigo);
		
		lbFiltradoPorObservacion = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.por.codigo")); 
		lbFiltradoPorObservacion.setBounds(8, 111, 93, 14);
		
		panel_filtrados.add(lbFiltradoPorObservacion);
		
		rdbtnFiltradoPorObservacion = new JRadioButton("");
		rdbtnFiltradoPorObservacion.addActionListener((e)->{
			if(rdbtnFiltradoPorObservacion.isSelected()) {
				
			/*Predicate <OrdenDeRetiroDTO> predicado = (OrdenDeRetiroDTO o)->
				String.valueOf(o.getCodigo()).contains(this.tfFiltradoPorCodigoOrden.getText());
				//List<OrdenDeRetiroDTO> ordenes = api.obtenerOrdenesDeRetiro(predicado);
			}*/
			}
		});
		rdbtnFiltradoPorObservacion.setBounds(190, 107, 21, 23);
		panel_filtrados.add(rdbtnFiltradoPorObservacion);
		
		tfFiltradoPorObservacion = new JTextField();
		tfFiltradoPorObservacion.addActionListener((e)->{
			
			
			
		});
		tfFiltradoPorObservacion.setBounds(100, 110, 86, 20);
		panel_filtrados.add(tfFiltradoPorObservacion);
		tfFiltradoPorObservacion.setColumns(10);
		
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

}
