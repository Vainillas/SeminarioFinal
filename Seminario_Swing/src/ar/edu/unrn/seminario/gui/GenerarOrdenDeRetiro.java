package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
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

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.SystemColor;

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
	private IApi api;
	private String dniRecolectorSeleccionado = null;

	
	public GenerarOrdenDeRetiro(IApi api){
		setTitle("Orden de retiro");
		this.api = api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1088, 625);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		ResourceBundle labels = ResourceBundle.getBundle("labels",new Locale("es"));
		//ResourceBundle labels = ResourceBundle.getBundle("labels");
		JPanel panelOrdenesRetiro = new JPanel();
		panelOrdenesRetiro.setBounds(10, 46, 500, 495);
		panelOrdenesRetiro.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelOrdenesRetiro);
		
		String[] titulosPedidoDeRetiro = { "DIRECCION","CARGA PESADA", "FECHA", "OBSERVACION"};

		JScrollPane scrollPaneOrdenes = new JScrollPane();

		panelOrdenesRetiro.add(scrollPaneOrdenes, BorderLayout.CENTER);
		JTable tablePedidos = new JTable();
		tablePedidos.addMouseListener(new MouseAdapter() {
			
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
		panelRecolector.setBounds(520, 46, 526, 495);
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
				
			}
		});
		
		tableRecolector.setRowSelectionAllowed(true);//permitiendo seleccion de fila
		tableRecolector.setColumnSelectionAllowed(false);//eliminando seleccion de columnas
		
		String[] titulosRecolector = {"NOMBRE","APELLIDO","DNI","TELEFONO","EMAIL"};
		modeloRecolector = new DefaultTableModel(new Object[][] {}, titulosRecolector);
		

		tableRecolector.setModel(modeloRecolector);

		List<RecolectorDTO> recolector = null;
		try {
			recolector = api.obtenerRecolectores();
		} catch (DataEmptyException | StringNullException | IncorrectEmailException | AppException e2) {
			// TODO Bloque catch generado automáticamente
			JOptionPane.showMessageDialog(null, e2.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		
		for(RecolectorDTO r : recolector) {
			modeloRecolector.addRow(new Object[] {r.getNombre(), r.getApellido(), r.getDni(), r.getTelefono(), r.getEmail()});
		}
		
		tableRecolector.setModel(modeloRecolector);
		
		scrollPaneRecolector.setViewportView(tableRecolector);
		
		
		
		
		
		
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(new Color(255, 248, 220));
		panelBotones.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, null));
		panelBotones.setBounds(295, 542, 442, 44);
		contentPane.add(panelBotones);
		
		JButton btnAceptar = new JButton("Generar Orden");
		btnAceptar.addActionListener((e)->{
			try {
				System.out.println(codigoPedidoSeleccionado);
				if(this.dniRecolectorSeleccionado!= null) {
					api.generarOrdenDeRetiro(codigoPedidoSeleccionado, dniRecolectorSeleccionado);
				}
				else {
				api.generarOrdenDeRetiro(this.codigoPedidoSeleccionado);
				}
				
				
			} catch (AppException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
			}
			
		});
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnAceptar.setHorizontalAlignment(SwingConstants.LEADING);
		btnAceptar.setVerticalAlignment(SwingConstants.TOP);
		btnAceptar.setToolTipText("");
		panelBotones.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar Orden");
		btnCancelar.addActionListener((e)->{
			setVisible(false);
			dispose();
		
		});
		btnCancelar.setHorizontalAlignment(SwingConstants.LEADING);
		panelBotones.add(btnCancelar);
		
		JLabel lbRecolector = new JLabel("Seleccionar recolector");
		lbRecolector.setHorizontalAlignment(SwingConstants.CENTER);
		lbRecolector.setVerticalAlignment(SwingConstants.BOTTOM);
		lbRecolector.setBounds(694, 21, 275, 14);
		contentPane.add(lbRecolector);
		
		JLabel lblNewLabel = new JLabel("Seleccionar Pedido De Retiro");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(97, 21, 268, 14);
		contentPane.add(lblNewLabel);
		

	}
}
