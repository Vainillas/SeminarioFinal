package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.api.IApi;
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

public class PedidoDeRetiro extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7736390012295310754L;
	private JPanel contentPane;
	private Date fechaActual = DateHelper.getDate();
	private IApi api = null;
	private JPanel panelBotones;
	private JButton buttonCancelar;
	private JButton buttonFinalizar;
	private JPanel panelResiduos;
	private JTextField textField_Vidrio;
	private JTextField textField_Plastico;
	private JTextField textField_Metal;
	private JTextField textField_Carton;







	public PedidoDeRetiro(IApi api) {
		this.api = api;
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es"));
		
		setTitle(labels.getString("pedido.retiro.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.info);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		
		
		
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		JPanel panelDatos = new JPanel();
		panelDatos.setBackground(SystemColor.info);
		panelDatos.setLayout(null);
		panelDatos.setBounds(10, 11, 414, 196);
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
		textObservacion.setBounds(88, 90, 326, 106);
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
		panelResiduos.setBounds(426, 11, 210, 196);
		contentPane.add(panelResiduos);
		panelResiduos.setLayout(null);
		
		JRadioButton radioButtonVidrio = new JRadioButton(labels.getString("pedido.retiro.radio.button.vidrio"));
		radioButtonVidrio.addActionListener((e)-> {
					textField_Vidrio.setVisible(true);
			
		});
			
		radioButtonVidrio.setBackground(UIManager.getColor("window"));
		radioButtonVidrio.setBounds(6, 50, 81, 23);
		panelResiduos.add(radioButtonVidrio);

		
		JRadioButton radioButtonPlastico = new JRadioButton(labels.getString("pedido.retiro.radio.button.plastico"));
		radioButtonPlastico.addActionListener((e)-> {
				textField_Plastico.setVisible(true);
			
		});
	
		radioButtonPlastico.setBackground(UIManager.getColor("window"));
		radioButtonPlastico.setBounds(6, 80, 81, 23);
		panelResiduos.add(radioButtonPlastico);


		JRadioButton radioButtonMetal = new JRadioButton(labels.getString("pedido.retiro.radio.button.metal"));
		radioButtonMetal.addActionListener((e)-> {
			textField_Metal.setVisible(true);
			
		});
		radioButtonMetal.setBackground(UIManager.getColor("window"));
		radioButtonMetal.setBounds(6, 110, 81, 23);
		panelResiduos.add(radioButtonMetal);
		
		JRadioButton radioButtonCarton = new JRadioButton(labels.getString("pedido.retiro.radio.button.carton"));
		radioButtonCarton.addActionListener((e)-> {
			textField_Carton.setVisible(true);
			
		});
		
		
		radioButtonCarton.setBackground(UIManager.getColor("window"));
		radioButtonCarton.setBounds(6, 140, 81, 23);
		panelResiduos.add(radioButtonCarton);
		

		
		JRadioButton [] residuos= {radioButtonVidrio, radioButtonPlastico,radioButtonMetal,radioButtonCarton};
		JTextField [] residuosKg = {textField_Vidrio,textField_Plastico,textField_Metal,textField_Carton};
		buttonFinalizar = new JButton(labels.getString("pedido.retiro.button.finalizar"));

		buttonFinalizar.setBounds(120, 11, 89, 23);
		panelBotones.add(buttonFinalizar);
		buttonFinalizar.addActionListener((e)->{
			
				ArrayList <String> residuosSeleccionados = new ArrayList<String>();
				for(JRadioButton r : residuos) {
					if(r.isSelected()) {
						residuosSeleccionados.add(r.getText());
				}
				ArrayList <String> residuosSeleccionadosKg = new ArrayList<String>();
				for(JTextField kg : residuosKg) {
					if(!kg.getText().equals("0")) {
						residuosSeleccionadosKg.add(kg.getText());
					}
				}
				try {
					//api.generarPedidoDeRetiro(boxCargaPesada.isSelected(), residuosSeleccionados,  residuosSeleccionadosKg , textObservacion.getText(),fechaActual);
					api.generarPedidoDeRetiro(boxCargaPesada.isSelected(), residuosSeleccionados,  residuosSeleccionadosKg , textObservacion.getText());
					JOptionPane.showMessageDialog(null, labels.getString("pedido.retiro.mensaje.exito"), labels.getString("pedido.retiro.mensaje.informativo"), JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("crear la exception");
				}

				
				
		}
		});
		textField_Vidrio = new JTextField("0");
		textField_Vidrio.setVisible(false);
		textField_Vidrio.setBounds(103, 51, 86, 20);
		panelResiduos.add(textField_Vidrio);
		textField_Vidrio.setColumns(10);
		
		textField_Plastico = new JTextField("0");
		textField_Plastico.setBounds(103, 81, 86, 20);
		textField_Plastico.setVisible(false);
		panelResiduos.add(textField_Plastico);
		textField_Plastico.setColumns(10);
		
		textField_Metal = new JTextField("0");
	
		textField_Metal.setBounds(103, 111, 86, 20);
		textField_Metal.setVisible(false);
		panelResiduos.add(textField_Metal);
		textField_Metal.setColumns(10);
		
		textField_Carton = new JTextField("0");
		textField_Carton.setVisible(false);
		textField_Carton.setBounds(103, 141, 86, 20);
		panelResiduos.add(textField_Carton);
		textField_Carton.setColumns(10);
}
}