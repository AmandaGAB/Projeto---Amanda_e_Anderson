/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

package aplicacaoSwing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fachada.Fachada;

public class TelaLogin {
	private JFrame frame;
	private JLabel label;
	private JLabel lblEmail;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblSenha;
	private JButton btnEntrar;

	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaListarReuniao window = new TelaListarReuniao();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login");
		frame.setBounds(100, 100, 346, 145);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(10, 80, 310, 14);
		frame.getContentPane().add(label);

		lblEmail = new JLabel("Nome");
		lblEmail.setBounds(25, 24, 46, 14);
		frame.getContentPane().add(lblEmail);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(87, 21, 86, 20);
		frame.getContentPane().add(textField);

		passwordField = new JPasswordField();
		passwordField.setBounds(87, 49, 86, 20);
		frame.getContentPane().add(passwordField);

		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(25, 52, 46, 14);
		frame.getContentPane().add(lblSenha);

		btnEntrar = new JButton("login");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String nome = textField.getText();
					String senha = new String(passwordField.getPassword());
					Fachada.login(nome, senha);
					label.setText("usuario logado com sucesso: "+Fachada.getLogado().getNome());
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}

			}
		});
		btnEntrar.setBounds(206, 35, 72, 23);
		frame.getContentPane().add(btnEntrar);

		
		frame.setVisible(true);
	}

}
