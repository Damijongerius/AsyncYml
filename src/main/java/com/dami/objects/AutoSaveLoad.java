package com.dami.objects;

import org.reflections.Reflections;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

public class AutoSaveLoad {
    private ScheduledExecutorService scheduler;

    public AutoSaveLoad() {

        Set<Class<?>> classes = findAnnotatedClassesInProject();
        Set<Class<? extends Savable>> checkedClasses = new HashSet<>();
        System.out.println(classes.size());
        for (Class<?> clazz : classes) {
            if (Savable.class.isAssignableFrom(clazz)) {
                //noinspection unchecked;
                checkedClasses.add((Class<? extends Savable>) clazz);
            }
        }
        Set<Savable> instances = new HashSet<>();

        for (Class<? extends Savable> clazz : checkedClasses) {
            List<Savable> savables = Savable.loadFromFolder((Class<Savable>) clazz, Savable.getbasePath() + "/" + clazz.getSimpleName());
            instances.addAll(savables);
        }

        //Savable.Initialize(instances);

        System.out.println("Initialized AutoSaveLoad");

        // Initialize the scheduler and schedule the Save task after initialization is complete
       // scheduler = Executors.newScheduledThreadPool(1);
       // scheduler.scheduleAtFixedRate(this::Save, 0, 10, TimeUnit.MINUTES);
    }


    private void Save() {
        Set<Class<?>> classes =  findAnnotatedClassesInProject();
        Set<Class<? extends Savable>> checkedClasses = new HashSet<>();
        for(Class<?> clazz : classes){
            if (Savable.class.isAssignableFrom(clazz)) {

                //noinspection unchecked;
                checkedClasses.add((Class<? extends Savable>) clazz);
            }
        }

        for (Class<? extends Savable> clazz : checkedClasses) {
            Savable.getInstances(clazz).forEach(Savable::saveToFileAsync);
        }
    }

    private Set<Class<?>> findAnnotatedClassesInProject() {
        Reflections reflections = new Reflections("");

        return reflections.getTypesAnnotatedWith(AutoSL.class);
    }
}