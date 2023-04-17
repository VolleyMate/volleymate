package org.springframework.samples.volleymate.logro;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.valoracion.Valoracion;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogroController {

  private String CREATE_OR_UPDATE_ACHIEVEMENT_FORM = "/logro/crearOActualizar";
    private String ACHIEVEMENTS_LISTING = "/logro/listado";
    
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
        model.put("conseguido", updateLogros(principal));
        
        return ACHIEVEMENTS_LISTING;
    }

    
    private List<Logro> updateLogros(Principal principal){

       Jugador player = playerService.findJugadorByUsername(principal.getName());
      Collection<Logro> achievements = achievementService.getAllAchievements();

      for(Logro a:achievements){
        if(!a.getJugadores().contains(player)){ checkLogro(a, player); }
      }

      return player.getLogros();
    }

    private void checkLogro(Logro l, Jugador j){

      List<Logro> lj = j.getLogros();
      int v = 0;

      if(l.getMetrica().equals("partidos")){ v = j.getPartidos().size(); }
      else if (l.getMetrica().equals("valoracion")){

        int n = j.getValoracionesRecibidas().size();
        int vj = 0;

        if(0 < n){
          for(Valoracion val:j.getValoracionesRecibidas()){ vj += val.getPuntuacion(); }
          v = vj/n;
        }

      } else { v = 100000000; }

      if(l.getThreshold() <= v){ lj.add(l); }

      j.setLogros(lj);
      playerService.saveJugador(j);
    }
    
    @GetMapping("/logro/delete/{achievementId}")
    public String deleteAchievement(@PathVariable("achievementId") int id, Principal principal){
      
      Logro a = achievementService.getAchievementById(id);
      Jugador j = playerService.findJugadorByUsername(principal.getName());

      List<Logro> logrosJugador = j.getLogros();
      logrosJugador.remove(a);

      j.setLogros(logrosJugador);

      playerService.saveJugador(j);
    	achievementService.deleteAchievementById(id);
       
    	return "redirect:/logro/";
    }
    
    @GetMapping("/logro/edit/{id}")
    public String initUpdateForm(@PathVariable("id") int id, Map<String, Object> model) {
       
    	Logro achievement = this.achievementService.getAchievementById(id);
        model.put("logro", achievement);
        
        return CREATE_OR_UPDATE_ACHIEVEMENT_FORM;
    }
    
    @PostMapping("/logro/edit/{id}")
    public String processUpdateForm(@Valid Logro achievement, BindingResult result,
                            @PathVariable("id") int id, Map<String, Object> model){
        
    	if(result.hasErrors()){
        	
            model.put("logro", achievement);
            return CREATE_OR_UPDATE_ACHIEVEMENT_FORM;
            
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
    
	@GetMapping("/logro/new")
	public String create(Map<String, Object> model) {
		
		Logro a = new Logro();
        model.put("logro", a);
        
        return CREATE_OR_UPDATE_ACHIEVEMENT_FORM;
	}
	
	@PostMapping("/logro/new")
	public String post(@Valid Logro a, Map<String, Object> model,
			BindingResult result) {
		
		if(result.hasErrors()){
			
            model.put("logro", a);
            return CREATE_OR_UPDATE_ACHIEVEMENT_FORM;
            
        } else
        	achievementService.saveAchievement(a);
        
        return "redirect:/logro/";
	}
  
}
