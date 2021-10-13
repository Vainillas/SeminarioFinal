package ar.edu.unrn.seminario.gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotCorrectPasswordException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotRegisterException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class IniciarSesion extends JFrame {

	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textPassword;	
	private IApi api;
	private JPanel panelNoRegistrado;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					IniciarSesion frame = new IniciarSesion(api);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IniciarSesion(IApi api) {
		
		ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
		setTitle(labels.getString("iniciar.sesion.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 316);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		JPanel panelDatos = new JPanel();
		panelDatos.setLayout(null);
		panelDatos.setBounds(20, 11, 404, 118);
		contentPane.add(panelDatos);
		
		textUsuario = new JTextField("");
		textUsuario.setToolTipText("");
		textUsuario.setBounds(101, 40, 234, 20);
		panelDatos.add(textUsuario);
		textUsuario.setColumns(10);
		
		textPassword = new JPasswordField("");
		textPassword.setToolTipText("");
		textPassword.setBounds(101, 80, 234, 20);
		panelDatos.add(textPassword);
		
		JLabel labelUsuario = new JLabel(labels.getString("iniciar.sesion.label.usuario"));
		labelUsuario.setBounds(10, 43, 64, 14);
		panelDatos.add(labelUsuario);
		
		JLabel labelPassword = new JLabel(labels.getString("iniciar.sesion.label.password"));
		labelPassword.setBounds(10, 83, 64, 14);
		panelDatos.add(labelPassword);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(20, 128, 404, 52);
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		
		JButton buttonCancelar = new JButton(labels.getString("iniciar.sesion.button.cancelar"));
		buttonCancelar.addActionListener((e)->{
			
				setVisible(false);
				dispose();
			
		});
		buttonCancelar.setBounds(216, 18, 89, 23);
		panelBotones.add(buttonCancelar);
		
		
		JButton buttonAceptar = new JButton(labels.getString("iniciar.sesion.button.aceptar"));
		buttonAceptar.addActionListener((e)->{
				//System.out.println(textUsuario.getText());
				try {
						
						//System.out.printf(textUsuario.getText() + String.valueOf(textPassword.getPassword() ));
						api.validarUsuario(textUsuario.getText(), String.valueOf(textPassword.getPassword()));
							//api.usuarioActivo(textUsuario.getText());
								VentanaPrincipal ventana = new VentanaPrincipal(api);
								ventana.setVisible(true);
								dispose();
						
				}catch (DataEmptyException | IncorrectEmailException | AppException | NotCorrectPasswordException | StringNullException  e1) {
					
					JOptionPane.showMessageDialog(null,e1.getMessage() ,"Error" ,0);
				}
				catch(NotRegisterException e1) {
					panelNoRegistrado.setVisible(true);
					JOptionPane.showMessageDialog(null,e1.getMessage() ,"Error" ,0);
				}
			
		});
		
		buttonAceptar.setBounds(81, 18, 89, 23);
		panelBotones.add(buttonAceptar);
		
		 panelNoRegistrado = new JPanel();
		panelNoRegistrado.setBounds(211, 191, 213, 84);
		panelNoRegistrado.setLayout(null);
		panelNoRegistrado.setVisible(false);
		contentPane.add(panelNoRegistrado);
		
		JLabel labelNoRegistrado = new JLabel(labels.getString("iniciar.sesion.label.no.registrado"));
		labelNoRegistrado.setBounds(53, 11, 150, 14);
		panelNoRegistrado.add(labelNoRegistrado);
		
		JButton buttonRegistrarse = new JButton(labels.getString("iniciar.sesion.label.registrarse"));
		buttonRegistrarse.addActionListener((e)->{
				AltaUsuario usuario = new AltaUsuario(api);
				usuario.setVisible(true);
			
		});
		
		buttonRegistrarse.setBounds(54, 36, 107, 23);
		panelNoRegistrado.add(buttonRegistrarse);
	}
}
