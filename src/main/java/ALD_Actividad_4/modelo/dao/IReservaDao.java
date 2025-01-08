package ALD_Actividad_4.modelo.dao;

import java.sql.Date;
import java.util.List;

import ALD_Actividad_4.modelo.entidades.Reserva;

public interface IReservaDao extends IGenericoCrud<Reserva, Integer> {

    List<Reserva> listarReservasActivas(String username, Date fechaActual);

    int contarReservasPorEvento(int idEvento);

    int contarCantidadReservadaPorEvento(int idEvento);

    boolean existeReservaPorUsuarioYEvento(String username, int idEvento);

    List<Reserva> buscarPorUsuario(String username);

}
