package com.ecommerce.apirest.eccomerce.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.apirest.eccomerce.Entities.Producto;
import com.ecommerce.apirest.eccomerce.Entities.Usuario;
import com.ecommerce.apirest.eccomerce.Repositories.ProductoRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

    
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductoRepository productoRepository;

  

    @GetMapping()
    public String adminHome(Model model, HttpSession session) { 
        
        
        if (isAdmin(session)) {
            model.addAttribute("productos", productoRepository.findAll());
            return "admin";
        }
        return "redirect:/";
    }

private static boolean isAdmin(HttpSession  session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if(usuario != null && usuario.getRol().equals("ADMIN")){
        return true;
    } 
return false;
}

@GetMapping("/product/saveForm")
public String mostrarFormularioSave(HttpSession session) {
    if (isAdmin(session)) {
        return "agregarProducto";
        
    }
    return "redirect:/";
}

@PostMapping("/product/save")
public String crearProducto(@RequestParam String nombre, @RequestParam String descripcion, @RequestParam String precio, @RequestParam String imagen, @RequestParam String stock){
    Producto producto = new Producto();
    producto.setNombre(nombre);
    producto.setDescripcion(descripcion);
    producto.setPrecio(Double.parseDouble(precio));
    producto.setImagen(imagen);
    producto.setStock(Integer.parseInt(stock));
    
    productoRepository.save(producto);

    return "redirect:/admin";
}


@GetMapping("/product/delete/{id}")
public String eliminarProducto(@PathVariable Long id, Model model){
    try {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto con id: " +id+ " no encontrado"));
        productoRepository.delete(producto);
        return "redirect:/admin";
    } catch (Exception e) {
        model.addAttribute("error", e.getMessage());
        return "redirect:/admin";
    }
}

  @GetMapping("/product/edit/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (isAdmin(session)) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("producto", producto);
        return "edit";}
        return "redirect:/";
    }

    @PostMapping("/product/update")
    public String updateProduct(@ModelAttribute("producto") Producto producto) {
        System.out.println(producto); // Reemplaza console.log por System.out.println en Java
        productoRepository.save(producto);
        return "redirect:/admin";
    }

    

}

