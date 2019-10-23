package py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import py.com.fuentepy.appfinanzasBackend.converter.TipoConceptoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoConcepto;
import py.com.fuentepy.appfinanzasBackend.service.TipoConceptoService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/tipo-concepto")
public class TipoConceptoResource {

    @Autowired
    private TipoConceptoService tipoConceptoService;

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping()
    public List<TipoConceptoModel> index() {
        return TipoConceptoConverter.listEntitytoListModel(tipoConceptoService.findAll());
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping("/page")
    public ResponseEntity<?> index(@ApiIgnore Pageable pageable) {
        Page<TipoConcepto> tipoConceptos = null;
        Map<String, Object> response = new HashMap<>();
        try {
            tipoConceptos = tipoConceptoService.findAll(pageable);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (tipoConceptos == null) {
            response.put("mensaje", "No existen Tipos Conceptos en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("page", tipoConceptos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        TipoConcepto tipoConcepto = null;
        Map<String, Object> response = new HashMap<>();
        try {
            tipoConcepto = tipoConceptoService.findById(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (tipoConcepto == null) {
            response.put("mensaje", "El Tipo Concepto ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoConcepto, HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody TipoConceptoModel tipoConceptoModel, BindingResult result) {
        TipoConceptoModel tipoConceptoNew = null;
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
            tipoConceptoNew = tipoConceptoService.save(tipoConceptoModel);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El Tipo Concepto ha sido creado con éxito!");
        response.put("tipo_concepto", tipoConceptoNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @PutMapping()
    public ResponseEntity<?> update(@Valid @RequestBody TipoConceptoModel tipoConceptoModel, BindingResult result) {
        Integer id = tipoConceptoModel.getId();
        TipoConceptoModel tipoConceptoUpdated = null;
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
        if (tipoConceptoService.findById(id) == null) {
            response.put("mensaje", "Error: no se pudo editar, el Tipo Concepto ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            tipoConceptoUpdated = tipoConceptoService.save(tipoConceptoModel);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La Tipo Concepto ha sido actualizado con éxito!");
        response.put("tipo_concepto", tipoConceptoUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
            tipoConceptoService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el Tipo Concepto de la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El Tipo Concepto eliminado con éxito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
//    @PostMapping("/tipoConceptos/upload")

//    @GetMapping("/uploads/img/{nombreFoto:.+}")

//    @Secured({"ROLE_ADMIN"})
//    @GetMapping("/tipoConceptos/regiones")
}
