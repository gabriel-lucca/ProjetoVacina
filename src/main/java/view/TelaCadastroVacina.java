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

		JLabel lblPasDeOrigem = new JLabel("PaÃ­s de origem:");
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

		JLabel lblDataIncioE = new JLabel("Data inÃ­cio pesquisa:");
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
				if (resultadoValidData.equals("DATA VÃ�LIDA.")) {

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
				if (resultadoValidData.equals("DATA VÃ�LIDA.")) {
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
		if (resultadoValidacao == null) {
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
		String[] listaDePaises = new String[] { "AlbÃ¢nia", "Alemanha", "Andorra", "Angola", "Anguilla", "AntÃ¡rtida",
				"AntÃ­gua e Barbuda", "Antilhas Holandesas", "ArÃ¡bia Saudita", "ArgÃ©lia", "Argentina", "ArmÃªnia",
				"Aruba", "AustrÃ¡lia", "Ã�ustria", "AzerbaijÃ£o", "Bahamas", "Bahrein", "Bangladesh", "Barbados",
				"Belarus", "BÃ©lgica", "Belize", "Benin", "Bermudas", "BolÃ­via", "BÃ³snia-HerzegÃ³vina", "Botsuana",
				"Brasil", "Brunei", "BulgÃ¡ria", "Burkina Fasso", "Burundi", "ButÃ£o", "Cabo Verde", "CamarÃµes",
				"Camboja", "CanadÃ¡", "CazaquistÃ£o", "Chade", "Chile", "China", "Chipre", "Cingapura", "ColÃ´mbia",
				"Congo", "CorÃ©ia do Norte", "CorÃ©ia do Sul", "Costa do Marfim", "Costa Rica", "CroÃ¡cia (Hrvatska)",
				"Cuba", "Dinamarca", "Djibuti", "Dominica", "Egito", "El Salvador", "Emirados Ã�rabes Unidos", "Equador",
				"EritrÃ©ia", "EslovÃ¡quia", "EslovÃªnia", "Espanha", "Estados Unidos", "EstÃ´nia", "EtiÃ³pia", "Fiji",
				"Filipinas", "FinlÃ¢ndia", "FranÃ§a", "GabÃ£o", "GÃ¢mbia", "Gana", "GeÃ³rgia", "Gibraltar",
				"GrÃ£-Bretanha (Reino Unido, UK)", "Granada", "GrÃ©cia", "GroelÃ¢ndia", "Guadalupe",
				"Guam (TerritÃ³rio dos Estados Unidos)", "Guatemala", "Guernsey", "Guiana", "Guiana Francesa", "GuinÃ©",
				"GuinÃ© Equatorial", "GuinÃ©-Bissau", "Haiti", "Holanda", "Honduras", "Hong Kong", "Hungria", "IÃªmen",
				"Ilha Bouvet (TerritÃ³rio da Noruega)", "Ilha do Homem", "Ilha Natal", "Ilha Pitcairn", "Ilha ReuniÃ£o",
				"Ilhas Aland", "Ilhas Cayman", "Ilhas Cocos", "Ilhas Comores", "Ilhas Cook", "Ilhas Faroes",
				"Ilhas Falkland (Malvinas)", "Ilhas GeÃ³rgia do Sul e Sandwich do Sul",
				"Ilhas Heard e McDonald (TerritÃ³rio da AustrÃ¡lia)", "Ilhas Marianas do Norte", "Ilhas Marshall",
				"Ilhas Menores dos Estados Unidos", "Ilhas Norfolk", "Ilhas Seychelles", "Ilhas SolomÃ£o",
				"Ilhas Svalbard e Jan Mayen", "Ilhas Tokelau", "Ilhas Turks e Caicos", "Ilhas Virgens (Estados Unidos)",
				"Ilhas Virgens (Inglaterra)", "Ilhas Wallis e Futuna", "Ã�ndia", "IndonÃ©sia", "Iraque", "Irlanda",
				"IslÃ¢ndia", "Israel", "ItÃ¡lia", "Jamaica", "JapÃ£o", "Jersey", "JordÃ¢nia", "KÃªnia", "Kiribati", "Kuait",
				"Laos", "LÃ¡tvia", "Lesoto", "LÃ­bano", "LibÃ©ria", "LÃ­bia", "Liechtenstein", "LituÃ¢nia", "Luxemburgo",
				"Macau", "MacedÃ´nia (RepÃºblica Yugoslava)", "Madagascar", "MalÃ¡sia", "Malaui", "Maldivas", "Mali",
				"Malta", "Marrocos", "Martinica", "MaurÃ­cio", "MauritÃ¢nia", "Mayotte", "MÃ©xico", "MicronÃ©sia",
				"MoÃ§ambique", "Moldova", "MÃ´naco", "MongÃ³lia", "Montenegro", "Montserrat", "Myanma", "NamÃ­bia", "Nauru",
				"Nepal", "NicarÃ¡gua", "NÃ­ger", "NigÃ©ria", "Niue", "Noruega", "Nova CaledÃ´nia", "Nova ZelÃ¢ndia", "OmÃ£",
				"Palau", "PanamÃ¡", "Papua-Nova GuinÃ©", "PaquistÃ£o", "Paraguai", "Peru", "PolinÃ©sia Francesa", "PolÃ´nia",
				"Porto Rico", "Portugal", "Qatar", "QuirguistÃ£o", "RepÃºblica Centro-Africana",
				"RepÃºblica DemocrÃ¡tica do Congo", "RepÃºblica Dominicana", "RepÃºblica Tcheca", "RomÃªnia", "Ruanda",
				"RÃºssia", "Saara Ocidental", "Saint Vincente e Granadinas", "Samoa Americana", "Samoa Ocidental",
				"San Marino", "Santa Helena", "Santa LÃºcia", "SÃ£o Bartolomeu", "SÃ£o CristÃ³vÃ£o e NÃ©vis", "SÃ£o Martim",
				"SÃ£o TomÃ© e PrÃ­ncipe", "Senegal", "Serra Leoa", "SÃ©rvia", "SÃ­ria", "SomÃ¡lia", "Sri Lanka",
				"St. Pierre and Miquelon", "SuazilÃ¢ndia", "SudÃ£o", "SuÃ©cia", "SuÃ­Ã§a", "Suriname", "TadjiquistÃ£o",
				"TailÃ¢ndia", "Taiwan", "TanzÃ¢nia", "TerritÃ³rios do Sul da FranÃ§a", "TerritÃ³rios Palestinos Ocupados",
				"Timor Leste", "Togo", "Tonga", "Trinidad and Tobago", "TunÃ­sia", "TurcomenistÃ£o", "Turquia", "Tuvalu",
				"UcrÃ¢nia", "Uganda", "UzbequistÃ£o", "Vanuatu", "Vaticano", "Venezuela", "VietnÃ£", "ZÃ¢mbia",
				"ZimbÃ¡bue" };

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
