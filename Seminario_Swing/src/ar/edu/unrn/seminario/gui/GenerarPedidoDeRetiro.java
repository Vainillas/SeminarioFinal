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


public class GenerarPedidoDeRetiro extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7736390012295310754L;
	private JPanel contentPane;
	private Date fechaActual = DateHelper.getDate();
	private IApi api = null;
	private DefaultTableModel modelo;
	private JPanel panelBotones;
	private JButton buttonCancelar;
	private JButton buttonFinalizar;
	private JPanel panelResiduos;
	private ArrayList<String> domicilioSeleccionado = null;
	private JTextField textFieldCodViv;
	private JLabel lbResiduos;
	private int mostrarKG = 0;
	private JPanel panelViviendas;
	private JTable table;
	private JScrollPane scrollPane;
	private JFormattedTextField formattedTextField;
	private JLabel lbKg;
	public static void main(String [] args) {
		PersistenceApi api = new PersistenceApi();
		GenerarPedidoDeRetiro p = new GenerarPedidoDeRetiro(api);
		p.setVisible(true);
	}
	
	public GenerarPedidoDeRetiro(IApi api) {
		this.api = api;
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es"));
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
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 90, 295, 108);
		panelDatos.add(textArea);
		

		panelBotones = new JPanel();
		panelBotones.setBackground(SystemColor.info);
		panelBotones.setBounds(20, 220, 358, 60);
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		
		buttonCancelar = new JButton(labels.getString("pedido.retiro.button.cancelar"));
		buttonCancelar.addActionListener((e)->{
				
				int res = JOptionPane.showConfirmDialog(null, labels.getString("pedido.retiro.confirmar"),labels.getString("pedido.retiro.mensaje.informativo") , JOptionPane.YES_NO_OPTION);
				if(res == JOptionPane.YES_OPTION) {
					
					setVisible(false);
					dispose();
				}
		});
		buttonCancelar.setBounds(259, 11, 89, 23);
		panelBotones.add(buttonCancelar);

		
		panelResiduos = new JPanel();
		panelResiduos.setBackground(SystemColor.info);

		panelResiduos.setBounds(345, 11, 284, 175);

	

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
		
		//JRadioButton [] residuos= {radioButtonVidrio, radioButtonPlastico,radioButtonMetal,radioButtonCarton};
		//JTextField [] residuosKg = {tfCantResiduoDinamico,textField_Plastico,textField_Metal,textField_Carton};
		
		buttonFinalizar = new JButton(labels.getString("pedido.retiro.button.finalizar"));

		buttonFinalizar.setBounds(120, 11, 89, 23);
		panelBotones.add(buttonFinalizar);
		buttonFinalizar.addActionListener((e)->{
				//System.out.println(textField_Plastico.getText());
				
				 
	           
	               
	                 
					try {
						api.generarPedidoDeRetiro(boxCargaPesada.isSelected(), null,  null ,null ,domicilioSeleccionado);
						
						JOptionPane.showMessageDialog(null, labels.getString("pedido.retiro.mensaje.exito"), labels.getString("pedido.retiro.mensaje.informativo"), JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						dispose();
						
					} catch (AppException | DataEmptyException | NotNullException 
							| StringNullException| DateNullException | NumberFormatException | KilogramEmptyException | NotNumberException e1) {
						
						
						JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
						
					}
					
				
		});

		 lbResiduos = new JLabel(labels.getString("pedido.retiro.label.residuos")); 
		lbResiduos.setBounds(0, 11, 115, 14);
		panelResiduos.add(lbResiduos);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(6, 50, 78, 22);
		panelResiduos.add(comboBox);
		comboBox.addItem("Plastico");
		comboBox.addItem("Metal");
		comboBox.addItem("Carton");
		comboBox.addItem("Vidrio");
		
		formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(116, 51, 64, 21);
		panelResiduos.add(formattedTextField);
		
		lbKg = new JLabel(labels.getString("pedido.retiro.label.residuos.kg")); 
		lbKg.setBounds(113, 11, 139, 17);
		panelResiduos.add(lbKg);
		
		JRadioButton rdbtnEnviarKg = new JRadioButton(labels.getString("pedido.retiro.button.aceptar.kg")); //$NON-NLS-1$
		rdbtnEnviarKg.setBackground(SystemColor.info);
		rdbtnEnviarKg.setBounds(186, 50, 92, 23);
		panelResiduos.add(rdbtnEnviarKg);
		
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
						viviendas = api.obtenerViviendas();
						
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
	
	private void cambiarVisibilidadKG() {
		if(this.mostrarKG == 0) {
			lbKg.setVisible(false);
		}
		else {
			lbKg.setVisible(true);
		}
	}
	
	private void cambiarVisibilidad(JRadioButton button) {
		/*JTextField [] listaTextos = {tfCantResiduoDinamico, textField_Plastico,textField_Metal, textField_Carton};
		String [] nombresTextos = {"Vidrio","Plástico","Metal", "Cartón"};
		boolean validacion = true;
		int i = 0;
		while(i < listaTextos.length && validacion) {
			if(button.getText().equals(nombresTextos[i])) {
				if(button.isSelected()) {
					listaTextos[i].setVisible(true);
					this.mostrarKG++;
				}
				else {
					listaTextos[i].setVisible(false);
					this.mostrarKG--;
					
				}
			cambiarVisibilidadKG();
			validacion = false;
				
			}
		i++;
		}*/
		
		
	}
	private void habilitarBotones(boolean b) {
		//

}
}