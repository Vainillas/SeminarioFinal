package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.OrdenDeRetiroDTO;
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.dto.ResiduoDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StringNullException;


import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.utilities.Predicate;

import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.JTree;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GenerarRegistroDeVisita extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JPanel panel_ordenes_de_retiro = new JPanel();
	private JScrollPane scrollPane;
	private JPanel panel_visita = new JPanel();
	private JTextPane tp_observacion;
	private JLabel lb_observacion;
	private JLabel lb_año;
	private JLabel lb_dia;
	private JSlider slider_Dinamico;
	private JLabel lb_mes;
	private JPanel panel_botones = new JPanel();;
	private JButton btn_cancelar;
	private JButton btn_registrar_visita;
	private JButton btn_limpiar;
	private ResourceBundle labels;
	private JLabel lb_cantidad_residuos;
	private ArrayList<String> ordenSeleccionada = new ArrayList<String>();
	private ArrayList<String> residuosSeleccionados = new ArrayList<String>();
	private ArrayList<String> cantResiduosRetirados = new ArrayList<String>();
	private Integer codigoOrden;
	private String descripcion;  
	private JComboBox<String> comboBoxResiduosDinamico;
	private JLabel lbResiduoSeleccionado;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
					PersistenceApi api = new PersistenceApi();
					GenerarRegistroDeVisita frame = new GenerarRegistroDeVisita(api,labels);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace(); 
				}
			}
		});
	}




	public GenerarRegistroDeVisita(IApi api, ResourceBundle labels) {

		setTitle(labels.getString("registro.de.visita.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1193, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		 panel_ordenes_de_retiro.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_ordenes_de_retiro.setBounds(550, 46, 617, 433);
		contentPane.add(panel_ordenes_de_retiro);
		panel_ordenes_de_retiro.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.controlShadow, SystemColor.controlShadow, SystemColor.controlShadow, SystemColor.controlShadow));
		panel_ordenes_de_retiro.add(scrollPane);
		String[] titulosDireccion = { 
				
					labels.getString("registro.de.visita.titulos.fecha.orden"),
					labels.getString("registro.de.visita.titulos.codigo.orden"),
					labels.getString("registro.de.visita.titulos.estado"),
					
					labels.getString("registro.de.visita.titulos.nombre.apellido.recolector"), 
					labels.getString("registro.de.visita.titulos.dni.recolector"), 
					labels.getString("registro.de.visita.titulos.codigo.pedido.de.retiro"), 
					labels.getString("registro.de.visita.titulos.descripcion.pedido"), 
					
			};	
		
		table = new JTable();
		comboBoxResiduosDinamico  = new JComboBox();
		comboBoxResiduosDinamico.addActionListener((e)->{
			
			
			try {
				PedidoDeRetiroDTO pedido = api.obtenerPedidoDeRetiro(Integer.parseInt(ordenSeleccionada.get(5)));
				this.reloadSliderGrid(pedido.getListResiduos());
			}catch (NumberFormatException | DataEmptyException | NotNullException | StringNullException
					| DateNullException | AppException e1) {
				 JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
		
			
		});
		comboBoxResiduosDinamico.setBounds(152, 40, 96, 20);
		table.addMouseListener(new MouseAdapter() {
			//ArrayList<String> ordenSeleccionada;
			public void mouseClicked(MouseEvent arg0) {
				residuosSeleccionados.clear();
				
				cantResiduosRetirados.clear();
				comboBoxResiduosDinamico.removeAllItems();
				lbResiduoSeleccionado.setVisible(true);
				comboBoxResiduosDinamico.removeAllItems();
				lb_cantidad_residuos.setVisible(true);
				int fila = table.getColumnCount();
				ordenSeleccionada = new ArrayList<String>();
				for (int i = 0; i < fila; i++) {
					ordenSeleccionada.add( (String) table.getValueAt(table.getSelectedRow(), i));
					
				}
				
				PedidoDeRetiroDTO pedido = null;
				try {
					pedido = api.obtenerPedidoDeRetiro(Integer.parseInt(ordenSeleccionada.get(5)));
					for(Residuo r : pedido.getListResiduos()) {
						comboBoxResiduosDinamico.addItem(r.getTipo().getNombre());
					}
				}catch (NumberFormatException | DataEmptyException | NotNullException | StringNullException
						| DateNullException | AppException e) {
					 JOptionPane.showMessageDialog(null, e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				}
				reloadSliderGrid(pedido.getListResiduos());
				slider_Dinamico.setEnabled(true);
				
				
				
			}
		});
		
		slider_Dinamico = new JSlider();
		slider_Dinamico.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				boolean validacion = false;
					for(int i = 0 ;i<residuosSeleccionados.size();i++) {
						String s = (String)comboBoxResiduosDinamico.getSelectedItem();
						String itemSeleccionado = residuosSeleccionados.get(i);
						if(s.equals(itemSeleccionado) ){
							cantResiduosRetirados.remove(i);
							cantResiduosRetirados.add(i,String.valueOf(slider_Dinamico.getValue()) );
							validacion = true;
						}
					}
					if(!validacion ) {
						residuosSeleccionados.add((String)comboBoxResiduosDinamico.getSelectedItem());
						cantResiduosRetirados.add(String.valueOf(slider_Dinamico.getValue()) );
					}
					
				
				System.out.println(residuosSeleccionados.toString());
				System.out.println(cantResiduosRetirados.toString());
				
				
			}
		});
		slider_Dinamico.setValue(0);
		slider_Dinamico.setMaximum(50);
		slider_Dinamico.setPaintLabels(true);
		slider_Dinamico.setPaintTicks(true);
		slider_Dinamico.setMajorTickSpacing(5);
		slider_Dinamico.setMinorTickSpacing(10);
		slider_Dinamico.setBounds(258, 40, 262, 40);
		slider_Dinamico.setEnabled(false);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		
		modelo = new DefaultTableModel(new Object[][] {}, titulosDireccion);

			try {

				List<OrdenDeRetiroDTO > ordenes = api.obtenerOrdenesDeRetiro();				

				Predicate <OrdenDeRetiroDTO> predicate = (OrdenDeRetiroDTO o)->
				!o.getEstado().obtenerEstado().equalsIgnoreCase("concretado");
				ordenes = api.obtenerOrdenesDeRetiro(predicate);				
				// Agrega las direcciones de el dueño en el model <- Incorrecto
				for (OrdenDeRetiroDTO o : ordenes) {
					//if(!o.getEstado().obtenerEstado().equalsIgnoreCase("concretado")) {
					modelo.addRow(new Object[] {
							DateHelper.changeFormat(o.getFechaOrden()),
							Integer.toString(o.getCodigo()),
						 	o.getEstado().obtenerEstado(),
							o.getRecolector().getNombre() +" "+  o.getRecolector().getApellido(),
							o.getRecolector().getDni(),
							Integer.toString(o.getPedidoAsociado().getCodigo()),
							o.getPedidoAsociado().getObservacion()
							
					});
					//}
				}

			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "error: ",JOptionPane.ERROR_MESSAGE);
				setVisible(false);
				dispose();
				
			}
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		
		
		panel_visita.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_visita.setBounds(10, 11, 530, 310);
		
		panel_visita.setLayout(null);
		panel_visita.add(slider_Dinamico);
		contentPane.add(panel_visita);
		
		tp_observacion = new JTextPane();
		tp_observacion.setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.textInactiveText, SystemColor.textInactiveText, SystemColor.textInactiveText, SystemColor.textInactiveText));
		tp_observacion.setBackground(SystemColor.info);
		tp_observacion.setBounds(64, 152, 363, 147);
		panel_visita.add(tp_observacion);
		
		lb_observacion = new JLabel(labels.getString("registro.de.visita.label.observacion"));
		lb_observacion.setHorizontalAlignment(SwingConstants.CENTER);
		lb_observacion.setBounds(32, 127, 142, 14);
		panel_visita.add(lb_observacion);
		
		lb_año = new JLabel(labels.getString("registro.de.visita.label.hora"));
		lb_año.setBounds(20, 78, 46, 14);
		panel_visita.add(lb_año);
		lb_dia = new JLabel(labels.getString("registro.de.visita.label.dia"));
		lb_dia.setBounds(20, 11, 46, 14);
		panel_visita.add(lb_dia);
		
		
		lb_mes = new JLabel(labels.getString("registro.de.visita.label.mes"));
		lb_mes.setBounds(20, 43, 46, 14);
		panel_visita.add(lb_mes);
		
		lb_cantidad_residuos = new JLabel(labels.getString("registro.de.visita.label.cantidad.residuos"));
		lb_cantidad_residuos.setHorizontalAlignment(SwingConstants.CENTER);
		lb_cantidad_residuos.setHorizontalTextPosition(SwingConstants.CENTER);
		lb_cantidad_residuos.setBounds(240, 11, 303, 14);
		lb_cantidad_residuos.setVisible(false);
		
		panel_visita.add(lb_cantidad_residuos);
		
		

		List<String> numeros = this.inicializarFecha(24);		
		SpinnerModel maximo_hora = new SpinnerListModel(numeros);
		JSpinner spinner_año = new JSpinner(maximo_hora);
		spinner_año.setModel(maximo_hora);
		panel_visita.add(spinner_año);	
		spinner_año.setModel(maximo_hora);
		spinner_año.setBounds(74, 75, 52, 20);
		panel_visita.add(spinner_año);
		
		
		
		
		
		numeros = inicializarFecha(31);
		SpinnerModel maximo_dia = new SpinnerListModel(numeros);
		JSpinner spinner_dia = new JSpinner(maximo_dia);
		spinner_dia.setModel(maximo_dia);
		panel_visita.add(spinner_dia);
		spinner_dia.setBounds(74, 8, 52, 20);
		
		
		numeros = inicializarFecha(12);
		SpinnerModel maximo_mes = new SpinnerListModel(numeros);
		JSpinner spinner_mes = new JSpinner(maximo_mes);
		spinner_mes.setModel(maximo_mes);
		spinner_mes.setBounds(76, 40, 52, 20);
		panel_visita.add(spinner_mes);
		
		
		
		panel_visita.add(comboBoxResiduosDinamico);
		
		lbResiduoSeleccionado = new JLabel("Residuo");
		lbResiduoSeleccionado.setVisible(false);
		lbResiduoSeleccionado.setHorizontalAlignment(SwingConstants.CENTER);
		lbResiduoSeleccionado.setBounds(152, 11, 96, 14);
		panel_visita.add(lbResiduoSeleccionado);
		
		panel_botones.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_botones.setBounds(10, 350, 530, 55); 
		contentPane.add(panel_botones);
		
		btn_cancelar = new JButton(labels.getString("registro.de.visita.label.cancelar"));
		btn_cancelar.addActionListener((e)->{
			
			setVisible(false);
			dispose(); 
			
			
		});
		
		btn_limpiar = new JButton(labels.getString("registro.de.visita.label.limpiar"));
		btn_limpiar.addActionListener((e)->{
			
		});
		panel_botones.add(btn_limpiar);
		
		btn_registrar_visita = new JButton(labels.getString("registro.de.visita.label.registrar.visita"));
		btn_registrar_visita.addActionListener((e)->{
			System.out.println(this.residuosSeleccionados.toString());
			try { 
				this.descripcion = this.tp_observacion.getText();
				System.out.println(table.getValueAt(table.getSelectedRow(), 1));
				this.codigoOrden = (Integer) table.getValueAt(table.getSelectedRow(), 1);
				spinner_dia.getValue();
				
					api.registrarVisita(residuosSeleccionados, cantResiduosRetirados,this.descripcion,codigoOrden,(Integer)spinner_dia.getValue(),(Integer)spinner_mes.getValue(),(Integer)spinner_año.getValue());
					
				 
				
				JOptionPane.showMessageDialog(null,"Registro de visita generado con exito!","mensaje informativo",JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				dispose();
			} catch ( AppException | NotNullException e1) {

				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
			catch(java.lang.ArrayIndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "Debe Seleccionar Un Pedido De Retiro","error",JOptionPane.ERROR_MESSAGE);
			}
		
			
		});
		
		panel_botones.add(btn_registrar_visita);
		
		panel_botones.add(btn_cancelar);
		
		JLabel lbOrdenSeleccion = new JLabel(labels.getString("registro.de.visita.label.orden.seleccion"));
		lbOrdenSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenSeleccion.setBounds(754, 11, 241, 14);
		contentPane.add(lbOrdenSeleccion);
	}
	
	
	private ArrayList<String> inicializarFecha(int max) {
		ArrayList<String> numeros = new ArrayList<String>();
		for(int i=1;i<=max;i++) {
			String v = String.valueOf(i);
			numeros.add(v);
		}
		return numeros;
		
	}




	private void reloadSliderGrid(ArrayList<Residuo> residuos) {
		for(Residuo r : residuos) {
			if(r.getTipo().getNombre().equals((String) comboBoxResiduosDinamico.getSelectedItem())) {
				slider_Dinamico.setMaximum(r.getCantidadKg());
				
				slider_Dinamico.setValue(r.getCantidadKg()/2);
			}
			
		}
	}
	
}
