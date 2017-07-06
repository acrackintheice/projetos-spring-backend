package br.ufsc.framework.services.security;

import projetostcc.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    private static final long serialVersionUID = 1L;

    private final Long idUfsc;
    private final String nome;

    private final Usuario usuarioTCC;

    public CustomUser(String username, String password, boolean isEnabled, Collection<? extends GrantedAuthority> authorities,
                      Long idUfsc, String nome, Usuario usuarioTCC) {
        super(username, password, isEnabled, true, true, true, authorities);
        this.idUfsc = idUfsc;
        this.nome = nome;
        this.usuarioTCC = usuarioTCC;
    }

    public Long getIdUfsc() {
        return idUfsc;
    }

    public String getNome() {
        return nome;
    }

    public Usuario getUsuarioTCC() {
        return usuarioTCC;
    }
}
