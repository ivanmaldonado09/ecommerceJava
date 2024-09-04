package com.ecommerce.apirest.eccomerce.Entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Orden {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    private Usuario usuario; // la orden mantiene una relacion 1 a muchos con usuario ya que un usuario puede tener muchas ordenes

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<ItemOrden> items; // la orden mantiene una relacion 1 a muchos con itemOrden y cascade all para que se guarden los items al guardar la orden
}
