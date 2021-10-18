package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;

import javax.swing.JOptionPane;

import javax.swing.JButton;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;
/*La pantalla de GenerarOrdenRetiro tiene que ser mas grande por el tema de que la observación puede 
 * ser de muchos renglones. 
 * 
 * También tiene que tener una barra para poder deslizar entre el listado de los pedidos de retiro. 
También hay que contemplar la opción de que se puede generar una orden de retiro sin un recolector 
y asignárselo después.
Esas son las correcciones de GenerarOrdenRetiro hasta ahora
Nosotros vamos a estar trabajando en la implementación de la OrdenDeRetiro en la BD y en el programa, 
después seguro pasemos a los recolectores.*/





public class GenerarOrdenDeRetiro extends JFrame {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					GenerarOrdenDeRetiro frame = new GenerarOrdenDeRetiro(api);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JPanel contentPane;
	private DefaultTableModel modeloVivienda;
	private DefaultTableModel modeloRecolector;
	private  IApi api;
	
	public GenerarOrdenDeRetiro(IApi api) {
		setTitle("Orden de retiro");
		this.api = api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1088, 600);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		JPanel panelVivienda = new JPanel();
		panelVivienda.setBounds(10, 11, 409, 539);
		panelVivienda.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelVivienda);
		
		JScrollPane scrollPaneVivienda = new JScrollPane();
		panelVivienda.add(scrollPaneVivienda, BorderLayout.CENTER);
		JTable tableVivienda = new JTable();

		String[] titulosPedidoDeRetiro = { "DIRECCION","CARGA PESADA", "FECHA", "OBSERVACION"};
		
		String[] titulosVivienda = { "OBSERVACION", "FECHA", "DIRECCION", "DNI PROPIETARIO", "MAQUINARIA PESADA", 
				
				/*labels.getString("generar.orden.retiro.titulos.vivienda.BARRIO"), 
				labels.getString("generar.orden.retiro.titulos.vivienda.CALLE"),
				labels.getString("generar.orden.retiro.titulos.vivienda.ALTURA"),
				labels.getString("generar.orden.retiro.titulos.vivienda.LATITUD"),
				labels.getString("generar.orden.retiro.titulos.vivienda.LONGITUD")*/
		};
		modeloVivienda = new DefaultTableModel(new Object[][] {}, titulosPedidoDeRetiro);
		
		tableVivienda.setModel(modeloVivienda);
		
		List<PedidoDeRetiroDTO> pedidos;
		try {
			pedidos = api.obtenerPedidosDeRetiro();
		
			for (PedidoDeRetiroDTO v : pedidos) {
				modeloVivienda.addRow(new Object[] { v.getObservacion(),v.getFechaDelPedido(), v.getVivienda().getDireccion(),v.getVivienda().getDueño().getDni(), v.getMaquinaPesada()});
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		
		scrollPaneVivienda.setViewportView(tableVivienda);
		
		JPanel panelRecolector = new JPanel();
		panelRecolector.setBounds(637, 11, 409, 539);
		panelRecolector.setLayout(new BorderLayout(0,0));
		contentPane.add(panelRecolector);
		
		JScrollPane scrollPaneRecolector = new JScrollPane();
		panelRecolector.add(scrollPaneRecolector);
		
		
		
		
		panelRecolector.add(scrollPaneRecolector,BorderLayout.CENTER);
		JTable tableRecolector = new JTable();
		String[] titulosRecolector = {"NOMBRE","APELLIDO","TELEFONO","DNI","EMAIL"};
		modeloRecolector = new DefaultTableModel(new Object[][] {}, titulosRecolector);
		tableRecolector.setModel(modeloRecolector);
		List<RecolectorDTO> recolector= api.obtenerRecolectores();
		/*for(RecolectorDTO r : recolector) {
			modeloVivienda.addRow(new Object[] {r.getNombre(), r.getApellido(), r.getTelefono(), r.getDni(), r.getEmail()});
		}
		tableRecolector.setModel(modeloRecolector);*/
		
		scrollPaneRecolector.setViewportView(tableRecolector);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(462, 11, 128, 472);
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		
		JButton btnAceptar = new JButton("Generar Orden");
		btnAceptar.setHorizontalAlignment(SwingConstants.LEADING);
		btnAceptar.setVerticalAlignment(SwingConstants.TOP);
		btnAceptar.setToolTipText("");
		btnAceptar.setBounds(0, 179, 128, 23);
		panelBotones.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar Orden");
		btnCancelar.setHorizontalAlignment(SwingConstants.LEADING);
		btnCancelar.setBounds(0, 213, 128, 23);
		panelBotones.add(btnCancelar);
		

	}
}
