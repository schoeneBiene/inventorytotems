package fun.dungeonstudios.inventorytotem.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TotemEvent implements Listener {

    private final JavaPlugin plugin;

    public TotemEvent(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // Check if the player has Totems in the inventory
            if (hasTotem(player)) {
                // Cancel the damage event to prevent the player from taking damage
                event.setCancelled(true);

                // Consume a Totem from the inventory
                consumeTotem(player);

                // Perform any other actions you want when a Totem is used
                activateTotem(player);
            }
        }
    }

    private boolean hasTotem(Player player) {
        // Check if the player has Totems in the inventory
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
                return true;
            }
        }
        return false;
    }

    private void consumeTotem(Player player) {
        // Consume a Totem from the inventory
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
                item.setAmount(item.getAmount() - 1);
                break;
            }
        }
    }

    private void activateTotem(Player player) {
        // Perform any other actions you want when a Totem is used
        player.sendMessage("Totem activated!");

        // Play the death pop sound
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);

        // Restore a small portion of health
        player.setHealth(Math.min(player.getHealth() + 4.0, player.getMaxHealth()));

        // Grant 4 full hearts of absorption
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 3));

        // Give Player Regeneration 1
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));

        // Give player fire resistance 1
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 0));

        // Spawn particles that all players can see and hear (3 times the particles)
        spawnTotemParticles(player.getLocation(), 90);
    }

    private void spawnTotemParticles(Location location, int particleCount) {
        // Spawn particles that all players can see and hear
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.spawnParticle(org.bukkit.Particle.TOTEM, location, particleCount, 0.5, 0.5, 0.5, 1);
            onlinePlayer.playSound(location, Sound.ENTITY_PLAYER_DEATH, 1.0f, 1.0f);
        }
    }
}
