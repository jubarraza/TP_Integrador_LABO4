package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.PrestamoDao;
import daoImpl.PrestamoImpl;
import entidad.Prestamo;
import entidad.Usuario;

@WebServlet("/MisPrestamosServlet")
public class MisPrestamosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        PrestamoDao prestamoDao = new PrestamoImpl();
        List<Prestamo> listaPrestamos = prestamoDao.readAllByClienteId(usuario.getIdcliente());

        request.setAttribute("listaPrestamos", listaPrestamos);
        request.getRequestDispatcher("/ListadoDePrestamos.jsp").forward(request, response);
    }
}
