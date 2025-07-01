package servlet; 

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDao;
import daoImpl.Conexion;
import daoImpl.UsuarioImpl;
import entidad.Usuario;

@WebServlet("/admin/usuarios")
public class GestionUsuariosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GestionUsuariosServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = Conexion.getConexion().getSQLConexion();
            UsuarioDao usuarioDao = new UsuarioImpl(conn);
            List<Usuario> listaUsuarios = usuarioDao.ReadAll();
            
            System.out.println("DEBUG: Cantidad de usuarios encontrados en el DAO: " + listaUsuarios.size());
            
            request.setAttribute("listaUsuarios", listaUsuarios);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ListaUser.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            String action = request.getParameter("action");
            
            if ("delete".equals(action)) {
                conn = Conexion.getConexion().getSQLConexion();
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                
                UsuarioDao usuarioDao = new UsuarioImpl(conn);
                usuarioDao.logicalDelete(idUsuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/listarClientes");

    }
}
