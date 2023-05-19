package org.springframework.samples.volleymate.logro;

import java.security.Principal;
import java.util.ArrayList;
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
  public LogroController(LogroService achievementService,
      JugadorService playerService) {

    this.achievementService = achievementService;
    this.playerService = playerService;
  }

  @GetMapping("/logro")
  public String getAllAchievements(Map<String, Object> model,
      Principal principal) {

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

  @GetMapping("/logro/delete/{achievementId}")
  public String deleteAchievement(@PathVariable("achievementId") int id, Principal principal,
      RedirectAttributes redirAttrs) {

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
  }

  @GetMapping("/logro/edit/{id}")
  public String initUpdateForm(@PathVariable("id") int id, Map<String, Object> model) {

    Logro achievement = this.achievementService.getAchievementById(id);
    model.put("logro", achievement);

    return CREATE_ACHIEVEMENT_FORM;
  }

  @PostMapping("/logro/edit/{id}")
  public String processUpdateForm(@Valid Logro achievement, BindingResult result,
      @PathVariable("id") int id, Map<String, Object> model) {

    List<String> errores = new ArrayList<>();
    if (achievement.getMetrica() == null) {
      errores.add("La métrica no puede ser nula");
    }
    if (achievement.getDescripcion() == null || achievement.getDescripcion() == "") {
      errores.add("La descripción no puede estar vacía");
    }
    if (achievement.getNombre() == null || achievement.getNombre() == "") {
      errores.add("El nombre no puede estar vacío");
    }
    if (achievement.getThreshold() == null || achievement.getThreshold() < 1) {
      errores.add("El número de meta no puede ser menor que 1");
    }

    if (result.hasErrors()) {

      model.put("logro", achievement);
      return CREATE_ACHIEVEMENT_FORM;

    } else if (!errores.isEmpty()) {
      model.put("errors", errores);
      model.put("logro", achievement);
      return CREATE_ACHIEVEMENT_FORM;
    } else {

      Logro achievement2Update = this.achievementService.getAchievementById(id);
      BeanUtils.copyProperties(achievement, achievement2Update, "id");

      achievementService.saveAchievement(achievement2Update);
    }
    return "redirect:/logro/";
  }

  @GetMapping(value = "/logro/new")
  public String initCreationForm(Principal principal, ModelMap model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
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
    List<String> errores = new ArrayList<>();
    if (logro.getMetrica() == null) {
      errores.add("La métrica no puede ser nula");
    }
    if (logro.getDescripcion() == null || logro.getDescripcion() == "") {
      errores.add("La descripción no puede estar vacía");
    }
    if (logro.getNombre() == null || logro.getNombre() == "") {
      errores.add("El nombre no puede estar vacío");
    }
    if (logro.getThreshold() == null || logro.getThreshold() < 1) {
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
