package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.ProdutoMemoriaRepository;
import br.edu.ifto.web.model.ItemCarrinho;
import br.edu.ifto.web.model.Produto;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/carrinho/itens")
public class CarrinhoItemServlet extends HttpServlet {
    private final ProdutoMemoriaRepository repository = new ProdutoMemoriaRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = lerId(request);
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id do produto inválido.");
            return;
        }
        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Produto não encontrado.");
            return;
        }

        HttpSession sessao = request.getSession();
        synchronized (sessao) {
            List<ItemCarrinho> carrinho = obterOuCriarCarrinho(sessao);
            ItemCarrinho existente = carrinho.stream()
                    .filter(item -> item.getProduto().getId() == id)
                    .findFirst().orElse(null);
            if (existente == null) carrinho.add(new ItemCarrinho(produto, 1));
            else existente.incrementarQuantidade();
        }

        response.sendRedirect(request.getContextPath() + "/carrinho");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = lerId(request);
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id do produto inválido.");
            return;
        }
        HttpSession sessao = request.getSession(false);
        if (sessao != null) {
            synchronized (sessao) {
                List<ItemCarrinho> carrinho = obterCarrinho(sessao);
                if (carrinho != null) carrinho.removeIf(item -> item.getProduto().getId() == id);
            }
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private Integer lerId(HttpServletRequest request) {
        try { return Integer.valueOf(request.getParameter("id")); }
        catch (NumberFormatException | NullPointerException e) { return null; }
    }

    @SuppressWarnings("unchecked")
    private List<ItemCarrinho> obterOuCriarCarrinho(HttpSession sessao) {
        List<ItemCarrinho> carrinho = obterCarrinho(sessao);
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            sessao.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    @SuppressWarnings("unchecked")
    private List<ItemCarrinho> obterCarrinho(HttpSession sessao) {
        Object valor = sessao.getAttribute("carrinho");
        return valor instanceof List ? (List<ItemCarrinho>) valor : null;
    }
}
