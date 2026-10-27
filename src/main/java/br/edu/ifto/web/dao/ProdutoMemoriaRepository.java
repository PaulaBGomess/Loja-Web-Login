package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.Produto;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProdutoMemoriaRepository {
    private final List<Produto> produtos = Arrays.asList(
        new Produto(1, "Notebook", "Notebook para trabalho e estudos.", new BigDecimal("3500.00")),
        new Produto(2, "Mouse", "Mouse óptico USB.", new BigDecimal("80.00")),
        new Produto(3, "Teclado Mecânico", "Teclado mecânico para produtividade.", new BigDecimal("250.00")),
        new Produto(4, "Monitor", "Monitor para estação de trabalho.", new BigDecimal("1200.00")),
        new Produto(5, "Headset", "Headset para aulas e reuniões.", new BigDecimal("180.00"))
    );

    public List<Produto> listarTodos() {
        return Collections.unmodifiableList(produtos);
    }

    public Produto buscarPorId(int id) {
        return produtos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
}
