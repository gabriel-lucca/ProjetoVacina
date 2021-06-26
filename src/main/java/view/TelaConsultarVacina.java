package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ControladoraVacina;
import model.dao.PessoaDAO;
import model.dao.VacinaDAO;
import model.vo.PessoaVO;
import model.vo.VacinaVO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaConsultarVacina extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelo = new DefaultTableModel();
	private ControladoraVacina vController = new ControladoraVacina();
	private int respostaExclusao;
	Object[] opcoes = {"Sim", "Não"};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsultarVacina frame = new TelaConsultarVacina();
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
	public TelaConsultarVacina() {
		setTitle("Consultar Vacinas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1001, 405);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(33, 117, 942, 110);
		contentPane.add(scrollPane);
		
		table = new JTable(modelo);
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		
		modelo.addColumn("idVacina");
		modelo.addColumn("Nome pesquisador responsavel");
		modelo.addColumn("Pais Origem");
		modelo.addColumn("Nome Vacina");
		modelo.addColumn("Data inicio pesquisa");
		modelo.addColumn("Quantidade doses");
		modelo.addColumn("Intervalo doses");
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadastroVacina telaCadastroVacina = new TelaCadastroVacina();
				Integer idVacinaSelecionada = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
				VacinaVO vacinaSelecionada = vController.consultarPorId(idVacinaSelecionada);
				
				setVisible(false);
				telaCadastroVacina.setVisible(true);
				telaCadastroVacina.preencherCampos(vacinaSelecionada);
//				limparTabelaPessoa();
//				carregarTabela();
			}
		});
		btnAlterar.setBounds(280, 264, 210, 56);
		contentPane.add(btnAlterar);
		
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Integer idVacinaSelecionada = (Integer) table.getModel().getValueAt(table.getSelectedRow(), 0);
				respostaExclusao = JOptionPane.showOptionDialog(null, "\nDeseja excluir vacina?", "Confirmação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
				excluir(idVacinaSelecionada);
			}
		});
		btnExcluir.setBounds(500, 264, 210, 56);
		contentPane.add(btnExcluir);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparTabelaVacina();
				carregarTabela();
			}
		});
		btnConsultar.setBounds(410, 65, 174, 41);
		contentPane.add(btnConsultar);
	}
	
	public void excluir(Integer id) {
		if(respostaExclusao==0){
			if(vController.excluir(id)) {
				JOptionPane.showMessageDialog(null, "\nVacina excluída com sucesso");
				modelo.setRowCount(0);
				carregarTabela();
			} else {
				JOptionPane.showMessageDialog(null, "\nNão foi possível excluir vacina");
			}
		} else {
			
		}
	}
	
	public void carregarTabela() {	
		VacinaDAO vacina = new VacinaDAO();
		ArrayList<VacinaVO> list = vacina.consultarTodos();
			
		for (VacinaVO vac : list) {
			modelo.addRow(new Object[]{vac.getIdVacina(),vac.getNomePesquisadorResponsavel(),vac.getPaisOrigem(),
					vac.getNomeVacina(),vac.getDataInicioPesquisa(),vac.getQuantidadeDoses(),vac.getIntervaloDoses()});
		}
	}
	
	private void limparTabelaVacina() {
		modelo.setRowCount(0);		
	}
}
