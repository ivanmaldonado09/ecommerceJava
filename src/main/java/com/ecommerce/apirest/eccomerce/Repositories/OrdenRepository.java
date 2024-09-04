package com.ecommerce.apirest.eccomerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.apirest.eccomerce.Entities.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

}
