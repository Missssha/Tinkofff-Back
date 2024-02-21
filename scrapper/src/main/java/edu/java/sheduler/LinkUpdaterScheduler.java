package edu.java.sheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.logging.Logger;

@EnableScheduling
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update(){
        Logger.getLogger("Log...");
    }
}
