/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

package aplicacaoSwing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import fachada.Fachada;
import modelo.Usuario;

public class TelaUsuario {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel lblEmail;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblSenha;
	private JButton btnEntrar;
	private Timer timer;

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
	public TelaUsuario() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});
		frame.setTitle("Usu\u00E1rios");
		frame.setBounds(100, 100, 443, 323);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 11, 339, 179);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.PINK);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"",""}
				));
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(44, 259, 409, 14);
		frame.getContentPane().add(label);

		lblEmail = new JLabel("Nome");
		lblEmail.setBounds(42, 201, 46, 14);
		frame.getContentPane().add(lblEmail);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(104, 198, 86, 20);
		frame.getContentPane().add(textField);

		passwordField = new JPasswordField();
		passwordField.setBounds(104, 226, 86, 20);
		frame.getContentPane().add(passwordField);

		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(42, 229, 46, 14);
		frame.getContentPane().add(lblSenha);

		btnEntrar = new JButton("Criar usuario");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = textField.getText();
					String senha = new String(passwordField.getPassword());
					Fachada.criarUsuario(nome, senha);
					label.setText("usuario criado");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}

			}
		});
		btnEntrar.setBounds(213, 211, 153, 23);
		frame.getContentPane().add(btnEntrar);

		timer = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				String s = formatador.format(new Date());
				frame.setTitle("Usuario  -- "+ s);
				frame.repaint();
				listagem();
			}
		});
		timer.setRepeats(true);
		timer.setDelay(8000);	//5 segundos
		timer.start();
		
		
		frame.setVisible(true);
	}

	public void listagem () {
		try{
			List<Usuario> lista = Fachada.listarUsuarios();
			
			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("Nome");
			model.addColumn("Ativo");
			for(Usuario u : lista)
					model.addRow(new String[]{ u.getNome(), u.ativo()+"" });

			table.setModel(model);
		}
		catch(Exception erro){
			JOptionPane.showMessageDialog(frame, erro.getMessage());
		}
	}
}
