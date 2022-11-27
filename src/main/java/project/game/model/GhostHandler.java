package project.game.model;

import java.util.ArrayList;

//Class qui nous sert a manipuler les fantomes et les faire apparaitre
//en fonction du niveau actuel
public class GhostHandler {
    WorldModel world;
    ArrayList<IntPosition> spawnPoints= new ArrayList<>();
    int nbSpawnPoints = 0;
    int nbGhosts=0;

    GhostHandler(WorldModel world){
        this.world=world;
    }

    /*Ajoute les coordonnées d'un point d'apparaition des fantômes dans spawnPoints*/
    void addSpawnPoint(int x,int y){
        spawnPoints.add(new IntPosition(x,y));
        nbSpawnPoints++;
    }

    /*Ajoute un fantôme dans notre world */
    void addGhost(){
        Ghost tmp=new Ghost(world);
        int ind=(int)(Math.random()*nbSpawnPoints*10 % nbSpawnPoints);
        tmp.setSpawn(spawnPoints.get(ind).x,spawnPoints.get(ind).y );
        world.addEntity(tmp);
        nbGhosts+=1;
    }
    
    /*Gestion de lapparaition de fantômes en fonction du niveau actuel */
    public void manage(){
        if(world.levelProgressionManager.getCurrentLevel() == 0){
            if(nbGhosts==0)addGhost();
        }else if(world.levelProgressionManager.getCurrentLevel() == 1){
            if(nbGhosts<2)addGhost();
        }else if(world.levelProgressionManager.getCurrentLevel() == 2){
            if(nbGhosts<4)addGhost();
        }else if(world.levelProgressionManager.getCurrentLevel() == 3){
            if(nbGhosts<7)addGhost();
        }else if(world.levelProgressionManager.getCurrentLevel() == 4){
            if(nbGhosts<10)addGhost();
        }
    }
}
