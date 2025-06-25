package uce.edu.web.api.service;

import uce.edu.web.api.repository.modelo.Profesor;

public interface IProfesorService {
    Profesor buscarPorId(Integer id);
}
