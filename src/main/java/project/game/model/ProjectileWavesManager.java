// gere la vague des projectiles (le nombre augmente en fonction du niveau)

package project.game.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

import javafx.util.Pair;

public class ProjectileWavesManager {
	WorldModel model;

	ArrayList<Consumer<Void>> queuedSpawners = new ArrayList<>();


	ProjectileWavesManager(WorldModel model) {
		this.model = model;
	}


	void dispatchEvents() {
		testlevel();
		/*
		if (model.levelProgressionManager.getCurrentLevel() == 0) {
			manageLevelOne();
		} else if (model.levelProgressionManager.getCurrentLevel() == 1) {
			manageLevelTwo();
		} else if(model.levelProgressionManager.getCurrentLevel()==2){
			manageLevelThree();
		} else if(model.levelProgressionManager.getCurrentLevel()==3){
			manageLevelFour();
		} else if(model.levelProgressionManager.getCurrentLevel()==4){
			manageLevelFive();
		}
		*/

		if (model.getCurrentTick() >= 4 * 60 * 60)
			return;
		
		
		
	}

	void testlevel(){
		manageLevelOne();
	}
	//TILES_WIDTH = 29 
	// TILES_HEIGHT = 21
	boolean startlevel1=false;
	private void manageLevelOne() {
		double speed = 0.02;
		//boucle qui tire les spawners charge dans queuedSpawners
		if (model.getCurrentTick() % 10 == 0 && queuedSpawners.size() > 0) {
			shootqueuedSpawners(0);
		}
		if(!startlevel1){
		addStarSpawner(new IntPosition(5,5),speed);
		addStarSpawner(new IntPosition(GridMap.TILES_WIDTH-5,5),speed);
		addStarSpawner(new IntPosition(5,GridMap.TILES_HEIGHT-5),speed);
		addStarSpawner(new IntPosition(GridMap.TILES_WIDTH-5,GridMap.TILES_HEIGHT-5),speed);
		startlevel1=true;
		}
		//en une seconde on a 60 tick de jeu donc on multiplie par 60 pour recuperer une unité de temps en s
		if(model.getCurrentTick()%(45*60)==(5*60)){
			shootWallLeft(5,speed*2);
		}
		if(model.getCurrentTick()%(45*60)==(8*60)){
			shootWallLeft(8,speed*2);
		}
		if(model.getCurrentTick()%(45*60)==(11*60)){
			shootWallLeft(11,speed*2);
		}
		if(model.getCurrentTick()%(45*60)==(14*60)){
			shootWallLeft(14,speed*2);
		}
		if(model.getCurrentTick()%(45*60)==(20*60)){
			launchTargetedEncirclement(5,0.5);
		}
		if(model.getCurrentTick()%(45*60)==(31*60)){
			shootWallTop(5,speed*2);
		}
		if(model.getCurrentTick()%(45*60)==(34*60)){
			shootWallTop(8,speed*2);
		}
		if(model.getCurrentTick()%(45*60)==(37*60)){
			shootWallTop(11,speed*2);
		}
		if(model.getCurrentTick()%(45*60)==(40*60)){
			shootWallTop(14,speed*2);
		}
		if(model.getCurrentTick()%(45*60) == 0){startlevel1=false;}
		
	}

	private void manageLevelTwo() {
		if(model.getCurrentTick()%60==0){
			launchRandomTarget(0.05);
		}
		if(model.getCurrentTick()%(45*60)==(5*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.025, 2);
		}

		if(model.getCurrentTick()%(45*60)==(10*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.035, 3);
		}

		if(model.getCurrentTick()%(45*60)==(15*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.045, 4);
		}
		if(model.getCurrentTick()%(45*60)==(20*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.05, 5);
		}
		if(model.getCurrentTick()%(45*60)==(25*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.045, 4);
		}
		if(model.getCurrentTick()%(45*60)==(35*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.035, 3);
		}
		if(model.getCurrentTick()%(45*60)==(40*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.025, 2);
		}
	}
	
	private void manageLevelThree(){
		
	}
	
	private void manageLevelFour(){
	
	}

	private void manageLevelFive(){

	}

	static IntPosition getRandomPosition() {
		return new IntPosition((int) (Math.random() *
				GridMap.TILES_WIDTH),
				(int) (Math.random() * GridMap.TILES_HEIGHT));
	}

