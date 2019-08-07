package py.com.fuentepy.appfinanzasBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;

/**
 * @author vinsfran
 */
@Data
public class DashboardModel {

    @JsonProperty("totalIngresos")
    @SerializedName("totalIngresos")
    private Long totalIngresos;

    @JsonProperty("totalEgresos")
    @SerializedName("totalEgresos")
    private Long totalEgresos;

    @JsonProperty("saldoIngresosEgresos")
    @SerializedName("saldoIngresosEgresos")
    private Long saldoIngresosEgresos;

    @JsonProperty("cantidadPrestamos")
    @SerializedName("cantidadPrestamos")
    private Integer cantidadPrestamos;

    @JsonProperty("saldoTotalPrestamos")
    @SerializedName("saldoTotalPrestamos")
    private Long saldoTotalPrestamos;

    @JsonProperty("totalCuotasMontoPrestamos")
    @SerializedName("totalCuotasMontoPrestamos")
    private Long totalCuotasMontoPrestamos;

    @JsonProperty("proximoVencimientoPrestamos")
    @SerializedName("proximoVencimientoPrestamos")
    private Date proximoVencimientoPrestamos;

    @JsonProperty("cantidadAhorros")
    @SerializedName("cantidadAhorros")
    private Integer cantidadAhorros;

    @JsonProperty("totalMontoInteresAhorros")
    @SerializedName("totalMontoInteresAhorros")
    private Long totalMontoInteresAhorros;

    @JsonProperty("totalMontoCapitalAhorros")
    @SerializedName("totalMontoCapitalAhorros")
    private Long totalMontoCapitalAhorros;

    @JsonProperty("proximoVencimientoAhorros")
    @SerializedName("proximoVencimientoAhorros")
    private Date proximoVencimientoAhorros;

    @JsonProperty("cantidadTarjetas")
    @SerializedName("cantidadTarjetas")
    private Integer cantidadTarjetas;

    @JsonProperty("totalDeudaTarjetas")
    @SerializedName("totalDeudaTarjetas")
    private Long totalDeudaTarjetas;

    @JsonProperty("totalLineaTarjetas")
    @SerializedName("totalLineaTarjetas")
    private Long totalLineaTarjetas;
}
