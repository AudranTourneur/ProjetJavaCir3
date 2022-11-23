package project.game.model;

public class LevelProgressionManager {

    WorldModel model;

    LevelProgressionManager(WorldModel model) {
        this.model = model;
    }

    public static final int NUMBER_OF_LEVELS = 5;
    public static final int NUMBER_OF_WORLDS = NUMBER_OF_LEVELS;

    public int currentLevel = 0;

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void nextLevel() {
        currentLevel++;
        if (currentLevel >= NUMBER_OF_LEVELS) {
            currentLevel = 0;
        }
    }

    public void previousLevel() {
        currentLevel--;
        if (currentLevel < 0) {
            currentLevel = NUMBER_OF_LEVELS - 1;
        }
    }

    public void reset() {
        currentLevel = 0;
    }

    public void manage() {
        FoodHandler.manageFoodGeneration(model);

        if (currentLevel == 0) {
            manageLevelZero();
        } else if (currentLevel == 1) {
            manageLevelOne();
        }
    }

    void manageLevelZero() {
        FoodHandler.generateFood(model, 10);
    }

    void manageLevelOne() {
        FoodHandler.generateFood(model, 20);
    }
}
