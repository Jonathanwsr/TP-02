package TopReports.Segurity;

import TopReports.Service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Extrai o token do cabeçalho Authorization
            String token = jwtUtils.extractToken(request);

            if (token != null && jwtUtils.isValidToken(token)) {
                // Extrai o username do token
                String username = jwtUtils.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Carrega os detalhes do usuário
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtils.isValidToken(token)) { // Validação extra do token
                        // Cria a autenticação e configura no contexto de segurança
                        var authentication = jwtUtils.getAuthentication(userDetails);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            // Log de erro para facilitar o debug
            System.err.println("Erro ao processar o filtro JWT: " + e.getMessage());
        }

        // Continue o processamento do restante do filtro
        filterChain.doFilter(request, response);
    }
}
