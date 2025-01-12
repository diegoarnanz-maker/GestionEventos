package ALD_Actividad_4.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ALD_Actividad_4.modelo.dao.IEventoDao;
import ALD_Actividad_4.modelo.entidades.Evento;
import ALD_Actividad_4.modelo.entidades.Tipo;

@Service
public class EventoService {

    @Autowired
    private IEventoDao eventoDao;

    public String validarEvento(Evento evento) {
        StringBuilder errores = new StringBuilder();

        if (evento.getNombre() == null || evento.getNombre().length() < 3) {
            errores.append("El nombre debe tener al menos 3 caracteres. ");
        }

        if (evento.getAforoMaximo() <= 0) {
            errores.append("El aforo máximo debe ser mayor a 0. ");
        }

        if (evento.getMinimoAsistencia() <= 0) {
            errores.append("El mínimo de asistencia debe ser mayor a 0. ");
        }

        if (evento.getDuracion() <= 0) {
            errores.append("La duración debe ser mayor a 0. ");
        }

        if (evento.getPrecio() == null || evento.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            errores.append("El precio debe ser mayor a 0. ");
        }

        if (evento.getFechaInicio() == null) {
            errores.append("La fecha de inicio no puede ser nula. ");
        } else {
            LocalDate fechaInicio = evento.getFechaInicio().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (fechaInicio.isBefore(LocalDate.now())) {
                errores.append("La fecha de inicio no puede ser anterior a la fecha actual. ");
            }
        }

        if (evento.getDestacado() == null
                || (!evento.getDestacado().equals("S") && !evento.getDestacado().equals("N"))) {
            errores.append("El valor de destacado debe ser 'S' o 'N'. ");
        }

        return errores.toString();
    }

    public List<Evento> filtrarEventos(String estado, String destacado) {
        if (destacado != null && destacado.equals("S")) {
            return eventoDao.listarDestacados(destacado);
        } else if (estado != null) {
            return eventoDao.listarPorEstado(estado);
        } else {
            return eventoDao.obtenerTodos();
        }
    }

    public String determinarEstadoMostrar(String estado, String destacado) {
        if (destacado != null && destacado.equals("S")) {
            return "Eventos Destacados";
        } else if (estado != null) {
            return estado;
        }
        return "Todos los Eventos";
    }

    public Map<Integer, Integer> calcularPlazasDisponibles(List<Evento> eventos) {
        Map<Integer, Integer> plazasDisponibles = new HashMap<>();
        for (Evento evento : eventos) {
            int reservasRealizadas = eventoDao.contarReservasPorEvento(evento.getIdEvento());
            int disponibles = evento.getAforoMaximo() - reservasRealizadas;
            plazasDisponibles.put(evento.getIdEvento(), disponibles);
        }
        return plazasDisponibles;
    }

    public void guardarOActualizarEvento(Evento evento, Tipo tipo) {
        evento.setTipo(tipo);

        if (evento.getIdEvento() != 0) {
            Evento eventoExistente = eventoDao.buscarPorId(evento.getIdEvento());
            if (eventoExistente != null) {
                evento.setEstado(eventoExistente.getEstado());
            }
        } else {
            evento.setEstado("ACEPTADO");
        }

        eventoDao.guardar(evento);
    }

    public void actualizarEstadosFechaVencida() {
        List<Evento> eventos = eventoDao.obtenerTodos();
        Date fechaActual = new Date();

        for (Evento evento : eventos) {
            if (evento.getFechaInicio().before(fechaActual) && !"TERMINADO".equals(evento.getEstado())) {
                evento.setEstado("TERMINADO");
                eventoDao.guardar(evento);
            }
        }
    }
}
