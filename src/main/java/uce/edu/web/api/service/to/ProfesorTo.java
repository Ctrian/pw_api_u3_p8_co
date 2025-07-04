package uce.edu.web.api.service.to;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.controller.ProfesorController;

public class ProfesorTo {

    private Integer id;

    private String nombre;

    private String apellido;

    private String materia;

    private String email;

    // Links de hijos para ProfesorTo
    public Map<String, String> _links = new HashMap<>();

    public ProfesorTo(Integer id, String nombre, String apellido, String materia, String email, UriInfo uriInfo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.materia = materia;
        this.email = email;

        URI todosHijos = uriInfo.getBaseUriBuilder()
                .path(ProfesorController.class)
                .path(ProfesorController.class, "obtenerHijosPorId")
                .build(id);

        _links.put("hijos", todosHijos.toString());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
