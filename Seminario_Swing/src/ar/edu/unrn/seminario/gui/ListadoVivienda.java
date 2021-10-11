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
public class ListadoVivienda extends JFrame {
	IApi api;
	private JTable table;
	DefaultTableModel modelo;

	private JPanel contentPane;
	private JButton botonAtras;
	private JButton botonOrdenar;


	public ListadoVivienda(IApi api) throws Exception {
		this.api=api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
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
			throw e1;
			
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
		contentPane.add(botonAtras, BorderLayout.SOUTH);

		botonOrdenar = new JButton("Ordenar");
		botonOrdenar.addActionListener((e)->{
				DefaultTableModel modelo1 = new DefaultTableModel(new Object[][] {}, titulos);
				List<ViviendaDTO> viviendasOrdenadas = null ;
				for (ViviendaDTO v : viviendasOrdenadas) {
					modelo1.addRow(new Object[] { v.getDireccion().toString(), v.getDueño().toString(), v.getID() });
				}
				try {
					viviendasOrdenadas = api.obtenerViviendasOrdenadas();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"error",2);
				}
				
				table.setModel(modelo1);
			
		});
		contentPane.add(botonOrdenar, BorderLayout.NORTH);
	}
}