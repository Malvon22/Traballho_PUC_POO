package br.controle;

import java.awt.Window;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import br.dao.AlunoDao;
import br.modelo.Aluno;
import br.util.Utilidade;

public class AlunoControle extends SelectorComposer<org.zkoss.zul.Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	Window windowAluno;
	@Wire
	Tab tabCad;
	@Wire
	Tab tabPesq;
	@Wire
	Intbox intId;
	@Wire
	Textbox txtNome;
	@Wire
	Textbox txtCpf;
	@Wire
	Textbox txtNomeAlt;
	@Wire
	Textbox txtCpfAlt;
	@Wire
	Button btnIncluir; 
	@Wire
	Button btnAlterar;
	@Wire
	Button btnExcluir;
	@Wire
	Button btnLimpar; 
	@Wire
	Button btnLimparAlt;
	@Wire
	Textbox txtPesquisa;
	@Wire
	Listbox lsbPesquisa;
	@Wire
	Button btnLimparLista; 
	@Wire
	Button btnAtualizarLista;
	@Wire
	Listbox listBoxInc;

	public String OPCAO = ""; 

	@Listen("onCreate=#window_aluno")
	public void criaJanela() throws SQLException, InterruptedException,
	IOException, ClassNotFoundException {
		this.limpaLsbPesquisa();
		this.limpaDados();
		preencheListaLbxExemplo();
	}

	public void limpaLsbPesquisa()
	{
		this.lsbPesquisa.getItems().clear();
	}

	public void preencheLsbPesquisa() throws SQLException,
	InterruptedException, IOException, ClassNotFoundException {
		this.limpaLsbPesquisa();
		AlunoDao bdDAO = new AlunoDao();
		Aluno bd = new Aluno(); 
		ArrayList<Aluno> al = new ArrayList<Aluno>();
		al = bdDAO.listar();

		for (int i = 0; i < al.size(); i++) {
			Listitem li = new Listitem();
			Listcell lc01 = new Listcell(); 
			lc01.setLabel(Integer.toString(al.get(i).getCodigo()));

			Listcell lc02 = new Listcell(); 
			lc02.setLabel(al.get(i).getNome());

			Listcell lc03 = new Listcell();
			lc03.setLabel(al.get(i).getCpf());

			li.appendChild(lc01);
			li.appendChild(lc02);
			li.appendChild(lc03);
			this.lsbPesquisa.appendChild(li);
		}
	}

	@Listen("onClick = #btnIncluir")
	public void onClickbtnIncluir() throws Exception
	{
		OPCAO = "I";
		boolean retorno = true; 

		AlunoDao AlunoDAO = new AlunoDao();
		Aluno obj = new Aluno();
		String dataS = "";

		if(validaDados()) {
			obj = this.atualizaDados(obj);
			obj = AlunoDAO.incluir(obj);
			Utilidade.mensagem(obj.getMsgErro());
			limpaDados();
			preencheListaLbxExemplo();
		}
	}
	@Listen("onClick = #btnAlterar")
	public void onClickbtnAlterar() throws Exception { 
		Aluno aluno = new Aluno();
		AlunoDao aDao = new AlunoDao();
		if(validaDadosAlt()) {
			aluno.setNome(this.txtNomeAlt.getValue());
			aluno.setCpf(this.txtCpfAlt.getValue());
			aluno.setCodigo(this.intId.getValue());
			if(aDao.pesquisarPorId(aluno.getCodigo())!=null) {
				aluno = aDao.alterar(aluno);
				Utilidade.mensagem(aluno.getMsgErro());
				preencheListaLbxExemplo();
			}else 
				Utilidade.mensagem("Id não cadastrada");
		}
	}
	@Listen("onBlur = #intId")
	public void onBlurintId () throws SQLException, InterruptedException, IOException, WrongValueException, ClassNotFoundException{
		AlunoDao alunoDao = new AlunoDao();
		Aluno aluno = new Aluno();
		if (!(this.intId.getValue() == null || this.intId.getText().isEmpty() ||
				this.intId.getText().equals("")))
		{
			aluno = alunoDao.pesquisarPorId(Integer.parseInt(this.intId.getText()));
			if(aluno!=null) {
				this.txtNomeAlt.setText(aluno.getNome());
				this.txtCpfAlt.setText(aluno.getCpf());
			}
		}
	}
	@Listen("onClick = #btnExcluir")
	public void onClick$btnExcluir() throws Exception { 
		OPCAO = "E";

		AlunoDao AlunoDAO = new AlunoDao();
		Aluno aluno = new Aluno();
		String dataS = "";
		if (!(this.intId.getValue() == null || this.intId.getText().isEmpty() ||
				this.intId.getText().equals("")))
		{
			aluno.setCodigo(this.intId.getValue());
			if(AlunoDAO.pesquisarPorId(aluno.getCodigo())!=null)
			{
				aluno = AlunoDAO.excluir(aluno);
				Utilidade.mensagem(aluno.getMsgErro());
				limpaDadosAlt();
				preencheListaLbxExemplo();
			}
			else
				Utilidade.mensagem("Id não cadastrada");
		}
	}

	public boolean validaDados() throws InterruptedException, WrongValueException, SQLException, IOException {
		boolean retorno = false; 
		int conta = 0;
		String msg = "";

		if (this.txtNome.getText().toString().length() == 0 || this.txtNome.getText().toString().equals("") || this.txtNome == null) {
			conta++; 
			msg += "Informe o nome da Aluno\n";
		}

		if(this. txtCpf.getText().toString().length() == 0 || this.txtCpf.getText().toString().equals("") || this.txtCpf == null)
		{
			conta++;
			msg += "Informe o Cpf\n";
		}
		if (conta > 0) {
			Utilidade.mensagem(msg);
			retorno=false;
		}
		if(conta == 0) {
			retorno = true;
		}
		return retorno; 
	}
	public boolean validaDadosAlt() throws InterruptedException, WrongValueException, SQLException, IOException {
		boolean retorno = false; 
		int conta = 0;
		String msg = "";

		if ((this.txtNomeAlt.getText().toString().length() == 0 || this.txtNomeAlt.getText().toString().equals("") || this.txtNomeAlt == null) &&
				(this. txtCpfAlt.getText().toString().length() == 0 || this.txtCpfAlt.getText().toString().equals("") || this.txtCpfAlt == null)) {
			conta++; 
			msg += "Informe o nome ou o CPF do Aluno\n";
		}
		if (this.intId.getValue() == null || this.intId.getText().isEmpty() ||
				this.intId.getText().equals(""))
		{
			conta++;
			msg+=("Informe o Id");
		}
		if (conta > 0) {
			Utilidade.mensagem(msg);
			retorno=false;
		}
		if(conta == 0) {
			retorno = true;
		}
		return retorno; 
	}
	public Aluno atualizaDados(Aluno f) throws WrongValueException, SQLException, InterruptedException, IOException { 
		String dataS = "";
		if(!OPCAO.equals("I"))
			f.setCodigo(this.intId.getValue());

		f.setNome(this.txtNome.getValue());
		f.setCpf(this.txtCpf.getValue());
		this.txtPesquisa.setText("");
		return f;
	}
	@Listen("onClick = #btnLimparLista")
	public void onClickbtnLimparLista() {
		this.limpaLsbPesquisa();
		this.txtPesquisa.setText("");
	}

	@Listen("onClick = #btnAtualizarLista")
	public void onclickbtnAtualizarLista() throws SQLException, InterruptedException, IOException, WrongValueException, ClassNotFoundException { 
		this.onBlurtxtPesquisa();
	}

	//@Listen("onBlur = #txtPesquisa")
	public void onBlurtxtPesquisa() throws SQLException, InterruptedException, IOException, WrongValueException, ClassNotFoundException{
		this.limpaLsbPesquisa();
		AlunoDao objDao = new AlunoDao(); 
		ArrayList<Aluno> al = new ArrayList<Aluno>();
		String aux = "";
		aux = this.txtPesquisa.getValue();
		String sql = "";
		if(aux.length() == 0 || aux.isEmpty() || aux.equals("") || aux == null)
		{
			sql = "SELECT * from Aluno order by nome_Aluno"; 
		}
		else
		{
			sql ="SELECT * from Aluno where nome_Aluno like '%"+aux+"%' order by nome_Aluno";
		}
		al = objDao.listarSql(sql);
		if(al!=null)
		{
			for (int i = 0; i < al.size(); i++) {
				Listitem li = new Listitem();
				Listcell lc01 = new Listcell(); 
				lc01.setLabel(Integer.toString(al.get(i).getCodigo()));

				Listcell lc02 = new Listcell(); 
				lc02.setLabel(al.get(i).getNome());

				Listcell lc03 = new Listcell();
				lc03.setLabel(al.get(i).getCpf());

				li.appendChild(lc01);
				li.appendChild(lc02);
				li.appendChild(lc03);
				this.lsbPesquisa.appendChild(li);
			}
		}
		else
		{
			Utilidade.mensagem("Sem dados para ser visualizados");
		}
	}

