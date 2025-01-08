package ALD_Actividad_4.modelo.dao;

import ALD_Actividad_4.modelo.entidades.Usuario;

public interface IUsuarioDao extends IGenericoCrud<Usuario, String > {

    int obtenerIdPerfil(String username);
    void asignarRol(String username, int idPerfil);
    Usuario buscarPorUsername(String username);

}
