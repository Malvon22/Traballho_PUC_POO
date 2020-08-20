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

import br.dao.CursoDao;
import br.modelo.Curso;
import br.util.Utilidade;

public class CursoControle extends SelectorComposer<org.zkoss.zul.Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	Window window_Curso;
	@Wire
	Tab tabCad;
	@Wire
	Tab tabPesq;
	@Wire
	Intbox intId;
	@Wire
	Textbox txtNome;
	@Wire
	Textbox txtNomeAlt;
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

	@Listen("onCreate=#window_Curso")
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
		CursoDao bdDAO = new CursoDao();
		ArrayList<Curso> al = new ArrayList<Curso>();
		al = bdDAO.listar();

		for (int i = 0; i < al.size(); i++) {
			Listitem li = new Listitem();
			Listcell lc01 = new Listcell(); 
			lc01.setLabel(Integer.toString(al.get(i).getCodigo()));

			Listcell lc02 = new Listcell(); 
			lc02.setLabel(al.get(i).getNome());

			li.appendChild(lc01);
			li.appendChild(lc02);
			this.lsbPesquisa.appendChild(li);
		}
	}

	@Listen("onClick = #btnIncluir")
	public void onClickbtnIncluir() throws Exception
	{
		OPCAO = "I";
		boolean retorno = true; 

		CursoDao CursoDAO = new CursoDao();
		Curso obj = new Curso();
		String dataS = "";

		if(validaDados()) {
			obj = this.atualizaDados(obj);
			obj = CursoDAO.incluir(obj);
			Utilidade.mensagem(obj.getMsgErro());
			limpaDados();
			preencheListaLbxExemplo();
		}
	}
	@Listen("onClick = #btnAlterar")
	public void onClickbtnAlterar() throws Exception { 
		Curso Curso = new Curso();
		CursoDao aDao = new CursoDao();
		if(validaDadosAlt()) {
			Curso.setNome(this.txtNomeAlt.getValue());
			Curso.setCodigo(this.intId.getValue());
			if(aDao.pesquisarPorId(Curso.getCodigo())!=null) {
				Curso = aDao.alterar(Curso);
				Utilidade.mensagem(Curso.getMsgErro());
				preencheListaLbxExemplo();
			}else 
				Utilidade.mensagem("Id não cadastrada");
		}
	}
	@Listen("onBlur = #intId")
	public void onBlurintId () throws SQLException, InterruptedException, IOException, WrongValueException, ClassNotFoundException{
		CursoDao CursoDao = new CursoDao();
		Curso Curso = new Curso();
		if (!(this.intId.getValue() == null || this.intId.getText().isEmpty() ||
				this.intId.getText().equals("")))
		{
			Curso = CursoDao.pesquisarPorId(Integer.parseInt(this.intId.getText()));
			if(Curso!=null) {
				this.txtNomeAlt.setText(Curso.getNome());
			}
		}
	}
	@Listen("onClick = #btnExcluir")
	public void onClick$btnExcluir() throws Exception { 
		OPCAO = "E";

		CursoDao CursoDAO = new CursoDao();
		Curso Curso = new Curso();
		String dataS = "";
		if (!(this.intId.getValue() == null || this.intId.getText().isEmpty() ||
				this.intId.getText().equals("")))
		{
			Curso.setCodigo(this.intId.getValue());
			if(CursoDAO.pesquisarPorId(Curso.getCodigo())!=null)
			{
				Curso = CursoDAO.excluir(Curso);
				Utilidade.mensagem(Curso.getMsgErro());
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
			msg += "Informe o nome da Curso\n";
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

		if (this.txtNomeAlt.getText().toString().length() == 0 || this.txtNomeAlt.getText().toString().equals("") || this.txtNomeAlt == null) {
			conta++; 
			msg += "Informe o nome do Curso\n";
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
	public Curso atualizaDados(Curso f) throws WrongValueException, SQLException, InterruptedException, IOException { 
		String dataS = "";
		if(!OPCAO.equals("I"))
			f.setCodigo(this.intId.getValue());

		f.setNome(this.txtNome.getValue());
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
		CursoDao objDao = new CursoDao(); 
		ArrayList<Curso> al = new ArrayList<Curso>();
		String aux = "";
		aux = this.txtPesquisa.getValue();
		String sql = "";
		if(aux.length() == 0 || aux.isEmpty() || aux.equals("") || aux == null)
		{
			sql = "SELECT * from Curso order by nome_Curso"; 
		}
		else
		{
			sql ="SELECT * from Curso where nome_Curso like '%"+aux+"%' order by nome_Curso";
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

				li.appendChild(lc01);
				li.appendChild(lc02);
				this.lsbPesquisa.appendChild(li);
			}
		}
		else
		{
			Utilidade.mensagem("Sem dados para ser visualizados");
		}
	}

//	@Listen("onSelect = #lsbPesquisa")
//	public void obtemCursoSelecionada() throws SQLException, ClassNotFoundException, InterruptedException { 
//		int indice = this.lsbPesquisa.getSelectedIndex();
//		int id = 0;
//		CursoDao objDAO = new CursoDao();
//		Curso Curso = new Curso(); 
//		if (indice >= 0)
//		{
//			Listitem li = new Listitem();
//			Listcell lc01 = new Listcell();
//			lc01 = (Listcell) this.lsbPesquisa.getSelectedItem().getChildren().get(0);
//			id = Integer.parseInt(lc01.getLabel().toString());
//			Curso = objDAO.pesquisarPorId(id);
//			if (Curso!= null) {
//				this.intId.setText(Integer.toString(Curso.getCodigo()));
//				this.txtNome.setText(Curso.getNome());
//				this.txtCpf.setText(Curso.getCpf());
//				this.limpaLsbPesquisa();
//				this.txtPesquisa.setText("");
//				this.tabCad.setSelected(true);
//			}
//		}
//	}
	public void preencheListaLbxExemplo()
			throws InterruptedException, SQLException, ClassNotFoundException
		{
			 Curso p = new Curso();
			 CursoDao pDAO = new CursoDao();
			 ArrayList<Curso> lista = new ArrayList<Curso>();
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
			 Curso p = new Curso();
			 CursoDao pDAO = new CursoDao();
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
					 this.txtNomeAlt.setValue(p.getNome());
					 //obtem aqui o id do item selecionado na Listbox
					 }
				 }	
			}return idDeRetorno;
		}


	
	@Listen("onClick = #btnLimpar")
	public void limpaDados() throws SQLException, InterruptedException, IOException { 
		OPCAO = "";
		this.txtNome.setText("");
	}
	@Listen("onClick = #btnLimparAlt")
	public void limpaDadosAlt() throws SQLException, InterruptedException, IOException { 
		this.txtNomeAlt.setText("");
		this.intId.setText("");
	}
}
