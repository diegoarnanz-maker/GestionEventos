package ALD_Actividad_4.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ALD_Actividad_4.modelo.dao.IReservaDao;
import ALD_Actividad_4.modelo.dao.ITipoDao;
import ALD_Actividad_4.modelo.entidades.Evento;
import ALD_Actividad_4.modelo.entidades.Reserva;
import ALD_Actividad_4.modelo.entidades.Tipo;
import ALD_Actividad_4.modelo.entidades.Usuario;

@Service
public class ClienteService {

    @Autowired
    private IReservaDao reservaDao;

    @Autowired
    private ITipoDao tipoDao;

    public Map<Integer, Integer> calcularPlazasDisponibles(List<Evento> eventos) {
        Map<Integer, Integer> plazasDisponibles = new HashMap<>();
        for (Evento evento : eventos) {
            try {
                int reservasRealizadas = reservaDao.contarCantidadReservadaPorEvento(evento.getIdEvento());
                int disponibles = evento.getAforoMaximo() - reservasRealizadas;
                plazasDisponibles.put(evento.getIdEvento(), disponibles);
            } catch (Exception e) {
                System.err.println("Error al calcular plazas disponibles para el id evento: " + evento.getIdEvento());
            }
        }
        return plazasDisponibles;
    }

    public String obtenerNombreTipo(Integer tipoId) {
        try {
            Tipo tipo = tipoDao.buscarPorId(tipoId);
            return (tipo != null) ? tipo.getNombre() : "Tipo no encontrado";
        } catch (Exception e) {
            System.err.println("Error al obtener el nombre del tipo: " + e.getMessage());
            return "Tipo no encontrado";
        }
    }

    public String mapearEstado(String estado) {
        if (estado == null || estado.isEmpty()) {
            return "Todos los eventos";
        }
        switch (estado) {
            case "ACEPTADO":
                return "Eventos Activos";
            case "TERMINADO":
                return "Eventos Terminados";
            case "CANCELADO":
                return "Eventos Cancelados";
            default:
                return "Eventos Desconocidos";
        }
    }

    //Este deberia ir en el servicio de reserva, pero por simplicidad se deja aquí

    public Reserva registrarReserva(Evento evento, Usuario usuario, int cantidad, String observaciones) {
        Reserva reserva = new Reserva();
        reserva.setCantidad(cantidad);
        reserva.setObservaciones(observaciones);
        reserva.setEvento(evento);
        reserva.setUsuario(usuario);
        reserva.setPrecioVenta(evento.getPrecio().multiply(BigDecimal.valueOf(cantidad)));

        reservaDao.guardar(reserva);
        return reserva;
    }

}
