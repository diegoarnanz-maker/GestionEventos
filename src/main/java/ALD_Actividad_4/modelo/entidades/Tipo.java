package ALD_Actividad_4.modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="tipos")
public class Tipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TIPO")
	private int idTipo;

	private String descripcion;

	private String nombre;
}
