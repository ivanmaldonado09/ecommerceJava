package com.ecommerce.apirest.eccomerce.Controllers;

import com.ecommerce.apirest.eccomerce.Entities.Producto;
import com.ecommerce.apirest.eccomerce.Entities.Usuario;
import com.ecommerce.apirest.eccomerce.Entities.Carrito;
import com.ecommerce.apirest.eccomerce.Entities.ItemCarrito;
import com.ecommerce.apirest.eccomerce.Repositories.CarritoRepository;
import com.ecommerce.apirest.eccomerce.Repositories.ProductoRepository;
import com.ecommerce.apirest.eccomerce.Repositories.ItemCarritoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
 @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

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
            
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(1);
            carrito.getItems().add(nuevoItem);
        }

        carritoRepository.save(carrito); 

        model.addAttribute("carrito", carrito);
        return "redirect:/carrito"; 
    }


    @PostMapping("/confirmarCompra")
public String confirmarCompra(@RequestParam("cantidades") List<Integer> cantidades, HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        return "redirect:/";
    }

    Carrito carrito = carritoRepository.findByUsuario(usuario);
    if (carrito == null || carrito.getItems().isEmpty()) {
        return "redirect:/carrito";
    }

    boolean tieneError = false;
    StringBuilder errores = new StringBuilder();
    List<ItemCarrito> itemsAEliminar = new ArrayList<>();


    List<ItemCarrito> items = carrito.getItems();
    for (int i = 0; i < items.size(); i++) {
        ItemCarrito item = items.get(i);
        Integer cantidadSolicitada = cantidades.get(i);

        if (cantidadSolicitada == null || cantidadSolicitada <= 0) {
            tieneError = true;
            errores.append("La cantidad para el producto '")
                   .append(item.getProducto().getNombre())
                   .append("' debe ser mayor a 0.\n");
        } else if (cantidadSolicitada > item.getProducto().getStock()) {
            tieneError = true;
            errores.append("No hay suficiente stock para el producto '")
                   .append(item.getProducto().getNombre())
                   .append("'. Stock disponible: ")
                   .append(item.getProducto().getStock())
                   .append(", cantidad solicitada: ")
                   .append(cantidadSolicitada)
                   .append(".\n");
        } else {
            item.getProducto().setStock(item.getProducto().getStock() - cantidadSolicitada);
            itemsAEliminar.add(item); 
            
        }
    }

    if (tieneError) {
        model.addAttribute("error", errores.toString());
        model.addAttribute("carrito", carrito);
        return "miCarrito";
    }

    itemCarritoRepository.deleteAll(itemsAEliminar);

    carrito.getItems().clear();
    carritoRepository.save(carrito);

    return "pedidoConfirmado";
}


    @GetMapping("/eliminar/{productoId}")
public String eliminarProductoDelCarrito(@PathVariable Long productoId, HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        return "redirect:/login"; 
    }

    Carrito carrito = carritoRepository.findByUsuario(usuario);
    if (carrito == null) {
        return "redirect:/carrito"; 
    }

    
    ItemCarrito itemAEliminar = carrito.getItems().stream()
            .filter(item -> item.getProducto().getId().equals(productoId))
            .findFirst()
            .orElse(null);

    if (itemAEliminar != null) {
        carrito.getItems().remove(itemAEliminar); 
        itemCarritoRepository.delete(itemAEliminar); 
        carritoRepository.save(carrito); 
    }

    model.addAttribute("carrito", carrito);
    return "redirect:/carrito";
}
    
}
