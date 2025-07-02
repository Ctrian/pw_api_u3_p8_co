package uce.edu.web.api.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.Operation;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.service.IEstudianteService;

@Path("/estudiantes")
public class EstudianteController {

    @Inject
    private IEstudianteService estudianteService;

    @GET
    @Path("/{id}")
    public Estudiante consultarEstudiantePorId(@PathParam("id") Integer id) {
        return this.estudianteService.buscarPorId(id);
    }


    //?genero=F&provincia=pichincha
    @GET
    @Path("")
    @Operation(
        summary = "Consultar Estudiantes",
        description = "Este endpoint obtiene una lista de todos los estudiantes"
    )
    public List<Estudiante> consultarEstudiantes(@QueryParam("genero")  String genero, @QueryParam("provincia") String provincia) {
        System.out.println(provincia);
        return this.estudianteService.buscarTodos(genero);
    }

    @POST
    @Path("")
    @Operation(
        summary = "Guardar Estudiante",
        description = "Guarda un nuevo estudiante en el sistema"
    )
    public void guardar(Estudiante estudiante) {
       this.estudianteService.guardar(estudiante);
    }
 
    @PUT
    @Path("/{id}")
    public void actualizar(@RequestBody Estudiante estudiante, @PathParam("id") Integer id) {
        estudiante.setId(id);
       this.estudianteService.actualizarPorId(estudiante);
    }
 
    @PATCH
    @Path("/{id}")
    public void actualizarParcial(@RequestBody Estudiante estudiante, @PathParam("id") Integer id) {
        estudiante.setId(id);
        Estudiante e = this.estudianteService.buscarPorId(id);
        if(estudiante.getNombre() != null) {
            e.setNombre(estudiante.getNombre());
        }
        if(estudiante.getApellido() != null) {
            e.setApellido(estudiante.getApellido());
        }
        if(estudiante.getFechaNacimiento() != null) {
            e.setFechaNacimiento(estudiante.getFechaNacimiento());
        }
        this.estudianteService.actualizarParcialPorId(e);
    }
 
    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") Integer id) {
        this.estudianteService.eliminarPorId(id);
    }
}