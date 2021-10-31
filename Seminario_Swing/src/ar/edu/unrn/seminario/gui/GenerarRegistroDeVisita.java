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
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;

import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JProgressBar;
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

public class GenerarRegistroDeVisita extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JPanel panel_ordenes_de_retiro;
	private JScrollPane scrollPane;
	private JPanel panel_visita;
	private JTextPane tp_observacion;
	private JLabel lb_observacion;
	private JLabel lb_hora;
	private JLabel lb_dia;
	private JSlider slider_plastico;
	private JLabel lb_slider_plastico;
	private JLabel lb_mes;
	private JSlider slider_metal;
	private JSlider slider_carton;
	private JSlider slider_vidrio;
	private JLabel lb_slider_metal;
	private JLabel lb_slider_carton;
	private JLabel lb_slider_vidrio;
	private JPanel panel_botones;
	private JButton btn_cancelar;
	private JButton btn_registrar_visita;
	private JButton btn_limpiar;
	private ResourceBundle labels;
	private JLabel lb_cantidad_residuos;



	public GenerarRegistroDeVisita(IApi api) {
		labels = ResourceBundle.getBundle("labels",new Locale("es"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1193, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		 panel_ordenes_de_retiro = new JPanel();
		 panel_ordenes_de_retiro.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_ordenes_de_retiro.setBounds(550, 46, 617, 433);
		contentPane.add(panel_ordenes_de_retiro);
		panel_ordenes_de_retiro.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.controlShadow, SystemColor.controlShadow, SystemColor.controlShadow, SystemColor.controlShadow));
		panel_ordenes_de_retiro.add(scrollPane);
		String[] titulosDireccion = { 
				/*"BARRIO","CALLE","ALTURA","CODIGO POSTAL","NOMBRE","APELLIDO","DNI"*/
				
					labels.getString("registro.de.visita.titulos.fecha.orden"),
					labels.getString("registro.de.visita.titulos.codigo.orden"),
					labels.getString("registro.de.visita.titulos.estado"),
					
					labels.getString("registro.de.visita.titulos.nombre.apellido.recolector"), 
					labels.getString("registro.de.visita.titulos.dni.recolector"), 
					labels.getString("registro.de.visita.titulos.codigo.pedido.de.retiro"), 
			};	
		
		table = new JTable();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {

				int fila = table.getColumnCount();
				
				ArrayList<String> ordenSeleccionada = new ArrayList<String>();
				for (int i = 0; i < fila; i++) {
					
					ordenSeleccionada.add( (String) table.getValueAt(table.getSelectedRow(), i));
				}
				//OrdenDeRetiroDTO orden = api.obtenerOrdenDeRetiro
				//PedidoDeRetiroDTO pedido = api.obtenerPe
				
			}
			
		});
		
		table.setRowSelectionAllowed(true);//permitiendo seleccion de fila
		table.setColumnSelectionAllowed(false);//eliminando seleccion de columnas
		
		modelo = new DefaultTableModel(new Object[][] {}, titulosDireccion);
		// Obtiene la lista de direcciones a mostrar


			try {
				List<OrdenDeRetiroDTO > ordenes = api.obtenerOrdenesDeRetiro();				
				// Agrega las direcciones de el dueño en el model
				for (OrdenDeRetiroDTO o : ordenes) {
					modelo.addRow(new Object[] {
							DateHelper.changeFormat(o.getFechaOrden()),
							Integer.toString(o.getCodigo()),
						 	o.getEstado().obtenerEstado(),
							o.getRecolector().getNombre() +" "+  o.getRecolector().getApellido(),
							o.getRecolector().getDni(),
							Integer.toString(o.getPedidoAsociado().getCodigo())
							
					});
				}

			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "error: ",JOptionPane.ERROR_MESSAGE);
				setVisible(false);
				dispose();
				
			}
		
		
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		
		panel_visita = new JPanel();
		panel_visita.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_visita.setBounds(10, 11, 530, 388);
		contentPane.add(panel_visita);
		panel_visita.setLayout(null);
		
		tp_observacion = new JTextPane();
		tp_observacion.setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.textInactiveText, SystemColor.textInactiveText, SystemColor.textInactiveText, SystemColor.textInactiveText));
		tp_observacion.setBackground(SystemColor.info);
		tp_observacion.setBounds(10, 152, 184, 225);
		panel_visita.add(tp_observacion);
		
		lb_observacion = new JLabel(labels.getString("registro.de.visita.label.observacion"));
		lb_observacion.setBounds(80, 127, 46, 14);
		panel_visita.add(lb_observacion);
		
		lb_hora = new JLabel(labels.getString("registro.de.visita.label.hora"));
		lb_hora.setBounds(10, 11, 46, 14);
		panel_visita.add(lb_hora);
		
		 lb_dia = new JLabel(labels.getString("registro.de.visita.label.dia"));
		lb_dia.setBounds(10, 48, 46, 14);
		panel_visita.add(lb_dia);
		
		slider_plastico = new JSlider();
		slider_plastico.setValue(25);
		slider_plastico.setMaximum(50);
		slider_plastico.setPaintLabels(true);
		slider_plastico.setPaintTicks(true);
		slider_plastico.setMajorTickSpacing(5);
		slider_plastico.setMinorTickSpacing(10);
		slider_plastico.setBounds(258, 40, 262, 40);
		panel_visita.add(slider_plastico);
		panel_visita.enableInputMethods(false);
		lb_slider_plastico = new JLabel(labels.getString("registro.de.visita.label.plastico"));
		lb_slider_plastico.setHorizontalAlignment(SwingConstants.TRAILING);
		lb_slider_plastico.setHorizontalTextPosition(SwingConstants.LEADING);
		lb_slider_plastico.setBounds(176, 60, 70, 14);
		panel_visita.add(lb_slider_plastico);
		
		lb_mes = new JLabel(labels.getString("registro.de.visita.label.mes"));
		lb_mes.setBounds(10, 78, 46, 14);
		panel_visita.add(lb_mes);
		
		slider_metal = new JSlider();
		slider_metal.setValue(25);
		slider_metal.setMaximum(50);
		slider_metal.setPaintTicks(true);
		slider_metal.setPaintLabels(true);
		slider_metal.setMinorTickSpacing(10);
		slider_metal.setMajorTickSpacing(5);
		slider_metal.setBounds(258, 95, 262, 40);
		panel_visita.add(slider_metal);
		
		slider_carton = new JSlider();
		slider_carton.setValue(25);
		slider_carton.setMaximum(50);
		slider_carton.setMajorTickSpacing(5);
		slider_carton.setPaintTicks(true);
		slider_carton.setPaintLabels(true);
		slider_carton.setBounds(258, 150, 262, 40);
		panel_visita.add(slider_carton);
		
		slider_vidrio = new JSlider();
		slider_vidrio.setValue(25);
		slider_vidrio.setMajorTickSpacing(5);
		slider_vidrio.setPaintLabels(true);
		slider_vidrio.setPaintTicks(true);
		slider_vidrio.setMaximum(50);
		slider_vidrio.setBounds(258, 205, 262, 40);
		panel_visita.add(slider_vidrio);
		
		lb_slider_metal = new JLabel(labels.getString("registro.de.visita.label.metal"));
		lb_slider_metal.setHorizontalAlignment(SwingConstants.TRAILING);
		lb_slider_metal.setBounds(200, 110, 46, 14);
		panel_visita.add(lb_slider_metal);
		
		lb_slider_carton = new JLabel(labels.getString("registro.de.visita.label.carton"));
		lb_slider_carton.setHorizontalAlignment(SwingConstants.TRAILING);
		lb_slider_carton.setBounds(200, 160, 46, 14);
		panel_visita.add(lb_slider_carton);
		
		lb_slider_vidrio = new JLabel(labels.getString("registro.de.visita.label.vidro"));
		lb_slider_vidrio.setHorizontalAlignment(SwingConstants.TRAILING);
		lb_slider_vidrio.setBounds(200, 215, 46, 14);
		panel_visita.add(lb_slider_vidrio);
		
		lb_cantidad_residuos = new JLabel(labels.getString("registro.de.visita.label.cantidad.residuos"));
		lb_cantidad_residuos.setHorizontalAlignment(SwingConstants.CENTER);
		lb_cantidad_residuos.setHorizontalTextPosition(SwingConstants.CENTER);
		lb_cantidad_residuos.setBounds(217, 11, 303, 14);
		panel_visita.add(lb_cantidad_residuos);
		
		JSpinner spinner_hora = new JSpinner();
		SpinnerNumberModel maximo_hora = new SpinnerNumberModel();
		maximo_hora.setMaximum(24);
		maximo_hora.setMinimum(0);
		spinner_hora.setModel(maximo_hora);
		spinner_hora.setBounds(74, 8, 52, 20);
		panel_visita.add(spinner_hora);
		
		
		JSpinner spinner_dia = new JSpinner();
		spinner_dia.setBounds(74, 45, 52, 20);
		
		panel_visita.add(spinner_dia);
		SpinnerNumberModel maximo_dia = new SpinnerNumberModel();
		maximo_dia.setMaximum(31);
		maximo_dia.setMinimum(0);
		spinner_dia.setModel(maximo_dia);
		
		final String [] numeros = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		SpinnerModel maximo_mes = new SpinnerListModel(numeros);
		JSpinner spinner_mes = new JSpinner(maximo_mes);
		spinner_mes.setModel(maximo_mes);
		spinner_mes.setBounds(74, 75, 52, 20);
		panel_visita.add(spinner_mes);
		panel_botones = new JPanel();
		panel_botones.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_botones.setBounds(10, 409, 530, 55);
		contentPane.add(panel_botones);
		
		btn_cancelar = new JButton(labels.getString("registro.de.visita.label.cancelar"));
		btn_cancelar.addActionListener((e)->{
			//JOptionPane.showMessageDialog(null,"adios","mensaje informativo",JOptionPane.INFORMATION_MESSAGE);
			
			setVisible(false);
			dispose();
			
			
		});
		
		btn_limpiar = new JButton(labels.getString("registro.de.visita.label.limpiar"));
		btn_limpiar.addActionListener((e)->{
			
		});
		panel_botones.add(btn_limpiar);
		
		btn_registrar_visita = new JButton(labels.getString("registro.de.visita.label.registrar.visita"));
		btn_registrar_visita.addActionListener((e)->{
			//api.generar
			
			//JOptionPane.showMessageDialog(null, e.getMessage(),"Registro visita",JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			dispose();
		});
		panel_botones.add(btn_registrar_visita);
		
		panel_botones.add(btn_cancelar);
		
		JLabel lbOrdenSeleccion = new JLabel(labels.getString("registro.de.visita.label.orden.seleccion"));
		lbOrdenSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenSeleccion.setBounds(754, 11, 241, 14);
		contentPane.add(lbOrdenSeleccion);
	}
	private void reloadGrid() {
		
		
	}
}
