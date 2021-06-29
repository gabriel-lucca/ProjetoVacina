package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import exception.exception_pessoa.AnalisarCamposPessoaException;
import model.dao.PessoaDAO;
import model.vo.PessoaVO;
import seletor.FiltroPessoa;
import util.PlanilhaPessoa;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaConsultarPessoa extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo = new DefaultTableModel();	
	private int respostaExclusao;
	private JTextField txtNome;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnCancelar;
	private ControladoraPessoa pController = new ControladoraPessoa();
	MaskFormatter mascaraDtNascimento;
	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	Object[] opcoes = {"Sim", "Não"};
	private JTextField txtCidade;
	private JTextField txtIdadeMinima;
	private JTextField txtIdadeMaxima;
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
		scrollPane.setBounds(25, 210, 970, 286);
		contentPane.add(scrollPane);
		
		table = new JTable(modelo);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(table.getSelectedRow());
				if((Integer) table.getModel().getValueAt(table.getSelectedRow(), 0)>0) {
					btnAlterar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnCancelar.setVisible(true);
					btnCancelar.setEnabled(true);
				} else {
					btnAlterar.setEnabled(false);
					btnExcluir.setEnabled(false);
					btnCancelar.setVisible(false);
					btnCancelar.setEnabled(false);
				}
			}
		});
		
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
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setEnabled(false);
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

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
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
		lblNomePessoa.setBounds(25, 59, 129, 14);
		contentPane.add(lblNomePessoa);
		
		txtNome = new JTextField();
		txtNome.setBounds(25, 84, 188, 37);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(807, 59, 87, 14);
		contentPane.add(lblCidade);
		
		JButton btnGerarPlanilha = new JButton("Gerar relatório");
		btnGerarPlanilha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Salvar relatoório como...");
				ArrayList<PessoaVO> list = pController.consultarTodos();
				int resultado = jfc.showSaveDialog(null);
				if(resultado == JFileChooser.APPROVE_OPTION) {
					String caminhoEscolhido = jfc.getSelectedFile().getAbsolutePath();
					PlanilhaPessoa planilha = new PlanilhaPessoa();
					planilha.gerarPlanilhaPessoas(caminhoEscolhido, list);
					JOptionPane.showMessageDialog(null, "Salvo com sucesso.");
				}
			}
		});
		btnGerarPlanilha.setBounds(25, 150, 149, 49);
		contentPane.add(btnGerarPlanilha);
		
		btnCancelar = new JButton("Limpar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limparTabelaPessoa();
				btnCancelar.setEnabled(false);
				btnCancelar.setVisible(false);
			}
		});
		btnCancelar.setEnabled(false);
		btnCancelar.setVisible(false);
		btnCancelar.setBounds(846, 150, 149, 49);
		contentPane.add(btnCancelar);
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(807, 84, 188, 37);
		contentPane.add(txtCidade);
		
		txtIdadeMinima = new JTextField();
		txtIdadeMinima.setColumns(10);
		txtIdadeMinima.setBounds(372, 84, 95, 37);
		contentPane.add(txtIdadeMinima);
		
		txtIdadeMaxima = new JTextField();
		txtIdadeMaxima.setColumns(10);
		txtIdadeMaxima.setBounds(509, 84, 95, 37);
		contentPane.add(txtIdadeMaxima);
		
		JLabel lblIdadeMnima = new JLabel("Idade mínima");
		lblIdadeMnima.setBounds(372, 59, 95, 14);
		contentPane.add(lblIdadeMnima);
		
		JLabel lblIdadeMxima = new JLabel("Idade máxima");
		lblIdadeMxima.setBounds(509, 59, 95, 14);
		contentPane.add(lblIdadeMxima);
		
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
	
	public boolean verificarFiltroPreenchido() {
		boolean resposta = false;
		if(txtNome.getText().toString().length()!=0 ||
			!txtIdadeMinima.getText().isEmpty() ||
			!txtIdadeMaxima.getText().isEmpty() ||
			txtCidade.getText().toString().length()!=0) {
			resposta = true;
		}
		return resposta;
	}
	public void carregarTabela() {
		PessoaDAO pessoa = new PessoaDAO();
		ArrayList<PessoaVO> list = new ArrayList<PessoaVO>();
		FiltroPessoa seletor = new FiltroPessoa();
		
		if(verificarFiltroPreenchido()) {		
			if(txtNome.getText().toString().length()!=0) {
				seletor.setNome(txtNome.getText().toString());
			}
			if(!txtIdadeMinima.getText().isEmpty()) {
				seletor.setIdadeMinima(Integer.parseInt(txtIdadeMinima.getText()));
			}
			if(!txtIdadeMaxima.getText().isEmpty()) {
				seletor.setIdadeMaxima(Integer.parseInt(txtIdadeMinima.getText()));
			}
			if(txtCidade!=null) {
				seletor.setCidade(txtCidade.getText());
			}
			list = pController.consultarComFiltro(seletor);
			String mensagem = "";
			if(list.size()>0) {
				mensagem = list.size()+" resultados encontrados";
			} else {
				mensagem = "Nenhum resultado encontrado";
			}
			JOptionPane.showMessageDialog(null, mensagem);
		} else {
			list = pController.consultarTodos();
		}		
		for (PessoaVO p : list) {
			modelo.addRow(new Object[] { p.getIdPessoa(), p.getNome(), p.getCpf(), p.getEmail(), p.getTelefone(),
					p.getDataNascimento(), p.getCidade(), p.getEstado(), p.getEndereco() });
		}
	}
	private void limparTabelaPessoa() {
		modelo.setRowCount(0);
	}
}
