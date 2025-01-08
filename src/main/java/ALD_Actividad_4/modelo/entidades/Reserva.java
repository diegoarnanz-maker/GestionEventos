package ALD_Actividad_4.modelo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="reservas")
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_RESERVA")
	private int idReserva;

	private int cantidad;

	private String observaciones;

	@Column(name="PRECIO_VENTA")
	private BigDecimal precioVenta;

	//uni-directional many-to-one association to Evento
	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USERNAME")
	private Usuario usuario;
}
