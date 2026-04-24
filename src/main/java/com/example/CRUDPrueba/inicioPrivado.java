package com.example.CRUDPrueba;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;



@Controller
public class InicioPrivado {
    
    @GetMapping("/privado")
    public String cargarInicioPrivado(HttpSession sesion){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        String usuario = (String) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) o no es "gonzalo2", lo echamos
        if (usuario==null || !usuario.equals("gonzalo2")) {
            return "redirect:/login";
        }

        return "inicioPrivado";
    }

    @PostMapping("/privado")
    public String manejarDatosFormularioPost(
        @RequestParam(value = "nom", defaultValue = "") String nombre,
        Model modelo,
        HttpSession sesion
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        String usuario = (String) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) o no es "gonzalo2", lo echamos
        if (usuario==null || !usuario.equals("gonzalo2")) {
            return "redirect:/login";
        }

        modelo.addAttribute("n1",nombre);
        System.out.println("Sesión en privado del usuario: " + usuario);

        return "inicioPrivado";
    }
}
