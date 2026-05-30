package com.example.CRUDPrueba;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;



@Controller
public class InicioPrivado {

    @Autowired
    private TareaGestion tareaBBDD;
    
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

        List<Tarea> tareas = tareaBBDD.findByUsuario(usuario);
        for (Tarea tarea : tareas) {
            System.out.println(tarea);
        }
        modelo.addAttribute("tareas", tareas);

        modelo.addAttribute("valor_nombre", "");
        modelo.addAttribute("valor_categoria", "");
        modelo.addAttribute("valor_estado", null);

        modelo.addAttribute("categorias", Categoria.values());
        modelo.addAttribute("estados", Estado.values());
        modelo.addAttribute("nombreCompleto",usuario.getDescUsuario());

        return "inicioPrivado";
    }

    @PostMapping("/privado")
    public String manejarDatosFormularioPost(
        @RequestParam(value = "nom", defaultValue = "") String nombre,
        @RequestParam(value = "formCategoria", defaultValue = "") String formCategoria,
        @RequestParam(value = "formEstado", required = false) Estado formEstado,
        Model modelo,
        HttpSession sesion
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a Usuario)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        // List<Tarea> tareas = usuario.getTareas();
        // List<Tarea> tareas = tareaBBDD.findByUsuario(usuario);
        // List<Tarea> tareas = tareaBBDD.findByCategoria(formCategoria);
        // List<Tarea> tareas = tareaBBDD.findByUsuarioAndCategoria(usuario, formCategoria);
        List<Tarea> tareas = tareaBBDD.filtrarTareasDinamico(
            usuario, nombre, formCategoria, formEstado
        );

        modelo.addAttribute("tareas", tareas);

        modelo.addAttribute("valor_nombre", nombre);
        modelo.addAttribute("valor_categoria", formCategoria);
        modelo.addAttribute("valor_estado", formEstado);

        modelo.addAttribute("categorias", Categoria.values());
        modelo.addAttribute("estados", Estado.values());
        modelo.addAttribute("nombreCompleto",usuario.getDescUsuario());

        System.out.println("Categoria: " + formCategoria + ", estado: " + (formEstado == null? "" : formEstado.getTextoMostrar()) );

        return "inicioPrivado";
    }

}
