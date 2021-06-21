package view;

import java.awt.BorderLayout; 
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.ControladoraPessoa; 
import model.vo.PessoaVO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroPessoa extends JFrame {
	
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtTelefone;
	private JTextField txtEndereco;
	private JTextField txtCidade;
	private JTextField txtEmail;
	private JTextField txtCpf;
	private JTextField txtDataNascimento;
	private JComboBox cbxEstado;
	private ControladoraPessoa controller = new ControladoraPessoa();
	private MaskFormatter mascaraDtNascimento;



	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroPessoa frame = new TelaCadastroPessoa();
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
	public TelaCadastroPessoa() {
		setTitle("Cadastro de pessoa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 622, 495);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(42, 53, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(176, 50, 347, 29);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblCpf = new JLabel("Cpf:");
		lblCpf.setBounds(42, 99, 46, 14);
		contentPane.add(lblCpf);
		
		MaskFormatter mascaraTelefone;
		try {
			mascaraTelefone = new MaskFormatter("(##)#####-####");
			txtTelefone = new JFormattedTextField(mascaraTelefone);
		} catch (ParseException e1) {
		}
		txtTelefone.setColumns(10);
		txtTelefone.setBounds(176, 176, 347, 29);
		contentPane.add(txtTelefone);
		
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(42, 183, 98, 14);
		contentPane.add(lblTelefone);
		
		
		txtEndereco = new JTextField();
		txtEndereco.setBounds(176, 318, 347, 29);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(42, 325, 98, 14);
		contentPane.add(lblEndereo);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(42, 274, 46, 14);
		contentPane.add(lblCidade);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(176, 267, 168, 29);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(396, 274, 46, 14);
		contentPane.add(lblEstado);
		
	//	MaskFormatter mascaraDtNascimento;
		try {
			mascaraDtNascimento = new MaskFormatter("##/##/####");
			txtDataNascimento = new JFormattedTextField(mascaraDtNascimento);
		} catch (ParseException e1) {
		}
		txtDataNascimento.setColumns(10);
		txtDataNascimento.setBounds(176, 218, 75, 29);
		contentPane.add(txtDataNascimento);
		
		JLabel lblDataDeNascimento = new JLabel("Data de nascimento:");
		lblDataDeNascimento.setBounds(42, 225, 136, 14);
		contentPane.add(lblDataDeNascimento);
		
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(42, 141, 46, 14);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(176, 134, 347, 29);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
				
		JButton btnCadastrarPessoa = new JButton("Cadastrar pessoa");
		btnCadastrarPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCadastrarPessoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cadastrar();
			}
		});
		btnCadastrarPessoa.setBounds(368, 380, 155, 46);
		contentPane.add(btnCadastrarPessoa);
		
		MaskFormatter mascaraCPF;
		try {
			mascaraCPF = new MaskFormatter("###.###.###-##");
			txtCpf = new JFormattedTextField(mascaraCPF);
		} catch (ParseException e1) {
		}
		txtCpf.setColumns(10);
		txtCpf.setBounds(176, 92, 347, 29);
		contentPane.add(txtCpf);
		
		
		
		
		cbxEstado = new JComboBox();
		cbxEstado.setModel(new DefaultComboBoxModel(new String[] {"AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "DF"}));
		cbxEstado.setBounds(448, 267, 75, 29);
		contentPane.add(cbxEstado);
		}
		
		public void cadastrar() {
			//TODO desenvolver 
			
			//Instanciar uma nova vacina (de VacinaVO)
			//Preencher a nova vacina com todos os campos da tela
			PessoaVO novaPessoa = new PessoaVO();
			novaPessoa.setCidade(txtCidade.getText());
			novaPessoa.setCpf(txtCpf.getText());
			novaPessoa.setDataNascimento(LocalDate.parse(txtDataNascimento.getText(), dataFormatter));
			novaPessoa.setEmail(txtEmail.getText());
			novaPessoa.setEndereco(txtEndereco.getText());
			novaPessoa.setEstado(cbxEstado.getSelectedItem().toString());
			novaPessoa.setNome(txtNome.getText());
			novaPessoa.setTelefone(txtTelefone.getText());
			
			//Chamar o controller para cadastrar
			controller.cadastrar(novaPessoa);
			limparCampos();
			System.out.println(novaPessoa);
			int resposta = JOptionPane.showConfirmDialog(null, null, "Deseja corrigir as informações?", JOptionPane.OK_CANCEL_OPTION);
			if(resposta == JOptionPane.OK_OPTION) {

				preencherCampos(novaPessoa);	
				
				
				PessoaVO pessoaAlterada = new PessoaVO();
				pessoaAlterada.setCidade(txtCidade.getText());
				pessoaAlterada.setCpf(txtCpf.getText());
				pessoaAlterada.setDataNascimento(LocalDate.parse(txtDataNascimento.getText(), dataFormatter));
				pessoaAlterada.setEmail(txtEmail.getText());
				pessoaAlterada.setEndereco(txtEndereco.getText());
				pessoaAlterada.setEstado(cbxEstado.getSelectedItem().toString());
				pessoaAlterada.setNome(txtNome.getText());
				pessoaAlterada.setTelefone(txtTelefone.getText());
				
				controller.alteraInformacoes(pessoaAlterada);
				
			} else {
				TelaPrincipal telaPrincipal = new TelaPrincipal();
				telaPrincipal.setVisible(true);
				this.dispose();
			}
			int confirma = JOptionPane.showConfirmDialog(null, "Deseja ir cadastrar vacina?", "Deseja confirmar?", JOptionPane.OK_CANCEL_OPTION);
			if(confirma == JOptionPane.OK_OPTION) {
				this.dispose();
				TelaCadastroVacina telaCadastroVacina = new TelaCadastroVacina();
				telaCadastroVacina.setVisible(true);
				
			} else {
				TelaCadastroPessoa telaCadastroPessoa = new TelaCadastroPessoa();
				telaCadastroPessoa.setVisible(true);
			}
			
		}
		public void preencherCampos(PessoaVO novaPessoa) {
			this.txtEmail.setText(novaPessoa.getEmail());
			this.txtEndereco.setText(novaPessoa.getEndereco());
			this.txtCidade.setText(novaPessoa.getCidade());
			this.txtNome.setText(novaPessoa.getNome());
			this.txtDataNascimento.setText(String.valueOf(novaPessoa.getDataNascimento()));
			this.txtTelefone.setText(novaPessoa.getTelefone());
			this.txtCpf.setText(novaPessoa.getCpf());
			this.cbxEstado.setSelectedIndex(0);	
			
			
		}
		
		private void limparCampos() {
			this.txtEmail.setText("");
			this.txtEndereco.setText("");
			this.txtCidade.setText("");
			this.txtNome.setText("");
//			try {
//				mascaraDtNascimento = new MaskFormatter("##/##/####");
//				txtDataNascimento = new JFormattedTextField(mascaraDtNascimento);
//			} catch (ParseException e1) {
//			}
			this.txtTelefone.setText("");
			this.txtCpf.setText("");
			this.cbxEstado.setSelectedIndex(0);
			
		}
	}
