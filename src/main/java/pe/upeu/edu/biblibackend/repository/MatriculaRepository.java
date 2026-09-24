package pe.upeu.edu.biblibackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.upeu.edu.biblibackend.entity.Matricula;
import pe.upeu.edu.biblibackend.dto.MatriculadosPorCursoDTO;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findByEstudianteId(Long estudianteId);

    @Query("SELECT new pe.upeu.edu.biblibackend.dto.MatriculadosPorCursoDTO(" +
            "c.codigo, c.nombre, COUNT(m.id)) " +
            "FROM DetalleMatricula d JOIN d.curso c JOIN d.matricula m " +
            "WHERE m.estado = 'REGISTRADA' " +
            "GROUP BY c.codigo, c.nombre")
    List<MatriculadosPorCursoDTO> obtenerMatriculadosPorCurso();
}