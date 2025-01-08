package ALD_Actividad_4.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ALD_Actividad_4.modelo.entidades.Usuario;
import ALD_Actividad_4.modelo.repository.IUsuarioRepository;

@Repository
public class UsuarioDaoImplJpaMy8 implements IUsuarioDao {

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> obtenerTodos() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Usuario buscarPorId(String id) {
        try {
            return usuarioRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void guardar(Usuario entidad) {
        try {
            usuarioRepository.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Usuario entidad) {
        try {
            usuarioRepository.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Usuario entidad) {
        try {
            usuarioRepository.delete(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPorId(String id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void asignarRol(String username, int idPerfil) {
        try {
            usuarioRepository.asignarRol(username, idPerfil);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int obtenerIdPerfil(String username) {
        try {
            return usuarioRepository.obtenerIdPerfilPorUsuario(username);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        try {
            return usuarioRepository.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
