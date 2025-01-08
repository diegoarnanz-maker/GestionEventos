package ALD_Actividad_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ALD_Actividad_4.modelo.dao.IPerfilDao;
import ALD_Actividad_4.modelo.entidades.Perfil;

@Controller
@RequestMapping("/admin/perfiles")
public class PerfilController {

    @Autowired
    private IPerfilDao perfilDao;

    @GetMapping
    public String listarPerfiles(Model model) {
        model.addAttribute("perfiles", perfilDao.obtenerTodos());
        return "admin/perfil/perfiles";
    }

    @GetMapping("/crear")
    public String crearPerfil(Model model) {
        model.addAttribute("perfil", new Perfil());
        return "admin/perfil/perfil-form";
    }

    @PostMapping()
    public String guardarPerfil(@ModelAttribute Perfil perfil, Model model) {
        perfilDao.guardar(perfil);
        return "redirect:/admin/perfiles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPerfil(@PathVariable int id, Model model) {
        perfilDao.eliminarPorId(id);
        return "redirect:/admin/perfiles";
    }

    @GetMapping("/editar/{id}")
    public String editarPerfil(@PathVariable int id, Model model) {
        model.addAttribute("perfil", perfilDao.buscarPorId(id));
        return "admin/perfil/perfil-form";
    }

}
