/*package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;

public class AltaUsuario extends JFrame {

	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("Usuario:");
	private JTextField textField;
	private JTextField textField_1;

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaUsuario frame = new AltaUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaUsuario frame = new AltaUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
		
		AltaUsuario() {
		setTitle("Alta Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		lblNewLabel.setBounds(52, 67, 98, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_1.setBounds(52, 109, 98, 31);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(145, 72, 150, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(145, 114, 150, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(67, 194, 89, 23);
		contentPane.add(btnNewButton);
	
		

		
		JButton btnNewButton_1 = new JButton("Aceptar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				
			    
				System.out.print(textField.getText());
				System.out.print(" "+textField_1.getText());
				textField.setText(null);
				textField_1.setText(null);
			}
		});
		btnNewButton_1.setBounds(251, 194, 89, 23);
		contentPane.add(btnNewButton_1);
		
		
	}
}
*/

package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.RolDTO;

public class AltaUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField usuarioTextField;
	private JTextField contrasenaTextField;
	private JTextField nombreTextField;
	private JTextField emailTextField;
	private JComboBox rolComboBox;

	private List<RolDTO> roles = new ArrayList<>();

	/**
	 * Create the frame.
	 */
	public AltaUsuario(IApi api) {

		// Obtengo los roles
		this.roles = api.obtenerRoles();

		setTitle("Alta Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel usuarioLabel = new JLabel("User:");
		usuarioLabel.setBounds(43, 16, 76, 16);
		contentPane.add(usuarioLabel);

		JLabel contrasenaLabel = new JLabel("Contrase\u00F1a:");
		contrasenaLabel.setBounds(43, 56, 93, 16);
		contentPane.add(contrasenaLabel);

		usuarioTextField = new JTextField();
		usuarioTextField.setBounds(148, 13, 160, 22);
		contentPane.add(usuarioTextField);
		usuarioTextField.setColumns(10);

		contrasenaTextField = new JTextField();
		contrasenaTextField.setBounds(148, 53, 160, 22);
		contentPane.add(contrasenaTextField);
		contrasenaTextField.setColumns(10);

		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RolDTO rol = roles.get(rolComboBox.getSelectedIndex());

					api.registrarUsuario(usuarioTextField.getText(), contrasenaTextField.getText(),
							nombreTextField.getText(), emailTextField.getText(), rol.getCodigo());
					JOptionPane.showMessageDialog(null, "Usuario registrado con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
			}
		});
		aceptarButton.setBounds(218, 215, 97, 25);
		contentPane.add(aceptarButton);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		cancelarButton.setBounds(323, 215, 97, 25);
		contentPane.add(cancelarButton);

		JLabel nombreLabel = new JLabel("Nombre:");
		nombreLabel.setBounds(43, 88, 56, 16);
		contentPane.add(nombreLabel);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(43, 125, 56, 16);
		contentPane.add(emailLabel);

		JLabel rolLabel = new JLabel("Rol:");
		rolLabel.setBounds(43, 154, 56, 16);
		contentPane.add(rolLabel);

		nombreTextField = new JTextField();
		nombreTextField.setBounds(148, 85, 160, 22);
		contentPane.add(nombreTextField);
		nombreTextField.setColumns(10);

		emailTextField = new JTextField();
		emailTextField.setBounds(148, 122, 160, 22);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);

		rolComboBox = new JComboBox();
		rolComboBox.setBounds(148, 151, 160, 22);
		contentPane.add(rolComboBox);

		for (RolDTO rol : this.roles) {
			rolComboBox.addItem(rol.getNombre());
		}

	}
}

