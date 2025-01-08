package ALD_Actividad_4.modelo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Min(value = 1, message = "El aforo máximo debe ser mayor a 0")
    @Column(name = "AFORO_MAXIMO")
    private int aforoMaximo;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    private String destacado;

    @NotNull(message = "La dirección no puede ser nula")
    private String direccion;

    @Min(value = 1, message = "La duración debe ser mayor a 0")
    private int duracion;

    private String estado;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_INICIO")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de inicio no puede ser nula")
    private Date fechaInicio;

    @Min(value = 1, message = "El mínimo de asistencia debe ser mayor a 0")
    @Column(name = "MINIMO_ASISTENCIA")
    private int minimoAsistencia;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 3, message = "El nombre no puede ser inferior a 3 caracteres")
    private String nombre;

    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    // uni-directional many-to-one association to Tipo
    @ManyToOne
    @JoinColumn(name = "ID_TIPO")
    private Tipo tipo;
}
