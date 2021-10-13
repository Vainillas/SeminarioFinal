package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class OrdenDeRetiro extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modeloVivienda;
	private DefaultTableModel modeloRecolector;
	private  IApi api;
	
	public OrdenDeRetiro(IApi api) {
		this.api = api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 851, 346);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		JPanel panelVivienda = new JPanel();
		panelVivienda.setBounds(10, 11, 370, 253);
		panelVivienda.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelVivienda);
		
		JScrollPane scrollPaneVivienda = new JScrollPane();
		panelVivienda.add(scrollPaneVivienda, BorderLayout.CENTER);
		JTable tableVivienda = new JTable();
		String[] titulosVivienda = { "DIRECCION", "FECHA", "OBSERVACION"
				
				/*labels.getString("generar.orden.retiro.titulos.vivienda.BARRIO"), 
				labels.getString("generar.orden.retiro.titulos.vivienda.CALLE"),
				labels.getString("generar.orden.retiro.titulos.vivienda.ALTURA"),
				labels.getString("generar.orden.retiro.titulos.vivienda.LATITUD"),
				labels.getString("generar.orden.retiro.titulos.vivienda.LONGITUD")*/
		};
		List<PedidoDeRetiroDTO> pedidos;
		try {
			pedidos = api.obtenerPedidosDeRetiro();
			for (PedidoDeRetiroDTO v : pedidos) {
				//modeloVivienda.addRow(new Object[] { v.getObservacion(), v.getMaquinaPesada(), v.getListResiduos(),v.getFechaDelPedido(), v.getVivienda()});
				//modeloVivienda.addRow(new Object[] { v.getVivienda}());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		modeloVivienda = new DefaultTableModel(new Object[][] {}, titulosVivienda);
			
		tableVivienda.setModel(modeloVivienda);
		
		scrollPaneVivienda.setViewportView(tableVivienda);
		
		JPanel panelRecolector = new JPanel();
		panelRecolector.setBounds(429, 11, 400, 253);
		panelRecolector.setLayout(new BorderLayout(0,0));
		contentPane.add(panelRecolector);
		
		JScrollPane scrollPaneRecolector = new JScrollPane();
		panelRecolector.add(scrollPaneRecolector);
		
		
		
		
		panelRecolector.add(scrollPaneRecolector,BorderLayout.CENTER);
		JTable tableRecolector = new JTable();
		String[] titulosRecolector = {"NOMBRE","APELLIDO","TELEFONO","DNI","EMAIL"};
		modeloRecolector = new DefaultTableModel(new Object[][] {}, titulosRecolector);
		List<RecolectorDTO> recolector= api.obtenerRecolectores();
		/*for(RecolectorDTO r : recolector) {
			modeloVivienda.addRow(new Object[] {r.getNombre(), r.getApellido(), r.getTelefono(), r.getDni(), r.getEmail()});
		}
		tableRecolector.setModel(modeloRecolector);*/
		
		scrollPaneRecolector.setViewportView(tableRecolector);
		


		
		
	}
}
