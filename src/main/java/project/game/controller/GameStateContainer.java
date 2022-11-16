package project.game.controller;

import project.game.model.GameWorldModel;
import project.game.view.GameView;

public class GameStateContainer {
	public GameWorldModel model;
	public GameView view;

	public GameStateContainer(GameWorldModel model, GameView view) {
		this.model = model;
		this.view = view;
	}
}