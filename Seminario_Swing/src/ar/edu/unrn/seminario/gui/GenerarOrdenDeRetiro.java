package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;

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
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

import javax.swing.JOptionPane;

import javax.swing.JButton;

import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private Integer codigoPedidoSeleccionado = null;
	private JPanel contentPane;
	private DefaultTableModel modeloPedidos;
	private DefaultTableModel modeloRecolector;
	private  IApi api;
	private String dniRecolectorSeleccionado = null;

	
	public GenerarOrdenDeRetiro(IApi api) throws DataEmptyException, StringNullException, IncorrectEmailException, AppException {
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
		JPanel panelOrdenesRetiro = new JPanel();
		panelOrdenesRetiro.setBounds(10, 11, 442, 539);
		panelOrdenesRetiro.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelOrdenesRetiro);
		
		String[] titulosPedidoDeRetiro = { "DIRECCION","CARGA PESADA", "FECHA", "OBSERVACION"};

		JScrollPane scrollPaneOrdenes = new JScrollPane();

		panelOrdenesRetiro.add(scrollPaneOrdenes, BorderLayout.CENTER);
		JTable tablePedidos = new JTable();
		tablePedidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				
					
					codigoPedidoSeleccionado = (Integer) tablePedidos.getValueAt(tablePedidos.getSelectedRow(),5 );
					System.out.println(codigoPedidoSeleccionado);
				
			}
		});
		tablePedidos.setRowSelectionAllowed(true);//permitiendo seleccion de fila
		tablePedidos.setColumnSelectionAllowed(false);//eliminando seleccion de columnas
		
		String[] titulosPedidoRetiro = { 
				"OBSERVACION", 
				"DIRECCION" ,
				"DNI PROPIETARIO",
				"FECHA PEDIDO", 
				"MAQUINARIA PESADA",
				"CODIGO PEDIDO"
				
				/*labels.getString("generar.orden.retiro.titulos.vivienda.BARRIO"), 
				labels.getString("generar.orden.retiro.titulos.vivienda.CALLE"),
				labels.getString("generar.orden.retiro.titulos.vivienda.ALTURA"),
				labels.getString("generar.orden.retiro.titulos.vivienda.LATITUD"),
				labels.getString("generar.orden.retiro.titulos.vivienda.LONGITUD")*/
		};
		modeloPedidos = new DefaultTableModel(new Object[][] {}, titulosPedidoRetiro); 
		
		
		
		List<PedidoDeRetiroDTO> pedidos;
		try {
			pedidos = api.obtenerPedidosDeRetiro();
		
			for (PedidoDeRetiroDTO p : pedidos) {
				modeloPedidos.addRow(new Object[] { p.getObservacion(),p.getVivienda().getDireccion(),p.getVivienda().getDueño().getDni(), p.getFechaDelPedido(),p.getMaquinaPesada(),p.getCodigo()});
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		
		tablePedidos.setModel(modeloPedidos);
		scrollPaneOrdenes.setViewportView(tablePedidos);
		
		
		
		
		
		JPanel panelRecolector = new JPanel();
		panelRecolector.setBounds(600, 11, 446, 539);
		panelRecolector.setLayout(new BorderLayout(0,0));
		contentPane.add(panelRecolector);
		
		JScrollPane scrollPaneRecolector = new JScrollPane();
		panelRecolector.add(scrollPaneRecolector);
		
		
		
		
		panelRecolector.add(scrollPaneRecolector,BorderLayout.CENTER);
		JTable tableRecolector = new JTable();
		tableRecolector.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dniRecolectorSeleccionado = (String) tableRecolector.getValueAt(tableRecolector.getSelectedRow(),2);
				//System.out.println(dniRecolectorSeleccionado);
			}
		});
		
		tableRecolector.setRowSelectionAllowed(true);//permitiendo seleccion de fila
		tableRecolector.setColumnSelectionAllowed(false);//eliminando seleccion de columnas
		
		String[] titulosRecolector = {"NOMBRE","APELLIDO","DNI","TELEFONO","EMAIL"};
		modeloRecolector = new DefaultTableModel(new Object[][] {}, titulosRecolector);
		
		
		List<RecolectorDTO> recolector= api.obtenerRecolectores();
		
		for(RecolectorDTO r : recolector) {
			modeloRecolector.addRow(new Object[] {r.getNombre(), r.getApellido(), r.getDni(), r.getTelefono(), r.getEmail()});
		}
		
		tableRecolector.setModel(modeloRecolector);
		
		scrollPaneRecolector.setViewportView(tableRecolector);
		
		
		
		
		
		
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(462, 11, 128, 472);
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		
		JButton btnAceptar = new JButton("Generar Orden");
		btnAceptar.addActionListener((e)->{
			try {
				System.out.println(codigoPedidoSeleccionado);
				if(this.dniRecolectorSeleccionado!= null) {
					api.generarOrdenDeRetiro(this.codigoPedidoSeleccionado,dniRecolectorSeleccionado);
				}
				else {
					api.generarOrdenDeRetiro(this.codigoPedidoSeleccionado);
				}
			} catch (AppException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
			
			
		});
		btnAceptar.setHorizontalAlignment(SwingConstants.LEADING);
		btnAceptar.setVerticalAlignment(SwingConstants.TOP);
		btnAceptar.setToolTipText("");
		btnAceptar.setBounds(0, 179, 128, 23);
		panelBotones.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar Orden");
		btnCancelar.addActionListener((e)->{
			setVisible(false);
			dispose();
		
		});
		btnCancelar.setHorizontalAlignment(SwingConstants.LEADING);
		btnCancelar.setBounds(0, 213, 128, 23);
		panelBotones.add(btnCancelar);
		

	}
}
