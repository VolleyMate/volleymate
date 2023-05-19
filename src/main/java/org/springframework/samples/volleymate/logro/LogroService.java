package org.springframework.samples.volleymate.logro;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.valoracion.Valoracion;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class LogroService {

    @Autowired
    public LogroRepository achievementRepo;

    @Autowired
    public JugadorService playerService;

    @Autowired
    public LogroService(LogroRepository achievementRepository) {
        this.achievementRepo = achievementRepository;
    }

    public Collection<Logro> getAllAchievements() {
        return (Collection<Logro>) achievementRepo.findAll();
    }

    public Logro getAchievementById(Integer id) {
        return achievementRepo.findById(id).get();
    }

    public void deleteAchievementById(Integer id) {
        achievementRepo.deleteById(id);
    }

    public void saveAchievement(Logro achievement) {
        achievementRepo.save(achievement);
    }

    public List<Logro> updateLogros(Jugador player, Map<String, Object> model) {

        Collection<Logro> achievements = getAllAchievements();
        Map<String, Double> mem = new HashMap<>();

        model.put("logros", achievements);
        model.put("jugador", player);

        for (Logro a : achievements) {
            updateProgreso(a, player, mem);
            if (!a.getJugadores().contains(player)) {
                checkLogro(a, player, mem);
            }
        }

        model.put("conseguido", player.getLogros());
        model.put("progreso", mem);

        return player.getLogros();
    }

    public void checkLogro(Logro l, Jugador j, Map<String, Double> mem) {

        List<Logro> lj = j.getLogros();
        Double v = mem.get(l.getMetrica());

        if (l.getThreshold() <= v) {
            lj.add(l);
        }

        j.setLogros(lj);
        playerService.saveJugador(j);
    }

    public void updateProgreso(Logro l, Jugador j, Map<String, Double> mem) {
        Double v = .0;

        if (l.getMetrica().equals("partidos")) {

            v = j.getPartidos().size() * 1.;

        } else if (l.getMetrica().equals("valoracion")) {

            int n = j.getValoracionesRecibidas().size();
            Double vj = .0;

            if (0 < n) {
                for (Valoracion val : j.getValoracionesRecibidas()) {
                    vj += val.getPuntuacion();
                }
                v = vj / n;
            }

        } else {
            v = 0.;
        }

        mem.put(l.getMetrica(), v);
    }

    public ModelAndView showAll() {

        ModelAndView res = new ModelAndView("logro/listado");
        res.addObject("achievements", getAllAchievements());

        return res;
    }

}
