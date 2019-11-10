package py.com.fuentepy.appfinanzasBackend.resource.concepto;

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
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.ConceptoService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/conceptos")
public class ConceptoResource {

    private static final Log LOG = LogFactory.getLog(ConceptoResource.class);

    @Autowired
    private ConceptoService conceptoService;

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping()
    public List<ConceptoModel> getAll(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        Long usuarioId = userPrincipal.getId();
        return conceptoService.findByUsuarioId(usuarioId);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping("/page")
    public ResponseEntity<?> getPageByUsuarioId(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                                @ApiIgnore Pageable pageable) {
        Long usuarioId = userPrincipal.getId();
        Page<ConceptoModel> entidadesFinancieras = null;
        Map<String, Object> response = new HashMap<>();
        try {
            entidadesFinancieras = conceptoService.findByUsuarioId(usuarioId, pageable);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (entidadesFinancieras == null) {
            response.put("mensaje", "No existen Entidades Financieras en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("page", entidadesFinancieras);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<?> getMovimientos(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                            @PathVariable Long id) {
        Long usuarioId = userPrincipal.getId();
        List<ConceptoMovimientoModel> movimientos = null;
        Map<String, Object> response = new HashMap<>();
        try {
            movimientos = conceptoService.findByUsuarioAndConceptoId(usuarioId, id);
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
            ConceptoModel conceptoModel = conceptoService.findByIdAndUsuarioId(id, usuarioId);
            if (conceptoModel == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: El Concepto Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "Consulta correcta");
                messages.add(message);
                response = new ConceptoResponse(httpStatus.value(), messages, conceptoModel);
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
                                    @Valid @RequestBody ConceptoRequestNew conceptoRequestNew,
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
                if (conceptoService.create(conceptoRequestNew, usuarioId)) {
                    httpStatus = HttpStatus.CREATED;
                    message = new MessageResponse(StatusLevel.INFO, "El Concepto ha sido creada con éxito!");
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                } else {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = new MessageResponse(StatusLevel.ERROR, "El Concepto no se pudo crear!");
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
                                    @Valid @RequestBody ConceptoRequestUpdate conceptoRequestUpdate,
                                    BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        Long id = conceptoRequestUpdate.getId();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            if (conceptoService.findByIdAndUsuarioId(id, usuarioId) == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo editar, el Concepto Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                try {
                    if (conceptoService.update(conceptoRequestUpdate, usuarioId)) {
                        httpStatus = HttpStatus.CREATED;
                        message = new MessageResponse(StatusLevel.INFO, "El Concepto ha sido actualizada con éxito!");
                        messages.add(message);
                        response = new BaseResponse(httpStatus.value(), messages);
                    } else {
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = new MessageResponse(StatusLevel.ERROR, "El Concepto no se pudo actualizar!");
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
                                   @Valid @RequestBody ConceptoRequestPago conceptoRequestPago,
                                   BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        Long id = conceptoRequestPago.getId();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            if (conceptoService.findByIdAndUsuarioId(id, usuarioId) == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo pagar, el Concepto Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                try {
                    if (conceptoService.pagar(conceptoRequestPago, usuarioId)) {
                        httpStatus = HttpStatus.CREATED;
                        message = new MessageResponse(StatusLevel.INFO, "El Concepto ha sido pagado con éxito!");
                        messages.add(message);
                        response = new BaseResponse(httpStatus.value(), messages);
                    } else {
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = new MessageResponse(StatusLevel.ERROR, "El Concepto no se pudo pagar!");
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
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/cobro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cobro(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                   @Valid @RequestBody ConceptoRequestCobro conceptoRequestCobro,
                                   BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        Long id = conceptoRequestCobro.getId();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            if (conceptoService.findByIdAndUsuarioId(id, usuarioId) == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo cobrar, el Concepto Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                try {
                    if (conceptoService.cobrar(conceptoRequestCobro, usuarioId)) {
                        httpStatus = HttpStatus.CREATED;
                        message = new MessageResponse(StatusLevel.INFO, "El Concepto ha sido cobrado con éxito!");
                        messages.add(message);
                        response = new BaseResponse(httpStatus.value(), messages);
                    } else {
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = new MessageResponse(StatusLevel.ERROR, "El Concepto no se pudo cobrar!");
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

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            conceptoService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el Concepto de la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Concepto eliminado con éxito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
//    @PostMapping("/entidadesFinancieras/upload")

//    @GetMapping("/uploads/img/{nombreFoto:.+}")

//    @Secured({"ROLE_ADMIN"})
//    @GetMapping("/entidadesFinancieras/regiones")
}
