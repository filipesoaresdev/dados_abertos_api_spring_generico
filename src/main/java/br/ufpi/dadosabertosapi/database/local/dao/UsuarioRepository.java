package br.ufpi.dadosabertosapi.database.local.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpi.dadosabertosapi.database.local.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByLogin(String login);
}
