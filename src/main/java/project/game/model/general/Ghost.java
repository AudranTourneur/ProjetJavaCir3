package project.game.model.general;

import project.game.model.utils.Direction;
import project.game.model.utils.FloatPosition;
import project.game.model.utils.IntPosition;
import project.game.model.utils.SquareValidityResponse;

/*Création des ghosts et gestion de leur position, 
les variables position ne sont plus utilisées depuis qu'on utilise des données discrètes pour le déplacement*/
public class Ghost extends Entity {

    public Direction currentDirection;
    public Direction desiredDirection;

    int gridPositionX;
    int gridPositionY;

    /*Variable qui sert à changer de direction de manière aléatoire*/
    int compteur = 0;

    /* Permet de regarder si notre ghost est en mouvement ou pas => regarder s'il est bloqué*/
    IntPosition currentPosition;
    IntPosition oldPosition;

    WorldModel world;
    GridMap map;

    public Ghost(WorldModel world) {
        this.world = world;
        this.map = world.map;
        getNewDirection();
    }

    public void setSpawn(int x, int y) {
        position.x = x;
        position.y = y;

        gridPositionX = GridMap.STEP + x * 2 * GridMap.STEP;
        gridPositionY = GridMap.STEP + y * 2 * GridMap.STEP;

        currentPosition = new IntPosition(gridPositionX, gridPositionY);
        oldPosition = null;
    }

    public int getGridPositionX() {
        return gridPositionX;
    }

    public int getGridPositionY() {
        return gridPositionY;
    }

    /*Convertit les coordonnées de gridPosition en position float */
    FloatPosition getNormalizedPosition() {
        return new FloatPosition(
                (float) this.gridPositionX / (2 * GridMap.STEP),
                (float) this.gridPositionY / (2 * GridMap.STEP));
    }

    @Override
    public void move(double delta) {
        checkCollision();
        allRandomMove(delta);
    }

    private void checkCollision() {
        IntPosition[] dirs = { Direction.DOWN.getBaseVector(), Direction.UP.getBaseVector(),
                Direction.LEFT.getBaseVector(), Direction.RIGHT.getBaseVector(), new IntPosition(0, 0) };

        for (IntPosition dir : dirs) {
            if (getGridPositionX() + dir.x == world.player.getGridPositionX()
                    && getGridPositionY() + dir.y == world.player.getGridPositionY()) {
                world.player.hit();
                break;
            }
        }

    }

    /*Mouvement complétement aléatoire du fantôme */
    private void allRandomMove(double delta) {
        int speed = 1;
        if (isStuck()) {
            getNewDirection();
        } else if (compteur % 100 == 0)
            getNewDirection();

        int dtx = gridPositionX + desiredDirection.getX() * speed;
        int dty = gridPositionY + desiredDirection.getY() * speed;
        if (map.isAbstractPositionAllowed(dtx, dty) == SquareValidityResponse.VALID) {
            this.currentDirection = desiredDirection;
        }

        if (currentDirection != null) {
            int ctx = gridPositionX + currentDirection.getX() * speed;
            int cty = gridPositionY + currentDirection.getY() * speed;
            if (map.isAbstractPositionAllowed(ctx, cty) == SquareValidityResponse.VALID) {
                this.gridPositionX += currentDirection.getX() * speed;
                this.gridPositionY += currentDirection.getY() * speed;
            }
        }

        oldPosition = currentPosition;
        currentPosition = new IntPosition(gridPositionX, gridPositionY);
        compteur++;
        this.position = getNormalizedPosition();
    }

    //obtenir nouvelle direction du ghost.
    private void getNewDirection() {    
        int rand = (int) (Math.random() * 40 % 4);
        switch (rand) {
            case 0:
                desiredDirection = Direction.DOWN;
                break;
            case 1:
                desiredDirection = Direction.UP;
                break;
            case 2:
                desiredDirection = Direction.RIGHT;
                break;
            case 3:
                desiredDirection = Direction.LEFT;
                break;
        }
    }

    private boolean isStuck() {
        return currentPosition.equals(oldPosition);
    }

}
