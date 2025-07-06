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
import daoImpl.TransferenciaImpl;
import entidad.Cuenta;
import entidad.Transferencia;
import entidad.Usuario;

@WebServlet("/TransferenciaCbuServlet")
public class TransferenciaCbuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public TransferenciaCbuServlet() {
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
		    request.getRequestDispatcher("TransferenciaCbu.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");

	    if (usuarioLogueado == null) {
	        response.sendRedirect("InicioSesion.jsp");
	        return;
	    }

	    try {
	        String cuentaOrigen = request.getParameter("cuentaOrigen");
	        String cbuDestino = request.getParameter("cbu");
	        String detalle = request.getParameter("detalle");
	        String montoStr = request.getParameter("monto");

	        if (cuentaOrigen == null || cbuDestino == null || montoStr == null ||
	            cuentaOrigen.trim().isEmpty() || cbuDestino.trim().isEmpty() || montoStr.trim().isEmpty()) {
	            response.sendRedirect("Transferencias.jsp?mensaje=error");
	            return;
	        }

	        double monto = Double.parseDouble(montoStr);
	        if (monto <= 0) {
	            response.sendRedirect("Transferencias.jsp?mensaje=montoInvalido");
	            return;
	        }

	        CuentaImpl cuentaDao = new CuentaImpl();
	        Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorCBU(cbuDestino);

	        if (cuentaDestino == null) {
	            response.sendRedirect("Transferencias.jsp?mensaje=cbuInvalido");
	            return;
	        }

	        String numCuentaDestino = cuentaDestino.getNumDeCuenta();

	        List<Cuenta> cuentasCliente = cuentaDao.readAllByClienteId(usuarioLogueado.getIdcliente());

	        boolean esCuentaPropia = cuentasCliente.stream()
	            .anyMatch(c -> c.getNumDeCuenta().equals(numCuentaDestino));

	        if (esCuentaPropia) {
	            response.sendRedirect("Transferencias.jsp?mensaje=cbuPropio");
	            return;
	        }


	        if (cuentaOrigen.equals(numCuentaDestino)) {
	            response.sendRedirect("Transferencias.jsp?mensaje=mismaCuenta");
	            return;
	        }

	        Transferencia transferencia = new Transferencia(cuentaOrigen, numCuentaDestino, monto);
	        TransferenciaImpl dao = new TransferenciaImpl();

	        boolean exito = dao.Insert(transferencia, detalle);

	        if (exito) {
	            response.sendRedirect("Transferencias.jsp?mensaje=ok");
	        } else {
	            response.sendRedirect("Transferencias.jsp?mensaje=error");
	        }

	    } catch (NumberFormatException e) {
	        response.sendRedirect("Transferencias.jsp?mensaje=montoInvalido");
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("Transferencias.jsp?mensaje=error");
	    }
	}

}
