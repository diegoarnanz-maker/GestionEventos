package ALD_Actividad_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ALD_Actividad_4.modelo.dao.IEventoDao;

@Controller
@RequestMapping("/anonimo")
public class AnonimoController {

    @Autowired
    private IEventoDao eventoDao;

    // Listar eventos por estado

    // Listar eventos activos
    @GetMapping("/listaActivos")
    public String listarEventosActivos(Model model) {
        model.addAttribute("eventos", eventoDao.listarPorEstado("ACEPTADO"));
        model.addAttribute("estado", "Eventos Activos");
        return "anonimo/evento/eventos";
    }

    // Listar eventos destacados
    @GetMapping("/listaDestacados")
    public String listarEventosDestacados(Model model) {
        model.addAttribute("eventos", eventoDao.listarDestacados("S"));
        model.addAttribute("estado", "Eventos Destacados");
        return "anonimo/evento/eventos";
    }

    // Listar eventos cancelados
    @GetMapping("/listaCancelados")
    public String listarEventosCancelados(Model model) {
        model.addAttribute("eventos", eventoDao.listarPorEstado("CANCELADO"));
        model.addAttribute("estado", "Eventos Cancelados");
        return "anonimo/evento/eventos";
    }

    // Ver detalle de evento
    @GetMapping("/detalle/{id}")
    public String verDetalleEvento(@PathVariable int id, Model model) {
        model.addAttribute("evento", eventoDao.buscarPorId(id));
        return "anonimo/evento/evento-detalle";
    }
}
