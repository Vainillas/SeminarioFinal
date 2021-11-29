package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.CanjeDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.utilities.NotEditJTable;
import ar.edu.unrn.seminario.utilities.Predicate;

import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ListadoDeCanjes extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modelo;
	private JTable table;
	private JTextField tfFiltrarPorNombreCampaña;
	private JTextField tfFiltrarPorCodigoCampaña;
	private JTextField tfFiltrarDescripcionBeneficio;
	private JTextField tfFiltrarPorUsuarioAsociado;
	private JTextField tfFiltrarPorCodigoUsuario;
	private JTextField tfFiltrarPorCodigoBeneficio;
	private List<CanjeDTO> listaFiltrados;
	private List<CanjeDTO> listaOrdenamiento;
	private String rolUsuarioActivo;
	private String [] titulos;
	private JLabel filtrarPorCodigoUsuario ;
	private JLabel filtrarPorUsuarioAsociado ;
	private JRadioButton rdbtnFiltrarPorUsuarioAsociado;
	private JRadioButton rdbtnUsuarioAsociado;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
					ListadoDeCanjes frame = new ListadoDeCanjes(api,labels);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param labels 
	 */
	public ListadoDeCanjes(IApi api, ResourceBundle labels) {
		this.setTitle(labels.getString("listado.de.canjes.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1139, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);



		String []titulos = { 
				labels.getString("listado.de.canjes.titulo.nombre.campaña"),
				labels.getString("listado.de.canjes.titulo.codigo.campaña"), 
				labels.getString("listado.de.canjes.titulo.descripcion.beneficio"), 
				labels.getString("listado.de.canjes.titulo.codigo.beneficio"), 
				labels.getString("listado.de.canjes.titulo.dueño.asociado"),
				labels.getString("listado.de.canjes.titulo.dni.dueño") 	
		};
		String []titulos2 = { 
				labels.getString("listado.de.canjes.titulo2.nombre.campaña"),
				labels.getString("listado.de.canjes.titulo2.codigo.campaña"), 
				labels.getString("listado.de.canjes.titulo2.descripcion.beneficio"), 
				labels.getString("listado.de.canjes.titulo2.codigo.beneficio"), 
		};
			List<CanjeDTO> canjes = null;
			
			try {
				if(api.obtenerRolUsuarioActivo().equalsIgnoreCase("ADMIN")) {
					modelo = new DefaultTableModel(new Object[][] {},titulos);
					canjes = api.obtenerCanjes();
					rolUsuarioActivo = "ADMIN";
					this.titulos = titulos;
				}
				else {
					modelo = new DefaultTableModel(new Object[][] {},titulos2);
					this.titulos = titulos;
					canjes = api.obtenerCanjesPorUsuario();
					rolUsuarioActivo = "COMUNIDAD";
				}
				
			reloadGrid(canjes);
			} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
			}
			
			
		JButton cerrarButton = new JButton(labels.getString("listado.de.canjes.boton.cerrar"));
		cerrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		contentPane.setLayout(null);

		JPanel pnlBotonesOperaciones = new JPanel();
		pnlBotonesOperaciones.setBounds(5, 322, 841, 37);
		pnlBotonesOperaciones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(pnlBotonesOperaciones);
	
		pnlBotonesOperaciones.add(cerrarButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 835, 317);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		table = new NotEditJTable();
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		reloadGrid(canjes);
		JPanel panel_Filtrados = new JPanel();
		panel_Filtrados.setVisible(false);
		panel_Filtrados.setBounds(848, 5, 265, 203);
		contentPane.add(panel_Filtrados);
		panel_Filtrados.setLayout(null);
		
		JLabel lbFiltrarPor = new JLabel(labels.getString("listado.de.canjes.label.filtrar.por"));
		lbFiltrarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrarPor.setBounds(69, 11, 110, 14);
		panel_Filtrados.add(lbFiltrarPor);
		
		JLabel filtrarPorNombreCampaña = new JLabel(labels.getString("listado.de.canjes.label.nombre.de.campaña"));
		filtrarPorNombreCampaña.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorNombreCampaña.setBounds(0, 39, 139, 14);
		panel_Filtrados.add(filtrarPorNombreCampaña);
		
		tfFiltrarPorNombreCampaña = new JTextField();
		tfFiltrarPorNombreCampaña.setBounds(149, 36, 86, 20);
		panel_Filtrados.add(tfFiltrarPorNombreCampaña);
		tfFiltrarPorNombreCampaña.setColumns(10);
		
		JRadioButton rdbtnFiltrarPorNombreCampaña = new JRadioButton("");
		rdbtnFiltrarPorNombreCampaña.addActionListener((e)->{
			rdbtnFiltrarPorNombreCampaña.setSelected(false);
			
			Predicate <CanjeDTO> predicate = (CanjeDTO c)->c.getCampaña().getNombreCampaña().toLowerCase().contains(this.tfFiltrarPorNombreCampaña.getText());
			try {
				
				if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
					this.listaFiltrados = api.obtenerCanjesPorUsuario();
				}
				else {
					this.listaFiltrados = api.obtenerCanjes();
				}
				
				reloadGrid(api.obtenerCanjes(predicate));
			} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
			}
			
			
		});
		rdbtnFiltrarPorNombreCampaña.setBounds(238, 36, 21, 23);
		rdbtnFiltrarPorNombreCampaña.addActionListener((e)->{
			
			
		});
		panel_Filtrados.add(rdbtnFiltrarPorNombreCampaña);
		
		JLabel filtrarPorCodigoCampaña = new JLabel(labels.getString("listado.de.canjes.label.codigo.de.campaña"));

		filtrarPorCodigoCampaña.setHorizontalAlignment(SwingConstants.CENTER);
		
		filtrarPorCodigoCampaña.setBounds(0, 64, 139, 14);
		panel_Filtrados.add(filtrarPorCodigoCampaña);
		
		JLabel filtrarPorDescripcionBeneficio = new JLabel(labels.getString("listado.de.canjes.label.descripcion.del.beneficio"));
		filtrarPorDescripcionBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorDescripcionBeneficio.setBounds(0, 89, 139, 14);
		panel_Filtrados.add(filtrarPorDescripcionBeneficio);
		
		JLabel filtrarPorCodigoBeneficio = new JLabel(labels.getString("listado.de.canjes.label.codigo.del.beneficio"));
		filtrarPorCodigoBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorCodigoBeneficio.setBounds(0, 114, 139, 14);
		panel_Filtrados.add(filtrarPorCodigoBeneficio);
		
		filtrarPorUsuarioAsociado = new JLabel(labels.getString("listado.de.canjes.label.usuario.asociado"));
		filtrarPorUsuarioAsociado.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorUsuarioAsociado.setBounds(0, 139, 139, 14);
		panel_Filtrados.add(filtrarPorUsuarioAsociado);
		
		 filtrarPorCodigoUsuario = new JLabel(labels.getString("listado.de.canjes.label.codigo.usuario"));
		filtrarPorCodigoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorCodigoUsuario.setBounds(0, 164, 139, 14);
		panel_Filtrados.add(filtrarPorCodigoUsuario);
		
		tfFiltrarPorCodigoCampaña = new JTextField();
		tfFiltrarPorCodigoCampaña.setColumns(10);
		tfFiltrarPorCodigoCampaña.setBounds(149, 61, 86, 20);
		panel_Filtrados.add(tfFiltrarPorCodigoCampaña);
		
		tfFiltrarDescripcionBeneficio = new JTextField();
		tfFiltrarDescripcionBeneficio.setColumns(10);
		tfFiltrarDescripcionBeneficio.setBounds(149, 86, 86, 20);
		panel_Filtrados.add(tfFiltrarDescripcionBeneficio);
		
		tfFiltrarPorUsuarioAsociado = new JTextField();
		tfFiltrarPorUsuarioAsociado.setColumns(10);
		tfFiltrarPorUsuarioAsociado.setBounds(149, 136, 86, 20);
		panel_Filtrados.add(tfFiltrarPorUsuarioAsociado);
		
		tfFiltrarPorCodigoUsuario = new JTextField();
		tfFiltrarPorCodigoUsuario.setColumns(10);
		tfFiltrarPorCodigoUsuario.setBounds(149, 161, 86, 20);
		panel_Filtrados.add(tfFiltrarPorCodigoUsuario);
		
		tfFiltrarPorCodigoBeneficio = new JTextField();
		tfFiltrarPorCodigoBeneficio.setColumns(10);
		tfFiltrarPorCodigoBeneficio.setBounds(149, 111, 86, 20);
		panel_Filtrados.add(tfFiltrarPorCodigoBeneficio);
		
		JRadioButton rdbtnFiltrarPorCodigoCampaña = new JRadioButton("");
		rdbtnFiltrarPorCodigoCampaña.addActionListener((e)->{
			rdbtnFiltrarPorCodigoCampaña.setSelected(false);
			
		});
		rdbtnFiltrarPorCodigoCampaña.setBounds(238, 60, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorCodigoCampaña);
		
		JRadioButton rdbtnFiltrarPorDescripcionBeneficio = new JRadioButton("");
		rdbtnFiltrarPorDescripcionBeneficio.addActionListener((e)->{
			rdbtnFiltrarPorDescripcionBeneficio.setSelected(false);

		});
		rdbtnFiltrarPorDescripcionBeneficio.setBounds(238, 85, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorDescripcionBeneficio);
		
		JRadioButton rdbtnFiltrarPorCodigoBeneficio = new JRadioButton("");
		rdbtnFiltrarPorCodigoBeneficio.addActionListener((e)->{
			rdbtnFiltrarPorCodigoBeneficio.setSelected(false);
			
			
		});
		rdbtnFiltrarPorCodigoBeneficio.setBounds(238, 110, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorCodigoBeneficio);
		
		rdbtnFiltrarPorUsuarioAsociado = new JRadioButton("");
		rdbtnFiltrarPorUsuarioAsociado.addActionListener((e)->{
			rdbtnFiltrarPorUsuarioAsociado.setSelected(false);
		});
		
		rdbtnFiltrarPorUsuarioAsociado.setBounds(238, 135, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorUsuarioAsociado);
		
		JRadioButton rdbtnFiltrarPorCodigoUsuario = new JRadioButton("");
		rdbtnFiltrarPorCodigoUsuario.addActionListener((e)->{
			
			rdbtnFiltrarPorCodigoUsuario.setSelected(false);
			
		});
		rdbtnFiltrarPorCodigoUsuario.setBounds(238, 160, 21, 23);
		panel_Filtrados.add(rdbtnFiltrarPorCodigoUsuario);
		
		JPanel panel_Ordenamiento = new JPanel();
		panel_Ordenamiento.setVisible(false);
		panel_Ordenamiento.setBounds(848, 210, 265, 178);
		contentPane.add(panel_Ordenamiento);
		panel_Ordenamiento.setLayout(null);
		
		JLabel lbOrdenarPor = new JLabel(labels.getString("listado.de.canjes.label.ordenar.por"));
		lbOrdenarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenarPor.setBounds(70, 0, 119, 14);
		panel_Ordenamiento.add(lbOrdenarPor);
		
		JRadioButton rdbtnOrdenarNombreCampaña = new JRadioButton(labels.getString("listado.de.canjes.label.nombre.de.campaña"));
		rdbtnOrdenarNombreCampaña.addActionListener((e)->{
			rdbtnOrdenarNombreCampaña.setSelected(false);
			
		});
		rdbtnOrdenarNombreCampaña.setBounds(24, 21, 165, 25);
		panel_Ordenamiento.add(rdbtnOrdenarNombreCampaña);
		
		JRadioButton rdbtnOrdenarCodigoCampaña = new JRadioButton(labels.getString("listado.de.canjes.label.codigo.de.campaña"));
		rdbtnOrdenarCodigoCampaña.addActionListener((e)->{
			rdbtnOrdenarCodigoCampaña.setSelected(false);
		});
		rdbtnOrdenarCodigoCampaña.setBounds(111, 49, 148, 23);
		panel_Ordenamiento.add(rdbtnOrdenarCodigoCampaña);
		
		JRadioButton rdbtnOrdenarDescripcionBeneficio = new JRadioButton(labels.getString("listado.de.canjes.label.descripcion.del.beneficio"));
		rdbtnOrdenarDescripcionBeneficio.addActionListener((e)->{
			rdbtnOrdenarDescripcionBeneficio.setSelected(false);
		});
		rdbtnOrdenarDescripcionBeneficio.setBounds(24, 75, 184, 23);
		panel_Ordenamiento.add(rdbtnOrdenarDescripcionBeneficio);
		
		JRadioButton rdbtnOrdenarCodigoBeneficio = new JRadioButton(labels.getString("listado.de.canjes.label.codigo.del.beneficio"));
		rdbtnOrdenarCodigoBeneficio.addActionListener((e)->{
			rdbtnOrdenarCodigoBeneficio.setSelected(false);
		});
		rdbtnOrdenarCodigoBeneficio.setBounds(111, 101, 148, 23);
		panel_Ordenamiento.add(rdbtnOrdenarCodigoBeneficio);
		
		rdbtnUsuarioAsociado = new JRadioButton(labels.getString("listado.de.canjes.label.usuario.asociado"));
		rdbtnUsuarioAsociado.addActionListener((e)->{
			rdbtnUsuarioAsociado.setSelected(false);
		});
		
		rdbtnUsuarioAsociado.setBounds(24, 122, 154, 25);
		panel_Ordenamiento.add(rdbtnUsuarioAsociado);
		
		JRadioButton rdbtnCodigoUsuario = new JRadioButton(labels.getString("listado.de.canjes.label.codigo.usuario"));
		rdbtnCodigoUsuario.addActionListener((e)->{
			rdbtnCodigoUsuario.setSelected(false);
			
		});
		rdbtnCodigoUsuario.setBounds(111, 150, 146, 25);
		panel_Ordenamiento.add(rdbtnCodigoUsuario);
		if(api.obtenerRolUsuarioActivo().equals("COMUNIDAD")) {
			this.filtrarPorCodigoUsuario.setVisible(false);
			this.filtrarPorUsuarioAsociado.setVisible(false);
			this.rdbtnFiltrarPorUsuarioAsociado.setVisible(false);
			rdbtnFiltrarPorCodigoUsuario.setVisible(false);
			tfFiltrarPorCodigoUsuario.setVisible(false);
			tfFiltrarPorUsuarioAsociado.setVisible(false);
			//lbFiltrarPorUsuarioAsociado.setVisible(false);
			
			
		}
	}
	
	private void reloadGrid(List <CanjeDTO> canje) {
		
		modelo.setRowCount(0);
		if(this.rolUsuarioActivo.equals("ADMIN")) {
			for (CanjeDTO c : canje) {
				modelo.addRow(new Object[] { 
							c.getCampaña().getNombreCampaña(),
							c.getCampaña().getCodigo(),
							c.getBeneficioCanjeado().getDescripcion(),
							c.getBeneficioCanjeado().getCodigo(),
							c.getDueñoCanjeador().getNombre()+" "+ c.getDueñoCanjeador().getApellido(),
							c.getDueñoCanjeador().getDni()
				});
			}
		}
		else {
			for (CanjeDTO c : canje) {
				modelo.addRow(new Object[] { 
							c.getCampaña().getNombreCampaña(),
							c.getCampaña().getCodigo(),
							c.getBeneficioCanjeado().getDescripcion(),
							c.getBeneficioCanjeado().getCodigo(),
				});
			}
		}
		

	}
}
