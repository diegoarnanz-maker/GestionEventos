package ALD_Actividad_4.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private String apellidos;

	private String direccion;

	private int enabled;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_REGISTRO")
	private Date fechaRegistro;

	private String nombre;

	private String password;

	private String email;

	//bi-directional many-to-many association to Perfile
	@ManyToMany
	@JoinTable(
		name="usuario_perfiles"
		, joinColumns={
			@JoinColumn(name="USERNAME")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_PERFIL")
			}
		)
	private List<Perfil> perfiles;
}
