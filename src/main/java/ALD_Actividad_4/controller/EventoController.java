package ALD_Actividad_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ALD_Actividad_4.modelo.dao.IEventoDao;
import ALD_Actividad_4.modelo.dao.ITipoDao;
import ALD_Actividad_4.modelo.entidades.Evento;
import ALD_Actividad_4.modelo.entidades.Tipo;
import jakarta.validation.Valid;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/eventos")
public class EventoController {

    @Autowired
    private IEventoDao eventoDao;

    @Autowired
    private ITipoDao tipoDao;

    // Listar eventos
    @GetMapping
    public String listarEventos(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String destacado,
            Model model) {

        // Antes de mostrar, actualizo estado por si hay alguno que se ha pasado la
        // fecha de inicio y lo paso a "TERMINADO"
        actualizarEstadosFechaVencida();

        List<Evento> eventos;
        if (destacado != null && destacado.equals("S")) {
            eventos = eventoDao.listarDestacados(destacado);
            model.addAttribute("estado", "Eventos Destacados");
        } else if (estado != null) {
            eventos = eventoDao.listarPorEstado(estado);
            model.addAttribute("estado", estado);
        } else {
            eventos = eventoDao.obtenerTodos();
            model.addAttribute("estado", "Todos los Eventos");
        }

        // Calcular las plazas disponibles para cada evento
        Map<Integer, Integer> plazasDisponibles = new HashMap<>();
        for (Evento evento : eventos) {
            int reservasRealizadas = eventoDao.contarReservasPorEvento(evento.getIdEvento());
            int disponibles = evento.getAforoMaximo() - reservasRealizadas;
            plazasDisponibles.put(evento.getIdEvento(), disponibles);
        }

        model.addAttribute("eventos", eventos);
        model.addAttribute("plazasDisponibles", plazasDisponibles);

        return "admin/evento/eventos";
    }

    // Ver detalle de un evento
    @GetMapping("/detalle/{id}")
    public String verDetalleEvento(@PathVariable int id, Model model) {
        model.addAttribute("evento", eventoDao.buscarPorId(id));
        return "admin/evento/evento-detalle";
    }

    // Crear un evento
    @GetMapping("/crear")
    public String crearEvento(Model model) {
        model.addAttribute("evento", new Evento());
        model.addAttribute("tipos", tipoDao.obtenerTodos());
        return "admin/evento/evento-form";
    }

    // Guardar un evento
    @PostMapping
    public String guardarEvento(@Valid @ModelAttribute Evento evento, @RequestParam int tipoId, BindingResult result, Model model) {

        // Compruebo si el form tiene errores. Por probar algo nuevo he querido implementar la validacion en el backend con la dependencia de jakarta.validation (VER ENTIDAD EVENTO). Podria haberlo hecho en el frontend con thymeleaf.
        if (result.hasErrors()) {
            model.addAttribute("tipos", tipoDao.obtenerTodos());
            return "admin/evento/evento-form";
        }
        Tipo tipo = tipoDao.buscarPorId(tipoId);

        // Si es un evento nuevo, el estado por defecto será "ACEPTADO".
        if (evento.getIdEvento() == 0) {
            evento.setEstado("ACEPTADO");
        }

        evento.setTipo(tipo);
        eventoDao.guardar(evento);

        return "redirect:/admin/eventos";
    }

    // Editar un evento
    @GetMapping("/editar/{id}")
    public String editarEvento(@PathVariable int id, Model model) {
        model.addAttribute("evento", eventoDao.buscarPorId(id));
        model.addAttribute("tipos", tipoDao.obtenerTodos());
        return "admin/evento/evento-form";
    }

    // Eliminar un evento
    @GetMapping("/eliminar/{id}")
    public String eliminarEvento(@PathVariable int id) {
        eventoDao.eliminarPorId(id);
        return "redirect:/admin/eventos";
    }

    // Cancelar un evento
    @GetMapping("/cancelar/{id}")
    public String cancelarEvento(@PathVariable int id) {
        Evento e = eventoDao.buscarPorId(id);
        e.setEstado("CANCELADO");
        eventoDao.guardar(e);
        return "redirect:/admin/eventos";
    }

    // Método para actualizar el estado si la fecha de inicio del evento ya pasó
    private void actualizarEstadosFechaVencida() {
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
