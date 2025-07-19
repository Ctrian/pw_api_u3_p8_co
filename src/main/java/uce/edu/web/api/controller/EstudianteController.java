package uce.edu.web.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.annotation.security.RolesAllowed;
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
import uce.edu.web.api.service.IEstudianteService;
import uce.edu.web.api.service.IHijoService;
import uce.edu.web.api.service.mapper.EstudianteMapper;
import uce.edu.web.api.service.to.EstudianteTo;

@Path("/estudiantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteController {

    @Inject
    JsonWebToken jwt;

    @Inject
    @Claim("sub")
    ClaimValue<String> subject;

    @Inject
    private IEstudianteService estudianteService;

    @Inject
    private IHijoService hijoService;

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response consultarEstudiantePorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {

        EstudianteTo estudianteTo = EstudianteMapper.toTo(this.estudianteService.buscarPorId(id));
        estudianteTo.buildURI(uriInfo);

        return Response.status(227).entity(estudianteTo).build();
    }

    // ?genero=F&provincia=pichincha
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar Estudiantes", description = "Este endpoint obtiene una lista de todos los estudiantes")
    public Response consultarEstudiantes(@QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia, @Context UriInfo uriInfo) {
        System.out.println("Provincia query param:" + provincia);

        List<EstudianteTo> estudianteTos = this.estudianteService.buscarTodos(genero).stream()
                .map(EstudianteMapper::toTo).collect(Collectors.toList());

        return Response.status(Response.Status.OK).entity(estudianteTos).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Guardar Estudiante", description = "Guarda un nuevo estudiante en el sistema")
    public void guardar(@RequestBody EstudianteTo estudianteTo) {
        this.estudianteService.guardar(EstudianteMapper.toEntity(estudianteTo));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizar(@RequestBody EstudianteTo estudianteTo, @PathParam("id") Integer id) {
        estudianteTo.setId(id);
        this.estudianteService.actualizarPorId(EstudianteMapper.toEntity(estudianteTo));
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarParcialPorId(@RequestBody EstudianteTo estudianteTo, @PathParam("id") Integer id) {
        estudianteTo.setId(id);

        EstudianteTo e = EstudianteMapper.toTo(this.estudianteService.buscarPorId(id));
        if (estudianteTo.getApellido() != null) {
            e.setApellido(estudianteTo.getApellido());
        }
        if (estudianteTo.getNombre() != null) {
            e.setNombre(estudianteTo.getNombre());
        }
        if (estudianteTo.getFechaNacimiento() != null) {
            e.setFechaNacimiento(estudianteTo.getFechaNacimiento());
        }
        if (estudianteTo.getGenero() != null) {
            e.setGenero(estudianteTo.getGenero());
        }
        this.estudianteService.actualizarParcialPorId(EstudianteMapper.toEntity(e));
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") Integer id) {
        this.estudianteService.eliminarPorId(id);
    }

    // http://.../estudiantes/1/hijos GET
    @GET
    @Path("/{id}/hijos")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        return this.hijoService.buscarPorEstudianteId(id);
    }
}