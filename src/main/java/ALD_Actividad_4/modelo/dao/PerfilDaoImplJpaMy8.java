package ALD_Actividad_4.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ALD_Actividad_4.modelo.entidades.Perfil;
import ALD_Actividad_4.modelo.repository.IPerfilRepository;

@Repository
public class PerfilDaoImplJpaMy8 implements IPerfilDao {

    @Autowired
    IPerfilRepository perfilRepository;

    @Override
    public List<Perfil> obtenerTodos() {
        try {
            return perfilRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Perfil buscarPorId(Integer id) {
        try {
            return perfilRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void guardar(Perfil entidad) {
        try {
            perfilRepository.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Perfil entidad) {
        try {
            perfilRepository.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Perfil entidad) {
        try {
            perfilRepository.delete(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPorId(Integer id) {
        try {
            perfilRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
