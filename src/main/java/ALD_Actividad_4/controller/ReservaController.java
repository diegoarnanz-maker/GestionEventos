package ALD_Actividad_4.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ALD_Actividad_4.modelo.dao.IReservaDao;
import ALD_Actividad_4.modelo.dao.IUsuarioDao;
import ALD_Actividad_4.modelo.entidades.Evento;
import ALD_Actividad_4.modelo.entidades.Reserva;
import ALD_Actividad_4.modelo.entidades.Usuario;

@Controller
@RequestMapping("/cliente/reserva")
public class ReservaController {

    @Autowired
    private IReservaDao reservaDao;

    @Autowired
    private IUsuarioDao usuarioDao;

    @GetMapping
    public String listarReservas(Authentication authentication, Model model) {
        // Saco el nombre de usuario del usuario autenticado
        String username = authentication.getName();
        Usuario usuario = usuarioDao.buscarPorUsername(username);

        // Busco las reservas del usuario
        model.addAttribute("reservas", reservaDao.buscarPorUsuario(usuario.getUsername()));
        model.addAttribute("usuario", usuario);

        return "cliente/reserva/reservas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable int id) {
        reservaDao.eliminarPorId(id);
        return "redirect:/cliente/reserva";
    }

    @GetMapping("/editar/{id}")
    public String editarReserva(@PathVariable int id, Model model) {

        // Busco la reserva y el evento asociado al id pasado
        Reserva reserva = reservaDao.buscarPorId(id);
        Evento evento = reserva.getEvento();

        // Calculo las plazas disponibles restando las reservas realizadas al aforo
        // máximo
        int reservasRealizadas = reservaDao.contarCantidadReservadaPorEvento(evento.getIdEvento());
        int plazasDisponibles = evento.getAforoMaximo() - reservasRealizadas + reserva.getCantidad();

        model.addAttribute("reserva", reserva);
        model.addAttribute("evento", evento);
        model.addAttribute("plazasDisponibles", plazasDisponibles);

        return "cliente/reserva/reserva-form";
    }

    // Post para el form de editar reserva
    @PostMapping("/editar/{id}")
    public String editarReserva(@PathVariable int id, @RequestParam int cantidad,
            @RequestParam String observaciones, Model model, Authentication authentication) {

        Reserva reserva = reservaDao.buscarPorId(id);
        Evento evento = reserva.getEvento();

        int reservasRealizadas = reservaDao.contarCantidadReservadaPorEvento(evento.getIdEvento());
        int plazasDisponibles = evento.getAforoMaximo() - reservasRealizadas + reserva.getCantidad();

        // Validaciones de la cantidad.
        if (cantidad > plazasDisponibles) {
            model.addAttribute("error", "No hay suficientes plazas disponibles para actualizar esta reserva.");
            model.addAttribute("reserva", reserva);
            model.addAttribute("evento", evento);
            model.addAttribute("plazasDisponibles", plazasDisponibles);
            return "cliente/reserva/reserva-form";
        }

        if (cantidad < 1) {
            model.addAttribute("error", "La cantidad debe ser al menos 1.");
            model.addAttribute("reserva", reserva);
            model.addAttribute("evento", evento);
            model.addAttribute("plazasDisponibles", plazasDisponibles);
            return "cliente/reserva/reserva-form";
        }

        // Actualizo la reserva pasando la cantidad verificada, el nuevo precio y observaciones
        reserva.setCantidad(cantidad);
        reserva.setPrecioVenta(evento.getPrecio().multiply(BigDecimal.valueOf(cantidad)));
        if (observaciones != null) {
            reserva.setObservaciones(observaciones);
        }

        // Guardo la reserva updated en el dao
        reservaDao.guardar(reserva);

        model.addAttribute("success", "Reserva actualizada exitosamente.");
        return "redirect:/cliente/reserva";
    }

}
