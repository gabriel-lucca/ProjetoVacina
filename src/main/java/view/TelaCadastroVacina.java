package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.ControladoraVacina;
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

public class TelaCadastroVacina extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeVacina;
	private JTextField txtNomePesquisador;
	private JTextField txtIntervalo; 
	private JTextField txtDataInicio;
	private JComboBox cbxDoses;
	private JComboBox cbxPais;
	private ControladoraVacina controller = new ControladoraVacina();
	private MaskFormatter mascaraDataInicio;
	
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 656, 486);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPasDeOrigem = new JLabel("Pa\u00EDs de origem:");
		lblPasDeOrigem.setFont(new Font("Dialog", Font.BOLD, 13));
		lblPasDeOrigem.setBounds(30, 101, 175, 18);
		contentPane.add(lblPasDeOrigem);
		
		txtNomeVacina = new JTextField();
		txtNomeVacina.setBounds(265, 129, 310, 35);
		contentPane.add(txtNomeVacina);
		txtNomeVacina.setColumns(10);
		
		JLabel lblNomeDaVacina = new JLabel("Nome da vacina:");
		lblNomeDaVacina.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNomeDaVacina.setBounds(30, 139, 175, 14);
		contentPane.add(lblNomeDaVacina);
		
		JLabel lblDataIncioE = new JLabel("Data in\u00EDcio pesquisa:");
		lblDataIncioE.setFont(new Font("Dialog", Font.BOLD, 13));
		lblDataIncioE.setBounds(265, 192, 137, 14);
		contentPane.add(lblDataIncioE);
		
