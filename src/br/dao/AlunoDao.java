package br.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.persistencia.Conexao;
import br.modelo.Aluno;
import br.persistencia.Conexao;


public class AlunoDao extends Conexao  {
	
	public ArrayList<Aluno> listar() throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();
		
		ArrayList<Aluno> all = new ArrayList<Aluno>();
		Aluno aluno = new Aluno();
		Statement st=null;
		
		String select = "SELECT * FROM aluno order by nome_aluno";
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			while(rs.next()) {
				aluno = new Aluno();
				aluno.setCodigo(rs.getInt("codigo"));
				aluno.setNome(rs.getString("nome_aluno"));
				aluno.setCpf(rs.getString("cpf"));
				all.add(aluno);
			}
		}catch (SQLException e) {
			all=null;
		}finally {
		}
		return all;		
	}
	public ArrayList<Aluno> listarSql(String sql) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();
		
		ArrayList<Aluno> all = new ArrayList<Aluno>();
		Aluno aluno = new Aluno();
		Statement st=null;
		
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()) {
				aluno = new Aluno();
				aluno.setCodigo(rs.getInt("codigo"));
				aluno.setNome(rs.getString("nome_aluno"));
				aluno.setCpf(rs.getString("cpf"));
				all.add(aluno);
			}
		}catch (SQLException e) {
			all=null;
		}finally {
		}
		return all;		
	}
	public Aluno pesquisarPorNome(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();

		Aluno aluno = new Aluno();
		Statement st=null;
		
		String select = "SELECT * FROM aluno WHERE nome_aluno = '"+str+"'";
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			if(rs.next()) {
				aluno = new Aluno();
				aluno.setCodigo(rs.getInt("codigo"));
				aluno.setNome(rs.getString("nome_aluno"));
				aluno.setCpf(rs.getString("cpf"));
			}
		}catch(SQLException e) {
			aluno=null;
		}finally {
		}
		return aluno;
	}
	public Aluno pesquisarPorId(int id) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();

		Aluno aluno = new Aluno();
		Statement st=null;
		
		String select = "SELECT * FROM aluno WHERE codigo = "+id;
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			if(rs.next()) {
				aluno = new Aluno();
				aluno.setCodigo(rs.getInt("codigo"));
				aluno.setNome(rs.getString("nome_aluno"));
				aluno.setCpf(rs.getString("cpf"));
			}
		}catch(SQLException e) {
			aluno=null;
		}finally {
		}
		return aluno;
	}
	public ArrayList<Aluno> listarPorNome(String str) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();

		ArrayList<Aluno> all = new ArrayList<Aluno>();
		Aluno aluno = new Aluno();
		Statement st=null;
		
		String select = "SELECT * FROM aluno WHERE nome_aluno LIKE '%"+str+"%'";
		try {
			st=(Statement) conn.createStatement();
			rs=st.executeQuery(select);
			while(rs.next()) {
				aluno = new Aluno();
				aluno.setCodigo(rs.getInt("codigo"));
				aluno.setNome(rs.getString("nome_aluno"));
				aluno.setCpf(rs.getString("cpf"));
				all.add(aluno);
			}
		}catch(SQLException e) {
			all=null;
		}finally {
		}
		return all;
	}
	public Aluno incluir(Aluno aluno) throws ClassNotFoundException, InterruptedException, SQLException
	{
		ResultSet rs=null;
		Connection conn=(Connection) this.getConnection();
		Statement st=(Statement) conn.createStatement();
		PreparedStatement pstm;
		String select ="SELECT *FROM aluno where nome_aluno = ?";
		PreparedStatement pstm1;
		pstm1=(PreparedStatement) conn.prepareStatement(select);
		pstm1.setString(1, aluno.getNome());
		ResultSet rs1 = pstm1.executeQuery();
		if(rs1.next()) {
			aluno.setMsgErro("Aluno ja cadastrado");
			return aluno;
		}
		String insert = "INSERT INTO aluno (nome_aluno, cpf) VALUES(?, ?)";
		try {
			pstm=(PreparedStatement) conn.prepareStatement(insert);
			pstm.setString(1, aluno.getNome());
			pstm.setString(2, aluno.getCpf());
			
			pstm.executeUpdate();
			
			aluno.setMsgErro("Inclusão efetuada com sucesso");
		}catch(SQLException e) {
			aluno.setMsgErro("Erro de inclusão");
		}
		return aluno;	
	}
	public Aluno alterar(Aluno aluno) throws ClassNotFoundException, InterruptedException, SQLException
	{
		Connection conn=(Connection) this.getConnection();
		PreparedStatement pstm;
		
		String update = "UPDATE aluno SET nome_aluno='"+aluno.getNome()+"',cpf='"+aluno.getCpf()+"' WHERE codigo="+aluno.getCodigo();
		try {
			pstm = (PreparedStatement) conn.prepareStatement(update);
//			pstm.setString(1, aluno.getNome());
//			pstm.setString(1, aluno.getCpf());
//			pstm.setInt(3, aluno.getCodigo());
			pstm.execute();
			aluno.setMsgErro("Alteração efetuada com sucesso");
		}catch (SQLException e) {
			aluno.setMsgErro("Erro na alteração");
		}
		return aluno;
	}
	public Aluno excluir(Aluno aluno) throws ClassNotFoundException, InterruptedException, SQLException
	{
		Connection conn=(Connection) this.getConnection();
		PreparedStatement pstm;
		
		String update = "DELETE FROM aluno WHERE codigo="+aluno.getCodigo();
		try {
			pstm = (PreparedStatement) conn.prepareStatement(update);
			pstm.execute();
			aluno.setMsgErro("Exclusão efetuada com sucesso");
		}catch (SQLException e) {
			aluno.setMsgErro("Erro na exclusão");
		}
		return aluno;
	}
}
