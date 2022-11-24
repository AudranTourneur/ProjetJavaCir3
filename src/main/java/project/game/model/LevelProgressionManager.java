package project.game.model;

public class LevelProgressionManager {

    private WorldModel model;

    public LevelProgressionManager(WorldModel model) {
        this.model = model;
    }

    private static final int[] levelToFoodRequired = { 10, 20, 30, 40, 50 };
    private static final int NUMBER_OF_LEVELS = levelToFoodRequired.length;

    public int currentLevel = 0;
    private int foodRemaining = levelToFoodRequired[0];

    public int getFoodRemaining() {
        return foodRemaining;
    }

    public void eatOneFood() {
        foodRemaining--;
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

    // int[] levelToFoodRequire = {10, 20, 30, 40, 50};

    public void incrementCurrentLevel() {
        if (currentLevel < NUMBER_OF_LEVELS - 1) {
            currentLevel++;
            this.foodRemaining = levelToFoodRequired[currentLevel];
            FoodHandler.generateFood(model, levelToFoodRequired[currentLevel]);
        }

    }

    public void firstSpawn() {
        FoodHandler.generateFood(model, levelToFoodRequired[0]);
    }

    public void manage() {
        if (this.foodRemaining == 0) {
            incrementCurrentLevel();
        }

        if (currentLevel == 0) {
            manageLevelZero();
        } else if (currentLevel == 1) {
            manageLevelOne();
        }
        else if (currentLevel == 2) 
            manageLevelTwo();
    }

    private void manageLevelZero() {
    }

    private void manageLevelOne() {
    }

    private void manageLevelTwo() {
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
