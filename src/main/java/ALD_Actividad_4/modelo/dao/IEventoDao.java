package ALD_Actividad_4.modelo.dao;

import java.util.List;

import ALD_Actividad_4.modelo.entidades.Evento;

public interface IEventoDao extends IGenericoCrud<Evento, Integer> {

    List<Evento> listarPorEstado(String estado);
    List<Evento> listarDestacados(String destacado);
    List<Evento> listarPorTipo(Integer idTipo);
    List<Evento> listarPorEstadoYTipo(String estado, Integer idTipo);
    int contarReservasPorEvento(Integer idEvento);

    // List<Evento> listarPorDestacadoYEstado(String destacado, String estado);
    // List<Evento> listarPorDestacadoYTipo(String destacado, Integer idTipo);
    // List<Evento> listarPorDestacadoEstadoYTipo(String destacado, String estado, Integer idTipo);

    List<Evento> filtrarEventos(String destacado, String estado, Integer idTipo);
}
