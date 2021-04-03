import graphic.GraphicalAgent;
import Logic.LogicalAgent;

public class Main {

    public static void main(String[] args) {
        LogicalAgent logicalAgent = new LogicalAgent();
        GraphicalAgent graphicalAgent = new GraphicalAgent(logicalAgent);
    }
}
