package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.ControladoraAplicacaoVacina;
import controller.ControladoraVacina;
import exception.exception_aplicacao_vacina.AnalisarCamposAplicacaoException;
import exception.exception_aplicacao_vacina.AnalisarSePodeAplicarException;
import model.dao.AplicacaoVacinaDAO;
import model.dao.PessoaDAO;
import model.dao.VacinaDAO;
import model.vo.AplicacaoVacinaVO;
import model.vo.PessoaVO;
import model.vo.VacinaVO;
import util.PlanilhaAplicacaoVacina;

public class TelaAplicacaoVacina extends JFrame {

	JScrollPane scrollPane = new JScrollPane();
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
	VacinaDAO vacina = new VacinaDAO();
	PessoaVO pessoaEncontrada = new PessoaVO();
	VacinaVO vacinaEncontrada = new VacinaVO();
	private Object[] opcoes = { "Sim", "NÃ£o" };
	private Object[] opcoes2 = { "Voltar a tela inicial", "Sair" };
	private int respostaCadastro;
	private Integer resposta = null;
	private JButton btnBuscar;
	private JButton btnLimpar;
	private JButton btnGerarPlanilha;

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
		setBounds(100, 100, 636, 510);

		contentPane = new JPanel();

		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(77, 54, 46, 14);
		contentPane.add(lblCpf);

