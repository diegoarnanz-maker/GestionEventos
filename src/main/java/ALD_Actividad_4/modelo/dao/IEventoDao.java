package ALD_Actividad_4.modelo.dao;

import java.util.List;

import ALD_Actividad_4.modelo.entidades.Evento;

public interface IEventoDao extends IGenericoCrud<Evento, Integer> {

    List<Evento> listarPorEstado(String estado);
    List<Evento> listarDestacados(String destacado);
    List<Evento> listarPorTipo(Integer idTipo);
    List<Evento> listarPorEstadoYTipo(String estado, Integer idTipo);
    int contarReservasPorEvento(Integer idEvento);
}
