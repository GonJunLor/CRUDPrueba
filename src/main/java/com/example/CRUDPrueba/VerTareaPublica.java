package com.example.CRUDPrueba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerTareaPublica {

    @Autowired
    private TareaGestion tareaBBDD;

    @GetMapping("/verTareaPublica")
    public String vacio(){return "redirect:/";}

    @PostMapping("/verTareaPublica")
    public String cargarTareaPublica(
        Model modelo,
        @RequestParam(value = "idTareaBoton", defaultValue = "") String id
    ){

        Tarea tarea = tareaBBDD.findById(Long.parseLong(id));

        modelo.addAttribute("valor_id", id);
        modelo.addAttribute("valor_hecho", tarea.getEstado()==Estado.COMPLETADA? true : false);
        modelo.addAttribute("valor_nombre", tarea.getNombre());
        modelo.addAttribute("valor_descripcion", tarea.getDescripcion());
        modelo.addAttribute("valor_categoria", tarea.getCategoria());
        modelo.addAttribute("valor_estado", tarea.getStringEstado());
        modelo.addAttribute("valor_fechaTrabajo", tarea.getFechaTrabajo());

        return "verTareaPublica";
    }
}
