package com.example.CRUDPrueba;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class ModificarTarea {

    @Autowired
    private TareaGestion tareaBBDD;

    @PostMapping("/verTarea")
    public String cargarTarea(
        Model modelo,
        HttpSession sesion,
        @RequestParam(value = "idTareaBoton", defaultValue = "") String id
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        Tarea tarea = tareaBBDD.findById(Long.parseLong(id));

        System.out.println("salida de privacidad: " + tarea.getPrivacidad());

        modelo.addAttribute("valor_id", id);
        modelo.addAttribute("valor_hecho", tarea.getEstado()==Estado.COMPLETADA? true : false);
        modelo.addAttribute("valor_nombre", tarea.getNombre());
        modelo.addAttribute("valor_descripcion", tarea.getDescripcion());
        modelo.addAttribute("valor_categoria", tarea.getCategoria());
        modelo.addAttribute("valor_estado", tarea.getStringEstado());
        modelo.addAttribute("valor_privacidad", tarea.getPrivacidad());
        modelo.addAttribute("valor_fechaTrabajo", tarea.getFechaTrabajo());

        modelo.addAttribute("categorias", Categoria.values());

        return "modificarTarea";
    }

    @PostMapping("/modificarTarea")
    public String formularioModificarTarea(
        Model modelo,
        HttpSession sesion,
        @RequestParam(value = "idTarea") String id,
        @RequestParam(value = "hecho", required = false, defaultValue = "false") boolean hecho,
        @RequestParam(value = "nombre", defaultValue = "") String nombre,
        @RequestParam(value = "descripcion", defaultValue = "") String descripcion,
        @RequestParam(value = "categoria", defaultValue = "") String categoria,
        @RequestParam(value = "estado", defaultValue = "") String estado,
        @RequestParam(value = "privacidad", defaultValue = "") String privacidad,
        @RequestParam(value = "fechaTrabajo", defaultValue = "") String fechaTrabajo
    ){
        // Recuperamos el atributo de la sesión (hay que castearlo a String)
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        // Si es null (no ha pasado por el login) lo echamos
        if (usuario==null) {
            return "redirect:/login";
        }

        boolean validaOK = true;

        if (nombre.length()<3) {
            modelo.addAttribute("error_nombre", "Mínimo tres caracteres");
            validaOK = false;
        }

        if (fechaTrabajo.equals("") && hecho) {
            modelo.addAttribute("error_hecho", "No puedes completar una tarea sin fecha de trabajo.");
            validaOK = false;
        }

        System.out.println(fechaTrabajo);

        if (validaOK) {
            Tarea tarea = tareaBBDD.findById(Long.parseLong(id));

            tarea.setNombre(nombre);
            tarea.setDescripcion(descripcion);
            tarea.setCategoria(categoria);
            tarea.setPrivacidad(privacidad);
            tarea.setFechaCreacion(LocalDate.now());
            tarea.setFechaTrabajo(fechaTrabajo.equals("")? null : LocalDate.parse(fechaTrabajo));
            tarea.setFechaFinal(calculoFechaFinal(hecho, tarea.getFechaFinal()));
            tarea.setEstado(calculoEstado(hecho, fechaTrabajo));
            tarea.setUsuario(usuario);

            tareaBBDD.save(tarea);

            return "redirect:/privado";
        }

        modelo.addAttribute("valor_id", id);
        modelo.addAttribute("valor_nombre", nombre);
        modelo.addAttribute("valor_descripcion", descripcion);
        modelo.addAttribute("valor_categoria", categoria);
        modelo.addAttribute("valor_estado", estado);
        modelo.addAttribute("valor_privacidad", privacidad);
        modelo.addAttribute("valor_fechaTrabajo", fechaTrabajo);

        modelo.addAttribute("categorias", Categoria.values());

        return "modificarTarea";
    }

    private Estado calculoEstado(Boolean hecho, String fechaTrabajo){

        if (fechaTrabajo.equals("")) {
            return Estado.PENDIENTE;
        } else if (hecho) {
            return Estado.COMPLETADA;
        } else {
            return Estado.PROGRESO;
        }
    }

    private LocalDate calculoFechaFinal(boolean hecho, LocalDate fechaFinalAnterior){
        if (hecho) {
            // Si ya tenía una fecha de fin, la mantiene. Si no, le pone la de hoy.
            return (fechaFinalAnterior != null) ? fechaFinalAnterior : LocalDate.now();
        }
        // Si el checkbox de 'hecho' está desmarcado, la fecha final siempre debe limpiarse (null)
        return null; 
    }
}
