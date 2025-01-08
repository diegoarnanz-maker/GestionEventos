package ALD_Actividad_4.modelo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ALD_Actividad_4.modelo.entidades.Reserva;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByUsuario_UsernameAndEvento_FechaInicioAfter(String username, Date fechaActual);

    int countByEvento_IdEvento(int idEvento);

    @Query("SELECT COALESCE(SUM(r.cantidad), 0) FROM Reserva r WHERE r.evento.idEvento = :idEvento")
    int contarCantidadReservadaPorEvento(@Param("idEvento") int idEvento);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r WHERE r.usuario.username = :username AND r.evento.idEvento = :idEvento")
    boolean existsByUsuario_UsernameAndEvento_IdEvento(@Param("username") String username, @Param("idEvento") int idEvento);

    List<Reserva> findByUsuario_Username(String username);

}
