package com.example.CRUDPrueba;

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
    public String cargarRegistro(HttpSession sesion){

        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuarioActual!=null) {
            return "redirect:/privado";
        }

        return "registro";
    } 

    @PostMapping("/registro")
    public String crearUsuario(
        @RequestParam(value = "codUsuario", defaultValue = "") String codUsuario,
        @RequestParam(value = "contrasena", defaultValue = "") String contrasena,
        @RequestParam(value = "descUsuario", defaultValue = "") String descUsuario,
        Model modelo,
        HttpSession sesion
    ){
        if (usuarioBBDD.existsByCodUsuario(codUsuario)) {
            // Si existe el usuario lanzamos un mensaje de error
            modelo.addAttribute("error_codUsuario", "El usuario ya existe");
        } else {
            // Si no existe el usuario lo creamos y vamos a inicio privado
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setCodUsuario(codUsuario);
            nuevoUsuario.setContrasena(BCrypt.hashpw(contrasena, BCrypt.gensalt()));
            nuevoUsuario.setDescUsuario(descUsuario);
            nuevoUsuario.setFechaHoraUltimaConexion(new Date());
            nuevoUsuario.setPerfil("Usuario");

            usuarioBBDD.save(nuevoUsuario);

            // Guardamos el estado de usuario logueado en la sesión privada de éste usuario
            sesion.setAttribute("usuarioLogueado", nuevoUsuario);
            
            return "redirect:/privado";
        }

        return "registro";
    }
}
