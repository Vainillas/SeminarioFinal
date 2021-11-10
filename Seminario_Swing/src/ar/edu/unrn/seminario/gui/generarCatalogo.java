package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.DueñoDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class generarCatalogo extends JFrame {

	private JPanel contentPane;
	private JTable tableBeneficiosNoAsociados;
	private JTable tableBeneficiosAsociados;
	private DefaultTableModel modeloBeneficiosNoAsociados;
	private DefaultTableModel modeloBeneficiosAsociados;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					generarCatalogo frame = new generarCatalogo(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public generarCatalogo(IApi api) {
		setTitle("Generar Campaña");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panelBeneficiosNoAsociados = new JPanel();
		panelBeneficiosNoAsociados.setBounds(10, 50, 402, 262);
		contentPane.add(panelBeneficiosNoAsociados);
		String [] titulos = {
				"Descripcion",
				"Puntaje"
				
		};
		modeloBeneficiosAsociados = new DefaultTableModel(new Object[][] {}, titulos);
		modeloBeneficiosNoAsociados = new DefaultTableModel(new Object[][] {}, titulos);
		try {
			List<BeneficioDTO> beneficio = api.obtenerBeneficios();
			for(BeneficioDTO b : beneficio) {
				
				modeloBeneficiosNoAsociados.addRow(new Object[] {b.getDescripcion(),b.getPuntajeConsumible()});
			}
		} catch (AppException | NotNullException | DataEmptyException | NotNumberException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		JPanel panelBeneficiosAsociados = new JPanel();
		panelBeneficiosAsociados.setBounds(572, 50, 402, 262);
		contentPane.add(panelBeneficiosAsociados);
		
		panelBeneficiosAsociados.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneBeneficiosAsociados = new JScrollPane();
		panelBeneficiosAsociados.add(scrollPaneBeneficiosAsociados, BorderLayout.CENTER);
		
		tableBeneficiosAsociados = new JTable();
		tableBeneficiosAsociados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String  descripcion = (String)tableBeneficiosAsociados.getValueAt(tableBeneficiosAsociados.getSelectedRow(),0 );
				String puntaje = (String)tableBeneficiosAsociados.getValueAt(tableBeneficiosAsociados.getSelectedRow(),1 );
				//tableBeneficiosNoAsociados.clearSelection();
				
				modeloBeneficiosAsociados.removeRow(tableBeneficiosAsociados.getSelectedRow());
				
				modeloBeneficiosNoAsociados.addRow(new Object[] {descripcion,puntaje});
				tableBeneficiosNoAsociados.setModel(modeloBeneficiosNoAsociados);
			}
		});
		scrollPaneBeneficiosAsociados.setViewportView(tableBeneficiosAsociados);
		panelBeneficiosNoAsociados.setLayout(new BorderLayout(0, 0));
		this.tableBeneficiosAsociados.setModel(this.modeloBeneficiosAsociados);
		
		JScrollPane scrollPaneBeneficioNoAsociado = new JScrollPane();
		panelBeneficiosNoAsociados.add(scrollPaneBeneficioNoAsociado, BorderLayout.CENTER);
		tableBeneficiosNoAsociados = new JTable();
		tableBeneficiosNoAsociados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String  descripcion = (String)tableBeneficiosNoAsociados.getValueAt(tableBeneficiosNoAsociados.getSelectedRow(),0 );
				String puntaje = (String)tableBeneficiosNoAsociados.getValueAt(tableBeneficiosNoAsociados.getSelectedRow(),1 );
				//tableBeneficiosNoAsociados.clearSelection();
				
				modeloBeneficiosNoAsociados.removeRow(tableBeneficiosNoAsociados.getSelectedRow());
				
				modeloBeneficiosAsociados.addRow(new Object[] {descripcion,puntaje});
				tableBeneficiosAsociados.setModel(modeloBeneficiosAsociados);

				
			}
		});
		
		tableBeneficiosNoAsociados.setModel(this.modeloBeneficiosNoAsociados);
		scrollPaneBeneficioNoAsociado.setViewportView(tableBeneficiosNoAsociados);
		
		JLabel lbBeneficiosSinAsociar = new JLabel("beneficios sin Asociar");
		lbBeneficiosSinAsociar.setHorizontalAlignment(SwingConstants.CENTER);
		lbBeneficiosSinAsociar.setBounds(141, 25, 168, 14);
		contentPane.add(lbBeneficiosSinAsociar);
		
		JLabel lbBeneficiosAsociados = new JLabel("Beneficios Asociados ");
		lbBeneficiosAsociados.setHorizontalAlignment(SwingConstants.CENTER);
		lbBeneficiosAsociados.setBounds(678, 25, 199, 14);
		contentPane.add(lbBeneficiosAsociados);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(422, 50, 140, 262);
		contentPane.add(panelBotones);
		
		JButton btnAgregarBeneficio = new JButton("AgregarBeneficio");
		btnAgregarBeneficio.addActionListener((e)->{
			if(btnAgregarBeneficio.isEnabled()) {
				
			}
			
		});
		
		panelBotones.add(btnAgregarBeneficio);
		
		
		
		
		
		


		

	}
	private void reloadGrid(List<BeneficioDTO> beneficio ) {
		/*this.modeloBeneficiosSeleccionados.setRowCount(0);
		
			for (BeneficioDTO b : beneficio) {
				this.modeloBeneficiosSeleccionados.addRow(new Object[] { 
						"d",
						
					
				});*/
				
			
	}
	}

