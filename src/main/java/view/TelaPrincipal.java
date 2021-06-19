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
		setBounds(100, 100, 677, 526);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
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
		btnCadastroPessoa.setBounds(201, 86, 252, 76);
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
		btnCadastroVacina.setBounds(201, 194, 253, 76);
		contentPane.add(btnCadastroVacina);
		
		JButton btnAplicacaoVacina = new JButton("Aplica\u00E7\u00E3o Vacina");
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
		btnAplicacaoVacina.setBounds(201, 298, 252, 73);
		contentPane.add(btnAplicacaoVacina);
	}
}
