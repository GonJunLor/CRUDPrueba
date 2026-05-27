package com.example.CRUDPrueba;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private int id;
    private String codUsuario, contrasena,  descUsuario, perfil;
    private Date fechaHoraUltimaConexion;

    // fetch = FetchType.EAGER solo para cuando hay pocas tareas que recuperar sino hacer solucion comentada al final
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Tarea> tareas;

    public Usuario(){}

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public Date getFechaHoraUltimaConexion() {
        return fechaHoraUltimaConexion;
    }
    public void setFechaHoraUltimaConexion(Date fechaHoraUltimaConexion) {
        this.fechaHoraUltimaConexion = fechaHoraUltimaConexion;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    /*
    En la clase que queramos recuperar las tareas:
        // Volvemos a pedir el usuario completo a la BBDD usando su ID.
        // Al estar dentro del repositorio, la sesión de Hibernate estará abierta y activa.
        Usuario usuarioActual = usuarioBBDD.findById(usuarioSesion.getId()).orElse(null);

        if (usuarioActual != null) {
            List<Tarea> tareas = usuarioActual.getTareas();
            for (Tarea tarea : tareas) {
                System.out.println(tarea.getNombre());
            }
        }
    */

}
