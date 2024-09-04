package com.ecommerce.apirest.eccomerce.Repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.apirest.eccomerce.Entities.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
Optional<Usuario> findByEmailAndPassword(String email, String password);

Optional<Usuario> findByEmail(String email);

}
