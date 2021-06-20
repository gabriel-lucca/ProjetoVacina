package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.ControladoraAplicacaoVacina;
import controller.ControladoraPessoa;
import model.dao.PessoaDAO;
import model.vo.PessoaVO;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaAplicacaoVacina extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTable table;
	private JTextField txtCpf;
	PessoaDAO pessoaDAO = new PessoaDAO();
	private String nome = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaAplicacaoVacina frame = new TelaAplicacaoVacina();
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
	public TelaAplicacaoVacina() {
		setTitle("Aplica\u00E7\u00E3o Vacina");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 470);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCpf = new JLabel("Cpf:");
		lblCpf.setBounds(36, 34, 46, 14);
		contentPane.add(lblCpf);
		
		
		MaskFormatter mascaraCPF;
		try {
			mascaraCPF = new MaskFormatter("###.###.###-##");
			txtCpf = new JFormattedTextField(mascaraCPF);
		} catch (ParseException e1) {
		}
		txtCpf.setColumns(10);
		txtCpf.setBounds(124, 31, 187, 29);
		contentPane.add(txtCpf);
		
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultarPorCpf();
			}

			protected void consultarPorCpf() {
				ControladoraPessoa controller = new ControladoraPessoa();
				PessoaVO p = controller.consultarPorCpf(String.valueOf(txtCpf.getText()));
				
				if(p != null) {
					preencherEnderecoNaTela(p);
				}
			}

			protected void preencherEnderecoNaTela(PessoaVO p) {
				txtCpf.setText(String.valueOf(p.getCpf()));
				txtNome.setText(p.getNome());
				txtNome.setEnabled(false);
				
			}
		});
		btnBuscar.setBounds(378, 30, 99, 29);
		contentPane.add(btnBuscar);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(36, 95, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBackground(Color.WHITE);
		txtNome.setEnabled(false);
		txtNome.setEditable(false);
		txtNome.setBounds(124, 91, 352, 22);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		
		JLabel lblVacina = new JLabel("Vacina:");
		lblVacina.setBounds(36, 145, 46, 14);
		contentPane.add(lblVacina);
		
		JComboBox cbxVacina = new JComboBox();
		cbxVacina.setForeground(Color.WHITE);
		cbxVacina.setBounds(124, 138, 352, 29);
		contentPane.add(cbxVacina);
		
		JButton btnVacinar = new JButton("Vacinar");
		btnVacinar.setEnabled(false);
		btnVacinar.setBounds(378, 352, 99, 51);
		contentPane.add(btnVacinar);
		
		JLabel lblHistrico = new JLabel("Hist\u00F3rico:");
		lblHistrico.setBounds(36, 240, 76, 14);
		contentPane.add(lblHistrico);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(36, 273, 441, 57);
		contentPane.add(scrollPane);
		
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
			},
			new String[] {
				"Data de aplica\u00E7\u00E3o", "Dose"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(117);
	}
	
	
	
}
