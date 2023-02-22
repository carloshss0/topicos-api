package br.com.estudos.forum.controller.dto;

import br.com.estudos.forum.modelo.Usuario;

public class UsuarioDto {

    private String nome;
    private String email;
    private String senha;

    public UsuarioDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
