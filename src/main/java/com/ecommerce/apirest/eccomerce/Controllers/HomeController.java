package com.ecommerce.apirest.eccomerce.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("mensaje", "Bienvenido a la tienda de videojuegos PS5");
        return "index";
    }

    
}
