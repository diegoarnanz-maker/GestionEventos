package ALD_Actividad_4.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ALD_Actividad_4.modelo.entidades.Evento;

public interface IEventoRepository extends JpaRepository<Evento, Integer> {

    List<Evento> findByDestacado(String destacado);

    // @Query("SELECT e FROM Evento e WHERE (:estado IS NULL OR e.estado =
    // :estado)")
    // List<Evento> listarPorEstado(@Param("estado") String estado);
    List<Evento> findByEstado(String estado);

    List<Evento> findByEstadoAndTipo_IdTipo(String estado, Integer idTipo);

    List<Evento> findByTipo_IdTipo(Integer idTipo);

    @Query("SELECT SUM(r.cantidad) FROM Reserva r WHERE r.evento.idEvento = :idEvento")
    int contarReservasPorEvento(@Param("idEvento") int idEvento);

}
