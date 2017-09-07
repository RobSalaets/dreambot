package examples;

import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import base.AbstractFeaturedScript;

@ScriptManifest(author = "RobbieBoi", name = "TestScript", version = 1.0, description = "flaps", category = Category.MONEYMAKING)
public class TestScript extends AbstractFeaturedScript{

	
	@Override
	public int onLoop(){
		super.onLoop();
		getBank().openClosest();
		getBank().setWithdrawMode(BankMode.NOTE);
		getBank().withdraw(3105, 10);
		getBank().setWithdrawMode(BankMode.ITEM);
		
		return 10000;
	}
}
