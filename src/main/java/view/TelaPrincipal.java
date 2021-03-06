package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		setTitle("Tela principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 416);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		JButton btnCadastroPessoa = new JButton("Cadastro de pessoa");
		btnCadastroPessoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TelaCadastroPessoa telaCadastroPessoa = new TelaCadastroPessoa();
				telaCadastroPessoa.setVisible(true);
			}
		});
		btnCadastroPessoa.setForeground(Color.BLACK);
		btnCadastroPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCadastroPessoa.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnCadastroPessoa.setBackground(Color.CYAN);
		btnCadastroPessoa.setBounds(75, 65, 281, 76);
		contentPane.add(btnCadastroPessoa);
		
		JButton btnCadastroVacina = new JButton("Cadastro de vacina");
		btnCadastroVacina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TelaCadastroVacina telaCadastroVacina = new TelaCadastroVacina();
				telaCadastroVacina.setVisible(true);
			}
		});
		btnCadastroVacina.setForeground(Color.BLACK);
		btnCadastroVacina.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnCadastroVacina.setBackground(Color.CYAN);
		btnCadastroVacina.setBounds(75, 152, 281, 76);
		contentPane.add(btnCadastroVacina);
		
		JButton btnAplicacaoVacina = new JButton("Aplica????o Vacina");
		btnAplicacaoVacina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TelaAplicacaoVacina telaAplicacaoVacina = new TelaAplicacaoVacina();
				telaAplicacaoVacina.setVisible(true);
			}
		});
		btnAplicacaoVacina.setForeground(Color.BLACK);
		btnAplicacaoVacina.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnAplicacaoVacina.setBackground(Color.CYAN);
		btnAplicacaoVacina.setBounds(75, 239, 281, 79);
		contentPane.add(btnAplicacaoVacina);
		
		JButton btnConsultarVacina = new JButton("Consultar Vacina");
		btnConsultarVacina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TelaConsultarVacina telaConsultarVacina = new TelaConsultarVacina();
				telaConsultarVacina.setVisible(true);
			}
		});
		btnConsultarVacina.setForeground(Color.BLACK);
		btnConsultarVacina.setBackground(Color.CYAN);
		btnConsultarVacina.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnConsultarVacina.setBounds(377, 198, 148, 120);
		contentPane.add(btnConsultarVacina);
		
		JButton btnConsultarPessoa = new JButton("Consultar pessoa");
		btnConsultarPessoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TelaConsultarPessoa telaConsultarPessoa = new TelaConsultarPessoa();
				telaConsultarPessoa.setVisible(true);
			}
		});
		btnConsultarPessoa.setBackground(Color.CYAN);
		btnConsultarPessoa.setForeground(Color.BLACK);
		btnConsultarPessoa.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnConsultarPessoa.setBounds(377, 65, 148, 120);
		contentPane.add(btnConsultarPessoa);
	}
}
