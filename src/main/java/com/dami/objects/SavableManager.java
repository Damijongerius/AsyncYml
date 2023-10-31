package com.dami.objects;

import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

public abstract class SavableManager {

    private Set<Savable> instances;

    public SavableManager(Class<?> clazz) {
        //load class
        if(!clazz.isAnnotationPresent(AutoSL.class)){
            System.out.println("class is not annotated with AutoSL");
            return;
        }

        instances = Savable.getInstances(clazz);
        if(instances.isEmpty()){
            instances = new HashSet<>();
            System.out.println("no instances found for" + clazz.getName());
        }
    }

    public void removeInstance(Savable instance){
        instances.remove(instance);
    }

    public void addInstance(Savable instance){
        instances.add(instance);
    }

    public Set<Savable> getInstances(){
        return instances;
    }

    public abstract void onInitialize();
}
