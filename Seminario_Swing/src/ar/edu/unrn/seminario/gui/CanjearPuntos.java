package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CampañaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.InsuficientPointsException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.utilities.NotEditJTable;

import java.awt.Color;
import java.awt.Font;

public class CanjearPuntos extends JFrame {

	private JPanel contentPane;
	private JTable tableCampañas;
	private DefaultTableModel modeloCampaña;
	private DefaultTableModel modeloBeneficio;
	private JButton btnVolver;
	private JPanel panelBeneficios;
	private JButton cerrarButton;
	private JPanel panelCampañas;
	private JScrollPane scrollPaneCampañas;
	private JScrollPane scrollPaneBeneficios;
	private JTable tableBeneficios;
	private JTextField tfPuntosDelDueño;
	private int codCampaña;
	private int codBeneficio;

	public CanjearPuntos(IApi api,ResourceBundle labels) {
		
		setTitle(labels.getString("canjear.puntos.titulo"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1045, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		btnVolver = new JButton(labels.getString("canjear.puntos.button.volver"));
		btnVolver.setBounds(5, 342, 970, 23);
		btnVolver.addActionListener((e)->{
			setVisible(false);
			dispose();
		});
		contentPane.setLayout(null);
		
		contentPane.add(btnVolver);
		panelBeneficios = new JPanel();
		panelBeneficios.setBounds(356, 60, 435, 277);
		contentPane.add(panelBeneficios);
		panelBeneficios.setLayout(new BorderLayout(0, 0));
		
		scrollPaneBeneficios = new JScrollPane();
		panelBeneficios.add(scrollPaneBeneficios, BorderLayout.CENTER);
		
		tableBeneficios = new NotEditJTable();
		tableBeneficios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					codBeneficio = (Integer)tableBeneficios.getValueAt(tableBeneficios.getSelectedRow(),2);
					
				
			}
		});
		scrollPaneBeneficios.setViewportView(tableBeneficios);
		scrollPaneCampañas = new JScrollPane();
		panelCampañas = new JPanel();
		panelCampañas.setBounds(5, 60, 342, 280);
		contentPane.add(panelCampañas);
		panelCampañas.setLayout(null);
		
		scrollPaneCampañas.setBounds(0, 0, 342, 280);
		panelCampañas.add(scrollPaneCampañas);
		tableCampañas = new NotEditJTable();

		

		tableCampañas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String nombreCam = null;
				
