package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.Color;

public class RegistrarVivienda extends JFrame {
	IApi api;
    private JPanel contentPane;
    private JTextField calleIngresada;
    private JLabel labelCodPostal;
    //private JFormattedTextField codPostIngresado;
    private JTextField latitudIngresada;
    private JTextField barrioIngresado;
    private JTextField longitudIngresada;
    public RegistrarVivienda(IApi api, ResourceBundle labels){
    	

    	setTitle(labels.getString("registro.viviendas.titulo"));
    	this.api=api;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 576, 351);
        contentPane = new JPanel();
        contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JLabel labeldireccion = new JLabel(labels.getString("registro.viviendas.label.direccion"));
        labeldireccion.setFont(new Font("Tahoma", Font.PLAIN, 20));
        labeldireccion.setBounds(85, 11, 208, 25);
        contentPane.add(labeldireccion);
        
        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.GRAY, null, Color.DARK_GRAY, null));
        panel.setBounds(85, 36, 413, 253);
        contentPane.add(panel);
        panel.setLayout(null);
        
        calleIngresada = new JTextField();
        calleIngresada.setBounds(215, 11, 77, 20);
        panel.add(calleIngresada);
        calleIngresada.setColumns(10);
        
        JLabel labelCalle = new JLabel(labels.getString("registro.viviendas.label.calle"));
        labelCalle.setFont(new Font("Tahoma", Font.BOLD, 13));
        labelCalle.setBounds(135, 13, 111, 14);
        panel.add(labelCalle);
        
        JLabel label = new JLabel(labels.getString("registro.viviendas.label.altura"));
        label.setFont(new Font("Tahoma", Font.BOLD, 13));
        label.setBounds(10, 44, 111, 20);
        panel.add(label);
        
        JFormattedTextField alturaIngresada = new JFormattedTextField();
        alturaIngresada.setBounds(105, 44, 77, 20);
        panel.add(alturaIngresada);
        
        labelCodPostal = new JLabel(labels.getString("registro.viviendas.label.codigo.postal"));
        labelCodPostal.setFont(new Font("Tahoma", Font.BOLD, 13));
        labelCodPostal.setBounds(119, 75, 111, 14);
        panel.add(labelCodPostal);
        
        JLabel labelLatitud = new JLabel(labels.getString("registro.viviendas.label.latitud"));
        labelLatitud.setFont(new Font("Tahoma", Font.BOLD, 13));
        labelLatitud.setBounds(10, 121, 111, 20);
        panel.add(labelLatitud);
        
        JFormattedTextField codPostIngresado = new JFormattedTextField();
        codPostIngresado.setBounds(215, 72, 77, 20);
        panel.add(codPostIngresado);
        latitudIngresada = new JTextField();
        latitudIngresada.setBounds(105, 121, 77, 20);
        panel.add(latitudIngresada);
        latitudIngresada.setColumns(10);
        
        JLabel labellongitud = new JLabel(labels.getString("registro.viviendas.label.longitud"));
        labellongitud.setFont(new Font("Tahoma", Font.BOLD, 13));
        labellongitud.setBounds(119, 152, 111, 20);
        panel.add(labellongitud);
        
        longitudIngresada = new JTextField();
        longitudIngresada.setBounds(215, 152, 77, 20);
        panel.add(longitudIngresada);
        longitudIngresada.setColumns(10);
        
        JLabel labelBarrio = new JLabel(labels.getString("registro.viviendas.label.barrio"));
        labelBarrio.setFont(new Font("Tahoma", Font.BOLD, 13));
        labelBarrio.setBounds(5, 205, 121, 23);
        panel.add(labelBarrio);
        
        barrioIngresado = new JTextField();
        barrioIngresado.setBounds(105, 207, 77, 20);
        panel.add(barrioIngresado);
        barrioIngresado.setColumns(10);
        
        JButton botonAceptar = new JButton(labels.getString("registro.viviendas.button.aceptar"));
        botonAceptar.setBounds(192, 216, 89, 23);
        panel.add(botonAceptar);
        
        JButton botonCancelar = new JButton(labels.getString("registro.viviendas.button.cancelar"));
        botonCancelar.setBounds(305, 216, 89, 23);
        panel.add(botonCancelar);
        
        JLabel lbRelleneLosCampos = new JLabel(labels.getString("registro.viviendas.label.rellene.campos"));
        lbRelleneLosCampos.setBounds(392, 20, 106, 14);
        
        contentPane.add(lbRelleneLosCampos);
        botonCancelar.addActionListener((e)->{
                setVisible (false);
            
        });
        botonAceptar.addActionListener((e)->{
            	

            		try {
						if(!api.existeVivienda(api.obtenerDueñoActivo().getDni(), calleIngresada.getText(), alturaIngresada.getText()) && !api.existeDireccion(calleIngresada.getText(), alturaIngresada.getText())) {
							api.registrarVivienda(
									calleIngresada.getText(),
									alturaIngresada.getText(),
									codPostIngresado.getText(),
									longitudIngresada.getText(), 
									latitudIngresada.getText(),
									barrioIngresado.getText());
							
							api.registrarDireccion(calleIngresada.getText(),
									alturaIngresada.getText(),
									codPostIngresado.getText(),
									latitudIngresada.getText(),
									longitudIngresada.getText(),
									barrioIngresado.getText());
							JOptionPane.showMessageDialog(null ,
									labels.getString("registro.viviendas.mensaje.carga.correcta"));
						    setVisible (false);
						    dispose();
						}
						else
							JOptionPane.showMessageDialog(null, labels.getString("registro.viviendas.mensaje.error"));
					} catch (HeadlessException | AppException | DataEmptyException | StringNullException
							| IncorrectEmailException | NotNumberException  e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),0);
					}
								 
        });
        
        
        
        
        
    }
}


