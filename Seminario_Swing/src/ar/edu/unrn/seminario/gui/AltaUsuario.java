
package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

public class AltaUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField usuarioTextField;
	private JTextField contrasenaTextField;
	private JTextField emailTextField;
	private JComboBox rolComboBox;
	private ResourceBundle labels ;
	private List<RolDTO> roles = new ArrayList<>();

	/**
	 * Create the frame.
	 */
	public AltaUsuario(IApi api) {

		// Obtengo los roles
		try {
			this.roles = api.obtenerRoles();
		} catch (AppException e3) {
			JOptionPane.showMessageDialog(null,e3.getMessage(),"error",2);
		}
		labels = ResourceBundle.getBundle("labels");
		setTitle(labels.getString("alta.usuario.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//JLabel usuarioLabel = new JLabel(labels.getString("alta.usuario.label.nombre.usuario"));
		JLabel usuarioLabel = new JLabel("Usuario");
		usuarioLabel.setBounds(43, 16, 76, 16);
		contentPane.add(usuarioLabel);

		JLabel contrasenaLabel = new JLabel(labels.getString("alta.usuario.label.password"));
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
		JLabel emailLabel = new JLabel(labels.getString("alta.usuario.label.email"));
		emailLabel.setBounds(43, 96, 56, 16);
		contentPane.add(emailLabel);

		JLabel rolLabel = new JLabel(labels.getString("alta.usuario.label.rol"));
		rolLabel.setBounds(43, 134, 56, 16);
		contentPane.add(rolLabel);

		emailTextField = new JTextField();
		emailTextField.setBounds(148, 93, 160, 22);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);

		JButton aceptarButton = new JButton(labels.getString("alta.usuario.button.aceptar") );
		aceptarButton.addActionListener((e)->{//utilizando metodos lambda
			RolDTO rol = roles.get(rolComboBox.getSelectedIndex());
				
					try {
	
						api.registrarUsuario(usuarioTextField.getText(), contrasenaTextField.getText(),emailTextField.getText(),rol.getCodigo());
						JOptionPane.showMessageDialog(null, labels.getString("alta.usuario.mensaje.informativo"), "Info", JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						dispose();
					} catch ( NotNullException | IncorrectEmailException | DataEmptyException | StringNullException | AppException  e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(),"error",2);
					}
					
					
		});
		
		aceptarButton.setBounds(218, 215, 97, 25);
		contentPane.add(aceptarButton);

		JButton cancelarButton = new JButton(labels.getString("alta.usuario.button.cancelar"));
		cancelarButton.addActionListener((e)->{
				setVisible(false);
				dispose();
		});
		
		cancelarButton.setBounds(323, 215, 97, 25);
		contentPane.add(cancelarButton);
		rolComboBox = new JComboBox();
		rolComboBox.setBounds(148, 131, 160, 22);
		contentPane.add(rolComboBox);
		
		for (RolDTO rol : this.roles) {
			rolComboBox.addItem(rol.getNombre());
		}

	}
}

