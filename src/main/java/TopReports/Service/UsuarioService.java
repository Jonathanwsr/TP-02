package TopReports.Service;

import TopReports.Dto.UsuarioDTO;
import TopReports.Enity.Usuarios;
import TopReports.Repository.UsuarioRepository;
import TopReports.Segurity.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return usuarioRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + userName));
    }

    public ResponseEntity<String> registro(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByUserName(usuarioDTO.getUserName()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe!");

        }

        Usuarios usuario = new Usuarios();
        usuario.setUserName(usuarioDTO.getUserName());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setRole("USER");

        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    public String login(UsuarioDTO usuarioDTO) {
        Optional<Usuarios> optionalUsuario = usuarioRepository.findByUserName(usuarioDTO.getUserName());

        if (optionalUsuario.isPresent()) {
            Usuarios usuario = optionalUsuario.get();
            if (passwordEncoder.matches(usuarioDTO.getPassword(), usuario.getPassword())) {
                return Jwts.builder()
                        .setSubject(usuarioDTO.getUserName())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia de validade
                        .signWith(SignatureAlgorithm.HS512, "secret_key")
                        .compact();
            }
        }
        return null;
    }

    public UsuarioDTO getAuthenticatedUser(Long id) {
        Usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com ID: " + id));

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUserName(usuario.getUserName());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setRole(usuario.getRole());

        return usuarioDTO;
    }

}
