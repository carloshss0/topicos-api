package br.com.estudos.forum.repository;

import br.com.estudos.forum.modelo.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
}
