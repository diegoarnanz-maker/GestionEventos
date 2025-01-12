package ALD_Actividad_4.modelo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;
// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "eventos")
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENTO")
    private int idEvento;

    @Column(name = "AFORO_MAXIMO")
    private int aforoMaximo;

    private String descripcion;

    private String destacado;

    private String direccion;

    private int duracion;

    private String estado;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_INICIO")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @Column(name = "MINIMO_ASISTENCIA")
    private int minimoAsistencia;

    private String nombre;

    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO")
    private Tipo tipo;

    public void setDestacado(String destacado) {
        if (!"S".equals(destacado) && !"N".equals(destacado)) {
            throw new IllegalArgumentException("El valor de 'destacado' debe ser 'S' o 'N'.");
        }
        this.destacado = destacado;
    }
}
