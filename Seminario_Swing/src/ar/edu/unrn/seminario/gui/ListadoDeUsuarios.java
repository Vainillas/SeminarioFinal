package ar.edu.unrn.seminario.gui;


import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;

import ar.edu.unrn.seminario.exceptions.StateException;
import ar.edu.unrn.seminario.utilities.OrderingPredicate;
import ar.edu.unrn.seminario.utilities.Predicate;

import javax.swing.JTextField;
import javax.swing.JLabel;

import javax.swing.SwingConstants;

public class ListadoDeUsuarios extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private IApi api;
	private JButton activarButton;
	private JButton desactivarButton;
	private JPanel panelBotonesOrdenamiento;
	private JButton btnOrdenarPorCorreo;
	private JButton btnOrdenarPorNombre;
	private JButton btnOrdenarPorRol;
	private JButton btnOrdenarPorEstado;
	private JLabel lbUsername;
	private JButton btnLimpiarFIltro;

	public ListadoDeUsuarios(IApi api, ResourceBundle labels){
		this.api = api;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 977, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(250, 5, 701, 312);
		contentPane.add(scrollPane);
		//ResourceBundle labels = ResourceBundle.getBundle("labels"); 
		setTitle(labels.getString("listado.usuario.titulo"));
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
					modelo.addRow(new Object[] { u.getUsername(), u.getEmail(), u.getEstado(), u.getRol().getNombre() });
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
						//reloadGrid();
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
						//reloadGrid(null);
					} catch (StateException | AppException e1) {
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


		JPanel pnlBotonesOperaciones = new JPanel();
		pnlBotonesOperaciones.setBounds(250, 328, 679, 37);
		pnlBotonesOperaciones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(pnlBotonesOperaciones);
		pnlBotonesOperaciones.add(desactivarButton);
		pnlBotonesOperaciones.add(activarButton);
		pnlBotonesOperaciones.add(cerrarButton);
		

		panelBotonesOrdenamiento = new JPanel();
		panelBotonesOrdenamiento.setBounds(53, 5, 165, 167);
		contentPane.add(panelBotonesOrdenamiento);

		
		btnOrdenarPorRol = new JButton(labels.getString("listado.usuario.button.ordenar.por.rol"));

		btnOrdenarPorRol.setBounds(39, 25, 87, 23);
		btnOrdenarPorRol.addActionListener((e)->{

			Comparator <UsuarioDTO> comparator = (UsuarioDTO v1, UsuarioDTO v2)
					->(v1.getRol().getNombre().compareToIgnoreCase(v2.getRol().getNombre())
			);
			
			try {
				List<UsuarioDTO> u = api.obtenerUsuarios(comparator);
				this.reloadGrid(u);
			} catch (AppException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
			

			

			

			
			
		});
		panelBotonesOrdenamiento.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ordenar por:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 11, 145, 14);
		panelBotonesOrdenamiento.add(lblNewLabel_1);
		panelBotonesOrdenamiento.add(btnOrdenarPorRol);
		
		btnOrdenarPorEstado = new JButton(labels.getString("listado.usuario.button.ordenar.por.estado")); 
		btnOrdenarPorEstado.setBounds(39, 59, 87, 23);
		btnOrdenarPorEstado.addActionListener((e)->{
			Comparator <UsuarioDTO> comparator = (UsuarioDTO v1, UsuarioDTO v2)
					->(v1.getEstado().compareToIgnoreCase(v2.getEstado())
			);
			
			try {
				List<UsuarioDTO> u = api.obtenerUsuarios(comparator);
				this.reloadGrid(u);
			} catch (AppException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
			
			
		});
		panelBotonesOrdenamiento.add(btnOrdenarPorEstado);
		
		btnOrdenarPorNombre = new JButton(labels.getString("listado.usuario.button.ordenar.por.nombre")); //$NON-NLS-1$
		btnOrdenarPorNombre.setBounds(39, 127, 87, 23);
		btnOrdenarPorNombre.addActionListener((e)->{
			Comparator <UsuarioDTO> comparator = (UsuarioDTO v1, UsuarioDTO v2)
					->(v1.getUsername().compareToIgnoreCase(v2.getUsername())
			);
			
			try {
				List<UsuarioDTO> u = api.obtenerUsuarios(comparator);
				this.reloadGrid(u);
			} catch (AppException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
		});
		
		btnOrdenarPorCorreo = new JButton(labels.getString("listado.usuario.button.ordenar.por.correo")); //$NON-NLS-1$
		btnOrdenarPorCorreo.setBounds(39, 93, 87, 23);
		btnOrdenarPorCorreo.addActionListener((e)->{
			Comparator <UsuarioDTO> comparator = (UsuarioDTO v1, UsuarioDTO v2)
					->(v1.getEmail().compareToIgnoreCase(v2.getEmail())
			);
			
			try {
				List<UsuarioDTO> u = api.obtenerUsuarios(comparator);
				this.reloadGrid(u);
			} catch (AppException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}

			
			
		});
		panelBotonesOrdenamiento.add(btnOrdenarPorCorreo);
		
		panelBotonesOrdenamiento.add(btnOrdenarPorNombre);
		
		JPanel panelBotonesFiltrados = new JPanel();
		panelBotonesFiltrados.setBounds(10, 186, 230, 179);
		contentPane.add(panelBotonesFiltrados);
		panelBotonesFiltrados.setLayout(null);
		
		JLabel lbFiltradoPor = new JLabel("filtrar por:"); 
		lbFiltradoPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltradoPor.setBounds(24, 11, 181, 14);
		panelBotonesFiltrados.add(lbFiltradoPor);
		
		lbUsername = new JLabel("Username"); 
		lbUsername.setBounds(10, 40, 73, 20);
		panelBotonesFiltrados.add(lbUsername);
		
		JTextField tfUsernameSeleccionado = new JTextField("");
		tfUsernameSeleccionado.setBounds(91, 40, 100, 20);
		panelBotonesFiltrados.add(tfUsernameSeleccionado);
		tfUsernameSeleccionado.setColumns(10);
		
		JRadioButton rdbtnAceptar = new JRadioButton(""); 
		rdbtnAceptar.addActionListener((e)->{
			if(rdbtnAceptar.isSelected()) {
				Predicate <UsuarioDTO> predicate = 
				(UsuarioDTO usuario)->usuario.getUsername().contains((String)tfUsernameSeleccionado.getText());	
				try {
					List<UsuarioDTO> us =api.obtenerUsuarios(predicate);
					reloadGrid(us);
				} catch (AppException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				}
				
				rdbtnAceptar.setSelected(false);
			}
		
			
						
		});
		rdbtnAceptar.setBounds(199, 40, 21, 20);
		panelBotonesFiltrados.add(rdbtnAceptar);
		
		btnLimpiarFIltro = new JButton("Limpiar Filtro"); //$NON-NLS-1$
		btnLimpiarFIltro.addActionListener((e)->{
			
			try {
				this.reloadGrid(api.obtenerUsuarios());
			} catch (AppException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
		});
		btnLimpiarFIltro.setBounds(71, 114, 89, 23);
		panelBotonesFiltrados.add(btnLimpiarFIltro);

		// Deshabilitar botones que requieren tener una fila seleccionada
		habilitarBotones(false);
		
	}

	private void habilitarBotones(boolean b) {
		activarButton.setEnabled(b);
		desactivarButton.setEnabled(b);

	}
	private void reloadGrid(List<UsuarioDTO> us) {
		modelo.setRowCount(0);
		// Agrega los usuarios en el model
		for (UsuarioDTO u : us) {
		modelo.addRow(new Object[] { u.getUsername(), u.getEmail(), u.getEstado(), u.getRol() });
		}
	}

}


	


