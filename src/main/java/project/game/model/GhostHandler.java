package project.game.model;

import java.util.ArrayList;

//Class qui nous sert a manipuler les fantomes et les faire apparaitre et disparaitre
public class GhostHandler {
    WorldModel world;
    ArrayList<IntPosition> spawnPoints= new ArrayList<>();
    int nbSpawnPoints = 0;
    int nbGhosts=0;
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
        nbGhosts+=1;
    }
    
    
    public void manage(){
        if(world.levelProgressionManager.getCurrentLevel() == 0){
            if(nbGhosts==0)addGhost();
        }else if(world.levelProgressionManager.getCurrentLevel() == 1){
            if(nbGhosts==1)addGhost();
        }else if(world.levelProgressionManager.getCurrentLevel() == 2){
            addGhost();
        }else if(world.levelProgressionManager.getCurrentLevel() == 3){
            addGhost();
        }else if(world.levelProgressionManager.getCurrentLevel() == 4){
            addGhost();
        }
    }

    /*
    void removeGhost(){
        world.removeEntity(null);
    }
    */

}
