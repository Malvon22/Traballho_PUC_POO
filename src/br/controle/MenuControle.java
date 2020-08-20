package br.controle;

import java.awt.MenuItem;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class MenuControle extends SelectorComposer<Window> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	MenuItem menuItemAlunos;
	@Wire
	MenuItem menuItemCurso;
	@Wire
	MenuItem menuItemAulas;
	
	@Listen("onClick=#menuItemAlunos")
	public void abrirjanelaAlunos() throws InterruptedException{
		String window="alunocadastro.zul";
		Window wAberta=(Window) Executions.createComponents(window, null, null);
		wAberta.doModal();
	}
	@Listen("onClick=#menuItemCursos")
	public void abrirjanelaCursos() throws InterruptedException{
		String window="cursocadastro.zul";
		Window wAberta=(Window) Executions.createComponents(window, null, null);
		wAberta.doModal();
	}
	@Listen("onClick=#menuItemAulas")
	public void abrirjanelaAulas() throws InterruptedException{
		String window="aulacadastro.zul";
		Window wAberta=(Window) Executions.createComponents(window, null, null);
		wAberta.doModal();
	}
	
}
