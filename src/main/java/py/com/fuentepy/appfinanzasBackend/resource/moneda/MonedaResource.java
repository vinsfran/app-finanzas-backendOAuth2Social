package py.com.fuentepy.appfinanzasBackend.resource.moneda;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import py.com.fuentepy.appfinanzasBackend.service.MonedaService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/monedas")
public class MonedaResource {

    @Autowired
    private MonedaService monedaService;

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping()
    public List<MonedaModel> index() {
        return monedaService.findAll();
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping("/page")
    public ResponseEntity<?> index(@ApiIgnore Pageable pageable) {
        Page<MonedaModel> monedas = null;
        Map<String, Object> response = new HashMap<>();
        try {
            monedas = monedaService.findAll(pageable);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (monedas == null) {
            response.put("mensaje", "No existen monedas en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("page", monedas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        try {
            MonedaModel monedaModel = monedaService.findById(id);
            if (monedaModel == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: La Moneda Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "Consulta correcta");
                messages.add(message);
                response = new MonedaResponse(httpStatus.value(), messages, monedaModel);
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
    public ResponseEntity<?> create(@Valid @RequestBody MonedaRequestNew monedaRequestNew,
                                    BindingResult result) {
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
                if (monedaService.create(monedaRequestNew)) {
                    httpStatus = HttpStatus.CREATED;
                    message = new MessageResponse(StatusLevel.INFO, "La Moneda ha sido creada con éxito!");
                    messages.add(message);
                    response = new BaseResponse(httpStatus.value(), messages);
                } else {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = new MessageResponse(StatusLevel.ERROR, "La Moneda no se pudo crear!");
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
    public ResponseEntity<?> update(@Valid @RequestBody MonedaRequestUpdate monedaRequestUpdate, BindingResult result) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Integer id = monedaRequestUpdate.getId();
        if (result.hasErrors()) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (FieldError err : result.getFieldErrors()) {
                message = new MessageResponse(StatusLevel.INFO, "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
                messages.add(message);
            }
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            if (monedaService.findById(id) == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo editar, La Moneda Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                try {
                    if (monedaService.update(monedaRequestUpdate)) {
                        httpStatus = HttpStatus.CREATED;
                        message = new MessageResponse(StatusLevel.INFO, "La Moneda ha sido actualizada con éxito!");
                        messages.add(message);
                        response = new BaseResponse(httpStatus.value(), messages);
                    } else {
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = new MessageResponse(StatusLevel.ERROR, "La Moneda no se pudo actualizar!");
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
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            monedaService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la moneda de la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La moneda eliminado con éxito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
//    @PostMapping("/monedas/upload")

//    @GetMapping("/uploads/img/{nombreFoto:.+}")

//    @Secured({"ROLE_ADMIN"})
//    @GetMapping("/monedas/regiones")
}
