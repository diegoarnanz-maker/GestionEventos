package ALD_Actividad_4.modelo.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ALD_Actividad_4.modelo.entidades.Reserva;
import ALD_Actividad_4.modelo.repository.IReservaRepository;

@Repository
public class ReservaDaoImplJpaMy8 implements IReservaDao {

    @Autowired
    IReservaRepository reservaRepository;

    @Override
    public List<Reserva> obtenerTodos() {
        try{
            return reservaRepository.findAll();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Reserva buscarPorId(Integer id) {
        try{
            return reservaRepository.findById(id).get();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void guardar(Reserva entidad) {
        try{
            reservaRepository.save(entidad);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Reserva entidad) {
        try{
            reservaRepository.save(entidad);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Reserva entidad) {
        try{
            reservaRepository.delete(entidad);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPorId(Integer id) {
        try{
            reservaRepository.deleteById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Reserva> listarReservasActivas(String username, Date fechaActual) {
        try{
            return reservaRepository.findByUsuario_UsernameAndEvento_FechaInicioAfter(username, fechaActual);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int contarReservasPorEvento(int idEvento) {
        try{
            return reservaRepository.countByEvento_IdEvento(idEvento);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int contarCantidadReservadaPorEvento(int idEvento) {
        try{
            return reservaRepository.contarCantidadReservadaPorEvento(idEvento);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean existeReservaPorUsuarioYEvento(String username, int idEvento) {
        try{
            return reservaRepository.existsByUsuario_UsernameAndEvento_IdEvento(username, idEvento);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Reserva> buscarPorUsuario(String username) {
        try{
            return reservaRepository.findByUsuario_Username(username);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
