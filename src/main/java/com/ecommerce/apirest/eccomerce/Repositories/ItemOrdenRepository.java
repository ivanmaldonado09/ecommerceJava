package com.ecommerce.apirest.eccomerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.apirest.eccomerce.Entities.ItemOrden;

public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long> {

}
