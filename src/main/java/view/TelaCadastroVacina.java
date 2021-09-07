package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.ControladoraVacina;
import exception.exceptionVacina.AnalisarCamposVacinaException;
import exception.exceptionVacina.VacinaJaExisteException;
import model.vo.VacinaVO;
import util.Data;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class TelaCadastroVacina extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeVacina;
	private JTextField txtNomePesquisador;
	private JTextField txtIntervalo;
	private JTextField txtDataInicio;
	private JComboBox cbxDoses;
	private JComboBox cbxPais;
	private JButton btnCadastrar;
	private JButton btnAlterar;
	private ControladoraVacina controller = new ControladoraVacina();
	private MaskFormatter mascaraDataInicio;
	private int respostaCadastro;
	private int respostaAlteracao;
	private int respostaExclusao;
	private JLabel jlAvisoDose;
	private boolean ativaBotao = true;
	private String nomeAntigo;
	private String resultadoValidData;

	private DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private int id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroVacina frame = new TelaCadastroVacina();
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
	public TelaCadastroVacina() {
		setBackground(Color.BLACK);
		setTitle("Tela cadastro de vacina");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 656, 472);
		contentPane = new JPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (verificarCamposPreenchdidos()) {
					btnCadastrar.setEnabled(ativaBotao);
				} else {
					btnCadastrar.setEnabled(false);
				}

				if (txtIntervalo.getText().length() > 0) {
					boolean resosta = validarCampo(txtIntervalo);
					if (!resosta) {
						String numeroString = txtIntervalo.getText().toString();
						StringBuilder intervalo = new StringBuilder(numeroString);
						numeroString = String.valueOf(intervalo.deleteCharAt(numeroString.length() - 1));
						txtIntervalo.setText(numeroString);
					}
				}
			}
		});
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		JLabel lblPasDeOrigem = new JLabel("País de origem:");
		lblPasDeOrigem.setFont(new Font("Dialog", Font.BOLD, 13));
		lblPasDeOrigem.setBounds(26, 104, 175, 18);
		contentPane.add(lblPasDeOrigem);

		txtNomeVacina = new JTextField();
		txtNomeVacina.setBounds(261, 133, 310, 35);
		contentPane.add(txtNomeVacina);
		txtNomeVacina.setColumns(10);

		JLabel lblNomeDaVacina = new JLabel("Nome da vacina:");
		lblNomeDaVacina.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNomeDaVacina.setBounds(26, 142, 175, 14);
		contentPane.add(lblNomeDaVacina);

		JLabel lblDataIncioE = new JLabel("Data início pesquisa:");
		lblDataIncioE.setFont(new Font("Dialog", Font.BOLD, 13));
		lblDataIncioE.setBounds(261, 186, 137, 14);
		contentPane.add(lblDataIncioE);

		// MaskFormatter mascaraDataInicio;
		try {
			mascaraDataInicio = new MaskFormatter("##/##/####");
			txtDataInicio = new JFormattedTextField(mascaraDataInicio);
			txtDataInicio.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (txtDataInicio.getText().equals("  /  /    ")) {
						JOptionPane.showMessageDialog(null, "O campo data deve ser preenchido", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);
						txtDataInicio.requestFocus();
					} else {
						Data dtClass = new Data();
						String[] data = txtDataInicio.getText().toString().split("/");
						int dia = Integer.parseInt(data[0]);
						int mes = Integer.parseInt(data[1]);
						int ano = Integer.parseInt(data[2]);
						resultadoValidData = dtClass.validarData(dia, mes, ano);
					}
				}
			});
		} catch (ParseException e1) {
			System.out.println("Erro: " + e1.getMessage());
		}
		txtDataInicio.setColumns(10);
		txtDataInicio.setBounds(497, 179, 74, 31);
		contentPane.add(txtDataInicio);

		JLabel lblQntDoses = new JLabel("Quantidade de doses:");
		lblQntDoses.setFont(new Font("Dialog", Font.BOLD, 13));
		lblQntDoses.setBounds(261, 238, 184, 14);
		contentPane.add(lblQntDoses);

		JLabel lblNomePesquisador = new JLabel("Nome do pesquisador respons\u00E1vel:");
		lblNomePesquisador.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNomePesquisador.setBounds(26, 66, 240, 14);
		contentPane.add(lblNomePesquisador);

		txtNomePesquisador = new JTextField();
		txtNomePesquisador.setColumns(10);
		txtNomePesquisador.setBounds(261, 46, 310, 34);
		contentPane.add(txtNomePesquisador);

		JLabel lblIntervalo = new JLabel("Intervalo entre as doses (em dias):");
		lblIntervalo.setFont(new Font("Dialog", Font.BOLD, 13));
		lblIntervalo.setBounds(261, 281, 224, 14);
		contentPane.add(lblIntervalo);

		txtIntervalo = new JTextField();

		txtIntervalo.setBounds(497, 273, 74, 31);
		contentPane.add(txtIntervalo);
		txtIntervalo.setColumns(10);

		cbxDoses = new JComboBox();
		for (int i = 0; i < listarDoses().length; i++) {
			cbxDoses.addItem(listarDoses()[i]);
		}
		cbxDoses.setBounds(497, 229, 74, 33);
		contentPane.add(cbxDoses);

		cbxPais = new JComboBox();
		for (int i = 0; i < listarPaises().length; i++) {
			cbxPais.addItem(listarPaises()[i]);
		}
		cbxPais.setBounds(261, 91, 310, 33);
		contentPane.add(cbxPais);

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (resultadoValidData.equals("DATA VÁLIDA.")) {

					try {
						cadastrar();
					} catch (AnalisarCamposVacinaException | VacinaJaExisteException e) {
						JOptionPane.showMessageDialog(null, e);
					}
				} else {
					JOptionPane.showMessageDialog(null, resultadoValidData);
				}
			}
		});
		btnCadastrar.setEnabled(false);
		btnCadastrar.setBounds(418, 334, 153, 47);
		contentPane.add(btnCadastrar);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (resultadoValidData.equals("DATA VÁLIDA.")) {
					try {
						alterar();
					} catch (AnalisarCamposVacinaException | VacinaJaExisteException e) {
						JOptionPane.showMessageDialog(null, e);
					}
				} else {
					JOptionPane.showMessageDialog(null, resultadoValidData);
				}
			}
		});
		btnAlterar.setEnabled(false);
		btnAlterar.setBounds(255, 334, 153, 47);
		contentPane.add(btnAlterar);

		jlAvisoDose = new JLabel("Insira apenas valores inteiros*");
		jlAvisoDose.setForeground(Color.RED);
		jlAvisoDose.setFont(new Font("Tahoma", Font.PLAIN, 9));
		jlAvisoDose.setBounds(497, 303, 129, 14);
		jlAvisoDose.setVisible(false);
		contentPane.add(jlAvisoDose);

	}

	// MÃ©todos e funÃ§Ãµes:
	public void cadastrar() throws AnalisarCamposVacinaException, VacinaJaExisteException {
		// Instanciar uma nova vacina (de VacinaVO)
		// Preencher a nova vacina com todos os campos da tela
		VacinaVO novaVacina = new VacinaVO();
		novaVacina.setDataInicioPesquisa(LocalDate.parse(txtDataInicio.getText(), dataFormatter));
		novaVacina.setIntervaloDoses(Integer.valueOf(txtIntervalo.getText()));
		novaVacina.setNomePesquisadorResponsavel(txtNomePesquisador.getText());
		novaVacina.setNomeVacina(txtNomeVacina.getText());
		novaVacina.setPaisOrigem(cbxPais.getSelectedItem().toString());
		novaVacina.setQuantidadeDoses(cbxDoses.getSelectedItem().toString());

		// Chamar o controller para cadastrar
		controller.cadastrar(novaVacina);
		String resultadoValidacao = controller.validarCampos(novaVacina);
		if (resultadoValidacao != null) {
			JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
		}
		setVisible(false);
	}

	public void alterar() throws AnalisarCamposVacinaException, VacinaJaExisteException {
		VacinaVO vacinaAlterada = new VacinaVO();
		vacinaAlterada.setDataInicioPesquisa(LocalDate.parse(txtDataInicio.getText(), dataFormatter));
		vacinaAlterada.setIntervaloDoses(Integer.valueOf(txtIntervalo.getText()));
		vacinaAlterada.setNomePesquisadorResponsavel(txtNomePesquisador.getText());
		vacinaAlterada.setNomeVacina(txtNomeVacina.getText());
		vacinaAlterada.setPaisOrigem(cbxPais.getSelectedItem().toString());
		vacinaAlterada.setQuantidadeDoses(cbxDoses.getSelectedItem().toString());
		vacinaAlterada.setIdVacina(controller.consultarPorNome(nomeAntigo).getIdVacina());

		String resultadoValidacao = controller.validarCampos(vacinaAlterada);
		if (controller.alterar(vacinaAlterada)) {
			JOptionPane.showMessageDialog(null, "Alterado com sucesso");
		}

		setVisible(false);
	}

	public boolean verificarCamposPreenchdidos() {
		boolean resposta = false;
		if (!txtIntervalo.getText().isEmpty() && !txtNomePesquisador.getText().isEmpty()
				&& !txtNomeVacina.getText().isEmpty() && !txtDataInicio.getText().isEmpty()
				&& !cbxDoses.getSelectedItem().toString().isEmpty()
				&& !cbxPais.getSelectedItem().toString().isEmpty()) {
			resposta = true;
		}
		return resposta;
	}

	public void preencherCampos(VacinaVO vacina) {
		this.txtIntervalo.setText(String.valueOf(vacina.getIntervaloDoses()));
		this.txtNomePesquisador.setText(vacina.getNomePesquisadorResponsavel());
		this.txtNomeVacina.setText(vacina.getNomeVacina());
		this.nomeAntigo = txtNomeVacina.getText();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataInicioPesquisaFormatada = formatador.format(vacina.getDataInicioPesquisa());
		this.txtDataInicio.setText(dataInicioPesquisaFormatada);
		this.cbxDoses.setSelectedItem(vacina.getQuantidadeDoses());
		this.cbxPais.setSelectedItem(vacina.getPaisOrigem());

		ativaBotao = false;
		btnAlterar.setEnabled(true);
	}

	private void limparCampos() {
		this.txtIntervalo.setText("");
		this.txtNomePesquisador.setText("");
		this.txtNomeVacina.setText("");
		this.txtDataInicio.setText("");
		this.cbxDoses.setSelectedIndex(0);
		this.cbxPais.setSelectedIndex(0);

	}

	public String[] listarDoses() {
		String[] listaDeDoses = new String[] { "1", "2", "3", "4", "5" };

		return listaDeDoses;
	};

	public String[] listarPaises() {
		String[] listaDePaises = new String[] { "Afeganistão", "África do Sul", "Akrotiri", "Albânia", "Alemanha",
				"Andorra", "Angola", "Anguila", "Antárctida", "Antígua e Barbuda", "Arábia Saudita", "Arctic Ocean",
				"Argélia", "Argentina", "Arménia", "Aruba", "Ashmore and Cartier Islands", "Atlantic Ocean",
				"Austrália", "Áustria", "Azerbaijão", "Baamas", "Bangladeche", "Barbados", "Barém", "Bélgica", "Belize",
				"Benim", "Bermudas", "Bielorrússia", "Birmânia", "Bolívia", "Bósnia e Herzegovina", "Botsuana",
				"Brasil", "Brunei", "Bulgária", "Burquina Faso", "Burúndi", "Butão", "Cabo Verde", "Camarões",
				"Camboja", "Canadá", "Catar", "Cazaquistão", "Chade", "Chile", "China", "Chipre", "Clipperton Island",
				"Colômbia", "Comores", "Congo-Brazzaville", "Congo-Kinshasa", "CoralSea Islands", "Coreia do Norte",
				"Coreia do Sul", "Costa do Marfim", "Costa Rica", "Croácia", "Cuba", "Curacao", "Dhekelia", "Dinamarca",
				"Domínica", "Egipto", "Emiratos Árabes Unidos", "Equador", "Eritreia", "Eslováquia", "Eslovénia",
				"Espanha", "Estados Unidos", "Estónia", "Etiópia", "Faroé", "Fiji", "Filipinas", "Finlândia", "França",
				"Gabão", "Gâmbia", "Gana", "Gaza Strip", "Geórgia", "Geórgia do Sul e Sandwich do Sul", "Gibraltar",
				"Granada", "Grécia", "Gronelândia", "Guame", "Guatemala", "Guernsey", "Guiana", "Guiné",
				"Guiné Equatorial", "Guiné-Bissau", "Haiti", "Honduras", "Hong Kong", "Hungria", "Lémen", "Ilha Bouvet",
				"Ilha do Natal", "Ilha Norfolk", "Ilhas Caimão", "Ilhas Cook", "Ilhas dos Cocos", "Ilhas Falkland",
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

	public boolean validarCampo(JTextField numero) {
		String mensagem;
		long valor;
		boolean resposta = false;
		boolean inteiro = true;

		jlAvisoDose.setVisible(resposta);
		String numeroString = numero.getText().toString();
		if (numero.getText().length() != 0) {
			try {
				valor = Integer.parseInt(numero.getText());
			} catch (NumberFormatException ex) {
				inteiro = false;
			}
		}

		if (inteiro) {
			resposta = true;
		} else {
			resposta = false;
		}
		return resposta;
	}
}
