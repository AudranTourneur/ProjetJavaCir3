package project.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodGenerator {
    static void generateFood(WorldModel world, int number) {
        // world.foods;

        List<IntPosition> positions = new ArrayList<>(world.map.getEmptyPositions());
        Collections.shuffle(positions);

        for (int i = 0; i < Math.min(number, positions.size()); i++) {
            world.foods.add(positions.get(i));
            //System.out.println("Generated positions: " + position[i]);
        }
    
    }
}