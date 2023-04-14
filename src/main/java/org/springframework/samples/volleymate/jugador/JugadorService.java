package org.springframework.samples.volleymate.jugador;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.volleymate.jugador.exceptions.YaUnidoException;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.partido.PartidoRepository;
import org.springframework.samples.volleymate.user.Authorities;
import org.springframework.samples.volleymate.user.AuthoritiesService;
import org.springframework.samples.volleymate.user.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.samples.volleymate.solicitud.Solicitud;
import org.springframework.samples.volleymate.solicitud.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JugadorService {
    
    @Autowired
    private JugadorRepository jugadorRepository;
    private PartidoRepository partidoRepository;
    private UserService userService;
    private AuthoritiesService authoritiesService;
    private SolicitudRepository solicitudRepository;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository, PartidoRepository partidoRepository, UserService userService,AuthoritiesService authoritiesService,SolicitudRepository solicitudRepository) {
        this.jugadorRepository = jugadorRepository;
        this.partidoRepository = partidoRepository;
        this.userService=userService;
        this.authoritiesService=authoritiesService;
        this.solicitudRepository = solicitudRepository;
    }

    @Transactional(readOnly = true)
    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Jugador findJugadorById(int id) throws DataAccessException {
        return jugadorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Jugador findJugadorByUsername(String username) throws DataAccessException {
      return jugadorRepository.findByUsername(username);
    }


    @Transactional
	public void saveJugador(@Valid Jugador jugador) throws DataAccessException, DataIntegrityViolationException {
		jugadorRepository.save(jugador);
		userService.saveUser(jugador.getUser());
		authoritiesService.saveAuthorities(jugador.getUser().getUsername(), "jugador");
	}


    public void unirsePartida(int jugadorId, int partidoId) throws YaUnidoException{
        Jugador jugador = this.jugadorRepository.findById(jugadorId);
        Partido partido = this.partidoRepository.findById(partidoId);
        
        if(jugador.getPartidos().contains(partido)){
            throw new YaUnidoException();
        }
        else{
            //metemos dato en jugador; check para debuggear
            Set<Partido> partidos = new HashSet<>();
            if(jugador.getPartidos()!=null){
                partidos = jugador.getPartidos();
            }
            partidos.add(partido);
            jugador.setPartidos(partidos);

            //metemos dato en partido; if para debuggear
            List<Jugador> jugadores = new ArrayList<>();
            if(partido.getJugadores() != null){
                jugadores = partido.getJugadores();
            }
            jugadores.add(jugador);
            partido.setJugadores(jugadores);

            this.jugadorRepository.save(jugador);
            this.partidoRepository.save(partido);
        }
    }

    @Transactional
    public void crearSolicitudPartido(Jugador jugador, Partido partido) {
        Solicitud solicitud = new Solicitud();
        solicitud.setJugador(jugador);
        solicitud.setPartido(partido);
        this.solicitudRepository.save(solicitud);
    }

    @Transactional
    public void eliminarSolicitud(Solicitud solicitud) {
        this.solicitudRepository.delete(solicitud);
    }

    public Solicitud findSolicitudById(int solicitudId) {
        return this.solicitudRepository.findById(solicitudId).get();
    }

    public List<Partido> findPartidosByJugadorId(int jugadorId) {
        List<Partido> lista = this.partidoRepository.findAll();
        List<Partido> listaReturn = new ArrayList<>();
        for(Partido p: lista) {
            if(p.getJugadores().contains(this.jugadorRepository.findById(jugadorId))) {
                listaReturn.add(p); 
            }
        }
        return listaReturn;
    }


    public List<Jugador> listAll(String palabraClave, int valoracionMedia){
        if(palabraClave!=null && valoracionMedia != 0){
            List<Jugador> lista = jugadorRepository.findAll(palabraClave);
            return lista.stream().filter(jugador -> jugador.getValoracionMedia()>=valoracionMedia).collect(Collectors.toList());
        } else if (valoracionMedia != 0 && palabraClave == null ){
            List<Jugador> lista = jugadorRepository.findAll();
            return lista.stream().filter(jugador -> jugador.getValoracionMedia()>=valoracionMedia).collect(Collectors.toList());
        } else if (valoracionMedia == 0 && palabraClave != null ){
            return jugadorRepository.findAll(palabraClave);
        }
        return jugadorRepository.findAll();
    }

    public List<Jugador> listAll(){
        
        return jugadorRepository.findAll();
    }

    public List<String> findErroresCrearJugador(Jugador jugador){
        List<String> errores = new ArrayList<>();
        Integer digitos = (int)(Math.log10(jugador.getTelephone())+1);
        if(!digitos.equals(9)) {
            errores.add("El teléfono debe tener 9 cifras");
        }
        if(!jugador.getUser().getCorreo().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errores.add("El correo no es válido");
        }
        if(jugador.getFirstName().length() < 3) {
            errores.add("El nombre debe tener más de 3 caracteres");
        }
        if(jugador.getLastName().length() < 3) {
            errores.add("El apellido debe tener más de 3 caracteres");
        }
        if(jugadorRepository.findByUsername(jugador.getUser().getUsername()) != null){
            errores.add("El nombre de usuario ya existe");
        }
        if(jugadorRepository.findByCorreo(jugador.getUser().getCorreo()) != null){
            errores.add("El correo ya existe");
        }
        return errores;
    }

    public boolean esAdmin(Jugador jugador){
        Boolean esAdmin = false;
        Set<Authorities> authorities = jugador.getUser().getAuthorities();
		for(Authorities a: authorities) {
			if(a.getAuthority().equals("admin")) {
				esAdmin = true;
			}
		}
        return esAdmin;
    }

    @Scheduled(cron = "0 0 12 * * MON")
    public void volleysSemanales() throws InterruptedException{
        List<Jugador> jugadores = jugadorRepository.findAll();
        for(Jugador j: jugadores){
            Integer volleys = j.getVolleys();
            volleys += 150;
            j.setVolleys(volleys);
            this.jugadorRepository.save(j);
        }
    }

    public Map<String,Object> getValoresCompra(Double precio, Integer numVolleys, String paquete, Integer idCompra, Map<String,Object> model){
        model.put("precio", precio);
        model.put("numVolleys", numVolleys);
        model.put("paquete", paquete);
        model.put("idCompra", idCompra);
        return model;
    }

    @Transactional
    public void deleteJugador(Jugador j) {
        this.jugadorRepository.delete(j);
    }

}
