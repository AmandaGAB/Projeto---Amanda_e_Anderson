/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

package aplicacaoSwing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import fachada.Fachada;
import modelo.Mensagem;

public class TelaMensagem {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_4;
	private JLabel label;
	private JTextField textField;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JPanel panel;
	private ButtonGroup grupobotoes;

	private Timer timer;
	private JRadioButton radioButton_2;
	private JTextField textField_2;


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
	public TelaMensagem() {
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
		frame.setTitle("Mensagens");
		frame.setBounds(100, 100, 776, 411);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 53, 715, 179);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"", "", "", ""}
				));
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		button_4 = new JButton("Apagar mensagem");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					if (table.getSelectedRow() < 0)
						JOptionPane.showMessageDialog(null, "selecione uma linha");
					else {
						//pegar o id na linha selecionada
						String id = (String) table.getValueAt( table.getSelectedRow(), 0); //0=nome

						Object[] options = { "Confirmar", "Cancelar" };
						int escolha = JOptionPane.showOptionDialog(null, "Esta operação apagará a mensagem", "Alerta",
								JOptionPane.DEFAULT_OPTION, 
								JOptionPane.WARNING_MESSAGE, null, options, options[1]);
						if(escolha == 0) {
							Fachada.apagarMensagens(Integer.parseInt(id));
							label.setText("exclusão realizada"); 
						}
						else
							label.setText("exclusão cancelada"); 
					}
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_4.setBounds(24, 277, 176, 23);
		frame.getContentPane().add(button_4);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 347, 718, 14);
		frame.getContentPane().add(label);

		button = new JButton("Criar mensagem");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Fachada.criarMensagem(textField.getText());
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(21, 243, 176, 23);
		frame.getContentPane().add(button);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(207, 244, 531, 20);
		frame.getContentPane().add(textField);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Exibir:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(24, 11, 290, 37);
		panel.setLayout(new GridLayout(0, 3));
		frame.getContentPane().add(panel);

		radioButton = new JRadioButton("grupo");
		radioButton.setSelected(true);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		radioButton_1 = new JRadioButton("minhas");
		radioButton_1.setSelected(true);
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		radioButton_2 = new JRadioButton("pesquisa");
		radioButton_2.setSelected(true);
		radioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		panel.add(radioButton);
		panel.add(radioButton_1);
		panel.add(radioButton_2);

		grupobotoes = new ButtonGroup();	//permite selecao unica dos botoes
		grupobotoes.add(radioButton);
		grupobotoes.add(radioButton_1);
		grupobotoes.add(radioButton_2);



		textField_2 = new JTextField();
		textField_2.setBounds(324, 22, 161, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		//----------timer----------------
		timer = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				String s = formatador.format(new Date());
				frame.setTitle("Listar Mensagens  -- "+ s);
				frame.repaint();
				listagem();
			}
		});
		timer.setRepeats(true);
		timer.setDelay(8000);	//8 segundos
		timer.start();


		frame.setVisible(true);
	}

	public void listagem() {
		try{
			List<Mensagem> lista=null;
			if(radioButton.isSelected()) 
				lista = Fachada.listarMensagens();

			if(radioButton_1.isSelected()) {
				lista = Fachada.listarMensagensUsuario();
			}

			if(radioButton_2.isSelected()) {
				if(textField_2.getText().isEmpty())
					throw new Exception("termo vazio");
				lista = Fachada.buscarMensagens(textField_2.getText());
			}

			//objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//criar as colunas (0,1,2, 3) da tabela
			model.addColumn("Id");
			model.addColumn("Datahora");
			model.addColumn("Criador");
			model.addColumn("Texto");
			//criar as linhas da tabela
			label.setText(""); 
			String texto;
			for(Mensagem m : lista) {
				model.addRow(new Object[]{
						m.getId(), 
						m.getData(),
						m.getCriador().getNome(), 
						m.getTexto() });
			}
			table.setModel(model);

			//redimensionar a coluna 3
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 		//desabilita
			table.getColumnModel().getColumn(3).setMinWidth(300);	//coluna do texto
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //habilita
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(frame, ex.getMessage());
		}
	}
}
