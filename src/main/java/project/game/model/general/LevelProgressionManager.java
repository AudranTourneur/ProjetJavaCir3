package project.game.model.general;

import java.util.stream.IntStream;

import project.game.controller.AudioController;

/*Gestion de la progression actuel sur le niveau et gère le changement de niveau */
public class LevelProgressionManager {

    private static final int[] LEVEL_TO_FOOD_REQUIRED = { 10, 15, 20, 25, 30 };
    private static final int TOTAL_FOOD = IntStream.of(LEVEL_TO_FOOD_REQUIRED).sum();

    public static final int NUMBER_OF_LEVELS = LEVEL_TO_FOOD_REQUIRED.length;

    private WorldModel model;

    public int currentLevel = 0;
    private int foodRemaining = LEVEL_TO_FOOD_REQUIRED[0];

    public LevelProgressionManager(WorldModel model) {
        this.model = model;
    }

    // Progression globale du jeu entre 0 et TOTAL_FOOD inclusif
    private int progression = 0;

    public int getFoodRemaining() {
        return foodRemaining;
    }

    public void eatOneFood() {
        foodRemaining--;
        progression += 1;
    }

    // Entre 0 et TOTAL_FOOD inclusif
    public int getTotalEatenFood() {
        return progression;
    }

    // Entre 0 et 100%
    public double getProgressionPercent() {
        return (double) progression / (double) TOTAL_FOOD * 100.0;
    }

    public boolean isVictory() {
        return progression >= TOTAL_FOOD; 
    }

    public void nextLevel() {
        currentLevel++;
        if (currentLevel >= NUMBER_OF_LEVELS) {
            currentLevel = 0;
        }
    }

    public void previousLevel() {
        currentLevel--;
        if (currentLevel < 0)
            currentLevel = NUMBER_OF_LEVELS - 1;
    }

    public void reset() {
        currentLevel = 0;
    }

    public void incrementCurrentLevel() {
        if (currentLevel < NUMBER_OF_LEVELS - 1) {
            currentLevel++;
            this.foodRemaining = LEVEL_TO_FOOD_REQUIRED[currentLevel];
            FoodHandler.generateFood(model, LEVEL_TO_FOOD_REQUIRED[currentLevel]);
            AudioController.playLevelUpSound();
        }
    }

    public void firstSpawn() {
        FoodHandler.generateFood(model, LEVEL_TO_FOOD_REQUIRED[0]);
    }

    public void manage() {
        if (this.foodRemaining == 0)
            incrementCurrentLevel();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
