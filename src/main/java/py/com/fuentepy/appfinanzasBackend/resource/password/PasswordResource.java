package py.com.fuentepy.appfinanzasBackend.resource.password;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.service.EmailService;
import py.com.fuentepy.appfinanzasBackend.service.UsuarioService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/password")
public class PasswordResource {

    private static final Log LOG = LogFactory.getLog(PasswordResource.class);

    @Value("${app.urlResetPassword}")
    private String urlResetPassword;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //    @Secured({"ROLE_USER"})
    @PutMapping(value = "/forgot", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processForgotPassword(@Valid @RequestBody ForgotRequest forgotRequest,
                                                   @ApiIgnore BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            try {
                Optional<Usuario> optional = usuarioService.findUserByEmail(forgotRequest.getEmail());
                if (optional.isPresent()) {
                    Usuario user = optional.get();
                    user.setResetToken(UUID.randomUUID().toString());
                    usuarioService.save(user);
                    String appUrl = urlResetPassword;
                    SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
                    passwordResetEmail.setFrom("support@demo.com");
                    passwordResetEmail.setTo(user.getEmail());
                    passwordResetEmail.setSubject("Password Reset Request");
                    passwordResetEmail.setText("To reset your password, click the link below: " + appUrl
                            + "/reset?token=" + user.getResetToken());
                    emailService.sendEmail(passwordResetEmail);
                    httpStatus = HttpStatus.OK;
                    message = new MessageResponse(StatusLevel.INFO, "A password reset link has been sent to " + forgotRequest.getEmail());
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                } else {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = new MessageResponse(StatusLevel.ERROR, "No se pudo crear generar token!");
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                }
            } catch (DataAccessException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = new MessageResponse(StatusLevel.INFO, "Error al realizar el insert en la base de datos!");
                messages.add(message);
                message = new MessageResponse(StatusLevel.ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            }
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setNewPassword(@Valid @RequestBody ResetRequest resetRequest,
                                            @ApiIgnore BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            try {
                Optional<Usuario> optional = usuarioService.findUserByResetToken(resetRequest.getToken());
                if (optional.isPresent()) {
                    Usuario user = optional.get();
                    user.setPassword(passwordEncoder.encode(resetRequest.getPassword()));
                    user.setResetToken(null);
                    usuarioService.save(user);
                    httpStatus = HttpStatus.OK;
                    message = new MessageResponse(StatusLevel.INFO, "You have successfully reset your password.  You may now login.");
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                } else {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = new MessageResponse(StatusLevel.ERROR, "No se pudo cambiar el password!");
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                }
            } catch (DataAccessException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = new MessageResponse(StatusLevel.INFO, "Error al realizar el insert en la base de datos!");
                messages.add(message);
                message = new MessageResponse(StatusLevel.ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            }
        }
        return new ResponseEntity<>(response, httpStatus);
    }


}
