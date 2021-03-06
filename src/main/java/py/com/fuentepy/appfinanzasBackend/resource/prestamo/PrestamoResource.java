package py.com.fuentepy.appfinanzasBackend.resource.prestamo;

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
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.resource.model.MovimientoIdResponse;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.PrestamoService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoResource {

    private static final Log LOG = LogFactory.getLog(PrestamoResource.class);

    @Autowired
    private PrestamoService prestamoService;

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping()
    public List<PrestamoModel> getAll(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        Long usuarioId = userPrincipal.getId();
        return prestamoService.findByUsuarioId(usuarioId);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping("/page")
    public ResponseEntity<?> getPageByUsuarioId(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                                @ApiIgnore Pageable pageable) {
        Long usuarioId = userPrincipal.getId();
        Page<PrestamoModel> prestamos = null;
        Map<String, Object> response = new HashMap<>();
        try {
            prestamos = prestamoService.findByUsuarioId(usuarioId, pageable);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (prestamos == null) {
            response.put("mensaje", "No existen ahorros en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("page", prestamos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping("/{prestamo_id}/movimientos")
    public ResponseEntity<?> getMovimientos(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                            @PathVariable(name = "prestamo_id") Long prestamoId) {
        Long usuarioId = userPrincipal.getId();
        List<PrestamoMovimientoModel> movimientos = null;
        Map<String, Object> response = new HashMap<>();
        try {
            movimientos = prestamoService.findByUsuarioAndPrestamoId(usuarioId, prestamoId);
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
    @DeleteMapping("/{prestamo_id}/movimientos/{movimiento_id}")
    public ResponseEntity<?> deleteMovimiento(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                              @PathVariable(name = "prestamo_id") Long prestamoId,
                                              @PathVariable(name = "movimiento_id") Long movimientoId) {
        Long usuarioId = userPrincipal.getId();
        Map<String, Object> response = new HashMap<>();
        try {
            prestamoService.deleteMovimiento(usuarioId, movimientoId);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Movimiento borrado!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{prestamo_id}")
    public ResponseEntity<?> show(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                  @PathVariable(name = "prestamo_id") Long prestamoId) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            PrestamoModel prestamoModel = prestamoService.findByIdAndUsuarioId(prestamoId, usuarioId);
            if (prestamoModel == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: El Prestamo Nro: ".concat(prestamoId.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "Consulta correcta");
                messages.add(message);
                response = new PrestamoResponse(httpStatus.value(), messages, prestamoModel);
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
                                    @Valid @RequestBody PrestamoRequestNew prestamoNew) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            if (prestamoService.create(prestamoNew, usuarioId)) {
                httpStatus = HttpStatus.CREATED;
                message = new MessageResponse(StatusLevel.INFO, "El Prestamo ha sido creado con éxito!");
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = new MessageResponse(StatusLevel.ERROR, "El Prestamo no se pudo crear!");
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
        return new ResponseEntity<>(response, httpStatus);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                    @Valid @RequestBody PrestamoRequestUpdate prestamoUpdate,
                                    BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        Long id = prestamoUpdate.getId();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            if (prestamoService.findByIdAndUsuarioId(id, usuarioId) == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo editar, el Prestamo Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                try {
                    if (prestamoService.update(prestamoUpdate, usuarioId)) {
                        httpStatus = HttpStatus.CREATED;
                        message = new MessageResponse(StatusLevel.INFO, "El Prestamo ha sido actualizado con éxito!");
                        messages.add(message);
                        response = new BaseResponse(httpStatus.value(), messages);
                    } else {
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = new MessageResponse(StatusLevel.ERROR, "El Prestamo no se pudo actualizar!");
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
                                   @Valid @RequestBody PrestamoRequestPago prestamoRequestPago) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        Long id = prestamoRequestPago.getId();
        if (prestamoService.findByIdAndUsuarioId(id, usuarioId) == null) {
            httpStatus = HttpStatus.NOT_FOUND;
            message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo pagar, el Prestamo Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            messages.add(message);
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            try {
                Movimiento movimiento = prestamoService.pagar(prestamoRequestPago, usuarioId);
                if (movimiento != null) {
                    httpStatus = HttpStatus.CREATED;
                    message = new MessageResponse(StatusLevel.INFO, "El Prestamo ha sido pagado con éxito!");
                    messages.add(message);
                    response = new MovimientoIdResponse(httpStatus.value(), messages, movimiento.getId());
                } else {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = new MessageResponse(StatusLevel.ERROR, "El Prestamo no se pudo pagar!");
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
        return new ResponseEntity<>(response, httpStatus);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/{prestamo_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                    @PathVariable(name = "prestamo_id") Long prestamoId) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        if (prestamoService.findByIdAndUsuarioId(prestamoId, usuarioId) == null) {
            httpStatus = HttpStatus.NOT_FOUND;
            message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo borrar, el Prestamo Nro: ".concat(prestamoId.toString()).concat(" no existe en la base de datos!"));
            messages.add(message);
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            try {
                prestamoService.delete(usuarioId, prestamoId);
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "El Prestamo ha sido borrado con éxito!");
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
