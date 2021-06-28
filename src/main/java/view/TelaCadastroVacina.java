package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.ControladoraVacina;
import exception.exceptionVacina.AnalisarCamposVacinaException;
import exception.exceptionVacina.PaisJaTemVacinaRegistradaException;
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

import javax.swing.DropMode;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
	private Object[] opcoes = {"Sim", "Não"};
	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
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
					btnCadastrar.setEnabled(true);
					btnAlterar.setEnabled(true);
				} else {
					btnCadastrar.setEnabled(false);
					btnAlterar.setEnabled(false);
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
		lblDataIncioE.setBounds(265, 192, 137, 14);
		contentPane.add(lblDataIncioE);
		
//		MaskFormatter mascaraDataInicio;
		try {
			mascaraDataInicio = new MaskFormatter("##/##/####");
			txtDataInicio = new JFormattedTextField(mascaraDataInicio);
		} catch (ParseException | NullPointerException npe) {
		}
		
		txtDataInicio.setColumns(10);
		txtDataInicio.setBounds(501, 184, 74, 31);
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
		
		try {
			txtIntervalo = new JTextField();
		} catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, nfe.getMessage());
		}
		txtIntervalo.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				validarNumero(txtIntervalo);
				
			}
		});
		txtIntervalo.setBounds(501, 270, 74, 31);
		contentPane.add(txtIntervalo);
		txtIntervalo.setColumns(10);		
		
		cbxDoses = new JComboBox();	
		cbxDoses.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		cbxDoses.setBounds(501, 226, 74, 33);
		contentPane.add(cbxDoses);
		
		cbxPais = new JComboBox();
		for(int i = 0; i<listarPaises().length; i++) {
	    	cbxPais.addItem(listarPaises()[i]);
	    }
		cbxPais.setBounds(265, 88, 310, 33);
		contentPane.add(cbxPais);
		
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setEnabled(false);
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					cadastrar();
				} catch (AnalisarCamposVacinaException | VacinaJaExisteException | PaisJaTemVacinaRegistradaException e) {
					respostaCadastro = JOptionPane.showOptionDialog(null, e+"\nDeseja editar as informações?", "Aviso", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);		
				}
			}	
		});
		btnCadastrar.setBounds(422, 331, 153, 47);
		contentPane.add(btnCadastrar);

		btnAlterar = new JButton("Alterar");
		btnAlterar.setEnabled(false);
		btnAlterar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				try {
					alterar();
				} catch (AnalisarCamposVacinaException e) {
					respostaAlteracao = JOptionPane.showOptionDialog(null, e+"\nDeseja editar as informações?", "Aviso", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);		
				}
			}
		});
		btnAlterar.setBounds(259, 331, 153, 47);
		contentPane.add(btnAlterar);
		
	}
	
	//Métodos e funções:
	public void cadastrar() throws AnalisarCamposVacinaException, VacinaJaExisteException, PaisJaTemVacinaRegistradaException {
		//Instanciar uma nova vacina (de VacinaVO)
		//Preencher a nova vacina com todos os campos da tela
		VacinaVO novaVacina = new VacinaVO();
		novaVacina.setDataInicioPesquisa(LocalDate.parse(txtDataInicio.getText(), dataFormatter));
		novaVacina.setIntervaloDoses(Integer.valueOf(txtIntervalo.getText()));
		novaVacina.setNomePesquisadorResponsavel(txtNomePesquisador.getText());
		novaVacina.setNomeVacina(txtNomeVacina.getText());
		novaVacina.setPaisOrigem(cbxPais.getSelectedItem().toString());
		novaVacina.setQuantidadeDoses(Integer.valueOf(cbxDoses.getSelectedItem().toString()));
		
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
	}
	public void alterar() throws AnalisarCamposVacinaException {
		VacinaVO vacinaAlterada = new VacinaVO();
		vacinaAlterada.setDataInicioPesquisa(LocalDate.parse(txtDataInicio.getText(), dataFormatter));
		vacinaAlterada.setIntervaloDoses(Integer.valueOf(txtIntervalo.getText()));
		vacinaAlterada.setNomePesquisadorResponsavel(txtNomePesquisador.getText());
		vacinaAlterada.setNomeVacina(txtNomeVacina.getText());
		vacinaAlterada.setPaisOrigem(cbxPais.getSelectedItem().toString());
		vacinaAlterada.setQuantidadeDoses(Integer.valueOf(cbxDoses.getSelectedItem().toString()));
		controller.alterar(vacinaAlterada);
		String resultadoValidacao = controller.validarCampos(vacinaAlterada);
		if(resultadoValidacao!=null && !resultadoValidacao.isEmpty()) {
			if(respostaCadastro == 1) {
				setVisible(false);
				setVisible(false);
			} else {
				setVisible(false);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Alterado com sucesso");
		}
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
		
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataInicioPesquisaFormatada = formatador.format(vacina.getDataInicioPesquisa());
		
		this.txtDataInicio.setText(dataInicioPesquisaFormatada);
		this.cbxDoses.setSelectedItem(vacina.getQuantidadeDoses());
		this.cbxPais.setSelectedItem(vacina.getPaisOrigem());
	}

	private void limparCampos() {
		this.txtIntervalo.setText("");
		this.txtNomePesquisador.setText("");
		this.txtNomeVacina.setText("");
		this.txtDataInicio.setText("");
		this.cbxDoses.setSelectedIndex(0);	
		this.cbxPais.setSelectedIndex(0);
	
	}
    
	public String[] listarPaises(){
		String[] listaDePaises = new String[] {" --- Selecione --- ","Alb�nia", "Alemanha", "Andorra", "Angola", "Anguilla", "Ant�rtida", "Ant�gua e Barbuda", "Antilhas Holandesas",
		"Ar�bia Saudita", "Arg�lia", "Argentina", "Arm�nia", "Aruba", "Austr�lia", "�ustria", "Azerbaij�o", "Bahamas", "Bahrein", "Bangladesh", "Barbados",
		"Belarus", "B�lgica", "Belize", "Benin", "Bermudas", "Bol�via", "B�snia-Herzeg�vina", "Botsuana", "Brasil", "Brunei", "Bulg�ria", "Burkina Fasso",
		"Burundi", "But�o", "Cabo Verde", "Camar�es", "Camboja", "Canad�", "Cazaquist�o", "Chade", "Chile", "China", "Chipre", "Cingapura", "Col�mbia",
		"Congo", "Cor�ia do Norte", "Cor�ia do Sul", "Costa do Marfim", "Costa Rica", "Cro�cia (Hrvatska)", "Cuba", "Dinamarca", "Djibuti", "Dominica",
		"Egito", "El Salvador", "Emirados �rabes Unidos", "Equador", "Eritr�ia", "Eslov�quia", "Eslov�nia", "Espanha", "Estados Unidos","Est�nia", "Eti�pia",
		"Fiji", "Filipinas", "Finl�ndia", "Fran�a", "Gab�o", "G�mbia", "Gana", "Ge�rgia", "Gibraltar", "Gr�-Bretanha (Reino Unido, UK)", "Granada", "Gr�cia",
		"Groel�ndia", "Guadalupe", "Guam (Territ�rio dos Estados Unidos)", "Guatemala", "Guernsey", "Guiana", "Guiana Francesa", "Guin�", "Guin� Equatorial",
		"Guin�-Bissau", "Haiti", "Holanda", "Honduras", "Hong Kong", "Hungria", "I�men", "Ilha Bouvet (Territ�rio da Noruega)", "Ilha do Homem", "Ilha Natal",
		"Ilha Pitcairn", "Ilha Reuni�o", "Ilhas Aland", "Ilhas Cayman", "Ilhas Cocos", "Ilhas Comores", "Ilhas Cook", "Ilhas Faroes", "Ilhas Falkland (Malvinas)",
		"Ilhas Ge�rgia do Sul e Sandwich do Sul", "Ilhas Heard e McDonald (Territ�rio da Austr�lia)", "Ilhas Marianas do Norte", "Ilhas Marshall",
		"Ilhas Menores dos Estados Unidos", "Ilhas Norfolk", "Ilhas Seychelles", "Ilhas Solom�o", "Ilhas Svalbard e Jan Mayen", "Ilhas Tokelau", "Ilhas Turks e Caicos",
		"Ilhas Virgens (Estados Unidos)", "Ilhas Virgens (Inglaterra)", "Ilhas Wallis e Futuna", "�ndia", "Indon�sia", "Iraque", "Irlanda", "Isl�ndia", "Israel",
		"It�lia", "Jamaica", "Jap�o", "Jersey", "Jord�nia", "K�nia", "Kiribati", "Kuait", "Laos", "L�tvia", "Lesoto", "L�bano", "Lib�ria",
		"L�bia", "Liechtenstein", "Litu�nia", "Luxemburgo", "Macau", "Maced�nia (Rep�blica Yugoslava)", "Madagascar", "Mal�sia", "Malaui", "Maldivas",
		"Mali", "Malta", "Marrocos", "Martinica", "Maur�cio", "Maurit�nia", "Mayotte", "M�xico", "Micron�sia", "Mo�ambique", "Moldova", "M�naco","Mong�lia",
		"Montenegro", "Montserrat", "Myanma", "Nam�bia", "Nauru", "Nepal", "Nicar�gua", "N�ger", "Nig�ria", "Niue","Noruega", "Nova Caled�nia",
		"Nova Zel�ndia", "Om�", "Palau", "Panam�", "Papua-Nova Guin�", "Paquist�o", "Paraguai", "Peru", "Polin�sia Francesa", "Pol�nia", "Porto Rico",
		"Portugal", "Qatar", "Quirguist�o", "Rep�blica Centro-Africana", "Rep�blica Democr�tica do Congo", "Rep�blica Dominicana", "Rep�blica Tcheca","Rom�nia",
		"Ruanda", "R�ssia (antiga URSS) - Federa��o Russa", "Saara Ocidental", "Saint Vincente e Granadinas", "Samoa Americana", "Samoa Ocidental", "San Marino",
		"Santa Helena", "Santa L�cia", "S�o Bartolomeu", "S�o Crist�v�o e N�vis", "S�o Martim", "S�o Tom� e Pr�ncipe", "Senegal", "Serra Leoa", "S�rvia", "S�ria",
		"Som�lia", "Sri Lanka", "St. Pierre and Miquelon", "Suazil�ndia", "Sud�o", "Su�cia", "Su��a", "Suriname", "Tadjiquist�o", "Tail�ndia", "Taiwan",
		"Tanz�nia", "Territ�rios do Sul da Fran�a", "Territ�rios Palestinos Ocupados", "Timor Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tun�sia",
		"Turcomenist�o", "Turquia", "Tuvalu", "Ucr�nia", "Uganda", "Uzbequist�o", "Vanuatu", "Vaticano", "Venezuela","Vietn�","Z�mbia","Zimb�bue"};

		return listaDePaises;
		};
	
	public boolean validarNumero(JTextField numero) { 	
		long valor; 
		boolean resposta = false;
		String numeroString = numero.getText().toString();
		if (numero.getText().length() != 0){ 
			try { 
				valor = Integer.parseInt(numero.getText()); 
			}catch(NumberFormatException ex){ 
				JOptionPane.showMessageDialog(null, "Este campo aceita apenas números inteiros." ,"Informação",JOptionPane.INFORMATION_MESSAGE); 
				numero.grabFocus(); 
			}
//			numero.setText(numeroString.substring(numero.getText().length()));
		} else {
			resposta = true;
		}
		return resposta;
	}
}
