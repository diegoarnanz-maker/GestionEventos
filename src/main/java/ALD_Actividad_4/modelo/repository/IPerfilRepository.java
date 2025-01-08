package ALD_Actividad_4.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ALD_Actividad_4.modelo.entidades.Perfil;

public interface IPerfilRepository extends JpaRepository<Perfil, Integer> {
    
}
