package com.alkemy.ong.config;

import com.alkemy.ong.entity.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ActivityRepository activityRepository;

    @Autowired
    public DatabaseSeeder(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        activityRepository.save(this.buildActivity("Apoyo Escolar nivel primario", "En contraturno al horario escolar, de 6 a 15años", "img_actividad1.jpg"));
        activityRepository.save(this.buildActivity("Apoyo Escolar nivel Secundario", "En contraturno al horario escolar, de 13 a 20años", "img_actividad2.jpg"));
        activityRepository.save(this.buildActivity("Tutorias", "Encuentro semanal con tutores", "img_actividad3.jpg"));
        activityRepository.save(this.buildActivity("Actividad proyecto", "Deberan pensar una actividad relacionada que quieran hacer", "img_actividad4.jpg"));
        activityRepository.save(this.buildActivity("Ayudantias", "A partir del 2do año deben elegir una actividad relaciona a la institucion para integrarse", "img_actividad5.jpg"));
        activityRepository.save(this.buildActivity("Acompañamiento escolar y familiar", "Los tutores son encargados de articular con las familias y las escuelas", "img_actividad6.jpg"));
        activityRepository.save(this.buildActivity("Beca Estimulo", "Beca Estimulo supervisada por lo tutores", "img_actividad7.jpg"));
        activityRepository.save(this.buildActivity("Taller arte y cuentos", "Se realiza semanalmente", "img_actividad8.jpg"));
        activityRepository.save(this.buildActivity("Paseos recreativos y educativos", "Pensado para promover la participacion y el sentido de pertenencia", "img_actividad9.jpg"));

    }

    private Activity buildActivity(String name, String content, String image) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setContent(content);
        activity.setImage(image);
        activity.setSoftDelete(false);
        activity.setTimestamps(new Timestamp(System.currentTimeMillis()));
        return activity;
    }
}
