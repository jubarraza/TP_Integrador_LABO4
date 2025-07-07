package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CuotaDao;
import daoImpl.CuotaImpl;
import entidad.Cuota;

@WebServlet("/DetallePrestamoServlet")
public class DetallePrestamoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPrestamo = 0;
        try {
            idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        CuotaDao cuotaDao = new CuotaImpl();
        List<Cuota> listaCuotas = cuotaDao.readAllByPrestamoId(idPrestamo);
        
        request.setAttribute("listaCuotas", listaCuotas);
        request.getRequestDispatcher("/CuotasPendientes.jsp").forward(request, response);
    }
}