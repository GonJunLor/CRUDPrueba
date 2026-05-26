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
    public String cargarInicioPrivado(
        Model modelo,
        HttpSession sesion
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        modelo.addAttribute("categorias", Categoria.values());
        modelo.addAttribute("estados", Estado.values());
        modelo.addAttribute("nombreCompleto",usuario.getDescUsuario());

        return "inicioPrivado";
    }

    @PostMapping("/privado")
    public String manejarDatosFormularioPost(
        @RequestParam(value = "nom", defaultValue = "") String nombre,
        @RequestParam(value = "formCategoria", defaultValue = "") String formCategoria,
        @RequestParam(value = "formEstado", defaultValue = "") String formEstado,
        Model modelo,
        HttpSession sesion
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a Usuario)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        modelo.addAttribute("categorias", Categoria.values());
        modelo.addAttribute("estados", Estado.values());
        modelo.addAttribute("nombreCompleto",usuario.getDescUsuario());

        System.out.println("Categoria: " + formCategoria + ", estado: " + Estado.valueOf(formEstado).getTextoMostrar() );

        return "inicioPrivado";
    }

    private void cosasComunes(){
        
    }
}
