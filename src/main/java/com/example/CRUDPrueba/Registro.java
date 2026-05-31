package com.example.CRUDPrueba;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Registro {

    @Autowired
	private UsuarioGestion usuarioBBDD;

    @GetMapping("/registro")
    public String cargarRegistro(
        Model modelo,
        HttpSession sesion
    ){

        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuarioActual!=null) {
            return "redirect:/privado";
        }

        modelo.addAttribute("valor_codUsuario", "");

        return "registro";
    } 

    @PostMapping("/registro")
    public String crearUsuario(
        @RequestParam(value = "codUsuario", defaultValue = "") String codUsuario,
        @RequestParam(value = "contrasena", defaultValue = "") String contrasena,
        @RequestParam(value = "contrasena2", defaultValue = "") String contrasena2,
        @RequestParam(value = "descUsuario", defaultValue = "") String descUsuario,
        @RequestParam(value = "palabraSeguridad", defaultValue = "") String palabraSeguridad,
        Model modelo,
        HttpSession sesion
    ){
        boolean validaOK = true;

        // Comprobación de la palabra de seguridad
        if(!BCrypt.checkpw(palabraSeguridad, "$2a$10$DNUYSMUhFX1xQf/ONd1wxebBZCVSP6bNu3xyMX1JVODW4aOXlHfZ2")){
            modelo.addAttribute("error_palabraSeguridad", "Palabra de seguridad incorrecta");
            validaOK = false;
        }
        
        // Si existe el usuario lanzamos un mensaje de error
        if (usuarioBBDD.existsByCodUsuario(codUsuario)) {
            modelo.addAttribute("error_codUsuario", "El usuario ya existe");
            validaOK = false;
        } 

        String errorUsuario = "El codigo de usuario: "; 
        // El codUsuario tiene que ser una palabra solo
        if (codUsuario.contains(" ")) {
            errorUsuario += "Tiene que ser una palabra sin espacios. ";
            modelo.addAttribute("error_codUsuario", errorUsuario);
            validaOK = false;
        }

        // El codUsuario tiene que tener más de tres letras.
        if (codUsuario.length()<4) {
            errorUsuario += "Tiene que tener más de tres letras. ";
            modelo.addAttribute("error_codUsuario", errorUsuario);
            validaOK = false;
        }

        String errorContrasena = "La contraseña: ";
        if (contrasena.contains(" ")) {
            errorContrasena += "Tiene que ser una palabra sin espacios. ";
            modelo.addAttribute("error_contrasena", errorContrasena);
            validaOK = false;
        }

        if (contrasena.length()<5) {
            errorContrasena += "Tiene que tener más de cuatro caracteres. ";
            modelo.addAttribute("error_contrasena", errorContrasena);
            validaOK = false;
        }

        // Si las contraseñas son distintas lanzamos el error
        if (!contrasena.equals(contrasena2)) {
            modelo.addAttribute("error_contrasena2", "Las contraseñas no son iguales");
            validaOK = false;
        }

        // Si ha pasado las validaciones, creamos el usuario y vamos a inicio privado
        if (validaOK) {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setCodUsuario(codUsuario);
            nuevoUsuario.setContrasena(BCrypt.hashpw(contrasena, BCrypt.gensalt()));
            nuevoUsuario.setDescUsuario(descUsuario);
            nuevoUsuario.setFechaHoraUltimaConexion(LocalDate.now());
            nuevoUsuario.setPerfil("Usuario");

            usuarioBBDD.save(nuevoUsuario);

            // Guardamos el estado de usuario logueado en la sesión privada de éste usuario
            sesion.setAttribute("usuarioLogueado", nuevoUsuario);
            sesion.setAttribute("fechaUltimaConexionAnterior", "Ninguna");
            
            return "redirect:/privado";
        }

        // Si no pasa las validaciones devolvemos los datos al formulario.
        modelo.addAttribute("valor_codUsuario", codUsuario);
        modelo.addAttribute("valor_contrasena", contrasena);
        modelo.addAttribute("valor_contrasena2", contrasena2);
        modelo.addAttribute("valor_descUsuario", descUsuario);
        modelo.addAttribute("valor_palabraSeguridad", palabraSeguridad);

        return "registro";
    }
}