				CampañaDTO campa = null;
				try {
					
					nombreCam = (String)tableCampañas.getValueAt(tableCampañas.getSelectedRow(),0 );
					codCampaña = (Integer)tableCampañas.getValueAt(tableCampañas.getSelectedRow(),1);
					campa = api.obtenerCampañaPorCodigo(codCampaña);
					
					modeloBeneficio.setRowCount(0);
				for(Beneficio bene : campa.getCatalogo().getListaBeneficios()) {
					modeloBeneficio.addRow(new Object[] {	
						bene.getDescripcion(),
						bene.getPuntajeConsumible(),
						bene.getCodigo()
					});

				}
				} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				}
				catch(java.lang.ClassCastException e1) {
					
				}
				
				
				
			}
		});
		String [] titulosBeneficios = {
				labels.getString("canjear.puntos.titulos.beneficio.descripcion"),
				labels.getString("canjear.puntos.titulos.beneficio.puntaje.consumible"),
				labels.getString("canjear.puntos.titulos.beneficio.codigo.puntaje")};

		String[] titulos = { 
				labels.getString("canjear.puntos.titulos.campaña.nombre"),
				labels.getString("canjear.puntos.titulos.campaña.codigo")};
		modeloCampaña = new DefaultTableModel(new Object[][] {}, titulos);
		modeloBeneficio = new DefaultTableModel(new Object[][] {},titulosBeneficios);
		
		
		// Obtiene la lista de usuarios a mostrar
		List<CampañaDTO> campaña;
		
		try {
			
			campaña = api.obtenerCampañas();
			
			for (CampañaDTO c : campaña) {
				modeloCampaña.addRow(new Object[] { 
						c.getNombreCampaña(),
						c.getCodigo()
						});
				

			}
		} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
		}
		

		tableCampañas.setModel(modeloCampaña);
		scrollPaneCampañas.setViewportView(tableCampañas);
		tableBeneficios.setModel(modeloBeneficio);
		scrollPaneBeneficios.setViewportView(tableBeneficios);
		
		JLabel lbCampañas = new JLabel(labels.getString("canjear.puntos.label.indique.campaña"));
		lbCampañas.setHorizontalAlignment(SwingConstants.CENTER);
		lbCampañas.setBounds(29, 35, 302, 14);
		contentPane.add(lbCampañas);
		
		JLabel lbBeneficio = new JLabel(labels.getString("canjear.puntos.label.indique.beneficio.que.quiere.cambiar"));
		lbBeneficio.setHorizontalAlignment(SwingConstants.CENTER);
		lbBeneficio.setBounds(453, 35, 243, 14);
		contentPane.add(lbBeneficio);
		
		JLabel lbMisPuntos = new JLabel(labels.getString("canjear.puntos.label.mis.puntos"));
		lbMisPuntos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbMisPuntos.setBounds(813, 26, 110, 23);
		contentPane.add(lbMisPuntos);
		
		tfPuntosDelDueño = new JTextField();
		tfPuntosDelDueño.setEditable(false);
		tfPuntosDelDueño.setBackground(Color.WHITE);
		tfPuntosDelDueño.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfPuntosDelDueño.setForeground(Color.BLACK);
		try {
			tfPuntosDelDueño.setText(String.valueOf(api.obtenerDueñoActivo().getPuntaje()));
			
		} catch (AppException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		tfPuntosDelDueño.setBounds(849, 60, 94, 26);
		contentPane.add(tfPuntosDelDueño);
		tfPuntosDelDueño.setColumns(10);
		
		JButton btnCanjearPuntos = new JButton(labels.getString("canjear.puntos.label.canjear.puntos"));
		btnCanjearPuntos.addActionListener((e)->{
			if(this.tableCampañas.isRowSelected(this.tableCampañas.getSelectedRow())) {
			if(this.tableBeneficios.isRowSelected(this.tableBeneficios.getSelectedRow()) &&this.tableBeneficios.getSelectedRowCount() ==1) {
				
				try {
					api.generarCanje(codBeneficio, codCampaña);
					//JOptionPane.showMessageDialog(null,labels.getString("canjear.puntos.label.canjear.puntos.exitoso"),labels.getString("canjear.puntos.label.mensaje.informativo"),JOptionPane.INFORMATION_MESSAGE);
					int res = JOptionPane.showConfirmDialog(null,labels.getString("canjear.puntos.mensaje.informativo1"),labels.getString("canjear.puntos.mensaje.informativo2"),JOptionPane.YES_NO_OPTION );
					if(res == 0 ) {
						tfPuntosDelDueño.setText((String.valueOf(api.obtenerDueñoActivo().getPuntaje())));
						
						
					}
					else {
						setVisible(false);
						dispose();
						
					}
					

					
				} catch (AppException | NotNullException | InsuficientPointsException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);	
				}
			}
			else {
				JOptionPane.showMessageDialog(null,labels.getString("canjear.puntos.mensaje.de.error.beneficio"),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
			}
			}
			else {
				JOptionPane.showMessageDialog(null,labels.getString("canjear.puntos.mensaje.de.error.campaña"),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
			}
		});
		btnCanjearPuntos.setBounds(809, 97, 151, 23);
		contentPane.add(btnCanjearPuntos);
		
		
		
		
		


		cerrarButton = new JButton(labels.getString("canjear.puntos.button.cerrar"));
		cerrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});



	}
	
	private void reloadGridBeneficio(List<BeneficioDTO> beneficio) {
		this.modeloBeneficio.setRowCount(0);
		for (BeneficioDTO b : beneficio) {
			this.modeloBeneficio.addRow(new Object[] {
				b.getDescripcion(),
				b.getPuntajeConsumible()
			});
		}
		
	}
	
	private void reloadGrid(List<CampañaDTO> campañaDTO) {
		
		this.modeloCampaña.setRowCount(0);
		this.modeloBeneficio.setRowCount(0);
		for (CampañaDTO c : campañaDTO) {
			this.modeloCampaña.addRow(new Object[] {
				c.getNombreCampaña(),
			});
		}

		
	}
}


