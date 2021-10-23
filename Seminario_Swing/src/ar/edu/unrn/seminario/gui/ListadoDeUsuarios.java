package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StateException;

public class ListadoDeUsuarios extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo;
	IApi api;
	JButton activarButton;
	JButton desactivarButton;
	private JPanel panel;
	private JButton btnOrdenarPorCorreo;
	private JButton btnOrdenarPorNombre;
	private JButton btnOrdenarPorRol;
	private JButton btnOrdenarPorEstado;

	public ListadoDeUsuarios(IApi api){
		this.api = api;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 725, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(208, 5, 491, 214);
		contentPane.add(scrollPane);
		ResourceBundle labels = ResourceBundle.getBundle("labels");
		//ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en"));
		table = new JTable();
		String[] titulosUsuario = { 
				labels.getString("listado.usuario.titulos.usuario.USUARIO"),  
				labels.getString("listado.usuario.titulos.usuario.EMAIL"), 
				labels.getString("listado.usuario.titulos.usuario.ESTADO"), 
				labels.getString("listado.usuario.titulos.usuario.ROL") };
		
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				// Habilitar botones
				habilitarBotones(true);

			}
		});
		modelo = new DefaultTableModel(new Object[][] {}, titulosUsuario);

		// Obtiene la lista de usuarios a mostrar
		List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		
		try {
				usuarios = api.obtenerUsuarios();
				// Agrega los usuarios en el model
				for (UsuarioDTO u : usuarios) {
					modelo.addRow(new Object[] { u.getUsername(), u.getEmail(), u.getEstado(), u.getRol() });
				}

		} catch (AppException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "error: ",JOptionPane.ERROR_MESSAGE);
				setVisible(false);
				dispose();
				
		}
		
		
		table.setModel(modelo);

		scrollPane.setViewportView(table);

		activarButton = new JButton(labels.getString("listado.usuario.button.activar"));
		activarButton.addActionListener((e)->{
				int reply = JOptionPane.showConfirmDialog(null,
						labels.getString("listado.usuario.mensaje.cambiar.estado"),
						labels.getString("listado.usuario.mensaje.confirmar.cambio.estado"),
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					String username = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
					try {
						api.activarUsuario(username);
						reloadGrid(null);
					}
					catch(StateException | AppException e1){
						JOptionPane.showMessageDialog(null, e1.getMessage(), 
								labels.getString("listado.usuario.mensaje.error"), JOptionPane.ERROR_MESSAGE);
					}

				}

			

		});

		desactivarButton = new JButton(labels.getString("listado.usuario.button.desactivar"));
		desactivarButton.addActionListener((e)->{
				int reply = JOptionPane.showConfirmDialog(null,
						labels.getString("listado.usuario.mensaje.cambiar.estado"), 
						labels.getString("listado.usuario.mensaje.confirmar.cambio.estado"),
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					String username = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
					try {
						api.desactivarUsuario(username);
						reloadGrid(null);
					} catch (StateException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), 
								labels.getString("listado.usuario.mensaje.error"), 
								JOptionPane.ERROR_MESSAGE);
					}
				}
			
		});

		JButton cerrarButton = new JButton(labels.getString("listado.usuario.button.cerrar"));
		cerrarButton.addActionListener((e)->{
				setVisible(false);
				dispose();
			
		});
