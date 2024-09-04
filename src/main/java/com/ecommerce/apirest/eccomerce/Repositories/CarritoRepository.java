package com.ecommerce.apirest.eccomerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.apirest.eccomerce.Entities.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}
