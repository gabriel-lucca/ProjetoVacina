package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;

import util.Data;
import controller.ControladoraPessoa;
import exception.exception_pessoa.AnalisarCamposPessoaException;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
	private int respostaCadastro;
	private int respostaAlteracao;
	private int respostaExclusao;
	private JButton btnCadastrarPessoa;
	private JButton btnAlterar;
	private Object[] opcoes = { "Sim", "Não" };
	private boolean ativaBotao = true;
	private String cpfAntigo = null;
	private String resultadoValidData;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 656, 513);
		contentPane = new JPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (verificarCamposPreenchdidos()) {
					btnCadastrarPessoa.setEnabled(ativaBotao);
				} else {
					btnCadastrarPessoa.setEnabled(false);
				}
			}
		});
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(66, 53, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(200, 50, 347, 29);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(66, 99, 46, 14);
		contentPane.add(lblCpf);

		MaskFormatter mascaraTelefone;
		try {
			mascaraTelefone = new MaskFormatter("(##)#####-####");
			txtTelefone = new JFormattedTextField(mascaraTelefone);
		} catch (ParseException e1) {
		}
		txtTelefone.setColumns(10);
		txtTelefone.setBounds(200, 176, 347, 29);
		contentPane.add(txtTelefone);

		JLabel lblTelefone = new JLabel("Celular:");
		lblTelefone.setBounds(66, 183, 98, 14);
		contentPane.add(lblTelefone);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(200, 318, 347, 29);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(66, 325, 98, 14);
		contentPane.add(lblEndereo);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(66, 274, 46, 14);
		contentPane.add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setBounds(200, 267, 193, 29);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(420, 274, 46, 14);
		contentPane.add(lblEstado);

		// MaskFormatter mascaraDtNascimento;
		try {
			mascaraDtNascimento = new MaskFormatter("##/##/####");
			txtDataNascimento = new JFormattedTextField(mascaraDtNascimento);
			txtDataNascimento.addFocusListener(new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {
					if (txtDataNascimento.getText().equals("  /  /    ")) {
						JOptionPane.showMessageDialog(null, "O campo data deve ser preenchido", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);
						txtDataNascimento.requestFocus();
					} else {
						Data dtClass = new Data();
						ControladoraPessoa controller = new ControladoraPessoa();
						String[] data = txtDataNascimento.getText().toString().split("/");
						int dia = Integer.parseInt(data[0]);
						int mes = Integer.parseInt(data[1]);
						int ano = Integer.parseInt(data[2]);
						resultadoValidData = dtClass.validarData(dia, mes, ano);
					}
				}
			});
		} catch (ParseException e1) {
		}
		txtDataNascimento.setColumns(10);
		txtDataNascimento.setBounds(200, 218, 75, 29);
		contentPane.add(txtDataNascimento);

		JLabel lblDataDeNascimento = new JLabel("Data de nascimento:");
		lblDataDeNascimento.setBounds(66, 225, 136, 14);
		contentPane.add(lblDataDeNascimento);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(66, 141, 46, 14);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(200, 134, 347, 29);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		btnCadastrarPessoa = new JButton("Cadastrar pessoa");
		btnCadastrarPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] opcoes = { "Sim", "Não" };

				if (resultadoValidData.equals("DATA VÁLIDA.")) {

					try {
						cadastrar();
					} catch (AnalisarCamposPessoaException e) {
						JOptionPane.showMessageDialog(null, e);
					}
				} else {
					JOptionPane.showMessageDialog(null, resultadoValidData);
				}
			}
		});
		btnCadastrarPessoa.setEnabled(false);
		btnCadastrarPessoa.setBounds(392, 380, 155, 46);
		contentPane.add(btnCadastrarPessoa);

		MaskFormatter mascaraCPF;
		try {
			mascaraCPF = new MaskFormatter("###.###.###-##");
			txtCpf = new JFormattedTextField(mascaraCPF);
		} catch (ParseException e1) {
		}
		txtCpf.setColumns(10);
		txtCpf.setBounds(200, 92, 347, 29);
		contentPane.add(txtCpf);

		cbxEstado = new JComboBox();
		cbxEstado.setModel(new DefaultComboBoxModel(
				new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR",
						"PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "DF" }));
		cbxEstado.setBounds(472, 267, 75, 29);
		contentPane.add(cbxEstado);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (resultadoValidData.equals("DATA VÁLIDA.")) {
					try {
						alterar();
					} catch (AnalisarCamposPessoaException e) {
						JOptionPane.showMessageDialog(null, e);
					}
				} else {
					JOptionPane.showMessageDialog(null, resultadoValidData);
				}
			}
		});
		btnAlterar.setEnabled(false);
		btnAlterar.setBounds(234, 380, 148, 46);
		contentPane.add(btnAlterar);
	}

	public void cadastrar() throws AnalisarCamposPessoaException {
		// Instanciar uma nova vacina (de VacinaVO)
		// Preencher a nova vacina com todos os campos da tela
		PessoaVO novaPessoa = new PessoaVO();
		novaPessoa.setCidade(txtCidade.getText());
		novaPessoa.setCpf(txtCpf.getText());
		novaPessoa.setDataNascimento(LocalDate.parse(txtDataNascimento.getText(), dataFormatter));
		novaPessoa.setEmail(txtEmail.getText());
		novaPessoa.setEndereco(txtEndereco.getText());
		novaPessoa.setEstado(cbxEstado.getSelectedItem().toString());
		novaPessoa.setNome(txtNome.getText());
		novaPessoa.setTelefone(txtTelefone.getText());

		// Chamar o controller para cadastrar
		controller.cadastrar(novaPessoa);
		String resultadoValidacao = controller.validarCampos(novaPessoa);
		if (resultadoValidacao == null) {
			JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");
		}
		setVisible(false);
	}

	public void alterar() throws AnalisarCamposPessoaException {

		PessoaVO pessoaAlterada = new PessoaVO();
		pessoaAlterada.setCidade(txtCidade.getText());
		pessoaAlterada.setCpf(txtCpf.getText());
		pessoaAlterada.setDataNascimento(LocalDate.parse(txtDataNascimento.getText(), dataFormatter));
		pessoaAlterada.setEmail(txtEmail.getText());
		pessoaAlterada.setEndereco(txtEndereco.getText());
		pessoaAlterada.setEstado(cbxEstado.getSelectedItem().toString());
		pessoaAlterada.setNome(txtNome.getText());
		pessoaAlterada.setTelefone(txtTelefone.getText());
		pessoaAlterada.setIdPessoa(controller.consultarPorCpf(cpfAntigo).getIdPessoa());

		String resultadoValidacao = controller.validarCampos(pessoaAlterada);
		if (controller.alterar(pessoaAlterada)) {
			JOptionPane.showMessageDialog(null, "Alterado com sucesso");
		}

		setVisible(false);
	}

	public boolean verificarCamposPreenchdidos() {
		boolean resposta = false;
		if (!txtCidade.getText().isEmpty() && !txtCpf.getText().isEmpty() && !txtDataNascimento.getText().isEmpty()
				&& !txtEmail.getText().isEmpty() && !txtEndereco.getText().isEmpty() && !txtNome.getText().isEmpty()
				&& !txtTelefone.getText().isEmpty() && !cbxEstado.getSelectedItem().toString().isEmpty()) {
			resposta = true;
		}
		return resposta;
	}

	public void preencherCampos(PessoaVO pessoa) {
		this.txtEmail.setText(pessoa.getEmail());
		this.txtEndereco.setText(pessoa.getEndereco());
		this.txtCidade.setText(pessoa.getCidade());
		this.txtNome.setText(pessoa.getNome());

		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataNascimentoFormatada = formatador.format(pessoa.getDataNascimento());

		this.txtDataNascimento.setText(dataNascimentoFormatada);
		this.txtTelefone.setText(pessoa.getTelefone());
		this.txtCpf.setText(pessoa.getCpf());
		this.cpfAntigo = txtCpf.getText();
		this.cbxEstado.setSelectedItem(pessoa.getEstado());

		ativaBotao = false;
		btnAlterar.setEnabled(true);

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