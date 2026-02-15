package com.example.CRUDPrueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class CrudPruebaApplication {

	public static String sesion;

	public static void main(String[] args) {
		SpringApplication.run(CrudPruebaApplication.class, args);
		sesion = "gonzal";
	}

	@GetMapping("/")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return "inicioPublico";
    }
}
