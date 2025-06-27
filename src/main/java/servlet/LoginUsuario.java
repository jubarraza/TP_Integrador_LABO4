package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.UsuarioImpl;
import entidad.Usuario;

@WebServlet("/LoginUsuario")
public class LoginUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginUsuario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		    try {
		        // LOGOUT
		        if (request.getParameter("btnLogout") != null) {
		            request.getSession().setAttribute("usuarioLogin", null); // cierra la sesión
		            response.sendRedirect("InicioSesion.jsp");
		            return; // corta la ejecución
		        }

		        // LOGIN
		        if (request.getParameter("btnLogin") != null) {
		            String username = request.getParameter("username");
		            String password = request.getParameter("password");

		            UsuarioImpl usuarioImpl = new UsuarioImpl();
		            Usuario usuario = usuarioImpl.Autenticar(username, password);

		            if (usuario != null) {
		                request.getSession().setAttribute("usuarioLogin", usuario);
		                response.sendRedirect("Home.jsp");
		            } else {
		                // usuario o clave incorrectos
		                request.setAttribute("errorLogin", "Usuario o contraseña incorrectos.");
		                request.getRequestDispatcher("InicioSesion.jsp").forward(request, response);
		            }
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		

		
	}

}
