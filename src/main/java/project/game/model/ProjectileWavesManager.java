package project.game.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

import javafx.util.Pair;

//Gestion des vagues de projectiles en fonction du niveu actuel
public class ProjectileWavesManager {
	WorldModel model;

	//Listes des spawners qui attendent à spawn
	ArrayList<Consumer<Void>> queuedSpawners = new ArrayList<>();


	ProjectileWavesManager(WorldModel model) {
		this.model = model;
	}

	//Appel la gestion du niveau actuel
	void dispatchEvents() {
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
	}

	/*Gestion du niveau 3
	 * dans notre jeu chaque pattern de projectile dure 45s et se répètent 
	 * comme dans une seconde on a 60 ticks on multiplie par 60 pour réfléchir en
	 * secondes
	*/
	boolean startlevel1=false;
	private void manageLevelThree() {
		double speed = 0.02;
		//Fais apparatitre les spawners en attente
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
			launchTargetedEncirclement(5,0.08);
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

	private void manageLevelFour() {
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
	
	private void manageLevelOne(){
		if(model.getCurrentTick() % (45*60)==(3*60)){
			launchRandomStar(0.01);
		}
		if(model.getCurrentTick() % (45*60) == (7*60)){
			launchRandomStar(0.02);
		}
		if(model.getCurrentTick() % (45*60) == (11*60)){
			launchRandomStar(0.03);
		}
		if(model.getCurrentTick() % (45*60) == (15*60)){
			launchRandomStar(0.035);
		}
		if(model.getCurrentTick() % (45*60) == (19*60)){
			launchRandomStar(0.038);
		}
		if(model.getCurrentTick() % (45*60) == (23*60)){
			launchRandomStar(0.040);
		}
		if(model.getCurrentTick() % (45*60) == (27*60)){
			launchRandomStar(0.042);
		}
		if(model.getCurrentTick() % (45*60) == (31*60)){
			launchRandomStar(0.045);
		}
		if(model.getCurrentTick() % (45*60) == (35*60)){
			launchRandomStar(0.050);
		}
		if(model.getCurrentTick() % (45*60) == (39*60)){
			launchRandomStar(0.052);
		}
		
	}
	
	private void manageLevelTwo(){
		if(model.getCurrentTick()%(2*60)==0){
			launchRandomTarget(0.1);
		}
	}

	private void manageLevelFive(){
		if(model.getCurrentTick()%(45*60)==(3*60)){
			shootWallLeft(GridMap.TILES_HEIGHT/2, 0.05);
		}
		if(model.getCurrentTick()%(45*60)==(5*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.055, 3);
		}
		if(model.getCurrentTick()%(45*60)==(8*60)){
			launchUnidirectionEncirclement(1, 0.012);
		}
		if(model.getCurrentTick()%(45*60)==(10*60)){
			for(int i =0;i<20;i++){
				launchRandomTarget(0.08);
			}
		}
		if(model.getCurrentTick()%(45*60)==(13*60)){
			shootWallRight(GridMap.TILES_HEIGHT/2, 0.05);
		}
		if(model.getCurrentTick()%(45*60)==(15*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.055, 3);
		}
		if(model.getCurrentTick()%(45*60)==(18*60)){
			launchUnidirectionEncirclement(1, 0.012);
		}
		if(model.getCurrentTick()%(45*60)==(20*60)){
			shootWallTop(-10, 0.05);
		}
		if(model.getCurrentTick()%(45*60)==(22*60)){
			shootWallTop(-10, 0.05);
			for(int i=0;i<5;i++){
				launchRandomStar(0.05);
				launchRandomTarget(0.08);
			}
		}
		if(model.getCurrentTick()%(45*60)==(24*60)){
			shootWallTop(-10, 0.05);
		}
		if(model.getCurrentTick()%(45*60)==(27*60)){
			launchTargetedEncirclementAroundPlayer(1, 0.01, 4);
		}
		if(model.getCurrentTick()%(45*60)==(30*60) || model.getCurrentTick()%(45*60)==(35*60) || model.getCurrentTick()%(45*60)==(40*60)){
			shootWallBottom(GridMap.TILES_WIDTH/2, 0.03);
			shootWallTop(GridMap.TILES_WIDTH/2, 0.03);
			shootWallRight(GridMap.TILES_HEIGHT/2, 0.03);
			shootWallLeft(GridMap.TILES_HEIGHT/2, 0.03);
		}
		if(model.getCurrentTick()%(45*60)==(41*60) || model.getCurrentTick()%(45*60)==(43*60) ||model.getCurrentTick()%(45*60)==(44*60) ){
			launchUnidirectionEncirclement(4, 0.025);
		}

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
	//Tire des spawners qui vise le joueuer sur les extrémités de la carte avec un espacement de spacing
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

	/*Tire un mur de projectiles avec un trou pour passer */
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
