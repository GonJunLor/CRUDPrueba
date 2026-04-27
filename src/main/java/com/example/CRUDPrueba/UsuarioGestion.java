package com.example.CRUDPrueba;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioGestion  extends CrudRepository<Usuario, Long>{

    
    // Spring entiende que quieres hacer un "SELECT * FROM usuarios WHERE nombre = ?"
    Usuario findByNombre(String nombre);
    /* Para que esa magia funcione, los nombres de los métodos tienen que seguir una convicción 
    muy estricta en inglés: siempre deben empezar por prefijos como findBy, existsBy, deleteBy, 
    seguidos del nombre exacto de la variable en tu clase (empezando en mayúscula). */

    // Le decimos a Spring la consulta exacta (se usa JPQL, que es un SQL adaptado a tus clases Java)
    // @Query("SELECT u FROM Usuario u WHERE u.nombre = ?1")
    // Usuario buscarUsuarioPorNombre(String nombre);
    // Si queremos usar otros nombre podemos poner la query manualmente, aquí ya podemos hacer lo que sea
}
