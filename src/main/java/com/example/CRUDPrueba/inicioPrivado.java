package com.example.CRUDPrueba;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class inicioPrivado {
    
    @GetMapping("/privado")
    public String cargarInicioPrivado(
        @RequestParam(value = "nom", defaultValue = "jaja") String nombre,
        Model modelo
    ){
        if (CrudPruebaApplication.sesion.equals("gonzalo")) {
            modelo.addAttribute("n1", nombre);
            return "inicioPrivado";
        }
        
        return "redirect:/login";
    }

}
