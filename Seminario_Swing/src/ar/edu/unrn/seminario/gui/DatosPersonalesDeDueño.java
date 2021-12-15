package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.DueñoDTO;
import ar.edu.unrn.seminario.exceptions.AppException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class DatosPersonalesDeDueño extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel lbNombre;
	private JLabel lbApellido;
	private JLabel labelDni;
	private JLabel lbCorreo;
	private JPanel panelBotones;
	private JLabel lbNombreDueño;
	private JLabel lbApellidoDueño;
	private JLabel lbDniDueño;
	private JLabel lbCorreoDueño;
	private JButton btnMenuPrincipal;
	private JPanel panel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					ResourceBundle labels = ResourceBundle.getBundle("labels");
					DatosPersonalesDeDueño frame = new DatosPersonalesDeDueño(api, labels);
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
	public DatosPersonalesDeDueño(IApi api,ResourceBundle labels) {
		setTitle(labels.getString("datos.personales.de.dueño.titulo"));
		DueñoDTO d = null;
		try {
			d = api.obtenerDueñoActivo();
		} catch (AppException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),labels.getString("mensaje.error.general"),0);
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 419, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		panel.setLayout(null);
		contentPane.add(panel, BorderLayout.CENTER);
		
		lbNombre = new JLabel(labels.getString("datos.personales.de.dueño.label.nombre"));
		
		lbNombre.setHorizontalAlignment(SwingConstants.TRAILING);
		lbNombre.setBounds(80, 30, 120, 15);
		lbNombre.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(lbNombre);
		
		lbApellido = new JLabel(labels.getString("datos.personales.de.dueño.label.apellido"));
		lbApellido.setHorizontalAlignment(SwingConstants.TRAILING);
		lbApellido.setBounds(80, 60, 120, 15);
		lbApellido.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(lbApellido);
		
		labelDni = new JLabel("DNI:");
		labelDni.setHorizontalAlignment(SwingConstants.TRAILING);
		labelDni.setBounds(80, 90, 120, 15);
		labelDni.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(labelDni);
		
		lbCorreo = new JLabel(labels.getString("datos.personales.de.dueño.label.correo.electronico"));
		lbCorreo.setHorizontalAlignment(SwingConstants.TRAILING);
		lbCorreo.setBounds(80, 120, 120, 15);
		lbCorreo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(lbCorreo);
		
		panelBotones = new JPanel();
		panelBotones.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelBotones.setBounds(55, 180, 312, 33);
		panel.add(panelBotones);
		panelBotones.setLayout(new BorderLayout(0, 0));
		
		btnMenuPrincipal = new JButton(labels.getString("datos.personales.de.dueño.button.ir.al.menu.principal"));
		btnMenuPrincipal.addActionListener((e)->{
			setVisible(false);
			dispose();
		});
		panelBotones.add(btnMenuPrincipal);
		
		lbNombreDueño = new JLabel();
		lbNombreDueño.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lbNombreDueño.setText(d.getNombre());
		
		lbNombreDueño.setHorizontalAlignment(SwingConstants.CENTER);
		lbNombreDueño.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		lbNombreDueño.setBounds(210, 30, 142, 15);
		panel.add(lbNombreDueño);
		
		lbApellidoDueño = new JLabel(d.getApellido());
		lbApellidoDueño.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		lbApellidoDueño.setHorizontalAlignment(SwingConstants.CENTER);
		lbApellidoDueño.setBounds(210, 60, 142, 15);
		panel.add(lbApellidoDueño);
		
		lbDniDueño = new JLabel(d.getDni());
		lbDniDueño.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lbDniDueño.setHorizontalAlignment(SwingConstants.CENTER);
		lbDniDueño.setBounds(210, 90, 142, 15);
		panel.add(lbDniDueño);
		
		lbCorreoDueño = new JLabel(d.getCorreoElectronico());
		lbCorreoDueño.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lbCorreoDueño.setHorizontalAlignment(SwingConstants.CENTER);
		lbCorreoDueño.setBounds(210, 120, 142, 15);
		panel.add(lbCorreoDueño);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 240));
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 51), null, new Color(0, 0, 51), null));
		panel_1.setBounds(55, 11, 309, 152);
		panel.add(panel_1);
	}
}
