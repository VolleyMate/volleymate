package org.springframework.samples.volleymate.logro;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.valoracion.Valoracion;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogroController {

  private String CREATE_ACHIEVEMENT_FORM = "logro/crearLogro";
  private String UPDATE_ACHIEVEMENT_FORM = "logro/actualizarLogro";
    private String ACHIEVEMENTS_LISTING = "logro/listado";

    
    private final LogroService achievementService;
    private final JugadorService playerService;
    
    @Autowired
    public LogroController(LogroService achievementService,
    		JugadorService playerService) {
        
    	  this.achievementService = achievementService;
        this.playerService = playerService;
    }
    
    @GetMapping("/logro")
    public String getAllAchievements(Map<String, Object> model,
    		Principal principal) {

        Jugador player = playerService.findJugadorByUsername(principal.getName());
        Collection<Logro> achievements = achievementService.getAllAchievements();
        Boolean isAdmin = playerService.esAdmin(player);
        
        model.put("logros", achievements);
        model.put("esAdmin", isAdmin);
        model.put("jugador", player);
        model.put("conseguido", updateLogros(player, model));
        
        return ACHIEVEMENTS_LISTING;
    }

    @GetMapping("/logro/player/{id}")
    public String seeOtherPlayerAchievements(@PathVariable("id") int playerId, Map<String, Object> model){

      Jugador player = playerService.findJugadorById(playerId);

      updateLogros(player, model);
      model.put("jugador", player);

       return ACHIEVEMENTS_LISTING;
    }

    private List<Logro> updateLogros(Jugador player, Map<String, Object> model){

      Collection<Logro> achievements = achievementService.getAllAchievements();

       Map<String,Double> mem = new HashMap<>();

      for(Logro a:achievements){
        if(!a.getJugadores().contains(player)){ checkLogro(a, player, mem); }
      }

      model.put("progreso", mem);

      return player.getLogros();
    }

    private void checkLogro(Logro l, Jugador j, Map<String,Double> mem){

      List<Logro> lj = j.getLogros();
      Double v = .0;

      if(l.getMetrica().equals("partidos")){

        v = j.getPartidos().size() * 1.;
        mem.put("partidos", v);

      } else if (l.getMetrica().equals("valoracion")){

        int n = j.getValoracionesRecibidas().size();
        Double vj = .0;

        if(0 < n){
          for(Valoracion val:j.getValoracionesRecibidas()){ vj += val.getPuntuacion(); }
          v = vj/n;
        }

        mem.put("valoracion", vj);
      } else { v = 100000000.; }

      if(l.getThreshold() <= v){ lj.add(l); }

      j.setLogros(lj);
      playerService.saveJugador(j);
    }
    
    @GetMapping("/logro/delete/{achievementId}")
    public String deleteAchievement(@PathVariable("achievementId") int id, Principal principal){
      
      Logro a = achievementService.getAchievementById(id);
      List<Jugador> jugadoresConLogro = a.getJugadores();

      for(Jugador j:jugadoresConLogro){

        List<Logro> logrosJugador = j.getLogros();
        logrosJugador.remove(a);

        j.setLogros(logrosJugador);

        playerService.saveJugador(j);
      }

    	achievementService.deleteAchievementById(id);
       
    	return "redirect:/logro/";
    }
    
    @GetMapping("/logro/edit/{id}")
    public String initUpdateForm(@PathVariable("id") int id, Map<String, Object> model) {
       
    	Logro achievement = this.achievementService.getAchievementById(id);
        model.put("logro", achievement);
        
        return UPDATE_ACHIEVEMENT_FORM;
    }
    
    @PostMapping("/logro/edit/{id}")
    public String processUpdateForm(@Valid Logro achievement, BindingResult result,
                            @PathVariable("id") int id, Map<String, Object> model){
        
    	if(result.hasErrors()){
        	
            model.put("logro", achievement);
            return UPDATE_ACHIEVEMENT_FORM;
            
        } else{
        	
            Logro achievement2Update = this.achievementService.getAchievementById(id);
            BeanUtils.copyProperties(achievement, achievement2Update, "id");
            
            achievementService.saveAchievement(achievement2Update);
        }
        return "redirect:/logro/";
    }
    
	public ModelAndView showAll(){
		
		ModelAndView res = new ModelAndView(ACHIEVEMENTS_LISTING);
		res.addObject("achievements", achievementService.getAllAchievements());
		
		return res;
	}
    
	@GetMapping(value = "/logro/new")
	public String initCreationForm(Principal principal, ModelMap model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
          Logro logro = new Logro();
		      model.put("logro", logro);
		      return CREATE_ACHIEVEMENT_FORM;
        }else{
          model.put("message", "No tienes permisos para realizar esta acción");
            return "redirect:/logro/";
        }

	}

	@PostMapping(value = "/logro/new")
	public String processCreationForm(@Valid Logro logro, BindingResult result, ModelMap model,Principal principal) {
		List<String> errores = new ArrayList<>();
		if(logro.getMetrica() == null) {
			errores.add("La métrica no puede ser nula");
		}
		if(logro.getDescripcion()==null || logro.getDescripcion()=="") {
			errores.add("La descripción no puede estar vacía");
		}
		if(logro.getNombre()==null || logro.getNombre()=="") {
			errores.add("El nombre no puede estar vacío");
		}
		if(logro.getThreshold()==null || logro.getThreshold()<1){
			errores.add("El número de meta no puede ser menor que 1");
		}
		if (!errores.isEmpty()) {
			model.put("logro", logro);
			model.put("errors", errores);
			return CREATE_ACHIEVEMENT_FORM;
		} else {
			this.achievementService.saveAchievement(logro);
			return "redirect:/logro";
		}
	}
  
}
