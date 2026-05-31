package com.example.CRUDPrueba;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codUsuario, contrasena,  descUsuario, perfil;
    private LocalDate fechaHoraUltimaConexion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Tarea> tareas;

    public Usuario(){}

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }    
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public String getCodUsuario() {
        return codUsuario;
    }
    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }
    public String getDescUsuario() {
        return descUsuario;
    }
    public void setDescUsuario(String descUsuario) {
        this.descUsuario = descUsuario;
    }
    public String getPerfil() {
        return perfil;
    }
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    public String getFechaHoraUltimaConexionFormateada() {
        if (this.fechaHoraUltimaConexion == null) {
            return "--";
        }
        return this.fechaHoraUltimaConexion.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    public LocalDate getFechaHoraUltimaConexion() {
        return fechaHoraUltimaConexion;
    }
    public void setFechaHoraUltimaConexion(LocalDate fechaHoraUltimaConexion) {
        this.fechaHoraUltimaConexion = fechaHoraUltimaConexion;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

}