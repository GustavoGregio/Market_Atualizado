/*
Created By : Welington
Date : 08/03/2020

Requirements for this DAO:

DB : PostgreSQL
create table usuario ( 
        id int primary key, 
	nome varchar(100),
	cnpj int,
	email varchar(100),
	senha varchar(100),
	cep int,
	logradouro varchar(100),
	cidade varchar(100),
	estado varchar(100),
	pais varchar(100)
	)

create sequence seq_usuario;
*/

package dao;

import Util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Endereco;
import model.Usuario;

public class UsuarioDAO {

public void Cadastrar(Usuario usuario) throws SQLException, ClassNotFoundException {
        System.out.println("usuario = " + usuario);
        Connection con = ConnectionFactory.getConexao();
        PreparedStatement comand = con.prepareStatement("insert into usuario (id, nome, cnpj, email, senha, cep, logradouro, cidade, estado, pais) values (nextval('seq_usuario'),?,?,?,?,?,?,?,?,?)");
        comand.setString(1, usuario.getNome());
        comand.setInt(2, usuario.getCnpj());
        comand.setString(3, usuario.getEmail());
        comand.setString(4, usuario.getSenha());
        comand.setInt(5, usuario.getEndereco().getCep());
        comand.setString(6, usuario.getEndereco().getLogradouro());
        comand.setString(7, usuario.getEndereco().getCidade());
        comand.setString(8, usuario.getEndereco().getEstado());
        comand.setString(9, usuario.getEndereco().getPais());
        comand.execute();
        con.close();
    }

public Usuario Validacao(Usuario usuario) throws SQLException, ClassNotFoundException {
        Usuario usuarioConsultado = new Usuario();
        Connection con = ConnectionFactory.getConexao();
        PreparedStatement comand = con.prepareStatement("select id, nome, email, senha from usuario where email == ?");
        comand.setString(1, usuario.getEmail());
        ResultSet resultado = comand.executeQuery();
        if (resultado.next()) {
            usuarioConsultado.setId(Integer.parseInt(resultado.getString("id")));
            usuarioConsultado.setNome(resultado.getString("nome"));
            usuarioConsultado.setEmail(resultado.getString("email"));
            usuarioConsultado.setSenha(resultado.getString("senha"));
        }
        con.close();
        return usuarioConsultado;
    }

public List<Usuario> ConsultarUsuario(Usuario usuario) throws SQLException {
         Usuario usuarioConsultado = new Usuario();
        Connection con = ConnectionFactory.getConexao();
        PreparedStatement comand = con.prepareStatement("SELECT id, nome, cnpj, email, senha, cep, logradouro, cidade, estado, pais FROM usuario WHERE id == ?");
        comand.setString(1, usuario.getEmail());
        ResultSet resultado = comand.executeQuery();
        
        List<Usuario> todosUsuario = new ArrayList<>();
        if (resultado.next()) {
            usuarioConsultado.setId(resultado.getInt("id"));
            usuarioConsultado.setNome(resultado.getString("nome"));
            usuarioConsultado.setCnpj(resultado.getInt("cnpj"));
            usuarioConsultado.setEmail(resultado.getString("email"));
            usuarioConsultado.setSenha(resultado.getString("senha"));
            Endereco endereco = new Endereco();
            endereco.setCep(resultado.getInt("cep"));
            endereco.setLogradouro(resultado.getString("logradouro"));
            endereco.setCidade(resultado.getString("cidade"));
            endereco.setEstado(resultado.getString("estado"));
            endereco.setPais(resultado.getString("pais"));
            
            usuarioConsultado.setEndereco(endereco);
        }
        con.close();
        return todosUsuario;
    }
    
       

  public void Editar(Usuario usuario) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();
        PreparedStatement comand = con.prepareStatement("update tbl_usuario set nome = ? ,cnpj=? ,email=?, senha=?, cep=?, logradouro=?, cidade=?, estado=?, pais=? FROM usuario where id = ?");
        comand.setString(1, usuario.getNome());
        comand.setInt(2, usuario.getCnpj());
        comand.setString(3, usuario.getEmail());
        comand.setString(4, usuario.getSenha());
        comand.setInt(5, usuario.getEndereco().getCep());
        comand.setString(6, usuario.getEndereco().getLogradouro());
        comand.setString(7, usuario.getEndereco().getCidade());
        comand.setString(8, usuario.getEndereco().getEstado());
        comand.setString(9, usuario.getEndereco().getPais());
        comand.execute();
        con.close();
    }
  
   public void Excluir(Usuario usuario) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();
        PreparedStatement comando = con.prepareStatement("delete from tbl_usuario where id=?");
        comando.setInt(1, usuario.getId());
        comando.execute();
        con.close();
    }

      public void ConsultarId(Usuario usuario) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();
        PreparedStatement comando = con.prepareStatement("select id, nome, cnpj, email, senha, cep, logradouro, cidade, estado, pais from tbl_usuario where id=?");
        comando.setInt(1, usuario.getId());
        ResultSet resultado = comando.executeQuery();

        if (resultado.next()) {
            usuario.setId(resultado.getInt("id"));
            usuario.setNome(resultado.getString("nome"));
            usuario.setCnpj(resultado.getInt("cnpj"));
            usuario.setEmail(resultado.getString("Email"));
            usuario.getSenha(resultado.getString("Senha"));
            Endereco endereco = new Endereco();
            endereco.setCep(resultado.getInt("cep"));
            endereco.setLogradouro(resultado.getString("logradouro"));
            endereco.setCidade(resultado.getString("cidade"));
            endereco.setEstado(resultado.getString("estado"));
            endereco.setPais(resultado.getString("pais"));
            
            usuario.setEndereco(endereco);
      
             
        }
        
        con.close();
    }

    public List<Usuario> Consultar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
