package ALD_Actividad_4.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ALD_Actividad_4.modelo.dao.IUsuarioDao;
import ALD_Actividad_4.modelo.entidades.Usuario;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioDao.obtenerTodos());
        return "admin/usuario/usuarios";
    }

    @GetMapping("/crear")
    public String crearUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuario/usuario-form";
    }

    @PostMapping()
    public String guardarUsuario(@ModelAttribute Usuario usuario,
            @RequestParam String passwordConfirm,
            @RequestParam String rol,
            Model model) {
        if (!usuario.getPassword().equals(passwordConfirm)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "admin/usuarios/crear";
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setFechaRegistro(new Date());
        usuarioDao.guardar(usuario);
        // 1: ROLE_ADMON, 2: ROLE_USER | Uso el input para ver el rol seleccionado
        int rolId = rol.equals("ROLE_ADMON") ? 1 : 2;
        usuarioDao.asignarRol(usuario.getUsername(), rolId);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/eliminar/{username}")
    public String eliminarUsuario(@PathVariable String username) {
        usuarioDao.eliminarPorId(username);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{username}")
    public String editarUsuario(@PathVariable String username, Model model) {
        Usuario usuario = usuarioDao.buscarPorId(username);
        model.addAttribute("usuario", usuario);
        return "admin/usuario/usuario-form";
    }

}
