package br.com.estudos.forum.repository;

import br.com.estudos.forum.modelo.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Perfil findByNome(String role);
}
