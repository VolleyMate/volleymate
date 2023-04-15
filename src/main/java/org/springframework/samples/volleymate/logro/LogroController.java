package org.springframework.samples.volleymate.logro;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/logros")
public class LogroController {

  private String CREATE_OR_UPDATE_ACHIEVEMENT_FORM = "/achievements/AchievementCreateOrUpdateForm";
    private String ACHIEVEMENTS_LISTING = "/achievements/AchievementsListing";
    
    private final LogroService achievementService;
    private final JugadorService playerService;
    
    @Autowired
    public LogroController(LogroService achievementService,
    		JugadorService playerService) {
        
    	  this.achievementService = achievementService;
        this.playerService = playerService;
    }
    
    @GetMapping("/")
    public String getAllAchievements(Map<String, Object> model,
    		Principal principal) {
    	
        Collection<Logro> achievements = achievementService.getAllAchievements();
        Boolean isAdmin = playerService.esAdmin(playerService.findJugadorByUsername(principal.getName()));
        
        model.put("achievements", achievements);
        model.put("isAdmin", isAdmin);
        
        return ACHIEVEMENTS_LISTING;
    }
    
    @GetMapping("/delete/{achievementId}")
    public String deleteAchievement(@PathVariable("achievementId") int id){
        
    	achievementService.deleteAchievementById(id);
       
    	return "redirect:/logros";
    }
    
    @GetMapping("/edit/{id}")
    public String initUpdateForm(@PathVariable("id") int id, Map<String, Object> model) {
       
    	Logro achievement = this.achievementService.getAchievementById(id);
        model.put("achievement", achievement);
        
        return CREATE_OR_UPDATE_ACHIEVEMENT_FORM;
    }
    
    @PostMapping("/edit/{id}")
    public String processUpdateForm(@Valid Logro achievement, BindingResult result,
                            @PathVariable("id") int id, Map<String, Object> model){
        
    	if(result.hasErrors()){
        	
            model.put("achievement", achievement);
            return CREATE_OR_UPDATE_ACHIEVEMENT_FORM;
            
        } else{
        	
            Logro achievement2Update = this.achievementService.getAchievementById(id);
            BeanUtils.copyProperties(achievement, achievement2Update, "id");
            
            achievementService.saveAchievement(achievement2Update);
        }
        return "redirect:/statistics/achievements/";
    }
    
	public ModelAndView showAll(){
		
		ModelAndView res = new ModelAndView(ACHIEVEMENTS_LISTING);
		res.addObject("achievements", achievementService.getAllAchievements());
		
		return res;
	}
    
	@GetMapping(value = "/new")
	public String create(Map<String, Object> model) {
		
		Logro a = new Logro();
        model.put("achievement", a);
        
        return CREATE_OR_UPDATE_ACHIEVEMENT_FORM;
	}
	
	@PostMapping(value = "/new")
	public String post(@Valid Logro a, Map<String, Object> model,
			BindingResult result) {
		
		if(result.hasErrors()){
			
            model.put("achievement", a);
            return CREATE_OR_UPDATE_ACHIEVEMENT_FORM;
            
        } else
        	achievementService.saveAchievement(a);
        
        return "redirect:/statistics/achievements/";
	}
  
}