	void addStarSpawner(IntPosition pos,double speed) {
		model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnStarPattern(model,speed)));
	}

	void addTargetSpawner(IntPosition pos,double speed) {
		model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnTargetPattern(model,speed)));
	}

	void addUnidirectionSpawner(IntPosition pos, Direction dir,double speed) {
		model.addEntity(
				new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnUnidirectionalPattern(model, dir,speed)));
	}

	void launchRandomStar(double speed) {
		addStarSpawner(getRandomPosition(),speed);
	}

	void launchRandomTarget(double speed) {
		addTargetSpawner(getRandomPosition(),speed);
	}

	//tire x nombre de spawners qui sont dans l'array queuedspawners
	//si on met un x=0 on vide toute la queue 
	//si on met un x negatif alors on fait rien
	void shootqueuedSpawners(int x)	{
		if(x==0){
			while(queuedSpawners.size()>0){
				queuedSpawners.get(0).accept(null);
				queuedSpawners.remove(0);
			}
		}
		else if(x>0){ 
			for(int i =0;i<x;i++){
				if(queuedSpawners.size()>0){
				queuedSpawners.get(0).accept(null);
				queuedSpawners.remove(0);
			}
		}
		}else{System.out.println("on a mis un x negatif dans shootqueuedSpawners");} 
	}

	void launchUnidirectionEncirclement(int spacing,double speed) {
		LinkedHashSet<Pair<Direction, IntPosition>> set = new LinkedHashSet<>();
		for (int i = 0; i < GridMap.TILES_WIDTH; i += spacing)
			set.add(new Pair<Direction, IntPosition>(Direction.DOWN, new IntPosition(i, 0)));

		for (int i = 0; i < GridMap.TILES_HEIGHT; i += spacing)
			set.add(new Pair<Direction, IntPosition>(Direction.LEFT, new IntPosition(GridMap.TILES_WIDTH - 1, i)));

		for (int i = GridMap.TILES_WIDTH - 1; i >= 0; i -= spacing)
			set.add(new Pair<Direction, IntPosition>(Direction.UP, new IntPosition(i, GridMap.TILES_HEIGHT - 1)));

		for (int i = GridMap.TILES_HEIGHT - 1; i >= 0; i -= spacing)
			set.add(new Pair<Direction, IntPosition>(Direction.RIGHT, new IntPosition(0, i)));

		for (var dirAndPos : set) {
			queuedSpawners.add((Consumer<Void>) x -> {
				addUnidirectionSpawner(dirAndPos.getValue(), dirAndPos.getKey(),speed);
			});
		}
	}

	//Spawn un carre autour du joueuer
	void launchTargetedEncirclementAroundPlayer(int spacing,double speed,int distanceToPlayers){
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		int playerX=model.player.getTilePosition().x;
		int playerY=model.player.getTilePosition().y;
		for(int i=Math.max(0,playerX-distanceToPlayers);i<=Math.min(GridMap.TILES_WIDTH,playerX+distanceToPlayers);i+=spacing){
			set.add(new IntPosition(i,(int)Math.max(0,playerY-distanceToPlayers)));
			set.add(new IntPosition(i,(int)Math.min(GridMap.TILES_HEIGHT,playerY+distanceToPlayers)));
		}
		for(int i=playerY-distanceToPlayers+1;i<playerY+distanceToPlayers;i+=spacing){
			set.add(new IntPosition(playerX-distanceToPlayers,i));
			set.add(new IntPosition(playerX+distanceToPlayers,i));
		}
		for (IntPosition pos : set) {
			addTargetSpawner(pos,speed);
		}
		
	}
	void launchTargetedEncirclement(int spacing,double speed) {
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for (int i = 0; i < GridMap.TILES_WIDTH; i += spacing)
			set.add(new IntPosition(i, 0));

		for (int i = 0; i < GridMap.TILES_HEIGHT; i += spacing)
			set.add(new IntPosition(GridMap.TILES_WIDTH - 1, i));

		for (int i = GridMap.TILES_WIDTH - 1; i >= 0; i -= spacing)
			set.add(new IntPosition(i, GridMap.TILES_HEIGHT - 1));

		for (int i = GridMap.TILES_HEIGHT - 1; i >= 0; i -= spacing)
			set.add(new IntPosition(0, i));

		for (IntPosition pos : set) {
			queuedSpawners.add((Consumer<Void>) x -> {
				addTargetSpawner(pos,speed);
			});
		}
	}

	


	//TODO should look into moving these into the pattern section to organize the code in a better manner
	//Shoots a wall of projectiles from left side with a 3 wide hole around holePosition
	void shootWallLeft(int holePosition,double speed){
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for(int i=0;i<GridMap.TILES_HEIGHT;i++){
			if(	i==holePosition - 1 ||
			i==holePosition + 1||
			i==holePosition 
			)continue;
			set.add(new IntPosition(0,i));
		}
		for(IntPosition pos : set){
			addUnidirectionSpawner(pos,Direction.RIGHT,speed);
		}
	}

	void shootWallRight(int holePosition,double speed){
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for(int i=0;i<GridMap.TILES_HEIGHT;i++){
			if(	i==holePosition - 1 ||
			i==holePosition + 1||
			i==holePosition 
			)continue;
			set.add(new IntPosition(GridMap.TILES_WIDTH-1,i));
		}
		for(IntPosition pos : set){
			addUnidirectionSpawner(pos,Direction.LEFT,speed);
		}
	}

	void shootWallTop(int holePosition,double speed){
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for(int i=0;i<GridMap.TILES_WIDTH;i++){
			if(	i==holePosition - 1 ||
			i==holePosition + 1||
			i==holePosition 
			)continue;
			set.add(new IntPosition(i,0));
		}
		for(IntPosition pos : set){
			addUnidirectionSpawner(pos,Direction.DOWN,speed);
		}
	}

	void shootWallBottom(int holePosition,double speed){
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for(int i=0;i<GridMap.TILES_WIDTH;i++){
			if(	i==holePosition - 1 ||
			i==holePosition + 1||
			i==holePosition 
			)continue;
			set.add(new IntPosition(i,GridMap.TILES_HEIGHT-1));
		}
		for(IntPosition pos : set){
			addUnidirectionSpawner(pos,Direction.UP,speed);
		}
	}
	
}
