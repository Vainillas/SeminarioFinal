package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Panel;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.exceptions.AppException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JRadioButtonMenuItem;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipalDinamica extends JFrame {

	private JPanel contentPane;
	//ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
	private static GraphicsDevice device = GraphicsEnvironment
	        .getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					VentanaPrincipalDinamica frame = new VentanaPrincipalDinamica(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaPrincipalDinamica(IApi api) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		List<RolDTO> roles = null;
		setBounds(100, 100, 1056, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(1080, 1920, WIDTH, HEIGHT);
		setContentPane(contentPane);
		try {
			roles = api.obtenerRoles();
		} catch (AppException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		if(roles.get(0).getNombre().equals("PERSONAL")) {
			//mostrarPanelPersonal();
		}
		if(roles.get(0).getNombre().equals("COMUNIDAD")) {
			//mostrarPanelComunidad();
		}
		if(roles.get(0).getNombre().equals("PERSONAL")) {
			//mostrarPanelPersonal();
		}
		
		
		
		JPanel panelAdministrador = new JPanel();
		panelAdministrador.setBounds(530, 11, 500, 240);
		panelAdministrador.setVisible(false);
		contentPane.setLayout(null);
		contentPane.add(panelAdministrador);
		JPanel panelPersonal = new JPanel();
		panelPersonal.setBounds(10, 11, 500, 240);
		panelPersonal.setVisible(false);
		contentPane.add(panelPersonal);
		panelPersonal.setLayout(new BorderLayout(0, 0));
		
		JMenuBar mbPersonal = new JMenuBar();
		panelPersonal.add(mbPersonal, BorderLayout.NORTH);
		
		JMenu mnOrdenes = new JMenu();
		mnOrdenes.setText("new menu");
		mbPersonal.add(mnOrdenes);
		
		JMenuItem mntmPerListarOrdenesDeRetiro = new JMenuItem();
		mnOrdenes.add(mntmPerListarOrdenesDeRetiro);
		
		JMenu MnDatosDelPersonal = new JMenu();
		MnDatosDelPersonal.setText("new mnu");
		mbPersonal.add(MnDatosDelPersonal);
		
		JMenuItem mntmVivPerDatosDelPersonal = new JMenuItem();
		MnDatosDelPersonal.add(mntmVivPerDatosDelPersonal);
		
		JMenu mnConfiguracionPersonal = new JMenu(); //$NON-NLS-1$
		mnConfiguracionPersonal.setText("new menu");
		mbPersonal.add(mnConfiguracionPersonal);
		
		JMenuItem mntmPantallaCompletaPersonal = new JMenuItem("New menu item");
		mnConfiguracionPersonal.add(mntmPantallaCompletaPersonal);
		
		JMenuItem mntmConfiguracionPersonal = new JMenuItem(); //$NON-NLS-1$
		mntmConfiguracionPersonal.setText("New menu item");
		mnConfiguracionPersonal.add(mntmConfiguracionPersonal);
		
		panelAdministrador.setVisible(true);
		panelAdministrador.setLayout(new BorderLayout(0, 0));
		JMenuBar mbAdministrador = new JMenuBar();
		panelAdministrador.add(mbAdministrador, BorderLayout.NORTH);
		
		JMenu mnConfiguracion = new JMenu("New menu");
		mbAdministrador.add(mnConfiguracion);
		
		JMenuItem mntmSalirAdmin = new JMenuItem(); 
		mnConfiguracion.add(mntmSalirAdmin);
		
		JMenuItem mntmPantallaCompletaAdmin = new JMenuItem("New menu item");
		mntmPantallaCompletaAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(MAXIMIZED_BOTH);
				
				

			}
		});
		mnConfiguracion.add(mntmPantallaCompletaAdmin);
		
		JMenu mUsuarios = new JMenu("New menu");
		mbAdministrador.add(mUsuarios);
		
		JMenuItem mntmAdmListadoUsuario = new JMenuItem("New menu item");
		mUsuarios.add(mntmAdmListadoUsuario);
		
		JMenuItem mntmAdmAltaUsuario = new JMenuItem(); //$NON-NLS-1$
		mUsuarios.add(mntmAdmAltaUsuario);
		
		JMenu mnViviendas = new JMenu("New menu");
		mnViviendas.setBackground(new Color(240, 240, 240));
		mbAdministrador.add(mnViviendas);
		
		JMenuItem mntmiAdmListadoViviendas = new JMenuItem("New menu item");
		mnViviendas.add(mntmiAdmListadoViviendas);
		
		JMenu mnOrdenesDeRetiro = new JMenu("New menu");
		mbAdministrador.add(mnOrdenesDeRetiro);
		
		JMenuItem mntmAdmListadoOrdenesDeRetiros = new JMenuItem("New menu item");
		mnOrdenesDeRetiro.add(mntmAdmListadoOrdenesDeRetiros);
		
		JMenu mnPedidosDeRetiro = new JMenu("New menu");
		mbAdministrador.add(mnPedidosDeRetiro);
		
		JMenuItem mntmAdmListadoPedidosDeRetiros = new JMenuItem("New menu item");
		mnPedidosDeRetiro.add(mntmAdmListadoPedidosDeRetiros);
		
		JMenu mnDueño = new JMenu("New menu");
		mbAdministrador.add(mnDueño);
		
		JMenuItem mntmAdmListadoDueños = new JMenuItem("New menu item");
		mnDueño.add(mntmAdmListadoDueños);
		
		JMenu mnPersonal = new JMenu("New menu");
		mbAdministrador.add(mnPersonal);
		
		JMenuItem mntmAdmListadoPersonal = new JMenuItem("New menu item");
		mnPersonal.add(mntmAdmListadoPersonal);
		
		JPanel panelDueño = new JPanel();
		panelDueño.setBounds(10, 261, 500, 240);
		contentPane.add(panelDueño);
		panelDueño.setLayout(new BorderLayout(0, 0));
		
		JMenuBar mbDueño = new JMenuBar();
		panelDueño.add(mbDueño, BorderLayout.NORTH);
		
		JMenu mnViviendasDueño = new JMenu("New menu");
		mbDueño.add(mnViviendasDueño);
		
		JMenuItem mntmListarViviendasDueño = new JMenuItem("New menu item");
		mnViviendasDueño.add(mntmListarViviendasDueño);
		
		JMenu menuDatosDueño = new JMenu("New menu");
		mbDueño.add(menuDatosDueño);
		
		JMenuItem mntmVivDatosDelDueño = new JMenuItem("New menu item");
		menuDatosDueño.add(mntmVivDatosDelDueño);
		
		JMenu mnConfiguracionDueño = new JMenu("New menu");
		mbDueño.add(mnConfiguracionDueño);
		
		JMenuItem mntnSalirDueño = new JMenuItem("New menu item");
		mnConfiguracionDueño.add(mntnSalirDueño);
		
		JMenuItem mntmPantallaCompletaDueño = new JMenuItem("New menu item");
		mnConfiguracionDueño.add(mntmPantallaCompletaDueño);
	}

	private void mostrarPanelPersonal() {
		// TODO Esbozo de método generado automáticamente
		
	}
}
