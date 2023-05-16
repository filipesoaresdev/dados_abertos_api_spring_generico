package br.ufpi.dadosabertosapi.config;

import br.ufpi.dadosabertosapi.services.UsuarioDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    @Autowired
    private UsuarioDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUTIL jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");


        String username = null;
        String jwt = null;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken uToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    uToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(uToken);


                }
            }
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            e.printStackTrace();
            response = getErrorValidade(response);
        }

    }

    private HttpServletResponse getErrorValidade(HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseString = objectMapper.writeValueAsString(new Exception("A chave não é válida ou expirou."));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.SC_FORBIDDEN);
        PrintWriter out = response.getWriter();
        out.print(responseString);
        out.close();
        return response;
    }

}
