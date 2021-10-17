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
		setBounds(100, 100, 972, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
		
		JPanel panelAdministrador = new JPanel();
		panelAdministrador.setLayout(null);
		panelAdministrador.setBounds(488, 11, 458, 239);
		panelAdministrador.setVisible(false);
		contentPane.add(panelAdministrador);
		JPanel panelPersonal = new JPanel();
		panelPersonal.setVisible(false);
		panelPersonal.setBounds(10, 11, 458, 239);
		contentPane.add(panelPersonal);
		panelPersonal.setLayout(new BorderLayout(0, 0));
		
		JMenuBar mbPersonal = new JMenuBar();
		panelPersonal.add(mbPersonal, BorderLayout.NORTH);
		
		JMenu mnOrdenes = new JMenu(labels.getString("ventana.principal.dinamica.menu.ordenes"));
		mbPersonal.add(mnOrdenes);
		
		JMenuItem mntmPerListarOrdenesDeRetiro = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.personal.listar.ordenes.de.retiro"));
		mnOrdenes.add(mntmPerListarOrdenesDeRetiro);
		
		JMenu MnDatosDelPersonal = new JMenu(labels.getString("ventana.principal.dinamica.menu.datos.del.personal"));
		mbPersonal.add(MnDatosDelPersonal);
		
		JMenuItem mntmVivPerDatosDelPersonal = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.vivienda.datos.del.personal"));
		MnDatosDelPersonal.add(mntmVivPerDatosDelPersonal);
		
		panelAdministrador.setVisible(true);
		JMenuBar mbAdministrador = new JMenuBar();
		mbAdministrador.setBounds(0, 0, 448, 22);
		panelAdministrador.add(mbAdministrador);
		
		JMenu mUsuarios = new JMenu("New menu");
		mbAdministrador.add(mUsuarios);
		
		JMenuItem minAdmListadoUsuario = new JMenuItem("New menu item");
		mUsuarios.add(minAdmListadoUsuario);
		
		JMenu mnViviendas = new JMenu("New menu");
		mbAdministrador.add(mnViviendas);
		
		JMenuItem mniAdmListadoViviendas = new JMenuItem("New menu item");
		mnViviendas.add(mniAdmListadoViviendas);
		
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
		panelDueño.setBounds(10, 261, 458, 239);
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
	}
}
