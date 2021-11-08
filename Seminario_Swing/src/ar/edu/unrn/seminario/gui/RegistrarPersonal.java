package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrarPersonal extends JFrame {

	private JPanel panelPrincipal;
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfDni;
	private JTextField tfEmail;
	private ResourceBundle labels;
	private JButton btnCancelar;
	private JButton btnLimpiar;
	private JButton btnAceptar;
	private JLabel lbApellido;
	private JLabel lbNombre;
	private JLabel lbDni;
	private JLabel lbEmail;
	private JPanel panelBotones;
	private JPanel panelDatos;
	private JLabel lbTelefono;
	private JTextField tfTelefono;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					ResourceBundle labels = ResourceBundle.getBundle("labels"); 
					RegistrarPersonal frame = new RegistrarPersonal(api,labels);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public RegistrarPersonal(IApi api, ResourceBundle labels) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setBounds(100, 100, 450, 315);
		setTitle(labels.getString("registrar.personal.titulo"));
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelPrincipal.setLayout(null);
		setContentPane(panelPrincipal);
		
		panelDatos = new JPanel();
		panelDatos.setBounds(10, 0, 414, 190);
		panelDatos.setLayout(null);
		panelPrincipal.add(panelDatos);
		
		panelBotones = new JPanel();
		panelBotones.setBounds(45, 201, 350, 60);
		panelPrincipal.add(panelBotones);
		panelBotones.setLayout(null);
		
		btnCancelar = new JButton(labels.getString("registrar.personal.button.cancelar"));
		btnCancelar.addActionListener((e)->{
			int res = JOptionPane.showConfirmDialog(null, labels.getString("registrar.personal.mensaje.confirmar"),labels.getString("registrar.personal.mensaje.informativo") , JOptionPane.YES_NO_OPTION);
			if(res == JOptionPane.YES_OPTION) {
				setVisible(false);
				dispose();
			}
			
			
			
			
		});
		btnCancelar.setBounds(240, 11, 89, 23);
		panelBotones.add(btnCancelar);
		
		btnAceptar = new JButton(labels.getString("registrar.personal.button.aceptar"));
		btnAceptar.addActionListener((e)->{
			
				try { //Resolver AppException con un catch
					api.registrarPersonal(tfNombre.getText(), tfApellido.getText(),
							tfDni.getText(), tfEmail.getText(), tfTelefono.getText());
					JOptionPane.showMessageDialog(null,"Recolector registrado con exito","Mensaje informativo",1);
					setVisible(false);
					dispose();
				} catch (DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.WARNING_MESSAGE);
					
				}
				
				
			
		
			
			
		});
		
		btnAceptar.setBounds(122, 26, 89, 23);
		panelBotones.add(btnAceptar);
		
		btnLimpiar = new JButton(labels.getString("registrar.personal.button.limpiar"));
		btnLimpiar.addActionListener((e)->{
			this.tfApellido.setText("");
			this.tfNombre.setText("");
			this.tfTelefono.setText("");
			this.tfDni.setText("");
			this.tfEmail.setText("");
			
		});
		btnLimpiar.setBounds(10, 11, 89, 23);
		panelBotones.add(btnLimpiar);
		
		lbNombre = new JLabel(labels.getString("registrar.personal.label.nombre"));
		lbNombre.setBounds(44, 30, 110, 14);
		panelDatos.add(lbNombre);
		
		lbApellido = new JLabel(labels.getString("registrar.personal.label.apellido"));
		lbApellido.setBounds(44, 60, 110, 14);
		panelDatos.add(lbApellido);
		
		lbTelefono = new JLabel(labels.getString("registrar.personal.label.telefono"));
		lbTelefono.setBounds(44, 120, 110, 14);
		panelDatos.add(lbTelefono);
		
		lbDni = new JLabel(labels.getString("registrar.personal.label.dni"));
		lbDni.setBounds(44, 90, 110, 14);
		panelDatos.add(lbDni);
		
		lbEmail = new JLabel(labels.getString("registrar.personal.label.email"));
		lbEmail.setBounds(44, 150, 110, 14);
		panelDatos.add(lbEmail);
		
		
		tfNombre = new JTextField();
		tfNombre.setBounds(164, 30, 197, 20);
		panelDatos.add(tfNombre);
		
		tfApellido = new JTextField();
		tfApellido.setBounds(164, 60, 197, 20);
		panelDatos.add(tfApellido);
		tfApellido.setColumns(10);
		
		tfTelefono = new JTextField();
		tfTelefono.setBounds(164, 120, 197, 20);
		panelDatos.add(tfTelefono);
		tfTelefono.setColumns(10);	
		
		tfDni = new JTextField();
		tfDni.setBounds(164, 90, 197, 20);
		panelDatos.add(tfDni);
		tfDni.setColumns(10);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(164, 150, 197, 20);
		panelDatos.add(tfEmail);
		tfEmail.setColumns(10);
		
		
		

		
		
		

		
		
		
		
		
	}
}
