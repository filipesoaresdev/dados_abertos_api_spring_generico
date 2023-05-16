package br.ufpi.dadosabertosapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufpi.dadosabertosapi.database.local.dao.UsuarioRepository;
import br.ufpi.dadosabertosapi.database.local.model.Usuario;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByLogin(login)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário "+ login + " não encontrado"));
		return new User(
				
				usuario.getLogin(), 
				usuario.getPassword(), 
				usuario.getGrantedAuthoritiesList()
				
				);
		
	}

}
