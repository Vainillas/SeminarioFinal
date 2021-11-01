package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;



import java.util.Locale;
import java.util.ResourceBundle;
import ar.edu.unrn.seminario.api.IApi;

import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.StateException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {
	
	ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));

	private JPanel contentPane;
	private IApi api;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//IApi api = new MemoryApi();
					IApi api2 = new PersistenceApi();
					VentanaPrincipal frame = new VentanaPrincipal(api2);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaPrincipal(IApi api) {
		this.api = api;
		try {
			this.api.activarUsuario("jordan");
		} catch (StateException | AppException e1) {
			// TODO Bloque catch generado automáticamente
			e1.printStackTrace();
		}
		setTitle("Ventana Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setBounds(100, 100, 440, 302);
		//i18n
		ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuUsuarios = new JMenu(labels.getString("ventana.principal.menu.usuarios"));
		menuBar.add(menuUsuarios);

		JMenuItem menuItemAltaModificacion = new JMenuItem(labels.getString("ventana.principal.menu.alta.modificacion"));
		menuItemAltaModificacion.addActionListener((e)->{
				AltaUsuario alta = new AltaUsuario(api);
				alta.setLocationRelativeTo(null);
				alta.setVisible(true);

		});


		menuUsuarios.add(menuItemAltaModificacion);
		
		JMenuItem menuItemListadoUsuarios = new JMenuItem(labels.getString("ventana.principal.menu.item.listado"));
		menuItemListadoUsuarios.addActionListener((e)->{
				ListadoDeUsuarios listado= new ListadoDeUsuarios(api);
				listado.setLocationRelativeTo(null);
				listado.setVisible(true);
		});
		menuUsuarios.add(menuItemListadoUsuarios);
		
		JMenu menuConfiguracion = new JMenu(labels.getString("ventana.principal.menu.configuracion"));
		
		menuBar.add(menuConfiguracion);
		
		JMenuItem menuItemSalir = new JMenuItem(labels.getString("ventana.principal.menu.item.salir"));
		menuItemSalir.addActionListener( e->{System.exit(ABORT);dispose();});
			
			
		menuConfiguracion.add(menuItemSalir);
		
		JMenu menuIdiomas = new JMenu(labels.getString("ventana.principal.menu.idiomas"));
		menuConfiguracion.add(menuIdiomas);
		
		JMenuItem menuItemEspañol = new JMenuItem(labels.getString("ventana.principal.menu.item.español"));
		
		menuIdiomas.add(menuItemEspañol);
		
		/*ActionListener a = (e->System.out.println("v"));
		a.actionPerformed(null);*/
		
		JMenuItem menuItemEnglish = new JMenuItem(labels.getString("ventana.principal.menu.item.ingles")); 
		menuItemEnglish.addActionListener((e)->{
				 
				this.setVisible(false);
				dispose();
				setVisible(true);
			
		});
		menuIdiomas.add(menuItemEnglish);
		
		JMenu menuViviendas = new JMenu(labels.getString("ventana.principal.menu.viviendas"));
		menuBar.add(menuViviendas);
		
		JMenuItem menuItemListadoViviendas = new JMenuItem(labels.getString("ventana.principal.menu.item.listado"));
		menuItemListadoViviendas.addActionListener((e)->{

			ListadoDeViviendas v =  new ListadoDeViviendas(api);
			v.setVisible(true);
			

			/*try {
				List<PedidoDeRetiroDTO> lista = api.obtenerPedidosDeRetiro();
				for(PedidoDeRetiroDTO p: lista) {
					System.out.println(p.getObservacion());
				}
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}*/

			
		});
		
				/*new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoVivienda listado= new ListadoVivienda(api);
				listado.;
				listado.setVisible(true);
			}
		});
		*/
		menuViviendas.add(menuItemListadoViviendas);
		
		JMenuItem menuItemRegistrarViviendas = new JMenuItem(labels.getString("ventana.principal.menu.item.registrar.vivienda"));
		
		menuItemRegistrarViviendas.addActionListener((e)->{
			RegistroVivienda v =new RegistroVivienda(api);
			v.setVisible(true);
			v.setLocationRelativeTo(null);
		});
		

		menuViviendas.add(menuItemRegistrarViviendas);
		
		JMenu menuPedidos = new JMenu(labels.getString("ventana.principal.menu.item.pedidos"));
		menuBar.add(menuPedidos);
		
		JMenuItem menuItemPedidoDeRetiro = new JMenuItem(labels.getString("ventana.principal.menu.item.pedido.de.retiro"));
		menuItemPedidoDeRetiro.addActionListener((e)->{
			GenerarPedidoDeRetiro p  = new GenerarPedidoDeRetiro(api);
			p.setVisible(true);
			p.setLocationRelativeTo(null);
			});
		
		menuPedidos.add(menuItemPedidoDeRetiro);
		
		JMenuItem menuItemOrdenDeRetiro = new JMenuItem(labels.getString("ventana.principal.menu.item.generar.orden.de.retiro"));
		
		menuItemOrdenDeRetiro.addActionListener( (e)->{ 
			GenerarOrdenDeRetiro o = null;
			o = new GenerarOrdenDeRetiro(api);
			o.setVisible(true);
			o.setLocationRelativeTo(null); 
			
		});
				/*new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenerarOrdenDeRetiro o = new GenerarOrdenDeRetiro(api);
				o.setVisible(false);
			}
		});*/
		menuPedidos.add(menuItemOrdenDeRetiro);
		
		JMenu Personal = new JMenu(labels.getString("ventana.principal.menu.personal")); 
		menuBar.add(Personal);
		
		JMenuItem miRegistrarPersonal = new JMenuItem(labels.getString("ventana.principal.menu.item.registrar.personal")); 
		
		miRegistrarPersonal.addActionListener((e)->{
			RegistrarPersonal personal = new RegistrarPersonal(api);
			personal.setVisible(true);
			
		});
		Personal.add(miRegistrarPersonal);
		
	}

}