package com.ecommerce.apirest.eccomerce.Entities;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario usuario; // el carrito mantiene una relacion 1 a 1 conn usuario

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<ItemCarrito> items; // el carrito mantiene una relacion 1 a muchos con itemCarrito y cascade all para que se guarden los items al guardar el carrito    

  
    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

  
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

}
