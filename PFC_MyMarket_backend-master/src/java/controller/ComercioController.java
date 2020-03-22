/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ComercioDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Comercio;
import model.Endereco;

/**
 *
 * @author gusta
 */
@WebServlet(name = "ComercioController", urlPatterns = {"/CadastrarComercio", "/ComercioListar", "/deletarComercio", "/editarComercio"})
public class ComercioController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        try {
            String uri = request.getRequestURI();

            if (uri.equals(request.getContextPath() + "/deletarComercio")) {
                excluir(request, response);
            } else if (uri.equals(request.getContextPath() + "/editarComercio")) {
                iniciarAlteracao(request, response);
            } else if (uri.equals(request.getContextPath() + "/listarComercio")) {
                listarTodos(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(ComercioController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        try {
            String uri = request.getRequestURI();

            if (uri.equals(request.getContextPath() + "/CadastrarComercio")) {
                cadastrar(request, response);
                alterar(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(ComercioController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
        Comercio comercio = new Comercio();
        comercio.setNome(request.getParameter("nome"));
        comercio.setCnpj(Integer.parseInt(request.getParameter("cnpj")));

        Endereco endereco = new Endereco();

        if (request.getParameter("cep") != null) {
            endereco.setCep(Integer.parseInt(request.getParameter("cep")));
        }

        endereco.setLogradouro(request.getParameter("logradouro"));
        endereco.setCidade(request.getParameter("cidade"));
        endereco.setEstado(request.getParameter("estado"));
        endereco.setPais(request.getParameter("pais"));

        comercio.setEndereco(endereco);

        ComercioDAO comercioDAO = new ComercioDAO();

        // -- Gerar JSON
        String json = new Gson().toJson(comercio);
        //System.out.println("json:"+json);
        try {
            ComercioDAO.Cadastrar(comercio);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try {
                response.getWriter().write(json);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void iniciarAlteracao(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, ServletException {
        Comercio comercio = new Comercio();
        comercio.setId(Integer.parseInt(request.getParameter("id")));

        ComercioDAO dao = new ComercioDAO();
        dao.ConsultarId(comercio);
        request.setAttribute("comercio", comercio);

    }

    private void alterar(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, ServletException {
        Comercio comercio = new Comercio();
        comercio.setNome(request.getParameter("nome"));
        comercio.setCnpj(Integer.parseInt(request.getParameter("cnpj")));

        Endereco endereco = new Endereco();

        if (request.getParameter("cep") != null) {
            endereco.setCep(Integer.parseInt(request.getParameter("cep")));
        }

        endereco.setLogradouro(request.getParameter("logradouro"));
        endereco.setCidade(request.getParameter("cidade"));
        endereco.setEstado(request.getParameter("estado"));
        endereco.setPais(request.getParameter("pais"));

        comercio.setEndereco(endereco);
        ComercioDAO dao = new ComercioDAO();
     
        dao.Editar(comercio);
//            request.setAttribute("produto", produto);
//            request.getRequestDispatcher("AlterarProduto.jsp").forward(request, response);
        listarTodos(request, response);
    }
    
    private void excluir(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException{
            Comercio comercio = new Comercio();
            comercio.setId(Integer.parseInt(request.getParameter("id")));
            
            
            ComercioDAO dao = new ComercioDAO();
            dao.Excluir(comercio);
            
    }
    
    private void listarTodos(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException, ServletException{
            Comercio comercio = new Comercio();
            ComercioDAO dao = new ComercioDAO();
            List<Comercio> lista = dao.Consultar();
            request.setAttribute("lista",lista);
            
            
        }
}
