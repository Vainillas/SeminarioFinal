package ar.edu.unrn.seminario.gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.Color;
public class ListadoDeViviendas extends JFrame {
	IApi api;
	private JTable table;
	DefaultTableModel modelo;

	private JPanel contentPane;
	private JPanel panel;
	private JPanel panelOrdenamiento;
	private JButton btnSalir;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnOrdenarPorCalle;
	private JButton btnOrdenarPorBarrio;
	private JButton btnOrdenarPorCodPostal;
	private JButton btnOrdenarPorCodigo;
	private JButton btnOrdenarPorNombreYApellido;
	private JButton btnOrdenarPorLatitutYLongitud;


	public ListadoDeViviendas(IApi api){
		this.api=api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 879, 581);
		contentPane = new JPanel();
		panel = new JPanel();
		panel.setBounds(5, 5, 604, 473);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		contentPane.add(panel);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		table = new JTable();
		String[] titulos = { 
				"BARRIO",
				"CALLE",
				"ALTURA",
				"CODIGO POSTAL",
				"LATITUD",
				"LONGITUD",
				"NOMBRE",
				"APELLIDO",
				"DNI",
				"CORREO ELECTRONICO",
				"CODIGO"
		};
		
		
		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar
		List<ViviendaDTO> viviendas;
		try {
			viviendas = api.obtenerViviendas();
			for (ViviendaDTO v : viviendas) {
				modelo.addRow(new Object[] { v.getDireccion().getBarrio(),
							v.getDireccion().getCalle(),
							v.getDireccion().getAltura(),
							v.getDireccion().getCodPostal(),
							v.getDireccion().getLatitud(),
							v.getDireccion().getLongitud(),
							v.getDueño().getNombre(),
							v.getDueño().getApellido(),
							v.getDueño().getDni(),
							v.getDueño().getCorreo(),
							v.getID()
							
							});
			}
			
		} catch (AppException e1)  {
			JOptionPane.showMessageDialog(null,e1.getMessage(),"error",2);
			dispose();
			
		} catch (Exception e1){
			JOptionPane.showMessageDialog(null,e1.getMessage(),"error",2);
			setVisible(false);
			dispose();
		}
		
		// Agrega los usuarios en el model
		
		table.setModel(modelo);
		

		scrollPane.setViewportView(table);
		
		btnSalir = new JButton("SALIR");
		btnSalir.addActionListener((e)->{
			setVisible(false);
			dispose();
			
		});
		panel.add(btnSalir, BorderLayout.SOUTH);
		
		panelOrdenamiento = new JPanel();
		panelOrdenamiento.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, null));
		panelOrdenamiento.setBounds(619, 143, 234, 189);
		contentPane.add(panelOrdenamiento);
		
		btnOrdenarPorCalle = new JButton("FILTRAR POR CALLE");
		btnOrdenarPorCalle.addActionListener((e)->{
			reloadGrid("calle");
			
			
		});
		
		panelOrdenamiento.add(btnOrdenarPorCalle);
		
		btnOrdenarPorBarrio = new JButton("FILTRAR POR BARRIO");
		btnOrdenarPorBarrio.addActionListener((e)->{
			this.reloadGrid("barrio");
			
		});
		
		panelOrdenamiento.add(btnOrdenarPorBarrio);
		
		btnOrdenarPorCodPostal = new JButton("FILTRAR POR CODIGO POSTAL");
		btnOrdenarPorCodPostal.addActionListener((e)->{
			this.reloadGrid("codigoPostal");

			
			
		});
		panelOrdenamiento.add(btnOrdenarPorCodPostal);
		
		btnOrdenarPorCodigo = new JButton("FILTRAR POR CODIGO");
		btnOrdenarPorCodigo.addActionListener((e)->{
			this.reloadGrid("codigo");

			
		});
		panelOrdenamiento.add(btnOrdenarPorCodigo);
		
		
		btnOrdenarPorNombreYApellido = new JButton("FILTRAR POR NOMBRE Y APELLIDO");
		btnOrdenarPorNombreYApellido.addActionListener((e)->{
			this.reloadGrid("nombreApellido");

			
			
		});
		panelOrdenamiento.add(btnOrdenarPorNombreYApellido);
		
		btnOrdenarPorLatitutYLongitud = new JButton("FILTRAR POR LATITUD Y LONGITUD");
		btnOrdenarPorLatitutYLongitud.addActionListener((e)->{
			this.reloadGrid("latitudLongitud");

		
		
		});
		panelOrdenamiento.add(btnOrdenarPorLatitutYLongitud);
		
	}
	private void reloadGrid(String tipoOrdenamiento){
			// Obtiene el model del table
			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			// Obtiene la lista de usuarios a mostrar
			List<ViviendaDTO> viviendas;
			try {
				switch(tipoOrdenamiento) {
			
					case "calle":
						viviendas = api.obtenerViviendasOrdenadasPorCalle();
						break;
					case "barrio":
						viviendas = api.obtenerViviendasOrdenadasPorBarrio();
						break;
					case "altura":
						viviendas = api.obtenerViviendasOrdenadasPorAltura();
						break;
					case "codigo":
						viviendas = api.obtenerViviendasOrdenadasPorCodigo();
						break;
					case "codigoPostal":
						viviendas = api.obtenerViviendasOrdenadasPorCodigoPostal();
						break;
					case "nombreApellido":
						viviendas = api.obtenerViviendasOrdenadasPorNombreYApellido();
						break;
					case "latitudLongitud":
						viviendas = api.obtenerViviendasPorLatitudYLongitud();
					default:
						viviendas = api.obtenerViviendas();
						break;
				}
				modelo.setRowCount(0);
				// Agrega los usuarios en el model
				for (ViviendaDTO v : viviendas) {
					modelo.addRow(new Object[] { v.getDireccion().getBarrio(),
									v.getDireccion().getCalle(),
									v.getDireccion().getAltura(),
									v.getDireccion().getCodPostal(),
									v.getDireccion().getLatitud(),
									v.getDireccion().getLongitud(),
									v.getDueño().getNombre(),
									v.getDueño().getApellido(),
									v.getDueño().getDni(),
									v.getDueño().getCorreo(),
									v.getID()
										
				});
						
				}
				
			
				}catch (Exception e) {
				setVisible(false);
				JOptionPane.showMessageDialog(null,e.getMessage(), "error",JOptionPane.ERROR_MESSAGE);
			
			
				
			}
		
		}
}
