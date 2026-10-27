package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.Usuario;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/** Repositório didático: não usar senhas em texto puro em produção. */
public class UsuarioMemoriaRepository {
    private static final Map<String, Usuario> USUARIOS = new HashMap<>();
    private static final Map<String, String> SENHAS = new HashMap<>();

    static {
        cadastrar("ana@loja.com", "123456", "Ana Beatriz Santos");
        cadastrar("carlos@loja.com", "123456", "Carlos Lima");
    }

    private static void cadastrar(String email, String senha, String nome) {
        USUARIOS.put(email, new Usuario(nome, email));
        SENHAS.put(email, senha);
    }

    public Usuario autenticar(String email, String senha) {
        if (email == null || senha == null) return null;
        String chave = email.trim().toLowerCase(Locale.ROOT);
        return senha.equals(SENHAS.get(chave)) ? USUARIOS.get(chave) : null;
    }
}
