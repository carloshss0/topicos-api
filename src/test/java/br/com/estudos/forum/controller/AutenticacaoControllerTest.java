package br.com.estudos.forum.controller;

import br.com.estudos.forum.modelo.Usuario;
import br.com.estudos.forum.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository repository;

    @Test
    public void deveriaDevolver400CasoDadosDeAutenticacaoEstejamIncorretos() throws Exception {
        URI uri = new URI("/auth");
        String json = "{\"email\":\"invalido@email.com\",\"senha\":\"123456\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));


    }

    @Test
    public void deveriaDevolver200CasoDadosDeAutenticacaoEstejamCorretos() throws Exception {

        Usuario aluno = new Usuario();

        aluno.setNome("Aluno");
        aluno.setEmail("aluno@email.com");
        aluno.setSenha("$2a$10$F1OoFeeCxH2nC9///hARB.Dgycmi2LyluUwnipyipu5Q46dC71Jca");

        repository.save(aluno);

        URI uri = new URI("/auth");
        String json = "{\"email\":\"aluno@email.com\",\"senha\":\"123456\"}";


        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void deveriaDevolver400CasoSenhaEstejaIncorreta() throws Exception {

        Usuario aluno = new Usuario();

        aluno.setNome("Aluno");
        aluno.setEmail("aluno@email.com");
        aluno.setSenha("$2a$10$F1OoFeeCxH2nC9///hARB.Dgycmi2LyluUwnipyipu5Q46dC71Jca");

        repository.save(aluno);

        URI uri = new URI("/auth");
        String json = "{\"email\":\"aluno@email.com\",\"senha\":\"testesenhaincorreta\"}";


        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }


}