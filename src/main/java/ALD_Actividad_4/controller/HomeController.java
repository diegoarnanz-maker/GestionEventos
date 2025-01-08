package ALD_Actividad_4.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ALD_Actividad_4.modelo.dao.IUsuarioDao;
import ALD_Actividad_4.modelo.entidades.Usuario;

@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioDao usuarioDao;

    // Redirecciona a la página de inicio según el rol del usuario utilizando authentication
    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {

            // String username = authentication.getName();
            String rol = authentication.getAuthorities().toString();
            if (rol.contains("ROLE_ADMON")) {
                return "redirect:/admin/menu";
            } else if (rol.contains("ROLE_CLIENTE")) {
                return "redirect:/cliente/menu";
            }
        }
        return "anonimo/anonimos";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "registrado", required = false) String registrado,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Por favor, inténtelo de nuevo.");
        }
        if (registrado != null) {
            model.addAttribute("success", "Usuario registrado exitosamente. Ahora puede iniciar sesión.");
        }
        return "home/login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "home/registro";
    }

    @PostMapping("/registro")
    public String registroForm(@ModelAttribute Usuario usuario,
            @RequestParam String passwordConfirm,
            Model model) {
        if (!usuario.getPassword().equals(passwordConfirm)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "home/registro";
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setFechaRegistro(new Date());
        usuario.setEnabled(1);
        usuarioDao.guardar(usuario);
        usuarioDao.asignarRol(usuario.getUsername(), 2);

        return "redirect:/login?registrado=true";
    }
}
