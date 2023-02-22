package br.com.estudos.forum.controller.form;

import br.com.estudos.forum.modelo.Resposta;
import br.com.estudos.forum.modelo.Topico;
import br.com.estudos.forum.modelo.Usuario;
import br.com.estudos.forum.repository.UsuarioRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class RespostaForm {

    @NotNull
    @NotEmpty
    @Length(min = 10)
    private String mensagem;


    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Resposta converter(String usuarioDoAutor, UsuarioRepository usuarioRepository, Topico topico) {
        Optional<Usuario> autor = usuarioRepository.findByEmail(usuarioDoAutor);
        return new Resposta(mensagem, autor.get(), topico);
    // tem que retornar um objeto Resposta.
    }
}
