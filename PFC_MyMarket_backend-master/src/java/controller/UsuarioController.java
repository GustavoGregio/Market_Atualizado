package controller;

import Util.ConnectionFactory;
import com.google.gson.Gson;
import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Endereco;
import model.Usuario;

@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController", "/CadastrarUsuario", "/teste", "/AutenticarUsuario", "/ConsultarUsuario", "/AlterarUsuario", "/ExcluirUsuario"})
public class UsuarioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.equals(request.getContextPath() + "/teste")) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        if (uri.equals(request.getContextPath() + "/CadastrarUsuario")) {
            Cadastrar(request, response);
        } else if (uri.equals(request.getContextPath() + "/AutenticarUsuario")) {
            Autenticar(request, response);
        }
    }

    void Cadastrar(HttpServletRequest request, HttpServletResponse response) {
        Usuario usuario = new Usuario();
        if (request.getParameter("cnpj") != null)
        usuario.setCnpj(Integer.parseInt(request.getParameter("cnpj")));
        usuario.setEmail(request.getParameter("email"));
        usuario.setNome(request.getParameter("nome"));
        usuario.setSenha(request.getParameter("senha"));

        Endereco endereco = new Endereco();

        if (request.getParameter("cep") != null) {
            endereco.setCep(Integer.parseInt(request.getParameter("cep")));
        }

        endereco.setLogradouro(request.getParameter("logradouro"));
        endereco.setCidade(request.getParameter("cidade"));
        endereco.setEstado(request.getParameter("estado"));
        endereco.setPais(request.getParameter("pais"));

        usuario.setEndereco(endereco);

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // -- Gerar JSON
        String json = new Gson().toJson(usuario);
        //System.out.println("json:"+json);
        try {
            usuarioDAO.Cadastrar(usuario);
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

    void Autenticar(HttpServletRequest request, HttpServletResponse response) {
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getParameter("email"));
        usuario.setSenha(request.getParameter("senha"));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            Usuario usuarioConsultado = usuarioDAO.Validacao(usuario);
            System.out.println("validou usuario");
            if (usuarioConsultado.getSenha() == usuario.getSenha()) {
                System.out.println("usuario autenticado!");
                Usuario usuarioAutenticado = new Usuario();
                usuarioAutenticado = (Usuario) usuarioDAO.ConsultarUsuario(usuario);

                String json = new Gson().toJson(usuarioAutenticado);
                //System.out.println("json:"+json);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                try {
                    response.getWriter().write(json);
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // -- Gerar JSON
        String json = new Gson().toJson(usuario);
        //System.out.println("json:"+json);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.getWriter().write(json);

    }
    
     private void ConsultarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException, ServletException{
         Usuario usuario = new Usuario();
         UsuarioDAO dao = new UsuarioDAO();
         List<Usuario> lista = dao.Consultar();
         request.setAttribute("lista",lista);
        
     }
    
    void AlterarUsuario(HttpServletRequest request, HttpServletResponse response) {
        Usuario usuario = new Usuario();
        if (request.getParameter("cnpj") != null)
        usuario.setCnpj(Integer.parseInt(request.getParameter("cnpj")));
        usuario.setEmail(request.getParameter("email"));
        usuario.setNome(request.getParameter("nome"));
        usuario.setSenha(request.getParameter("senha"));

        Endereco endereco = new Endereco();

        if (request.getParameter("cep") != null) {
            endereco.setCep(Integer.parseInt(request.getParameter("cep")));
        }

        endereco.setLogradouro(request.getParameter("logradouro"));
        endereco.setCidade(request.getParameter("cidade"));
        endereco.setEstado(request.getParameter("estado"));
        endereco.setPais(request.getParameter("pais"));

        usuario.setEndereco(endereco);

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // -- Gerar JSON
        String json = new Gson().toJson(usuario);
        //System.out.println("json:"+json);
        try {
            usuarioDAO.Editar(usuario);
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
    
    
      private void excluirUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException{
            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(request.getParameter("id")));
            
            
            UsuarioDAO dao = new UsuarioDAO();
            dao.Excluir(usuario);
            
            // -- Gerar JSON
        String json = new Gson().toJson(usuario);
        //System.out.println("json:"+json);
        try {
            dao.Editar(usuario);
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
      
}
