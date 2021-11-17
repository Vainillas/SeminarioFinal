package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.utilities.Predicate;

import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ListadoDeCanjes extends JFrame {

	private JPanel contentPane;
	DefaultTableModel modelo;
	IApi api;
	private JTable table;
	private JTextField tfFiltrarPorNombreCampaña;
	private JTextField tfFiltrarPorCodigoCampaña;
	private JTextField tfFiltrarDescripcionBeneficio;
	private JTextField tfFiltrarPorUsuarioAsociado;
	private JTextField tfFiltrarPorCodigoUsuario;
	private JTextField tfFiltrarPorCodigoBeneficio;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					ListadoDeCanjes frame = new ListadoDeCanjes(api);
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
	public ListadoDeCanjes(IApi api) {
		this.api = api;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1139, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);


			String[] titulos = { 
					"NOMBRE CAMPAÑA",
					"CODIGO CAMPAÑA", 
					"DESCRIPCION BENEFICIO", 
					"CODIGO BENEFICIO", 
					"DUEÑO ASOCIADO",
					"DNI DUEÑO" 
					
			};
			
			List<CanjeDTO> canjes = null;
			try {
				canjes = api.obtenerCanjes();
			} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
			}
			
			for (CanjeDTO c : canjes) {
				modelo.addRow(new Object[] {
						c.getCampaña().getNombreCampaña(),
						c.getCampaña().getCodigo(),
						c.getBeneficioCanjeado().getDescripcion(),
						c.getBeneficioCanjeado().getCodigo(),
						c.getDueñoCanjeador().getNombre(),
						c.getDueñoCanjeador().getDni()

				});
			}
			
		modelo = new DefaultTableModel(new Object[][] {}, titulos);



		JButton cerrarButton = new JButton("Cerrar");
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
		
		table = new JTable();
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		
		JPanel panel_Filtrados = new JPanel();
		panel_Filtrados.setBounds(848, 5, 265, 203);
		contentPane.add(panel_Filtrados);
		panel_Filtrados.setLayout(null);
		
		JLabel lbFiltrarPor = new JLabel("Filtrar Por:");
		lbFiltrarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrarPor.setBounds(69, 11, 110, 14);
		panel_Filtrados.add(lbFiltrarPor);
		
		JLabel filtrarPorNombreCampaña = new JLabel("Nombre De Campa\u00F1a:");
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
				reloadGrid(api.obtenerCanjes(predicate));
			} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
			}
			
			
		});
		rdbtnFiltrarPorNombreCampaña.setBounds(238, 36, 21, 23);
		rdbtnFiltrarPorNombreCampaña.addActionListener((e)->{
			
			
		});
		panel_Filtrados.add(rdbtnFiltrarPorNombreCampaña);
		
		JLabel filtrarPorCodigoCampaña = new JLabel("Codigo De Campa\u00F1a:");

		filtrarPorCodigoCampaña.setHorizontalAlignment(SwingConstants.CENTER);
		
		filtrarPorCodigoCampaña.setBounds(0, 64, 139, 14);
		panel_Filtrados.add(filtrarPorCodigoCampaña);
		
		JLabel filtrarPorDescripcionBeneficio = new JLabel("Descripcion Del Beneficio:");
		filtrarPorDescripcionBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorDescripcionBeneficio.setBounds(0, 89, 139, 14);
		panel_Filtrados.add(filtrarPorDescripcionBeneficio);
		
		JLabel filtrarPorCodigoBeneficio = new JLabel("Codigo Del Beneficio");
		filtrarPorCodigoBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorCodigoBeneficio.setBounds(0, 114, 139, 14);
		panel_Filtrados.add(filtrarPorCodigoBeneficio);
		
		JLabel filtrarPorUsuarioAsociado = new JLabel("Usuario Asociado:");
		filtrarPorUsuarioAsociado.setHorizontalAlignment(SwingConstants.CENTER);
		filtrarPorUsuarioAsociado.setBounds(0, 139, 139, 14);
		panel_Filtrados.add(filtrarPorUsuarioAsociado);
		
		JLabel filtrarPorCodigoUsuario = new JLabel("Codigo Usuario:");
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
		
		JRadioButton rdbtnFiltrarPorUsuarioAsociado = new JRadioButton("");
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
		panel_Ordenamiento.setBounds(848, 210, 265, 178);
		contentPane.add(panel_Ordenamiento);
		panel_Ordenamiento.setLayout(null);
		
		JLabel lbOrdenarPor = new JLabel("Ordenar Por:");
		lbOrdenarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrdenarPor.setBounds(70, 0, 119, 14);
		panel_Ordenamiento.add(lbOrdenarPor);
		
		JRadioButton rdbtnOrdenarNombreCampaña = new JRadioButton("Nombre De Campa\u00F1a");
		rdbtnOrdenarNombreCampaña.addActionListener((e)->{
			rdbtnOrdenarNombreCampaña.setSelected(false);
			
		});
		rdbtnOrdenarNombreCampaña.setBounds(24, 21, 165, 25);
		panel_Ordenamiento.add(rdbtnOrdenarNombreCampaña);
		
		JRadioButton rdbtnOrdenarCodigoCampaña = new JRadioButton("Codigo De Campa\u00F1a");
		rdbtnOrdenarCodigoCampaña.addActionListener((e)->{
			rdbtnOrdenarCodigoCampaña.setSelected(false);
		});
		rdbtnOrdenarCodigoCampaña.setBounds(111, 49, 148, 23);
		panel_Ordenamiento.add(rdbtnOrdenarCodigoCampaña);
		
		JRadioButton rdbtnOrdenarDescripcionBeneficio = new JRadioButton("Descripcion De Beneficio");
		rdbtnOrdenarDescripcionBeneficio.addActionListener((e)->{
			rdbtnOrdenarDescripcionBeneficio.setSelected(false);
		});
		rdbtnOrdenarDescripcionBeneficio.setBounds(24, 75, 184, 23);
		panel_Ordenamiento.add(rdbtnOrdenarDescripcionBeneficio);
		
		JRadioButton rdbtnOrdenarCodigoBeneficio = new JRadioButton("Codigo Beneficio");
		rdbtnOrdenarCodigoBeneficio.addActionListener((e)->{
			rdbtnOrdenarCodigoBeneficio.setSelected(false);
		});
		rdbtnOrdenarCodigoBeneficio.setBounds(111, 101, 148, 23);
		panel_Ordenamiento.add(rdbtnOrdenarCodigoBeneficio);
		
		JRadioButton rdbtnUsuarioAsociado = new JRadioButton("Usuario Asociado");
		rdbtnUsuarioAsociado.addActionListener((e)->{
			rdbtnUsuarioAsociado.setSelected(false);
		});
		
		rdbtnUsuarioAsociado.setBounds(24, 122, 154, 25);
		panel_Ordenamiento.add(rdbtnUsuarioAsociado);
		
		JRadioButton rdbtnCodigoUsuario = new JRadioButton("Codigo Usuario");
		rdbtnCodigoUsuario.addActionListener((e)->{
			rdbtnCodigoUsuario.setSelected(false);
			
		});
		rdbtnCodigoUsuario.setBounds(111, 150, 146, 25);
		panel_Ordenamiento.add(rdbtnCodigoUsuario);

	}
	
	private void reloadGrid(List <CanjeDTO> canje) {
		
		modelo.setRowCount(0);
		
		// Agrega los usuarios en el model
		

	}
}
