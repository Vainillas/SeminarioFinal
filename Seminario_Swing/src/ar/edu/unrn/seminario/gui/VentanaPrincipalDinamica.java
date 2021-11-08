package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class VentanaPrincipalDinamica extends JFrame {
	private static final long serialVersionUID = 4771947449650351645L;
	private JPanel contentPane;
	private JMenuItem mntmPantallaNormalPersonal;
	private JPanel panelAdministrador = new JPanel();
	private JPanel panelPersonal = new JPanel();
	private JPanel panelDueño = new JPanel();
	
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
					VentanaPrincipalDinamica frame = new VentanaPrincipalDinamica(api,labels);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaPrincipalDinamica(IApi api, ResourceBundle labels) {
		String rol = "ADMINISTRADOR";
		//String rol = api.obtenerRolUsuarioActivo();

		if(rol.equals("PERSONAL")) {
			panelPersonal.setVisible(true);
			panelDueño.setVisible(false);
			panelAdministrador.setVisible(false);
		}
		
		if(rol.equals("ADMINISTRADOR")) {
			panelAdministrador.setVisible(true);
			panelPersonal.setVisible(false);
			panelDueño.setVisible(false);
		}
		if(rol.equals("COMUNIDAD")) {
			panelDueño.setVisible(true);
			panelAdministrador.setVisible(false);
			panelPersonal.setVisible(false);
		}
		
		setTitle(labels.getString("ventana.principal.dinamica.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		List<RolDTO> roles = null;
		setBounds(100, 100, 1315, 533);
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
		
		
		
		panelAdministrador.setBounds(560, 11, 630, 240);
		contentPane.setLayout(null);
		contentPane.add(panelAdministrador);

		panelPersonal.setBounds(10, 11, 540, 240);
		contentPane.add(panelPersonal);
		panelPersonal.setLayout(new BorderLayout(0, 0));
		JMenuBar mbPersonal = new JMenuBar();
		panelPersonal.add(mbPersonal, BorderLayout.NORTH);
		
		JMenu mnOrdenesPersonal = new JMenu(labels.getString("ventana.principal.dinamica.menu.ordenes"));
		mbPersonal.add(mnOrdenesPersonal);
		
		JMenuItem mntmPerListarOrdenesDeRetiro = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.personal.lista.ordenes"));
		mntmPerListarOrdenesDeRetiro.addActionListener((e)->{
			ListadoDeOrdenesDeRetiro listado = new ListadoDeOrdenesDeRetiro(api,labels);
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
		panelAdministrador.setLayout(new BorderLayout(0, 0));
		JMenuBar mbAdministrador = new JMenuBar();
		panelAdministrador.add(mbAdministrador, BorderLayout.NORTH);
		
		JMenu mUsuariosAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.usuarios.administrador"));
		mbAdministrador.add(mUsuariosAdministrador);
		
		JMenuItem mntmAdmListadoUsuario = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.usuarios"));
		mntmAdmListadoUsuario.addActionListener((e)->{
			ListadoDeUsuarios listUsuario = new ListadoDeUsuarios(api, labels);
			listUsuario.setVisible(true);
			
			
		});
		mUsuariosAdministrador.add(mntmAdmListadoUsuario);
		
		JMenuItem mntmAdmAltaUsuario = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.alta.usuario.administrador")); 
		mntmAdmAltaUsuario.addActionListener((e)->{
			AltaUsuario usuario = new AltaUsuario(api,labels);
			usuario.setVisible(true);
		});
		mUsuariosAdministrador.add(mntmAdmAltaUsuario);
		
		JMenu mnViviendasAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.viviendas.administrador"));
		mnViviendasAdministrador.setBackground(new Color(240, 240, 240));
		mbAdministrador.add(mnViviendasAdministrador);
		
		JMenuItem mntmiAdmListadoViviendas = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.viviendas.administrador"));
		mntmiAdmListadoViviendas.addActionListener((e)->{
			ListadoDeViviendas listadoViviendas = new ListadoDeViviendas(api,labels);
			listadoViviendas.setVisible(true);
			
		});
		mnViviendasAdministrador.add(mntmiAdmListadoViviendas);
		
		JMenuItem mntmRegistrarViviendaAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.alta.vivienda.administrador"));
		mntmRegistrarViviendaAdministrador.addActionListener((e)->{
			RegistroVivienda vivienda = new RegistroVivienda(api,labels);
			vivienda.setVisible(true);
			
			
		});
		
		mnViviendasAdministrador.add(mntmRegistrarViviendaAdministrador);
		
		JMenu mnOrdenesDeRetiroAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.ordenes.de.retiro.administrador"));
		mbAdministrador.add(mnOrdenesDeRetiroAdministrador);
		
		JMenuItem mntmAdmListadoOrdenesDeRetiros = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.ordenes.de.retiro.administrador"));
		mntmAdmListadoOrdenesDeRetiros.addActionListener((e)->{
			ListadoDeOrdenesDeRetiro listado = new ListadoDeOrdenesDeRetiro(api,labels);
			listado.setVisible(true);
			
			
		});
		mnOrdenesDeRetiroAdministrador.add(mntmAdmListadoOrdenesDeRetiros);
		
		JMenuItem mntmGenerarOrdenDeRetiroAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.generar.orden.de.retiro.administrador")); 
		mntmGenerarOrdenDeRetiroAdministrador.addActionListener((e)->{
			GenerarOrdenDeRetiro ordenDeRetiro = new GenerarOrdenDeRetiro(api,labels);
			ordenDeRetiro.setVisible(true);
			ordenDeRetiro.setLocationRelativeTo(null); 
		});
		
		JMenuItem mntmAdmGenerarRegistroVisita = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.generar.registro.visita.administrador")); //$--$
		mntmAdmGenerarRegistroVisita.addActionListener((e)->{
			GenerarRegistroDeVisita registroVisita = new GenerarRegistroDeVisita(api,labels );
			registroVisita.setVisible(true);

		});
		mnOrdenesDeRetiroAdministrador.add(mntmAdmGenerarRegistroVisita);
		mnOrdenesDeRetiroAdministrador.add(mntmGenerarOrdenDeRetiroAdministrador);
		
		JMenu mnPedidosDeRetiroAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.pedidos.de.retiro.administrador"));
		mbAdministrador.add(mnPedidosDeRetiroAdministrador);
		
		JMenuItem mntmAdmListadoPedidosDeRetiros = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.pedidos.de.retiro.administrador"));
		mntmAdmListadoPedidosDeRetiros.addActionListener((e)->{
			ListadoDePedidosDeRetiro listadoPedidos = new ListadoDePedidosDeRetiro(api, labels);
			listadoPedidos.setVisible(true);
			
		});
		mnPedidosDeRetiroAdministrador.add(mntmAdmListadoPedidosDeRetiros);
		
		JMenuItem mntmGenerarPedidoDeRetiroAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.generar.pedido.de.retiro.administrador")); 
		mntmGenerarPedidoDeRetiroAdministrador.addActionListener((e)->{
			GenerarPedidoDeRetiro pedido = new GenerarPedidoDeRetiro(api,labels);
			pedido.setVisible(true);
			
		});
		mnPedidosDeRetiroAdministrador.add(mntmGenerarPedidoDeRetiroAdministrador);
		
		JMenu mnDueñoAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.dueño.administrador"));
		mbAdministrador.add(mnDueñoAdministrador);
		
		JMenuItem mntmAdmListadoDueños = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.dueños.administrador"));
		mnDueñoAdministrador.add(mntmAdmListadoDueños);
		
		JMenuItem mntmRegistrarDueñoAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.registrar.dueño.administrador")); 
		mntmRegistrarDueñoAdministrador.addActionListener((e)->{
			RegistrarDueño registro =  new RegistrarDueño(api,labels);
			registro.setVisible(true);
			
		});
		mnDueñoAdministrador.add(mntmRegistrarDueñoAdministrador);
		
		JMenu mnPersonalAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.personal.administrador"));
		mbAdministrador.add(mnPersonalAdministrador);
		
		JMenuItem mntmAdmListadoPersonal = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listado.personal.administrador"));
		mntmAdmListadoPersonal.addActionListener((e)->{
			ListadoDePersonal listadoPersonal = new ListadoDePersonal(api,labels);
			listadoPersonal.setVisible(true);
		});
		mnPersonalAdministrador.add(mntmAdmListadoPersonal);
		
		JMenuItem mntmRegistrarPersonalAdministrador = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.alta.personal.administrador")); 
		mntmRegistrarPersonalAdministrador.addActionListener((e)->{
			RegistrarPersonal personalNuevo = new RegistrarPersonal(api,labels);
			personalNuevo.setVisible(true);
			});
		
		
		mnPersonalAdministrador.add(mntmRegistrarPersonalAdministrador);
		
		JMenu mnConfiguracionAdministrador = new JMenu(labels.getString("ventana.principal.dinamica.menu.configuracion.administrador"));
		mbAdministrador.add(mnConfiguracionAdministrador);
		
		JMenuItem mntmSalirAdmin = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.salir.administrador")); 
		mnConfiguracionAdministrador.add(mntmSalirAdmin);
		JMenuItem mntmPantallaEstandar = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.personal.configuracion.pantalla.estandar"));
		mntmPantallaEstandar.setVisible(false);
		JMenuItem mntmPantallaCompletaAdmin = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.personal.configuracion.pantalla.completa"));
		mntmPantallaCompletaAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(MAXIMIZED_BOTH);
				mntmPantallaCompletaAdmin.setVisible(false);
				mntmPantallaEstandar.setVisible(true);
				

			}
		});
		mntmPantallaEstandar.addActionListener((e)->{
			setExtendedState(JFrame.NORMAL);
			mntmPantallaCompletaAdmin.setVisible(true);
			mntmPantallaEstandar.setVisible(false);
		});

		mnConfiguracionAdministrador.add(mntmPantallaEstandar);
		mnConfiguracionAdministrador.add(mntmPantallaCompletaAdmin);
		
		JMenu mnCampaña = new JMenu(labels.getString("ventana.principal.dinamica.menu.campaña")); 
		mbAdministrador.add(mnCampaña);
		
		JMenuItem mntmListarCampañas = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.generar.campaña")); //$NON-NLS-1$
		mnCampaña.add(mntmListarCampañas);
		
		JMenuItem mntmGenerarCampaña = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listar.campañas")); //$NON-NLS-1$
		mnCampaña.add(mntmGenerarCampaña);
		
		panelDueño.setBounds(10, 262, 540, 240);
		contentPane.add(panelDueño);
		panelDueño.setLayout(new BorderLayout(0, 0));
		
		//;api.existeDueño();
		
		JMenuBar mbDueño = new JMenuBar();
		panelDueño.add(mbDueño, BorderLayout.NORTH);
		
		JMenu mnViviendasDueño = new JMenu(labels.getString("ventana.principal.dinamica.menu.viviendas.dueño"));
		mbDueño.add(mnViviendasDueño);
		
		JMenuItem mntmListarViviendasDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listar.viviendas.dueño"));
		mnViviendasDueño.add(mntmListarViviendasDueño);
		
		JMenu menuDatosDueño = new JMenu(labels.getString("ventana.principal.dinamica.menu.datos.dueño"));
		mbDueño.add(menuDatosDueño);
		
		JMenuItem mntmDatosDelDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.datos.del.dueño"));
		menuDatosDueño.add(mntmDatosDelDueño);
		
		JMenu mnConfiguracionDueño = new JMenu(labels.getString("ventana.principal.dinamica.menu.configuracion.dueño"));
		mbDueño.add(mnConfiguracionDueño);
		
		JMenuItem mntnSalirDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.salir.dueño"));
		mntnSalirDueño.addActionListener((e)->{
			setVisible(false);
			dispose();
			
		});
		
		mnConfiguracionDueño.add(mntnSalirDueño);
		JMenuItem mntmPantallaEstandarDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.pantalla.estandar.dueño")); 
		JMenuItem mntmPantallaCompletaDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.pantalla.completa.dueño"));
		mntmPantallaEstandarDueño.setVisible(false);
		mntmPantallaCompletaDueño.addActionListener((e)->{
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			mntmPantallaCompletaDueño.setVisible(false);
			mntmPantallaEstandarDueño.setVisible(true);
			
			
		});
		
		mntmPantallaEstandarDueño.addActionListener((e)->{
			this.setExtendedState(JFrame.NORMAL);
			mntmPantallaCompletaDueño.setVisible(true);
			mntmPantallaEstandarDueño.setVisible(false);
			
			
		});
		mnConfiguracionDueño.add(mntmPantallaEstandarDueño);
		mnConfiguracionDueño.add(mntmPantallaCompletaDueño);
		
		JMenu mnPedidoDeRetiroDueño = new JMenu(labels.getString("ventana.principal.dinamica.menu.pedido.de.retiro.dueño")); 
		mbDueño.add(mnPedidoDeRetiroDueño);
		
		JMenuItem mntmListarPedidosDeRetiroDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.listar.pedido.de.retiro.dueño")); 
		mnPedidoDeRetiroDueño.add(mntmListarPedidosDeRetiroDueño);
		
		JMenuItem mntmGenerarPedidoDeRetiroDueño = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.generar.pedido.de.retiro.dueño")); //$NON-NLS-1$
		mnPedidoDeRetiroDueño.add(mntmGenerarPedidoDeRetiroDueño);
		
		JPanel panelDueñoNoRegistrado = new JPanel();
		panelDueñoNoRegistrado.setVisible(false);
		panelDueñoNoRegistrado.setBounds(560, 262, 402, 204);
		contentPane.add(panelDueñoNoRegistrado);
		panelDueñoNoRegistrado.setLayout(new BorderLayout(0, 0));
		
		JMenuBar mbDueñoNoRegistrado = new JMenuBar();
		panelDueñoNoRegistrado.add(mbDueñoNoRegistrado, BorderLayout.NORTH);
		
		JMenu mnDueñoNoRegistrado = new JMenu(labels.getString("ventana.principal.dinamica.menu.registro")); 
		mbDueñoNoRegistrado.add(mnDueñoNoRegistrado);
		
		JMenuItem mntmRegistrarDueñoNoRegistrado = new JMenuItem(labels.getString("ventana.principal.dinamica.menu.item.registrarse")); 
		mnDueñoNoRegistrado.add(mntmRegistrarDueñoNoRegistrado);
	}
}
