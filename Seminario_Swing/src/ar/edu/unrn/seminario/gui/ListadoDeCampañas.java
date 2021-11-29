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
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CampañaDTO;
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.utilities.NotEditJTable;
import ar.edu.unrn.seminario.utilities.Predicate;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;

public class ListadoDeCampañas extends JFrame {

	private JPanel contentPane;
	private JTable tableCampañas;
	private DefaultTableModel modeloCampaña;
	private DefaultTableModel modeloBeneficio;
	
	private IApi api;
	private JButton activarButton;
	private JButton desactivarButton;
	private JButton btnVolver;
	private JPanel panelBeneficios;
	private JButton cerrarButton;
	private JPanel panelCampañas;
	private JScrollPane scrollPaneCampañas;
	private JScrollPane scrollPaneBeneficios;
	private JTable tableBeneficios;
	private JTextField tfEstado;
	private JTextField tfCodigo;
	private JTextField tfNombre;
	private JTextField tfDescripcion;
	private JTextField tfFiltrarBeneficioPorCodigo;
	private JTextField tfFiltrarBeneficioPorPuntaje;
	private List<Beneficio> beneficiosActual = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					ListadoDeCampañas frame = new ListadoDeCampañas(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ListadoDeCampañas(IApi api) {
		this.api = api;
		setTitle("Listar Campañas");
		//setTitle(labels.getString("listado.de.campañas.titulo.listado"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1045, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		btnVolver = new JButton("Volver a Menu Principal");
		//btnVolver = new JButton(labels.getString("listado.de.campañas.boton.menu.principal"));
		btnVolver.setBounds(5, 342, 970, 23);
		btnVolver.addActionListener((e)->{
			setVisible(false);
			dispose();
		});
		contentPane.setLayout(null);
		
		contentPane.add(btnVolver);
		panelBeneficios = new JPanel();
		panelBeneficios.setBounds(356, 60, 435, 277);
		contentPane.add(panelBeneficios);
		panelBeneficios.setLayout(new BorderLayout(0, 0));
		
		scrollPaneBeneficios = new JScrollPane();
		panelBeneficios.add(scrollPaneBeneficios, BorderLayout.CENTER);
		
		tableBeneficios = new NotEditJTable();
		tableBeneficios.setCellSelectionEnabled(false);
		scrollPaneBeneficios.setViewportView(tableBeneficios);
		scrollPaneCampañas = new JScrollPane();
		panelCampañas = new JPanel();
		panelCampañas.setBounds(5, 60, 342, 280);
		contentPane.add(panelCampañas);
		panelCampañas.setLayout(null);
		
		scrollPaneCampañas.setBounds(0, 0, 342, 280);
		panelCampañas.add(scrollPaneCampañas);
		tableCampañas = new NotEditJTable();
		tableCampañas.setCellSelectionEnabled(false);
		tableCampañas.setColumnSelectionAllowed(false);
		tableCampañas.setRowSelectionAllowed(false);
		
		tableCampañas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Integer codigo = null;
				modeloBeneficio.setRowCount(0);
				List<CampañaDTO> campa = null;
				List<Beneficio> b = null;
				try {
					codigo = (Integer)tableCampañas.getValueAt(tableCampañas.getSelectedRow(),2 );
					
					campa = api.obtenerCampañas();
					for(CampañaDTO c : campa) {
						if(codigo.equals(c.getCodigo())) {
							b = c.getCatalogo().getListaBeneficios();
							beneficiosActual = b;
							break;
						}
					}
					for(Beneficio bene : b) {
						
						modeloBeneficio.addRow(new Object[] {
							
							bene.getDescripcion(),
							bene.getCodigo(),
							bene.getPuntajeConsumible()
							
						});
						
						
					}
				
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				}
				catch(java.lang.ClassCastException e1) {
					
				}
				
				
				
			}
		});
		String [] titulosBeneficios = {"DESCRIPCION","CODIGO","PUNTAJE CONSUMIBLE "};
		/*String [] titulosBeneficios = {labels.getString("listado.de.campañas.titulo.descripcion"),
		 * labels.getString("listado.de.campañas.titulo.codigo"),
		 * labels.getString("listado.de.campañas.titulo.puntaje.consumible")};
		 * 
		 */
		String[] titulos = { "NOMBRE", "ESTADO","CODIGO"};
		/*String[] titulos = { labels.getString("listado.de.campañas.titulo.nombre"),
		 *  labels.getString("listado.de.campañas.titulo.estado"),
		 *  labels.getString("listado.de.campañas.titulo.codigo")};
		 * 
		 */
		modeloCampaña = new DefaultTableModel(new Object[][] {}, titulos);
		modeloBeneficio = new DefaultTableModel(new Object[][] {},titulosBeneficios);
		
		
		// Obtiene la lista de usuarios a mostrar
		List<CampañaDTO> campaña;
		
		try {
			campaña = api.obtenerCampañas();
			for (CampañaDTO c : campaña) {
				modeloCampaña.addRow(new Object[] { 
						c.getNombreCampaña(),
						c.getEstado(),
						c.getCodigo() 
						});
				

			}
		} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		

		tableCampañas.setModel(modeloCampaña);
		scrollPaneCampañas.setViewportView(tableCampañas);
		tableBeneficios.setModel(modeloBeneficio);
		scrollPaneBeneficios.setViewportView(tableBeneficios);
		
		JLabel lbCampañas = new JLabel("campa\u00F1as");
		/*
		 * JLabel lbCampañas = new JLabel(labels.getString("listado.de.campañas.label.campañas"));
		 */
		lbCampañas.setHorizontalAlignment(SwingConstants.CENTER);
		lbCampañas.setBounds(79, 35, 179, 14);
		contentPane.add(lbCampañas);
		
		JLabel lbBeneficio = new JLabel("Beneficios De La campa\u00F1a Seleccionada");
		/*
		 * JLabel lbBeneficio = new JLabel(labels.getString("listado.de.campañas.label.beneficios.de.campaña"));
		 */
		lbBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		lbBeneficio.setBounds(453, 35, 243, 14);
		contentPane.add(lbBeneficio);
		
		JPanel panelFiltradosCampaña = new JPanel();
		panelFiltradosCampaña.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelFiltradosCampaña.setBounds(801, 60, 218, 116);
		contentPane.add(panelFiltradosCampaña);
		panelFiltradosCampaña.setLayout(null);
		
		JLabel lbFiltrarPorNombre = new JLabel("Nombre :");
		/*
		 * JLabel lbFiltrarPorNombre = new JLabel(labels.getString("listado.de.campañas.filtrado.por.nombre"));
		 */
		lbFiltrarPorNombre.setBounds(16, 8, 54, 14);
		panelFiltradosCampaña.add(lbFiltrarPorNombre);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(80, 5, 86, 20);
		panelFiltradosCampaña.add(tfNombre);
		tfNombre.setColumns(10);
		
		JRadioButton rdbtnNombre = new JRadioButton("");
		rdbtnNombre.addActionListener((e)->{
			
			Predicate<CampañaDTO> predicado = (CampañaDTO c)->c.getNombreCampaña().toLowerCase().contains(this.tfNombre.getText().toLowerCase());
			rdbtnNombre.setSelected(false);
			try {
				if(!this.tfNombre.getText().equals("")) {
					
					reloadGrid(api.obtenerCampañas(predicado));
					
				}
				
			} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
			
			
			
		});
		rdbtnNombre.setBounds(170, 5, 21, 21);
		panelFiltradosCampaña.add(rdbtnNombre);
		
		JLabel lblEstado = new JLabel("Estado:");
		/*
		 * JLabel lblEstado = new JLabel(labels.getString("listado.de.campañas.filtrado.por.estado"));
		 */
		lblEstado.setBounds(19, 34, 51, 14);
		panelFiltradosCampaña.add(lblEstado);
		
		tfEstado = new JTextField();
		tfEstado.setBounds(80, 31, 86, 20);
		panelFiltradosCampaña.add(tfEstado);
		tfEstado.setColumns(10);
		
		JRadioButton rdbtnEstado = new JRadioButton("");
		rdbtnEstado.addActionListener((e)->{
			rdbtnEstado.setSelected(false);
			if(!this.tfEstado.getText().equals("")) {
				Predicate<CampañaDTO> predicate  = (CampañaDTO c)->c.getEstado().toLowerCase().contains(this.tfEstado.getText().toLowerCase());
				try {
				reloadGrid(api.obtenerCampañas(predicate));
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		rdbtnEstado.setBounds(170, 31, 21, 21);
		panelFiltradosCampaña.add(rdbtnEstado);
		
		JLabel lblCodigo = new JLabel("Codigo:");
		/*
		 * JLabel lblCodigo = new JLabel(labels.getString("listado.de.campañas.filtrado.por.codigo"));
		 */
		lblCodigo.setBounds(19, 60, 51, 14);
		panelFiltradosCampaña.add(lblCodigo);
		
		tfCodigo = new JTextField();
		tfCodigo.setBounds(80, 57, 86, 20);
		tfCodigo.setColumns(10);
		panelFiltradosCampaña.add(tfCodigo);
		
		JRadioButton rdbtnCodigoCampaña = new JRadioButton("");
		rdbtnCodigoCampaña.addActionListener((e)->{
		rdbtnCodigoCampaña.setSelected(false);
			if(!this.tfCodigo.getText().equals("")) {
				try {
					Predicate <CampañaDTO> predicate = (CampañaDTO c)->c.getCodigo() == Integer.parseInt(this.tfCodigo.getText());
				reloadGrid(api.obtenerCampañas(predicate));
				}catch (AppException | NotNullException | DataEmptyException | NotNumberException  e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				}catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Debe introducir un codigo numerico","error",JOptionPane.ERROR_MESSAGE);
					/*
					 * JOptionPane.showMessageDialog(null, labels.getString("listado.de.campañas.mensaje.error.numerico"),"error",JOptionPane.ERROR_MESSAGE);
					 */
				}
		
			}
		});
		rdbtnCodigoCampaña.setBounds(170, 57, 21, 21);
		panelFiltradosCampaña.add(rdbtnCodigoCampaña);
		
		JButton btnLimpiarFiltroCampañas = new JButton("Limpiar Filtro");
		/*
		 * JButton btnLimpiarFiltroCampañas = new JButton(labels.getString(" listado.de.campañas.filtrado.limpiar.filtro"));
		 */
		btnLimpiarFiltroCampañas.addActionListener((e)->{
			
				try {
					this.tfCodigo.setText("");
					this.tfEstado.setText("");
					this.tfNombre.setText("");
					reloadGrid(api.obtenerCampañas());
					
				} 
				catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				}
			
			
		});
	
		btnLimpiarFiltroCampañas.setBounds(56, 85, 110, 23);
		panelFiltradosCampaña.add(btnLimpiarFiltroCampañas);
		
		JLabel lbFiltrarPor = new JLabel("Filtrar Campa\u00F1a por:");
		/*
		 * 	JLabel lbFiltrarPor = new JLabel(labels.getString("listado.de.campañas.filtrar.campañas.por"));
		 */
		lbFiltrarPor.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrarPor.setBounds(836, 35, 122, 14);
		contentPane.add(lbFiltrarPor);
		
		JPanel panelFiltradosBeneficios = new JPanel();
		panelFiltradosBeneficios.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelFiltradosBeneficios.setBounds(801, 203, 218, 134);
		contentPane.add(panelFiltradosBeneficios);
		panelFiltradosBeneficios.setLayout(null);
		
		JLabel lbFiltradoPorDescripcion = new JLabel("Descripcion:");
		/*
		 *JLabel lbFiltradoPorDescripcion = new JLabel(labels.getString("listado.de.campañas.filtrado.por.descripcion"));
		 */
		lbFiltradoPorDescripcion.setHorizontalAlignment(SwingConstants.TRAILING);
		lbFiltradoPorDescripcion.setBounds(0, 8, 79, 14);
		panelFiltradosBeneficios.add(lbFiltradoPorDescripcion);
		
		tfDescripcion = new JTextField();
		tfDescripcion.setBounds(84, 5, 86, 20);
		panelFiltradosBeneficios.add(tfDescripcion);
		tfDescripcion.setColumns(10);
		
		JRadioButton rdbtnDescripcion = new JRadioButton("");
		rdbtnDescripcion.addActionListener((e)->{
			rdbtnDescripcion.setSelected(false);
			if(!this.tfDescripcion.getText().equals("")) {
				
				if(this.tableCampañas.getSelectedRowCount() != 0 ) {
					
					Predicate <BeneficioDTO> predicado = (BeneficioDTO b)->
					b.getDescripcion().toLowerCase().contains(this.tfDescripcion.getText().toLowerCase());
					try {
						reloadGridBeneficio(api.obtenerBeneficios(predicado));
					} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
					}
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Debe Seleccionar Una Campaña Antes De Filtrar Beneficios ","Error",JOptionPane.ERROR_MESSAGE);
					/*
					 * JOptionPane.showMessageDialog(null, labels.getString("listado.de.campañas.mensaje.error.seleccionar.campaña"),"Error",JOptionPane.ERROR_MESSAGE);
					 */
				}

			}
		});
		rdbtnDescripcion.setBounds(175, 5, 21, 21);
		panelFiltradosBeneficios.add(rdbtnDescripcion);
		
		JLabel lbFiltrarBeneficioPorCodigo = new JLabel("Codigo:");
		/*
		 * JLabel lbFiltrarBeneficioPorCodigo = new JLabel(labels.getString("listado.de.campañas.filtrado.por.codigo"));
		 */
		lbFiltrarBeneficioPorCodigo.setHorizontalAlignment(SwingConstants.TRAILING);
		lbFiltrarBeneficioPorCodigo.setBounds(10, 34, 63, 14);
		panelFiltradosBeneficios.add(lbFiltrarBeneficioPorCodigo);
		
		tfFiltrarBeneficioPorCodigo = new JTextField();
		tfFiltrarBeneficioPorCodigo.setBounds(84, 31, 86, 20);
		tfFiltrarBeneficioPorCodigo.setColumns(10);
		panelFiltradosBeneficios.add(tfFiltrarBeneficioPorCodigo);
		
		JRadioButton rdbtnFiltrarBeneficioPorCodigo = new JRadioButton("");
		rdbtnFiltrarBeneficioPorCodigo.addActionListener((e)->{
			
			
		});
		rdbtnFiltrarBeneficioPorCodigo.setBounds(175, 31, 21, 21);
		panelFiltradosBeneficios.add(rdbtnFiltrarBeneficioPorCodigo);
		
		JLabel lbFiltrarPorPuntaje = new JLabel("Puntaje:");
		/*
		 * JLabel lbFiltrarPorPuntaje = new JLabel(labels.getString("listado.de.campañas.filtrado.beneficio.por.puntaje"));
		 */
		lbFiltrarPorPuntaje.setHorizontalAlignment(SwingConstants.TRAILING);
		lbFiltrarPorPuntaje.setBounds(10, 60, 63, 14);
		panelFiltradosBeneficios.add(lbFiltrarPorPuntaje);
		
		tfFiltrarBeneficioPorPuntaje = new JTextField();
		tfFiltrarBeneficioPorPuntaje.setBounds(84, 57, 86, 20);
		tfFiltrarBeneficioPorPuntaje.setColumns(10);
		panelFiltradosBeneficios.add(tfFiltrarBeneficioPorPuntaje);
		
		JRadioButton rdbtnPuntaje = new JRadioButton("");
		rdbtnPuntaje.setBounds(175, 57, 21, 21);
		panelFiltradosBeneficios.add(rdbtnPuntaje);
		
		JButton btnLimpiarFIltroBeneficios = new JButton("Limpiar Filtro");
		/*
		 * JButton btnLimpiarFIltroBeneficios = new JButton(labels.getString("listado.de.campañas.filtrado.limpiar.filtro"));
		 */
		btnLimpiarFIltroBeneficios.addActionListener((e)->{

				//modeloBeneficio.setRowCount(0);
				modeloBeneficio.setRowCount(0);
				this.tfDescripcion.setText("");
				for (Beneficio b : this.beneficiosActual) {
					this.modeloBeneficio.addRow(new Object[] {
						b.getDescripcion(),
						b.getCodigo(),
						b.getPuntajeConsumible()
					});
				}
			
			
			
		});
		btnLimpiarFIltroBeneficios.setBounds(56, 100, 114, 23);
		panelFiltradosBeneficios.add(btnLimpiarFIltroBeneficios);
		
		JLabel lbFiltrarBeneficio = new JLabel("Filtrar Beneficio Por:");
		/*
		 * JLabel lbFiltrarBeneficio = new JLabel(labels.getString("listado.de.campañas.filtrar.beneficios.por"));
		 */
		lbFiltrarBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		lbFiltrarBeneficio.setBounds(822, 179, 179, 14);
		contentPane.add(lbFiltrarBeneficio);
		
		
		
		
		


		cerrarButton = new JButton("Cerrar");
		/*
		 * cerrarButton = new JButton(labels.getString("listado.de.campañas.boton.cerrar"));
		 */
		cerrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});



	}
	/**private void reloadGridBeneficio(List<Beneficio> beneficio) {
		this.modeloBeneficio.setRowCount(0);
		for (Beneficio b : beneficio) {
			this.modeloBeneficio.addRow(new Object[] {
				b.getDescripcion(),
				b.getCodigo(),
				b.getPuntajeConsumible()
			});
		}
		
	}*/
	private void reloadGridBeneficio(List<BeneficioDTO> beneficio) {
		this.modeloBeneficio.setRowCount(0);
		for (BeneficioDTO b : beneficio) {
			this.modeloBeneficio.addRow(new Object[] {
				b.getDescripcion(),
				b.getCodigo(),
				b.getPuntajeConsumible()
			});
		}
		
	}

	private void reloadGrid(List<CampañaDTO> campañaDTO) {	
		this.modeloCampaña.setRowCount(0);
		this.modeloBeneficio.setRowCount(0);
		for (CampañaDTO c : campañaDTO) {
			this.modeloCampaña.addRow(new Object[] {
				c.getNombreCampaña(),
				c.getEstado(),
				c.getCodigo()
			});
		}

		
	}
}