//		contentPane.add(cerrarButton, BorderLayout.SOUTH);

		JPanel pnlBotonesOperaciones = new JPanel();
		pnlBotonesOperaciones.setBounds(5, 219, 694, 37);
		pnlBotonesOperaciones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(pnlBotonesOperaciones);
		pnlBotonesOperaciones.add(desactivarButton);
		pnlBotonesOperaciones.add(activarButton);
		pnlBotonesOperaciones.add(cerrarButton);
		
		panel = new JPanel();
		panel.setBounds(22, 5, 156, 214);
		contentPane.add(panel);
		
		btnOrdenarPorCorreo = new JButton(labels.getString("listado.usuario.button.ordenar.por.correo")); //$NON-NLS-1$
		btnOrdenarPorCorreo.addActionListener((e)->{
			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			// Obtiene la lista de usuarios a mostrar
			List<UsuarioDTO> usuario = null ;
			try {	
				usuario = api.obtenerUsuariosOrdenadosPorCorreo();
				modelo.setRowCount(0);
				
				// Agrega los usuarios en el model
				for (UsuarioDTO u : usuario) {
					modelo.addRow(new Object[] { u.getUsername(), u.getEmail(), u.getEstado(), u.getRol() });
				}
				
				// Resetea el model
				//reload(usuarios);
			} catch (AppException e2) {
				setVisible(false);
				JOptionPane.showMessageDialog(null,e2.getMessage(), "error",JOptionPane.ERROR_MESSAGE);
			}
			
			
		});
		
		btnOrdenarPorNombre = new JButton(labels.getString("listado.usuario.button.ordenar.por.nombre")); //$NON-NLS-1$
		btnOrdenarPorNombre.addActionListener((e)->{
			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			// Obtiene la lista de usuarios a mostrar
			List<UsuarioDTO> usuario = null ;
			try {	
				usuario = api.obtenerUsuariosOrdenadosPorNombre();
				modelo.setRowCount(0);
				
				// Agrega los usuarios en el model
				for (UsuarioDTO u : usuario) {
					modelo.addRow(new Object[] { u.getUsername(), u.getEmail(), u.getEstado(), u.getRol() });
				}
				
				// Resetea el model
				//reload(usuarios);
			} catch (AppException e2) {
				setVisible(false);
				JOptionPane.showMessageDialog(null,e2.getMessage(), "error",JOptionPane.ERROR_MESSAGE);
			}
			
			
		});
			
		panel.add(btnOrdenarPorNombre);
		panel.add(btnOrdenarPorCorreo);
		
		btnOrdenarPorRol = new JButton(labels.getString("listado.usuario.button.ordenar.por.rol"));
		btnOrdenarPorRol.addActionListener((e)->{
			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			// Obtiene la lista de usuarios a mostrar
			List<UsuarioDTO> usuario = null ;
			reloadGrid("rol");
			
			try {	
				usuario = api.obtenerUsuariosOrdenadosPorRol();
				modelo.setRowCount(0);
				
				// Agrega los usuarios en el model
				for (UsuarioDTO u : usuario) {
					modelo.addRow(new Object[] { u.getUsername(), u.getEmail(), u.getEstado(), u.getRol() });
				}
				
				// Resetea el model
				//reload(usuarios);
			} catch (AppException e2) {
				setVisible(false);
				JOptionPane.showMessageDialog(null,e2.getMessage(), "error",JOptionPane.ERROR_MESSAGE);
			}
			
			
		});
		panel.add(btnOrdenarPorRol);
		
		btnOrdenarPorEstado = new JButton(labels.getString("listado.usuario.button.ordenar.por.estado")); 
		btnOrdenarPorEstado.addActionListener((e)->{
			reloadGrid("estado");
			
			
		});
		panel.add(btnOrdenarPorEstado);

		// Deshabilitar botones que requieren tener una fila seleccionada
		habilitarBotones(false);
		
	}

	private void habilitarBotones(boolean b) {
		activarButton.setEnabled(b);
		desactivarButton.setEnabled(b);

	}
	private void reloadGrid(String tipoOrdenamiento){
		// Obtiene el model del table
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		// Obtiene la lista de usuarios a mostrar
		List<UsuarioDTO> usuarios;
		try {
			switch(tipoOrdenamiento) {
		
				case "username":
					usuarios = api.obtenerUsuariosOrdenadosPorNombre();
					break;
				case "rol":
						usuarios = api.obtenerUsuariosOrdenadosPorRol();
						break;
				case "correo":
					usuarios = api.obtenerUsuariosOrdenadosPorCorreo();
					break;
				case "estado":
					usuarios = api.obtenerUsuariosOrdenadosPorEstado();
					break;
				default:
					usuarios = api.obtenerUsuarios();
					break;
					
			}
			modelo.setRowCount(0);
			// Agrega los usuarios en el model
			for (UsuarioDTO u : usuarios) {
			modelo.addRow(new Object[] { u.getUsername(), u.getEmail(), u.getEstado(), u.getRol() });
			}
		
		}catch (AppException e) {
			setVisible(false);
			JOptionPane.showMessageDialog(null,e.getMessage(), "error",JOptionPane.ERROR_MESSAGE);
		
		
			
		}
	}
}


	


