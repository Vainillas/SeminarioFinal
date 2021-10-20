package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
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

public class RegistroVivienda extends JFrame {
	IApi api;
    private JPanel contentPane;
    private JTextField dniIngresado;
    private JTextField calleIngresada;
    private JLabel labelCodPostal;
    //private JFormattedTextField codPostIngresado;
    private JTextField latitudIngresada;
    private JTextField barrioIngresado;
    private JTextField longitudIngresada;
    private JTextField nombreIngresado;
    private JTextField correoIngresado;
    private JTextField apellidoIngresado;

    /**
     * Launch the application.
     */

    /**
     * Create the frame.
     */
    public RegistroVivienda(IApi api){
    	ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
    	setTitle(labels.getString("registro.viviendas.titulo"));
    	this.api=api;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 549, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JLabel labelDueno = new JLabel(labels.getString("registro.viviendas.label.dueño"));
        labelDueno.setFont(new Font("Tahoma", Font.PLAIN, 20));
        labelDueno.setBounds(22, 0, 112, 25);
        contentPane.add(labelDueno);
        
        JLabel labelDireccion = new JLabel(labels.getString("registro.viviendas.label.direccion"));
        labelDireccion.setBounds(281, 39, 111, 14);
        contentPane.add(labelDireccion);
        
        dniIngresado = new JTextField();

        dniIngresado.setBounds(105, 93, 96, 20);
        contentPane.add(dniIngresado);
        dniIngresado.setColumns(10);
        
        calleIngresada = new JTextField();
        calleIngresada.setBounds(402, 36, 77, 20);
        contentPane.add(calleIngresada);
        calleIngresada.setColumns(10);
        
        JFormattedTextField alturaIngresada = new JFormattedTextField();
        alturaIngresada.setBounds(402, 62, 77, 20);
        contentPane.add(alturaIngresada);
        
        JFormattedTextField codPostIngresado = new JFormattedTextField();
        codPostIngresado.setBounds(402, 93, 77, 20);
        contentPane.add(codPostIngresado);
        
        JLabel labelBarrio = new JLabel(labels.getString("registro.viviendas.label.barrio"));
        labelBarrio.setBounds(281, 195, 111, 23);
        contentPane.add(labelBarrio);
        
        JLabel labellongitud = new JLabel(labels.getString("registro.viviendas.label.longitud"));
        labellongitud.setBounds(281, 156, 111, 20);
        contentPane.add(labellongitud);
        
        JLabel labelLatitud = new JLabel(labels.getString("registro.viviendas.label.latitud"));
        labelLatitud.setBounds(281, 125, 111, 20);
        contentPane.add(labelLatitud);
        
        JLabel labelNombre = new JLabel(labels.getString("registro.viviendas.label.nombre"));
        labelNombre.setBounds(24, 36, 71, 17);
        contentPane.add(labelNombre);
        
        JLabel labelApellido = new JLabel(labels.getString("registro.viviendas.label.apellido"));
        labelApellido.setBounds(24, 64, 71, 17);
        contentPane.add(labelApellido);
        
        JLabel labelDni = new JLabel(labels.getString("registro.viviendas.label.dni"));
        labelDni.setBounds(22, 93, 73, 20);
        contentPane.add(labelDni);
        
        JLabel labelCorreo = new JLabel(labels.getString("registro.viviendas.label.correo"));
        labelCorreo.setBounds(24, 126, 71, 17);
        contentPane.add(labelCorreo);
        
        JLabel labeldireccion = new JLabel(labels.getString("registro.viviendas.label.direccion"));
        labeldireccion.setFont(new Font("Tahoma", Font.PLAIN, 20));
        labeldireccion.setBounds(242, 0, 208, 25);
        contentPane.add(labeldireccion);
        
        JButton botonAceptar = new JButton(labels.getString("registro.viviendas.button.aceptar"));
        botonAceptar.addActionListener((e)->{
            	try {
            		if(!api.existeDueño(dniIngresado.getText())) {//si el dueño no existe se procede a crear uno

            			api.registrarDueño(nombreIngresado.getText(),
                				apellidoIngresado.getText(),
                				dniIngresado.getText(),
                				correoIngresado.getText());
            		}
            		api.registrarDireccion(calleIngresada.getText(),
            				alturaIngresada.getText(),
            				codPostIngresado.getText(),
            				latitudIngresada.getText(),
            				longitudIngresada.getText(),
            				barrioIngresado.getText());
            		api.registrarVivienda(nombreIngresado.getText(),
            				apellidoIngresado.getText(),
            				dniIngresado.getText(),
            				correoIngresado.getText(),
            				calleIngresada.getText(),
            				alturaIngresada.getText(),
            				codPostIngresado.getText(),
            				longitudIngresada.getText(), 
            				latitudIngresada.getText(),
            				barrioIngresado.getText());
            		JOptionPane.showMessageDialog(null ,
            				labels.getString("registro.viviendas.mensaje.carga.correcta"));
                    setVisible (false);
                    dispose();
            	} 
				 catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), labels.getString("registro.viviendas.mensaje.error"), JOptionPane.ERROR_MESSAGE);
				}
        });
        
        
        botonAceptar.setBounds(87, 229, 89, 23);
        contentPane.add(botonAceptar);
        
        JButton botonCancelar = new JButton(labels.getString("registro.viviendas.button.cancelar"));
        botonCancelar.addActionListener((e)->{
                setVisible (false);
            
        });
        
        botonCancelar.setBounds(250, 229, 89, 23);
        contentPane.add(botonCancelar);
        
        JLabel label = new JLabel(labels.getString("registro.viviendas.label.altura"));
        label.setBounds(281, 62, 111, 20);
        contentPane.add(label);
        
        labelCodPostal = new JLabel(labels.getString("registro.viviendas.label.codigo.postal"));
        labelCodPostal.setBounds(281, 98, 111, 14);
        contentPane.add(labelCodPostal);
        latitudIngresada = new JTextField();
        latitudIngresada.setBounds(402, 124, 77, 20);
        contentPane.add(latitudIngresada);
        latitudIngresada.setColumns(10);
        
        barrioIngresado = new JTextField();
        barrioIngresado.setBounds(402, 196, 77, 20);
        contentPane.add(barrioIngresado);
        barrioIngresado.setColumns(10);
        
        longitudIngresada = new JTextField();
        longitudIngresada.setBounds(402, 156, 77, 20);
        contentPane.add(longitudIngresada);
        longitudIngresada.setColumns(10);
        
        
        
        nombreIngresado = new JTextField();
        nombreIngresado.setColumns(10);
        nombreIngresado.setBounds(105, 36, 96, 20);
        contentPane.add(nombreIngresado);
        
        correoIngresado = new JTextField();
        correoIngresado.setColumns(10);
        correoIngresado.setBounds(105, 125, 96, 20);
        contentPane.add(correoIngresado);
        
        apellidoIngresado = new JTextField();
        apellidoIngresado.setColumns(10);
        apellidoIngresado.setBounds(105, 62, 96, 20);
        contentPane.add(apellidoIngresado);
        
        
        
        
        
    }
}


