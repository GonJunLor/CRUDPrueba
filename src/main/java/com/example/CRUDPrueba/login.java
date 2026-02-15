package com.example.CRUDPrueba;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Login {

    // Aqui entra desde las redirecciones de los enlaces, etiquetas a de html
    @GetMapping("/login")
    public String cargarLogin(){
        
        return "login";
    }
    
    // Este método entra al dar al boton entrar en el formulario del login
    @PostMapping("/login")
    public String comprobarLogin(@RequestParam(value = "nombre", defaultValue = "") String nombre){
        if (nombre.equals("gonzalo")) {
            System.out.println("nombre de usuario: " + nombre);
            Principal.sesion = nombre;
            return "redirect:/privado";
        }
        return "login";
    }
}
