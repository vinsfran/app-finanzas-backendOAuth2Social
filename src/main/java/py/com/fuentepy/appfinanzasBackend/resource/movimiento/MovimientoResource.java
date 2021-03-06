package py.com.fuentepy.appfinanzasBackend.resource.movimiento;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.resource.model.ListStringResponse;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.ArchivoService;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoResource {

    private static final Log LOG = LogFactory.getLog(MovimientoResource.class);

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private ArchivoService archivoService;

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/documentos")
    public ResponseEntity<?> getDocumentos(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                           @RequestParam("movimiento_id") Long movimientoId) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        try {
            List<ArchivoModel> archivoModelList = archivoService.getArchivos(usuarioId, movimientoId, ConstantUtil.MOVIMIENTOS);
            if (archivoModelList == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: El Movimiento Nro: ".concat(movimientoId.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                List<String> list = new ArrayList<>();
                for (ArchivoModel archivoModel : archivoModelList) {
                    list.add(archivoModel.getNombre());
                }
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "Consulta correcta");
                messages.add(message);
                response = new ListStringResponse(httpStatus.value(), messages, list);
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = ""),
            @ApiImplicitParam(name = "parent", value = "parent Header", required = false, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    })
    @GetMapping()
    public List<MovimientoModel> getAll(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                        @RequestHeader(value = "parent", required = false) String parent,
                                        @RequestParam(value = "fecha_desde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
                                        @RequestParam(value = "fecha_hasta", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        Long usuarioId = userPrincipal.getId();
        return movimientoService.findByUsuarioIdAndParentAndFechaDesdeAndFechaHasta(usuarioId, parent, fechaDesde, fechaHasta);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @GetMapping("/page")
    public ResponseEntity<?> getAllByPage(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                          @ApiIgnore Pageable pageable) {
        Long usuarioId = userPrincipal.getId();
        Page<MovimientoModel> movimientos = null;
        Map<String, Object> response = new HashMap<>();
        try {
            movimientos = movimientoService.findByUsuarioId(usuarioId, pageable);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (movimientos == null) {
            response.put("mensaje", "No existen movimientos en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("page", movimientos);
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
            MovimientoModel movimientoModel = movimientoService.findByIdAndUsuarioId(id, usuarioId);
            if (movimientoModel == null) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = new MessageResponse(StatusLevel.WARNING, "Error: El Movimiento Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
                messages.add(message);
                response = new BaseResponse(httpStatus.value(), messages);
            } else {
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "Consulta correcta");
                messages.add(message);
                response = new MovimientoResponse(httpStatus.value(), messages, movimientoModel);
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
        if (movimientoService.findByIdAndUsuarioId(id, usuarioId) == null) {
            httpStatus = HttpStatus.NOT_FOUND;
            message = new MessageResponse(StatusLevel.WARNING, "Error: no se pudo borrar, el Movimiento Nro: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            messages.add(message);
            response = new BaseResponse(httpStatus.value(), messages);
        } else {
            try {
                movimientoService.deleteMovimiento(usuarioId, id);
                httpStatus = HttpStatus.OK;
                message = new MessageResponse(StatusLevel.INFO, "El Movimiento ha sido borrado con éxito!");
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
