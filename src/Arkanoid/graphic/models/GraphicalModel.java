package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Board;
import Arkanoid.Logic.models.Model;

import java.awt.*;

public abstract class GraphicalModel {
    public abstract void paint(Graphics2D g);
    public abstract void updateState(Model model);
}
