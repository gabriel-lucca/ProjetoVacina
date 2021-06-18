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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 526);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCadastroPessoa = new JButton("        Cadastro de pessoa");
		btnCadastroPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCadastroPessoa.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnCadastroPessoa.setBackground(new Color(0, 51, 204));
		btnCadastroPessoa.setBounds(201, 86, 252, 76);
		contentPane.add(btnCadastroPessoa);
		
		JButton btnCadastroVacina = new JButton("Cadastro de vacina");
		btnCadastroVacina.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnCadastroVacina.setBackground(new Color(0, 51, 204));
		btnCadastroVacina.setBounds(201, 194, 253, 76);
		contentPane.add(btnCadastroVacina);
		
		JButton btnAcessarRanking = new JButton("Acessar Ranking");
		btnAcessarRanking.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnAcessarRanking.setBackground(new Color(0, 51, 204));
		btnAcessarRanking.setBounds(201, 298, 252, 73);
		contentPane.add(btnAcessarRanking);
	}
}
