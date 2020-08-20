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

import br.dao.AulaDao;
import br.dao.CursoDao;
import br.modelo.Aula;
import br.modelo.Curso;
import br.util.Utilidade;

public class AulaControle extends SelectorComposer<org.zkoss.zul.Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	Window windowAula;
	@Wire
	Tab tabCad;
	@Wire
	Tab tabPesq;
	@Wire
	Intbox intId;
	@Wire
	Textbox txtTitulo;
	@Wire
	Textbox txtDescricao;
	@Wire
	Textbox txtTituloAlt;
	@Wire
	Textbox txtDescricaoAlt;
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
	@Wire
	Listbox listBoxCurso;
	@Wire
	Textbox txtCurso;
	@Wire
	Textbox txtCursoAlt;
	@Wire
	Intbox intIdCurso;

	public String OPCAO = ""; 

	@Listen("onCreate=#window_Aula")
	public void criaJanela() throws SQLException, InterruptedException,
	IOException, ClassNotFoundException {
		this.limpaLsbPesquisa();
		this.limpaDados();
		preencheListaLbxExemplo();
		preencheListaLbxCurso();
	}

	public void limpaLsbPesquisa()
	{
		this.lsbPesquisa.getItems().clear();
	}

	public void preencheLsbPesquisa() throws SQLException,
	InterruptedException, IOException, ClassNotFoundException {
		this.limpaLsbPesquisa();
		AulaDao bdDAO = new AulaDao();
		Aula bd = new Aula(); 
		ArrayList<Aula> al = new ArrayList<Aula>();
		al = bdDAO.listar();

		for (int i = 0; i < al.size(); i++) {
			Listitem li = new Listitem();
			Listcell lc01 = new Listcell(); 
			lc01.setLabel(Integer.toString(al.get(i).getCodigo()));

			Listcell lc02 = new Listcell(); 
			lc02.setLabel(al.get(i).getTitulo());

			Listcell lc03 = new Listcell();
			lc03.setLabel(al.get(i).getDescricao());

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

		AulaDao AulaDAO = new AulaDao();
		Aula obj = new Aula();
		String dataS = "";

		if(validaDados()) {
			obj = this.atualizaDados(obj);
			obj = AulaDAO.incluir(obj);
			Utilidade.mensagem(obj.getMsgErro());
			limpaDados();
			preencheListaLbxExemplo();
			preencheListaLbxCurso();
		}
	}
	@Listen("onClick = #btnAlterar")
	public void onClickbtnAlterar() throws Exception { 
		Aula Aula = new Aula();
		AulaDao aDao = new AulaDao();
		if(validaDadosAlt()) {
			Aula.setTitulo(this.txtTituloAlt.getValue());
			Aula.setDescricao(this.txtDescricaoAlt.getValue());
			Aula.setCodigo(this.intId.getValue());
			if(aDao.pesquisarPorId(Aula.getCodigo())!=null) {
				Aula = aDao.alterar(Aula);
				Utilidade.mensagem(Aula.getMsgErro());
				preencheListaLbxExemplo();
				preencheListaLbxCurso();
			}else 
				Utilidade.mensagem("Id não cadastrada");
		}
	}
	@Listen("onBlur = #intId")
	public void onBlurintId () throws SQLException, InterruptedException, IOException, WrongValueException, ClassNotFoundException{
		AulaDao AulaDao = new AulaDao();
		Aula Aula = new Aula();
		if (!(this.intId.getValue() == null || this.intId.getText().isEmpty() ||
				this.intId.getText().equals("")))
		{
			Aula = AulaDao.pesquisarPorId(Integer.parseInt(this.intId.getText()));
			if(Aula!=null) {
				this.txtTituloAlt.setText(Aula.getTitulo());
				this.txtDescricaoAlt.setText(Aula.getDescricao());
				CursoDao cursoDao = new CursoDao();
				Curso curso = cursoDao.pesquisarPorId(Aula.getCodigo_curso());
				this.txtCursoAlt.setText(curso.getNome());
			}
		}
	}
	@Listen("onClick = #btnExcluir")
	public void onClick$btnExcluir() throws Exception { 
		OPCAO = "E";

		AulaDao AulaDAO = new AulaDao();
		Aula Aula = new Aula();
		String dataS = "";
		if (!(this.intId.getValue() == null || this.intId.getText().isEmpty() ||
				this.intId.getText().equals("")))
		{
			Aula.setCodigo(this.intId.getValue());
			if(AulaDAO.pesquisarPorId(Aula.getCodigo())!=null)
			{
				Aula = AulaDAO.excluir(Aula);
				Utilidade.mensagem(Aula.getMsgErro());
				limpaDadosAlt();
				preencheListaLbxExemplo();
				preencheListaLbxCurso();
			}
			else
				Utilidade.mensagem("Id não cadastrada");
		}
	}

	public boolean validaDados() throws InterruptedException, WrongValueException, SQLException, IOException {
		boolean retorno = false; 
		int conta = 0;
		String msg = "";

		if (this.txtTitulo.getText().toString().length() == 0 || this.txtTitulo.getText().toString().equals("") || this.txtTitulo == null) {
			conta++; 
			msg += "Informe o Titulo da Aula\n";
		}

		if(this.txtDescricao.getText().toString().length() == 0 || this.txtDescricao.getText().toString().equals("") || this.txtDescricao == null)
		{
			conta++;
			msg += "Informe o Descricao\n";
		}
		if(this.txtCurso.getText().toString().length() == 0 || this.txtCurso.getText().toString().equals("") || this.txtCurso == null)
		{
			conta++;
			msg += "Escolha o Curso\n";
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

		if ((this.txtTituloAlt.getText().toString().length() == 0 || this.txtTituloAlt.getText().toString().equals("") || this.txtTituloAlt == null) &&
				(this. txtDescricaoAlt.getText().toString().length() == 0 || this.txtDescricaoAlt.getText().toString().equals("") || this.txtDescricaoAlt == null)) {
			conta++; 
			msg += "Informe o Titulo ou o Descricao do Aula\n";
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
	public Aula atualizaDados(Aula f) throws WrongValueException, SQLException, InterruptedException, IOException { 
		String dataS = "";
		if(!OPCAO.equals("I"))
			f.setCodigo(this.intId.getValue());

		f.setTitulo(this.txtTitulo.getValue());
		f.setDescricao(this.txtDescricao.getValue());
		f.setCodigo_curso(this.intIdCurso.getValue());
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
		AulaDao objDao = new AulaDao(); 
		ArrayList<Aula> al = new ArrayList<Aula>();
		String aux = "";
		aux = this.txtPesquisa.getValue();
		String sql = "";
		if(aux.length() == 0 || aux.isEmpty() || aux.equals("") || aux == null)
		{
			sql = "SELECT * from aula order by titulo"; 
		}
		else
		{
			sql ="SELECT * from aula where titulo like '%"+aux+"%' order by titulo";
		}
		al = objDao.listarSql(sql);
		if(al!=null)
		{
			for (int i = 0; i < al.size(); i++) {
				Listitem li = new Listitem();
				Listcell lc01 = new Listcell(); 
				lc01.setLabel(Integer.toString(al.get(i).getCodigo()));

				Listcell lc02 = new Listcell(); 
				lc02.setLabel(al.get(i).getTitulo());

				Listcell lc03 = new Listcell();
				lc03.setLabel(al.get(i).getDescricao());

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
//	public void obtemAulaSelecionada() throws SQLException, ClassNotFoundException, InterruptedException { 
//		int indice = this.lsbPesquisa.getSelectedIndex();
//		int id = 0;
//		AulaDao objDAO = new AulaDao();
//		Aula Aula = new Aula(); 
//		if (indice >= 0)
//		{
//			Listitem li = new Listitem();
//			Listcell lc01 = new Listcell();
//			lc01 = (Listcell) this.lsbPesquisa.getSelectedItem().getChildren().get(0);
//			id = Integer.parseInt(lc01.getLabel().toString());
//			Aula = objDAO.pesquisarPorId(id);
//			if (Aula!= null) {
//				this.intId.setText(Integer.toString(Aula.getCodigo()));
//				this.txtTitulo.setText(Aula.getTitulo());
//				this.txtDescricao.setText(Aula.getDescricao());
//				this.limpaLsbPesquisa();
//				this.txtPesquisa.setText("");
//				this.tabCad.setSelected(true);
//			}
//		}
//	}
	public void preencheListaLbxCurso()
			throws InterruptedException, SQLException, ClassNotFoundException
		{
			 Curso p = new Curso();
			 CursoDao pDAO = new CursoDao();
			 ArrayList<Curso> lista = new ArrayList<Curso>();
			 this.listBoxCurso.getItems().clear();
			 lista = pDAO.listar();
			 // insere elemento de informação no listbox
			 //com Listitem com um Listcell
			Listitem la = (Listitem) new Listitem();
			Listcell laa = (Listcell) new Listcell("Escolha um item");
			la.appendChild(laa);
			listBoxCurso.appendChild(la);
			 for(int i=0; i<lista.size();i++)
			{
			 //## preenche itens com os dados de lista
		     p=lista.get(i);
			 la = (Listitem) new Listitem();
			 laa = (Listcell) new Listcell(p.getNome());
			 la.appendChild(laa);
			 listBoxCurso.appendChild(la);
			 }
		}
	@Listen ("onSelect = #listBoxCurso")
	public int onSelectLbxCurso()
			 throws InterruptedException, SQLException, ClassNotFoundException
			{
			 int linha = this.listBoxCurso.getSelectedIndex();//obtem o índice do item selecionado
			 Curso p = new Curso();
			 CursoDao pDAO = new CursoDao();
			 //ClasseExemplo armazena os conteúdos auxiliares na Listbox
			 //”andar” pelo listbox para obter o id correspondente para usar esse
			 // id no cadastro que possui esse valor, talvez como chave estrangeira
			 int idDeRetorno=0;
			 for(int i = 1; i<this.listBoxCurso.getItems().size();i++)
			 {
				 Listitem li = this.listBoxCurso.getItemAtIndex(i);
				 Listcell lc0 = (Listcell) li.getChildren().get(0);
				 if (li.getIndex()==linha) // o item é o selecionado?
				 {
					 String aux= lc0.getLabel();
					 p = pDAO.pesquisarPorNome(aux);
					 if (p !=null)
					 {
					 idDeRetorno=p.getCodigo();
					 this.txtCurso.setValue(p.getNome());
					 this.intIdCurso.setValue(p.getCodigo());
					 //obtem aqui o id do item selecionado na Listbox
					 }
				 }	
			}return idDeRetorno;
		}
	public void preencheListaLbxExemplo()
			throws InterruptedException, SQLException, ClassNotFoundException
		{
			 Aula p = new Aula();
			 AulaDao pDAO = new AulaDao();
			 ArrayList<Aula> lista = new ArrayList<Aula>();
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
			 laa = (Listcell) new Listcell(p.getTitulo());
			 la.appendChild(laa);
			 listBoxInc.appendChild(la);
			 }
		}
	
	@Listen ("onSelect = #listBoxInc")
	public int onSelectLbxExemplo()
			 throws InterruptedException, SQLException, ClassNotFoundException
			{
			 int linha = this.listBoxInc.getSelectedIndex();//obtem o índice do item selecionado
			 Aula p = new Aula();
			 AulaDao pDAO = new AulaDao();
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
					 p = pDAO.pesquisarPorTitulo(aux);
					 if (p !=null)
					 {
					 idDeRetorno=p.getCodigo();
					 this.intId.setValue(p.getCodigo());
					 this.txtDescricaoAlt.setValue(p.getDescricao());
					 this.txtTituloAlt.setValue(p.getTitulo());
					 CursoDao cursoDao = new CursoDao();
			 		 Curso curso = cursoDao.pesquisarPorId(p.getCodigo_curso());
					 this.txtCursoAlt.setText(curso.getNome());
					 //obtem aqui o id do item selecionado na Listbox
					 }
				 }	
			}return idDeRetorno;
		}


	
	@Listen("onClick = #btnLimpar")
	public void limpaDados() throws SQLException, InterruptedException, IOException { 
		OPCAO = "";
		this.txtDescricao.setText("");
		this.txtTitulo.setText("");
		this.txtCurso.setText("");
		this.intIdCurso.setText("");
	}
	@Listen("onClick = #btnLimparAlt")
	public void limpaDadosAlt() throws SQLException, InterruptedException, IOException { 
		this.txtDescricaoAlt.setText("");
		this.txtTituloAlt.setText("");
		this.intId.setText("");
		this.txtCursoAlt.setText("");
	}
}
