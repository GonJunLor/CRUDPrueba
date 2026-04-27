package com.example.CRUDPrueba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		modelo.addAttribute("mensajeConexionBBDD", "Todavia no me he conectado a la BBDD");

		if (usuarioBBDD.findByNombre("gonzalo")!=null) {
			modelo.addAttribute("mensajeConexionBBDD", "Conexión a BBDD exitosa...");
		}
		
    	return "inicioPublico";
    }
}
