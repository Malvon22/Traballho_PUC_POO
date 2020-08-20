package br.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.persistencia.Conexao;
import br.modelo.Curso;
import br.persistencia.Conexao;


public class CursoDao extends Conexao  {
	
	public ArrayList<Curso> listar() throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();
		
		ArrayList<Curso> all = new ArrayList<Curso>();
		Curso Curso = new Curso();
		Statement st=null;
		
		String select = "SELECT * FROM Curso order by nome_curso";
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			while(rs.next()) {
				Curso = new Curso();
				Curso.setCodigo(rs.getInt("codigo"));
				Curso.setNome(rs.getString("nome_curso"));
				all.add(Curso);
			}
		}catch (SQLException e) {
			all=null;
		}finally {
		}
		return all;		
	}
	public ArrayList<Curso> listarSql(String sql) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();
		
		ArrayList<Curso> all = new ArrayList<Curso>();
		Curso Curso = new Curso();
		Statement st=null;
		
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()) {
				Curso = new Curso();
				Curso.setCodigo(rs.getInt("codigo"));
				Curso.setNome(rs.getString("nome_curso"));
				all.add(Curso);
			}
		}catch (SQLException e) {
			all=null;
		}finally {
		}
		return all;		
	}
	public Curso pesquisarPorNome(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();

		Curso Curso = new Curso();
		Statement st=null;
		
		String select = "SELECT * FROM Curso WHERE nome_curso = '"+str+"'";
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			if(rs.next()) {
				Curso = new Curso();
				Curso.setCodigo(rs.getInt("codigo"));
				Curso.setNome(rs.getString("nome_curso"));
			}
		}catch(SQLException e) {
			Curso=null;
		}finally {
		}
		return Curso;
	}
	public Curso pesquisarPorId(int id) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();

		Curso Curso = new Curso();
		Statement st=null;
		
		String select = "SELECT * FROM Curso WHERE codigo = "+id;
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			if(rs.next()) {
				Curso = new Curso();
				Curso.setCodigo(rs.getInt("codigo"));
				Curso.setNome(rs.getString("nome_curso"));
			}
		}catch(SQLException e) {
			Curso=null;
		}finally {
		}
		return Curso;
	}
	public ArrayList<Curso> listarPorNome(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();

		ArrayList<Curso> all = new ArrayList<Curso>();
		Curso Curso = new Curso();
		Statement st=null;
		
		String select = "SELECT * FROM Curso WHERE nome_curso LIKE '%"+str+"%'";
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			while(rs.next()) {
				Curso = new Curso();
				Curso.setCodigo(rs.getInt("codigo"));
				Curso.setNome(rs.getString("nome_curso"));
				all.add(Curso);
			}
		}catch(SQLException e) {
			all=null;
		}finally {
		}
		return all;
	}
	public Curso incluir(Curso Curso) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();
		Statement st=(Statement) conn.createStatement();
		PreparedStatement pstm;
		String select ="SELECT *FROM Curso where nome_curso = ?";
		PreparedStatement pstm1;
		pstm1=(PreparedStatement) conn.prepareStatement(select);
		pstm1.setString(1, Curso.getNome());
		ResultSet rs1 = pstm1.executeQuery();
		if(rs1.next()) {
			Curso.setMsgErro("Curso ja cadastrado");
			return Curso;
		}
		String insert = "INSERT INTO Curso (nome_curso) VALUES(?)";
		try {
			pstm=(PreparedStatement) conn.prepareStatement(insert);
			pstm.setString(1, Curso.getNome());
			
			pstm.executeUpdate();
			
			Curso.setMsgErro("Inclusão efetuada com sucesso");
		}catch(SQLException e) {
			Curso.setMsgErro("Erro de inclusão");
		}
		return Curso;	
	}
	public Curso alterar(Curso Curso) throws ClassNotFoundException, InterruptedException, SQLException
	{
		Connection conn=(Connection) this.getConnection();
		PreparedStatement pstm;
		
		String update = "UPDATE Curso SET nome_curso='"+Curso.getNome()+"' WHERE codigo="+Curso.getCodigo();
		try {
			pstm = (PreparedStatement) conn.prepareStatement(update);
//			pstm.setString(1, Curso.getNome());
//			pstm.setString(1, Curso.getCpf());
//			pstm.setInt(3, Curso.getCodigo());
			pstm.execute();
			Curso.setMsgErro("Alteração efetuada com sucesso");
		}catch (SQLException e) {
			Curso.setMsgErro("Erro na alteração");
		}
		return Curso;
	}
	public Curso excluir(Curso Curso) throws ClassNotFoundException, InterruptedException, SQLException
	{
		Connection conn=(Connection) this.getConnection();
		PreparedStatement pstm;
		
		String update = "DELETE FROM Curso WHERE codigo="+Curso.getCodigo();
		try {
			pstm = (PreparedStatement) conn.prepareStatement(update);
			pstm.execute();
			Curso.setMsgErro("Exclusão efetuada com sucesso");
		}catch (SQLException e) {
			Curso.setMsgErro("Erro na exclusão");
		}
		return Curso;
	}
}
