package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.ControladoraPessoa;
import controller.ControladoraVacina;
import model.dao.PessoaDAO;
import model.vo.PessoaVO;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

public class TelaConsultarPessoa extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo = new DefaultTableModel();
	private ControladoraPessoa pController = new ControladoraPessoa();
	private int respostaExclusao;
	Object[] opcoes = {"Sim", "Não"};
	private JTextField txtNome;
	private JTextField txtDataNascimento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsultarPessoa frame = new TelaConsultarPessoa();
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
	public TelaConsultarPessoa() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1033, 634);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(25, 210, 956, 286);
		contentPane.add(scrollPane);

		table = new JTable(modelo);
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);

		modelo.addColumn("idPessoa");
		modelo.addColumn("Nome pessoa");
		modelo.addColumn("Cpf");
		modelo.addColumn("Email");
		modelo.addColumn("Telefone");
		modelo.addColumn("Data nascimento");
		modelo.addColumn("Cidade");
		modelo.addColumn("Estado");
		modelo.addColumn("Endereco");

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadastroPessoa telaCadastroPessoa = new TelaCadastroPessoa();				
				Integer idPessoaSelecionada = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
				PessoaVO pessoaSelecionada = pController.consultarPorId(idPessoaSelecionada);

				setVisible(false);
				telaCadastroPessoa.setVisible(true);
				telaCadastroPessoa.preencherCampos(pessoaSelecionada);
//				limparTabelaPessoa();
//				carregarTabela();
			}
		});
		btnAlterar.setBounds(289, 512, 210, 56);
		contentPane.add(btnAlterar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Integer idPessoaSelecionada = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
				respostaExclusao = JOptionPane.showOptionDialog(null, "\nDeseja excluir pessoa?", "Confirmação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
				excluir(idPessoaSelecionada);
			}
		});
		btnExcluir.setBounds(509, 512, 210, 56);
		contentPane.add(btnExcluir);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparTabelaPessoa();
				carregarTabela();
			}

		});
		btnConsultar.setBounds(421, 150, 149, 49);
		contentPane.add(btnConsultar);
		
		JLabel lblNomePessoa = new JLabel("Nome pessoa");
		lblNomePessoa.setBounds(115, 59, 129, 14);
		contentPane.add(lblNomePessoa);
		
		txtNome = new JTextField();
		txtNome.setBounds(115, 84, 188, 37);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblDataNascimento = new JLabel("Data nascimento");
		lblDataNascimento.setBounds(421, 59, 107, 14);
		contentPane.add(lblDataNascimento);
		
		MaskFormatter mascaraDtNascimento;
			try {
				mascaraDtNascimento = new MaskFormatter("##/##/####");
				txtDataNascimento = new JFormattedTextField(mascaraDtNascimento);
			} catch (ParseException e1) {
			}
			txtDataNascimento.setColumns(10);
		txtDataNascimento.setBounds(421, 84, 107, 37);
		contentPane.add(txtDataNascimento);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(671, 59, 87, 14);
		contentPane.add(lblCidade);
		
		JComboBox cbxCidades = new JComboBox();
		cbxCidades.setBounds(671, 84, 119, 37);
		contentPane.add(cbxCidades);
	}
	
	public void excluir(Integer id) {
		if(respostaExclusao==0){
			if(pController.excluir(id)) {
				JOptionPane.showMessageDialog(null, "\nPessoa excluída com sucesso");
				modelo.setRowCount(0);
				carregarTabela();
			} else {
				JOptionPane.showMessageDialog(null, "\nNão foi possível excluir pessoa");
			}
		} else {
			
		}
	}
	

	public void carregarTabela() {
		PessoaDAO pessoa = new PessoaDAO();
		ArrayList<PessoaVO> list = pessoa.buscarTodos();

		for (PessoaVO p : list) {
			modelo.addRow(new Object[] { p.getIdPessoa(), p.getNome(), p.getCpf(), p.getEmail(), p.getTelefone(),
					p.getDataNascimento(), p.getCidade(), p.getEstado(), p.getEndereco() });
		}
	}

	private void limparTabelaPessoa() {
		modelo.setRowCount(0);
	}
}
