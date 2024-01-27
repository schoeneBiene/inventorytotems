package fun.dungeonstudios.inventorytotem;

import fun.dungeonstudios.inventorytotem.events.TotemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class InventoryTotem extends JavaPlugin {

    @Override
    public void onEnable() {
        new TotemEvent(this);
    }

    @Override
    public void onDisable() {
    }
}
