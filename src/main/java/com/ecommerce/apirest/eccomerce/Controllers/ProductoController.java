package com.ecommerce.apirest.eccomerce.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.apirest.eccomerce.Entities.Producto;
import com.ecommerce.apirest.eccomerce.Repositories.ProductoRepository;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;


    @GetMapping
    public String obtenerProductos(Model model){
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "producto";
    }

    @GetMapping("/{id}")
    public Producto obtenerProductoPorID(@PathVariable Long id){
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto con id: " +id+ " no encontrado"));
    }

    @GetMapping("/comprarAhora/{id}")
    public String comprarProducto(@PathVariable Long id, Model model){
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto con id: " +id+ " no encontrado"));
      //  producto.setStock(producto.getStock()-1)
      model.addAttribute("producto", producto);
        return "confirmarCompra";
    }

   @PostMapping("/confirmarPedido/{id}")
public String confirmarPedido(@PathVariable Long id, @RequestParam Integer cantidad, Model model) {

    
    Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto con id: " + id + " no encontrado"));

   if (cantidad <= 0) {
        model.addAttribute("error", "La cantidad debe ser mayor a 0.");
        model.addAttribute("producto", producto);
        return "confirmarCompra";
    
   }
    else if (producto.getStock() < cantidad) {
        model.addAttribute("error", "No hay suficiente stock para completar la compra.");
        model.addAttribute("producto", producto);
        return "confirmarCompra"; 
    }

    producto.setStock(producto.getStock() - cantidad);
    productoRepository.save(producto);

    model.addAttribute("producto", producto);
    return "pedidoConfirmado";
}
    

}
