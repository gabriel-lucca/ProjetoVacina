package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import model.dao.PessoaDAO;
import model.vo.PessoaVO;
import seletor.FiltroPessoa;
import util.PlanilhaPessoa;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class TelaConsultarPessoa extends JFrame {

	private DefaultTableModel modelo = new DefaultTableModel();	
	JScrollPane scrollPane = new JScrollPane();
	private JPanel contentPane;
	private JTable table;
	private int respostaExclusao;
	private JTextField txtNome;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private ControladoraPessoa pController = new ControladoraPessoa();
	private Object[] opcoes = {"Sim", "Não"};
	private JTextField txtCidade;
	private JComboBox cbIdadeMinima;
	private JComboBox cbIdadeMaxima;
	private JComboBox cbOpcoesIdade;
	private JLabel lblFiltros;
	private JLabel lblIdadeMinima;
	private JLabel lblIdadeMaxima;
	private JButton btnLimparFiltros;
	private JButton btnGerarPlanilha;
	
	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
		setTitle("Consulta de pessoas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1033, 634);
		contentPane = new JPanel();
		
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(verificarFiltroPreenchido()) {
					
					btnLimparFiltros.setVisible(true);
					btnLimparFiltros.setEnabled(true);
				} else {
					
					btnLimparFiltros.setVisible(false);
					btnLimparFiltros.setEnabled(false);
				}
			}
		});
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		scrollPane.setEnabled(false);
		scrollPane.setBounds(25, 210, 970, 286);
		contentPane.add(scrollPane);
		
		table = new JTable(modelo);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if((Integer) table.getModel().getValueAt(table.getSelectedRow(), 0)>0) {
					btnAlterar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnLimpar.setEnabled(true);
					btnLimparFiltros.setEnabled(true);
					btnGerarPlanilha.setEnabled(true);
				} else {
					btnAlterar.setEnabled(false);
					btnExcluir.setEnabled(false);
					btnLimpar.setEnabled(false);
					btnLimparFiltros.setEnabled(false);
					btnGerarPlanilha.setEnabled(false);
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
				limparTabela();
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
		
		btnGerarPlanilha = new JButton("Gerar relatório");
		btnGerarPlanilha.setEnabled(false);
		btnGerarPlanilha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Salvar relatoório como...");
				ArrayList<PessoaVO> list = pController.consultarTodosRelatorio();
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
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setEnabled(false);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparTabela();
				btnLimpar.setEnabled(false);
				btnGerarPlanilha.setEnabled(false);
				btnAlterar.setEnabled(false);
				btnExcluir.setEnabled(false);
			}
		});
		btnLimpar.setEnabled(false);
		btnLimpar.setBounds(846, 150, 149, 49);
		contentPane.add(btnLimpar);
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(807, 84, 188, 37);
		contentPane.add(txtCidade);
		
		int limiteIdade = 150;
		String idades[] = new String[limiteIdade];
		for(int i = 0; i < limiteIdade; i++) {
			if(i==0) {
				idades[i] = "Selecione a idade";
			} else {
				idades[i] = String.valueOf(i);
			}
		}
		cbIdadeMinima = new JComboBox(idades);
		cbIdadeMinima.setBounds(434, 84, 149, 37);
		cbIdadeMinima.setEnabled(false);
		contentPane.add(cbIdadeMinima);
		
		cbIdadeMaxima = new JComboBox(idades);
		cbIdadeMaxima.setBounds(623, 84, 149, 37);
		cbIdadeMaxima.setEnabled(false);
		contentPane.add(cbIdadeMaxima);
		
		lblIdadeMinima = new JLabel("Idade mínima");
		lblIdadeMinima.setBounds(434, 59, 149, 14);
		contentPane.add(lblIdadeMinima);
		
		lblIdadeMaxima = new JLabel("Idade máxima");
		lblIdadeMaxima.setBounds(623, 59, 149, 14);
		contentPane.add(lblIdadeMaxima);
		
		lblFiltros = new JLabel("Filtros:");
		lblFiltros.setBounds(25, 21, 67, 14);
		contentPane.add(lblFiltros);
		
		String opcoes[] = {"Selecione uma opção", "Dias", "Mêses", "Anos"};
		cbOpcoesIdade = new JComboBox(opcoes);
		cbOpcoesIdade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String opcaoEscolhida = cbOpcoesIdade.getSelectedItem().toString();
				if(cbOpcoesIdade.getSelectedItem().toString() != "Selecione uma opção") {
					cbIdadeMinima.setEnabled(true);
					cbIdadeMaxima.setEnabled(true);
					lblIdadeMinima.setText("Idade mínima(em "+opcaoEscolhida+")");
					lblIdadeMaxima.setText("Idade máxima(em "+opcaoEscolhida+")");
				} else {
					cbIdadeMinima.setEnabled(false);
					cbIdadeMaxima.setEnabled(false);
				}
			}
		});	
		cbOpcoesIdade.setBounds(247, 84, 149, 37);
		cbOpcoesIdade.setVisible(true);
		contentPane.add(cbOpcoesIdade);
		
		JLabel lblOpesDeIdade = new JLabel("Opções para idade");
		lblOpesDeIdade.setBounds(247, 59, 149, 14);
		contentPane.add(lblOpesDeIdade);
		
		btnLimparFiltros = new JButton("Limpar filtros");
		btnLimparFiltros.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limparFiltros();
			}
		});
		btnLimparFiltros.setBounds(434, 10, 112, 37);
		contentPane.add(btnLimparFiltros);
		
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
			cbIdadeMinima.getSelectedItem().toString() != "Selecione a idade"||
			cbIdadeMaxima.getSelectedItem().toString() != "Selecione a idade" ||
			cbOpcoesIdade.getSelectedItem().toString() != "Selecione uma opção"||
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
			if(cbIdadeMinima.getSelectedItem().toString() != "Selecione a idade") {
				seletor.setIdadeMinima(Integer.parseInt(cbIdadeMinima.getSelectedItem().toString()));
			}
			if(cbIdadeMaxima.getSelectedItem().toString() != "Selecione a idade") {
				seletor.setIdadeMaxima(Integer.parseInt(cbIdadeMaxima.getSelectedItem().toString()));
			}
			if(cbOpcoesIdade.getSelectedItem().toString() !="Selecione uma opção") {
				seletor.setOpcao(cbOpcoesIdade.getSelectedItem().toString());
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
			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dataNascimentoFormatada = formatador.format(p.getDataNascimento());
			
			modelo.addRow(new Object[] { p.getIdPessoa(), p.getNome(), p.getCpf(), p.getEmail(), p.getTelefone(),
					dataNascimentoFormatada, p.getCidade(), p.getEstado(), p.getEndereco() });
					
					
		}
	}
	private void limparFiltros() {
		txtNome.setText("");
		cbIdadeMinima.setSelectedItem("Selecione a idade");
		cbIdadeMaxima.setSelectedItem("Selecione a idade");
		cbOpcoesIdade.setSelectedItem("Selecione uma opção");
		txtCidade.setText("");
	}
	private void limparTabela() {
		modelo.setRowCount(0);
	}
}
