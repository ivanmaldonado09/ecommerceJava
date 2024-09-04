package com.ecommerce.apirest.eccomerce.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.apirest.eccomerce.Entities.Usuario;
import com.ecommerce.apirest.eccomerce.Repositories.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Usuario usuario = usuarioRepository.findByEmailAndPassword(email, password).orElse(null);

        if (usuario != null && usuario.getPassword().equals(password)) {
            session.setAttribute("usuario", usuario);
            session.setAttribute("nombreUsuario", usuario.getNombre());
            session.setAttribute("mailUsuario", email);
            session.setAttribute("isAuth", true);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Email o contraseña incorrectos.");
            return "index"; 
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String nombre, @RequestParam String password, Model model) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "El email ya está en uso.");
            return "index";
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setRol("USER");
        
        usuarioRepository.save(nuevoUsuario);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.removeAttribute("mailUsuario");
        session.removeAttribute("nombreUsuario");
        session.removeAttribute("usuario");
        session.removeAttribute("isAuth");

        return "redirect:/"; 
    }
}