package com.example.CRUDPrueba;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class inicioPrivado {
    
    @GetMapping("/privado")
    public String cargarInicioPrivado(){
        if (CrudPruebaApplication.sesion.equals("gonzalo")) {
            return "inicioPrivado";
        }
        return "redirect:/login";
    }

}
