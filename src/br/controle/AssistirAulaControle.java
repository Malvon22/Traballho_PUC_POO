package br.controle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.lowagie.text.Image;

import br.dao.AlunoDao;
import br.dao.AulaDao;
import br.dao.CursoDao;
import br.modelo.Aluno;
import br.modelo.Aula;
import br.modelo.Curso;
import br.util.Utilidade;

public class AssistirAulaControle extends SelectorComposer<Window>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	Listbox listBoxCurso;
	@Wire
	Listbox listBoxAula;
	@Wire
	Div imagem;
	@Wire
	Row aulaSelec;
	@Wire
	Window window_assitir;
	
	@Listen("onCreate = #window_assitir")
	public void criaJanela() throws SQLException, InterruptedException,
	IOException, ClassNotFoundException {
		preencheListaLbxCurso();
	}
	
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
						 ArrayList<Aula> all = new ArrayList<Aula>();
						 Aula aula = new Aula();
						 AulaDao aulaDAO = new AulaDao();
						 aulaSelec.setVisible(true);
						 all = aulaDAO.listarSql("SELECT * FROM aula WHERE codigo_curso = "+p.getCodigo());
						 for(int b=0; b<all.size();b++)
							{
							 //## preenche itens com os dados de lista
						     aula=all.get(b);
							 li = (Listitem) new Listitem();
							 lc0 = (Listcell) new Listcell(aula.getTitulo());
							 li.appendChild(lc0);
							 listBoxAula.appendChild(li);
							 }
					 
					 //obtem aqui o id do item selecionado na Listbox
					 }
				 }	
			}return idDeRetorno;
		}
	
	@Listen ("onSelect = #listBoxAula")
	public int onSelectLbxExemplo()
			 throws InterruptedException, SQLException, ClassNotFoundException
			{
			 int linha = this.listBoxAula.getSelectedIndex();//obtem o índice do item selecionado
			 Aula p = new Aula();
			 AulaDao pDAO = new AulaDao();
			 //ClasseExemplo armazena os conteúdos auxiliares na Listbox
			 //”andar” pelo listbox para obter o id correspondente para usar esse
			 // id no cadastro que possui esse valor, talvez como chave estrangeira
			 int idDeRetorno=0;
			 imagem.setVisible(true);
			 return idDeRetorno;
		
		}
	
}
