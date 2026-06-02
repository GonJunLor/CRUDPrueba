package com.example.CRUDPrueba;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TareaGestion extends CrudRepository<Tarea, Long> {

    Tarea findById(long id);

    List<Tarea> findByUsuario(Usuario usuario);

    List<Tarea> findByCategoria(String categoria);

    List<Tarea> findByPrivacidad(String privacidad);

    List<Tarea> findByUsuarioAndCategoria(Usuario usuario, String categoria);

    List<Tarea> findByUsuarioAndNombreContainingIgnoreCaseAndCategoriaAndEstado(
        Usuario usuario, String nombre,  String categoria, Estado estado
    );

    @Query("SELECT t FROM Tarea t WHERE t.usuario = :usuario " +
           "AND (:nombre = '' OR LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
           "AND (:categoria = '' OR t.categoria = :categoria) " +
           "AND (:estado IS NULL OR t.estado = :estado)")
    List<Tarea> filtrarTareasDinamico(
        @Param("usuario") Usuario usuario, 
        @Param("nombre") String nombre, 
        @Param("categoria") String categoria, 
        @Param("estado") Estado estado
    );

    @Query("SELECT t FROM Tarea t WHERE " +
            "(:usuario IS NULL OR t.usuario = :usuario) " +
            "AND (:nombre = '' OR LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:categoria = '' OR t.categoria = :categoria) " +
            "AND (:estado IS NULL OR t.estado = :estado)")
    List<Tarea> filtrarTareasDinamicoAdmin(
        @Param("usuario") Usuario usuario, 
        @Param("nombre") String nombre, 
        @Param("categoria") String categoria, 
        @Param("estado") Estado estado
    );
}
