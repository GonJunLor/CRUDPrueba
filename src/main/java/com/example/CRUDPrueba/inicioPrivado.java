package com.example.CRUDPrueba;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class InicioPrivado {
    
    @GetMapping("/privado")
    public String cargarInicioPrivado(){
        if (!Principal.sesion.equals("gonzalo")) {
            return "redirect:/login";
        }

        return "inicioPrivado";
    }

    @PostMapping("/privado")
    public String manejarDatosFormularioPost(
        @RequestParam(value = "nom", defaultValue = "") String nombre,
        Model modelo
    ){
        if (!Principal.sesion.equals("gonzalo")) {
            return "redirect:/login";
        }

        modelo.addAttribute("n1",nombre);

        return "inicioPrivado";
    }
}
