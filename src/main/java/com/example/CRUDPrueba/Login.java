package com.example.CRUDPrueba;
// Comentario de prueba para probar si se mantiene el nombre en mayusculas
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Login {

    // Aquí entra desde las redirecciones de los enlaces, etiquetas <a> de html
    @GetMapping("/login")
    public String cargarLogin(){
        
        return "login";
    }
    
    // Este método entra al dar al boton entrar en el formulario del login
    @PostMapping("/login")
    public String comprobarLogin(
        @RequestParam(value = "nombre", defaultValue = "") String nombre,
        HttpSession sesion
    ){
        // Cambiar equals por comprobación de nombre de usuario y contraseña en bbdd
        if (nombre.equals("gonzalo2")) {
            System.out.println("nombre de usuario: " + nombre);

            // Guardamos el estado de usuario logueado en la sesión privada de éste usuario
            sesion.setAttribute("usuarioLogueado", nombre);

            return "redirect:/privado";
        }
        return "login";
    }
}
