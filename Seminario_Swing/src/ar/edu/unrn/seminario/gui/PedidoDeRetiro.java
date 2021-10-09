package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import ar.edu.unrn.seminario.dto.DireccionDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exceptions.AppException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.JTree;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.DropMode;
import javax.swing.JSlider;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import java.awt.Canvas;
import javax.swing.JScrollPane;

import java.awt.Font;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;


public class PedidoDeRetiro extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7736390012295310754L;
	private JPanel contentPane;
	private Date fechaActual = DateHelper.getDate();
	private IApi api = null;
	DefaultTableModel modelo;
	private JPanel panelBotones;
	private JButton buttonCancelar;
	private JButton buttonFinalizar;
	private JPanel panelResiduos;
	private JTextField textField_Vidrio;
	private JTextField textField_Plastico;
	private JTextField textField_Metal;
	private JTextField textField_Carton;

	private JTextField textFieldCodViv;


	private JLabel lbKg;
	private JLabel lbResiduos;
	private int mostrarKG = 0;
	private JPanel panelViviendas;
	private JTable table;







	public PedidoDeRetiro(IApi api) {
		this.api = api;
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es"));
		
		setTitle(labels.getString("pedido.retiro.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 368);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.info);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		
		
		
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		JPanel panelDatos = new JPanel();
		panelDatos.setBackground(SystemColor.info);
		panelDatos.setLayout(null);
		panelDatos.setBounds(10, 11, 337, 196);
		contentPane.add(panelDatos);
		
		JLabel labelVehiculo = new JLabel(labels.getString("pedido.retiro.label.vehiculo"));
		labelVehiculo.setForeground(SystemColor.activeCaptionText);
		labelVehiculo.setBackground(SystemColor.desktop);
		labelVehiculo.setBounds(10, 14, 221, 14);
		panelDatos.add(labelVehiculo);
		
		JLabel labelObservacion = new JLabel(labels.getString("pedido.retiro.label.observacion"));
		labelObservacion.setBounds(10, 65, 175, 14);
		panelDatos.add(labelObservacion);
		
		JTextPane textObservacion = new JTextPane();
		textObservacion.setForeground(Color.BLACK);
		textObservacion.setBackground(SystemColor.textHighlightText);
		textObservacion.setBounds(88, 90, 249, 106);
		panelDatos.add(textObservacion);
		
		JCheckBox boxCargaPesada = new JCheckBox(labels.getString("pedido.retiro.check.box.si.no"));
		boxCargaPesada.setBackground(UIManager.getColor("window"));
		boxCargaPesada.setBounds(224, 10, 81, 23);
		panelDatos.add(boxCargaPesada);
		

		panelBotones = new JPanel();
		panelBotones.setBackground(SystemColor.info);
		panelBotones.setBounds(10, 205, 358, 60);
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

		panelResiduos.setBounds(357, 11, 252, 175);

	

		contentPane.add(panelResiduos);
		panelResiduos.setLayout(null);
		

		JRadioButton radioButtonVidrio = new JRadioButton(labels.getString("pedido.retiro.radio.button.vidrio"));
		radioButtonVidrio.addActionListener((e)-> {
			
			cambiarVisibilidad(radioButtonVidrio);
			
		});
			
		radioButtonVidrio.setBackground(UIManager.getColor("window"));
		radioButtonVidrio.setBounds(6, 50, 81, 23);
		panelResiduos.add(radioButtonVidrio);

		
		JRadioButton radioButtonPlastico = new JRadioButton(labels.getString("pedido.retiro.radio.button.plastico"));
		radioButtonPlastico.addActionListener((e)-> {
			
			cambiarVisibilidad(radioButtonPlastico);
				
		});
	
		radioButtonPlastico.setBackground(UIManager.getColor("window"));
		radioButtonPlastico.setBounds(6, 80, 81, 23);
		panelResiduos.add(radioButtonPlastico);


		JRadioButton radioButtonMetal = new JRadioButton(labels.getString("pedido.retiro.radio.button.metal"));
		radioButtonMetal.addActionListener((e)-> {
			
			cambiarVisibilidad(radioButtonMetal);
	
		});
		radioButtonMetal.setBackground(UIManager.getColor("window"));
		radioButtonMetal.setBounds(6, 110, 81, 23);
		panelResiduos.add(radioButtonMetal);
		
		JRadioButton radioButtonCarton = new JRadioButton(labels.getString("pedido.retiro.radio.button.carton"));
		radioButtonCarton.addActionListener((e)-> {
			
			cambiarVisibilidad(radioButtonCarton);
			
		});
		
		radioButtonCarton.setBackground(UIManager.getColor("window"));
		radioButtonCarton.setBounds(6, 140, 81, 23);
		panelResiduos.add(radioButtonCarton);
		

		
		
		
		textField_Vidrio = new JTextField("0");
		textField_Vidrio.setVisible(false);
		textField_Vidrio.setBounds(130, 51, 86, 20);
		panelResiduos.add(textField_Vidrio);
		textField_Vidrio.setColumns(10);
		
		textField_Plastico = new JTextField("0");
		textField_Plastico.setBounds(130, 81, 86, 20);
		textField_Plastico.setVisible(false);
		panelResiduos.add(textField_Plastico);
		textField_Plastico.setColumns(10);
		
		textField_Metal = new JTextField("0");
	
		textField_Metal.setBounds(130, 111, 86, 20);
		textField_Metal.setVisible(false);
		panelResiduos.add(textField_Metal);
		textField_Metal.setColumns(10);
		
		textField_Carton = new JTextField("0");
		textField_Carton.setVisible(false);
		textField_Carton.setBounds(130, 141, 86, 20);
		panelResiduos.add(textField_Carton);
		textField_Carton.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 9));
		textPane.setEditable(false);
		textPane.setText("Codigo Vivienda"); //$NON-NLS-1$
		textPane.setBounds(6, 176, 91, 20);
		panelResiduos.add(textPane);
		
		textFieldCodViv = new JTextField();
		textFieldCodViv.setBounds(103, 176, 86, 20);
		panelResiduos.add(textFieldCodViv);
		textFieldCodViv.setColumns(10);
		
		JRadioButton [] residuos= {radioButtonVidrio, radioButtonPlastico,radioButtonMetal,radioButtonCarton};
		JTextField [] residuosKg = {textField_Vidrio,textField_Plastico,textField_Metal,textField_Carton};
		
		buttonFinalizar = new JButton(labels.getString("pedido.retiro.button.finalizar"));

		buttonFinalizar.setBounds(120, 11, 89, 23);
		panelBotones.add(buttonFinalizar);
		buttonFinalizar.addActionListener((e)->{
			
				ArrayList <String> residuosSeleccionados = new ArrayList<String>();
				//ArrayList <String> residuosSeleccionadosKg = new ArrayList<String>();
				
				 for(JRadioButton r : residuos) {
	                    if(r.isSelected()) {
	                        residuosSeleccionados.add(r.getText());
	                }
				 }
	                ArrayList <String> residuosSeleccionadosKg = new ArrayList<String>();
	                for(JTextField kg : residuosKg) {
	                    if(!kg.getText().equals("0")) {
	                        residuosSeleccionadosKg.add(kg.getText());
	                    }
	                }
				try {
					api.generarPedidoDeRetiro(boxCargaPesada.isSelected(), residuosSeleccionados,  residuosSeleccionadosKg , textObservacion.getText(), textFieldCodViv.getText());
					//api.generarPedidoDeRetiro(boxCargaPesada.isSelected(), residuosSeleccionados,  residuosSeleccionadosKg , textObservacion.getText());
					JOptionPane.showMessageDialog(null, labels.getString("pedido.retiro.mensaje.exito"), labels.getString("pedido.retiro.mensaje.informativo"), JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("crear la exception");
				}

				
				 
		
		});

		 lbResiduos = new JLabel(labels.getString("pedido.retiro.label.residuos")); 
		lbResiduos.setBounds(6, 11, 95, 14);
		panelResiduos.add(lbResiduos);
		
		 lbKg = new JLabel(labels.getString("pedido.retiro.label.residuos.kg"));
		lbKg.setVisible(false);
		lbKg.setBounds(128, 11, 124, 14);
		panelResiduos.add(lbKg);
		
		panelViviendas = new JPanel();
		panelViviendas.setBounds(619, 11, 315, 307);
		contentPane.add(panelViviendas);
		String[] titulosDireccion = { 
				labels.getString("pedido.retiro.titulos.direccion.calle"),  
				labels.getString("pedido.retiro.titulos.direccion.altura"), 
				labels.getString("pedido.retiro.titulos.direccion.codigo.postal"), 
				labels.getString("pedido.retiro.titulos.direccion.latitud"), 
				labels.getString("pedido.retiro.titulos.direccion.longitud"), 
				labels.getString("pedido.retiro.titulos.direccion.barrio") };
		
		table = new JTable();
		panelViviendas.add(table);
		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent arg0) {
				habilitarBotones(true);
			}
		});
		
		modelo = new DefaultTableModel(new Object[][] {}, titulosDireccion);
		
		// Obtiene la lista de direcciones a mostrar
				List<DireccionDTO> direcciones= new ArrayList<DireccionDTO>();
		
		
					try {
						direcciones = api.obtenerDirecciones();
						// Agrega las direcciones de el dueño en el model
						for (DireccionDTO d : direcciones) {
							modelo.addRow(new Object[] { d.getBarrio(), d.getCalle(), d.getAltura(),
									d.getCodPostal(), d.getLatitud(),d.getLongitud() });
							
						}

					} catch (AppException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage(), "error: ",JOptionPane.ERROR_MESSAGE);
						setVisible(false);
						dispose();
						
					}
				
				
				table.setModel(modelo);
		
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
		JTextField [] listaTextos = {textField_Vidrio, textField_Plastico,textField_Metal, textField_Carton};
		String [] nombresTextos = {"Vidrio","Plastico","Metal", "Carton"};
		boolean validacion = true;int i = 0;
		
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
		}
		
		
	}
	private void habilitarBotones(boolean b) {
		//
	}
}