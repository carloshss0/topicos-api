package br.com.estudos.forum.controller;

import br.com.estudos.forum.controller.dto.UsuarioDto;
import br.com.estudos.forum.controller.form.SignInForm;
import br.com.estudos.forum.modelo.Perfil;
import br.com.estudos.forum.modelo.Usuario;
import br.com.estudos.forum.repository.PerfilRepository;
import br.com.estudos.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/signin")
public class SignInController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioDto> signIn(@RequestBody @Valid SignInForm form, UriComponentsBuilder uriBuilder) {
        Usuario usuario = form.converter();
        String email = usuario.getEmail();
        String nome = usuario.getNome();

        Optional<Usuario> possivelUsuariocomNomeCadastrado = usuarioRepository.findByNome(nome);
        Optional<Usuario> possivelUsuariocomEmailCadastrado = usuarioRepository.findByEmail(email);

        if (possivelUsuariocomNomeCadastrado.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        if (possivelUsuariocomEmailCadastrado.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Perfil perfil = perfilRepository.findByNome("ROLE_ALUNO");
        usuario.adicionarPerfil(perfil);

        usuarioRepository.save(usuario);
        return ResponseEntity.ok().body(new UsuarioDto(usuario));

    }
}
