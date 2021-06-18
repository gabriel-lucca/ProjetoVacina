package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;
import javax.swing.JScrollPane;

public class TelaRanking extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaRanking frame = new TelaRanking();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaRanking() {
		setForeground(Color.GRAY);
		setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setTitle("Ranking vacinas");
		setBounds(100, 100, 658, 483);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(244, 85, 153, 26);
		contentPane.add(btnBuscar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 155, 622, 107);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Ranking", "Vacina", "N\u00BA aplica\u00E7\u00F5es", "Unidades totais", "Porcentagem efic\u00E1cia"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(131);
		table.getColumnModel().getColumn(1).setPreferredWidth(131);
		table.getColumnModel().getColumn(2).setPreferredWidth(131);
		table.getColumnModel().getColumn(3).setPreferredWidth(131);
		table.getColumnModel().getColumn(4).setPreferredWidth(142);
		table.setBackground(Color.WHITE);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(81, 297, 217, 42);
		contentPane.add(btnAdicionar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(341, 297, 217, 42);
		contentPane.add(btnEditar);
	}
}
