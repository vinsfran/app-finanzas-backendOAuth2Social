package py.com.fuentepy.appfinanzasBackend.resource.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import py.com.fuentepy.appfinanzasBackend.data.entity.AuthProvider;
import py.com.fuentepy.appfinanzasBackend.data.entity.Dispositivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Mensaje;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.DispositivoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.MensajeRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.exception.BadRequestException;
import py.com.fuentepy.appfinanzasBackend.payload.ApiResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.resource.fcm.NotificationRequestModel;
import py.com.fuentepy.appfinanzasBackend.security.TokenProvider;
import py.com.fuentepy.appfinanzasBackend.service.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ConceptoService conceptoService;

    @Autowired
    private TipoCobroService tipoCobroService;

    @Autowired
    private EntidadFinancieraService entidadFinancieraService;

    @Autowired
    private TipoAhorroService tipoAhorroService;

    @Autowired
    private TipoPagoService tipoPagoService;


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
            throw new BadRequestException("Usuario existente.");
        }
        // Creating usuario's account
        Usuario usuario = new Usuario();
        usuario.setFirstName(signUpRequest.getFirstName());
        usuario.setLastName(signUpRequest.getLastName());
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setFechaNacimiento(signUpRequest.getFechaNacimiento());
        usuario.setSexo(signUpRequest.getSexo());
        usuario.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        usuario.setProvider(AuthProvider.local);
        usuario.setEmailVerified(true);
        usuario.setEnabled(true);
        usuario.setCreatedOn(new Date());
        Usuario result = usuarioRepository.saveAndFlush(usuario);
        conceptoService.saveDefault(result);
        tipoCobroService.saveDefault(result);
        entidadFinancieraService.saveDefault(result);
        tipoAhorroService.saveDefault(result);
        tipoPagoService.saveDefault(result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/usuario/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Usuario registered successfully"));
    }

}
