package ALD_Actividad_4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    //Ver el menu de role_admon
    @GetMapping("/menu")
    public String adminMenu() {
        return "admin/menu";
    }

}

