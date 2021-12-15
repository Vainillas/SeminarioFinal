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
import ar.edu.unrn.seminario.utilities.NotEditJTable;
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
import javax.swing.JTextArea;
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
	private JTextArea tp_observacion;
	private JLabel lb_observacion;
	private JTextField ftfCantResiduosSeleccionados;
	private JPanel panel_botones = new JPanel();;
	private JButton btn_cancelar;
	private JButton btn_registrar_visita;
	private JButton btn_limpiar;
	private JLabel lb_cantidad_residuos;
	private ArrayList<String> ordenSeleccionada = new ArrayList<String>();
	private ArrayList<String> residuosSeleccionados = new ArrayList<String>();
	private ArrayList<String> cantResiduosRetirados = new ArrayList<String>();
	private JLabel lbResiduosSeleccionados;
	private Integer codigoOrden;
	private String descripcion;  
	private JComboBox<String> comboBoxResiduosDinamico;
	private JLabel lbResiduoSeleccionado;
	private JComboBox<String> comboBoxResiduosSeleccionados;
	private JLabel lbCantidadMaxKg;
	private JButton btnRemoverResiduo;
	private JButton btnEnviarResiduos;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("en"));
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		
		
		comboBoxResiduosDinamico  = new JComboBox<String>();
		comboBoxResiduosDinamico.setVisible(false);
		comboBoxResiduosDinamico.addActionListener((e)->{
			PedidoDeRetiroDTO pedido = null;
			codigoOrden =Integer.valueOf( (String)table.getValueAt(table.getSelectedRow(),1));

			
			try {
				pedido = api.obtenerPedidoDeRetiro(Integer.parseInt(ordenSeleccionada.get(5)));
				for(ResiduoDTO r : api.devolverResiduosRestantes(codigoOrden)) {
					
				
					if(r.getTipo().getNombre().equalsIgnoreCase((String)this.comboBoxResiduosDinamico.getSelectedItem())) {
						this.lbCantidadMaxKg.setText(labels.getString("registro.de.visita.label.cantidad.maxima")+ String.valueOf( r.getCantidadKg()));
						
					}
				
					
				}
			} catch (AppException | NumberFormatException | DataEmptyException | NotNullException | StringNullException | DateNullException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
		});
		comboBoxResiduosDinamico.setBounds(32, 40, 96, 20);
		btnEnviarResiduos = new JButton(labels.getString("registro.de.visita.label.enviar.residuos"));
		btnEnviarResiduos.addActionListener((e)->{
			if(!this.ftfCantResiduosSeleccionados.getText().equals("")) {
				
				int res = JOptionPane.showConfirmDialog(null, labels.getString("registro.de.visita.mensaje.confirmacion")+ this.ftfCantResiduosSeleccionados.getText() + " " +  labels.getString("registro.de.visita.mensaje.confirmacion2") + " "  +(String)this.comboBoxResiduosDinamico.getSelectedItem() + " ?",labels.getString("registro.de.visita.mensaje.informativo"),JOptionPane.YES_NO_OPTION);
				if(res == 0) {
					this.setVisible(true);
					this.btnRemoverResiduo.setVisible(true);
					this.comboBoxResiduosSeleccionados.addItem(this.comboBoxResiduosDinamico.getSelectedItem() + " " + this.ftfCantResiduosSeleccionados.getText()+ " kg");
					comboBoxResiduosSeleccionados.setVisible(true);
					this.lbResiduosSeleccionados.setVisible(true);
					this.residuosSeleccionados.add((String)this.comboBoxResiduosDinamico.getSelectedItem());
					this.cantResiduosRetirados.add(this.ftfCantResiduosSeleccionados.getText());
					this.comboBoxResiduosDinamico.removeItem(this.comboBoxResiduosDinamico.getSelectedItem());
					if(comboBoxResiduosDinamico.getItemCount() == 0) {
						btnEnviarResiduos.setEnabled(false);
						this.lbResiduosSeleccionados.setVisible(false);
						this.btnRemoverResiduo.setVisible(true);
					}
					
					
				}
				ftfCantResiduosSeleccionados.setText(null);
				}
			else {
				JOptionPane.showMessageDialog(null,labels.getString("registro.de.visita.mensaje.error.de.campo"),labels.getString("mensaje.error.general"),0);
			}
			
		});
		
		table = new NotEditJTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				residuosSeleccionados.clear();
				cantResiduosRetirados.clear();
				btnEnviarResiduos.setVisible(true);
				ftfCantResiduosSeleccionados.setVisible(true);
				comboBoxResiduosDinamico.removeAllItems();
				lbResiduoSeleccionado.setVisible(true);
				comboBoxResiduosDinamico.setVisible(true);
				lb_cantidad_residuos.setVisible(true);
				int fila = table.getColumnCount();
				try {
				for (int i = 0; i < fila; i++) {
					ordenSeleccionada.add( (String) table.getValueAt(table.getSelectedRow(), i));
					
				}

				PedidoDeRetiroDTO pedido = null;
					codigoOrden =Integer.valueOf( (String)table.getValueAt(table.getSelectedRow(),1));
					pedido = api.obtenerPedidoDeRetiro(Integer.parseInt(ordenSeleccionada.get(5)));
					for(ResiduoDTO r : api.devolverResiduosRestantes(codigoOrden)) {
						comboBoxResiduosDinamico.addItem(r.getTipo().getNombre());
						
					}
					comboBoxResiduosSeleccionados.removeAllItems();
					ftfCantResiduosSeleccionados.setText("");
				}catch (NumberFormatException | DataEmptyException | NotNullException | StringNullException
						| DateNullException | AppException e) {
					 JOptionPane.showMessageDialog(null, e.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
				
				
				
				
				
			}
		});
		

		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		
		modelo = new DefaultTableModel(new Object[][] {}, titulosDireccion);
			try {

				List<OrdenDeRetiroDTO > ordenes = api.obtenerOrdenesDeRetiro();				

				Predicate <OrdenDeRetiroDTO> predicate = (OrdenDeRetiroDTO o)->
                !o.getEstado().obtenerEstado().equalsIgnoreCase("concretado")
                && !o.getEstado().obtenerEstado().equalsIgnoreCase("cancelado");
				ordenes = api.obtenerOrdenesDeRetiro(predicate);	
				
				
				for (OrdenDeRetiroDTO o : ordenes) {
					modelo.addRow(new Object[] {
							DateHelper.changeFormat(o.getFechaOrden()),
							Integer.toString(o.getCodigo()),
						 	o.getEstado().obtenerEstado(),
							o.getRecolector().getNombre() +" "+  o.getRecolector().getApellido(),
							o.getRecolector().getDni(),
							Integer.toString(o.getPedidoAsociado().getCodigo()),
							o.getPedidoAsociado().getObservacion()	
					});
				}

			} catch (AppException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				setVisible(false);
				dispose();
				
			}
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		
		
		panel_visita.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_visita.setBounds(10, 11, 530, 310);
		
		panel_visita.setLayout(null);
		contentPane.add(panel_visita);
		
		tp_observacion = new JTextArea();
		tp_observacion.setLineWrap(true);
		tp_observacion.setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.textInactiveText, SystemColor.textInactiveText, SystemColor.textInactiveText, SystemColor.textInactiveText));
		tp_observacion.setBackground(SystemColor.info);
		tp_observacion.setBounds(10, 152, 363, 147);
		panel_visita.add(tp_observacion);
		
		lb_observacion = new JLabel(labels.getString("registro.de.visita.label.observacion"));
		lb_observacion.setHorizontalAlignment(SwingConstants.CENTER);
		lb_observacion.setBounds(10, 127, 142, 14);
		panel_visita.add(lb_observacion);
		
		lb_cantidad_residuos = new JLabel(labels.getString("registro.de.visita.label.cantidad.residuos"));
		lb_cantidad_residuos.setHorizontalAlignment(SwingConstants.CENTER);
		lb_cantidad_residuos.setHorizontalTextPosition(SwingConstants.CENTER);
		lb_cantidad_residuos.setBounds(108, 11, 303, 14);
		lb_cantidad_residuos.setVisible(false);
		
		panel_visita.add(lb_cantidad_residuos);
		
		

		List<String> numeros = this.inicializarFecha(2022);		
		SpinnerModel maximo_hora = new SpinnerListModel(numeros);
		
		
		
		
		
		numeros = inicializarFecha(31);
		SpinnerModel maximo_dia = new SpinnerListModel(numeros);
		
		
		numeros = inicializarFecha(12);
		SpinnerModel maximo_mes = new SpinnerListModel(numeros);
		
		panel_visita.add(comboBoxResiduosDinamico);
		
		lbResiduoSeleccionado = new JLabel(labels.getString("registro.de.visita.label.residuo"));
		lbResiduoSeleccionado.setVisible(false);
		lbResiduoSeleccionado.setHorizontalAlignment(SwingConstants.CENTER);
		lbResiduoSeleccionado.setBounds(32, 11, 96, 14);
		panel_visita.add(lbResiduoSeleccionado);
		
		ftfCantResiduosSeleccionados = new JTextField(new Integer(0));
		ftfCantResiduosSeleccionados.setText(null);
		ftfCantResiduosSeleccionados.setBounds(198, 40, 135, 20);
		ftfCantResiduosSeleccionados.setVisible(false);
		panel_visita.add(ftfCantResiduosSeleccionados);
		
		
		btnEnviarResiduos.setVisible(false);
		btnEnviarResiduos.setBounds(358, 39, 125, 23);
		panel_visita.add(btnEnviarResiduos);
		
		lbResiduosSeleccionados = new JLabel(labels.getString("registro.de.visita.label.residuos.seleccionados"));
		lbResiduosSeleccionados.setVisible(false);
		lbResiduosSeleccionados.setBounds(369, 97, 151, 14);
		panel_visita.add(lbResiduosSeleccionados);
		comboBoxResiduosSeleccionados = new JComboBox<String>();
		comboBoxResiduosSeleccionados.setVisible(false);
		comboBoxResiduosSeleccionados.setBounds(379, 127, 122, 20);
		panel_visita.add(comboBoxResiduosSeleccionados);
		
		lbCantidadMaxKg = new JLabel("");
		lbCantidadMaxKg.setBounds(30, 71, 163, 14);
		panel_visita.add(lbCantidadMaxKg);
		
		btnRemoverResiduo = new JButton(labels.getString("registro.de.visita.button.remover.residuo"));
		btnRemoverResiduo.setVisible(false);
		btnRemoverResiduo.addActionListener((e)->{
			btnEnviarResiduos.setEnabled(true);
			if(this.comboBoxResiduosSeleccionados.getItemCount() != 0) {
				try {
					this.codigoOrden = Integer.valueOf( (String)table.getValueAt(table.getSelectedRow(),5));
				PedidoDeRetiroDTO pedido = api.obtenerPedidoDeRetiro(this.codigoOrden);
				int i = 0;
				for(Residuo r : pedido.getListResiduos()) {
					String resi = (String)this.comboBoxResiduosSeleccionados.getSelectedItem();
					if(resi.contains(r.getTipo().getNombre())) {
						this.comboBoxResiduosDinamico.addItem(r.getTipo().getNombre());
						this.cantResiduosRetirados.remove(i);
						
					}
					i++;
				}
				this.comboBoxResiduosSeleccionados.removeItemAt(this.comboBoxResiduosSeleccionados.getSelectedIndex());
				
				}
				catch (NumberFormatException | DataEmptyException | NotNullException | StringNullException
						| DateNullException | AppException e1) {
					 JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		btnRemoverResiduo.setBounds(383, 164, 137, 23);
		panel_visita.add(btnRemoverResiduo);
		
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
			this.comboBoxResiduosSeleccionados.removeAllItems();
			this.ftfCantResiduosSeleccionados.setText(null);
			this.lbCantidadMaxKg.setText("");
			this.tp_observacion.setText("");
		});
		panel_botones.add(btn_limpiar);
		btn_registrar_visita = new JButton(labels.getString("registro.de.visita.label.registrar.visita"));
		btn_registrar_visita.addActionListener((e)->{
			this.descripcion = this.tp_observacion.getText();	
			if(this.comboBoxResiduosSeleccionados.getItemCount() != 0) {
				try { 
					
					this.descripcion = this.tp_observacion.getText();
						api.registrarVisita(
								residuosSeleccionados, 
								cantResiduosRetirados,
								this.descripcion,
								codigoOrden);
					JOptionPane.showMessageDialog(null,labels.getString("registro.de.visita.mensaje.de.visita.generada.con.exito"),labels.getString("registro.de.visita.mensaje.informativo"),JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
				} catch ( AppException | NotNullException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(null,labels.getString("registro.de.visita.mensaje.error.introducir.visita"),labels.getString("mensaje.error.general"),0);
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
}
