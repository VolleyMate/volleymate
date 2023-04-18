package org.springframework.samples.volleymate.logro;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogroService {

    private LogroRepository achievementRepo;

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

    public void deleteAchievementById(Integer id){
        achievementRepo.deleteById(id);
    }

    public void saveAchievement(Logro achievement){
        achievementRepo.save(achievement);
    }
    
}
