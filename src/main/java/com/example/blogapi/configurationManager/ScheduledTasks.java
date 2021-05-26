package com.example.blogapi.configurationManager;

import com.example.blogapi.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {
    @Autowired
    PersonService personService;
    //private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    //private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        @Scheduled(fixedRate = 3000)
        public void scheduleTaskWithFixedRate() {
            personService.deactivatedPersonScheduler();
        }

}
