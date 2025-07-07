package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import daoImpl.Conexion;
import daoImpl.CuotaImpl;
import daoImpl.PrestamoImpl;
import entidad.Prestamo;

@WebServlet("/AutorizacionPrestamoServlet")
public class AutorizacionPrestamoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AutorizacionPrestamoServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String idPrest = request.getParameter("idPrestamo");

        if (idPrest == null || accion == null) {
            response.sendRedirect("ListarPrestamoServlet?error=parametros");
            return;
        }

        int idPrestamo;
        try {
            idPrestamo = Integer.parseInt(idPrest);
        } catch (NumberFormatException e) {
            response.sendRedirect("ListarPrestamoServlet?error=formato");
            return;
        }

        Connection conn = Conexion.getConexion().getSQLConexion();
        PrestamoImpl prestamoDao = new PrestamoImpl(conn);
        CuotaImpl cuotaDao = new CuotaImpl(conn);

        Prestamo prestamo = prestamoDao.readById(idPrestamo);
        if (prestamo == null) {
            response.sendRedirect("ListarPrestamoServlet?error=noexiste");
            return;
        }

        try {
            if (accion.equals("aprobar")) {
                // Actualizar estado
                prestamo.setEstado(true);
                prestamo.setAprobado(true);
                prestamo.setFinalizado(false);

                boolean actualizado = prestamoDao.actualizarEstado(prestamo);

                if (!actualizado) {
                    conn.rollback();
                    response.sendRedirect("ListarPrestamoServlet?error=update");
                    return;
                }

                boolean cuotasOk = cuotaDao.generarCuotas(prestamo);
                if (!cuotasOk) {
                    conn.rollback();
                    response.sendRedirect("ListarPrestamoServlet?error=cuotas");
                    return;
                }

                conn.commit();
                response.sendRedirect("ListarPrestamoServlet?success=aprobado");

            } else if (accion.equals("rechazar")) {
                prestamo.setEstado(true);
                prestamo.setAprobado(false);
                prestamo.setFinalizado(false);

                boolean actualizado = prestamoDao.actualizarEstado(prestamo);
                if (actualizado) {
                    conn.commit();
                    response.sendRedirect("ListarPrestamoServlet?success=rechazado");
                } else {
                    conn.rollback();
                    response.sendRedirect("ListarPrestamoServlet?error=update");
                }
            } else {
                response.sendRedirect("ListarPrestamoServlet?error=accion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.sendRedirect("ListarPrestamoServlet?error=excepcion");
        }
    }
}
