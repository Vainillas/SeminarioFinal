package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;



import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;



import ar.edu.unrn.seminario.api.IApi;

import ar.edu.unrn.seminario.api.PersistenceApi;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {
	
	ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//IApi api = new MemoryApi();
					IApi api = new PersistenceApi();
					VentanaPrincipal frame = new VentanaPrincipal(api);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public VentanaPrincipal(IApi api) {
		setTitle("Ventana Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 302);
		//i18n
		ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("en"));
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuUsuarios = new JMenu(labels.getString("ventana.principal.menu.usuarios"));
		menuBar.add(menuUsuarios);

		JMenuItem menuItemAltaModificacion = new JMenuItem(labels.getString("ventana.principal.menu.alta.modificacion"));
		menuItemAltaModificacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					AltaUsuario alta = new AltaUsuario(api);
					alta.setLocationRelativeTo(null);
					alta.setVisible(true);
				}
			
		});


		menuUsuarios.add(menuItemAltaModificacion);
		
		JMenuItem menuItemListadoUsuarios = new JMenuItem(labels.getString("ventana.principal.menu.item.listado"));
		menuItemListadoUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoUsuario listado= new ListadoUsuario(api);
				listado.setLocationRelativeTo(null);
				listado.setVisible(true);
			}
		});
		menuUsuarios.add(menuItemListadoUsuarios);
		
		JMenu menuConfiguracion = new JMenu(labels.getString("ventana.principal.menu.configuracion"));
		
		menuBar.add(menuConfiguracion);
		
		JMenuItem menuItemSalir = new JMenuItem(labels.getString("ventana.principal.menu.item.salir"));
		menuItemSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
				dispose();
			}
		});
		menuConfiguracion.add(menuItemSalir);
		
		JMenu menuIdiomas = new JMenu(labels.getString("ventana.principal.menu.idiomas"));
		menuConfiguracion.add(menuIdiomas);
		
		JMenuItem menuItemEspañol = new JMenuItem(labels.getString("ventana.principal.menu.item.español"));
		menuItemEspañol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ResourceBundle labels = ResourceBundle.getBundle("labels");
				setVisible(false);
				dispose();
				setVisible(true);
				ResourceBundle labels = ResourceBundle.getBundle("labels");
			}
		});
		
		menuIdiomas.add(menuItemEspañol);
		
		
		JMenuItem menuItemEnglish = new JMenuItem(labels.getString("ventana.principal.menu.item.ingles")); 
		menuItemEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("en"));
				setVisible(false);
				dispose();
				setVisible(true);
			}
		});
		menuIdiomas.add(menuItemEnglish);
		
		JMenu menuViviendas = new JMenu(labels.getString("ventana.principal.menu.viviendas"));
		menuBar.add(menuViviendas);
		
		JMenuItem menuItemListadoViviendas = new JMenuItem(labels.getString("ventana.principal.menu.item.listado"));
		menuItemListadoViviendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoVivienda listado= new ListadoVivienda(api);
				listado.setLocationRelativeTo(null);
				listado.setVisible(true);
			}
		});
		
		menuViviendas.add(menuItemListadoViviendas);
		
		JMenuItem menuItemRegistrarViviendas = new JMenuItem(labels.getString("ventana.principal.menu.item.registrar.vivienda"));
		menuItemRegistrarViviendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				RegistroVivienda casa = new RegistroVivienda(api);
				casa.setVisible(true);
			}
		});
		
		menuViviendas.add(menuItemRegistrarViviendas);
		
		JMenu menuPedidos = new JMenu(labels.getString("ventana.principal.menu.item.pedidos"));
		menuBar.add(menuPedidos);
		
		JMenuItem menuItemPedidoDeRetiro = new JMenuItem(labels.getString("ventana.principal.menu.item.pedido.de.retiro"));
		menuItemPedidoDeRetiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PedidoDeRetiro p = new PedidoDeRetiro(api);
				p.setVisible(true);
			}
		});
		
		menuPedidos.add(menuItemPedidoDeRetiro);
		
		JMenuItem menuItemOrdenDeRetiro = new JMenuItem(labels.getString("ventana.principal.menu.item.generar.orden.de.retiro"));
		
		menuItemOrdenDeRetiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenerarOrdenDeRetiro o = new GenerarOrdenDeRetiro(api);
				o.setVisible(false);
			}
		});
		menuPedidos.add(menuItemOrdenDeRetiro);
		
	}

}