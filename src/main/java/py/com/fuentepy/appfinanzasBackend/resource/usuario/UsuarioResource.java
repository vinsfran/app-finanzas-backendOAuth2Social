package py.com.fuentepy.appfinanzasBackend.resource.usuario;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.Impl.UsuarioServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("image_profile") MultipartFile imageProfile, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            if (!imageProfile.isEmpty()) {
                UsuarioModel usuarioModel = usuarioService.uploadImage(Base64.encodeBase64String(imageProfile.getBytes()), imageProfile.getOriginalFilename(), usuarioId);
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
}