//		MaskFormatter mascaraDataInicio;
		try {
			mascaraDataInicio = new MaskFormatter("##/##/####");
			txtDataInicio = new JFormattedTextField(mascaraDataInicio);
		} catch (ParseException e1) {
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
		
		JButton btnCadastrarVacina = new JButton("Cadastrar vacina");
		btnCadastrarVacina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cadastrar();
			}
		});
		btnCadastrarVacina.setBounds(422, 331, 153, 47);
		contentPane.add(btnCadastrarVacina);
		
		JLabel lblIntervalo = new JLabel("Intervalo entre as doses (em dias):");
		lblIntervalo.setFont(new Font("Dialog", Font.BOLD, 13));
		lblIntervalo.setBounds(265, 278, 224, 14);
		contentPane.add(lblIntervalo);
		
		txtIntervalo = new JTextField();
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
		cbxPais.setModel(new DefaultComboBoxModel(new String[] {"                                   Selecione o pa\u00EDs     ", "Alb\u00E2nia", "Alemanha", "Andorra", "Angola", "Anguilla", "Ant\u00E1rtida", "Ant\u00EDgua e Barbuda", "Antilhas Holandesas", "Ar\u00E1bia Saudita", "Arg\u00E9lia", "Argentina", "Arm\u00EAnia", "Aruba", "Austr\u00E1lia", "\u00C1ustria", "Azerbaij\u00E3o", "Bahamas", "Bahrein", "Bangladesh", "Barbados", "Belarus", "B\u00E9lgica", "Belize", "Benin", "Bermudas", "Bol\u00EDvia", "B\u00F3snia-Herzeg\u00F3vina", "Botsuana", "Brasil", "Brunei", "Bulg\u00E1ria", "Burkina Fasso", "Burundi", "But\u00E3o", "Cabo Verde", "Camar\u00F5es", "Camboja", "Canad\u00E1", "Cazaquist\u00E3o", "Chade", "Chile", "China", "Chipre", "Cingapura", "Col\u00F4mbia", "Congo", "Cor\u00E9ia do Norte", "Cor\u00E9ia do Sul", "Costa do Marfim", "Costa Rica", "Cro\u00E1cia (Hrvatska)", "Cuba", "Dinamarca", "Djibuti", "Dominica", "Egito", "El Salvador", "Emirados \u00C1rabes Unidos", "Equador", "Eritr\u00E9ia", "Eslov\u00E1quia", "Eslov\u00EAnia", "Espanha", "Estados Unidos", "Est\u00F4nia", "Eti\u00F3pia", "Fiji", "Filipinas", "Finl\u00E2ndia", "Fran\u00E7a", "Gab\u00E3o", "G\u00E2mbia", "Gana", "Ge\u00F3rgia", "Gibraltar", "Gr\u00E3-Bretanha (Reino Unido, UK)", "Granada", "Gr\u00E9cia", "Groel\u00E2ndia", "Guadalupe", "Guam (Territ\u00F3rio dos Estados Unidos)", "Guatemala", "Guernsey", "Guiana", "Guiana Francesa", "Guin\u00E9", "Guin\u00E9 Equatorial", "Guin\u00E9-Bissau", "Haiti", "Holanda", "Honduras", "Hong Kong", "Hungria", "I\u00EAmen", "Ilha Bouvet (Territ\u00F3rio da Noruega)", "Ilha do Homem", "Ilha Natal", "Ilha Pitcairn", "Ilha Reuni\u00E3o", "Ilhas Aland", "Ilhas Cayman", "Ilhas Cocos", "Ilhas Comores", "Ilhas Cook", "Ilhas Faroes", "Ilhas Falkland (Malvinas)", "Ilhas Ge\u00F3rgia do Sul e Sandwich do Sul", "Ilhas Heard e McDonald (Territ\u00F3rio da Austr\u00E1lia)", "Ilhas Marianas do Norte", "Ilhas Marshall", "Ilhas Menores dos Estados Unidos", "Ilhas Norfolk", "Ilhas Seychelles", "Ilhas Solom\u00E3o", "Ilhas Svalbard e Jan Mayen", "Ilhas Tokelau", "Ilhas Turks e Caicos", "Ilhas Virgens (Estados Unidos)", "Ilhas Virgens (Inglaterra)", "Ilhas Wallis e Futuna", "\u00EDndia", "Indon\u00E9sia", "Iraque", "Irlanda", "Isl\u00E2ndia", "Israel", "It\u00E1lia", "Jamaica", "Jap\u00E3o", "Jersey", "Jord\u00E2nia", "K\u00EAnia", "Kiribati", "Kuait", "Laos", "L\u00E1tvia", "Lesoto", "L\u00EDbano", "Lib\u00E9ria", "L\u00EDbia", "Liechtenstein", "Litu\u00E2nia", "Luxemburgo", "Macau", "Maced\u00F4nia (Rep\u00FAblica Yugoslava)", "Madagascar", "Mal\u00E1sia", "Malaui", "Maldivas", "Mali", "Malta", "Marrocos", "Martinica", "Maur\u00EDcio", "Maurit\u00E2nia", "Mayotte", "M\u00E9xico", "Micron\u00E9sia", "Mo\u00E7ambique", "Moldova", "M\u00F4naco", "Mong\u00F3lia", "Montenegro", "Montserrat", "Myanma", "Nam\u00EDbia", "Nauru", "Nepal", "Nicar\u00E1gua", "N\u00EDger", "Nig\u00E9ria", "Niue", "Noruega", "Nova Caled\u00F4nia", "Nova Zel\u00E2ndia", "Om\u00E3", "Palau", "Panam\u00E1", "Papua-Nova Guin\u00E9", "Paquist\u00E3o", "Paraguai", "Peru", "Polin\u00E9sia Francesa", "Pol\u00F4nia", "Porto Rico", "Portugal", "Qatar", "Quirguist\u00E3o", "Reino Unido", "Rep\u00FAblica Centro-Africana", "Rep\u00FAblica Democr\u00E1tica do Congo", "Rep\u00FAblica Dominicana", "Rep\u00FAblica Tcheca", "Rom\u00EAnia", "Ruanda", "R\u00FAssia (antiga URSS) - Federa\u00E7\u00E3o Russa", "Saara Ocidental", "Saint Vincente e Granadinas", "Samoa Americana", "Samoa Ocidental", "San Marino", "Santa Helena", "Santa L\u00FAcia", "S\u00E3o Bartolomeu", "S\u00E3o Crist\u00F3v\u00E3o e N\u00E9vis", "S\u00E3o Martim", "S\u00E3o Tom\u00E9 e Pr\u00EDncipe", "Senegal", "Serra Leoa", "S\u00E9rvia", "S\u00EDria", "Som\u00E1lia", "Sri Lanka", "St. Pierre and Miquelon", "Suazil\u00E2ndia", "Sud\u00E3o", "Su\u00E9cia", "Su\u00ED\u00E7a", "Suriname", "Tadjiquist\u00E3o", "Tail\u00E2ndia", "Taiwan", "Tanz\u00E2nia", "Territ\u00F3rios do Sul da Fran\u00E7a", "Territ\u00F3rios Palestinos Ocupados", "Timor Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tun\u00EDsia", "Turcomenist\u00E3o", "Turquia", "Tuvalu", "Ucr\u00E2nia", "Uganda", "Uzbequist\u00E3o", "Vanuatu", "Vaticano", "Venezuela", "Vietn\u00E3", "Z\u00E2mbia", "Zimb\u00E1bue"}));
		cbxPais.setBounds(265, 88, 310, 33);
		contentPane.add(cbxPais);
	}
	public void cadastrar() {
		//TODO desenvolver 
		
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
		limparCampos();
		System.out.println(novaVacina);
		int resposta = JOptionPane.showConfirmDialog(null, null, "Deseja corrigir as informações?", JOptionPane.OK_CANCEL_OPTION);
		if(resposta == JOptionPane.OK_OPTION) {
			
			preencherCampos(novaVacina);
			
			VacinaVO vacinaAlterada = new VacinaVO();
			
			vacinaAlterada.setDataInicioPesquisa(LocalDate.parse(txtDataInicio.getText(), dataFormatter));
			vacinaAlterada.setIntervaloDoses(Integer.valueOf(txtIntervalo.getText()));
			vacinaAlterada.setNomePesquisadorResponsavel(txtNomePesquisador.getText());
			vacinaAlterada.setNomeVacina(txtNomeVacina.getText());
			vacinaAlterada.setPaisOrigem(cbxPais.getSelectedItem().toString());
			vacinaAlterada.setQuantidadeDoses(Integer.valueOf(cbxDoses.getSelectedItem().toString()));
			
			controller.alteraInformacoes(vacinaAlterada);
			
		} else {
			TelaPrincipal telaPrincipal = new TelaPrincipal();
			telaPrincipal.setVisible(true);
			this.dispose();
		}
		
		int confirma = JOptionPane.showConfirmDialog(null, "Deseja ir para Aplicação vacina?", "Deseja confirmar?", JOptionPane.OK_CANCEL_OPTION);
		if(confirma == JOptionPane.OK_OPTION) {
			this.dispose();
			TelaAplicacaoVacina telaAplicacaoVacina = new TelaAplicacaoVacina();
			telaAplicacaoVacina.setVisible(true);
			
		} else {
			TelaCadastroVacina telaCadastroVacina = new TelaCadastroVacina();
			telaCadastroVacina.setVisible(true);
		}
		
	}
	
	private void preencherCampos(VacinaVO novaVacina) {
		this.txtIntervalo.setText(String.valueOf(novaVacina.getIntervaloDoses()));
		this.txtNomePesquisador.setText(novaVacina.getNomePesquisadorResponsavel());
		this.txtNomeVacina.setText(novaVacina.getNomeVacina());
		this.txtDataInicio.setText(String.valueOf(novaVacina.getDataInicioPesquisa()));
		this.cbxDoses.setSelectedIndex(0);	
		this.cbxPais.setSelectedIndex(0);
	}

	private void limparCampos() {
		this.txtIntervalo.setText("");
		this.txtNomePesquisador.setText("");
		this.txtNomeVacina.setText("");
	//	this.txtDataInicio.setText("");
		this.cbxDoses.setSelectedIndex(0);	
		this.cbxPais.setSelectedIndex(0);
	
	}

	public String[] ListarPaises(){
		String[] listaDePaises = new String[] {" --- Selecione --- ","Albânia", "Alemanha", "Andorra", "Angola", "Anguilla", "Antártida", "Antígua e Barbuda", "Antilhas Holandesas",
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
		"Ilhas Virgens (Estados Unidos)", "Ilhas Virgens (Inglaterra)", "Ilhas Wallis e Futuna", "índia", "Indonésia", "Iraque", "Irlanda", "Islândia", "Israel",
		"Itália", "Jamaica", "Japão", "Jersey", "Jordânia", "Kênia", "Kiribati", "Kuait", "Laos", "Látvia", "Lesoto", "Líbano", "Libéria",
		"Líbia", "Liechtenstein", "Lituânia", "Luxemburgo", "Macau", "Macedônia (República Yugoslava)", "Madagascar", "Malásia", "Malaui", "Maldivas",
		"Mali", "Malta", "Marrocos", "Martinica", "Maurício", "Mauritânia", "Mayotte", "México", "Micronésia", "Moçambique", "Moldova", "Mônaco","Mongólia",
		"Montenegro", "Montserrat", "Myanma", "Namíbia", "Nauru", "Nepal", "Nicarágua", "Níger", "Nigéria", "Niue","Noruega", "Nova Caledônia",
		"Nova Zelândia", "Omã", "Palau", "Panamá", "Papua-Nova Guiné", "Paquistão", "Paraguai", "Peru", "Polinésia Francesa", "Polônia", "Porto Rico",
		"Portugal", "Qatar", "Quirguistão", "República Centro-Africana", "República Democrática do Congo", "República Dominicana", "República Tcheca","Romênia",
		"Ruanda", "Rússia (antiga URSS) - Federação Russa", "Saara Ocidental", "Saint Vincente e Granadinas", "Samoa Americana", "Samoa Ocidental", "San Marino",
		"Santa Helena", "Santa Lúcia", "São Bartolomeu", "São Cristóvão e Névis", "São Martim", "São Tomé e Príncipe", "Senegal", "Serra Leoa", "Sérvia", "Síria",
		"Somália", "Sri Lanka", "St. Pierre and Miquelon", "Suazilândia", "Sudão", "Suécia", "Suíça", "Suriname", "Tadjiquistão", "Tailândia", "Taiwan",
		"Tanzânia", "Territórios do Sul da França", "Territórios Palestinos Ocupados", "Timor Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunísia",
		"Turcomenistão", "Turquia", "Tuvalu", "Ucrânia", "Uganda", "Uzbequistão", "Vanuatu", "Vaticano", "Venezuela","Vietnã","Zâmbia","Zimbábue"};

		return listaDePaises;
		};
	
	public void validarNumero(JTextField Numero) { 
		long valor; 
		if (Numero.getText().length() != 0){ 
			try { 
				valor = Long.parseLong(Numero.getText()); 
			}catch(NumberFormatException ex){ 
				JOptionPane.showMessageDialog(null, "Esse Campo só aceita números" ,"Informação",JOptionPane.INFORMATION_MESSAGE); 
				Numero.grabFocus(); 
			} 
		} 
	} 
}
