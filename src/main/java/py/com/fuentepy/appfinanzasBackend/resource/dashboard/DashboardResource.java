package py.com.fuentepy.appfinanzasBackend.resource.dashboard;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import py.com.fuentepy.appfinanzasBackend.data.entity.Ahorro;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Tarjeta;
import py.com.fuentepy.appfinanzasBackend.resource.common.BaseResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.MessageResponse;
import py.com.fuentepy.appfinanzasBackend.resource.common.StatusLevel;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.AhorroService;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;
import py.com.fuentepy.appfinanzasBackend.service.PrestamoService;
import py.com.fuentepy.appfinanzasBackend.service.TarjetaService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardResource {

    private static final Log LOG = LogFactory.getLog(DashboardResource.class);

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private AhorroService ahorroService;

    @Autowired
    private TarjetaService tarjetaService;

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "")
    )
    @Secured({"ROLE_ADMIN"})
    @GetMapping()
    public ResponseEntity<?> getPageByUsuarioId(@ApiIgnore @CurrentUser UserPrincipal userPrincipal,
                                                @RequestParam(value = "fecha_desde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
                                                @RequestParam(value = "fecha_hasta", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        HttpStatus httpStatus;
        BaseResponse response;
        MessageResponse message;
        List<MessageResponse> messages = new ArrayList<>();
        Long usuarioId = userPrincipal.getId();
        DashboardModel dashboardModel = new DashboardModel();
        try {
            dashboardModel.setTotalIngresos(0.0);
            dashboardModel.setTotalEgresos(0.0);
            for (Movimiento movimiento : movimientoService.movimientosByUsuarioAndRangoFecha(usuarioId, fechaDesde, fechaHasta)) {
                if (movimiento.getSigno().equals("+")) {
                    dashboardModel.setTotalIngresos(dashboardModel.getTotalIngresos() + movimiento.getMonto());
                } else {
                    dashboardModel.setTotalEgresos(dashboardModel.getTotalEgresos() + movimiento.getMonto());
                }
            }
            dashboardModel.setSaldoIngresosEgresos(dashboardModel.getTotalIngresos() - dashboardModel.getTotalEgresos());
            dashboardModel.setCantidadPrestamos(0);
            dashboardModel.setSaldoTotalPrestamos(0.0);
            dashboardModel.setTotalCuotasMontoPrestamos(0.0);
            dashboardModel.setProximoVencimientoPrestamos(new Date());
            for (Prestamo prestamo : prestamoService.movimientosByUsuarioAndRangoFecha(usuarioId, fechaDesde, fechaHasta)) {
                dashboardModel.setCantidadPrestamos(dashboardModel.getCantidadPrestamos() + 1);
                dashboardModel.setSaldoTotalPrestamos(dashboardModel.getSaldoTotalPrestamos() + prestamo.getMontoPrestamo() - prestamo.getMontoPagado());
                dashboardModel.setTotalCuotasMontoPrestamos(dashboardModel.getTotalCuotasMontoPrestamos() + prestamo.getMontoCuota());
            }
            dashboardModel.setCantidadAhorros(0);
            dashboardModel.setTotalMontoInteresAhorros(0.0);
            dashboardModel.setTotalMontoCapitalAhorros(0.0);
            dashboardModel.setProximoVencimientoAhorros(new Date());
            for (Ahorro ahorro : ahorroService.findByUsuarioAndEstado(usuarioId, true)) {
                dashboardModel.setCantidadAhorros(dashboardModel.getCantidadAhorros() + 1);
                dashboardModel.setTotalMontoInteresAhorros(dashboardModel.getTotalMontoInteresAhorros() + ahorro.getInteres());
                dashboardModel.setTotalMontoCapitalAhorros(dashboardModel.getTotalMontoCapitalAhorros() + ahorro.getMontoCapital());
            }
            dashboardModel.setCantidadTarjetas(0);
            dashboardModel.setTotalDeudaTarjetas(0.0);
            dashboardModel.setTotalLineaTarjetas(0.0);
            for (Tarjeta tarjeta : tarjetaService.findByUsuarioIdLista(usuarioId)) {
                dashboardModel.setCantidadTarjetas(dashboardModel.getCantidadTarjetas() + 1);
                dashboardModel.setTotalLineaTarjetas(dashboardModel.getTotalLineaTarjetas() + tarjeta.getLineaCredito());
            }
            httpStatus = HttpStatus.OK;
            message = new MessageResponse(StatusLevel.INFO, "Consulta correcta");
            messages.add(message);
            response = new DashboardResponse(httpStatus.value(), messages, dashboardModel);
        } catch (DataAccessException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = new MessageResponse(StatusLevel.INFO, "Error al realizar el insert en la base de datos!");
            messages.add(message);
            message = new MessageResponse(StatusLevel.ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            messages.add(message);
            response = new BaseResponse(httpStatus.value(), messages);
        }

//        if (totalAhorros == null) {
//            response.put("mensaje", "No existen ahorros en la base de datos!");
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
        return new ResponseEntity<>(response, httpStatus);
    }

}
