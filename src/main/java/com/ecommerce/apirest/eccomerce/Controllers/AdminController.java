package com.ecommerce.apirest.eccomerce.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.apirest.eccomerce.Entities.Producto;
import com.ecommerce.apirest.eccomerce.Entities.Usuario;
import com.ecommerce.apirest.eccomerce.Repositories.ProductoRepository;

import jakarta.servlet.http.HttpSession;

    
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping()
    public String adminHome(Model model, HttpSession session) { 
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario != null && usuario.getRol().equals("ADMIN")) {
            model.addAttribute("productos", productoRepository.findAll());
            return "admin";
        }
        return "redirect:/";
    }



@PostMapping
    public Producto crearProducto(@RequestBody Producto producto){
        return productoRepository.save(producto);
    }

    @PutMapping("/{id}") // a traves de la url nos llega el id del producto a actualizar
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto){
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto con id: " +id+ " no encontrado"));

        producto.setNombre(detallesProducto.getNombre());
        producto.setDescripcion(detallesProducto.getDescripcion());
        producto.setPrecio(detallesProducto.getPrecio());
        producto.setImagen(detallesProducto.getImagen());

        return productoRepository.save(producto);
    }

    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable Long id){
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto con id: " +id+ " no encontrado"));
        productoRepository.delete(producto);
        return "Producto eliminado";
    }


}

