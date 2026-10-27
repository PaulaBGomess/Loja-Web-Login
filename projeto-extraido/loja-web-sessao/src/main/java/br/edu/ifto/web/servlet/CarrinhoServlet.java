package br.edu.ifto.web.servlet;

import br.edu.ifto.web.model.ItemCarrinho;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession(false);
        List<ItemCarrinho> carrinho = sessao == null ? null : obterCarrinho(sessao);
        List<ItemCarrinho> itens;
        int quantidadeTotal;
        BigDecimal valorTotal;
        if (carrinho == null) {
            itens = Collections.emptyList();
            quantidadeTotal = 0;
            valorTotal = BigDecimal.ZERO;
        } else {
            synchronized (sessao) {
                itens = new ArrayList<>(carrinho);
                quantidadeTotal = itens.stream().mapToInt(ItemCarrinho::getQuantidade).sum();
                valorTotal = itens.stream().map(ItemCarrinho::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }

        request.setAttribute("itensCarrinho", itens);
        request.setAttribute("quantidadeTotal", quantidadeTotal);
        request.setAttribute("valorTotal", valorTotal);
        request.getRequestDispatcher("/WEB-INF/views/carrinho.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sessao = request.getSession(false);
        if (sessao != null) sessao.removeAttribute("carrinho");
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @SuppressWarnings("unchecked")
    private List<ItemCarrinho> obterCarrinho(HttpSession sessao) {
        Object valor = sessao.getAttribute("carrinho");
        return valor instanceof List ? (List<ItemCarrinho>) valor : null;
    }
}
