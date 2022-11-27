package project.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// gerer les evenements de nourriture (nombre, position, monde)
public class FoodHandler {

    static void manageFoodGeneration(WorldModel world) {
        if (world.getCompletionPercent() >= 99) return;

        if (world.getCurrentTick() % 600 == 0) {
            generateFood(world, 10);
        }

        if (world.getCurrentTick() % 3600 == 0) {
            generateFood(world, 50);
        }
    }

    static void generateFood(WorldModel world, int number) {
        // world.foods;

        List<IntPosition> positions = new ArrayList<>(world.map.getEmptyPositions());
        Collections.shuffle(positions);

        for (int i = 0; i < Math.min(number, positions.size()); i++) {
            world.foods.add(positions.get(i));
        }

    }

    static void manageFoodEating(WorldModel world, boolean log) {
        log = false;
        //IntPosition pos = new IntPosition(world.player.gridPositionX / 2 * step, world.player.gridPositionY / 2 * step);
        //IntPosition pos = world.player.getTilePosition();
        IntPosition pos = world.player.getCenteredTilePosition();
        if (world.foods.contains(pos)) {
            world.foods.remove(pos);
            world.player.score += 100;
            world.levelProgressionManager.eatOneFood();
        }

    }
}
