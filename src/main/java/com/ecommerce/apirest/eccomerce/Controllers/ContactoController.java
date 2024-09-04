package com.ecommerce.apirest.eccomerce.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ContactoController {
    
    @GetMapping("/contacto")
    public String contacto(Model model){
        model.addAttribute("mensaje", "Contactanos");
        return "contacto";
    }

    @GetMapping("/contacto/verSucursales")
    public String verSucursales(Model model){ 
        return "sucursales";
    }
    
}
