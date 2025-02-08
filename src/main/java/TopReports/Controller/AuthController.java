package TopReports.Controller;

import TopReports.Dto.UsuarioDTO;
import TopReports.Repository.UsuarioRepository;
import TopReports.Segurity.JwtUtils;
import TopReports.Service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    private final UsuarioService usuarioService;



    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }



    @PostMapping("/registro")
    public ResponseEntity<String> register(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.registro(usuarioDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) {
        String token = usuarioService.login(usuarioDTO);
        if (token != null) {
            return ResponseEntity.ok("Login realizado com sucesso! Token: " + token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
        }
    }

    @GetMapping("/Eu")
    public ResponseEntity<UsuarioDTO> getAuthenticatedUser(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (token != null && jwtUtils.isValidToken(token)) {
            Claims claims = jwtUtils.getClaims(token);
            Long userId = claims.get("id", Long.class);

            if (userId != null) {
                UsuarioDTO usuarioDTO = usuarioService.getAuthenticatedUser(userId);
                return ResponseEntity.ok(usuarioDTO);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}


