package py.com.fuentepy.appfinanzasBackend.resource.auth;

import org.springframework.http.HttpStatus;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.exception.BadRequestException;
import py.com.fuentepy.appfinanzasBackend.data.entity.AuthProvider;
import py.com.fuentepy.appfinanzasBackend.payload.ApiResponse;
import py.com.fuentepy.appfinanzasBackend.payload.SignUpRequest;
import py.com.fuentepy.appfinanzasBackend.data.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginModel loginModel = new LoginModel(tokenProvider.createToken(authentication));
        httpStatus = HttpStatus.OK;
        message = new MessageResponse(StatusLevel.INFO, "Autenticacion correcta");
        messages.add(message);
        response = new LoginResponse(httpStatus.value(), messages, loginModel);
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating usuario's account
        Usuario usuario = new Usuario();
        usuario.setName(signUpRequest.getName());
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setPassword(signUpRequest.getPassword());
        usuario.setProvider(AuthProvider.local);

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario result = usuarioRepository.save(usuario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/usuario/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Usuario registered successfully"));
    }

}
