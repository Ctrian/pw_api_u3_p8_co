package uce.edu.web.api.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uce.edu.web.api.repository.IProfesorRepo;
import uce.edu.web.api.repository.modelo.Profesor;

@ApplicationScoped
public class ProfesorServiceImpl implements IProfesorService{

    @Inject
    private IProfesorRepo profesorRepo;

    @Override
    public Profesor buscarPorId(Integer id) {
        return this.profesorRepo.seleccionarPorId(id);
    }

    @Override
    public List<Profesor> buscarTodos() {
        return this.profesorRepo.seleccionarTodos();
    }
    
}
