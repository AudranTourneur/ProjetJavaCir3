package project.game.model.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import project.game.model.utils.IntPosition;


//Gère la création de la nourriture en fonction du niveau, ainsi que la consomation de la nourriture par le 
//joueuer
public class FoodHandler {

    //Génere a des positions random valid une quantité "number" de food
    static void generateFood(WorldModel world, int number) {
        List<IntPosition> positions = new ArrayList<>(world.map.getEmptyPositions());
        Collections.shuffle(positions);

        for (int i = 0; i < Math.min(number, positions.size()); i++) {
            world.foods.add(positions.get(i));
        }

    }

    //Regarde si le joueuer à collisionner avec de la nourriture et met à jour le nécesaire
    static void manageFoodEating(WorldModel world) {
        IntPosition pos = world.player.getCenteredTilePosition();
        if (world.foods.contains(pos)) {
            world.foods.remove(pos);
            world.player.score += 100;
            world.levelProgressionManager.eatOneFood();
        }

    }
}
