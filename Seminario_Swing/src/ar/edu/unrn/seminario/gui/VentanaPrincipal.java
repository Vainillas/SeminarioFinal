package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import ar.edu.unrn.seminario.accesos.ViviendaDAOJDBC;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Vivienda;
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

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal(IApi api) {
		setTitle("Ventana Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 302);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Usuarios");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Alta/Modificacion");
		mntmNewMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					AltaUsuario alta = new AltaUsuario(api);
					alta.setLocationRelativeTo(null);
					alta.setVisible(true);
				}
			
		});


		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Listado");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoUsuario listado= new ListadoUsuario(api);
				listado.setLocationRelativeTo(null);
				listado.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu menuConfiguracion = new JMenu("Configuracion");
		menuBar.add(menuConfiguracion);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Salir");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
			}
		});
		menuConfiguracion.add(mntmNewMenuItem_2);
		
		JMenu menuIdiomas = new JMenu(labels.getString("ventana.principal.menu.idiomas"));
		menuConfiguracion.add(menuIdiomas);
		
		
		JMenuItem menuItemEspañol = new JMenuItem(labels.getString("ventana.principal.menu.item.español"));

		menuItemEspañol.addActionListener(new ActionListener() {
			//labels = ResourceBundle.getBundle("labels");
			public void actionPerformed(ActionEvent e) {
				ResourceBundle labels = ResourceBundle.getBundle("labels");
				setVisible(false);
				dispose();
				setVisible(true);
			}
		});
		menuIdiomas.add(menuItemEspañol);
		
		
		JMenuItem menuItemEnglish = new JMenuItem(labels.getString("ventana.principal.menu.item.ingles")); 
		menuItemEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//labels = ResourceBundle.getBundle("labels",new Locale("en"));
				//ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("en"));
				setVisible(false);
				dispose();
				setVisible(true);
			}
		});
		menuIdiomas.add(menuItemEnglish);
		
		JMenu mnNewMenu_2 = new JMenu("Viviendas");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Listado");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoVivienda listado= new ListadoVivienda(api);
				listado.setLocationRelativeTo(null);
				listado.setVisible(true);
			}
		});
		
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Registrar Vivienda");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				RegistroVivienda casa = new RegistroVivienda(api);
				casa.setVisible(true);
			}
		});
		
		mnNewMenu_2.add(mntmNewMenuItem_4);
		
		JMenu menuPedidos = new JMenu("Pedidos");
		menuBar.add(menuPedidos);
		
		JMenuItem menuItemPedidoDeRetiro = new JMenuItem("pedido de retiro ");
		menuItemPedidoDeRetiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PedidoDeRetiro p = new PedidoDeRetiro(api);
				p.setVisible(true);
			}
		});
		menuPedidos.add(menuItemPedidoDeRetiro);
		
		

	}

}
