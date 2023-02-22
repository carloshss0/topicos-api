package br.com.estudos.forum.controller;

import br.com.estudos.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.estudos.forum.controller.dto.RespostaDto;
import br.com.estudos.forum.controller.dto.TopicoDto;
import br.com.estudos.forum.controller.form.AtualizacaoTopicoForm;
import br.com.estudos.forum.controller.form.RespostaForm;
import br.com.estudos.forum.controller.form.TopicoForm;
import br.com.estudos.forum.modelo.Resposta;
import br.com.estudos.forum.modelo.Topico;
import br.com.estudos.forum.repository.CursoRepository;
import br.com.estudos.forum.repository.RespostaRepository;
import br.com.estudos.forum.repository.TopicoRepository;
import br.com.estudos.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaRepository respostaRepository;



    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {


        if (nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
            return TopicoDto.converter(topicos);
        }
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {

        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,@RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/{idTopico}/resposta")
    @Transactional
    public ResponseEntity<RespostaDto> responder(@PathVariable Long idTopico, @RequestBody @Valid RespostaForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoRepository.getOne(idTopico);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String autorDaResposta = authentication.getName();
        Resposta resposta = form.converter(autorDaResposta, usuarioRepository, topico);
        respostaRepository.save(resposta);
        topico.adicionarResposta(resposta);

        URI uri = uriBuilder.path("/topicos/{idTopico}/resposta/{idResposta}").buildAndExpand(topico.getId(), resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new RespostaDto(resposta));
    }




}
