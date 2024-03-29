package project.game.model.general;

import project.game.controller.AudioController;
import project.game.model.utils.Direction;
import project.game.model.utils.FloatPosition;
import project.game.model.utils.IntPosition;
import project.game.model.utils.SquareValidityResponse;

/*Le joueuer, on gère son déplacement,son score et son comportement quand il se fait toucher
 * par un projectile ou un fantôme
 */
public class Player extends Entity {

    //Taille de la hitbox du joueur
    public static final float RADIUS_HITBOX_SIZE = 0.07f;
    
    public Direction currentDirection = null;
    public Direction desiredDirection = null;

    public WorldModel world;
    public GridMap map;

    public boolean speedX2 = false;

    public IntPosition gridPosition = new IntPosition(0, 0);

    public int deaths;
    private int lives = 9;

    public final static int MAX_STAMINA = 10 * 60;
    public int stamina = MAX_STAMINA;
    
    public int score;

    public int finalScore = 0;

    public int invulnerabilityTicks = 0;

    public boolean isMoving = false;
    public IntPosition lastGridPosition = new IntPosition(getGridPositionX(), getGridPositionY());

    public int getGridPositionX() {
        return gridPosition.x;
    }

    public int getGridPositionY() {
        return gridPosition.y;
    }

    public Player(WorldModel world) {
        this.world = world;
        this.map = world.map;
    }

    //Convertit notre position de mode grid en mode float
    FloatPosition getNormalizedPosition() {
        return new FloatPosition(
                (float) getGridPositionX() / (2 * GridMap.STEP),
                (float) getGridPositionY() / (2 * GridMap.STEP));
    }

    //Convertit notre position de mode grid en mode tile
    public IntPosition getTilePosition() {
        return new IntPosition((getGridPositionX() - GridMap.STEP) / (2 * GridMap.STEP),
                (getGridPositionY() - GridMap.STEP) / (2 * GridMap.STEP));
    }

    public IntPosition getCenteredTilePosition() {
        return new IntPosition((getGridPositionX()) / (2 * GridMap.STEP), (getGridPositionY()) / (2 * GridMap.STEP));
    }

    @Override
    public void move(double delta) {
        //décompte les ticks d'invulnérabiltés
        this.invulnerabilityTicks = Math.max(0, this.invulnerabilityTicks - 1);
        if (desiredDirection != null) {
            int speed = 1;

            if (speedX2 && stamina <= 0) {
                speedX2 = false;
            }

            if (speedX2) {
                if (gridPosition.x % 2 == 0 && gridPosition.y % 2 == 0)
                    speed = 2;
            }

            /*Gestion de stamina */
            stamina += speedX2 ? -2 : 1;
            stamina = Math.min(MAX_STAMINA, stamina);
            stamina = Math.max(0, stamina);

            /*Si la position désirée du joueuer est valid alors
             * on change de direction vers la direction souhaité
             */
            int dtx = gridPosition.x + desiredDirection.getX() * speed;
            int dty = gridPosition.y + desiredDirection.getY() * speed;
            if (map.isAbstractPositionAllowed(dtx, dty) == SquareValidityResponse.VALID) {
                this.currentDirection = desiredDirection;
            }

            /*Bouge dans la direction indique */
            if (currentDirection != null) {
                int ctx = gridPosition.x + currentDirection.getX() * speed;
                int cty = gridPosition.y + currentDirection.getY() * speed;

                if (map.isAbstractPositionAllowed(ctx, cty) == SquareValidityResponse.VALID) { // == VALID
                    this.gridPosition.x += currentDirection.getX() * speed;
                    this.gridPosition.y += currentDirection.getY() * speed;
                } else if (map.isAbstractPositionAllowed(ctx, cty) == SquareValidityResponse.TELEPORT) {
                    IntPosition tpPos = world.map.getNextTeleportPosition(this.currentDirection,this.gridPosition, speed);
                    this.gridPosition.x = tpPos.x;
                    this.gridPosition.y = tpPos.y;
                }
            }

        }

        this.position = getNormalizedPosition();

        this.isMoving = !this.lastGridPosition.equals(this.gridPosition);
        this.lastGridPosition = new IntPosition(this.gridPosition);
    }

    // A partir de coordonnes x,y on definit notre premier lieu d'apparation du
    // joueur
    public void setSpawn(int x, int y) {
        position.x = x;
        position.y = y;

        gridPosition.x = GridMap.STEP + x * 2 * GridMap.STEP;
        gridPosition.y = GridMap.STEP + y * 2 * GridMap.STEP;
    }

    public String toString() {
        return this.position + " " + this.currentDirection + " " + this.desiredDirection;
    }

    public int getScore() {
        return score;
    }

    /*Gestion du comportement du joueuer quand il se fait toucher */
    public void hit() {
        if (this.world.hasFinished()) return;
        if (invulnerabilityTicks == 0) {
            removeLife();
            invulnerabilityTicks += 90;
            AudioController.playHitSound();
        }
        
    }

    public int getLives() {
        return lives;
    }

    void removeLife() {
        lives--;
        if (lives < 0)
            lives = 0;
    }
}
