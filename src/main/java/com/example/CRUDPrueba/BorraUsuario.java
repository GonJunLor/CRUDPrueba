package com.example.CRUDPrueba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class BorraUsuario {

    @Autowired
    private UsuarioGestion usuarioBBDD;

    @GetMapping("/verBorrarUsuario")
    public String vacio(){return "redirect:/";}
    @GetMapping("/borrarUsuario")
    public String vacio2(){return "redirect:/";}

    @PostMapping("/verBorrarUsuario")
    public String verBorrarUsuario(
        Model modelo,
        HttpSession sesion,
        @RequestParam(value = "idUsuarioBoton", defaultValue = "") Long id
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        Usuario usuarioBorrar = usuarioBBDD.findById(id).get();

        modelo.addAttribute("valor_id", id);
        modelo.addAttribute("valor_codUsuario", usuarioBorrar.getCodUsuario());
        modelo.addAttribute("valor_descUsuario", usuarioBorrar.getDescUsuario());
        modelo.addAttribute("valor_perfil", usuarioBorrar.getPerfil());

        return "borrarUsuario";
    }

    @PostMapping("/borrarUsuario")
    public String borrarUsuario(
        Model modelo,
        HttpSession sesion,
        @RequestParam(value = "idUsuario", defaultValue = "") Long id
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        usuarioBBDD.deleteById(id);

        return "redirect:/privado";
    }
}
