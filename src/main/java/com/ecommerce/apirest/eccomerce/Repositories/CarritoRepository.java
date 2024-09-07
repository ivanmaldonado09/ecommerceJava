package com.ecommerce.apirest.eccomerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.apirest.eccomerce.Entities.Carrito;
import com.ecommerce.apirest.eccomerce.Entities.Usuario;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    Carrito findByUsuarioId(Long id);
    Carrito findByUsuario(Usuario usuario);

}
