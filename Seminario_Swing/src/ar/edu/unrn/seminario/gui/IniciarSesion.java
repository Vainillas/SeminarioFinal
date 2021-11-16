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
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class IniciarSesion extends JFrame {

	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textPassword;	
	private IApi api;
	private JPanel panelNoRegistrado;
	private JComboBox comboBoxIdioma;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public IniciarSesion(IApi api) {
		
		ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
		
		setTitle(labels.getString("iniciar.sesion.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 316);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		JPanel panelDatos = new JPanel();
		panelDatos.setBackground(new Color(255, 255, 224));
		panelDatos.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelDatos.setBounds(80, 88, 275, 118);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel labelUsuario = new JLabel(labels.getString("iniciar.sesion.label.usuario"));
		labelUsuario.setBounds(10, 28, 99, 14);
		panelDatos.add(labelUsuario);
		
		textUsuario = new JTextField("");
		textUsuario.setBounds(119, 25, 146, 20);
		textUsuario.setToolTipText("");
		panelDatos.add(textUsuario);
		textUsuario.setColumns(10);
		
		textPassword = new JPasswordField("");
		textPassword.setBounds(119, 69, 146, 20);
		textPassword.setToolTipText("");
		panelDatos.add(textPassword);
		
		JLabel labelPassword = new JLabel(labels.getString("iniciar.sesion.label.password"));
		labelPassword.setBackground(new Color(255, 255, 224));
		labelPassword.setBounds(10, 72, 99, 14);
		panelDatos.add(labelPassword);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(new Color(255, 255, 224));
		panelBotones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelBotones.setBounds(10, 214, 414, 52);
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
					ResourceBundle l = ResourceBundle.getBundle("labels", new Locale("es"));
						if(comboBoxIdioma.getSelectedItem().equals("INGLES")) {
							l = ResourceBundle.getBundle("labels",new Locale("en"));
						}
						api.validarUsuario(textUsuario.getText(), String.valueOf(textPassword.getPassword())) ;
						api.usuarioActivo(textUsuario.getText());//sirve para mantener al usuario siempre
						VentanaPrincipalDinamica ventana = new VentanaPrincipalDinamica(api,l);
						ventana.setVisible(true);
						dispose();
						

				}catch (DataEmptyException | IncorrectEmailException | AppException | NotCorrectPasswordException | StringNullException  e1) {
					
					JOptionPane.showMessageDialog(null,e1.getMessage() ,"Error" ,0);
					
				}
				catch(NotRegisterException e1) {
					
					JOptionPane.showMessageDialog(null,e1.getMessage() ,"Error" ,0);
				}
			
		});
		
		buttonAceptar.setBounds(81, 18, 89, 23);
		panelBotones.add(buttonAceptar);
		comboBoxIdioma = new JComboBox();
		comboBoxIdioma.addItem("ESPAÑOL");
		comboBoxIdioma.addItem("INGLES");
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(new Color(255, 255, 224));
		panel.setBounds(10, 1, 181, 84);
		contentPane.add(panel);
		panel.setLayout(null);
		

		
		comboBoxIdioma.setBounds(58, 28, 113, 22);
		panel.add(comboBoxIdioma);
		
		JLabel lbIdioma = new JLabel(labels.getString("iniciar.sesion.label.idioma")); 
		lbIdioma.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbIdioma.setBounds(10, 11, 121, 14);
		panel.add(lbIdioma);
		
		 panelNoRegistrado = new JPanel();
		 panelNoRegistrado.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		 panelNoRegistrado.setBackground(new Color(255, 255, 224));
		 panelNoRegistrado.setBounds(243, 1, 181, 84);
		 contentPane.add(panelNoRegistrado);
		 panelNoRegistrado.setLayout(null);
		 panelNoRegistrado.setVisible(true);
		 JLabel labelNoRegistrado = new JLabel(labels.getString("iniciar.sesion.label.no.registrado"));
		 labelNoRegistrado.setFont(new Font("Tahoma", Font.BOLD, 11));
		 labelNoRegistrado.setBounds(10, 11, 150, 14);
		 panelNoRegistrado.add(labelNoRegistrado);
		 
		 JButton buttonRegistrarse = new JButton(labels.getString("iniciar.sesion.label.registrarse"));
		 buttonRegistrarse.addActionListener((e)->{
		 		AltaUsuario usuario = new AltaUsuario(api,labels);
		 		usuario.setVisible(true);
		 		this.textPassword.setText("");
		 		this.textUsuario.setText("");
		 		
		 		String idioma = (String) comboBoxIdioma.getSelectedItem();
		 		
		 	
		 	
		 });
		 
		 buttonRegistrarse.setBounds(54, 36, 107, 23);
		 panelNoRegistrado.add(buttonRegistrarse);
		
	}
}
