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
import javax.swing.JList;
import java.awt.GridBagLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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
		String[] titulosPedidoDeRetiro = { "DIRECCION", "FECHA", "OBSERVACION"
				
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
				modeloVivienda.addRow(new Object[] { v.getObservacion(), v.getMaquinaPesada(), v.getListResiduos(),v.getFechaDelPedido(), v.getVivienda()});
				//modeloVivienda.addRow(new Object[] { v.getVivienda()});
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
		panelBotones.setBounds(462, 11, 130, 472);
		contentPane.add(panelBotones);
		
		JButton btnCancelar = new JButton("New button");
		
		JButton btnAceptar = new JButton("New button");
		GroupLayout gl_panelBotones = new GroupLayout(panelBotones);
		gl_panelBotones.setHorizontalGroup(
			gl_panelBotones.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBotones.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_panelBotones.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAceptar)
						.addComponent(btnCancelar))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_panelBotones.setVerticalGroup(
			gl_panelBotones.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelBotones.createSequentialGroup()
					.addContainerGap(187, Short.MAX_VALUE)
					.addComponent(btnAceptar)
					.addGap(18)
					.addComponent(btnCancelar)
					.addGap(221))
		);
		panelBotones.setLayout(gl_panelBotones);
		

	}
}
