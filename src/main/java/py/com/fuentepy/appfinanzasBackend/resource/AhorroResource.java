package py.com.fuentepy.appfinanzasBackend.resource;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import py.com.fuentepy.appfinanzasBackend.model.AhorroModel;
import py.com.fuentepy.appfinanzasBackend.payload.request.ahorro.AhorroNew;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.AhorroService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/ahorros")
public class AhorroResource {

    private static final Log LOG = LogFactory.getLog(AhorroResource.class);

    @Autowired
    private AhorroService ahorroService;

    @GetMapping()
    public List<AhorroModel> getAll(@CurrentUser UserPrincipal userPrincipal) {
        Long usuarioId = userPrincipal.getId();
        return ahorroService.findByUsuarioId(usuarioId);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPageByUsuarioId(@CurrentUser UserPrincipal userPrincipal,
                                                @ApiIgnore Pageable pageable) {
        Long usuarioId = userPrincipal.getId();
        Page<AhorroModel> ahorros = null;
        Map<String, Object> response = new HashMap<>();
        try {
            ahorros = ahorroService.findByUsuarioId(usuarioId, pageable);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ahorros == null) {
            response.put("mensaje", "No existen ahorros en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("page", ahorros);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        AhorroModel ahorro = null;
        Map<String, Object> response = new HashMap<>();
        try {
            ahorro = ahorroService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (ahorro == null) {
            response.put("mensaje", "El Ahorro ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ahorro, HttpStatus.OK);
    }


    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @PostMapping()
    public ResponseEntity<?> create(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                    @Valid @RequestBody AhorroNew ahorroNew,
                                    @ApiIgnore BindingResult result) {

        Long usuarioId = userPrincipal.getId();

//        AhorroModel ahorroNew = null;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
//            List<String> errors = new ArrayList<>();
//            for (FieldError err : result.getFieldErrors()) {
//                errors.add("El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
//            }

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> {
                        return "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage());
                    })
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            if (ahorroService.create(ahorroNew, usuarioId)) {
                response.put("mensaje", "El Ahorro ha sido creada con éxito!");
            }else {
                response.put("mensaje", "El Ahorro se pudo crear!");
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        response.put("ahorro", ahorroNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping()
    public ResponseEntity<?> update(@Valid @RequestBody AhorroModel ahorroModel, BindingResult result) {
        Long id = ahorroModel.getId();
        AhorroModel ahorroUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
//            List<String> errors = new ArrayList<>();
//            for (FieldError err : result.getFieldErrors()) {
//                errors.add("El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()));
//            }

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> {
                        return "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage());
                    })
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        if (ahorroService.findById(id) == null) {
            response.put("mensaje", "Error: no se pudo editar, el Ahorro Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
//            ahorroUpdated = ahorroService.save(ahorroModel);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El Ahorro ha sido actualizado con éxito!");
        response.put("ahorro", ahorroUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            ahorroService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el Ahorro de la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El Ahorro eliminado con éxito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
//    @PostMapping("/ahorros/upload")

//    @GetMapping("/uploads/img/{nombreFoto:.+}")

//    @Secured({"ROLE_ADMIN"})
//    @GetMapping("/ahorros/regiones")
}
