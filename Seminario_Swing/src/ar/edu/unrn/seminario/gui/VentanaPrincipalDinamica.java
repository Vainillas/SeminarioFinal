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
	ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
	private JMenuItem mntmPantallaNormalPersonal;
	
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
		setTitle(labels.getString("ventana.principal.dinamica.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		List<RolDTO> roles = null;
		setBounds(100, 100, 1161, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(1080, 1920, WIDTH, HEIGHT);
		setContentPane(contentPane);
		try {
			roles = api.obtenerRoles();
		} catch (AppException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),labels.getString("ventana.principal.dinamica.mensaje.error"),JOptionPane.ERROR_MESSAGE);
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
		panelAdministrador.setBounds(560, 11, 565, 240);
		panelAdministrador.setVisible(false);
		contentPane.setLayout(null);
		contentPane.add(panelAdministrador);
		JPanel panelPersonal = new JPanel();
		panelPersonal.setBounds(10, 11, 540, 240);
		panelPersonal.setVisible(true);
		contentPane.add(panelPersonal);
		panelPersonal.setLayout(new BorderLayout(0, 0));
		
		JMenuBar mbPersonal = new JMenuBar();
		panelPersonal.add(mbPersonal, BorderLayout.NORTH);
		
		JMenu mnOrdenesPersonal = new JMenu(labels.getString("ventana.principal.dinamica.menu.ordenes"));
		mbPersonal.add(mnOrdenesPersonal);
		
		JMenuItem mntmPerListarOrdenesDeRetiro = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.personal.lista.ordenes"));
		mntmPerListarOrdenesDeRetiro.addActionListener((e)->{
			ListadoOrdenesDeRetiroDelPersonal listado = new ListadoOrdenesDeRetiroDelPersonal(api);
			listado.setVisible(true);
		});
		mnOrdenesPersonal.add(mntmPerListarOrdenesDeRetiro);
		
		JMenu MnDatosPersonal = new JMenu(labels.getString("ventana.principal.dinamica.menu.datos.personal"));
		
		mbPersonal.add(MnDatosPersonal);
		
		JMenuItem mntmPerDatosDelPersonal = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.viviendas.datos.personal"));
		mntmPerDatosDelPersonal.addActionListener((e)->{
			
		});
		MnDatosPersonal.add(mntmPerDatosDelPersonal);
		
		JMenu mnConfiguracionPersonal = new JMenu(labels.getString("ventana.principal.dinamica.menu.personal.configuracion"));
		
		mbPersonal.add(mnConfiguracionPersonal);
		
		JMenuItem mntmPantallaCompletaPersonal = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.personal.configuracion.pantalla.completa"));
		mntmPantallaCompletaPersonal.addActionListener((e)->{
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
			mntmPantallaNormalPersonal.setVisible(true);
			mntmPantallaCompletaPersonal.setVisible(false);
			
		});
		mnConfiguracionPersonal.add(mntmPantallaCompletaPersonal);
		
		mntmPantallaNormalPersonal = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.personal.configuracion.pantalla.normal")); 
		mntmPantallaNormalPersonal.addActionListener((e)->{
			this.setExtendedState(JFrame.NORMAL);	
			mntmPantallaNormalPersonal.setVisible(false);
			mntmPantallaCompletaPersonal.setVisible(true);
		});
		
		mnConfiguracionPersonal.add(mntmPantallaNormalPersonal);
		mntmPantallaNormalPersonal.setVisible(false);
		
		JMenuItem mntmConfiguracionPersonal = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.personal.configuracion.personal")); 
		
		mnConfiguracionPersonal.add(mntmConfiguracionPersonal);
		panelAdministrador.setVisible(true);
		panelAdministrador.setLayout(new BorderLayout(0, 0));
		JMenuBar mbAdministrador = new JMenuBar();
		panelAdministrador.add(mbAdministrador, BorderLayout.NORTH);
		
		JMenu mUsuariosAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.usuarios.administrador"));
		mbAdministrador.add(mUsuariosAdministrador);
		
		JMenuItem mntmAdmListadoUsuario = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.usuarios"));
		mUsuariosAdministrador.add(mntmAdmListadoUsuario);
		
		JMenuItem mntmAdmAltaUsuario = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.alta.usuario.administrador")); 
		mUsuariosAdministrador.add(mntmAdmAltaUsuario);
		
		JMenu mnViviendasAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.viviendas.administrador"));
		mnViviendasAdministrador.setBackground(new Color(240, 240, 240));
		mbAdministrador.add(mnViviendasAdministrador);
		
		JMenuItem mntmiAdmListadoViviendas = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.viviendas.administrador"));
		mnViviendasAdministrador.add(mntmiAdmListadoViviendas);
		
		JMenuItem mntmAltaViviendaAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.alta.vivienda.administrador")); 
		mnViviendasAdministrador.add(mntmAltaViviendaAdministrador);
		
		JMenu mnOrdenesDeRetiroAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.ordenes.de.retiro.administrador"));
		mbAdministrador.add(mnOrdenesDeRetiroAdministrador);
		
		JMenuItem mntmAdmListadoOrdenesDeRetiros = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.ordenes.de.retiro.administrador"));
		mnOrdenesDeRetiroAdministrador.add(mntmAdmListadoOrdenesDeRetiros);
		
		JMenuItem mntmGenerarOrdenDeRetiroAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.generar.orden.de.retiro.administrador")); 
		mnOrdenesDeRetiroAdministrador.add(mntmGenerarOrdenDeRetiroAdministrador);
		
		JMenu mnPedidosDeRetiroAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.pedidos.de.retiro.administrador"));
		mbAdministrador.add(mnPedidosDeRetiroAdministrador);
		
		JMenuItem mntmAdmListadoPedidosDeRetiros = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.pedidos.de.retiro.administrador"));
		mnPedidosDeRetiroAdministrador.add(mntmAdmListadoPedidosDeRetiros);
		
		JMenuItem mntmGenerarPedidoDeRetiroAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.generar.pedido.de.retiro.administrador")); 
		mnPedidosDeRetiroAdministrador.add(mntmGenerarPedidoDeRetiroAdministrador);
		
		JMenu mnDueñoAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.dueño.administrador"));
		mbAdministrador.add(mnDueñoAdministrador);
		
		JMenuItem mntmAdmListadoDueños = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.dueños.administrador"));
		mnDueñoAdministrador.add(mntmAdmListadoDueños);
		
		JMenuItem mntmRegistrarDueñoAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.registrar.dueño.administrador")); 
		mnDueñoAdministrador.add(mntmRegistrarDueñoAdministrador);
		
		JMenu mnPersonalAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.personal.administrador"));
		mbAdministrador.add(mnPersonalAdministrador);
		
		JMenuItem mntmAdmListadoPersonal = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.personal.administrador"));
		mnPersonalAdministrador.add(mntmAdmListadoPersonal);
		
		JMenuItem mntmAltaPersonalAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.alta.personal.administrador")); 
		
		
		mnPersonalAdministrador.add(mntmAltaPersonalAdministrador);
		
		JMenu mnConfiguracionAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.configuracion.administrador"));
		mbAdministrador.add(mnConfiguracionAdministrador);
		
		JMenuItem mntmSalirAdmin = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.salir.administrador")); 
		mnConfiguracionAdministrador.add(mntmSalirAdmin);
		
		JMenuItem mntmPantallaCompletaAdmin = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.personal.configuracion.pantalla.completa"));
		mntmPantallaCompletaAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(MAXIMIZED_BOTH);
				
				

			}
		});
		mnConfiguracionAdministrador.add(mntmPantallaCompletaAdmin);
		
		JPanel panelDueño = new JPanel();
		panelDueño.setBounds(10, 262, 540, 240);
		contentPane.add(panelDueño);
		panelDueño.setLayout(new BorderLayout(0, 0));
		
		JMenuBar mbDueño = new JMenuBar();
		panelDueño.add(mbDueño, BorderLayout.NORTH);
		
		JMenu mnViviendasDueño = new JMenu(labels.getString("ventana.principal.dinamica.menu.viviendas.dueño"));
		mbDueño.add(mnViviendasDueño);
		
		JMenuItem mntmListarViviendasDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listar.viviendas.dueño"));
		mnViviendasDueño.add(mntmListarViviendasDueño);
		
		JMenu menuDatosDueño = new JMenu(labels.getString("ventana.principal.dinamica.menu.datos.dueño"));
		mbDueño.add(menuDatosDueño);
		
		JMenuItem mntmVivDatosDelDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.datos.del.dueño"));
		menuDatosDueño.add(mntmVivDatosDelDueño);
		
		JMenu mnConfiguracionDueño = new JMenu(labels.getString("ventana.principal.dinamica.menu.configuracion.dueño"));
		mbDueño.add(mnConfiguracionDueño);
		
		JMenuItem mntnSalirDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.salir.dueño"));
		mnConfiguracionDueño.add(mntnSalirDueño);
		
		JMenuItem mntmPantallaCompletaDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.pantalla.completa.dueño"));
		mntmPantallaCompletaDueño.addActionListener((e)->{
			this.setExtendedState(JFrame.NORMAL);
			
		});
		mnConfiguracionDueño.add(mntmPantallaCompletaDueño);
		
		JMenu mnPedidoDeRetiroDueño = new JMenu(labels.getString("ventana.principal.dinamica.menu.pedido.de.retiro.dueño")); 
		mbDueño.add(mnPedidoDeRetiroDueño);
		
		JMenuItem mntmListarPedidosDeRetiroDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listar.pedido.de.retiro.dueño")); 
		mnPedidoDeRetiroDueño.add(mntmListarPedidosDeRetiroDueño);
		
		JMenuItem mntmGenerarPedidoDeRetiroDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.generar.pedido.de.retiro.dueño")); //$NON-NLS-1$
		mnPedidoDeRetiroDueño.add(mntmGenerarPedidoDeRetiroDueño);
	}

	private void mostrarPanelPersonal() {
		// TODO Esbozo de método generado automáticamente
		
	}
}
