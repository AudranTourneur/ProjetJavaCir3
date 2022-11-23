package project.game.model;

import java.util.ArrayList;

//Class qui nous sert a manipuler les fantomes et les faire apparaitre et disparaitre
public class GhostHandler {
    WorldModel world;
    ArrayList<IntPosition> spawnPoints= new ArrayList<>();
    static int nbSpawnPoints = 0;
    GhostHandler(WorldModel world){
        this.world=world;
    }

    void addSpawnPoint(int x,int y){
        spawnPoints.add(new IntPosition(x,y));
        nbSpawnPoints++;
    }
    void addGhost(){
        Ghost tmp=new Ghost(world);
        int ind=(int)(Math.random()*nbSpawnPoints*10 % nbSpawnPoints);
        tmp.setSpawn(spawnPoints.get(ind).x,spawnPoints.get(ind).y );
        world.addEntity(tmp);
    }
 
    /*
    void removeGhost(){
        world.removeEntity(null);
    }
    */

}
