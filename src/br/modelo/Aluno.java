package br.modelo;

public class Aluno extends ErroMsg {
	
	private int codigo;
	private String nome;
	private String cpf;
	
	public Aluno() {
		super();
		this.nome="";
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
