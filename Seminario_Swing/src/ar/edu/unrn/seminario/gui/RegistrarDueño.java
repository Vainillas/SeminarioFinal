package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import java.awt.SystemColor;
import javax.swing.JButton;

public class RegistrarDueño extends JFrame {

	private JPanel contentPane;
	private JTextField tf_nombre;
	private JTextField tf_apellido;
	private JTextField tf_dni;





	public RegistrarDueño(IApi api) {
		setTitle("registro de dueño");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 367, 243);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_registro = new JPanel();
		panel_registro.setBackground(SystemColor.window);
		panel_registro.setBounds(10, 11, 330, 183);
		contentPane.add(panel_registro);
		panel_registro.setLayout(null);
		
		JLabel lb_nombre = new JLabel("New label");
		lb_nombre.setBounds(10, 11, 102, 14);
		panel_registro.add(lb_nombre);
		
		tf_nombre = new JTextField();
		tf_nombre.setBounds(122, 8, 102, 20);
		panel_registro.add(tf_nombre);
		tf_nombre.setColumns(10);
		
		JLabel lb_apellido = new JLabel("New label");
		lb_apellido.setBounds(10, 54, 102, 14);
		panel_registro.add(lb_apellido);
		
		tf_apellido = new JTextField();
		tf_apellido.setBounds(122, 51, 102, 20);
		panel_registro.add(tf_apellido);
		tf_apellido.setColumns(10);
		
		JLabel lb_dni = new JLabel("New label");
		lb_dni.setBounds(10, 96, 102, 14);
		panel_registro.add(lb_dni);
		
		tf_dni = new JTextField();
		tf_dni.setBounds(122, 93, 102, 20);
		panel_registro.add(tf_dni);
		tf_dni.setColumns(10);
		
		JButton btn_limpiar = new JButton("New button");
		btn_limpiar.addActionListener((e)->{
			this.tf_apellido.setText("");
			this.tf_dni.setText("");
			this.tf_nombre.setText("");
			
			
		});
		btn_limpiar.setBounds(23, 136, 89, 23);
		panel_registro.add(btn_limpiar);
		
		JButton btn_aceptar = new JButton("New button");
		btn_aceptar.addActionListener((e)->{
			try {
				api.registrarDueño(this.tf_nombre.getText(),tf_apellido.getText(),tf_dni.getText());
			} catch (DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
		});
		btn_aceptar.setBounds(122, 136, 89, 23);
		panel_registro.add(btn_aceptar);
		
		JButton btn_cancelar = new JButton("New button");
		btn_cancelar.addActionListener((e)->{
			this.setVisible(false);
			dispose();
			
			
		});
		btn_cancelar.setBounds(221, 136, 89, 23);
		panel_registro.add(btn_cancelar);
	}
}
