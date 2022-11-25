package project.game.view;

import project.game.model.FloatPosition;

public class DisplayData {

    public double x;
    public double y;
    public double width;
    public double height;

    // Renvoie des coordonnées et tailles réelles sur l'écran en partant des coordonnées abstraites du modèle
    public DisplayData(GameView view, double x, double y, double width, double height) {
        this.width = width * view.tileSizeX;
        this.height = height * view.tileSizeY;
        this.x = view.offsetX + x * view.tileSizeX - this.width / 2;
        this.y = view.offsetY + y * view.tileSizeY - this.height / 2;
    }

    public DisplayData(GameView view, FloatPosition position, double width, double height) {
        this(view, (double) position.x, (double) position.y, width, height);
    }

    public DisplayData(GameView view, FloatPosition position, double widthAndHeight) {
        this(view, (double) position.x, (double) position.y, widthAndHeight, widthAndHeight);
    }

    public DisplayData(GameView view, double x, double y, double widthAndHeight) {
        this(view, x, y, widthAndHeight, widthAndHeight);
    }
}