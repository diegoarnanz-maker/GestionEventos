package ALD_Actividad_4.modelo.dao;

import java.util.List;

public interface IGenericoCrud<E, ID> {
    List<E> obtenerTodos();
    E buscarPorId(ID id);
    void guardar(E entidad);
    void editar(E entidad);
    void eliminar(E entidad);
    void eliminarPorId(ID id);
}
