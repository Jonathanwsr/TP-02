package TopReports.Service;

import TopReports.Dto.UsuarioDTO;
import TopReports.Enity.Usuarios;
import TopReports.Repository.UsuarioRepository;
import TopReports.Segurity.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
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

                return jwtUtils.generateToken(usuario.getUserName(), usuario.getId());
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
