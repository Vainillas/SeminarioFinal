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
public class ListadoDeViviendas extends JFrame {
	IApi api;
	private JTable table;
	DefaultTableModel modelo;

	private JPanel contentPane;
	private JButton botonAtras;
	private JButton botonOrdenar;
	private JPanel panel;


	public ListadoDeViviendas(IApi api){
		this.api=api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 598, 583);
		contentPane = new JPanel();
		panel = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		contentPane.add(panel);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		table = new JTable();
		String[] titulos = { "DIRECCION", "DUEÑO","CÓDIGO" };
		
		
		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar
		List<ViviendaDTO> viviendas;
		try {
			viviendas = api.obtenerViviendas();
			for (ViviendaDTO v : viviendas) {
				modelo.addRow(new Object[] { v.getDireccion().toString(), v.getDueño().toString(), v.getID() });
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

		botonAtras = new JButton("Atras");

		botonAtras.addActionListener((e)->{
				setVisible(false);
			
		});
		panel.add(botonAtras, BorderLayout.SOUTH);

		botonOrdenar = new JButton("Ordenar"); 
		botonOrdenar.addActionListener((e)->{
				DefaultTableModel modelo1 = new DefaultTableModel(new Object[][] {}, titulos);
				
				try {
					List<ViviendaDTO> viviendasOrdenadas = api.obtenerViviendasOrdenadas();
					
					for (ViviendaDTO v : viviendasOrdenadas) {
						modelo1.addRow(new Object[] { v.getDireccion().toString(), v.getDueño().toString(), v.getID() });
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",2);
				}
				
				table.setModel(modelo1);
			
		});
		panel.add(botonOrdenar, BorderLayout.NORTH);
	}
}