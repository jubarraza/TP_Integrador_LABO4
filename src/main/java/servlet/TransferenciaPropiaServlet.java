package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImpl.CuentaImpl;
import entidad.Cuenta;
import entidad.Usuario;


@WebServlet("/TransferenciaPropiaServlet")
public class TransferenciaPropiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public TransferenciaPropiaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
		    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");

		    if (usuarioLogueado == null) {
		        response.sendRedirect("InicioSesion.jsp");
		        return;
		    }

		    int idCliente = usuarioLogueado.getIdcliente();

		    CuentaImpl cuentaDao = new CuentaImpl();
		    List<Cuenta> cuentasCliente = cuentaDao.readAllByClienteId(idCliente);

		    request.setAttribute("cuentasCliente", cuentasCliente);
		    request.getRequestDispatcher("TransferenciaCuenta.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
