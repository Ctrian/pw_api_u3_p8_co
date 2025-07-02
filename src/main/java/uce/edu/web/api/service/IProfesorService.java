package uce.edu.web.api.service;

import java.util.List;

import uce.edu.web.api.repository.modelo.Profesor;

public interface IProfesorService {
    Profesor buscarPorId(Integer id);

    public List<Profesor> buscarTodos();

    public void actualizarPorId(Profesor profesor);

    public void actualizarParcialPorId(Profesor profesor);

    public void eliminarPorId(Integer id);

    public void guardar(Profesor profesor);
}
