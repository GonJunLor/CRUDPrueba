package com.example.CRUDPrueba;

public enum Estado {
    PENDIENTE("Pendiente"),
    PROGRESO("En progreso"),
    COMPLETADA("Completada");

    // Atributo para guardar el texto con espacios
    private final String textoMostrar;

    // El constructor del enum (siempre es privado internamente)
    Estado(String textoMostrar) {
        this.textoMostrar = textoMostrar;
    }

    // Getter para que Thymeleaf pueda leer el texto desde el HTML
    public String getTextoMostrar() {
        return this.textoMostrar;
    }
}
