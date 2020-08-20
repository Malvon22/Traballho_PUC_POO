package br.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Conexao {

	private String login = "root";
	private String senha = "";
	private String host = "localhost";
	private String dbName = "final";
	private String url = "jdbc:mysql://"+ host + "/" +dbName;

	public Connection conexao = null;
	public Conexao() { } 
	public Connection getConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			return null;
		}
		try {
			this.conexao = (Connection) DriverManager.getConnection(url,login,senha);
		}
		catch(SQLException ex)
		{
			return null;
		}
		return this.conexao;
	}

	public static void main(String[] args) throws SQLException {

		Conexao conexao = new Conexao();
		Connection conn = conexao.getConnection();
		System.out.println(conn); 
		if(conn == null) {
			System.out.println("Erro na conexao");
		}
		else {
			System.out.println("Conectado com o banco");
		}
	}
}

