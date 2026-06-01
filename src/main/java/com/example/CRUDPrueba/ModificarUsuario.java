package com.example.CRUDPrueba;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class ModificarUsuario {

    @Autowired
    private UsuarioGestion usuarioBBDD;

    @GetMapping("/verUsuario")
    public String vacio(){return "redirect:/";}
    @GetMapping("/modificarUsuario")
    public String vacio2(){return "redirect:/";}

    @PostMapping("/verUsuario")
    public String verUsuario(
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

        Usuario usuarioModificar = usuarioBBDD.findById(id).get();

        modelo.addAttribute("valor_id", id);
        modelo.addAttribute("valor_codUsuario", usuarioModificar.getCodUsuario());
        modelo.addAttribute("valor_contrasena", "");
        modelo.addAttribute("valor_contrasena2", "");
        modelo.addAttribute("valor_descUsuario", usuarioModificar.getDescUsuario());
        modelo.addAttribute("valor_perfil", usuarioModificar.getPerfil());

        return "modificarUsuario";
    }

    @PostMapping("/modificarUsuario")
    public String modificarUsuario(
        Model modelo,
        HttpSession sesion,
        @RequestParam(value = "idUsuario", defaultValue = "") Long id,
        @RequestParam(value = "codUsuario", defaultValue = "") String formCodUsuario,
        @RequestParam(value = "contrasena", defaultValue = "") String formContrasena,
        @RequestParam(value = "contrasena2", defaultValue = "") String formContrasena2,
        @RequestParam(value = "descUsuario", defaultValue = "") String formDescUsuario,
        @RequestParam(value = "perfil", defaultValue = "") String formPerfil
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        Usuario usuarioModificar = usuarioBBDD.findById(id).get();
        Boolean validaOK = false;

        if (!usuarioModificar.getCodUsuario().equals(formCodUsuario)) {
            validaOK = true;
            // Si existe el usuario lanzamos un mensaje de error
            if (usuarioBBDD.existsByCodUsuario(formCodUsuario)) {
                modelo.addAttribute("error_codUsuario", "El usuario ya existe");
                validaOK = false;
            } 

            String errorUsuario = "El codigo de usuario: "; 
            // El codUsuario tiene que ser una palabra solo
            if (formCodUsuario.contains(" ")) {
                errorUsuario += "Tiene que ser una palabra sin espacios. ";
                modelo.addAttribute("error_codUsuario", errorUsuario);
                validaOK = false;
            }

            // El codUsuario tiene que tener más de tres letras.
            if (formCodUsuario.length()<4) {
                errorUsuario += "Tiene que tener más de tres letras. ";
                modelo.addAttribute("error_codUsuario", errorUsuario);
                validaOK = false;
            }

            if (validaOK) {
                usuarioModificar.setCodUsuario(formCodUsuario);
            }
        }

        if (!formContrasena.equals("")) {
            validaOK = true;
            String errorContrasena = "La contraseña: ";
            if (formContrasena.contains(" ")) {
                errorContrasena += "Tiene que ser una palabra sin espacios. ";
                modelo.addAttribute("error_contrasena", errorContrasena);
                validaOK = false;
            }

            if (formContrasena.length()<4) {
                errorContrasena += "Tiene que tener cuatro o  más caracteres. ";
                modelo.addAttribute("error_contrasena", errorContrasena);
                validaOK = false;
            }

            // Si las contraseñas son distintas lanzamos el error
            if (!formContrasena.equals(formContrasena2)) {
                modelo.addAttribute("error_contrasena2", "Las contraseñas no son iguales");
                validaOK = false;
            }

            if (validaOK) {
                usuarioModificar.setContrasena(BCrypt.hashpw(formContrasena, BCrypt.gensalt()));
            }
        }
        
        if (!usuarioModificar.getDescUsuario().equals(formDescUsuario)) {
            validaOK = true;
            usuarioModificar.setDescUsuario(formDescUsuario);
        }

        if (!usuarioModificar.getPerfil().equals(formPerfil)) {
            validaOK = true;
            usuarioModificar.setPerfil(formPerfil);
        }

        String mensaje = "";
        if (validaOK) {
            usuarioBBDD.save(usuarioModificar);
            mensaje = "Usuario modificado con éxito";
        }

        modelo.addAttribute("mensaje_confirmacion", mensaje);
        modelo.addAttribute("valor_id", id);
        modelo.addAttribute("valor_codUsuario", formCodUsuario);
        modelo.addAttribute("valor_contrasena", formContrasena);
        modelo.addAttribute("valor_contrasena2", formContrasena2);
        modelo.addAttribute("valor_descUsuario", formDescUsuario);
        modelo.addAttribute("valor_perfil", formPerfil);

        return "modificarUsuario";
    }
}
