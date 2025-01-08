package ALD_Actividad_4.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ALD_Actividad_4.modelo.entidades.Evento;
import ALD_Actividad_4.modelo.repository.IEventoRepository;

@Repository
public class EventoDaoImplJpaMy8 implements IEventoDao {

    @Autowired
    IEventoRepository eventoRepository;

    @Override
    public List<Evento> obtenerTodos() {

        try {
            return eventoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Evento buscarPorId(Integer id) {
        try {
            return eventoRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void guardar(Evento entidad) {
        try {
            eventoRepository.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Evento entidad) {
        try {
            eventoRepository.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Evento entidad) {
        try {
            eventoRepository.delete(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPorId(Integer id) {
        try {
            eventoRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Evento> listarPorEstado(String estado) {
        try {
            if (estado == null || estado.isEmpty()) {
                return eventoRepository.findAll();
            } else {
                return eventoRepository.findByEstado(estado);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Evento> listarDestacados(String destacado) {
        try {
            return eventoRepository.findByDestacado(destacado);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Evento> listarPorEstadoYTipo(String estado, Integer idTipo) {
        try {
            return eventoRepository.findByEstadoAndTipo_IdTipo(estado, idTipo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Evento> listarPorTipo(Integer idTipo) {
        System.out.println("Filtrando eventos por tipo: " + idTipo);
        try {
            List<Evento> eventos = eventoRepository.findByTipo_IdTipo(idTipo);
            System.out.println("Eventos encontrados: " + eventos.size());
            return eventos;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public int contarReservasPorEvento(Integer idEvento) {
        try {
            return eventoRepository.contarReservasPorEvento(idEvento);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
