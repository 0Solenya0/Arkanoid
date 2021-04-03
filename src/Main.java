import graphic.GraphicalAgent;
import Logic.LogicalAgent;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
       LogicalAgent logicalAgent = new LogicalAgent();
       GraphicalAgent graphicalAgent = new GraphicalAgent(logicalAgent);
    }
}
