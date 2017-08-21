package base;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.randoms.RandomSolver;

public class Solver extends RandomSolver {

    public Solver(RandomEvent randomEvent, MethodContext methodContext) {
        super(randomEvent, methodContext);
    }

    @Override
    public boolean shouldExecute() {
        //Make sure to change this boolean to only be true if your Condition for the 'Random' is met.
        return true;
    }

    @Override
    public int onLoop() {
        return 0;
    }
}
