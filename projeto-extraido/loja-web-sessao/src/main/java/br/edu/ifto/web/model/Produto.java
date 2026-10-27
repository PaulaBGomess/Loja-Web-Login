package br.edu.ifto.web.model;

import java.math.BigDecimal;

public class Produto {
    private final int id;
    private final String nome;
    private final String descricao;
    private final BigDecimal preco;

    public Produto(int id, String nome, String descricao, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
}
