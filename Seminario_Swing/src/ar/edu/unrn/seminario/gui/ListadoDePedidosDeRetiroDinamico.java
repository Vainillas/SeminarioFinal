package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;


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
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.utilities.Predicate;
import java.awt.event.ActionListener;

public class ListadoDePedidosDeRetiroDinamico extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JTextField tf_filtrado_por_codigo_pedido;
	private JTextField tf_filtrado_por_vivienda;
	private JTextField tf_filtrado_por_codigo_vivienda;
	private JPanel panel;
	private JButton button;
	private JPanel panel_filtrados;
	private JLabel lb_filtrado_por_codigo_pedido;
	private JRadioButton rdbtn_filtrado_por_codigo_pedido;
	private JLabel lb_filtrado_por_vivienda;
	private JRadioButton rdbtn_filtrado_por_vivienda;
	private JLabel lb_filtrado_por_codigo_vivienda;
	private JRadioButton rdbtn_filtrado_por_codigo_vivienda;
	private JPanel panel_ordenamientos;
	private JLabel lbFiltrado;
	private JLabel lbOrdenamiento;
	private JPanel panel_botones;
	private JButton btnSalir;
	private JButton btnLimpiar;
	private ResourceBundle labels;
	private JRadioButton rdbtnNewRadioButton;
	private JLabel lb_ordenar_por_vivienda;
	private JRadioButton rdbtn_ordenar_por_vivienda;
	private JLabel lb_ordenar_por_codigo_vivienda;
	private JRadioButton rdbt_ordenar_por_codigo_vivienda;
	private JScrollPane scrollPane ;
	private JButton btn_limpiar_ordenamiento;
	private List<PedidoDeRetiroDTO> predicate;
	private List<PedidoDeRetiroDTO> comparator;
	private List<PedidoDeRetiroDTO> listaPedidos;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 * @param labels 
	 */
	public ListadoDePedidosDeRetiroDinamico(IApi api, ResourceBundle labels) {
		try {
			this.listaPedidos = api.obtenerPedidosDeRetiroDeUsuario();
			if(api.obtenerRolUsuarioActivo().equals("ADMIN")){
			
			
			}
			else {
				this.listaPedidos = api.obtenerPedidosDeRetiro();
			} 
			
		}catch (DataEmptyException | NotNullException | DateNullException | AppException
						| IncorrectEmailException | StringNullException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Error",0);
				}
			
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle(labels.getString("listado.de.pedidos.de.retiro.titulo"));
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(252, 11, 770, 345);
		panel.add(scrollPane);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1074, 464);
		
		

		
		

		
		//ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en"));
		table = new JTable();
		String[] titulosUsuario = { 
				labels.getString("listado.de.pedidos.de.retiro.titulos.fecha.pedido"), 
				labels.getString("listado.de.pedidos.de.retiro.titulos.codigo.pedido"), 
				labels.getString("listado.de.pedidos.de.retiro.titulos.requiere.maquinaria.pedido"), 
				labels.getString("listado.de.pedidos.de.retiro.titulos.vivienda.pedido"), 
				labels.getString("listado.de.pedidos.de.retiro.titulos.codigo.vivienda.pedido"),
				labels.getString("listado.de.pedidos.de.retiro.titulos.observacion")
				};
		
		modelo = new DefaultTableModel(new Object[][] {}, titulosUsuario);

		// Obtiene la lista de usuarios a mostrar
		//List<PedidoDeRetiroDTO> PedidoDeRetiro = new ArrayList<PedidoDeRetiroDTO>();
		
		try {
			reloadGrid(api.obtenerPedidosDeRetiro());
			table.setModel(modelo);
			scrollPane.setViewportView(table);
			
		} catch (Exception e3) {
			JOptionPane.showMessageDialog(null, e3.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		

		panel_filtrados = new JPanel();
		panel_filtrados.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), new Color(128, 128, 128), new Color(192, 192, 192), new Color(128, 128, 128)));
		panel_filtrados.setBounds(10, 35, 220, 127);
		panel.add(panel_filtrados);
		
		lb_filtrado_por_codigo_pedido = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.ordenamiento.por.codigo.pedido"));
		lb_filtrado_por_codigo_pedido.setHorizontalAlignment(SwingConstants.CENTER);
		lb_filtrado_por_codigo_pedido.setBounds(0, 8, 101, 14);
		
		tf_filtrado_por_codigo_pedido = new JTextField();
		tf_filtrado_por_codigo_pedido.setBounds(100, 5, 86, 20);
		tf_filtrado_por_codigo_pedido.setHorizontalAlignment(SwingConstants.CENTER);
		tf_filtrado_por_codigo_pedido.setColumns(10);
		
		rdbtn_filtrado_por_codigo_pedido = new JRadioButton("");
		rdbtn_filtrado_por_codigo_pedido.addActionListener((e)->{
			rdbtn_filtrado_por_codigo_pedido.setSelected(false);
			if(!tf_filtrado_por_codigo_pedido.getText().equals("")) {

			Predicate <PedidoDeRetiroDTO> predicado = (PedidoDeRetiroDTO p)->
			String.valueOf(p.getCodigo()).equals(tf_filtrado_por_codigo_pedido.getText());

				try {

					reloadGrid (api.obtenerPedidosDeRetiro(predicado));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				
				
				}
			
			}
		});
		rdbtn_filtrado_por_codigo_pedido.setBounds(190, 5, 21, 21);
		
		lb_filtrado_por_vivienda = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.ordenamiento.por.vivienda"));
		lb_filtrado_por_vivienda.setBounds(0, 38, 101, 14);
		lb_filtrado_por_vivienda.setHorizontalAlignment(SwingConstants.CENTER);
		
		tf_filtrado_por_vivienda = new JTextField();
		tf_filtrado_por_vivienda.setBounds(100, 35, 86, 20);
		tf_filtrado_por_vivienda.setColumns(10);
		
		rdbtn_filtrado_por_vivienda = new JRadioButton("");
		rdbtn_filtrado_por_vivienda.setSelected(false);
		rdbtn_filtrado_por_vivienda.addActionListener((e)->{
			rdbtn_filtrado_por_vivienda.setSelected(false);
			if(!tf_filtrado_por_vivienda.getText().equals("")) {
				Predicate <PedidoDeRetiroDTO> predicado = (PedidoDeRetiroDTO p )->
				
				p.getVivienda().getDireccion().getBarrio().toLowerCase().contains(tf_filtrado_por_vivienda.getText())||
				p.getVivienda().getDireccion().getCalle().toLowerCase().contains(tf_filtrado_por_vivienda.getText())||
				p.getVivienda().getDireccion().getAltura().toLowerCase().contains(tf_filtrado_por_vivienda.getText());
			
			
			try {

				reloadGrid(api.obtenerPedidosDeRetiro(predicado));
				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			}
			
			
		});
		rdbtn_filtrado_por_vivienda.setBounds(190, 29, 21, 21);
		
		lb_filtrado_por_codigo_vivienda = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.ordenamiento.por.codigo.vivienda"));
		lb_filtrado_por_codigo_vivienda.setBounds(0, 63, 101, 14);
		lb_filtrado_por_codigo_vivienda.setHorizontalAlignment(SwingConstants.CENTER);
		
		tf_filtrado_por_codigo_vivienda = new JTextField();
		tf_filtrado_por_codigo_vivienda.setBounds(100, 60, 86, 20);
		tf_filtrado_por_codigo_vivienda.setColumns(10);
		rdbtn_filtrado_por_codigo_vivienda = new JRadioButton("");
		rdbtn_filtrado_por_codigo_vivienda.addActionListener((e)->{
				rdbtn_filtrado_por_codigo_vivienda.setSelected(false);
				if(!tf_filtrado_por_codigo_vivienda.getText().equals("")) {
					Predicate <PedidoDeRetiroDTO> predicado = (PedidoDeRetiroDTO p )->
					String.valueOf(p.getVivienda().getID()).equals(tf_filtrado_por_codigo_vivienda.getText());
					try {
						reloadGrid(api.obtenerPedidosDeRetiro(predicado));
					
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

				}
			
		}
		});
		rdbtn_filtrado_por_codigo_vivienda.setBounds(190, 57, 21, 21);
		panel_filtrados.setLayout(null);
		panel_filtrados.add(lb_filtrado_por_codigo_pedido);
		panel_filtrados.add(tf_filtrado_por_codigo_pedido);
		panel_filtrados.add(rdbtn_filtrado_por_codigo_pedido);
		panel_filtrados.add(lb_filtrado_por_vivienda);
		panel_filtrados.add(tf_filtrado_por_vivienda);
		panel_filtrados.add(rdbtn_filtrado_por_vivienda);
		panel_filtrados.add(lb_filtrado_por_codigo_vivienda);
		panel_filtrados.add(tf_filtrado_por_codigo_vivienda);
		panel_filtrados.add(rdbtn_filtrado_por_codigo_vivienda);
		
		JButton btn_limpiar_filtro = new JButton(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.limpiar")); 
		btn_limpiar_filtro.addActionListener((e)->{
			try {
				reloadGrid(api.obtenerPedidosDeRetiro());
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
			
		});
		btn_limpiar_filtro.setBounds(51, 93, 135, 23);
		panel_filtrados.add(btn_limpiar_filtro);
		
		panel_ordenamientos = new JPanel();
		panel_ordenamientos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_ordenamientos.setBounds(20, 219, 199, 167);
		panel.add(panel_ordenamientos);
		panel_ordenamientos.setLayout(null);
		
		JLabel lb_ordenar_por_codigo_pedido = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.ordenamiento.por.codigo.pedido")); 
		lb_ordenar_por_codigo_pedido.setHorizontalAlignment(SwingConstants.CENTER);
		
		lb_ordenar_por_codigo_pedido.setBounds(18, 7, 96, 14);
		panel_ordenamientos.add(lb_ordenar_por_codigo_pedido);
		
		JRadioButton rdbtn_ordenar_por_codigo_pedido = new JRadioButton("");

		rdbtn_ordenar_por_codigo_pedido.addActionListener((e)->{
			rdbtn_ordenar_por_codigo_pedido.setSelected(false);
			try {
				Comparator <PedidoDeRetiroDTO> comparator = (PedidoDeRetiroDTO p1, PedidoDeRetiroDTO p2)->
				(String.valueOf(p1.getCodigo()).compareToIgnoreCase(String.valueOf(p2.getCodigo())));
				
				
				reloadGrid(api.obtenerPedidosDeRetiro(comparator));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
			
		});
		rdbtn_ordenar_por_codigo_pedido.setBounds(120, 7, 21, 21);
		
		panel_ordenamientos.add(rdbtn_ordenar_por_codigo_pedido);
		
		
		lb_ordenar_por_vivienda = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.ordenamiento.por.vivienda")); //$NON-NLS-1$
		lb_ordenar_por_vivienda.setHorizontalAlignment(SwingConstants.CENTER);
		lb_ordenar_por_vivienda.setBounds(18, 35, 96, 14);
		panel_ordenamientos.add(lb_ordenar_por_vivienda);
		
		rdbtn_ordenar_por_vivienda = new JRadioButton("");
		rdbtn_ordenar_por_vivienda.addActionListener((e)->{
			rdbtn_ordenar_por_vivienda.setSelected(false);
			try {
				Comparator <PedidoDeRetiroDTO> comparator = (PedidoDeRetiroDTO p1, PedidoDeRetiroDTO p2)->
						
				
						(p1.getVivienda().getDireccion().getBarrio().toLowerCase() + " "+  
						
						p1.getVivienda().getDireccion().getCalle().toLowerCase()  +" "+  
						p1.getVivienda().getDireccion().getAltura().toLowerCase()).
	compareToIgnoreCase(
						(p2.getVivienda().getDireccion().getBarrio().toLowerCase() +" "+  
						p2.getVivienda().getDireccion().getCalle().toLowerCase() +" "+  
						p2.getVivienda().getDireccion().getAltura().toLowerCase()))		
				
						
			
				
				;
				
						
				
				
				
				reloadGrid(api.obtenerPedidosDeRetiro(comparator));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
		});
		
		rdbtn_ordenar_por_vivienda.setBounds(120, 33, 21, 21);
		panel_ordenamientos.add(rdbtn_ordenar_por_vivienda);
		
		lb_ordenar_por_codigo_vivienda = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado.ordenamiento.por.codigo.vivienda")); //$NON-NLS-1$
		lb_ordenar_por_codigo_vivienda.setHorizontalAlignment(SwingConstants.CENTER);
		lb_ordenar_por_codigo_vivienda.setBounds(18, 61, 96, 14);
		panel_ordenamientos.add(lb_ordenar_por_codigo_vivienda);
		
		rdbt_ordenar_por_codigo_vivienda = new JRadioButton("");
		rdbt_ordenar_por_codigo_vivienda.addActionListener((e)->{
			rdbt_ordenar_por_codigo_vivienda.setSelected(false);
			
			Comparator <PedidoDeRetiroDTO> comparator = (PedidoDeRetiroDTO p1, PedidoDeRetiroDTO p2)->
			String.valueOf(p1.getVivienda().getID()).compareToIgnoreCase ( String.valueOf(p2.getVivienda().getID()));
			try {
				reloadGrid(api.obtenerPedidosDeRetiro(comparator));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				
			}
			
		});
		rdbt_ordenar_por_codigo_vivienda.setBounds(120, 59, 21, 21);
		panel_ordenamientos.add(rdbt_ordenar_por_codigo_vivienda);
		
		btn_limpiar_ordenamiento = new JButton(labels.getString("listado.de.pedidos.de.retiro.label.ordenamiento.limpiar")); //$NON-NLS-1$
		btn_limpiar_ordenamiento.setBounds(49, 93, 87, 23);
		panel_ordenamientos.add(btn_limpiar_ordenamiento);
		
		lbFiltrado = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.filtrado"));
		lbFiltrado.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrado.setBounds(52, 11, 143, 14);
		panel.add(lbFiltrado);
		
		lbOrdenamiento = new JLabel(labels.getString("listado.de.pedidos.de.retiro.label.ordenamiento"));
		lbOrdenamiento.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenamiento.setBounds(52, 193, 133, 14);
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
	private void reloadGrid(List<PedidoDeRetiroDTO> pedidoDeRetiroDTO) {
	
		modelo.setRowCount(0);
		for (PedidoDeRetiroDTO p : pedidoDeRetiroDTO) {
			
			String maquinaria = "si";
			if(!p.getMaquinaPesada()) {
				maquinaria = "no";
			}
			modelo.addRow(new Object[] { 
			 		p.getFechaDelPedido(),
			 		p.getCodigo(),
			 		maquinaria,
			 		p.getVivienda().getDireccion().getBarrio()+" "+p.getVivienda().getDireccion().getCalle()+" "+p.getVivienda().getDireccion().getAltura(),
			 		p.getVivienda().getID(),
			 		p.getObservacion()
			 		
				
			});
		}

		
	}
}
