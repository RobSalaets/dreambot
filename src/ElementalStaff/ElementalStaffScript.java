package ElementalStaff;

import java.util.HashMap;

import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

import base.AbstractFeaturedScript;
import base.Task;
import base.Task.TaskBody;

@ScriptManifest(author = "RobbieBoi", name = "Switch Elemental Staffs", version = 1.0, description = "flaps", category = Category.MONEYMAKING)
public class ElementalStaffScript extends AbstractFeaturedScript{
	
	private final int AIR = 1381;
	private final int FIRE = 1387;
	private final int EARTH = 1381;
	private final int WATER = 1385;
	private final int ELEMENTAL_SPHERE = 13660;
	private HashMap<Integer, Integer> optionsMap;

	private Task switchStaffs = new Task("Staff switch", new TaskBody() {
		@Override
		public int execute(){
			Item staff = getInventory().get(FIRE);
			if(staff != null){
				int slot = staff.getSlot();
				GameObject elementalSphere = getGameObjects().closest(ELEMENTAL_SPHERE);
				if(elementalSphere != null){
					staff.useOn(elementalSphere);
					conditionalSleep(()->getDialogues().inDialogue(), 500, 1000);
					if(getDialogues().inDialogue()){
						getDialogues().typeOption(optionsMap.get(AIR));
						conditionalSleep(()->getInventory().getIdForSlot(slot) == AIR, 5000, 6000);
					}
				}
			}
			return 0;
		}
	});

	@Override
	public void onStart(){
		super.onStart();
		setNextTask(switchStaffs);
		optionsMap = new HashMap<>();
		optionsMap.put(EARTH, 1);
		optionsMap.put(AIR, 2);
		optionsMap.put(FIRE, 3);
		optionsMap.put(WATER, 4);
	}
}
