package py.com.fuentepy.appfinanzasBackend.resource.dashboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleDeserializer;
import py.com.fuentepy.appfinanzasBackend.json.JsonDateSimpleSerializer;

import java.util.Date;

/**
 * @author vinsfran
 */
@Data
public class DashboardModel {

    @JsonProperty("total_ingresos")
    @SerializedName("total_ingresos")
    private Double totalIngresos;

    @JsonProperty("total_egresos")
    @SerializedName("total_egresos")
    private Double totalEgresos;

    @JsonProperty("saldo_ingresos_egresos")
    @SerializedName("saldo_ingresos_egresos")
    private Double saldoIngresosEgresos;

    @JsonProperty("cantidad_prestamos")
    @SerializedName("cantidad_prestamos")
    private Integer cantidadPrestamos;

    @JsonProperty("saldo_total_prestamos")
    @SerializedName("saldo_total_prestamos")
    private Double saldoTotalPrestamos;

    @JsonProperty("total_cuotas_monto_prestamos")
    @SerializedName("total_cuotas_monto_prestamos")
    private Double totalCuotasMontoPrestamos;

    @ApiModelProperty(value = "Fecha de Vencimiento de Prestamos", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("proximo_vencimiento_prestamos")
    @SerializedName("proximo_vencimiento_prestamos")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date proximoVencimientoPrestamos;

    @JsonProperty("cantidad_ahorros")
    @SerializedName("cantidad_ahorros")
    private Integer cantidadAhorros;

    @JsonProperty("total_monto_interes_ahorros")
    @SerializedName("total_monto_interes_ahorros")
    private Double totalMontoInteresAhorros;

    @JsonProperty("total_monto_capital_ahorros")
    @SerializedName("total_monto_capital_ahorros")
    private Double totalMontoCapitalAhorros;

    @ApiModelProperty(value = "Fecha de Vencimiento de Ahorros", required = false, example = "AAAA-MM-DD")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("proximo_vencimiento_ahorros")
    @SerializedName("proximo_vencimiento_ahorros")
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date proximoVencimientoAhorros;

    @JsonProperty("cantidad_tarjetas")
    @SerializedName("cantidad_tarjetas")
    private Integer cantidadTarjetas;

    @JsonProperty("total_deuda_tarjetas")
    @SerializedName("total_deuda_tarjetas")
    private Double totalDeudaTarjetas;

    @JsonProperty("total_linea_tarjetas")
    @SerializedName("total_linea_tarjetas")
    private Double totalLineaTarjetas;

    @JsonProperty("monto_presupuesto")
    @SerializedName("monto_presupuesto")
    private Double montoPresupuesto;

    @JsonProperty("monto_presupuesto_usado")
    @SerializedName("monto_presupuesto_usado")
    private Double montoPresupuestoUsado;
}
