package br.util;

import org.zkoss.zul.Messagebox;

public class Utilidade {
	private static String titulo="Final";
	
	public static void mensagem(String msg) throws InterruptedException
	{
		Messagebox.show(msg,titulo,Messagebox.OK,Messagebox.INFORMATION);
	}
	public static void mensagemSQL(Exception e) throws InterruptedException
	{
		String msg="MSG"+e.getMessage()+" CAUSA:"+e.getCause()+" MSGLOC"+e.getLocalizedMessage();
		Messagebox.show(msg,titulo,Messagebox.OK,Messagebox.INFORMATION);
	}
}
