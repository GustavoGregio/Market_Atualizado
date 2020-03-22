/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import model.Produto;
import Util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void Cadastrar(Produto produto) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();

        PreparedStatement comando = con.prepareStatement("insert into tbl_produto (descricao, preco) values (?,?)");
        comando.setString(1, produto.getDescricao());
        comando.setDouble(2,produto.getPreco());
        comando.execute();
        con.close();
    }

    public List<Produto> Consultar() throws ClassNotFoundException, SQLException {
        Connection con = ConnectionFactory.getConexao();

        PreparedStatement comando = con.prepareStatement("select id, descricao, preco from tbl_produto");
        ResultSet resultado = comando.executeQuery();

        List<Produto> todosProdutos = new ArrayList<>();
        while (resultado.next()) {
            Produto p = new Produto();
            p.setId(resultado.getInt("id"));
            p.setDescricao(resultado.getString("descricao"));
            p.setPreco(resultado.getDouble("preco"));
            todosProdutos.add(p);
        }
        con.close();
        return todosProdutos;
    }

}