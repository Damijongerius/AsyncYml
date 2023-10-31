package com.dami.objects;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class Savable{

    //all existing instances of Savable
    private static Set<Savable> instances = new HashSet<>();

    //instance number per extending class
    private final int instanceNumber;

    //path to save config to
    public static String getPath(){
        return "config/";
    }

    //input field for what you want to save
    public abstract Map<String, Object> saveToYaml();

    public Savable() {
        instances.add(this);
        instanceNumber = getInstances(this.getClass()).size();
    }

    public static <T extends Savable> T loadFromYaml(Class<T> clazz, String yamlData) {
        Yaml yaml = new Yaml();
        return yaml.loadAs(yamlData, clazz);
    }

    public CompletableFuture<Void> saveToFileAsync() {
        return CompletableFuture.runAsync(() -> {
            String filePath = getPath() + this.getClass().getSimpleName() + "/" + instanceNumber + ".yml";
            File file = new File(filePath);

            // Create the directory structure if it doesn't exist
            File parentDirectory = file.getParentFile();
            if (parentDirectory != null && !parentDirectory.exists()) {
                if (!parentDirectory.mkdirs()) {
                    System.out.println("Failed to create directory: " + parentDirectory.getAbsolutePath());
                    return; // Unable to create the directory, exit the method
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(getDataString());
                System.out.println("Data saved to: " + filePath);
            } catch (IOException e) {
                System.out.println("Error while saving data to " + filePath + ": " + e);
            }
        });
    }

    public String getDataString(){
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);

        return yaml.dump(saveToYaml());
    }

    public static Set<Savable> getInstances(Class<?> clazz){
        Set<Savable> typeInstances = new HashSet<>();
        for (Savable savable : instances) {
            if (savable.getClass().equals(clazz)) {
                typeInstances.add(savable);
            }
        }

        return typeInstances;
    }

    public static Set<Savable> getInstances() {
        return instances;
    }

    public static void Initialize(Set<Savable> newInstances){
        instances = newInstances;
    }

    public static <T extends Savable> T loadFromFile(Class<T> clazz, String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            StringBuilder content = new StringBuilder();
            int data;
            while ((data = reader.read()) != -1) {
                content.append((char) data);
            }
            return loadFromYaml(clazz, content.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends Savable> List<T> loadFromFolder(Class<T> clazz, String folderName) {
        List<T> resultList = new ArrayList<>();

        File folder = new File(folderName);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".yml")) {
                    T loadedObject = loadFromFile(clazz, file.getAbsolutePath());
                    resultList.add(loadedObject);
                }
            }
        }

        return resultList;
    }
}
