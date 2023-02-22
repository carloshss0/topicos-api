package br.com.estudos.forum.controller.form;

import br.com.estudos.forum.modelo.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SignInForm {

    @NotBlank
    @NotNull
    private String usuario;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String senha;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario converter() {
        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        String senhaCriptografada = criptografar.encode(senha);
        return new Usuario(usuario, email, senhaCriptografada);

    }
}
