package pe.upeu.edu.biblibackend.service.generic;


import java.util.List;

public interface CrudService<T, ID> {
    List<T> listar();
    T buscarPorId(ID id);
    T guardar(T entidad);
    T actualizar(ID id, T entidad);
    void eliminar(ID id);
}
