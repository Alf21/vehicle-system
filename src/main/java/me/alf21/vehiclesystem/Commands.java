package me.alf21.vehiclesystem;

import me.alf21.tacho.BlockBarTacho;
import me.alf21.tacho.IndividuelRealTacho;
import me.alf21.tacho.RealTacho;
import net.gtaun.shoebill.common.command.Command;
import net.gtaun.shoebill.common.command.CommandHelp;
import net.gtaun.shoebill.constant.PlayerState;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;


public class Commands {
	
	public Commands(){}

	@Command
	@CommandHelp("Show help message")
	public boolean tachohelp(Player player)
	{
		player.sendMessage(Color.YELLOW, "/tacho [id]");
		return true;
	}
	
	@Command
	@CommandHelp("/tacho [id]")
	public boolean tacho(Player player, int id)
	{
		Tacho tacho;
		switch(id) {
			case 1: tacho = (Tacho) new RealTacho(player); break;
			case 2: tacho = (Tacho) new IndividuelRealTacho(player); break;
			default: tacho = (Tacho) new BlockBarTacho(player); break;
		}
		
		PlayerData playerLifecycle = VehicleSystem.getInstance().getPlayerLifecycleHolder().getObject(player, PlayerData.class);
		if(playerLifecycle.getTacho() != null) {
			playerLifecycle.getTacho().destroy();
			playerLifecycle.setTacho(null);
		}
		if(player.getState() == PlayerState.DRIVER || player.getState() == PlayerState.PASSENGER) {
			playerLifecycle.setTacho(tacho);
			playerLifecycle.getTacho().create();
			playerLifecycle.getTacho().show();
		}
		return true;
	}
	
}
