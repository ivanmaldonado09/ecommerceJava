package com.ecommerce.apirest.eccomerce.Controllers;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {
    
    

    @GetMapping("/miPerfil")
    public String usuario(HttpSession session, Model model) {
        if (session.getAttribute("isAuth") == null) {
            return "redirect:/";
            
        }
        return "perfilUsuario";
    }

}
