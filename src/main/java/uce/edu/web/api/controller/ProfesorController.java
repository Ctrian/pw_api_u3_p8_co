package uce.edu.web.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.repository.modelo.Hijo;
import uce.edu.web.api.service.IHijoService;
import uce.edu.web.api.service.IProfesorService;
import uce.edu.web.api.service.mapper.ProfesorMapper;
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

        List<ProfesorTo> profesorTos = this.profesorService.buscarTodos().stream().map(ProfesorMapper::toTo)
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK).entity(profesorTos).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Guardar Profesor", description = "Guarda un nuevo profesor en el sistema")
    public void guardar(@RequestBody ProfesorTo profesorTo) {
        this.profesorService.guardar(ProfesorMapper.toEntity(profesorTo));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizar(@RequestBody ProfesorTo profesorTo, @PathParam("id") Integer id) {
        profesorTo.setId(id);
        this.profesorService.actualizarPorId(ProfesorMapper.toEntity(profesorTo));
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarParcialPorId(@RequestBody ProfesorTo profesorTo, @PathParam("id") Integer id) {
        profesorTo.setId(id);

        ProfesorTo p = ProfesorMapper.toTo(this.profesorService.buscarPorId(id));
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
        this.profesorService.actualizarParcialPorId(ProfesorMapper.toEntity(profesorTo));
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") Integer id) {
        this.profesorService.eliminarPorId(id);
    }

    // http://.../profesores/1/hijos GET
    @GET
    @Path("/{id}/hijos")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        return this.iHijoService.buscarPorProfesorId(id);
    }

}