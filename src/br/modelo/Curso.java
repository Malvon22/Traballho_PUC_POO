package br.modelo;

public class Curso extends ErroMsg {
	
	private int codigo;
	private String nome;
	
	public Curso() {
		super();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}	
}
