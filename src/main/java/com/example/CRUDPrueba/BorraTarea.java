package com.example.CRUDPrueba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class BorraTarea {

    @Autowired
    private TareaGestion tareaBBDD;

    @GetMapping("/verBorrarTarea")
    public String vacio(){return "redirect:/";}
    @GetMapping("/borrarTarea")
    public String vaci2(){return "redirect:/";}

    @PostMapping("/verBorrarTarea")
    public String abrirBorrarTarea(
        Model modelo,
        HttpSession sesion,
        @RequestParam(value = "idTareaBoton", defaultValue = "") long id
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        Tarea tarea = tareaBBDD.findById(id);

        modelo.addAttribute("valor_id", id);
        modelo.addAttribute("valor_hecho", tarea.getEstado()==Estado.COMPLETADA? true : false);
        modelo.addAttribute("valor_nombre", tarea.getNombre());
        modelo.addAttribute("valor_descripcion", tarea.getDescripcion());
        modelo.addAttribute("valor_categoria", tarea.getCategoria());
        modelo.addAttribute("valor_estado", tarea.getStringEstado());
        modelo.addAttribute("valor_fechaTrabajo", tarea.getFechaTrabajo());

        return "borrarTarea";
    }

    @PostMapping("/borrarTarea")
    public String BorrarTarea(
        Model modelo,
        HttpSession sesion,
        @RequestParam(value = "idTarea", defaultValue = "") long id
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        tareaBBDD.deleteById(id);
        
        return "redirect:/privado";
    }

}
