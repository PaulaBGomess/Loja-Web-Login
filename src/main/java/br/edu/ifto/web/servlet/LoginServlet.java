package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.UsuarioMemoriaRepository;
import br.edu.ifto.web.model.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UsuarioMemoriaRepository repository = new UsuarioMemoriaRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Usuario usuario = repository.autenticar(request.getParameter("email"), request.getParameter("senha"));
        if (usuario == null) {
            request.setAttribute("erro", "E-mail ou senha inválida.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        HttpSession anterior = request.getSession(false);
        if (anterior != null) anterior.invalidate();
        HttpSession sessao = request.getSession(true);
        sessao.setAttribute("usuarioLogado", usuario);
        response.sendRedirect(request.getContextPath() + "/");
    }
}
