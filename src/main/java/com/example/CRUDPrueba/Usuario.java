package com.example.CRUDPrueba;



import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String codUsuario, contrasena,  descUsuario, perfil;
    private Date fechaHoraUltimaConexion;

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



}
