package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

public class GenerarBeneficio extends JFrame {
	private JPanel contentPane;
	private List<String> puntaje = new ArrayList<String>();
	private List<String> descripcion = new ArrayList<String>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					ResourceBundle labels = ResourceBundle.getBundle("labels");
					GenerarBeneficio frame = new GenerarBeneficio(api,labels);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GenerarBeneficio(IApi api, ResourceBundle labels) {
		setTitle(labels.getString("generar.beneficio.titulo"));
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 558, 257);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 522, 165);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lbDescripcion = new JLabel(labels.getString("generar.beneficio.label.descripcion"));
		lbDescripcion.setBounds(10, 23, 116, 14);
		panel.add(lbDescripcion);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(20, 48, 179, 95);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTextPane tpDescripcion = new JTextPane();
		tpDescripcion.setText("");
		panel_1.add(tpDescripcion);
		
		JLabel lbPuntos = new JLabel(labels.getString("generar.beneficio.label.puntos"));
		lbPuntos.setBounds(203, 23, 197, 14);
		panel.add(lbPuntos);
		
		JFormattedTextField ftfPuntos = new JFormattedTextField("");
		ftfPuntos.setBounds(327, 48, 86, 20);
		panel.add(ftfPuntos);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 187, 522, 31);
		contentPane.add(panel_2);
		
		JButton btnLimpiar = new JButton(labels.getString("generar.beneficio.button.limpiar")); 
		btnLimpiar.addActionListener((e)->{
			ftfPuntos.setText("");
			tpDescripcion.setText("");
		});
		panel_2.add(btnLimpiar);
		
		JButton btnAceptar = new JButton(labels.getString("generar.beneficio.button.aceptar")); 
		btnAceptar.addActionListener((e)->{
			try {
				api.agregarBeneficio(tpDescripcion.getText(),ftfPuntos.getText());
				JOptionPane.showMessageDialog(null, labels.getString("generar.beneficio.mensaje.informativo.beneficio.generado.exitosamente"),labels.getString("mensaje.informativo.general"),JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				dispose();
			} catch (NotNullException | DataEmptyException | NotNumberException | AppException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),labels.getString("mensaje.error.general"),JOptionPane.ERROR_MESSAGE);
				
			}
			
		});
		panel_2.add(btnAceptar);
		
		JButton btnCancelar = new JButton(labels.getString("generar.beneficio.button.cancelar")); 
		btnCancelar.addActionListener((e)->{
			setVisible(false);
			dispose();
			
		});
		panel_2.add(btnCancelar);
	}


}
