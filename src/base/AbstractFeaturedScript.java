package base;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.message.Message;

import base.Task.TaskBody;

public abstract class AbstractFeaturedScript extends AbstractScript implements MessageListener{

	protected Set<SmallTask> smallTasks = new HashSet<SmallTask>();
	protected Timer timer;
	private Task currentTask = new Task("null", new TaskBody() {
		@Override
		public int execute(){
			log("No task initialized.");
			return 500;
		}
	});

	public void onStart(){
		timer = new Timer();
	}

	@Override
	public int onLoop(){
		for(SmallTask smallTask : smallTasks){
			smallTask.execute();
			smallTasks.remove(smallTask);
		}
		return currentTask.execute();
	}

	public void setNextTask(Task next){
		currentTask = next;
		smallTasks.clear();
	}
	
	public String getTask(){
		return currentTask.getLabel();
	}
	
	public Timer getTimer(){
		return timer;
	}

	protected void addToSmallTasks(int priority, TaskBody body){
		smallTasks.add(new SmallTask(priority, body));
	}

	protected void walkingSleep(){
		sleepUntil(new Condition() {
			public boolean verify(){
				return getLocalPlayer().isMoving();
			}
		}, Calculations.random(1200, 1600));
		sleepUntil(new Condition() {
			public boolean verify(){
				return !getLocalPlayer().isMoving();
			}
		}, Calculations.random(2400, 3600));
	}

	protected static boolean conditionalSleep(Condition condition, long lb, long ub){
		return sleepUntil(condition, Calculations.random(lb, ub));
	}

	protected boolean openObstacle(String obstacle){
		GameObject g = getGameObjects().closest(obstacle);
		if(g != null)
			if(g.interact("Open")){
				walkingSleep();
				return true;
			}
		return false;
	}

	protected void roofsOff(){
		getClientSettings().toggleRoofs(false);
	}

	public void onPaint(Graphics g){
		if(currentTask != null){
			g.drawString("State: " + currentTask.getLabel(), 10, 35);
		}
		if(timer != null)
			g.drawString("Runtime: " + timer.formatTime(), 10, 50);
	}

	@Override
	public void onGameMessage(Message m){
	}

	@Override
	public void onPlayerMessage(Message m){
	}

	@Override
	public void onPrivateInMessage(Message m){
	}

	@Override
	public void onPrivateOutMessage(Message m){
	}

	@Override
	public void onTradeMessage(Message m){
	}

}
