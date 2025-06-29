package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.CuentaImpl;
import entidad.Cuenta;
import entidad.TipoDeCuenta;


@WebServlet("/ModificarCuentaServlet")
public class ModificarCuentaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ModificarCuentaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String numeroCuenta = request.getParameter("nroCuenta");

	    CuentaImpl cuentaDao = new CuentaImpl();
	    List<Cuenta> cuentas = cuentaDao.readAll("num_de_cuenta = '" + numeroCuenta + "'");

	    if (!cuentas.isEmpty()) {
	        Cuenta cuenta = cuentas.get(0);
	        request.setAttribute("cuenta", cuenta);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("EditarCuentas.jsp");
	        dispatcher.forward(request, response);
	    } else {
	        response.sendRedirect("ListaDeCuentas.jsp");
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");

	    if ("modificar".equalsIgnoreCase(accion)) {
	        String numeroCuenta = request.getParameter("numeroCuenta");
	        int idTipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));
	        boolean estado = request.getParameter("estado") != null; 

	        TipoDeCuenta tipoDeCuenta = new TipoDeCuenta();
	        tipoDeCuenta.setIdTipoCuenta((short) idTipoCuenta);

	        Cuenta cuenta = new Cuenta();
	        cuenta.setNumDeCuenta(numeroCuenta);
	        cuenta.setTipoCuenta(tipoDeCuenta);
	        cuenta.setEstado(estado);

	        CuentaImpl cuentaDao = new CuentaImpl();
	        boolean exito = cuentaDao.actualizarTipoCuentaYEstado(cuenta);

	        if (exito) {
	            response.sendRedirect("ListarCuentasServlet");
	        } else {
	            request.setAttribute("error", "Error al actualizar la cuenta");
	            request.setAttribute("cuenta", cuenta);
	            request.getRequestDispatcher("EditarCuentas.jsp").forward(request, response);
	        }
	    }
	}
}
