package com.ecommerce.apirest.eccomerce.Controllers;

import com.ecommerce.apirest.eccomerce.Entities.Producto;
import com.ecommerce.apirest.eccomerce.Entities.Usuario;
import com.ecommerce.apirest.eccomerce.Entities.Carrito;
import com.ecommerce.apirest.eccomerce.Entities.ItemCarrito;
import com.ecommerce.apirest.eccomerce.Repositories.CarritoRepository;
import com.ecommerce.apirest.eccomerce.Repositories.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
 @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login"; 
        }

        Carrito carrito = carritoRepository.findByUsuario(usuario);
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuario(usuario);
            carritoRepository.save(carrito); 
        }

        model.addAttribute("carrito", carrito);
        return "miCarrito"; 
    }

    @GetMapping("/agregar/{productoId}")
    public String agregarProductoAlCarrito(@PathVariable Long productoId, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login"; 
        }

        Carrito carrito = carritoRepository.findByUsuario(usuario);
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuario(usuario);
            carritoRepository.save(carrito); 
        }

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // agregar un producto al carrito
        ItemCarrito itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            // Si el producto ya está en el carritoaumenta la cantidad
            itemExistente.setCantidad(itemExistente.getCantidad() + 1);
        } else {
            // Si el producto no está en el carrito se añade
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(1); // Empieza con 1
            carrito.getItems().add(nuevoItem);
        }

        carritoRepository.save(carrito); 

        model.addAttribute("carrito", carrito);
        return "redirect:/carrito"; 
    }
  
}
