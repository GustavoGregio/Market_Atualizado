/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Comercio;
import model.Endereco;

/**
 *
 * @author gusta
 */
public class ComercioDAO {
    
     public void Cadastrar(Comercio comercio) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();

        PreparedStatement comand = con.prepareStatement("insert into tbl_comercio (nome, cnpj, cep, logradouro, cidade, estado, pais) values (?,?,?,?,?,?,?)");
        comand.setString(1, comercio.getNome());
        comand.setInt(2, comercio.getCnpj());
        comand.setInt(3, comercio.getEndereco().getCep());
        comand.setString(4, comercio.getEndereco().getLogradouro());
        comand.setString(5, comercio.getEndereco().getCidade());
        comand.setString(6, comercio.getEndereco().getEstado());
        comand.setString(7, comercio.getEndereco().getPais());
        comand.execute();
        con.close();
    }
     
         public List<Comercio> Consultar() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();

        PreparedStatement comando = con.prepareStatement("select nome, cnpj, cep, logradouro, cidade, estado, pais from tbl_comercio");
        ResultSet resultado = comando.executeQuery();

        List<Comercio> todosComercios = new ArrayList<>();
        while (resultado.next()) {
            Comercio c = new Comercio();
            c.setNome(resultado.getString("nome"));
            c.setCnpj(resultado.getInt("cnpj"));
            Endereco endereco = new Endereco();
            endereco.setCep(resultado.getInt("cep"));
            endereco.setLogradouro(resultado.getString("logradouro"));
            endereco.setCidade(resultado.getString("cidade"));
            endereco.setEstado(resultado.getString("estado"));
            endereco.setPais(resultado.getString("pais"));
            todosComercios.add(c);
        }
        con.close();
        return todosComercios;
    }
         
          public void Editar(Comercio comercio) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();
        PreparedStatement comand = con.prepareStatement("update tbl_comercio set nome = ? ,cnpj=? ,cep=? ,logradouro=? ,cidade=? , estado=?, pais=? where id = ?");
        comand.setString(1, comercio.getNome());
        comand.setInt(2, comercio.getCnpj());
        comand.setInt(3, comercio.getEndereco().getCep());
        comand.setString(4, comercio.getEndereco().getLogradouro());
        comand.setString(5, comercio.getEndereco().getCidade());
        comand.setString(6, comercio.getEndereco().getEstado());
        comand.setString(7, comercio.getEndereco().getPais());
        comand.setInt(8, comercio.getId());
        comand.execute();
        con.close();
    }
          
          public void Excluir(Comercio comercio) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();
        PreparedStatement comand = con.prepareStatement("delete from tbl_comercio where id=?");
        comand.setInt(1, comercio.getId());
        comand.execute();
        con.close();
    }
          
          
           public void ConsultarId(Comercio comercio) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();
        PreparedStatement comando = con.prepareStatement("select id, nome, cnpj  from tbl_comercio where id=?");
        comando.setInt(1, comercio.getId());
        ResultSet resultado = comando.executeQuery();

        if (resultado.next()) {
            comercio.setId(resultado.getInt("id"));
            comercio.setNome(resultado.getString("nome"));
            comercio.setCnpj(resultado.getInt("Cnpj"));
           
        }
        
        con.close();
    }
    
}
