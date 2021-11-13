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
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.modelo.Beneficio;

import java.awt.FlowLayout;

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
	private JTable table;
	private JScrollPane scrollPaneBeneficios;
	private JTable tableBeneficios;
	
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1001, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		btnVolver = new JButton("Volver a Menu Principal");
		btnVolver.setBounds(5, 342, 679, 23);
		btnVolver.addActionListener((e)->{
			setVisible(false);
			dispose();
		});
		contentPane.setLayout(null);
		
		contentPane.add(btnVolver);
		panelBeneficios = new JPanel();
		panelBeneficios.setBounds(356, 60, 326, 277);
		contentPane.add(panelBeneficios);
		panelBeneficios.setLayout(new BorderLayout(0, 0));
		
		scrollPaneBeneficios = new JScrollPane();
		panelBeneficios.add(scrollPaneBeneficios, BorderLayout.CENTER);
		
		tableBeneficios = new JTable();
		scrollPaneBeneficios.setViewportView(tableBeneficios);
		scrollPaneCampañas = new JScrollPane();
		panelCampañas = new JPanel();
		panelCampañas.setBounds(5, 60, 342, 280);
		contentPane.add(panelCampañas);
		panelCampañas.setLayout(null);
		
		scrollPaneCampañas.setBounds(0, 0, 342, 280);
		panelCampañas.add(scrollPaneCampañas);
		tableCampañas = new JTable();

		tableCampañas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Integer codigo = (Integer)tableCampañas.getValueAt(tableCampañas.getSelectedRow(),2 );
				List<CampañaDTO> campa = null;
				List<Beneficio> b = null;
				try {
					campa = api.obtenerCampañas();
					for(CampañaDTO c : campa) {
					if(codigo.equals(c.getCodigo())) {
						//se que esto esta super mal pero cuando hagan lo de obtenerBeneficio() lo saco
						b = c.getCatalogo().getListaBeneficios();
						
						break;
					}
					
				}
				modeloBeneficio.setRowCount(0);
				
				for(Beneficio bene : b) {
					
					modeloBeneficio.addRow(new Object[] {
						
						bene.getDescripcion(),
						bene.getCodigo(),
						bene.getPuntajeConsumible()
						
					});
					
					
				}
				
				tableBeneficios.setModel(modeloBeneficio);
				scrollPaneBeneficios.setViewportView(tableBeneficios);
				
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				
				
				
			}
		});
		String [] titulosBeneficios = {"DESCRIPCION","CODIGO","PUNTAJE CONSUMIBLE "};
		String[] titulos = { "NOMBRE", "ESTADO","CODIGO"};

		modeloCampaña = new DefaultTableModel(new Object[][] {}, titulos);
		modeloBeneficio = new DefaultTableModel(new Object[][] {},titulosBeneficios);
		
		
		// Obtiene la lista de usuarios a mostrar
		List<CampañaDTO> campaña;
		
		try {
			campaña = api.obtenerCampañas();
			int i = 0;
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
		
		
		
		
		


		cerrarButton = new JButton("Cerrar");
		cerrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});



	}
}


