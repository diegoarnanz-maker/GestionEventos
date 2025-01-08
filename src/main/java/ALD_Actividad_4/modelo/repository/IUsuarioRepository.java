package ALD_Actividad_4.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ALD_Actividad_4.modelo.entidades.Usuario;
import jakarta.transaction.Transactional;

public interface IUsuarioRepository extends JpaRepository<Usuario, String> {

    @Query(value = "SELECT up.id_perfil FROM usuario_perfiles up WHERE up.username = :username", nativeQuery = true)
    int obtenerIdPerfilPorUsuario(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuario_perfiles (username, id_perfil) VALUES (:username, :idPerfil)", nativeQuery = true)
    void asignarRol(@Param("username") String username, @Param("idPerfil") int idPerfil);

    Usuario findByUsername(String username);
}
