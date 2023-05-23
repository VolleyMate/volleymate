package org.springframework.samples.volleymate.logro;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LogroController {

  private String CREATE_ACHIEVEMENT_FORM = "logro/crearLogro";
  private String ACHIEVEMENTS_LISTING = "logro/listado";

  private final LogroService achievementService;
  private final JugadorService playerService;


  @Autowired
  public LogroController(LogroService achievementService,JugadorService playerService) {

    this.achievementService = achievementService;
    this.playerService = playerService;
  }

  @GetMapping("/logro")
  public String getAllAchievements(Map<String, Object> model,Principal principal) {

    Jugador player = playerService.findJugadorByUsername(principal.getName());
    Jugador jugadorAutenticado = playerService.findJugadorByUsername(principal.getName());
    Boolean isAdmin = playerService.esAdmin(player);

    model.put("jugadorAutenticado", jugadorAutenticado);
    model.put("esAdmin", isAdmin);
    achievementService.updateLogros(player, model);

    return ACHIEVEMENTS_LISTING;
  }

  @GetMapping("/logro/player/{id}")
  public String seeOtherPlayerAchievements(@PathVariable("id") int playerId, Map<String, Object> model) {

    Jugador player = playerService.findJugadorById(playerId);

    achievementService.updateLogros(player, model);

    return ACHIEVEMENTS_LISTING;
  }

  @GetMapping(value = "/logro/new")
  public String initCreationForm(Principal principal, ModelMap model) {
    Jugador jugador = playerService.findJugadorByUsername(principal.getName());
    if (achievementService.esAdmin(jugador)) {
      Logro logro = new Logro();
      model.put("logro", logro);
      return CREATE_ACHIEVEMENT_FORM;
    } else {
      model.put("message", "No tienes permisos para realizar esta acción");
      return "redirect:/logro/";
    }

  }

  @PostMapping(value = "/logro/new")
  public String processCreationForm(@Valid Logro logro, BindingResult result, ModelMap model, Principal principal) {
    List<String> errores = achievementService.validarLogro(logro);
    
    if (!errores.isEmpty()) {
      model.put("logro", logro);
      model.put("errors", errores);
      return CREATE_ACHIEVEMENT_FORM;
    } else {
      this.achievementService.saveAchievement(logro);
      return "redirect:/logro";
    }
  }

  @GetMapping("/logro/edit/{id}")
  public String initUpdateForm(@PathVariable("id") int id, Map<String, Object> model, Principal principal) {
    Jugador jugador = playerService.findJugadorByUsername(principal.getName());
    if (achievementService.esAdmin(jugador)) {
      Logro achievement = this.achievementService.getAchievementById(id);
    model.put("logro", achievement);

    return CREATE_ACHIEVEMENT_FORM;
    } else {
      model.put("message", "No tienes permisos para realizar esta acción");
      return "redirect:/logro/";
    }
  }

  @PostMapping("/logro/edit/{id}")
  public String processUpdateForm(@Valid Logro achievement, BindingResult result,@PathVariable("id") int id, Map<String, Object> model,RedirectAttributes redirAttrs) {
    
    List<String> errores = achievementService.validarLogro(achievement);
    
    if (!errores.isEmpty()) {
      model.put("errors", errores);
      model.put("logro", achievement);
      return CREATE_ACHIEVEMENT_FORM;
    } else {
      Logro achievement2Update = this.achievementService.getAchievementById(id);
      BeanUtils.copyProperties(achievement, achievement2Update, "id");
      achievementService.saveAchievement(achievement2Update);
      redirAttrs.addFlashAttribute("mensajeExitoso", "Logro editado con éxito");
    }
    return "redirect:/logro/";
  }

  @GetMapping("/logro/delete/{achievementId}")
  public String deleteAchievement(@PathVariable("achievementId") int id, Principal principal,RedirectAttributes redirAttrs) {
    Jugador jugador = playerService.findJugadorByUsername(principal.getName());
    if (achievementService.esAdmin(jugador)) {
      Logro a = achievementService.getAchievementById(id);
      List<Jugador> jugadoresConLogro = a.getJugadores();
  
      for (Jugador j : jugadoresConLogro) {
  
        List<Logro> logrosJugador = j.getLogros();
        logrosJugador.remove(a);
  
        j.setLogros(logrosJugador);
  
        playerService.saveJugador(j);
      }
  
      achievementService.deleteAchievementById(id);
      redirAttrs.addFlashAttribute("mensajeExitoso", "Logro eliminado con éxito");
      return "redirect:/logro/";
    } else {
      redirAttrs.addFlashAttribute("mensajeError", "No tienes permisos para realizar esta acción");
      return "redirect:/logro/";
    }
    
  }

}
