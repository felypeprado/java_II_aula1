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
import br.com.naptec.pessoa.services.PessoaService;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrmPessoa extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNome;
	private JTextField txtIdade;
	private JTextField txtEmail;
	private final JButton btnLimpar = new JButton("Limpar");
	private JButton btnGravar;
	private PessoaService service;
	private JButton btnEditar;
	private JButton btnExcluir;
	private Pessoa pessoa;

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
		//Instanciar o objeto service da classe PessoaService
		service = new PessoaService();
		// Instanciar o objeto pessoa da classe Pessoa
		pessoa = new Pessoa();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 210);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setBounds(10, 11, 55, 20);
		contentPane.add(lblCdigo);
		
		txtCodigo = new JTextField();
		txtCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					pesquisar();
				}
			}
		});
		txtCodigo.setBounds(75, 11, 55, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 42, 55, 20);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(75, 42, 429, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblIdade = new JLabel("Idade");
		lblIdade.setBounds(10, 73, 55, 20);
		contentPane.add(lblIdade);
		
		txtIdade = new JTextField();
		txtIdade.setBounds(75, 73, 55, 20);
		contentPane.add(txtIdade);
		txtIdade.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 104, 55, 20);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(75, 104, 429, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(10, 135, 90, 26);
		contentPane.add(btnLimpar);
		
		btnGravar = new JButton("Gravar");
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravar();
			}
		});
		btnGravar.setBounds(414, 135, 90, 26);
		contentPane.add(btnGravar);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		btnPesquisar.setBounds(401, 10, 103, 23);
		contentPane.add(btnPesquisar);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setBounds(113, 135, 89, 27);
		contentPane.add(btnEditar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setBounds(215, 135, 89, 26);
		contentPane.add(btnExcluir);
	}
	
	protected void excluir() {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Deseja excluir o registro?", "Confirmação", JOptionPane.YES_NO_OPTION)) {
			try {
				if (null != pessoa.getId() && pessoa.getId() > 0) {
					service.excluir(pessoa.getId());
				} else {
					throw new Exception("Nenhum registro foi selecionado!");
				}
				
				limparCampos();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	protected void editar() {
		txtNome.setEditable(true);
		txtIdade.setEditable(true);
		txtEmail.setEditable(true);
	}

	protected void pesquisar() {
		try {
			String sCod = txtCodigo.getText();
			if ("".equals(sCod.trim())) {
				throw new Exception("Campo código é obrigatório");
			}
			
			Long cod = Long.parseLong(sCod);
			pessoa = service.buscarPorId(cod);
			
			if (null == pessoa || pessoa.getId() == null) {
				throw new Exception("Nenhum registro encontrado!");
			}
			
			// Definindo os valores dos campos
			txtCodigo.setText(pessoa.getId().toString());
			txtNome.setText(pessoa.getNome().toString());
			txtEmail.setText(pessoa.getEmail().toLowerCase());
			txtIdade.setText(pessoa.getIdade() + "");			
			
			// Desabilitando os campos para edição
			txtCodigo.setEditable(false);
			txtNome.setEditable(false);
			txtIdade.setEditable(false);
			txtEmail.setEditable(false);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void gravar() {
		try {
			pessoa = new Pessoa();
			//O método getText() devolve uma String com o valor          
			//do JTextField
			if (!"".equals(txtNome.getText().trim())) {			
				pessoa.setNome(txtNome.getText());
			} else {
				throw new Exception("O campo nome é obrigatório!");
			}
						
			pessoa.setEmail(txtEmail.getText());
			pessoa.setIdade(Integer.parseInt(txtIdade.getText()));
			
			String sId = txtCodigo.getText().trim();
			if (!"".equals(sId)) {
				pessoa.setId(Long.parseLong(sId));
			}
			service.gravar(pessoa);
			
			JOptionPane.showMessageDialog(this, "Registro gravado com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);
			
			limparCampos();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, 
					ex.getMessage());
		}
	}
	
	protected void limparCampos() {
		//setText(String): Define o texto do elemento JTextField
		this.txtCodigo.setText("");
		this.txtEmail.setText("");
		this.txtIdade.setText("");
		this.txtNome.setText("");
		
		// Habilitar os campos para edição
		txtCodigo.setEditable(true);
		txtNome.setEditable(true);
		txtIdade.setEditable(true);
		txtEmail.setEditable(true);
		
		pessoa = new Pessoa();
	}

	protected void alerta() {
		//this = esta instância de objeto da classe FrmPessoa
		JOptionPane.showMessageDialog(this, "Funciona!");		
	}
}












