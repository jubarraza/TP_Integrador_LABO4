package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import conexion.Conexion;
import entidad.Cliente;
import entidad.Localidad;
import entidad.Usuario;
import negocio.negocioCliente;
import negocioImpl.negocioClienteImpl;
import negocioImpl.negocioLocalidadImpl;

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
            
            //ClienteDao clienteDao = new ClienteImpl(conn);
            negocioCliente clienteNegocio = new negocioClienteImpl(conn);
            boolean exito = clienteNegocio.update(cliente);

            if (exito) {
                request.setAttribute("mensaje", "Perfil actualizado con éxito.");
            } else {
                request.setAttribute("mensaje", "Error al actualizar el perfil.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error inesperado.");
        }
        response.sendRedirect(request.getContextPath() + "/listarClientes");
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

            //ClienteDao clienteDao = new ClienteImpl(conn);
            negocioCliente clienteNegocio = new negocioClienteImpl(conn);
            Cliente cliente = clienteNegocio.ReadOne(usuarioLogueado.getIdcliente());

            request.setAttribute("cliente", cliente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ListaUser.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = Conexion.getConexion().getSQLConexion();
            //ClienteDao clienteDao = new ClienteImpl(conn);
            negocioCliente clienteNegocio = new negocioClienteImpl(conn);

            String idUsuarioParam = request.getParameter("idUsuario");
            Cliente cliente;

            if (idUsuarioParam != null) {
                int idUsuario = Integer.parseInt(idUsuarioParam);
                cliente = clienteNegocio.getPorIdUsuario(idUsuario);
            } else {
                HttpSession session = request.getSession();
                Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");

                if (usuarioLogueado == null) {
                    response.sendRedirect("/ListaUser.jsp");
                    return;
                }

                cliente = clienteNegocio.ReadOne(usuarioLogueado.getIdcliente());
            }

            negocioLocalidadImpl localidadNegocio = new negocioLocalidadImpl(conn);
            List<Localidad> listaLocalidades = localidadNegocio.readAll();

            request.setAttribute("cliente", cliente);
            request.setAttribute("listaLocalidades", listaLocalidades);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/EditarPerfil.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
