package project.game.model;

// Création d'un level avecplus de difficultés ; plus de projectiles/ plus de nourriture...)
public class LevelProgressionManager {

    private static final int[] LEVEL_TO_FOOD_REQUIRED = { 10, 20, 30, 40, 50 };
    private static final int TOTAL_FOOD = 10 + 20 + 30 + 40 + 50;

    public static final int NUMBER_OF_LEVELS = LEVEL_TO_FOOD_REQUIRED.length;

    private WorldModel model;

    public int currentLevel = 0;
    private int foodRemaining = LEVEL_TO_FOOD_REQUIRED[0];

    public LevelProgressionManager(WorldModel model) {
        this.model = model;
    }

    // Toute la progression du jeu c'est un nombre entre 0 et 100
    public double progression = 0;

    public int getFoodRemaining() {
        return foodRemaining;
    }

    public void eatOneFood() {
        foodRemaining--;
        progression += TOTAL_FOOD / 100;
    }

    public double getProgression() {
        return progression / 100;
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
