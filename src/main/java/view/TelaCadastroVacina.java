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
	
	private Object[] opcoes = {"Sim", "Não"};
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
		setBounds(100, 100, 656, 486);
		contentPane = new JPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(verificarCamposPreenchdidos()) {
					btnCadastrar.setEnabled(ativaBotao);
				} else {
					btnCadastrar.setEnabled(false);
				}
				
				if(txtIntervalo.getText().length()>0) {
					boolean resosta = validarCampo(txtIntervalo);
					if(!resosta) {
						String numeroString = txtIntervalo.getText().toString();
						StringBuilder intervalo = new StringBuilder(numeroString);
						numeroString = String.valueOf(intervalo.deleteCharAt(numeroString.length()-1));
						txtIntervalo.setText(numeroString);
					}
				}
			}
		});
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPasDeOrigem = new JLabel("Pais de origem:");
		lblPasDeOrigem.setFont(new Font("Dialog", Font.BOLD, 13));
		lblPasDeOrigem.setBounds(30, 101, 175, 18);
		contentPane.add(lblPasDeOrigem);
		
		txtNomeVacina = new JTextField();
		txtNomeVacina.setBounds(265, 130, 310, 35);
		contentPane.add(txtNomeVacina);
		txtNomeVacina.setColumns(10);
		
		JLabel lblNomeDaVacina = new JLabel("Nome da vacina:");
		lblNomeDaVacina.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNomeDaVacina.setBounds(30, 139, 175, 14);
		contentPane.add(lblNomeDaVacina);
		
		JLabel lblDataIncioE = new JLabel("Data inicio pesquisa:");
		lblDataIncioE.setFont(new Font("Dialog", Font.BOLD, 13));
		lblDataIncioE.setBounds(265, 183, 137, 14);
		contentPane.add(lblDataIncioE);
		
