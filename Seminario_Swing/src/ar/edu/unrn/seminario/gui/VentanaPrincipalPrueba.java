package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;

public class VentanaPrincipalPrueba extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					VentanaPrincipalPrueba frame = new VentanaPrincipalPrueba(api);
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
	public VentanaPrincipalPrueba(IApi api) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panelAdministrador = new JPanel();
		panelAdministrador.setLayout(null);
		panelAdministrador.setBounds(10, 11, 414, 239);
		panelAdministrador.setVisible(false);
		contentPane.add(panelAdministrador);
		JPanel panelPersonal = new JPanel();
		panelPersonal.setLayout(null);
		panelPersonal.setVisible(false);
		panelPersonal.setBounds(10, 11, 414, 239);
		contentPane.add(panelPersonal);
		
		JLabel lblNewLabel_2 = new JLabel("panel personal");
		lblNewLabel_2.setBounds(50, 150, 46, 14);
		panelPersonal.add(lblNewLabel_2);
		
		if(api.obtenerRolUsuarioActivo().equals("ADMIN")) {//nose si esta bien esto
			panelAdministrador.setVisible(true);
		}
		if(api.obtenerRolUsuarioActivo().equals("Comunidad")){//
			panelPersonal.setVisible(true);//se tendria que mostrar la ventana de usuario, esto esta mal por ahora
			
		}
		
		JMenuBar mbPrincipal = new JMenuBar();
		mbPrincipal.setBounds(0, 0, 404, 22);
		panelAdministrador.add(mbPrincipal);
		
		JMenu mUsuarios = new JMenu("New menu");
		mbPrincipal.add(mUsuarios);
		
		JMenuItem minListadoUsuario = new JMenuItem("New menu item");
		mUsuarios.add(minListadoUsuario);
		
		JMenu mnViviendas = new JMenu("New menu");
		mbPrincipal.add(mnViviendas);
		
		JMenuItem mniListadoVivienda = new JMenuItem("New menu item");
		mnViviendas.add(mniListadoVivienda);
		
		JMenu mnNewMenu = new JMenu("New menu");
		mbPrincipal.add(mnNewMenu);
		
		JLabel lblNewLabel = new JLabel("label administrador");
		lblNewLabel.setBounds(175, 115, 117, 14);
		panelAdministrador.add(lblNewLabel);
	}
}
