package ALD_Actividad_4.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class ErrorPersonalizadoController {
    @RequestMapping("/error")
    public String handleError() {
        // Redirige a una página de error personalizada
        return "error/error-personalizado";
    }
}

//REVISAR
