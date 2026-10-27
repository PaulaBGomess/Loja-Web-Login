package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.ProdutoMemoriaRepository;
import br.edu.ifto.web.model.ItemCarrinho;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {
    private final ProdutoMemoriaRepository repository = new ProdutoMemoriaRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("produtos", repository.listarTodos());
        HttpSession sessao = request.getSession(false);
        List<ItemCarrinho> carrinho = sessao == null ? Collections.emptyList() : obterCarrinho(sessao);
        int quantidadeTotal = carrinho.stream().mapToInt(ItemCarrinho::getQuantidade).sum();
        request.setAttribute("quantidadeTotal", quantidadeTotal);
        request.getRequestDispatcher("/WEB-INF/views/produtos.jsp").forward(request, response);
    }

    @SuppressWarnings("unchecked")
    private List<ItemCarrinho> obterCarrinho(HttpSession sessao) {
        Object valor = sessao.getAttribute("carrinho");
        return valor instanceof List ? (List<ItemCarrinho>) valor : Collections.emptyList();
    }
}
