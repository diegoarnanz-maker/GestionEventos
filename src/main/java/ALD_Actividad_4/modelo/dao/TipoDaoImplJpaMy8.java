package ALD_Actividad_4.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ALD_Actividad_4.modelo.entidades.Tipo;
import ALD_Actividad_4.modelo.repository.ITipoRepository;

@Repository
public class TipoDaoImplJpaMy8 implements ITipoDao {

    @Autowired
    ITipoRepository tipoRepository;

    @Override
    public List<Tipo> obtenerTodos() {
        try{
            return tipoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Tipo buscarPorId(Integer id) {
        try{
            return tipoRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void guardar(Tipo entidad) {
        try{
            tipoRepository.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Tipo entidad) {
        try{
            tipoRepository.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Tipo entidad) {
        try{
            tipoRepository.delete(entidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPorId(Integer id) {
        try{
            tipoRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