//		MaskFormatter mascaraDataInicio;
		try {
			mascaraDataInicio = new MaskFormatter("##/##/####");
			txtDataInicio = new JFormattedTextField(mascaraDataInicio);
		} catch (ParseException | NullPointerException npe) {
		}
		
		txtDataInicio.setColumns(10);
		txtDataInicio.setBounds(501, 176, 74, 31);
		contentPane.add(txtDataInicio);
		
		JLabel lblQntDoses = new JLabel("Quantidade de doses:");
		lblQntDoses.setFont(new Font("Dialog", Font.BOLD, 13));
		lblQntDoses.setBounds(265, 235, 184, 14);
		contentPane.add(lblQntDoses);
		
		JLabel lblNomePesquisador = new JLabel("Nome do pesquisador respons\u00E1vel:");
		lblNomePesquisador.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNomePesquisador.setBounds(30, 63, 240, 14);
		contentPane.add(lblNomePesquisador);
		
		txtNomePesquisador = new JTextField();
		txtNomePesquisador.setColumns(10);
		txtNomePesquisador.setBounds(265, 43, 310, 34);
		contentPane.add(txtNomePesquisador);
		
		JLabel lblIntervalo = new JLabel("Intervalo entre as doses (em dias):");
		lblIntervalo.setFont(new Font("Dialog", Font.BOLD, 13));
		lblIntervalo.setBounds(265, 278, 224, 14);
		contentPane.add(lblIntervalo);
		
		txtIntervalo = new JTextField();
		
		txtIntervalo.setBounds(501, 270, 74, 31);
		contentPane.add(txtIntervalo);
		txtIntervalo.setColumns(10);		
		
		cbxDoses = new JComboBox();
		for(int i = 0; i<listarDoses().length; i++) {
			cbxDoses.addItem(listarDoses()[i]);
	    }		
		cbxDoses.setBounds(501, 226, 74, 33);
		contentPane.add(cbxDoses);
		
		cbxPais = new JComboBox();
		for(int i = 0; i<listarPaises().length; i++) {
	    	cbxPais.addItem(listarPaises()[i]);
	    }
		cbxPais.setBounds(265, 88, 310, 33);
		contentPane.add(cbxPais);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					cadastrar();
				} catch (AnalisarCamposVacinaException | VacinaJaExisteException e) {
					respostaCadastro = JOptionPane.showOptionDialog(null, e+"\nDeseja editar as informações?", "Aviso", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);		
				}
			}	
		});
		btnCadastrar.setEnabled(false);
		btnCadastrar.setBounds(422, 331, 153, 47);
		contentPane.add(btnCadastrar);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					alterar();
				} catch (AnalisarCamposVacinaException e) {
					respostaAlteracao = JOptionPane.showOptionDialog(null, e+"\nDeseja editar as informações?", "Aviso", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);		
				}
			}
		});
		btnAlterar.setEnabled(false);
		btnAlterar.setBounds(259, 331, 153, 47);
		contentPane.add(btnAlterar);
		
	    jlAvisoDose = new JLabel("Insira apenas valores inteiros*");
		jlAvisoDose.setForeground(Color.RED);
		jlAvisoDose.setFont(new Font("Tahoma", Font.PLAIN, 9));
		jlAvisoDose.setBounds(501, 300, 129, 14);
		jlAvisoDose.setVisible(false);
		contentPane.add(jlAvisoDose);
		
	}
	
	//Métodos e funções:
	public void cadastrar() throws AnalisarCamposVacinaException, VacinaJaExisteException {
		//Instanciar uma nova vacina (de VacinaVO)
		//Preencher a nova vacina com todos os campos da tela
		VacinaVO novaVacina = new VacinaVO();
		novaVacina.setDataInicioPesquisa(LocalDate.parse(txtDataInicio.getText(), dataFormatter));
		novaVacina.setIntervaloDoses(Integer.valueOf(txtIntervalo.getText()));
		novaVacina.setNomePesquisadorResponsavel(txtNomePesquisador.getText());
		novaVacina.setNomeVacina(txtNomeVacina.getText());
		novaVacina.setPaisOrigem(cbxPais.getSelectedItem().toString());
		novaVacina.setQuantidadeDoses(cbxDoses.getSelectedItem().toString());
		
		//Chamar o controller para cadastrar
		controller.cadastrar(novaVacina);
		String resultadoValidacao = controller.validarCampos(novaVacina);
		if(resultadoValidacao!=null && !resultadoValidacao.isEmpty()) {
			if(respostaCadastro == 0) {
				setVisible(false);
				setVisible(false);
			} else {
				setVisible(false);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");	
		}
		setVisible(false);
	}
	public void alterar() throws AnalisarCamposVacinaException {
		VacinaVO vacinaAlterada = new VacinaVO();
		vacinaAlterada.setDataInicioPesquisa(LocalDate.parse(txtDataInicio.getText(), dataFormatter));
		vacinaAlterada.setIntervaloDoses(Integer.valueOf(txtIntervalo.getText()));
		vacinaAlterada.setNomePesquisadorResponsavel(txtNomePesquisador.getText());
		vacinaAlterada.setNomeVacina(txtNomeVacina.getText());
		vacinaAlterada.setPaisOrigem(cbxPais.getSelectedItem().toString());
		vacinaAlterada.setQuantidadeDoses(cbxDoses.getSelectedItem().toString());
		vacinaAlterada.setIdVacina(controller.consultarPorNome(nomeAntigo).getIdVacina());
		
		String resultadoValidacao = controller.validarCampos(vacinaAlterada);
		if(controller.alterar(vacinaAlterada)) {			
			JOptionPane.showMessageDialog(null, "Alterado com sucesso");
		}
		
		setVisible(false);
	}
	public boolean verificarCamposPreenchdidos() {
		boolean resposta = false;
		if(!txtIntervalo.getText().isEmpty() &&
		!txtNomePesquisador.getText().isEmpty() &&
		!txtNomeVacina.getText().isEmpty() &&
		!txtDataInicio.getText().isEmpty() && 
		!cbxDoses.getSelectedItem().toString().isEmpty() &&
		!cbxPais.getSelectedItem().toString().isEmpty()) {
			 resposta = true;
		}
		return resposta;
	}
	public void preencherCampos(VacinaVO vacina) {
		this.txtIntervalo.setText(String.valueOf(vacina.getIntervaloDoses()));
		this.txtNomePesquisador.setText(vacina.getNomePesquisadorResponsavel());
		this.txtNomeVacina.setText(vacina.getNomeVacina());
		this.nomeAntigo=txtNomeVacina.getText();
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
	
	public String[] listarDoses(){
		String[] listaDeDoses = new String[] {"1", "2", "3", "4", "5"};
		
		return listaDeDoses;
	};
    
	public String[] listarPaises(){
		String[] listaDePaises = new String[] {"Albânia", "Alemanha", "Andorra", "Angola", "Anguilla", "Antártida", "Antígua e Barbuda", "Antilhas Holandesas",
	    "Arábia Saudita", "Argélia", "Argentina", "Armênia", "Aruba", "Austrália", "Áustria", "Azerbaijão", "Bahamas", "Bahrein", "Bangladesh", "Barbados",
	    "Belarus", "Bélgica", "Belize", "Benin", "Bermudas", "Bolívia", "Bósnia-Herzegóvina", "Botsuana", "Brasil", "Brunei", "Bulgária", "Burkina Fasso",
	    "Burundi", "Butão", "Cabo Verde", "Camarões", "Camboja", "Canadá", "Cazaquistão", "Chade", "Chile", "China", "Chipre", "Cingapura", "Colômbia",
	    "Congo", "Coréia do Norte", "Coréia do Sul", "Costa do Marfim", "Costa Rica", "Croácia (Hrvatska)", "Cuba", "Dinamarca", "Djibuti", "Dominica",
	    "Egito", "El Salvador", "Emirados Árabes Unidos", "Equador", "Eritréia", "Eslováquia", "Eslovênia", "Espanha", "Estados Unidos","Estônia", "Etiópia",
	    "Fiji", "Filipinas", "Finlândia", "França", "Gabão", "Gâmbia", "Gana", "Geórgia", "Gibraltar", "Grã-Bretanha (Reino Unido, UK)", "Granada", "Grécia",
	    "Groelândia", "Guadalupe", "Guam (Território dos Estados Unidos)", "Guatemala", "Guernsey", "Guiana", "Guiana Francesa", "Guiné", "Guiné Equatorial",
	    "Guiné-Bissau", "Haiti", "Holanda", "Honduras", "Hong Kong", "Hungria", "Iêmen", "Ilha Bouvet (Território da Noruega)", "Ilha do Homem", "Ilha Natal", 
	    "Ilha Pitcairn", "Ilha Reunião", "Ilhas Aland", "Ilhas Cayman", "Ilhas Cocos", "Ilhas Comores", "Ilhas Cook", "Ilhas Faroes", "Ilhas Falkland (Malvinas)", 
	    "Ilhas Geórgia do Sul e Sandwich do Sul", "Ilhas Heard e McDonald (Território da Austrália)", "Ilhas Marianas do Norte", "Ilhas Marshall", 
	    "Ilhas Menores dos Estados Unidos", "Ilhas Norfolk", "Ilhas Seychelles", "Ilhas Solomão", "Ilhas Svalbard e Jan Mayen", "Ilhas Tokelau", "Ilhas Turks e Caicos", 
	    "Ilhas Virgens (Estados Unidos)", "Ilhas Virgens (Inglaterra)", "Ilhas Wallis e Futuna", "Índia", "Indonésia", "Iraque", "Irlanda", "Islândia", "Israel", 
	    "Itália", "Jamaica", "Japão", "Jersey", "Jordânia", "Kênia", "Kiribati", "Kuait", "Laos", "Látvia", "Lesoto", "Líbano", "Libéria", 
	    "Líbia", "Liechtenstein", "Lituânia", "Luxemburgo", "Macau", "Macedônia (República Yugoslava)", "Madagascar", "Malásia", "Malaui", "Maldivas", 
	    "Mali", "Malta", "Marrocos", "Martinica", "Maurício", "Mauritânia", "Mayotte", "México", "Micronésia", "Moçambique", "Moldova", "Mônaco","Mongólia", 
	    "Montenegro", "Montserrat", "Myanma", "Namíbia", "Nauru", "Nepal", "Nicarágua", "Níger", "Nigéria", "Niue","Noruega", "Nova Caledônia", 
        "Nova Zelândia", "Omã", "Palau", "Panamá", "Papua-Nova Guiné", "Paquistão", "Paraguai", "Peru", "Polinésia Francesa", "Polônia", "Porto Rico",
	    "Portugal", "Qatar", "Quirguistão", "República Centro-Africana", "República Democrática do Congo", "República Dominicana", "República Tcheca","Romênia",
	    "Ruanda", "Rússia", "Saara Ocidental", "Saint Vincente e Granadinas", "Samoa Americana", "Samoa Ocidental", "San Marino",
	    "Santa Helena", "Santa Lúcia", "São Bartolomeu", "São Cristóvão e Névis", "São Martim", "São Tomé e Príncipe", "Senegal", "Serra Leoa", "Sérvia", "Síria",
	    "Somália", "Sri Lanka", "St. Pierre and Miquelon", "Suazilândia", "Sudão", "Suécia", "Suíça", "Suriname", "Tadjiquistão", "Tailândia", "Taiwan",
	    "Tanzânia", "Territórios do Sul da França", "Territórios Palestinos Ocupados", "Timor Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunísia",
	    "Turcomenistão", "Turquia", "Tuvalu", "Ucrânia", "Uganda", "Uzbequistão", "Vanuatu", "Vaticano", "Venezuela","Vietnã","Zâmbia","Zimbábue"};
	   
	    return listaDePaises;
	}
	
	public boolean validarCampo(JTextField numero) {
		String mensagem;
		long valor; 
		boolean resposta = false;
		boolean inteiro = true;
		
		jlAvisoDose.setVisible(resposta);
		String numeroString = numero.getText().toString();
		if (numero.getText().length() != 0){ 
			try { 
				valor = Integer.parseInt(numero.getText()); 
			}catch(NumberFormatException ex){ 
				inteiro = false;
			}
		}
		
		if(inteiro) {
			resposta = true;
		} else {
			resposta = false;
		}
		return resposta;
	}
}
