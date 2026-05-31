package com.example.CRUDPrueba;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class InicioPrivadoAdmin {

    @Autowired
    private TareaGestion tareaBBDD;
    @Autowired
    private UsuarioGestion usuarioBBDD;

    @GetMapping("/privadoAdmin")
    public String cargarInicioPrivadoAdmin(
        Model modelo,
        HttpSession sesion
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        Iterable<Tarea> tareas = tareaBBDD.findAll();
        Iterable<Usuario> usuarios = usuarioBBDD.findAll();

        modelo.addAttribute("tareas", tareas);
        modelo.addAttribute("usuarios", usuarios);

        modelo.addAttribute("valor_nombre", "");
        modelo.addAttribute("valor_categoria", "");
        modelo.addAttribute("valor_estado", null);
        modelo.addAttribute("fechaUltimaConexionAnterior", sesion.getAttribute("fechaUltimaConexionAnterior"));

        modelo.addAttribute("categorias", Categoria.values());
        modelo.addAttribute("estados", Estado.values());
        modelo.addAttribute("nombreCompleto",usuario.getDescUsuario());

        return "inicioPrivadoAdmin";
    }

    @PostMapping("/privadoAdmin")
    public String manejarDatosFormularioPostAdmin(
        @RequestParam(value = "nom", defaultValue = "") String nombre,
        @RequestParam(value = "formCategoria", defaultValue = "") String formCategoria,
        @RequestParam(value = "formEstado", required = false) Estado formEstado,
        @RequestParam(value = "formUsuario", required = false) Long formUsuarioId,
        Model modelo,
        HttpSession sesion
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a Usuario)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        // Buscamos el objeto Usuario real si se seleccionó uno en el combo
        Usuario formUsuario = null;
        if (formUsuarioId != null) {
            // Asumiendo que tu usuarioBBDD tiene findById o findById(...).orElse(null)
            formUsuario = usuarioBBDD.findById(formUsuarioId).orElse(null); 
        }

        List<Tarea> tareas = tareaBBDD.filtrarTareasDinamicoAdmin(
            formUsuario, nombre, formCategoria, formEstado
        );
        Iterable<Usuario> usuarios = usuarioBBDD.findAll();

        modelo.addAttribute("tareas", tareas);
        modelo.addAttribute("usuarios", usuarios);

        modelo.addAttribute("valor_nombre", nombre);
        modelo.addAttribute("valor_categoria", formCategoria);
        modelo.addAttribute("valor_estado", formEstado);
        modelo.addAttribute("valor_usuario", formUsuario);
        modelo.addAttribute("fechaUltimaConexionAnterior", sesion.getAttribute("fechaUltimaConexionAnterior"));

        modelo.addAttribute("categorias", Categoria.values());
        modelo.addAttribute("estados", Estado.values());
        modelo.addAttribute("nombreCompleto",usuario.getDescUsuario());

        System.out.println("Categoria: " + formCategoria + ", estado: " + (formEstado == null? "" : formEstado.getTextoMostrar()) );

        return "inicioPrivadoAdmin";
    }
}
