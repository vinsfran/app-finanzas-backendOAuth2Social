package py.com.fuentepy.appfinanzasBackend.resource.archivo;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioResponse;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.Impl.ArchivoServiceImpl;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoResource {

    @Autowired
    private ArchivoServiceImpl archivoService;

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/movimientos/documentos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDocumentsList(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                                 @RequestParam("movimiento_id") Long movimientoId,
                                                 @RequestParam("archivos") List<MultipartFile> archivos) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            if (archivos != null && !archivos.isEmpty()) {
                archivoService.uploadDocumentsList(movimientoId, ConstantUtil.MOVIMIENTOS, usuarioId, archivos);
                httpStatus = HttpStatus.CREATED;
                message = new MessageResponse(StatusLevel.INFO, "Los archivos se guardaron correctamente!");
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "No existes archivos para subir!");
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = new MessageResponse(StatusLevel.INFO, "Error al subir los archivos!");
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
    @GetMapping(value = "/movimientos/documentos", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getResourceMovimientoByName(@RequestParam("file_name") String fileName,
                                                                @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        Long usuarioId = userPrincipal.getId();
        Resource resource = null;
        if (!fileName.isEmpty()) {
            try {
                resource = archivoService.getResourceByName(usuarioId, fileName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            throw new RuntimeException("No existe el archivo.");
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, cabecera, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/usuarios/imagen-perfil")
    public ResponseEntity<?> uploadImagenPerfil(@RequestParam("image_profile") MultipartFile imageProfile,
                                                @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            if (!imageProfile.isEmpty()) {
                UsuarioModel usuarioModel = archivoService.uploadImagenPerfil(imageProfile, usuarioId);
                if (usuarioModel == null) {
                    httpStatus = HttpStatus.NOT_FOUND;
                    message = new MessageResponse(StatusLevel.WARNING, "Error: El Usuario Nro: ".concat(usuarioId.toString()).concat(" no existe en la base de datos!"));
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                } else {
                    httpStatus = HttpStatus.CREATED;
                    message = new MessageResponse(StatusLevel.INFO, "Imagen perfil guardada");
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
    @GetMapping(value = "/usuarios/imagen-perfil", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getImagenPerfil(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        Long usuarioId = userPrincipal.getId();
        Resource resource = null;
        try {
            resource = archivoService.getImagenPerfil(usuarioId);
        } catch (Exception ex) {
            throw new RuntimeException("Error en imagen de perfil. " + ex.getCause().getMessage());
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, cabecera, HttpStatus.OK);
    }


}
