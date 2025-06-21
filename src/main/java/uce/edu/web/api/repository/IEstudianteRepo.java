package uce.edu.web.api.repository;

import uce.edu.web.api.repository.modelo.Estudiante;

public interface IEstudianteRepo {
    Estudiante seleccionarPorId(Integer id);
}
