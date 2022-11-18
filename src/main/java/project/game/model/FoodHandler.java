package project.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodHandler {
    static void generateFood(WorldModel world, int number) {
        // world.foods;

        List<IntPosition> positions = new ArrayList<>(world.map.getEmptyPositions());
        Collections.shuffle(positions);

        for (int i = 0; i < Math.min(number, positions.size()); i++) {
            world.foods.add(positions.get(i));
            //System.out.println("Generated positions: " + position[i]);
        }
    
    }

    static void eatFood(WorldModel world,boolean log){
        int step=world.map.STEP;
        IntPosition pos=new IntPosition(world.player.gridPositionX/2*step,world.player.gridPositionY/2*step);
        if(log)System.out.println(pos);
        if(log)System.out.println(world.foods);
        if(world.foods.contains(pos)){
            System.out.println("Found the food");
            world.foods.remove(pos);
            world.score+=100;
        }
        
    }
}
