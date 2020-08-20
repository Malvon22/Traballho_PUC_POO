package br.controle;

import java.sql.SQLException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.dao.AlunoDao;
import br.modelo.Aluno;
import br.util.Utilidade;

public class LoginControle extends SelectorComposer<Window>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	Textbox nome;
	@Wire
	Textbox password;
	@Wire
	Button logar;
	
	@Listen("onClick = #logar")
	public void logar() throws InterruptedException, WrongValueException, ClassNotFoundException, SQLException{
		String windowAdmin="menu.zul";
		String windowAluno="assistiraula.zul";
		AlunoDao alunoDao = new AlunoDao();
		Aluno aluno = new Aluno();
		aluno = alunoDao.pesquisarPorNome(this.nome.getValue());
		if(this.nome.getText().equals("Admin") && this.password.getValue().equals("Admin")) {
			Executions.sendRedirect(windowAdmin);
		}
		else {
			Utilidade.mensagem("Usuario ou senha incorretos");
		}
		if(aluno.getNome()!="") {
			Executions.sendRedirect(windowAluno);
		}
	}
}
