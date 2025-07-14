package uce.edu.web.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.repository.modelo.Hijo;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IHijoService;
import uce.edu.web.api.service.IProfesorService;
import uce.edu.web.api.service.mapper.EstudianteMapper;
import uce.edu.web.api.service.mapper.ProfesorMapper;
import uce.edu.web.api.service.to.EstudianteTo;
import uce.edu.web.api.service.to.ProfesorTo;

@Path("/profesores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfesorController {

    @Inject
    private IProfesorService profesorService;

    @Inject
    private IHijoService iHijoService;

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarProfesorPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {

        ProfesorTo profesorTo = ProfesorMapper.toTo(this.profesorService.buscarPorId(id));
        profesorTo.buildURI(uriInfo);

        return Response.status(Response.Status.ACCEPTED).entity(profesorTo).build();
    }

    // ?materia=Programacion&provincia=pichincha
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar Profesores", description = "Este endpoint obtiene una lista de todos los profesores")
    public Response consultarProfesores(@QueryParam("materia") String materia,
            @QueryParam("provincia") String provincia, @Context UriInfo uriInfo) {
        System.out.println("Provincia query param:" + provincia);

        List<Profesor> profesors = this.profesorService.buscarTodos();
        List<ProfesorTo> profesorTos = new ArrayList<>();

        for (Profesor p : profesors) {
            ProfesorTo profesorTo = ProfesorMapper.toTo(p);
            profesorTo.buildURI(uriInfo);
            profesorTos.add(profesorTo);
        }
        return Response.status(Response.Status.CREATED).entity(profesorTos).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Guardar Profesor", description = "Guarda un nuevo profesor en el sistema")
    public Response guardar(@RequestBody ProfesorTo profesorTo) {
        Profesor profesor = ProfesorMapper.toEntity(profesorTo);
        this.profesorService.guardar(profesor);
        return Response.status(Response.Status.CREATED).entity("Profesor guardado exitosamente").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(@RequestBody ProfesorTo profesorTo, @PathParam("id") Integer id) {
        Profesor profesor = ProfesorMapper.toEntity(profesorTo);
        profesor.setId(id);
        this.profesorService.actualizarPorId(profesor);
        return Response.status(Response.Status.OK).entity("Profesor actualizado exitosamente").build();
    }

    /*
     * @PATCH
     * 
     * @Path("/{id}")
     * 
     * @Consumes(MediaType.APPLICATION_JSON)
     * public Response actualizarParcial(@RequestBody Profesor
     * profesor, @PathParam("id") Integer id) {
     * profesor.setId(id);
     * Profesor p = this.profesorService.buscarPorId(id);
     * if (profesor.getNombre() != null) {
     * p.setNombre(profesor.getNombre());
     * }
     * if (profesor.getApellido() != null) {
     * p.setApellido(profesor.getApellido());
     * }
     * if (profesor.getEmail() != null) {
     * p.setEmail(profesor.getEmail());
     * }
     * this.profesorService.actualizarPorId(p);
     * return Response.status(Response.Status.OK).
     * entity("Profesor actualizado parcialmente").build();
     * }
     */

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarParcialPorId(@RequestBody ProfesorTo profesorTo, @PathParam("id") Integer id) {
    Profesor p = this.profesorService.buscarPorId(id);
        if (profesorTo.getApellido() != null) {
            p.setApellido(profesorTo.getApellido());
        }
        if (profesorTo.getNombre() != null) {
            p.setNombre(profesorTo.getNombre());
        }
        if (profesorTo.getMateria() != null) {
            p.setEmail(profesorTo.getEmail());
        }
        if (profesorTo.getEmail() != null) {
            p.setEmail(profesorTo.getEmail());
        }
        this.profesorService.actualizarParcialPorId(p);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        this.profesorService.eliminarPorId(id);
        return Response.status(Response.Status.NO_CONTENT).entity("Profesor eliminado exitosamente").build();
    }

    // http://.../profesores/1/hijos GET
    @GET
    @Path("/{id}/hijos")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        return this.iHijoService.buscarPorProfesorId(id);
    }

}
