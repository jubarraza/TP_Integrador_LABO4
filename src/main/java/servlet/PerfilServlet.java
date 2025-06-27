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
import javax.servlet.http.HttpSession;

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
    	
    	 // -- INICIO DE MODIFICACIÓN PARA PRUEBAS --
        
        // usuario con id_cliente = 2 inicio sesión.
        Usuario usuarioSimulado = new Usuario();
        usuarioSimulado.setIdcliente(2); // ID de Laura González para la prueba
        
        //  usuario simulado en la sesión para testear la modificacion.
        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuarioSimulado);  
     // -- Fin DE MODIFICACIÓN PARA PRUEBAS --
        
    	
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
            Conexion conexionDB = Conexion.getConexion();
            conn = conexionDB.getSQLConexion();

            HttpSession session = request.getSession();
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");
            
            if (usuarioLogueado == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            ClienteDao clienteDao = new ClienteImpl(conn);
            Cliente cliente = clienteDao.ReadOne(usuarioLogueado.getIdcliente()); //
            
            request.setAttribute("cliente", cliente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Profile.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            Conexion conexionDB = Conexion.getConexion();
            conn = conexionDB.getSQLConexion();

            HttpSession session = request.getSession();
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");
            
            if (usuarioLogueado == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            ClienteDao clienteDao = new ClienteImpl(conn);
            Cliente cliente = clienteDao.ReadOne(usuarioLogueado.getIdcliente());
            
            LocalidadDao localidadDao = new LocalidadImpl(conn); 
            List<Localidad> listaLocalidades = localidadDao.readAll();
            
            request.setAttribute("cliente", cliente);
            request.setAttribute("listaLocalidades", listaLocalidades);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/EditarPerfil.jsp"); // Un JSP específico para editar
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

