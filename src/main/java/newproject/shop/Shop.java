package newproject.shop;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Shop extends JavaPlugin implements Listener {

    Inventory inv = Bukkit.createInventory(null, 54, "상점");

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        ItemStack wool = new ItemStack(Material.WHITE_WOOL);
        ItemMeta woolmeta = wool.getItemMeta();
        woolmeta.setDisplayName("양털");
        woolmeta.setLore(Arrays.asList("클릭 시 양털 지급"));
        wool.setItemMeta(woolmeta);

        inv.setItem(0, wool);
    }

    @Override
    public void onDisable() {
        System.out.println("아무거나");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Action a = e.getAction();

        if(a == Action.LEFT_CLICK_AIR) {
            //행동
        } else if(a == Action.LEFT_CLICK_BLOCK) {
            //행동
        }
    }

    @EventHandler
    public void onEntityClick(PlayerInteractAtEntityEvent e) {
        Entity entity = e.getRightClicked();
        Player p = e.getPlayer();

        if(entity.getType().equals(EntityType.VILLAGER)) {

            p.openInventory(inv);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory().equals(inv)) {

            e.setCancelled(true);

            if (e.getCurrentItem().getType().equals(Material.WHITE_WOOL)) {

                if (p.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 4)) {

                    ItemStack iron = new ItemStack(Material.IRON_INGOT);
                    iron.setAmount(4);
                    p.getInventory().removeItem(null);

                    ItemStack wool =new ItemStack((Material.WHITE_WOOL));
                    wool.setAmount(16);
                    p.getInventory().addItem();
                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);

                } else {
                    p.sendMessage("철이 부족합니다");
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("gm")) {
            //실행할 내용
            Player p = (Player) sender;
            if (args.length == 1) {
                if (args[0].equals("0")) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage("게임 모드를 서바이벌 모드로 설정했습니다");
                } else if (args[0].equalsIgnoreCase("1")) {
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage("게임 모드를 크리에이티브 모드로 설정했습니다");
                } else if (args[0].equalsIgnoreCase("2")) {
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage("게임 모드를 모험 모드로 설정했습니다");
                } else if (args[0].equalsIgnoreCase("3")) {
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage("게임 모드를 관전자 모드로 설정했습니다");
                }
            }
        }
        return false;
    }
}
