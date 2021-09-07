package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ControladoraVacina;
import exception.exceptionVacina.AnalisarCamposVacinaException;
import model.dao.VacinaDAO;
import model.vo.VacinaVO;
import seletor.FiltroVacina;
import util.PlanilhaVacina;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseMotionAdapter;
import java.awt.Font;
import javax.swing.JTextPane;

public class TelaConsultarVacina extends JFrame {

	JScrollPane scrollPane = new JScrollPane();
	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo = new DefaultTableModel();
	private ControladoraVacina vController = new ControladoraVacina();
	private int respostaExclusao;
	Object[] opcoes = { "Sim", "Não" };
	private JTextField txtNomeVacina;
	private JComboBox cbxPais;
	private JComboBox cbxDoses;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JButton btnLimparFiltros;
	private JButton btnGerarPlanilha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsultarVacina frame = new TelaConsultarVacina();
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
	public TelaConsultarVacina() {
		setTitle("Consultar Vacinas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 980, 467);
		contentPane = new JPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (verificarFiltroPreenchido()) {
					btnLimparFiltros.setVisible(true);
					btnLimparFiltros.setEnabled(true);
				} else {
					btnLimparFiltros.setVisible(false);
					btnLimparFiltros.setEnabled(false);
				}
			}
		});
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		scrollPane.setEnabled(false);
		scrollPane.setBounds(12, 205, 942, 120);
		contentPane.add(scrollPane);

		table = new JTable(modelo);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (verificarSetabelaTemItemSelecionado()) {
					btnAlterar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnLimpar.setEnabled(true);
					btnLimparFiltros.setEnabled(true);
					btnGerarPlanilha.setEnabled(true);
				} else {
					btnAlterar.setEnabled(false);
					btnExcluir.setEnabled(false);
					btnLimpar.setEnabled(false);
					btnGerarPlanilha.setEnabled(false);
					btnLimparFiltros.setEnabled(false);
				}
			}
		});

		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		modelo.addColumn("idVacina");
		modelo.addColumn("Nome pesquisador responsável");
		modelo.addColumn("País Origem");
		modelo.addColumn("Nome Vacina");
		modelo.addColumn("Data início pesquisa");
		modelo.addColumn("Quantidade doses");
		modelo.addColumn("Intervalo doses");

		btnAlterar = new JButton("Alterar");
		btnAlterar.setEnabled(false);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadastroVacina telaCadastroVacina = new TelaCadastroVacina();
				Integer idVacinaSelecionada = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
				VacinaVO vacinaSelecionada = vController.consultarPorId(idVacinaSelecionada);

				setVisible(false);
				telaCadastroVacina.setVisible(true);
				telaCadastroVacina.preencherCampos(vacinaSelecionada);
