package com.ecommerce.apirest.eccomerce.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.apirest.eccomerce.Entities.Producto;
                                                            //tipo de dato de la llave primaria de la tabla
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByStockGreaterThan(int stock);  //productos con stock mayor a 0
    

}
