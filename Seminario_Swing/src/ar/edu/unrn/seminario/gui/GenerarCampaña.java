package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class GenerarCampaña extends JFrame {

	private JPanel contentPane;
	private JTable tableBeneficiosNoAsociados;
	private JTable tableBeneficiosAsociados;
	private DefaultTableModel modeloBeneficiosNoAsociados;
	private DefaultTableModel modeloBeneficiosAsociados;
	private List<BeneficioDTO> beneficio;
	private JTextField tfnombreCampaña;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					ResourceBundle labels = ResourceBundle.getBundle("labels");
					GenerarCampaña frame = new GenerarCampaña(api,labels);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public GenerarCampaña(IApi api, ResourceBundle labels) {
		setTitle(labels.getString("generar.campaña.titulo"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panelBeneficiosNoAsociados = new JPanel();
		panelBeneficiosNoAsociados.setBounds(10, 60, 402, 236);
		contentPane.add(panelBeneficiosNoAsociados);
		String [] titulos = {
				labels.getString("generar.campaña.titulos.descripcion"),
				labels.getString("generar.campaña.titulos.puntaje"),
				labels.getString("generar.campaña.titulos.codigo")
				
		};
		
		modeloBeneficiosAsociados = new DefaultTableModel(new Object[][] {}, titulos);
		modeloBeneficiosNoAsociados = new DefaultTableModel(new Object[][] {}, titulos);
		try {
			beneficio = api.obtenerBeneficios();
			for(BeneficioDTO b : beneficio) {
				
				modeloBeneficiosNoAsociados.addRow(new Object[] {b.getDescripcion(),b.getPuntajeConsumible(),b.getCodigo()});
			
			}
		} catch (AppException | NotNullException | DataEmptyException | NotNumberException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		JPanel panelBeneficiosAsociados = new JPanel();
		panelBeneficiosAsociados.setBounds(572, 60, 402, 236);
		contentPane.add(panelBeneficiosAsociados);
		
		panelBeneficiosAsociados.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneBeneficiosAsociados = new JScrollPane();
		panelBeneficiosAsociados.add(scrollPaneBeneficiosAsociados, BorderLayout.CENTER);
		
		tableBeneficiosAsociados = new JTable();
		
		tableBeneficiosAsociados.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				String  descripcion = (String)tableBeneficiosAsociados.getValueAt(tableBeneficiosAsociados.getSelectedRow(),0 );
				Integer puntaje = (Integer)tableBeneficiosAsociados.getValueAt(tableBeneficiosAsociados.getSelectedRow(),1 );
				Integer codigo = (Integer)tableBeneficiosAsociados.getValueAt(tableBeneficiosAsociados.getSelectedRow(),2 );
				
				//tableBeneficiosNoAsociados.clearSelection();
				
				modeloBeneficiosAsociados.removeRow(tableBeneficiosAsociados.getSelectedRow());
				
				modeloBeneficiosNoAsociados.addRow(new Object[] {descripcion,puntaje,codigo});
				tableBeneficiosNoAsociados.setModel(modeloBeneficiosNoAsociados);
				tableBeneficiosAsociados.clearSelection();
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
			public void mousePressed(MouseEvent e) {
				
				String  descripcion = (String)tableBeneficiosNoAsociados.getValueAt(tableBeneficiosNoAsociados.getSelectedRow(),0 );
				Integer puntaje = (Integer)tableBeneficiosNoAsociados.getValueAt(tableBeneficiosNoAsociados.getSelectedRow(),1 );
				Integer codigo = (Integer)tableBeneficiosNoAsociados.getValueAt(tableBeneficiosNoAsociados.getSelectedRow(),2 );		
				modeloBeneficiosNoAsociados.removeRow(tableBeneficiosNoAsociados.getSelectedRow());
				modeloBeneficiosAsociados.addRow(new Object[] {descripcion,puntaje,codigo});
				tableBeneficiosAsociados.setModel(modeloBeneficiosAsociados);
				tableBeneficiosNoAsociados.clearSelection();
				
			}
		});

		tableBeneficiosNoAsociados.setModel(modeloBeneficiosNoAsociados);
		JLabel lblNewLabel = new JLabel(labels.getString("generar.campaña.label.seleccionar.beneficios.campaña"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(355, 30, 279, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lbNombreCampaña = new JLabel(labels.getString("generar.campaña.label.nombre.campaña"));
		lbNombreCampaña.setBounds(10, 11, 140, 14);
		contentPane.add(lbNombreCampaña);
		
		tfnombreCampaña = new JTextField();
		tfnombreCampaña.setBounds(160, 8, 155, 20);
		contentPane.add(tfnombreCampaña);
		tfnombreCampaña.setColumns(10);
		
		JButton btnGenerarCampaña = new JButton(labels.getString("generar.campaña.button.generar.campaña"));
		btnGenerarCampaña.addActionListener((e)->{
			List<Integer>codigo = null;

			if(this.tableBeneficiosAsociados.getRowCount()!=0) {
				int res = JOptionPane.showConfirmDialog(null,labels.getString("generar.campaña.pregunta"),labels.getString("generar.campaña.confirmacion.envio"), JOptionPane.YES_NO_OPTION);
				if(res == 0) {
					codigo = new ArrayList<Integer>();
					
					for(int i =0 ;i<this.tableBeneficiosAsociados.getRowCount();i++ ) {
						codigo.add((Integer)tableBeneficiosAsociados.getValueAt(i,2 ));

					}
					try {
						
						api.generarCampaña(codigo,tfnombreCampaña.getText());
						JOptionPane.showMessageDialog(null,labels.getString("generar.campaña.confirmacion.campaña.generada"),labels.getString("generar.campaña.mensaje.informativo"),JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						dispose();
					} catch (AppException | NotNullException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);


						
					}}
			
			
			else {
				JOptionPane.showMessageDialog(null,labels.getString("generar.campaña.mensaje.error.seleccionar.beneficio"),"Error",JOptionPane.ERROR_MESSAGE);
			}
				}
		});
			
		

		scrollPaneBeneficioNoAsociado.setViewportView(tableBeneficiosNoAsociados);
		
		JLabel lbBeneficiosSinAsociar = new JLabel(labels.getString("generar.campaña.label.beneficios.sin.asociar"));
		lbBeneficiosSinAsociar.setHorizontalAlignment(SwingConstants.CENTER);
		lbBeneficiosSinAsociar.setBounds(130, 40, 168, 14);
		contentPane.add(lbBeneficiosSinAsociar);
		
		JLabel lbBeneficiosAsociados = new JLabel(labels.getString("generar.campaña.label.beneficios.asociados"));
		lbBeneficiosAsociados.setHorizontalAlignment(SwingConstants.CENTER);
		lbBeneficiosAsociados.setBounds(687, 40, 199, 14);
		contentPane.add(lbBeneficiosAsociados);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(417, 120, 150, 133);
		contentPane.add(panelBotones);
	
		
		panelBotones.add(btnGenerarCampaña);
		
		JButton btnCancelar = new JButton(labels.getString("generar.campaña.button.cancelar"));
		btnCancelar.addActionListener((e)->{
			setVisible(false);
			dispose();
		});
		panelBotones.add(btnCancelar);

		JButton btnRemoverBeneficios = new JButton(labels.getString("generar.campaña.button.remover.beneficios"));
		btnRemoverBeneficios.addActionListener((e)->{
			if(modeloBeneficiosNoAsociados.getRowCount()!= beneficio.size()) {
	
				this.modeloBeneficiosAsociados.setRowCount(0);
				this.modeloBeneficiosNoAsociados.setRowCount(0);
				
				List<BeneficioDTO> beneficios;
				
				try {
					beneficios = api.obtenerBeneficios();

					for(BeneficioDTO b : beneficios) {
						this.modeloBeneficiosNoAsociados.addRow(new Object[] {b.getDescripcion(),b.getPuntajeConsumible(),b.getCodigo()});
					}
					
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
		});
		panelBotones.add(btnRemoverBeneficios);
		
		

		
		
		
		
		
		


		

	}
	}