//				limparTabelaPessoa();
//				carregarTabela();
			}
		});
		btnAlterar.setBounds(256, 346, 210, 56);
		contentPane.add(btnAlterar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Integer idVacinaSelecionada = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
				respostaExclusao = JOptionPane.showOptionDialog(null, "\nDeseja excluir vacina?", "Confirmação",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
				excluir(idVacinaSelecionada);
			}
		});
		btnExcluir.setBounds(476, 346, 210, 56);
		contentPane.add(btnExcluir);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparTabelaVacina();
				carregarTabela();
				btnLimpar.setEnabled(true);
				btnGerarPlanilha.setEnabled(true);
				
				//Nao aparecer quando clica em consultar novamente
				btnAlterar.setEnabled(false);
				btnExcluir.setEnabled(false);
			}
		});
		btnConsultar.setBounds(394, 145, 174, 47);
		contentPane.add(btnConsultar);

		JLabel lblNomeVacina = new JLabel("Nome vacina");
		lblNomeVacina.setBounds(208, 32, 114, 14);
		contentPane.add(lblNomeVacina);

		txtNomeVacina = new JTextField();
		txtNomeVacina.setBounds(208, 50, 139, 35);
		contentPane.add(txtNomeVacina);
		txtNomeVacina.setColumns(10);

		JLabel lblPaisOrigem = new JLabel("País origem");
		lblPaisOrigem.setBounds(373, 32, 114, 14);
		contentPane.add(lblPaisOrigem);

		cbxPais = new JComboBox();
		for (int i = 0; i < listarPaises().length; i++) {
			cbxPais.addItem(listarPaises()[i]);
		}
		cbxPais.setBounds(373, 49, 174, 36);
		contentPane.add(cbxPais);

		JLabel lblDoses = new JLabel("Doses");
		lblDoses.setBounds(566, 32, 119, 14);
		contentPane.add(lblDoses);

		btnLimpar = new JButton("Limpar");
		btnLimpar.setEnabled(false);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparTabelaVacina();
				btnLimpar.setEnabled(false);
				btnGerarPlanilha.setEnabled(false);
				btnAlterar.setEnabled(false);
				btnExcluir.setEnabled(false);

			}
		});
		btnLimpar.setEnabled(false);
		btnLimpar.setBounds(805, 145, 149, 47);
		contentPane.add(btnLimpar);

		btnGerarPlanilha = new JButton("Gerar relatório");
		btnGerarPlanilha.setEnabled(false);
		btnGerarPlanilha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Salvar relatório como...");
				ArrayList<VacinaVO> list = vController.consultarTodosRelatorio();
				int resultado = jfc.showSaveDialog(null);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					String caminhoEscolhido = jfc.getSelectedFile().getAbsolutePath();
					PlanilhaVacina planilha = new PlanilhaVacina();
					planilha.gerarPlanilhaVacinas(caminhoEscolhido, list);
					JOptionPane.showMessageDialog(null, "Salvo com sucesso.");

				}
			}
		});
		btnGerarPlanilha.setBounds(12, 145, 149, 47);
		contentPane.add(btnGerarPlanilha);

		cbxDoses = new JComboBox();
		for (int i = 0; i < listarDoses().length; i++) {
			cbxDoses.addItem(listarDoses()[i]);
		}
		cbxDoses.setBounds(566, 50, 174, 35);
		contentPane.add(cbxDoses);

		btnLimparFiltros = new JButton("Limpar filtros");
		btnLimparFiltros.setEnabled(false);
		btnLimparFiltros.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limparFiltros();
				btnLimparFiltros.setEnabled(false);
			}
		});
		btnLimparFiltros.setEnabled(false);
		btnLimparFiltros.setBounds(805, 50, 139, 35);
		contentPane.add(btnLimparFiltros);
	}

	public void excluir(Integer id) {
		if (respostaExclusao == 0) {
			if (vController.excluir(id)) {
				JOptionPane.showMessageDialog(null, "\nVacina excluída com sucesso");
				modelo.setRowCount(0);
				carregarTabela();
			} else {
				JOptionPane.showMessageDialog(null, "\nNão foi possível excluir vacina");
			}
		} else {

		}
	}

	public boolean verificarFiltroPreenchido() {
		boolean resposta = false;
		if (txtNomeVacina.getText().toString().length() != 0
				|| cbxDoses.getSelectedItem().toString() != "Selecione a quantidade"
				|| cbxPais.getSelectedItem().toString() != "Selecione o país") {
			resposta = true;
		}
		return resposta;
	}

	public void carregarTabela() {
		VacinaDAO vacina = new VacinaDAO();
		ArrayList<VacinaVO> list = vacina.consultarTodos();
		FiltroVacina seletor = new FiltroVacina();

		if (verificarFiltroPreenchido()) {
			if (txtNomeVacina.getText().toString().length() != 0) {
				seletor.setNomeVacina(txtNomeVacina.getText().toString());
			}
			if (cbxDoses.getSelectedItem().toString() != "Selecione a quantidade") {
				seletor.setDoses(cbxDoses.getSelectedItem().toString());
			}

			if (cbxPais.getSelectedItem().toString() != "Selecione o país") {
				seletor.setPais(cbxPais.getSelectedItem().toString());
			}
			list = vController.consultarComFiltro(seletor);
			String mensagem = "";
			if (list.size() > 0) {
				mensagem = list.size() + " resultados encontrados";
			} else {
				mensagem = "Nenhum resultado encontrado";
			}
			JOptionPane.showMessageDialog(null, mensagem);
		} else {
			list = vController.consultarTodos();
		}
		for (VacinaVO vac : list) {
			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dataInicioFormatada = formatador.format(vac.getDataInicioPesquisa());

			modelo.addRow(new Object[] { vac.getIdVacina(), vac.getNomePesquisadorResponsavel(), vac.getPaisOrigem(),
					vac.getNomeVacina(), dataInicioFormatada, vac.getQuantidadeDoses(), vac.getIntervaloDoses() });
		}
	}

	private boolean verificarSetabelaTemItemSelecionado() {
		boolean resposta = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0) > 0;

		return resposta;
	}

	private void limparTabelaVacina() {
		modelo.setRowCount(0);
	}

	private void limparFiltros() {
		txtNomeVacina.setText("");
		cbxPais.setSelectedItem("Selecione o país");
		cbxDoses.setSelectedItem("Selecione a quantidade");
	}

	public String[] listarDoses() {
		String[] listaDeDoses = new String[] { "Selecione a quantidade", "1", "2", "3", "4", "5" };

		return listaDeDoses;
	};

	public String[] listarPaises() {
		String[] listaDePaises = new String[] { "Selecione o país", "Afeganistão", "África do Sul", "Akrotiri",
				"Albânia", "Alemanha", "Andorra", "Angola", "Anguila", "Antárctida", "Antígua e Barbuda",
				"Arábia Saudita", "Arctic Ocean", "Argélia", "Argentina", "Arménia", "Aruba",
				"Ashmore and Cartier Islands", "Atlantic Ocean", "Austrália", "Áustria", "Azerbaijão", "Baamas",
				"Bangladeche", "Barbados", "Barém", "Bélgica", "Belize", "Benim", "Bermudas", "Bielorrússia",
				"Birmânia", "Bolívia", "Bósnia e Herzegovina", "Botsuana", "Brasil", "Brunei", "Bulgária",
				"Burquina Faso", "Burúndi", "Butão", "Cabo Verde", "Camarões", "Camboja", "Canadá", "Catar",
				"Cazaquistão", "Chade", "Chile", "China", "Chipre", "Clipperton Island", "Colômbia", "Comores",
				"Congo-Brazzaville", "Congo-Kinshasa", "CoralSea Islands", "Coreia do Norte", "Coreia do Sul",
				"Costa do Marfim", "Costa Rica", "Croácia", "Cuba", "Curacao", "Dhekelia", "Dinamarca", "Domínica",
				"Egipto", "Emiratos Árabes Unidos", "Equador", "Eritreia", "Eslováquia", "Eslovénia", "Espanha",
				"Estados Unidos", "Estónia", "Etiópia", "Faroé", "Fiji", "Filipinas", "Finlândia", "França", "Gabão",
				"Gâmbia", "Gana", "Gaza Strip", "Geórgia", "Geórgia do Sul e Sandwich do Sul", "Gibraltar", "Granada",
				"Grécia", "Gronelândia", "Guame", "Guatemala", "Guernsey", "Guiana", "Guiné", "Guiné Equatorial",
				"Guiné-Bissau", "Haiti", "Honduras", "Hong Kong", "Hungria", "Lémen", "Ilha Bouvet", "Ilha do Natal",
				"Ilha Norfolk", "Ilhas Caimão", "Ilhas Cook", "Ilhas dos Cocos", "Ilhas Falkland",
				"Ilhas Heard e McDonald", "Ilhas Marshall", "Ilhas Salomão", "Ilhas Turcas e Caicos",
				"Ilhas Virgens Americanas", "Ilhas Virgens Britânicas", "Índia", "Indian Ocean", "Indonésia", "Irão",
				"Iraque", "Irlanda", "Islândia", "Israel", "Itália", "Jamaica", "Jan Mayen", "Japão", "Jersey",
				"Jibuti", "Jordânia", "Kosovo", "Kuwait", "Laos", "Lesoto", "Letónia", "Líbano", "Libéria", "Líbia",
				"Listenstaine", "Lituânia", "Luxemburgo", "Macau", "Macedónia", "Madagáscar", "Malásia", "Malávi",
				"Maldivas", "Mali", "Malta", "Man, Isle of", "Marianas do Norte", "Marrocos", "Maurícia", "Mauritânia",
				"México", "Micronésia", "Moçambique", "Moldávia", "Mónaco", "Mongólia", "Monserrate", "Montenegro",
				"Mundo", "Namíbia", "Nauru", "Navassa Island", "Nepal", "Nicarágua", "Níger", "Nigéria", "Niue",
				"Noruega", "Nova Caledónia", "Nova Zelândia", "Omã", "Pacific Ocean", "Países Baixos", "Palau",
				"Panamá", "Papua-Nova Guiné", "Paquistão", "Paracel Islands", "Paraguai", "Peru", "Pitcairn",
				"Polinésia Francesa", "Polónia", "Porto Rico", "Portugal", "Quénia", "Quirguizistão", "Quiribáti",
				"Reino Unido", "República Centro-Africana", "República Dominicana", "Roménia", "Ruanda", "Rússia",
				"Salvador", "Samoa", "Samoa Americana", "Santa Helena", "Santa Lúcia", "São Bartolomeu",
				"São Cristóvão e Neves", "São Marinho", "São Martinho", "São Pedro e Miquelon", "São Tomé e Príncipe",
				"São Vicente e Granadinas", "Sara Ocidental", "Seicheles", "Senegal", "Serra Leoa", "Sérvia",
				"Singapura", "Sint Maarten", "Síria", "Somália", "Southern Ocean", "Spratly Islands", "Sri Lanca",
				"Suazilândia", "Sudão", "Sudão do Sul", "Suécia", "Suíça", "Suriname", "Svalbard e Jan Mayen",
				"Tailândia", "Taiwan", "Tajiquistão", "Tanzânia", "Território Britânico do Oceano Índico",
				"Territórios Austrais Franceses", "Timor Leste", "Togo", "Tokelau", "Tonga", "Trindade e Tobago" };
		return listaDePaises;
	}
}
