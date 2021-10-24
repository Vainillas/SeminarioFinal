package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class DatosPersonalesDeDueño extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatosPersonalesDeDueño frame = new DatosPersonalesDeDueño();
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
	public DatosPersonalesDeDueño() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 849, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lbNombre = new JLabel("New label");
		lbNombre.setBounds(30, 26, 46, 14);
		panel.add(lbNombre);
		
		JLabel lbApellido = new JLabel("New label");
		lbApellido.setBounds(30, 61, 46, 14);
		panel.add(lbApellido);
		
		JLabel labelDni = new JLabel("New label");
		labelDni.setBounds(30, 101, 46, 14);
		panel.add(labelDni);
		
		JLabel lbCorreo = new JLabel("New label");
		lbCorreo.setBounds(30, 143, 46, 14);
		panel.add(lbCorreo);
	}
}
