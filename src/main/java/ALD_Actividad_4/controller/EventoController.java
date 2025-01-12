package ALD_Actividad_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
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
import ALD_Actividad_4.service.EventoService;
// import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/admin/eventos")
public class EventoController {

    @Autowired
    private IEventoDao eventoDao;

    @Autowired
    private ITipoDao tipoDao;

    @Autowired
    private EventoService eventoService;

    // Listar eventos
    @GetMapping
    public String listarEventos(@RequestParam(required = false) String estado,
            @RequestParam(required = false) String destacado,
            Model model) {
        eventoService.actualizarEstadosFechaVencida();

        List<Evento> eventos = eventoService.filtrarEventos(estado, destacado);
        String estadoMostrar = eventoService.determinarEstadoMostrar(estado, destacado);

        model.addAttribute("estado", estadoMostrar);
        model.addAttribute("eventos", eventos);
        model.addAttribute("plazasDisponibles", eventoService.calcularPlazasDisponibles(eventos));

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
        Evento evento = new Evento();
        System.out.println("Objeto Evento Inicializado: " + evento);
        model.addAttribute("evento", evento);
        model.addAttribute("tipos", tipoDao.obtenerTodos());
        return "admin/evento/evento-form";
    }

    // Guardar un evento
    @PostMapping
    public String guardarEvento(@ModelAttribute Evento evento, @RequestParam int tipoId, Model model) {
        String errores = eventoService.validarEvento(evento);

        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            model.addAttribute("tipos", tipoDao.obtenerTodos());
            return "admin/evento/evento-form";
        }

        Tipo tipo = tipoDao.buscarPorId(tipoId);
        if (tipo == null) {
            model.addAttribute("errores", "El tipo seleccionado no es válido.");
            model.addAttribute("tipos", tipoDao.obtenerTodos());
            return "admin/evento/evento-form";
        }

        eventoService.guardarOActualizarEvento(evento, tipo);

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

}
