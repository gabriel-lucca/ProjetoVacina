package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.ControladoraVacina;
import exception.exceptionVacina.AnalisarCamposVacinaException;
import exception.exceptionVacina.VacinaJaExisteException;
import model.vo.VacinaVO;
import util.Data;

public class TelaCadastroVacina extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeVacina;
	private JTextField txtNomePesquisador;
	private JTextField txtIntervalo;
	private JTextField txtDataInicio;
	private JComboBox cbxDoses;
	private JComboBox cbxPais;
	private JComboBox cbxStatus;
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

		JLabel lblPasDeOrigem = new JLabel("Pa??s de origem:");
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

		JLabel lblDataIncioE = new JLabel("Data in??cio pesquisa:");
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
						validarData(txtDataInicio.getText());
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

		JLabel lblStatus = new JLabel("Status da vacina:");
		lblStatus.setFont(new Font("Dialog", Font.BOLD, 13));
		lblStatus.setBounds(261, 328, 224, 14);
		contentPane.add(lblStatus);

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
		
		cbxStatus = new JComboBox();
		for (int i = 0; i < listarStatus().length; i++) {
			cbxStatus.addItem(listarStatus()[i]);
		}
		cbxStatus.setBounds(497, 320, 74, 33);
		contentPane.add(cbxStatus);


		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (resultadoValidData.equals("DATA V??LIDA.")) {

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
		btnCadastrar.setBounds(418, 364, 153, 47);
		contentPane.add(btnCadastrar);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validarData(txtDataInicio.getText());
				if (("DATA V??LIDA.").equals(resultadoValidData)) {
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
		btnAlterar.setBounds(255, 364, 153, 47);
		contentPane.add(btnAlterar);

		jlAvisoDose = new JLabel("Insira apenas valores inteiros*");
		jlAvisoDose.setForeground(Color.RED);
		jlAvisoDose.setFont(new Font("Tahoma", Font.PLAIN, 9));
		jlAvisoDose.setBounds(497, 303, 129, 14);
		jlAvisoDose.setVisible(false);
		contentPane.add(jlAvisoDose);

	}

	private void validarData(String string) {
		Data dtClass = new Data();
		String[] data = string.toString().split("/");
		int dia = Integer.parseInt(data[0]);
		int mes = Integer.parseInt(data[1]);
		int ano = Integer.parseInt(data[2]);
		resultadoValidData = dtClass.validarData(dia, mes, ano);
	}

	// M????todos e fun????????es:
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
		novaVacina.setStatusVacina(cbxStatus.getSelectedItem().toString());

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
		vacinaAlterada.setStatusVacina(cbxStatus.getSelectedItem().toString());
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
				&& !cbxStatus.getSelectedItem().toString().isEmpty()
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
		this.cbxStatus.setSelectedItem(vacina.getStatusVacina());

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
		this.cbxStatus.setSelectedIndex(0);

	}

	public String[] listarDoses() {
		String[] listaDeDoses = new String[] { "1", "2", "3", "4", "5" };

		return listaDeDoses;
	};

	public String[] listarStatus() {
		String[] listaDeStatus = new String[] { "Ativado", "Desativado" };

		return listaDeStatus;
	};

	public String[] listarPaises() {
		String[] listaDePaises = new String[] { "Afeganist??o", "??frica do Sul", "Akrotiri", "Alb??nia", "Alemanha",
				"Andorra", "Angola", "Anguila", "Ant??rctida", "Ant??gua e Barbuda", "Ar??bia Saudita", "Arctic Ocean",
				"Arg??lia", "Argentina", "Arm??nia", "Aruba", "Ashmore and Cartier Islands", "Atlantic Ocean",
				"Austr??lia", "??ustria", "Azerbaij??o", "Baamas", "Bangladeche", "Barbados", "Bar??m", "B??lgica", "Belize",
				"Benim", "Bermudas", "Bielorr??ssia", "Birm??nia", "Bol??via", "B??snia e Herzegovina", "Botsuana",
				"Brasil", "Brunei", "Bulg??ria", "Burquina Faso", "Bur??ndi", "But??o", "Cabo Verde", "Camar??es",
				"Camboja", "Canad??", "Catar", "Cazaquist??o", "Chade", "Chile", "China", "Chipre", "Clipperton Island",
				"Col??mbia", "Comores", "Congo-Brazzaville", "Congo-Kinshasa", "CoralSea Islands", "Coreia do Norte",
				"Coreia do Sul", "Costa do Marfim", "Costa Rica", "Cro??cia", "Cuba", "Curacao", "Dhekelia", "Dinamarca",
				"Dom??nica", "Egipto", "Emiratos ??rabes Unidos", "Equador", "Eritreia", "Eslov??quia", "Eslov??nia",
				"Espanha", "Estados Unidos", "Est??nia", "Eti??pia", "Faro??", "Fiji", "Filipinas", "Finl??ndia", "Fran??a",
				"Gab??o", "G??mbia", "Gana", "Gaza Strip", "Ge??rgia", "Ge??rgia do Sul e Sandwich do Sul", "Gibraltar",
				"Granada", "Gr??cia", "Gronel??ndia", "Guame", "Guatemala", "Guernsey", "Guiana", "Guin??",
				"Guin?? Equatorial", "Guin??-Bissau", "Haiti", "Honduras", "Hong Kong", "Hungria", "L??men", "Ilha Bouvet",
				"Ilha do Natal", "Ilha Norfolk", "Ilhas Caim??o", "Ilhas Cook", "Ilhas dos Cocos", "Ilhas Falkland",
				"Ilhas Heard e McDonald", "Ilhas Marshall", "Ilhas Salom??o", "Ilhas Turcas e Caicos",
				"Ilhas Virgens Americanas", "Ilhas Virgens Brit??nicas", "??ndia", "Indian Ocean", "Indon??sia", "Ir??o",
				"Iraque", "Irlanda", "Isl??ndia", "Israel", "It??lia", "Jamaica", "Jan Mayen", "Jap??o", "Jersey",
				"Jibuti", "Jord??nia", "Kosovo", "Kuwait", "Laos", "Lesoto", "Let??nia", "L??bano", "Lib??ria", "L??bia",
				"Listenstaine", "Litu??nia", "Luxemburgo", "Macau", "Maced??nia", "Madag??scar", "Mal??sia", "Mal??vi",
				"Maldivas", "Mali", "Malta", "Man, Isle of", "Marianas do Norte", "Marrocos", "Maur??cia", "Maurit??nia",
				"M??xico", "Micron??sia", "Mo??ambique", "Mold??via", "M??naco", "Mong??lia", "Monserrate", "Montenegro",
				"Mundo", "Nam??bia", "Nauru", "Navassa Island", "Nepal", "Nicar??gua", "N??ger", "Nig??ria", "Niue",
				"Noruega", "Nova Caled??nia", "Nova Zel??ndia", "Om??", "Pacific Ocean", "Pa??ses Baixos", "Palau",
				"Panam??", "Papua-Nova Guin??", "Paquist??o", "Paracel Islands", "Paraguai", "Peru", "Pitcairn",
				"Polin??sia Francesa", "Pol??nia", "Porto Rico", "Portugal", "Qu??nia", "Quirguizist??o", "Quirib??ti",
				"Reino Unido", "Rep??blica Centro-Africana", "Rep??blica Dominicana", "Rom??nia", "Ruanda", "R??ssia",
				"Salvador", "Samoa", "Samoa Americana", "Santa Helena", "Santa L??cia", "S??o Bartolomeu",
				"S??o Crist??v??o e Neves", "S??o Marinho", "S??o Martinho", "S??o Pedro e Miquelon", "S??o Tom?? e Pr??ncipe",
				"S??o Vicente e Granadinas", "Sara Ocidental", "Seicheles", "Senegal", "Serra Leoa", "S??rvia",
				"Singapura", "Sint Maarten", "S??ria", "Som??lia", "Southern Ocean", "Spratly Islands", "Sri Lanca",
				"Suazil??ndia", "Sud??o", "Sud??o do Sul", "Su??cia", "Su????a", "Suriname", "Svalbard e Jan Mayen",
				"Tail??ndia", "Taiwan", "Tajiquist??o", "Tanz??nia", "Territ??rio Brit??nico do Oceano ??ndico",
				"Territ??rios Austrais Franceses", "Timor Leste", "Togo", "Tokelau", "Tonga", "Trindade e Tobago" };

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
