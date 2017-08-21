package climbingboots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.friend.Friend;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.randoms.RandomSolver;

public class TradeSolver extends RandomSolver {

	ClimbingBootsScript script;
	private long lastTime = 0L;
	private float threshold;
	
	public TradeSolver(RandomEvent re, ClimbingBootsScript cbs) {
		super(re, cbs);
		this.script = cbs;
		this.threshold = (float) (Calculations.getRandom().nextFloat() * 0.25f + 0.75f);
	}

	@Override
	public int onLoop() {
		ClimbingBootsScript.log("tradeSolver");
		return 0;
	}

	@Override
	public boolean shouldExecute() {
		if(script.getTask().equals("Banking")){
			long delta = script.getTimer().elapsed() - lastTime;
			float fDeltaMinutes = ((float)delta) / 60000.0f;
			if(Math.exp((fDeltaMinutes - 5f) / 5f) > threshold){
				Friend trader = script.getFriends().getFriend(script.getTradeAccount());
				if(trader != null && trader.isOnline() && trader.isInMyWorld()){
					lastTime = script.getTimer().elapsed();
					threshold = 0.3f + Calculations.getRandom().nextFloat() * 0.7f;
					script.doTradeTask();
					return true;
				}
			}
		}
		return false;
	}

}
