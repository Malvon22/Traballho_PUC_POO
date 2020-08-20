package br.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.persistencia.Conexao;
import br.modelo.Aula;
import br.persistencia.Conexao;


public class AulaDao extends Conexao  {
	
	public ArrayList<Aula> listar() throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();
		
		ArrayList<Aula> all = new ArrayList<Aula>();
		Aula Aula = new Aula();
		Statement st=null;
		
		String select = "SELECT * FROM Aula order by titulo";
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			while(rs.next()) {
				Aula = new Aula();
				Aula.setCodigo(rs.getInt("codigo"));
				Aula.setTitulo(rs.getString("titulo"));
				Aula.setDescricao(rs.getString("Descricao"));
				Aula.setCodigo_curso(rs.getInt("codigo_curso"));
				all.add(Aula);
			}
		}catch (SQLException e) {
			all=null;
		}finally {
		}
		return all;		
	}
	public ArrayList<Aula> listarSql(String sql) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();
		
		ArrayList<Aula> all = new ArrayList<Aula>();
		Aula Aula = new Aula();
		Statement st=null;
		
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()) {
				Aula = new Aula();
				Aula.setCodigo(rs.getInt("codigo"));
				Aula.setTitulo(rs.getString("titulo"));
				Aula.setDescricao(rs.getString("Descricao"));
				Aula.setCodigo_curso(rs.getInt("codigo_curso"));
				all.add(Aula);
			}
		}catch (SQLException e) {
			all=null;
		}finally {
		}
		return all;		
	}
	public Aula pesquisarPorTitulo(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();

		Aula Aula = new Aula();
		Statement st=null;
		
		String select = "SELECT * FROM Aula WHERE titulo = '"+str+"'";
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			if(rs.next()) {
				Aula = new Aula();
				Aula.setCodigo(rs.getInt("codigo"));
				Aula.setTitulo(rs.getString("titulo"));
				Aula.setDescricao(rs.getString("Descricao"));
				Aula.setCodigo_curso(rs.getInt("codigo_curso"));
			}
		}catch(SQLException e) {
			Aula=null;
		}finally {
		}
		return Aula;
	}
	public Aula pesquisarPorId(int id) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();

		Aula Aula = new Aula();
		Statement st=null;
		
		String select = "SELECT * FROM Aula WHERE codigo = "+id;
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			if(rs.next()) {
				Aula = new Aula();
				Aula.setCodigo(rs.getInt("codigo"));
				Aula.setTitulo(rs.getString("titulo"));
				Aula.setDescricao(rs.getString("Descricao"));
				Aula.setCodigo_curso(rs.getInt("codigo_curso"));
			}
		}catch(SQLException e) {
			Aula=null;
		}finally {
		}
		return Aula;
	}
	public ArrayList<Aula> listarPorTitulo(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();

		ArrayList<Aula> all = new ArrayList<Aula>();
		Aula Aula = new Aula();
		Statement st=null;
		
		String select = "SELECT * FROM Aula WHERE titulo LIKE '%"+str+"%'";
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			while(rs.next()) {
				Aula = new Aula();
				Aula.setCodigo(rs.getInt("codigo"));
				Aula.setTitulo(rs.getString("titulo"));
				Aula.setDescricao(rs.getString("Descricao"));
				Aula.setCodigo_curso(rs.getInt("codigo_curso"));
				all.add(Aula);
			}
		}catch(SQLException e) {
			all=null;
		}finally {
		}
		return all;
	}
	public Aula incluir(Aula Aula) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();
		Statement st=(Statement) conn.createStatement();
		PreparedStatement pstm;
		String select ="SELECT *FROM Aula where titulo = ?";
		PreparedStatement pstm1;
		pstm1=(PreparedStatement) conn.prepareStatement(select);
		pstm1.setString(1, Aula.getTitulo());
		ResultSet rs1 = pstm1.executeQuery();
		if(rs1.next()) {
			Aula.setMsgErro("Aula ja cadastrado");
			return Aula;
		}
		String insert = "INSERT INTO Aula (titulo, descricao, codigo_curso) VALUES(?, ?, ?)";
		try {
			pstm=(PreparedStatement) conn.prepareStatement(insert);
			pstm.setString(1, Aula.getTitulo());
			pstm.setString(2, Aula.getDescricao());
			pstm.setInt(3, Aula.getCodigo_curso());
			
			pstm.executeUpdate();
			
			Aula.setMsgErro("Inclusão efetuada com sucesso");
		}catch(SQLException e) {
			Aula.setMsgErro("Erro de inclusão");
		}
		return Aula;	
	}
	public Aula alterar(Aula Aula) throws ClassNotFoundException, InterruptedException, SQLException
	{
		Connection conn=(Connection) this.getConnection();
		PreparedStatement pstm;
		
		String update = "UPDATE Aula SET titulo='"+Aula.getTitulo()+"',descricao='"+Aula.getDescricao()+"' WHERE codigo="+Aula.getCodigo();
		try {
			pstm = (PreparedStatement) conn.prepareStatement(update);
//			pstm.setString(1, Aula.getTitulo());
//			pstm.setString(1, Aula.getDescricao());
//			pstm.setInt(3, Aula.getCodigo());
			pstm.execute();
			Aula.setMsgErro("Alteração efetuada com sucesso");
		}catch (SQLException e) {
			Aula.setMsgErro("Erro na alteração");
		}
		return Aula;
	}
	public Aula excluir(Aula Aula) throws ClassNotFoundException, InterruptedException, SQLException
	{
		Connection conn=(Connection) this.getConnection();
		PreparedStatement pstm;
		
		String update = "DELETE FROM Aula WHERE codigo="+Aula.getCodigo();
		try {
			pstm = (PreparedStatement) conn.prepareStatement(update);
			pstm.execute();
			Aula.setMsgErro("Exclusão efetuada com sucesso");
		}catch (SQLException e) {
			Aula.setMsgErro("Erro na exclusão");
		}
		return Aula;
	}
}
