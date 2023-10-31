package com.dami;


import com.dami.objects.Apples;
import com.dami.objects.AutoSaveLoad;
import com.dami.objects.SavableManager;


public class main {

    public static void main(String[] args) {

        AutoSaveLoad autoSaveLoad = new AutoSaveLoad();

        SavableManager savableManager = new SavableManager(Apples.class) {
            @Override
            public void onInitialize() {
                System.out.println("Initialized");
            }
        };

        savableManager.getInstances().forEach(System.out::println);

        /*
        Pizza pizza = new Pizza();
        Pizza pizza2 = new Pizza();
        Pizza pizza3 = new Pizza();
        Pizza pizza4 = new Pizza();
        Pizza pizza5 = new Pizza();
        Pizza pizza6 = new Pizza();

        CompletableFuture<Void> task = pizza.saveToFileAsync();
        CompletableFuture<Void> task1 = pizza2.saveToFileAsync();
        CompletableFuture<Void> task2 = pizza3.saveToFileAsync();
        CompletableFuture<Void> task3 = pizza4.saveToFileAsync();
        CompletableFuture<Void> task4 = pizza5.saveToFileAsync();
        CompletableFuture<Void> task5 = pizza6.saveToFileAsync();

        // Wait for both futures to complete
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(task, task1, task2, task3, task4, task5);

        // Use join to block until both futures are complete
        //combinedFuture.join();

        System.out.println("Saved pizza to file");

                CompletableFuture<List<Pizza>> task = Savable.loadFromFolderAsync(Pizza.class, Savable.getPath() + "/" + Pizza.class.getSimpleName());

        List<Pizza> pizzas = task.join();

        Savable.getInstances();

         */

        //Savable.loadFromFolderAsync(Apples.class, Savable.getPath() + "/" + Apples.class.getSimpleName()).join();




    }
}
