package project.game.model.utils;

// Représente l'état possible d'une case de jeu

public enum SquareValidityResponse {
    INVALID, // n'existe pas 
    VALID, // existe
    TELEPORT, // permet une téléportation à l'endroit oppposé de la carte
}
