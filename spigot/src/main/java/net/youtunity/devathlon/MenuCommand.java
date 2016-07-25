package net.youtunity.devathlon;

import com.quartercode.quarterbukkit.api.select.ClickType;
import com.quartercode.quarterbukkit.api.select.LineInventoryLayouter;
import com.quartercode.quarterbukkit.api.select.SelectInventory;
import com.quartercode.quarterbukkit.api.select.Selection;
import net.youtunity.devathlon.api.protocol.interaction.ChangeTemplateRequest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * Created by thecrealm on 25.07.16.
 */
public class MenuCommand implements CommandExecutor {

    private DevathlonPlugin plugin;

    public MenuCommand(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {
            Player player = ((Player) commandSender);

            SelectInventory selectInventory = new SelectInventory(plugin, "Server Menu", new LineInventoryLayouter()) {

                @Override
                protected void onClick(Selection selection, ClickType clickType, Player player) {
                    String selected = selection.getValue().toString();
                    System.out.println(selected + " - " + plugin.getServerName());
                    plugin.getClient().sendMessage(new ChangeTemplateRequest(plugin.getServerName(), selected));
                    close(player);
                }
            };

            int count = 1;
            for (String template : plugin.getAvailableTemplates()) {
                selectInventory.add(template, Material.values()[count++], template);
            }

            selectInventory.open(player);
        }

        return true;
    }
}
