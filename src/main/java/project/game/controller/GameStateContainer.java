package project.game.controller;

import project.game.model.WorldModel;
import project.game.view.GameView;

public class GameStateContainer {
	public WorldModel model;
	public GameView view;

	public GameStateContainer(WorldModel model, GameView view) {
		this.model = model;
		this.view = view;
	}
}