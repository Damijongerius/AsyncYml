package com.dami.objects;

import com.dami.Pizza;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
            List<Savable> savables = Savable.loadFromFolder((Class<Savable>) clazz, Savable.getPath() + "/" + clazz.getSimpleName());
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