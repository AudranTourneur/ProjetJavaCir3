package project.game.model;

//Les variables position ne sont plus utilisées depuis qu'on utilise des données discrètes pur le déplacement
public class Ghost extends Entity{
    
    public Direction currentDirection;
    public Direction desiredDirection;

    int gridPositionX;
    int gridPositionY;

    int compteur=0;

    //Permet de regarder si notre ghost est en mouvement ou pas => regarde si il est coincé quoi
    IntPosition currentPosition;
    IntPosition oldPosition;

    WorldModel world;
    GridMap map;

    public Ghost(WorldModel world){
        this.world=world;
        this.map=world.map;
        getNewDirection();
    }

    public void setSpawn(int x, int y){
        position.x = x;
        position.y = y;

        gridPositionX = GridMap.STEP + x * 2 * GridMap.STEP;
        gridPositionY = GridMap.STEP + y * 2 * GridMap.STEP;

        currentPosition = new IntPosition(gridPositionX, gridPositionY);
        oldPosition = null;
    }

    public int getGridPositionX(){
        return gridPositionX;
    }

    public int getGridPositionY(){
        return gridPositionY;
    }

    FloatPosition getNormalizedPosition() {
        return new FloatPosition(
                (float) this.gridPositionX / (2 * GridMap.STEP),
                (float) this.gridPositionY / (2 * GridMap.STEP));
    }

@Override
public void move(double delta) {
    //TODO En fonction de l'etat actuel de la game qu'on va lire dans le model on va prendre des trajectoires differentes
    allRandomMove(delta);
    
}
    private void allRandomMove(double delta){
        int speed = 1;
    if (isStuck()){
        getNewDirection();
    }else if(compteur % 100 == 0)getNewDirection();

    int dtx = gridPositionX + desiredDirection.getX() * speed;
    int dty = gridPositionY + desiredDirection.getY() * speed;
    if (map.isAbstractPositionAllowed(dtx, dty)) {
        this.currentDirection = desiredDirection;
    }

    if (currentDirection != null) {

        int ctx = gridPositionX + currentDirection.getX() * speed;
        int cty = gridPositionY + currentDirection.getY() * speed;
        if (map.isAbstractPositionAllowed(ctx, cty)) {
            this.gridPositionX += currentDirection.getX() * speed;
            this.gridPositionY += currentDirection.getY() * speed;
            
        }
    }
    oldPosition=currentPosition;
    currentPosition=new IntPosition(gridPositionX, gridPositionY);
    compteur++;
    this.position=getNormalizedPosition();
    }

    private void getNewDirection(){
        int rand=(int)(Math.random()*40%4);
        System.out.println(rand);
        switch(rand){
            case 0:desiredDirection=Direction.DOWN;break;
            case 1:desiredDirection=Direction.UP;break;
            case 2:desiredDirection=Direction.RIGHT;break;
            case 3:desiredDirection=Direction.LEFT;break;
        }
    }

    private boolean isStuck(){
        if(currentPosition.equals(oldPosition)){
            System.out.println("we are stuck");
        }
        return currentPosition.equals(oldPosition);
    }
    
}
