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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CampañaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.utilities.Predicate;
import java.awt.Color;
import java.awt.Font;

public class CanjearPuntos extends JFrame {

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
	private JTextField tfPuntosDelDueño;
	private int codCampaña;
	private int codBeneficio;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					CanjearPuntos frame = new CanjearPuntos(api);
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
	public CanjearPuntos(IApi api) {
		this.api = api;
		setTitle("Listar Campañas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1045, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		btnVolver = new JButton("Volver a Menu Principal");
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
		
		tableBeneficios = new JTable();
		tableBeneficios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					CampañaDTO c = api.obtenerCampañaPorCodigo(codCampaña);
					for(Beneficio b :c.getCatalogo().getListaBeneficios() ) {
						if(tableBeneficios.getValueAt(tableBeneficios.getSelectedColumn(), 0).equals(b.getDescripcion())) {
							codBeneficio = b.getCodigo();
							break;
						}
					}
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
	
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		//tableBeneficios.setCellSelectionEnabled(false);
		scrollPaneBeneficios.setViewportView(tableBeneficios);
		scrollPaneCampañas = new JScrollPane();
		panelCampañas = new JPanel();
		panelCampañas.setBounds(5, 60, 342, 280);
		contentPane.add(panelCampañas);
		panelCampañas.setLayout(null);
		
		scrollPaneCampañas.setBounds(0, 0, 342, 280);
		panelCampañas.add(scrollPaneCampañas);
		tableCampañas = new JTable();
		tableCampañas.setCellSelectionEnabled(false);
		//tableCampañas.setColumnSelectionAllowed(false);
		tableCampañas.setRowSelectionAllowed(false);
		

		tableCampañas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String nombreCam = null;


				List<CampañaDTO> campa = null;
				List<Beneficio> b = null;
				try {
					nombreCam = (String)tableCampañas.getValueAt(tableCampañas.getSelectedRow(),0 );
					campa = api.obtenerCampañas();
					for(CampañaDTO c : campa) {
					if(nombreCam.equals(c.getNombreCampaña())) {
						b = c.getCatalogo().getListaBeneficios();
						break;
					}
					codCampaña = c.getCodigo();
				}
				modeloBeneficio.setRowCount(0);
				
				for(Beneficio bene : b) {
					modeloBeneficio.addRow(new Object[] {
							
						bene.getDescripcion(),
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
		String [] titulosBeneficios = {"DESCRIPCION","PUNTAJE CONSUMIBLE "};
		String[] titulos = { "NOMBRE"};

		modeloCampaña = new DefaultTableModel(new Object[][] {}, titulos);
		modeloBeneficio = new DefaultTableModel(new Object[][] {},titulosBeneficios);
		
		
		// Obtiene la lista de usuarios a mostrar
		List<CampañaDTO> campaña;
		
		try {
			
			campaña = api.obtenerCampañas();
			
			for (CampañaDTO c : campaña) {
				modeloCampaña.addRow(new Object[] { 
						c.getNombreCampaña(),
						});
				

			}
		} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		

		tableCampañas.setModel(modeloCampaña);
		scrollPaneCampañas.setViewportView(tableCampañas);
		tableBeneficios.setModel(modeloBeneficio);
		scrollPaneBeneficios.setViewportView(tableBeneficios);
		
		JLabel lbCampañas = new JLabel("Indique la Campa\u00F1a");
		lbCampañas.setHorizontalAlignment(SwingConstants.CENTER);
		lbCampañas.setBounds(29, 35, 302, 14);
		contentPane.add(lbCampañas);
		
		JLabel lbBeneficio = new JLabel("Indique el Beneficio Que Quiere Canjear");
		lbBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		lbBeneficio.setBounds(453, 35, 243, 14);
		contentPane.add(lbBeneficio);
		
		JLabel lbMisPuntos = new JLabel("Mis Puntos:");
		lbMisPuntos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbMisPuntos.setBounds(813, 26, 110, 23);
		contentPane.add(lbMisPuntos);
		
		tfPuntosDelDueño = new JTextField();
		tfPuntosDelDueño.setEditable(false);
		tfPuntosDelDueño.setBackground(Color.WHITE);
		tfPuntosDelDueño.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfPuntosDelDueño.setForeground(Color.BLACK);
		//tfPuntosDelDueño.setText( "55");
		try {
			tfPuntosDelDueño.setText(String.valueOf(api.obtenerDueñoActivo().getPuntaje()));
			
		} catch (AppException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		tfPuntosDelDueño.setBounds(849, 60, 94, 26);
		contentPane.add(tfPuntosDelDueño);
		tfPuntosDelDueño.setColumns(10);
		
		JButton btnCanjearPuntos = new JButton("Canjear Puntos");
		btnCanjearPuntos.addActionListener((e)->{
			
			if(this.tableBeneficios.isRowSelected(this.tableBeneficios.getSelectedRow())) {
				
				
				try {
					
					api.generarCanje(codBeneficio, codCampaña);
					int res = JOptionPane.showConfirmDialog(null,"Desea Canjear otro Beneficio?","Mensaje Informativo",JOptionPane.INFORMATION_MESSAGE );
					if(res == 0 ) {
						
					}
					else {
						setVisible(false);
						dispose();
						
					}
					JOptionPane.showMessageDialog(null,"Todo Sukistrukis,mañana lo termino xd","bien",JOptionPane.INFORMATION_MESSAGE);
					
				} catch (AppException | NotNullException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else {
				JOptionPane.showMessageDialog(null,"Debe seleccionar Un Beneficio","Error",JOptionPane.ERROR_MESSAGE);
			}
		});
		btnCanjearPuntos.setBounds(809, 97, 151, 23);
		contentPane.add(btnCanjearPuntos);
		
		
		
		
		


		cerrarButton = new JButton("Cerrar");
		cerrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});



	}
	private void reloadGridBeneficio(List<BeneficioDTO> beneficio) {
		this.modeloBeneficio.setRowCount(0);
		for (BeneficioDTO b : beneficio) {
			this.modeloBeneficio.addRow(new Object[] {
				b.getDescripcion(),
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
			});
		}

		
	}
}


