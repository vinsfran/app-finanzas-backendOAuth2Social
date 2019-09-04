package py.com.fuentepy.appfinanzasBackend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoAhorro;
import py.com.fuentepy.appfinanzasBackend.service.TipoAhorroService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class TipoAhorroResource {

    @Autowired
    private TipoAhorroService tipoAhorroService;

    @GetMapping("/tipoAhorros")
    public List<TipoAhorro> index() {
        return tipoAhorroService.findAll();
    }

    @GetMapping("/tipoAhorros/page")
    public ResponseEntity<?> index(@ApiIgnore Pageable pageable) {
        Page<TipoAhorro> tipoAhorros = null;
        Map<String, Object> response = new HashMap<>();
        try {
            tipoAhorros = tipoAhorroService.findAll(pageable);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (tipoAhorros == null) {
            response.put("mensaje", "No existen tipoAhorros en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("page", tipoAhorros);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/tipoAhorros/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        TipoAhorro tipoAhorro = null;
        Map<String, Object> response = new HashMap<>();
        try {
            tipoAhorro = tipoAhorroService.findById(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (tipoAhorro == null) {
            response.put("mensaje", "El tipoAhorro ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoAhorro, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/tipoAhorros")
    public ResponseEntity<?> create(@Valid @RequestBody TipoAhorro tipoAhorro, BindingResult result) {
        TipoAhorro tipoAhorroNew = null;
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
            tipoAhorroNew = tipoAhorroService.save(tipoAhorro);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El tipoAhorro ha sido creado con éxito!");
        response.put("tipoAhorro", tipoAhorroNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/tipoAhorros/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody TipoAhorro tipoAhorro, BindingResult result, @PathVariable Integer id) {
        TipoAhorro tipoAhorroActual = tipoAhorroService.findById(id);
        TipoAhorro tipoAhorroUpdated = null;
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
        if (tipoAhorroActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el tipoAhorro ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            tipoAhorroActual.setNombre(tipoAhorro.getNombre());
            tipoAhorroUpdated = tipoAhorroService.save(tipoAhorroActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El tipoAhorro ha sido actualizado con éxito!");
        response.put("tipoAhorro", tipoAhorroUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/tipoAhorros/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            tipoAhorroService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el tipoAhorro de la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El tipoAhorro eliminado con éxito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @Secured({"ROLE_USER", "ROLE_ADMIN"})
//    @PostMapping("/tipoAhorros/upload")

//    @GetMapping("/uploads/img/{nombreFoto:.+}")

//    @Secured({"ROLE_ADMIN"})
//    @GetMapping("/tipoAhorros/regiones")
}
