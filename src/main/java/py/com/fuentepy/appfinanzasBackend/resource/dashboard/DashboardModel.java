package py.com.fuentepy.appfinanzasBackend.resource.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;

/**
 * @author vinsfran
 */
@Data
public class DashboardModel {

    @JsonProperty("total_ingresos")
    @SerializedName("total_ingresos")
    private Long totalIngresos;

    @JsonProperty("total_egresos")
    @SerializedName("total_egresos")
    private Long totalEgresos;

    @JsonProperty("saldo_ingresos_egresos")
    @SerializedName("saldo_ingresos_egresos")
    private Long saldoIngresosEgresos;

    @JsonProperty("cantidad_prestamos")
    @SerializedName("cantidad_prestamos")
    private Integer cantidadPrestamos;

    @JsonProperty("saldo_total_prestamos")
    @SerializedName("saldo_total_prestamos")
    private Long saldoTotalPrestamos;

    @JsonProperty("total_cuotas_monto_prestamos")
    @SerializedName("total_cuotas_monto_prestamos")
    private Long totalCuotasMontoPrestamos;

    @JsonProperty("proximo_vencimiento_prestamos")
    @SerializedName("proximo_vencimiento_prestamos")
    private Date proximoVencimientoPrestamos;

    @JsonProperty("cantidad_ahorros")
    @SerializedName("cantidad_ahorros")
    private Integer cantidadAhorros;

    @JsonProperty("total_monto_interes_ahorros")
    @SerializedName("total_monto_interes_ahorros")
    private Long totalMontoInteresAhorros;

    @JsonProperty("total_monto_capital_ahorros")
    @SerializedName("total_monto_capital_ahorros")
    private Long totalMontoCapitalAhorros;

    @JsonProperty("proximo_vencimiento_ahorros")
    @SerializedName("proximo_vencimiento_ahorros")
    private Date proximoVencimientoAhorros;

    @JsonProperty("cantidad_tarjetas")
    @SerializedName("cantidad_tarjetas")
    private Integer cantidadTarjetas;

    @JsonProperty("total_deuda_tarjetas")
    @SerializedName("total_deuda_tarjetas")
    private Long totalDeudaTarjetas;

    @JsonProperty("total_linea_tarjetas")
    @SerializedName("total_linea_tarjetas")
    private Long totalLineaTarjetas;
}
