package py.com.fuentepy.appfinanzasBackend.resource.usuario;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.Impl.UsuarioServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    @Autowired
    private UsuarioServiceImpl usuarioService;

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
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                    @Valid @RequestBody UsuarioRequestUpdate usuarioRequestUpdate,
                                    BindingResult result) {
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
            if (usuarioService.findById(usuarioId) == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo editar, el Usuario Nro: ".concat(usuarioId.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                try {
                    if (usuarioService.update(usuarioRequestUpdate, usuarioId)) {
                        httpStatus = HttpStatus.CREATED;
                        message = new MessageResponse(StatusLevel.INFO, "El Usuario ha sido actualizado con éxito!");
                        messages.add(message);
                        response = new BaseResponse(httpStatus.value(), messages);
                    } else {
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = new MessageResponse(StatusLevel.ERROR, "El Usuario no se pudo actualizar!");
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
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("image_profile") MultipartFile imageProfile,
                                    @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            if (!imageProfile.isEmpty()) {
                UsuarioModel usuarioModel = usuarioService.uploadImage(imageProfile, usuarioId);
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
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = new MessageResponse(StatusLevel.INFO, "Error al realizar de la imagen!");
            messages.add(message);
            message = new MessageResponse(StatusLevel.ERROR, e.getCause().getMessage());
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
    @GetMapping("/uploads/img/{imageProfile:.+}")
    public ResponseEntity<Resource> upload(@PathVariable String imageProfile,
                                           @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        Long usuarioId = userPrincipal.getId();
        Resource resource = null;

        if (!imageProfile.isEmpty()) {
            try {
                resource = usuarioService.getImagenPerfil(usuarioId, imageProfile);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            throw new RuntimeException("No existe image_profile.");
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<>(resource, cabecera, HttpStatus.OK);
    }
}
