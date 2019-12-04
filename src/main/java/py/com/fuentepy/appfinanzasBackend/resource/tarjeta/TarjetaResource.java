package py.com.fuentepy.appfinanzasBackend.resource.tarjeta;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import py.com.fuentepy.appfinanzasBackend.service.TarjetaService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/tarjetas")
public class TarjetaResource {

    private static final Log LOG = LogFactory.getLog(TarjetaResource.class);

    @Autowired
    private TarjetaService tarjetaService;

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping()
    public List<TarjetaModel> getAll(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        Long usuarioId = userPrincipal.getId();
        return tarjetaService.findByUsuarioId(usuarioId);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping("/page")
    public ResponseEntity<?> getAllByPage(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                          @ApiIgnore Pageable pageable) {
        Long usuarioId = userPrincipal.getId();
        Page<TarjetaModel> tarjetas = null;
        Map<String, Object> response = new HashMap<>();
        try {
            tarjetas = tarjetaService.findByUsuarioId(usuarioId, pageable);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (tarjetas == null) {
            response.put("mensaje", "No existen tarjetas en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("page", tarjetas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<?> getMovimientos(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                            @PathVariable Long id) {
        Long usuarioId = userPrincipal.getId();
        List<TarjetaMovimientoModel> movimientos = null;
        Map<String, Object> response = new HashMap<>();
        try {
            movimientos = tarjetaService.findByUsuarioAndTarjetaId(usuarioId, id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (movimientos == null) {
            response.put("mensaje", "No existen movimientos en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("movimientos", movimientos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                  @PathVariable Long id) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            TarjetaModel tarjetaModel = tarjetaService.findByIdAndUsuarioId(id, usuarioId);
            if (tarjetaModel == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: La Tarjeta Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "Consulta correcta");
                messages.add(message);
                response = new TarjetaResponse(httpStatus.value(), messages, tarjetaModel);
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
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                    @Valid @RequestBody TarjetaRequestNew tarjetaNew,
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
                if (tarjetaService.create(tarjetaNew, usuarioId)) {
                    httpStatus = HttpStatus.CREATED;
                    message = new MessageResponse(StatusLevel.INFO, "El Tarjeta ha sido creado con éxito!");
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                } else {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = new MessageResponse(StatusLevel.ERROR, "El Tarjeta no se pudo crear!");
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

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                    @Valid @RequestBody TarjetaRequestUpdate tarjetaUpdate,
                                    BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        Long id = tarjetaUpdate.getId();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            if (tarjetaService.findByIdAndUsuarioId(id, usuarioId) == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo editar, La Tarjeta Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                try {
                    if (tarjetaService.update(tarjetaUpdate, usuarioId)) {
                        httpStatus = HttpStatus.CREATED;
                        message = new MessageResponse(StatusLevel.INFO, "La Tarjeta ha sido actualizado con éxito!");
                        messages.add(message);
                        response = new BaseResponse(httpStatus.value(), messages);
                    } else {
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = new MessageResponse(StatusLevel.ERROR, "La Tarjeta no se pudo actualizar!");
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
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/pagar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pagar(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                   @Valid @RequestBody TarjetaRequestPago tarjetaRequestPago,
                                   @RequestParam("archivos") MultipartFile[] multipartFileList,
                                   BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        Long id = tarjetaRequestPago.getId();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            if (tarjetaService.findByIdAndUsuarioId(id, usuarioId) == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo pagar, la Tarjeta Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                try {
                    if (tarjetaService.pagar(tarjetaRequestPago, multipartFileList, usuarioId)) {
                        httpStatus = HttpStatus.CREATED;
                        message = new MessageResponse(StatusLevel.INFO, "La Tarjeta ha sido pagada con éxito!");
                        messages.add(message);
                        response = new BaseResponse(httpStatus.value(), messages);
                    } else {
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = new MessageResponse(StatusLevel.ERROR, "La Tarjeta no se pudo pagar!");
                        messages.add(message);
                        response = new BaseResponse(httpStatus.value(), messages);
                    }
                } catch (DataAccessException e) {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = new MessageResponse(StatusLevel.INFO, "Error al realizar el update en la base de datos!");
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
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                    @PathVariable Long id) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        if (tarjetaService.findByIdAndUsuarioId(id, usuarioId) == null) {
            httpStatus = HttpStatus.NOT_FOUND;
            message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo borrar, la Tarjeta Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            messages.add(message);
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            try {
                tarjetaService.delete(usuarioId, id);
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "La Tarjeta ha sido borrado con éxito!");
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } catch (Exception e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = new MessageResponse(StatusLevel.INFO, "Error al realizar el delete en la base de datos!");
                messages.add(message);
                message = new MessageResponse(StatusLevel.ERROR, e.getMessage().concat(": ").concat(e.getMessage()));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            }
        }
        return new ResponseEntity<>(response, httpStatus);
    }
}
