package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.DueñoDTO;
import javax.swing.JScrollPane;

public class generarCatalogo extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modeloBeneficiosSinSeleccionar;
	private DefaultTableModel modeloBeneficiosSeleccionados;
	private JTable tableBeneficiosSeleccionados;
	private JTable tableBeneficioSinSeleccionar;
	private ScrollPane scrollPaneBeneficiosSinSeleccionar;
	private ScrollPane scrollPaneBeneficiosSeleccionados;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					generarCatalogo frame = new generarCatalogo(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public generarCatalogo(PersistenceApi api) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 402, 262);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
	
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(572, 11, 402, 262);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneBeneficiosSinSeleccionar = new JScrollPane();
		panel_1.add(scrollPaneBeneficiosSinSeleccionar, BorderLayout.CENTER);
		JScrollPane scrollPaneBeneficiosSeleccionados = new JScrollPane();
		panel.add(scrollPaneBeneficiosSeleccionados, BorderLayout.CENTER);
		
		String [] titulos = {
				"Descripcion",
				"Puntaje"
				
		};
		
		this.modeloBeneficiosSeleccionados = new DefaultTableModel(new Object[][] {}, titulos);
		this.modeloBeneficiosSinSeleccionar = new DefaultTableModel(new Object[][] {}, titulos);
		
		
		
		
		

		
		this.tableBeneficioSinSeleccionar.setModel(modeloBeneficiosSinSeleccionar);
		this.tableBeneficiosSeleccionados.setModel(modeloBeneficiosSeleccionados);
		scrollPaneBeneficiosSinSeleccionar.setViewportView(this.tableBeneficioSinSeleccionar);
		scrollPaneBeneficiosSeleccionados.setViewportView(this.tableBeneficiosSeleccionados);
	}
	private void reloadGrid(List<BeneficioDTO> beneficio ) {
		this.modeloBeneficiosSeleccionados.setRowCount(0);
		
			for (BeneficioDTO b : beneficio) {
				this.modeloBeneficiosSeleccionados.addRow(new Object[] { 
						"d",
						
					
				});
				
			
	}
			
	}
}
