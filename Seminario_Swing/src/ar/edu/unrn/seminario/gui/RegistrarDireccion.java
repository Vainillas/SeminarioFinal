package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class RegistrarDireccion extends JFrame {

	private JPanel contentPane;
	private JTextField txBarrio;
	private JTextField txCalle;
	private JTextField txAltura;
	private JTextField txCodPostal;
	private JTextField txLatitud;
	private JTextField txLongitud;
	private ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es"));
	private IApi api;


	public RegistrarDireccion(IApi api) {
		this.api = api;
		setTitle(labels.getString("registrar.direccion.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 245);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelRegistro = new JPanel();
		panelRegistro.setBackground(SystemColor.controlHighlight);
		panelRegistro.setLayout(null);
		panelRegistro.setBounds(10, 11, 763, 186);
		contentPane.add(panelRegistro);
		
		JLabel lbDatosDireccion = new JLabel(labels.getString("registrar.direccion.datos.de.direccion"));
		lbDatosDireccion.setForeground(Color.black);
		lbDatosDireccion.setBackground(Color.WHITE);
		lbDatosDireccion.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lbDatosDireccion.setToolTipText("black\r\n");
		lbDatosDireccion.setBounds(101, 11, 163, 14);
		panelRegistro.add(lbDatosDireccion);
		
		JLabel lbBarrio = new JLabel(labels.getString("registrar.direccion.label.barrio"));
		lbBarrio.setBounds(111, 42, 153, 14);
		panelRegistro.add(lbBarrio);
		
		txBarrio = new JTextField();
		txBarrio.setBounds(101, 67, 163, 20);
		panelRegistro.add(txBarrio);
		txBarrio.setColumns(10);
		
		JLabel lbCalle = new JLabel(labels.getString("registrar.direccion.label.calle"));
		lbCalle.setBounds(309, 42, 133, 14);
		panelRegistro.add(lbCalle);
		
		txCalle = new JTextField();
		txCalle.setBounds(319, 67, 191, 20);
		panelRegistro.add(txCalle);
		txCalle.setColumns(10);
		
		JLabel lbAltura = new JLabel(labels.getString("registrar.direccion.label.altura"));
		lbAltura.setBounds(550, 42, 163, 14);
		panelRegistro.add(lbAltura);
		
		txAltura = new JTextField();
		txAltura.setBounds(550, 67, 191, 20);
		panelRegistro.add(txAltura);
		txAltura.setColumns(10);
		
		JLabel lbCodPostal = new JLabel(labels.getString("registrar.direccion.label.codigo.postal"));
		lbCodPostal.setBounds(46, 105, 179, 20);
		panelRegistro.add(lbCodPostal);
		
		txCodPostal = new JTextField();
		txCodPostal.setBounds(46, 136, 184, 20);
		panelRegistro.add(txCodPostal);
		txCodPostal.setColumns(10);
		
		JLabel lbLatitud = new JLabel(labels.getString("registrar.direccion.label.latitud"));
		lbLatitud.setBounds(284, 108, 179, 14);
		panelRegistro.add(lbLatitud);
		
		txLatitud = new JTextField();
		txLatitud.setBounds(284, 136, 184, 20);
		panelRegistro.add(txLatitud);
		txLatitud.setColumns(10);
		
		JLabel lbLongitud = new JLabel(labels.getString("registrar.direccion.label.longitud"));
		lbLongitud.setBounds(511, 105, 184, 20);
		panelRegistro.add(lbLongitud);
		
		txLongitud = new JTextField();
		txLongitud.setBounds(511, 136, 191, 20);
		panelRegistro.add(txLongitud);
		txLongitud.setColumns(10);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(SystemColor.window);
		panelBotones.setBounds(783, 59, 113, 96);
		contentPane.add(panelBotones);
		
		JButton btnCancelar = new JButton(labels.getString("registrar.direccion.button.cancelar")); 
		btnCancelar.addActionListener((e)->{
			setVisible(false);
			dispose();
			
		});
		panelBotones.add(btnCancelar);
		
		JButton btnEnviarDatos = new JButton(labels.getString("registrar.direccion.button.enviar.datos")); 
		btnEnviarDatos.addActionListener((e)->{
			//api.registrarVivienda(getName(), getName(), getWarningString(), getName(), getName(), getName(), getName(), getTitle(), getWarningString(), getName())
			
		});

		panelBotones.add(btnEnviarDatos);
		
		JButton btnLimpiar = new JButton(labels.getString("registrar.direccion.button.limpiar")); 
		btnLimpiar.addActionListener((e)->{
			txAltura.setText("");
			txLatitud.setText("");
			txLongitud.setText("");
			txBarrio.setText("");
			txCodPostal.setText("");
			txCalle.setText("");
			
		});
		panelBotones.add(btnLimpiar);
	}
}
