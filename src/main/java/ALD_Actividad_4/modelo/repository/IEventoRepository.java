package ALD_Actividad_4.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ALD_Actividad_4.modelo.entidades.Evento;

public interface IEventoRepository extends JpaRepository<Evento, Integer> {

    List<Evento> findByDestacado(String destacado);

    List<Evento> findByEstado(String estado);

    List<Evento> findByEstadoAndTipo_IdTipo(String estado, Integer idTipo);

    List<Evento> findByTipo_IdTipo(Integer idTipo);

    @Query("SELECT SUM(r.cantidad) FROM Reserva r WHERE r.evento.idEvento = :idEvento")
    int contarReservasPorEvento(@Param("idEvento") int idEvento);

    @Query("SELECT e FROM Evento e " +
            "WHERE (:destacado IS NULL OR e.destacado = :destacado) " +
            "AND (:estado IS NULL OR e.estado = :estado) " +
            "AND (:idTipo IS NULL OR e.tipo.idTipo = :idTipo)")
    List<Evento> filtrarEventos(
            @Param("destacado") String destacado,
            @Param("estado") String estado,
            @Param("idTipo") Integer idTipo);

    // List<Evento> findByDestacadoAndEstado(String destacado, String estado);

    // @Query("SELECT e FROM Evento e WHERE e.destacado = :destacado AND e.tipo.idTipo = :idTipo")
    // List<Evento> findByDestacadoAndTipo_IdTipo(
    //         @Param("destacado") String destacado,
    //         @Param("idTipo") Integer idTipo);

    // @Query("SELECT e FROM Evento e WHERE e.destacado = :destacado AND e.estado = :estado AND e.tipo.idTipo = :idTipo")
    // List<Evento> findByDestacadoAndEstadoAndTipo_IdTipo(
    //         @Param("destacado") String destacado,
    //         @Param("estado") String estado,
    //         @Param("idTipo") Integer idTipo);

}
