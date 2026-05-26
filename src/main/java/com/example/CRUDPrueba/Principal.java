package com.example.CRUDPrueba;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@SpringBootApplication
@Controller
public class Principal {

	@Autowired
	private UsuarioGestion usuarioBBDD;

	public static void main(String[] args) {
		SpringApplication.run(Principal.class, args);
	}

	@GetMapping("/")
    public String hello(
		@RequestParam(value = "name", defaultValue = "World") String name,
		Model modelo
	) {
		
    	return "inicioPublico";
    }

	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession sesion){

		// Forma 1 que es eliminando el atributo del usuario logueado
		sesion.removeAttribute("usuarioLogueado");

		// Forma 2 que es borrando toda la sesion
		//sesion.invalidate();

		return "redirect:/";
	}
}
