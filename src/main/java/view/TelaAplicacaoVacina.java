package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.ControladoraAplicacaoVacina;
import controller.ControladoraVacina;
import exception.exceptionVacina.AnalisarCamposVacinaException;
import exception.exception_aplicacao_vacina.AnalisarCamposAplicacaoException;
import exception.exception_aplicacao_vacina.AnalisarSePodeAplicarException;
import model.dao.AplicacaoVacinaDAO;
import model.dao.PessoaDAO;
import model.dao.VacinaDAO;
import model.vo.AplicacaoVacinaVO;
import model.vo.PessoaVO;
import model.vo.VacinaVO;

public class TelaAplicacaoVacina extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome; 
	private JTable table;
	private JTextField txtCpf;
	ControladoraAplicacaoVacina avController = new ControladoraAplicacaoVacina();
	ControladoraVacina controllerVacina = new ControladoraVacina();
	PessoaDAO pessoaDAO = new PessoaDAO();
	JComboBox<String> cbxVacina = new JComboBox();
	DefaultTableModel modelo = new DefaultTableModel();
	private String nome = "";
	JButton btnVacinar = new JButton("Vacinar");
	PessoaVO encontrada = new PessoaVO();

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println(txtCpf.getText());
				// System.out.println(controller.consultarPorCpf(txtCpf.getText()).toString());
				PessoaVO encontrada = pessoaDAO.consultarPorCpf(txtCpf.getText());
				txtNome.setEnabled(true);
				txtNome.setEditable(false);
				txtNome.setText(encontrada.getNome());
				ArrayList<AplicacaoVacinaVO> historicoAplicacoes = avController.consultarAplicacoes(encontrada);
				int i = 1;
				for (AplicacaoVacinaVO av : historicoAplicacoes) {
						modelo.addRow(new Object[] {i,av.getDataAplicacao()});
						i++;
				}
				
				preencherCbxVacina();
				btnVacinar.setEnabled(true);
			}
		});

		btnBuscar.setBounds(378, 30, 99, 29);
		contentPane.add(btnBuscar);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(36, 95, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setEnabled(false);
		txtNome.setBackground(Color.WHITE);
		txtNome.setBounds(124, 91, 352, 22);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblVacina = new JLabel("Vacina:");
		lblVacina.setBounds(36, 145, 46, 14);
		contentPane.add(lblVacina);

		cbxVacina.setForeground(Color.BLACK);
		cbxVacina.setBounds(124, 138, 352, 29);
		contentPane.add(cbxVacina);

		JButton btnVacinar = new JButton("Vacinar");
		btnVacinar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					cadastrarAplicacao();
				} catch (AnalisarSePodeAplicarException | AnalisarCamposAplicacaoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
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

		table = new JTable(modelo);
		scrollPane.setViewportView(table);

		modelo.addColumn("Dose");
		modelo.addColumn("Data aplica��o");
	}

	public void consultarPorCpf() {
		PessoaVO p = pessoaDAO.consultarPorCpf(txtCpf.getSelectedText());
		if (p != null) {
			preencherNome(p);
		}
	}

	public void preencherNome(PessoaVO p) {
		this.txtNome.setText(p.getNome());
	}

	public void preencherCbxVacina() {
		VacinaDAO vacina = new VacinaDAO();
		ArrayList<VacinaVO> buscarTodos = vacina.consultarTodos();

		for (VacinaVO vacinaVO: buscarTodos) {
			cbxVacina.addItem(vacinaVO.getNomeVacina());
		}

	}
	
	private void cadastrarAplicacao() throws AnalisarSePodeAplicarException, AnalisarCamposAplicacaoException {
		AplicacaoVacinaVO aplicacaoVacina = new AplicacaoVacinaVO();
		aplicacaoVacina.setPessoa(encontrada); 
		
		aplicacaoVacina.setVacina(controllerVacina.consultarPorNome(cbxVacina.getSelectedItem().toString()));
		aplicacaoVacina.setDataAplicacao(LocalDate.now());
		System.out.println(avController.cadastrar(aplicacaoVacina));


	}

	public void carregarTabela() throws SQLException {

		AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();

		// List<AplicacaoVacinaVO> list = avDAO.buscarAplicacoes();

		// for (AplicacaoVacinaVO apvac : list) {
		// modelo.addRow(new Object[]{apvac.getDataAplicacao()});
	}
}
