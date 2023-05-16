package br.ufpi.dadosabertosapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.ufpi.dadosabertosapi.config.JwtUTIL;
import br.ufpi.dadosabertosapi.config.models.AuthenticationRequest;
import br.ufpi.dadosabertosapi.config.models.AuthenticationResponse;
import br.ufpi.dadosabertosapi.services.UsuarioDetailsServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("auth")
public class AuthResource {
	
	@Autowired
	private AuthenticationManager authenticationManagerBean;
	
	@Autowired
	private UsuarioDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtUTIL jwtUtil;

	//@RequestMapping(value="/jwt",method =  RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value="/jwt",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
			authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}catch (BadCredentialsException e) {
			throw new Exception("Nome do usuário ou senha inválido");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtUtil.generateToken(userDetails);
		
		
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
}
