package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;

import java.awt.Font;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;


public class GenerarPedidoDeRetiroDinamico extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7736390012295310754L;
	private JPanel contentPane;
	private DefaultTableModel modelo;
	private JPanel panelBotones;
	private JButton buttonCancelar;
	private JButton buttonFinalizar;
	private JPanel panelResiduos;
	private ArrayList<String> domicilioSeleccionado = null;
	private JTextField textFieldCodViv;
	private JLabel lbResiduos;
	private JPanel panelViviendas;
	private JTable table;
	private JScrollPane scrollPane;
	private JFormattedTextField ftfKg;
	private JLabel lblNewLabel;
	private JLabel lbTiposDeResiduos;
	private JLabel lbCantidadKg;
	private ArrayList<String > residuosSeleccionados = new ArrayList<String>();
	private ArrayList<String > kgSeleccionados = new ArrayList<String>();
	public static void main(String [] args) {
		PersistenceApi api = new PersistenceApi();
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es"));
		GenerarPedidoDeRetiroDinamico p = new GenerarPedidoDeRetiroDinamico(api,labels );
		p.setVisible(true);
	}
	
	public GenerarPedidoDeRetiroDinamico(IApi api, ResourceBundle labels) {

		setTitle(labels.getString("pedido.retiro.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1076, 368);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.info);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null); 
		setContentPane(contentPane);
		
		
		
		
		
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		JPanel panelDatos = new JPanel();
		panelDatos.setBackground(SystemColor.info);
		panelDatos.setLayout(null);
		panelDatos.setBounds(10, 11, 325, 209);
		contentPane.add(panelDatos);
		
		JLabel labelVehiculo = new JLabel(labels.getString("pedido.retiro.label.vehiculo"));
		labelVehiculo.setForeground(SystemColor.activeCaptionText);
		labelVehiculo.setBackground(SystemColor.desktop);
		labelVehiculo.setBounds(10, 14, 221, 14);
		panelDatos.add(labelVehiculo);
		
		JLabel labelObservacion = new JLabel(labels.getString("pedido.retiro.label.observacion"));
		labelObservacion.setBounds(10, 65, 175, 14);
		panelDatos.add(labelObservacion);
		
		JCheckBox boxCargaPesada = new JCheckBox(labels.getString("pedido.retiro.check.box.si.no"));
		boxCargaPesada.setBackground(UIManager.getColor("window"));
		boxCargaPesada.setBounds(224, 10, 81, 23);
		panelDatos.add(boxCargaPesada);
		
		JTextArea taObservacion = new JTextArea();
		taObservacion.setBounds(10, 90, 295, 108);
		panelDatos.add(taObservacion);
		

		panelBotones = new JPanel();
		panelBotones.setBackground(SystemColor.info);
		panelBotones.setBounds(10, 235, 358, 46);
		contentPane.add(panelBotones);
		
		buttonCancelar = new JButton(labels.getString("pedido.retiro.button.cancelar"));

		buttonCancelar.addActionListener((e)->{
				setVisible(false);
				dispose();

		});
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelBotones.add(buttonCancelar);

		
		panelResiduos = new JPanel();
		panelResiduos.setBackground(SystemColor.info);

		panelResiduos.setBounds(336, 11, 293, 175);

	

		contentPane.add(panelResiduos);
		panelResiduos.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 9));
		textPane.setEditable(false);
		textPane.setBounds(6, 176, 80, 10);
		panelResiduos.add(textPane);
		
		textFieldCodViv = new JTextField();
		textFieldCodViv.setBounds(103, 176, 86, 20);
		panelResiduos.add(textFieldCodViv);
		textFieldCodViv.setColumns(10);
		
		buttonFinalizar = new JButton(labels.getString("pedido.retiro.button.finalizar"));
		panelBotones.add(buttonFinalizar);
		buttonFinalizar.addActionListener((e)->{

					try {
						api.generarPedidoDeRetiro(boxCargaPesada.isSelected(), this.residuosSeleccionados,  this.kgSeleccionados ,taObservacion.getText() ,domicilioSeleccionado);
						
						JOptionPane.showMessageDialog(null, labels.getString("pedido.retiro.mensaje.exito"), labels.getString("pedido.retiro.mensaje.informativo"), JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						dispose();
						
					} catch (AppException | DataEmptyException | NotNullException 
							| StringNullException| DateNullException | NumberFormatException | KilogramEmptyException | NotNumberException e1) {

						JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);

					}
		});

		 lbResiduos = new JLabel(labels.getString("pedido.retiro.label.residuos")); 
		 lbResiduos.setHorizontalAlignment(SwingConstants.CENTER);
		lbResiduos.setBounds(6, 11, 263, 14);
		panelResiduos.add(lbResiduos);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(8, 95, 78, 22);
		try {
			List<String> residuos = api.obtenerNombresResiduos();
			for(String r : residuos) {
				comboBox.addItem(r);
			}
		} catch (AppException e1) {
			JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		
		panelResiduos.add(comboBox);
		
		ftfKg = new JFormattedTextField(Integer.valueOf(0));
		ftfKg.setValue(null);
		ftfKg.setBounds(125, 96, 64, 21);
		JButton btnEnviarKg = new JButton(labels.getString("pedido.retiro.button.enviar.kg")); 
		btnEnviarKg.setBounds(199, 95, 89, 23);
		panelResiduos.add(btnEnviarKg);
		
		panelResiduos.add(ftfKg);
		btnEnviarKg.addActionListener((e)->{
			if(!this.ftfKg.getText().equals("")) {
				int res = JOptionPane.showConfirmDialog(null,("seguro que desea seleccionar "+ftfKg.getText()+" kg de "+ String.valueOf(comboBox.getSelectedItem()).toLowerCase())+"?","",JOptionPane.YES_NO_OPTION);
				if(res == 0) {
					
					this.residuosSeleccionados.add((String)comboBox.getSelectedItem());
					this.kgSeleccionados.add(String.valueOf(ftfKg.getValue()));
					comboBox.removeItem(comboBox.getSelectedItem());
					
				}
					ftfKg.setValue(null);
				if(comboBox.getItemCount() == 0) {
					btnEnviarKg.setEnabled(false);
					ftfKg.setEnabled(false);
					comboBox.setEnabled(false);
				}
			}
			else {
				JOptionPane.showMessageDialog(null,labels.getString("pedido.retiro.mensaje.error"),"Error",0);
			}
			
			
		});
		
		lblNewLabel = new JLabel(labels.getString("pedido.retiro.label.residuos.2")); //$NON-NLS-1$
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 25, 254, 14);
		panelResiduos.add(lblNewLabel);
		
		lbTiposDeResiduos = new JLabel(labels.getString("pedido.retiro.label.tipo.residuo")); //$NON-NLS-1$
		lbTiposDeResiduos.setHorizontalAlignment(SwingConstants.CENTER);
		lbTiposDeResiduos.setBounds(0, 64, 116, 20);
		panelResiduos.add(lbTiposDeResiduos);
		
		lbCantidadKg = new JLabel(labels.getString("pedido.retiro.label.kg")); //$NON-NLS-1$
		lbCantidadKg.setHorizontalAlignment(SwingConstants.CENTER);
		lbCantidadKg.setBounds(103, 67, 111, 14);
		panelResiduos.add(lbCantidadKg);
		
		
		
		panelViviendas = new JPanel();
		panelViviendas.setBounds(639, 11, 411, 307);
		
		contentPane.add(panelViviendas);
		String[] titulosDireccion = { 
				
			labels.getString("pedido.retiro.titulos.direccion.barrio"),
				labels.getString("pedido.retiro.titulos.direccion.calle"),  
				labels.getString("pedido.retiro.titulos.direccion.altura"), 
				labels.getString("pedido.retiro.titulos.direccion.codigo.postal"), 
				labels.getString("pedido.retiro.titulos.dueño.nombre"), 
				labels.getString("pedido.retiro.titulos.dueño.apellido"), 
				labels.getString("pedido.retiro.titulos.dueño.dni"), 
		};	
	
		panelViviendas.setLayout(new BorderLayout(0, 0));
		scrollPane = new JScrollPane();
		panelViviendas.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {

				int fila = table.getColumnCount();
				
				domicilioSeleccionado = new ArrayList<String>();
				for (int i = 0; i < fila; i++) {
					domicilioSeleccionado.add( (String) table.getValueAt(table.getSelectedRow(), i));
					
				}
				
				habilitarBotones(true);
			}
		});
		table.setRowSelectionAllowed(true);//permitiendo seleccion de fila
		table.setColumnSelectionAllowed(false);//eliminando seleccion de columnas
		
		modelo = new DefaultTableModel(new Object[][] {}, titulosDireccion);
		
		// Obtiene la lista de direcciones a mostrar
				List<ViviendaDTO> viviendas= new ArrayList<ViviendaDTO>();	
		
					try {
						if(api.obtenerRolUsuarioActivo().equals("ADMIN")) {
						viviendas = api.obtenerViviendas();
						}
						else {
						viviendas = api.obtenerViviendasDeUsuario();
						}
						// Agrega las direcciones de el dueño en el model
						for (ViviendaDTO d : viviendas) {
							modelo.addRow(new Object[] { d.getDireccion().getBarrio(), 
									d.getDireccion().getCalle(), 
									d.getDireccion().getAltura(),
									d.getDireccion().getCodPostal(), 
									d.getDueño().getNombre(),
									d.getDueño().getApellido(),
									d.getDueño().getDni()
							});
							
						}

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage(), "error: ",JOptionPane.ERROR_MESSAGE);
						setVisible(false);
						dispose();
						
					}
				
				
				table.setModel(modelo);
				scrollPane.setViewportView(table);
				
		
}
	private void habilitarBotones(boolean b) {
		//

}
}