package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.CuentaImpl;
import entidad.Usuario;


@WebServlet("/EliminarCuentaServlet")
public class EliminarCuentaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public EliminarCuentaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String numCuenta = request.getParameter("numCuenta");

		    if (numCuenta != null && !numCuenta.isEmpty()) {
		        CuentaImpl cuentaDao = new CuentaImpl();
		        cuentaDao.darDeBajaCuenta(numCuenta);
		    }

		    response.sendRedirect("ListarCuentasServlet?filtroEstado=ACTIVA");
	}

}
