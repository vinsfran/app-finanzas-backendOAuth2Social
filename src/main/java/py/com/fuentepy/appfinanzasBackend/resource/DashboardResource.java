package py.com.fuentepy.appfinanzasBackend.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import py.com.fuentepy.appfinanzasBackend.entity.Ahorro;
import py.com.fuentepy.appfinanzasBackend.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.entity.Tarjeta;
import py.com.fuentepy.appfinanzasBackend.model.DashboardModel;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import py.com.fuentepy.appfinanzasBackend.service.AhorroService;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;
import py.com.fuentepy.appfinanzasBackend.service.PrestamoService;
import py.com.fuentepy.appfinanzasBackend.service.TarjetaService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getPageByUsuarioId(@CurrentUser UserPrincipal userPrincipal,
                                                @RequestParam(value = "fechaDesde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
                                                @RequestParam(value = "fechaHasta", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        Long usuarioId = userPrincipal.getId();
        DashboardModel dashboardModel = new DashboardModel();
        Map<String, Object> response = new HashMap<>();
        try {
            dashboardModel.setTotalIngresos(0L);
            dashboardModel.setTotalEgresos(0L);
            for (Movimiento movimiento : movimientoService.movimientosByUsuarioAndRangoFecha(usuarioId, fechaDesde, fechaHasta)) {
                if (movimiento.getConceptoId().getTipoConcepto().equals("Ingreso")) {
                    dashboardModel.setTotalIngresos(dashboardModel.getTotalIngresos() + movimiento.getMontoPagado());
                } else {
                    dashboardModel.setTotalEgresos(dashboardModel.getTotalEgresos() + movimiento.getMontoPagado());
                }
            }
            dashboardModel.setSaldoIngresosEgresos(dashboardModel.getTotalIngresos() - dashboardModel.getTotalEgresos());

            dashboardModel.setCantidadPrestamos(0);
            dashboardModel.setSaldoTotalPrestamos(0L);
            dashboardModel.setTotalCuotasMontoPrestamos(0L);
            dashboardModel.setProximoVencimientoPrestamos(new Date());
            for (Prestamo prestamo : prestamoService.movimientosByUsuarioAndRangoFecha(usuarioId, fechaDesde, fechaHasta)) {
                dashboardModel.setCantidadPrestamos(dashboardModel.getCantidadPrestamos() + 1);
                dashboardModel.setSaldoTotalPrestamos(dashboardModel.getSaldoTotalPrestamos() + prestamo.getMontoPrestamo() - prestamo.getMontoPagado());
                dashboardModel.setTotalCuotasMontoPrestamos(dashboardModel.getTotalCuotasMontoPrestamos() + prestamo.getMontoCuota());
            }

            dashboardModel.setCantidadAhorros(0);
            dashboardModel.setTotalMontoInteresAhorros(0L);
            dashboardModel.setTotalMontoCapitalAhorros(0L);
            dashboardModel.setProximoVencimientoAhorros(new Date());
            for (Ahorro ahorro : ahorroService.movimientosByUsuarioAndRangoFecha(usuarioId, fechaDesde, fechaHasta)) {
                dashboardModel.setCantidadAhorros(dashboardModel.getCantidadAhorros() + 1);
                dashboardModel.setTotalMontoInteresAhorros(dashboardModel.getTotalMontoInteresAhorros() + ahorro.getInteres());
                dashboardModel.setTotalMontoCapitalAhorros(dashboardModel.getTotalMontoCapitalAhorros() + ahorro.getMontoCapital());
            }

            dashboardModel.setCantidadTarjetas(0);
            dashboardModel.setTotalDeudaTarjetas(0L);
            dashboardModel.setTotalLineaTarjetas(0L);
            for (Tarjeta tarjeta : tarjetaService.findByUsuarioIdLista(usuarioId)) {
                dashboardModel.setCantidadTarjetas(dashboardModel.getCantidadTarjetas() + 1);

                dashboardModel.setTotalLineaTarjetas(dashboardModel.getTotalLineaTarjetas() + tarjeta.getLineaCredito());
            }

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        if (totalAhorros == null) {
//            response.put("mensaje", "No existen ahorros en la base de datos!");
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
        response.put("dashboard", dashboardModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
