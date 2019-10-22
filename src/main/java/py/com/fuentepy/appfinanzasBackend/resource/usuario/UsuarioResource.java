package py.com.fuentepy.appfinanzasBackend.resource.usuario;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.auth.LoginRequest;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.resource.password.ResetRequest;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.Impl.UsuarioServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    @Autowired
    private UsuarioServiceImpl usuarioService;

//    @Autowired
//    private UsuarioRepository usuarioRepository;

//    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER')")
//    public Usuario getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
//        return usuarioRepository.findById(userPrincipal.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", userPrincipal.getId()));
//    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/")
    public ResponseEntity<?> show(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {

        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            UsuarioModel usuarioModel = usuarioService.findById(usuarioId);
            if (usuarioModel == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: El Usuario Nro: ".concat(usuarioId.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "Consulta correcta");
                messages.add(message);
                response = new UsuarioResponse(httpStatus.value(), messages, usuarioModel);
            }
        } catch (DataAccessException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = new MessageResponse(StatusLevel.INFO, "Error al realizar la consulta en la base de datos!");
            messages.add(message);
            message = new MessageResponse(StatusLevel.ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            messages.add(message);
            response = new BaseResponse(httpStatus.value(), messages);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @PutMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                            @Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                            @ApiIgnore BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            try {
                if (usuarioService.changePassword(usuarioId, changePasswordRequest.getPasswordOld(), changePasswordRequest.getPasswordNew())) {
                    httpStatus = HttpStatus.OK;
                    message = new MessageResponse(StatusLevel.INFO, "Cambio de password exitoso!");
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                } else {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = new MessageResponse(StatusLevel.ERROR, "No se pudo cambiar el password!");
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                }
            } catch (Exception e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = new MessageResponse(StatusLevel.INFO, "Error al realizar el update en la base de datos!");
                messages.add(message);
                message = new MessageResponse(StatusLevel.ERROR, e.getMessage());
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            }
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/uploadMultipartFile")
    public ResponseEntity<?> uploadMultipartFile(@RequestParam("image_profile") MultipartFile imageProfile,
                                                 @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            if (!imageProfile.isEmpty()) {
                UsuarioModel usuarioModel = usuarioService.uploadImage(imageProfile.getBytes(), imageProfile.getOriginalFilename(), usuarioId);
                if (usuarioModel == null) {
                    httpStatus = HttpStatus.NOT_FOUND;
                    message = new MessageResponse(StatusLevel.WARNING, "Error: El Usuario Nro: ".concat(usuarioId.toString()).concat(" no existe en la base de datos!"));
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                } else {
                    httpStatus = HttpStatus.CREATED;
                    message = new MessageResponse(StatusLevel.INFO, "Imagen guardada");
                    messages.add(message);
                    response = new UsuarioResponse(httpStatus.value(), messages, usuarioModel);
                }
            } else {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "No existe en la Imagen!");
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            }
        } catch (DataAccessException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = new MessageResponse(StatusLevel.INFO, "Error al realizar la consulta en la base de datos!");
            messages.add(message);
            message = new MessageResponse(StatusLevel.ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            messages.add(message);
            response = new BaseResponse(httpStatus.value(), messages);
        } catch (IOException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = new MessageResponse(StatusLevel.INFO, "Error al realizar de la imagen!");
            messages.add(message);
            message = new MessageResponse(StatusLevel.ERROR, e.getMessage().concat(": ").concat(e.getMessage()));
            messages.add(message);
            response = new BaseResponse(httpStatus.value(), messages);
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/uploadStringBase64")
    public ResponseEntity<?> uploadStringBase64(@Valid @RequestBody UsuarioImageRequest usuarioImageRequest, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            if (usuarioImageRequest != null) {
                UsuarioModel usuarioModel = usuarioService.uploadImage(Base64.decodeBase64(usuarioImageRequest.getImageProfileData()), usuarioImageRequest.getImageProfileName(), usuarioId);
                if (usuarioModel == null) {
                    httpStatus = HttpStatus.NOT_FOUND;
                    message = new MessageResponse(StatusLevel.WARNING, "Error: El Usuario Nro: ".concat(usuarioId.toString()).concat(" no existe en la base de datos!"));
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                } else {
                    httpStatus = HttpStatus.CREATED;
                    message = new MessageResponse(StatusLevel.INFO, "Imagen guardada");
                    messages.add(message);
                    response = new UsuarioResponse(httpStatus.value(), messages, usuarioModel);
                }
            } else {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "No existe en la Imagen!");
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            }
        } catch (DataAccessException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = new MessageResponse(StatusLevel.INFO, "Error al realizar la consulta en la base de datos!");
            messages.add(message);
            message = new MessageResponse(StatusLevel.ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            messages.add(message);
            response = new BaseResponse(httpStatus.value(), messages);
        }
        return new ResponseEntity<>(response, httpStatus);

    }
}