		MaskFormatter mascaraCPF;
		try {
			mascaraCPF = new MaskFormatter("###.###.###-##");
			txtCpf = new JFormattedTextField(mascaraCPF);
			txtCpf.addCaretListener(new CaretListener() {
				public void caretUpdate(CaretEvent e) {
					if (txtCpf.getText().toString().equals("   .   .   -  ") != true
							&& txtCpf.getText().toString().length() == 14) {
						btnBuscar.setEnabled(true);
					}
				}
			});
		} catch (ParseException e1) {

		}
		txtCpf.setColumns(10);
		txtCpf.setBounds(165, 51, 187, 28);
		contentPane.add(txtCpf);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setEnabled(false);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cbxVacina.removeAllItems();
				limparTabelaAplicacaoVacina();
				pessoaEncontrada = pessoaDAO.consultarPorCpf(txtCpf.getText());
				if (pessoaEncontrada.getIdPessoa() == null) {
					JOptionPane.showMessageDialog(null, "CPF INVÁLIDO.");

				} else {
					// buscar qual vacina esse cidadÃ£oo tomou

					txtNome.setEnabled(true);
					txtNome.setText(pessoaEncontrada.getNome());
					AplicacaoVacinaVO encontrada = carregarTabela(pessoaEncontrada.getIdPessoa());

					preencherCbxVacina();
					// setar a vacina que o cidadão tomou lá no combobox
					if (encontrada != null && encontrada.getVacina().getIdVacina() != null) {
						cbxVacina.setSelectedIndex(encontrada.getVacina().getIdVacina());
					}

					btnVacinar.setEnabled(true);
					btnLimpar.setEnabled(true);
					btnGerarPlanilha.setEnabled(true);
				}

			}
		});
		btnBuscar.setBounds(419, 50, 99, 29);
		contentPane.add(btnBuscar);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(77, 115, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setEnabled(false);
		txtNome.setEditable(false);
		txtNome.setBackground(Color.WHITE);
		txtNome.setBounds(165, 111, 352, 29);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblVacina = new JLabel("Vacina:");
		lblVacina.setBounds(77, 165, 46, 14);
		contentPane.add(lblVacina);

		cbxVacina.setForeground(Color.BLACK);
		cbxVacina.setBounds(165, 158, 352, 29);
		contentPane.add(cbxVacina);

		btnVacinar = new JButton("Vacinar");
		btnVacinar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					cadastrarAplicacao();
				} catch (AnalisarSePodeAplicarException | AnalisarCamposAplicacaoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					// dispose();
				}
			}
		});
		btnVacinar.setBounds(419, 376, 99, 51);
		btnVacinar.setEnabled(false);
		contentPane.add(btnVacinar);

		JLabel lblHistrico = new JLabel("Histórico:");
		lblHistrico.setBounds(77, 260, 76, 14);
		contentPane.add(lblHistrico);

		scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(77, 294, 441, 57);
		contentPane.add(scrollPane);

		table = new JTable(modelo);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (verificarSetabelaTemItemSelecionado()) {
					btnLimpar.setEnabled(true);
					btnGerarPlanilha.setEnabled(true);
				} else {
					btnLimpar.setEnabled(false);
					btnGerarPlanilha.setEnabled(false);
				}
			}
		});

		scrollPane.setViewportView(table);
		modelo.addColumn("Dose");
		modelo.addColumn("Data da aplicação");
		modelo.addColumn("Status da vacina");

		btnLimpar = new JButton("Limpar");
		btnLimpar.setEnabled(false);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparTabelaAplicacaoVacina();
				txtCpf.setText("");
				txtNome.setText("");
				cbxVacina.removeAllItems();

				btnLimpar.setEnabled(false);
				btnGerarPlanilha.setEnabled(false);
				btnVacinar.setEnabled(false);
			}
		});
		btnLimpar.setBounds(419, 242, 99, 40);
		contentPane.add(btnLimpar);

		btnGerarPlanilha = new JButton("Gerar relatório");
		btnGerarPlanilha.setEnabled(false);
		btnGerarPlanilha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Salvar relatório como...");
				ArrayList<AplicacaoVacinaVO> list = avController.consultarRelatorioAplicacao();

				ArrayList<AplicacaoVacinaVO> lista = new ArrayList<>();
				for (AplicacaoVacinaVO aplicacao : list) {
					if (pessoaEncontrada.getIdPessoa() == aplicacao.getPessoa().getIdPessoa()) {
						lista.add(aplicacao);
					}
				}

				int resultado = jfc.showSaveDialog(null);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					String caminhoEscolhido = jfc.getSelectedFile().getAbsolutePath();
					PlanilhaAplicacaoVacina planilha = new PlanilhaAplicacaoVacina();
					planilha.gerarPlanilhaAplicacaoVacina(caminhoEscolhido, lista);
					JOptionPane.showMessageDialog(null, "Salvo com sucesso.");

				}
			}
		});
		btnGerarPlanilha.setBounds(264, 376, 143, 51);
		contentPane.add(btnGerarPlanilha);

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
		cbxVacina.addItem("--- Selecione uma vacina ---");
		for (VacinaVO vacinaVO : buscarTodos) {
			cbxVacina.addItem(vacinaVO.getNomeVacina());
		}

	}

	private void cadastrarAplicacao() throws AnalisarSePodeAplicarException, AnalisarCamposAplicacaoException {
		AplicacaoVacinaVO aplicacaoVacina = new AplicacaoVacinaVO();
		aplicacaoVacina.setPessoa(pessoaEncontrada);
		aplicacaoVacina.setVacina(controllerVacina.consultarPorNome(cbxVacina.getSelectedItem().toString()));

		ArrayList<AplicacaoVacinaVO> listaAplicacoes = avController.consultarAplicacoes(pessoaEncontrada.getIdPessoa());
		// AplicacaoVacinaVO ultimaAPlicacao =
		// listaAplicacoes.get(listaAplicacoes.size()-1);

		// LocalDate dtUltimaAplicacao = ultimaAPlicacao.getDataAplicacao();
		LocalDate dtNovaAplicacao = LocalDate.now();

		aplicacaoVacina.setDataAplicacao(dtNovaAplicacao);
		avController.cadastrar(aplicacaoVacina);
		if (avController.validarCampos(aplicacaoVacina) != null) {
			int respostaOpcao2;
			respostaOpcao2 = JOptionPane.showOptionDialog(null, "Selecione uma opÃÂ§ÃÂ£o", "OpÃÂ§ÃÂµes",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes2, opcoes2[0]);
			if (respostaOpcao2 == 0) {
				TelaPrincipal telaPrincipal = new TelaPrincipal();
				telaPrincipal.setVisible(true);
			} else if (respostaOpcao2 == 1) {
				dispose();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
		}
	}

	public void carregarTabela1(Integer id) {
		AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();
		ArrayList<AplicacaoVacinaVO> listaAplicacoes = avController.consultarAplicacoes(id);
		int i = 1;
		if (listaAplicacoes.size() != 0) {
			for (AplicacaoVacinaVO apvac : listaAplicacoes) {
				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String dataAplicacao = formatador.format(apvac.getDataAplicacao());
				modelo.addRow(new Object[] { i++, dataAplicacao });
			}
		} else {
			modelo.addRow(new Object[] { "---", "------" });
		}
	}
	
	public AplicacaoVacinaVO carregarTabela(Integer id) {
		AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();
		VacinaVO vacV0 = new VacinaVO();
		ArrayList<AplicacaoVacinaVO> listaAplicacoes = avController.consultarAplicacoes(id);
		ArrayList<VacinaVO> listaStatus = controllerVacina.consultarTodos();
		AplicacaoVacinaVO retorno = null;
		
		if(listaAplicacoes.size() >= 1) {
			retorno = listaAplicacoes.get(0);
			
			for (AplicacaoVacinaVO apvac : listaAplicacoes) {
				int i = 1;

				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String dataAplicacao = formatador.format(apvac.getDataAplicacao());
				String status = vacV0.getStatusVacina();
				
				

				modelo.addRow(new Object[] { i++, dataAplicacao, status});
			}
			
		}
	
	

		return retorno;
	}

	

	public boolean verificarCamposPreenchidos() {
		boolean resposta = false;
		if (pessoaEncontrada != null && txtNome.getText().toString().length() > 0
				&& cbxVacina.getSelectedItem().toString().length() > 0) {
			resposta = true;
		}
		return resposta;
	}

	private boolean verificarSetabelaTemItemSelecionado() {
		boolean resposta = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0) > 0;

		return resposta;
	}

	private void limparTabelaAplicacaoVacina() {
		modelo.setRowCount(0);
	}
}
