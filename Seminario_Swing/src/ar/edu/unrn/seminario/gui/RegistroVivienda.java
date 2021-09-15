package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.awt.Font;

public class RegistroVivienda extends JFrame {
	IApi api;
    private JPanel contentPane;
    private JTextField dniIngresado;
    private JTextField calleIngresada;
    private JLabel lblCodpostal;
    //private JFormattedTextField codPostIngresado;
    private JTextField latitudIngresada;
    private JTextField barrioIngresado;
    private JTextField longIngresada;
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
    	setTitle("Registro Vivienda");
    	this.api=api;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 403, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JLabel labelDueno = new JLabel("Due\u00F1o");
        labelDueno.setFont(new Font("Tahoma", Font.PLAIN, 20));
        labelDueno.setBounds(75, 0, 112, 25);
        contentPane.add(labelDueno);
        
        JLabel labelDireccion = new JLabel("Calle");
        labelDireccion.setBounds(242, 39, 49, 14);
        contentPane.add(labelDireccion);
        
        dniIngresado = new JTextField();

        dniIngresado.setBounds(75, 95, 96, 20);
        contentPane.add(dniIngresado);
        dniIngresado.setColumns(10);
        
        calleIngresada = new JTextField();
        calleIngresada.setBounds(276, 36, 77, 20);
        contentPane.add(calleIngresada);
        calleIngresada.setColumns(10);
        
        JFormattedTextField alturaIngresada = new JFormattedTextField();
        alturaIngresada.setBounds(276, 64, 77, 20);
        contentPane.add(alturaIngresada);
        
        JFormattedTextField codPostIngresado = new JFormattedTextField();
        codPostIngresado.setBounds(276, 95, 77, 20);
        contentPane.add(codPostIngresado);
        
        JLabel lblNewLabel = new JLabel("Barrio");
        lblNewLabel.setBounds(233, 187, 58, 23);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Longitud");
        lblNewLabel_1.setBounds(216, 156, 49, 20);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Latitud");
        lblNewLabel_2.setBounds(226, 126, 46, 20);
        contentPane.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Nombre");
        lblNewLabel_3.setBounds(24, 36, 46, 17);
        contentPane.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Apellido");
        lblNewLabel_4.setBounds(24, 64, 46, 17);
        contentPane.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("DNI");
        lblNewLabel_5.setBounds(32, 95, 46, 20);
        contentPane.add(lblNewLabel_5);
        
        JLabel lblNewLabel_6 = new JLabel("Correo");
        lblNewLabel_6.setBounds(24, 126, 46, 17);
        contentPane.add(lblNewLabel_6);
        
        JLabel lblDireccion = new JLabel("Direccion");
        lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblDireccion.setBounds(242, 0, 112, 25);
        contentPane.add(lblDireccion);
        
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		api.agregarVivienda(nombreIngresado.getText(),
            				apellidoIngresado.getText(),
            				dniIngresado.getText(),
            				correoIngresado.getText(),
            				calleIngresada.getText(),
            				alturaIngresada.getText(),
            				codPostIngresado.getText(),
            				longIngresada.getText(), 
            				latitudIngresada.getText(),
            				barrioIngresado.getText());
            		api.agregarDireccion(calleIngresada.getText(),
            				alturaIngresada.getText(),
            				codPostIngresado.getText(),
            				latitudIngresada.getText(),
            				longIngresada.getText(),
            				barrioIngresado.getText());
            		api.agregarDueño(nombreIngresado.getText(),
            				apellidoIngresado.getText(),
            				dniIngresado.getText(),
            				correoIngresado.getText());
            		JOptionPane.showMessageDialog(null , "La carga finalizó correctamente.");
		            setVisible (false);
		            dispose();
				} catch (DataEmptyException | NotNullException | IncorrectEmailException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error: ", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
        
        
        botonAceptar.setBounds(87, 229, 89, 23);
        contentPane.add(botonAceptar);
        
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible (false);
            }
        });
        
        botonCancelar.setBounds(250, 229, 89, 23);
        contentPane.add(botonCancelar);
        
        JLabel lblNro = new JLabel("Altura");
        lblNro.setBounds(233, 64, 49, 20);
        contentPane.add(lblNro);
        
        lblCodpostal = new JLabel("CodPostal");
        lblCodpostal.setBounds(216, 98, 63, 14);
        contentPane.add(lblCodpostal);
        
        latitudIngresada = new JTextField();
        latitudIngresada.setBounds(276, 126, 77, 20);
        contentPane.add(latitudIngresada);
        latitudIngresada.setColumns(10);
        
        barrioIngresado = new JTextField();
        barrioIngresado.setBounds(276, 188, 77, 20);
        contentPane.add(barrioIngresado);
        barrioIngresado.setColumns(10);
        
        longIngresada = new JTextField();
        longIngresada.setBounds(276, 157, 77, 20);
        contentPane.add(longIngresada);
        longIngresada.setColumns(10);
        
        
        
        nombreIngresado = new JTextField();
        nombreIngresado.setColumns(10);
        nombreIngresado.setBounds(75, 36, 96, 20);
        contentPane.add(nombreIngresado);
        
        correoIngresado = new JTextField();
        correoIngresado.setColumns(10);
        correoIngresado.setBounds(75, 126, 96, 20);
        contentPane.add(correoIngresado);
        
        apellidoIngresado = new JTextField();
        apellidoIngresado.setColumns(10);
        apellidoIngresado.setBounds(75, 64, 96, 20);
        contentPane.add(apellidoIngresado);
        
        
        
        
        
    }
}


