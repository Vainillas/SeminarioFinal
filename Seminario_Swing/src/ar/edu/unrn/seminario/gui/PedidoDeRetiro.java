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
	private JPanel panelBotones;
	private JButton buttonCancelar;
	private JButton buttonFinalizar;
	private JPanel panelResiduos;


	public PedidoDeRetiro(IApi api) {
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
		boxCargaPesada.setBounds(224, 10, 97, 23);
		panelDatos.add(boxCargaPesada);
		

		panelBotones = new JPanel();
		panelBotones.setBackground(SystemColor.info);
		panelBotones.setBounds(10, 205, 358, 60);
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		
		buttonCancelar = new JButton(labels.getString("pedido.retiro.button.cancelar"));
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int res = JOptionPane.showConfirmDialog(null, labels.getString("pedido.retiro.confirmar"),labels.getString("pedido.retiro.mensaje.informativo") , JOptionPane.YES_NO_OPTION);
				if(res == JOptionPane.YES_OPTION) {
					
					setVisible(false);
					dispose();
				}
				
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
		radioButtonVidrio.setBackground(UIManager.getColor("window"));
		radioButtonVidrio.setBounds(6, 50, 109, 23);
		panelResiduos.add(radioButtonVidrio);
		
		JRadioButton radioButtonPlastico = new JRadioButton(labels.getString("pedido.retiro.radio.button.plastico"));
		radioButtonPlastico.setBackground(UIManager.getColor("window"));
		radioButtonPlastico.setBounds(6, 80, 109, 23);
		panelResiduos.add(radioButtonPlastico);

		JRadioButton radioButtonMetal = new JRadioButton(labels.getString("pedido.retiro.radio.button.metal"));
		radioButtonMetal.setBackground(UIManager.getColor("window"));
		radioButtonMetal.setBounds(6, 110, 109, 23);
		panelResiduos.add(radioButtonMetal);
		
		JRadioButton radioButtonCarton = new JRadioButton(labels.getString("pedido.retiro.radio.button.carton"));
		radioButtonCarton.setBackground(UIManager.getColor("window"));
		radioButtonCarton.setBounds(6, 140, 109, 23);
		panelResiduos.add(radioButtonCarton);
		
		JLabel LabelResiduos = new JLabel(labels.getString("pedido.retiro.label.residuos"));
		LabelResiduos.setBounds(46, 0, 133, 14);
		panelResiduos.add(LabelResiduos);
		JRadioButton [] residuos = {radioButtonPlastico,radioButtonMetal,radioButtonCarton,radioButtonVidrio};
		
		
		buttonFinalizar = new JButton(labels.getString("pedido.retiro.button.finalizar"));
		buttonFinalizar.setBounds(120, 11, 89, 23);
		panelBotones.add(buttonFinalizar);
		buttonFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(boxCargaPesada.isSelected());
				ArrayList <String> residuosSeleccionados = new ArrayList<String>();
				for(JRadioButton r : residuos) {
					if(r.isSelected()) {
						residuosSeleccionados.add(r.getText());
					}
				}
				
				//para probar que funciona utilizar este for
				for(int i = 0;i<residuosSeleccionados.size();i++) {
					System.out.println(residuosSeleccionados.get(i));
				}

				api.generarPedidoDeRetiro(boxCargaPesada.isSelected(), residuosSeleccionados, textObservacion.getText(),fechaActual);
				JOptionPane.showMessageDialog(null, labels.getString("pedido.retiro.mensaje.exito"), labels.getString("pedido.retiro.mensaje.informativo"), JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				dispose();
				
			}
		});
	}
}
