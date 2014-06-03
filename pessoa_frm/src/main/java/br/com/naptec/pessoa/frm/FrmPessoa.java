package br.com.naptec.pessoa.frm;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.naptec.base_dados.beans.Pessoa;
import services.PessoaService;

public class FrmPessoa extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNome;
	private JTextField txtIdade;
	private JTextField txtEmail;
	
	private PessoaService service;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmPessoa frame = new FrmPessoa();
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
	public FrmPessoa() {
		// Instaciar o objeto service da classe PessoaService.
		service = new PessoaService();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 228);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(10, 11, 40, 20);
		contentPane.add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(60, 11, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 42, 40, 20);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(60, 42, 364, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblIdade = new JLabel("Idade");
		lblIdade.setBounds(10, 73, 40, 20);
		contentPane.add(lblIdade);
		
		txtIdade = new JTextField();
		txtIdade.setBounds(60, 73, 86, 20);
		contentPane.add(txtIdade);
		txtIdade.setColumns(10);
		
		JLabel lblEmail = new JLabel("e-mail");
		lblEmail.setBounds(10, 104, 40, 20);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(60, 104, 364, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimpar.setBounds(10, 151, 89, 23);
		contentPane.add(btnLimpar);
		
		JButton btnGravar = new JButton("Gravar");
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravar();
			}
		});
		btnGravar.setBounds(109, 151, 89, 23);
		contentPane.add(btnGravar);
	}
	
	protected void gravar() {
		try {
			Pessoa p = new Pessoa();
			
			// O m�todo getText() devolve uma String com o valor do JTextField.
			p.setNome(txtNome.getText());
			p.setEmail(txtEmail.getText());
			p.setIdade(Integer.parseInt(txtIdade.getText()));
			
			service.gravar(p);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
		
	}

	protected void LimparCampos() {
		/*
		 * setText(String): Define o texto do elemento JTextField;
		 * 
		 * */
		this.txtCodigo.setText("");
		this.txtNome.setText("");
		this.txtIdade.setText("");
		this.txtEmail.setText("");
	}

	protected void Alerta() {
		JOptionPane.showMessageDialog(this, "Funciona!");
		
	}
}
