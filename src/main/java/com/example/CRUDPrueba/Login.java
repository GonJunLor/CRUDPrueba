package com.example.CRUDPrueba;

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
    public String cargarLogin(){
        
        return "login";
    }
    
    // Este método entra al dar al boton entrar en el formulario del login
    @PostMapping("/login")
    public String comprobarLogin(
        @RequestParam(value = "codUsuario", defaultValue = "") String codUsuario,
        HttpSession sesion
    ){
        // Cambiar equals por comprobación de nombre de usuario y contraseña en bbdd
        if (usuarioBBDD.findByCodUsuario(codUsuario)!=null) {
            System.out.println("nombre de usuario: " + codUsuario);

            // Guardamos el estado de usuario logueado en la sesión privada de éste usuario
            sesion.setAttribute("usuarioLogueado", codUsuario);

            // return "redirect:/privado";
            return "inicioPrivado";
        }
        return "login";
    }
}
