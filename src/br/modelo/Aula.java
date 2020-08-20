package br.modelo;

public class Aula extends ErroMsg {
	
	private int codigo;
	private String titulo;
	private String descricao;
	private int codigo_curso;
	
	public Aula() {
		super();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getCodigo_curso() {
		return codigo_curso;
	}

	public void setCodigo_curso(int codigo_curso) {
		this.codigo_curso = codigo_curso;
	}
	
}
