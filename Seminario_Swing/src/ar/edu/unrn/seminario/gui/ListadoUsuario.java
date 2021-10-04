package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import ar.edu.unrn.seminario.exceptions.StateException;

public class ListadoUsuario extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo;
	IApi api;
	JButton activarButton;
	JButton desactivarButton;

	/**
	 * Create the frame.
	 */
	public ListadoUsuario(IApi api) {
		this.api = api;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en"));
		table = new JTable();
		String[] titulosUsuario = { 
				labels.getString("listado.usuario.titulos.usuario.USUARIO"),  
				labels.getString("listado.usuario.titulos.usuario.EMAIL"), 
				labels.getString("listado.usuario.titulos.usuario.ESTADO"), 
				labels.getString("listado.usuario.titulos.usuario.ROL") };
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Habilitar botones
				habilitarBotones(true);

			}
		});
		modelo = new DefaultTableModel(new Object[][] {}, titulosUsuario);

		// Obtiene la lista de usuarios a mostrar
		List<UsuarioDTO> usuarios = api.obtenerUsuarios();
		// Agrega los usuarios en el model
		for (UsuarioDTO u : usuarios) {
			modelo.addRow(new Object[] { u.getUsername(), u.getEmail(), u.getEstado(), u.getRol() });
		}

		table.setModel(modelo);

		scrollPane.setViewportView(table);

		activarButton = new JButton(labels.getString("listado.usuario.button.activar"));
		activarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null,
						labels.getString("listado.usuario.mensaje.cambiar.estado"),
						labels.getString("listado.usuario.mensaje.confirmar.cambio.estado"),
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					String username = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
					try {
						api.activarUsuario(username);
						reloadGrid();
					}
					catch(StateException e1){
						JOptionPane.showMessageDialog(null, e1.getMessage(), 
								labels.getString("listado.usuario.mensaje.error"), JOptionPane.ERROR_MESSAGE);
					}

				}

			}

		});

		desactivarButton = new JButton(labels.getString("listado.usuario.button.desactivar"));
		desactivarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null,
						labels.getString("listado.usuario.mensaje.cambiar.estado"), 
						labels.getString("listado.usuario.mensaje.confirmar.cambio.estado"),
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					String username = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
					try {
						api.desactivarUsuario(username);
						reloadGrid();
					} catch (StateException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), 
								labels.getString("listado.usuario.mensaje.error"), 
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JButton cerrarButton = new JButton(labels.getString("listado.usuario.button.cerrar"));
		cerrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
//		contentPane.add(cerrarButton, BorderLayout.SOUTH);

		JPanel pnlBotonesOperaciones = new JPanel();
		pnlBotonesOperaciones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(pnlBotonesOperaciones, BorderLayout.SOUTH);
		pnlBotonesOperaciones.add(desactivarButton);
		pnlBotonesOperaciones.add(activarButton);
		pnlBotonesOperaciones.add(cerrarButton);

		// Deshabilitar botones que requieren tener una fila seleccionada
		habilitarBotones(false);
	}

	private void habilitarBotones(boolean b) {
		activarButton.setEnabled(b);
		desactivarButton.setEnabled(b);

	}

	private void reloadGrid() {
		// Obtiene el model del table
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		// Obtiene la lista de usuarios a mostrar
		List<UsuarioDTO> usuarios = api.obtenerUsuarios();
		// Resetea el model
		modelo.setRowCount(0);

		// Agrega los usuarios en el model
		for (UsuarioDTO u : usuarios) {
			modelo.addRow(new Object[] { u.getUsername(), u.getNombre(), u.getEmail(), u.getEstado(), u.getRol() });
		}

	}

}
