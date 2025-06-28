package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.ClienteDao;
import dao.LocalidadDao;
import daoImpl.ClienteImpl;
import daoImpl.Conexion;
import daoImpl.LocalidadImpl;
import entidad.Cliente;
import entidad.Localidad;
import entidad.Usuario;

@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PerfilServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            mostrarFormularioEdicion(request, response);
        } else {
            mostrarPerfil(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            Conexion conexionDB = Conexion.getConexion();
            conn = conexionDB.getSQLConexion();

            Cliente cliente = new Cliente();
            cliente.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
            cliente.setDireccion(request.getParameter("direccion"));
            cliente.setCorreo(request.getParameter("correo"));
            cliente.setTelefono(request.getParameter("telefono"));

            Localidad loc = new Localidad();
            loc.setIdLocalidad((short) Integer.parseInt(request.getParameter("localidad")));
            cliente.setLocalidad(loc);

            ClienteDao clienteDao = new ClienteImpl(conn);
            boolean exito = clienteDao.update(cliente);

            if (exito) {
                request.setAttribute("mensaje", "Perfil actualizado con éxito.");
            } else {
                request.setAttribute("mensaje", "Error al actualizar el perfil.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error inesperado.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        mostrarPerfil(request, response);
    }

    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = Conexion.getConexion().getSQLConexion();
            HttpSession session = request.getSession();
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");

            if (usuarioLogueado == null) {
                response.sendRedirect("/ListaUser.jsp");
                return;
            }

            ClienteDao clienteDao = new ClienteImpl(conn);
            Cliente cliente = clienteDao.ReadOne(usuarioLogueado.getIdcliente());

            request.setAttribute("cliente", cliente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ListaUser.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = Conexion.getConexion().getSQLConexion();
            ClienteDao clienteDao = new ClienteImpl(conn);

            String idUsuarioParam = request.getParameter("idUsuario");
            Cliente cliente;

            if (idUsuarioParam != null) {
                int idUsuario = Integer.parseInt(idUsuarioParam);
                cliente = clienteDao.getPorIdUsuario(idUsuario);
            } else {
                HttpSession session = request.getSession();
                Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");

                if (usuarioLogueado == null) {
                    response.sendRedirect("/ListaUser.jsp");
                    return;
                }

                cliente = clienteDao.ReadOne(usuarioLogueado.getIdcliente());
            }

            LocalidadDao localidadDao = new LocalidadImpl(conn);
            List<Localidad> listaLocalidades = localidadDao.readAll();

            request.setAttribute("cliente", cliente);
            request.setAttribute("listaLocalidades", listaLocalidades);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/EditarPerfil.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