//	@Listen("onSelect = #lsbPesquisa")
//	public void obtemAlunoSelecionada() throws SQLException, ClassNotFoundException, InterruptedException { 
//		int indice = this.lsbPesquisa.getSelectedIndex();
//		int id = 0;
//		AlunoDao objDAO = new AlunoDao();
//		Aluno Aluno = new Aluno(); 
//		if (indice >= 0)
//		{
//			Listitem li = new Listitem();
//			Listcell lc01 = new Listcell();
//			lc01 = (Listcell) this.lsbPesquisa.getSelectedItem().getChildren().get(0);
//			id = Integer.parseInt(lc01.getLabel().toString());
//			Aluno = objDAO.pesquisarPorId(id);
//			if (Aluno!= null) {
//				this.intId.setText(Integer.toString(Aluno.getCodigo()));
//				this.txtNome.setText(Aluno.getNome());
//				this.txtCpf.setText(Aluno.getCpf());
//				this.limpaLsbPesquisa();
//				this.txtPesquisa.setText("");
//				this.tabCad.setSelected(true);
//			}
//		}
//	}
	public void preencheListaLbxExemplo()
			throws InterruptedException, SQLException, ClassNotFoundException
		{
			 Aluno p = new Aluno();
			 AlunoDao pDAO = new AlunoDao();
			 ArrayList<Aluno> lista = new ArrayList<Aluno>();
			 this.listBoxInc.getItems().clear();
			 lista = pDAO.listar();
			 // insere elemento de informação no listbox
			 //com Listitem com um Listcell
		 	 Listitem la = (Listitem) new Listitem();
		 	 Listcell laa = (Listcell) new Listcell("Escolha um item");
		 	 la.appendChild(laa);
			 listBoxInc.appendChild(la);
			 for(int i=0; i<lista.size();i++)
			{
			 //## preenche itens com os dados de lista
		     p=lista.get(i);
			 la = (Listitem) new Listitem();
			 laa = (Listcell) new Listcell(p.getNome());
			 la.appendChild(laa);
			 listBoxInc.appendChild(la);
			 }
		}
	
	@Listen ("onSelect = #listBoxInc")
	public int onSelectLbxExemplo()
			 throws InterruptedException, SQLException, ClassNotFoundException
			{
			 int linha = this.listBoxInc.getSelectedIndex();//obtem o índice do item selecionado
			 Aluno p = new Aluno();
			 AlunoDao pDAO = new AlunoDao();
			 //ClasseExemplo armazena os conteúdos auxiliares na Listbox
			 //”andar” pelo listbox para obter o id correspondente para usar esse
			 // id no cadastro que possui esse valor, talvez como chave estrangeira
			 int idDeRetorno=0;
			 for(int i = 1; i<this.listBoxInc.getItems().size();i++)
			 {
				 Listitem li = this.listBoxInc.getItemAtIndex(i);
				 Listcell lc0 = (Listcell) li.getChildren().get(0);
				 if (li.getIndex()==linha) // o item é o selecionado?
				 {
					 String aux= lc0.getLabel();
					 p = pDAO.pesquisarPorNome(aux);
					 if (p !=null)
					 {
					 idDeRetorno=p.getCodigo();
					 this.intId.setValue(p.getCodigo());
					 this.txtCpfAlt.setValue(p.getCpf());
					 this.txtNomeAlt.setValue(p.getNome());
					 //obtem aqui o id do item selecionado na Listbox
					 }
				 }	
			}return idDeRetorno;
		}


	
	@Listen("onClick = #btnLimpar")
	public void limpaDados() throws SQLException, InterruptedException, IOException { 
		OPCAO = "";
		this.txtCpf.setText("");
		this.txtNome.setText("");
	}
	@Listen("onClick = #btnLimparAlt")
	public void limpaDadosAlt() throws SQLException, InterruptedException, IOException { 
		this.txtCpfAlt.setText("");
		this.txtNomeAlt.setText("");
		this.intId.setText("");
	}
}
