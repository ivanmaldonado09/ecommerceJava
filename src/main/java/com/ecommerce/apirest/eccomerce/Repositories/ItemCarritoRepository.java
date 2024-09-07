package com.ecommerce.apirest.eccomerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.apirest.eccomerce.Entities.ItemCarrito;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
  
}
