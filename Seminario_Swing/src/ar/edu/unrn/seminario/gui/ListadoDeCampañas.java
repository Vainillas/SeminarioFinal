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
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.CampañaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

public class ListadoDeCampañas extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo;
	IApi api;
	JButton activarButton;
	JButton desactivarButton;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersistenceApi api = new PersistenceApi();
					ListadoDeCampañas frame = new ListadoDeCampañas(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ListadoDeCampañas(IApi api) {
		this.api = api;
		setTitle("Listar Campañas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		btnNewButton = new JButton("Volver a Menu Principal");
		btnNewButton.addActionListener((e)->{
			setVisible(false);
			dispose();
		});
		
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		String[] titulos = { "NOMBRE", "ESTADO","CODIGO"};

		modelo = new DefaultTableModel(new Object[][] {}, titulos);

		// Obtiene la lista de usuarios a mostrar
		List<CampañaDTO> campaña;
		try {
			campaña = api.obtenerCampañas();
			for (CampañaDTO c : campaña) {
				modelo.addRow(new Object[] { c.getNombreCampaña(),c.getEstado(),c.getCodigo() });
				
			}
		} catch (AppException | NotNullException | DataEmptyException | NotNumberException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
		}
		table.setModel(modelo);

		scrollPane.setViewportView(table);

		JButton cerrarButton = new JButton("Cerrar");
		cerrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});



	}
}


