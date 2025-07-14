package uce.edu.web.api.service.mapper;

import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.to.EstudianteTo;
import uce.edu.web.api.service.to.ProfesorTo;

public class ProfesorMapper {
    
    public static ProfesorTo toTo(Profesor profesor) {
 
        ProfesorTo eTo = new ProfesorTo();
        eTo.setId(profesor.getId());
        eTo.setNombre(profesor.getNombre());
        eTo.setApellido(profesor.getApellido());
        eTo.setMateria(profesor.getMateria());
        eTo.setEmail(profesor.getEmail());
        return eTo;
 
    }
 
    public static Profesor toEntity(ProfesorTo profesorTo) {
        Profesor e = new Profesor();
        e.setId(profesorTo.getId());
        e.setNombre(profesorTo.getNombre());
        e.setApellido(profesorTo.getApellido());
        e.setMateria(profesorTo.getMateria());
        e.setEmail(profesorTo.getEmail());
        return e;
    }
}
