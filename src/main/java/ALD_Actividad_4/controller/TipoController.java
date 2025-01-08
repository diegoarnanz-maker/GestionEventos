package ALD_Actividad_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ALD_Actividad_4.modelo.dao.ITipoDao;
import ALD_Actividad_4.modelo.entidades.Tipo;

@Controller
@RequestMapping("/admin/tipos")
public class TipoController {

    @Autowired
    private ITipoDao tipoDao;

    @GetMapping()
    public String listarTipos(Model model) {
        model.addAttribute("tipos", tipoDao.obtenerTodos());
        return "admin/tipo/tipos";
    }

    @GetMapping("/crear")
    public String crearTipo(Model model) {
        model.addAttribute("tipo", new Tipo());
        return "admin/tipo/tipo-form";
    }

    @PostMapping()
    public String guardarTipo(Tipo tipo, Model model) {
        tipoDao.guardar(tipo);
        return "redirect:/admin/tipos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTipo(@PathVariable int id, Model model) {
        tipoDao.eliminarPorId(id);
        return "redirect:/admin/tipos";
    }

    @GetMapping("/editar/{id}")
    public String editarTipo(@PathVariable int id, Model model) {
        model.addAttribute("tipo", tipoDao.buscarPorId(id));
        return "admin/tipo/tipo-form";
    }

}
