package com.example.CRUDPrueba;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class CrearTarea {

    @Autowired
    private TareaGestion tareaBBDD;

    @GetMapping("/crearTarea")
    public String cargaCrearTarea(
        Model modelo,
        HttpSession sesion
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        modelo.addAttribute("valor_nombre", "");
        modelo.addAttribute("valor_descripcion", "");
        modelo.addAttribute("valor_categoria", "");
        modelo.addAttribute("valor_privacidad", "privado");
        modelo.addAttribute("valor_fechaTrabajo", "");

        modelo.addAttribute("categorias", Categoria.values());

        return "crearTarea";
    }

    @PostMapping("/crearTarea")
    public String formularioCrearTarea(
        Model modelo,
        HttpSession sesion,
        @RequestParam(value = "nombre", defaultValue = "") String nombre,
        @RequestParam(value = "descripcion", defaultValue = "") String descripcion,
        @RequestParam(value = "categoria", defaultValue = "") String categoria,
        @RequestParam(value = "privacidad", defaultValue = "") String privacidad,
        @RequestParam(value = "fechaTrabajo", defaultValue = "") String fechaTrabajo
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        boolean validaOK = true;

        if (nombre.length()<3) {
            modelo.addAttribute("error_nombre", "Mínimo tres caracteres");
            validaOK = false;
        }

        System.out.println(fechaTrabajo);

        if (validaOK) {
            Tarea nuevaTarea = new Tarea();
            nuevaTarea.setNombre(nombre);
            nuevaTarea.setDescripcion(descripcion);
            nuevaTarea.setCategoria(categoria);
            nuevaTarea.setPrivacidad(privacidad);
            nuevaTarea.setFechaCreacion(LocalDate.now());
            nuevaTarea.setFechaTrabajo(fechaTrabajo.equals("")? null : LocalDate.parse(fechaTrabajo));
            nuevaTarea.setFechaFinal(null);
            nuevaTarea.setEstado(fechaTrabajo.equals("")? Estado.PENDIENTE : Estado.PROGRESO);
            nuevaTarea.setUsuario(usuario);

            tareaBBDD.save(nuevaTarea);

            return "redirect:/privado";
        }

        modelo.addAttribute("valor_nombre", nombre);
        modelo.addAttribute("valor_descripcion", descripcion);
        modelo.addAttribute("valor_categoria", categoria);
        modelo.addAttribute("valor_privacidad", privacidad);
        modelo.addAttribute("valor_fechaTrabajo", fechaTrabajo);

        modelo.addAttribute("categorias", Categoria.values());
        return "crearTarea";
    }
}
