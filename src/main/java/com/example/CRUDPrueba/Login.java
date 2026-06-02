package com.example.CRUDPrueba;

import java.time.LocalDate;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Login {

    @Autowired
	private UsuarioGestion usuarioBBDD;

    // Aquí entra desde las redirecciones de los enlaces, etiquetas <a> de html
    @GetMapping("/login")
    public String cargarLogin(HttpSession sesion){

        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuarioActual = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuarioActual!=null) {
            return "redirect:/privado";
        }
        
        return "login";
    }
    
    // Este método entra al dar al boton entrar en el formulario del login
    @PostMapping("/login")
    public String comprobarLogin(
        @RequestParam(value = "codUsuario", defaultValue = "") String codUsuario,
        @RequestParam(value = "contrasena", defaultValue = "") String contrasena,
        HttpSession sesion
    ){
        // Recuperamos el usuario de la bbdd por el codigo de usuario
        Usuario usuario = usuarioBBDD.findFirstByCodUsuario(codUsuario);
        
        // Comprobamos la contraseña con el método de la libreria BCrypt
        if (
            usuario!=null 
            && BCrypt.checkpw(contrasena, usuario.getContrasena()) 
        ) {
            
            // Guardamos el estado de usuario logueado en la sesión privada de éste usuario
            sesion.setAttribute("usuarioLogueado", usuario);
            sesion.setAttribute("fechaUltimaConexionAnterior", usuario.getFechaHoraUltimaConexionFormateada());

            usuario.setFechaHoraUltimaConexion(LocalDate.now());
            usuarioBBDD.save(usuario);

            if (usuario.getPerfil().equals("Usuario")) {
                return "redirect:/privado";
            } else {
                return "redirect:/privadoAdmin";
            }
        }
        return "login";
    }
}
