package ALD_Actividad_4.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ALD_Actividad_4.modelo.dao.IEventoDao;
import ALD_Actividad_4.modelo.dao.IReservaDao;
import ALD_Actividad_4.modelo.dao.ITipoDao;
import ALD_Actividad_4.modelo.dao.IUsuarioDao;
import ALD_Actividad_4.modelo.entidades.Evento;
import ALD_Actividad_4.modelo.entidades.Reserva;
import ALD_Actividad_4.modelo.entidades.Tipo;
import ALD_Actividad_4.modelo.entidades.Usuario;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IEventoDao eventoDao;

    @Autowired
    private IReservaDao reservaDao;

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private ITipoDao tipoDao;

    // Ver menú de role_cliente
    @GetMapping("/menu")
    public String clienteMenu(Model model) {
        List<Tipo> tipos = tipoDao.obtenerTodos();
        model.addAttribute("tipos", tipos);
        return "cliente/menu";
    }

    // Listar eventos con filtros combinados: estado, tipo y destacado
    @GetMapping("/evento")
    public String listarEventos(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String destacado,
            @RequestParam(required = false) String idTipo,
            Model model) {

        // Conversión de parámetros
        Integer tipoId = (idTipo != null && !idTipo.isEmpty()) ? Integer.parseInt(idTipo) : null;
        estado = (estado != null && !estado.isEmpty()) ? estado : null;
        destacado = (destacado != null && !destacado.isEmpty()) ? destacado : null;

        // Consulta filtrada
        List<Evento> eventos = eventoDao.filtrarEventos(destacado, estado, tipoId);

        // Títulos para el filtro aplicado
        String tipoTitulo = (tipoId != null) ? obtenerNombreTipo(tipoId) : "Todos los tipos";
        String estadoTitulo = (estado != null) ? mapearEstado(estado) : "Todos los eventos";
        String destacadoTitulo = (destacado != null && destacado.equals("S")) ? "Destacados" : null;


        // Cálculo de plazas disponibles
        Map<Integer, Integer> plazasDisponibles = calcularPlazasDisponibles(eventos);

        // Añadir atributos al modelo
        model.addAttribute("eventos", eventos);
        model.addAttribute("plazasDisponibles", plazasDisponibles);
        model.addAttribute("tipo", tipoTitulo);
        model.addAttribute("estado", estadoTitulo);
        model.addAttribute("destacado", destacadoTitulo);

        return "cliente/evento/eventos";
    }

    // Ver detalle evento por id
    @GetMapping("/evento/detalle/{id}")
    public String verDetalleEvento(@PathVariable int id, Model model) {
        Evento evento = eventoDao.buscarPorId(id);
        int reservasRealizadas = reservaDao.contarCantidadReservadaPorEvento(id);
        int plazasDisponibles = evento.getAforoMaximo() - reservasRealizadas;

        model.addAttribute("evento", evento);
        model.addAttribute("plazasDisponibles", plazasDisponibles);
        return "cliente/evento/evento-detalle";
    }

    // Registrar reserva de evento
    @PostMapping("/evento/reservar/{id}")
    public String reservarEvento(
            @PathVariable int id,
            @RequestParam int cantidad,
            @RequestParam String observaciones,
            Authentication authentication,
            Model model) {

        Evento evento = eventoDao.buscarPorId(id);
        int reservasRealizadas = reservaDao.contarCantidadReservadaPorEvento(id);
        int plazasDisponibles = evento.getAforoMaximo() - reservasRealizadas;

        Usuario usuario = usuarioDao.buscarPorUsername(authentication.getName());

        boolean reservaExistente = reservaDao.existeReservaPorUsuarioYEvento(usuario.getUsername(), id);
        if (reservaExistente) {
            model.addAttribute("error", "Ya has realizado una reserva para este evento.");
            model.addAttribute("evento", evento);
            model.addAttribute("plazasDisponibles", plazasDisponibles);
            return "cliente/evento/evento-detalle";
        }

        if (cantidad > 10) {
            model.addAttribute("error", "No puedes reservar más de 10 plazas por vez.");
            model.addAttribute("evento", evento);
            model.addAttribute("plazasDisponibles", plazasDisponibles);
            return "cliente/evento/evento-detalle";
        }

        if (cantidad > plazasDisponibles) {
            model.addAttribute("error", "No hay suficientes plazas disponibles.");
            model.addAttribute("evento", evento);
            model.addAttribute("plazasDisponibles", plazasDisponibles);
            return "cliente/evento/evento-detalle";
        }

        Reserva reserva = new Reserva();
        reserva.setCantidad(cantidad);
        reserva.setObservaciones(observaciones);
        reserva.setEvento(evento);
        reserva.setUsuario(usuario);
        reserva.setPrecioVenta(evento.getPrecio().multiply(BigDecimal.valueOf(cantidad)));

        reservaDao.guardar(reserva);

        model.addAttribute("success", "Reserva realizada exitosamente.");
        return "redirect:/cliente/reserva";
    }

    // METODOS (Podrian ir en un servicio para dejar mas limpio el controller)

    // Método que een el titulo de eventos salga mas bonito
    private String mapearEstado(String estado) {
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

    // Método para obtener el nombre del tipo
    private String obtenerNombreTipo(Integer tipoId) {
        try {
            Tipo tipo = tipoDao.buscarPorId(tipoId);
            return (tipo != null) ? tipo.getNombre() : "Tipo no encontrado";
        } catch (Exception e) {
            System.err.println("Error al obtener el nombre del tipo: " + e.getMessage());
            return "Tipo no encontrado";
        }
    }

    // Método para calcular las plazas disponibles de cada evento
    private Map<Integer, Integer> calcularPlazasDisponibles(List<Evento> eventos) {
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

}
