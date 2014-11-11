package com.gmail.lolzdurps.plugins.minecraftplugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Start
        extends JavaPlugin
        implements Listener {
    static final HashMap<Player, ArrayList<Block>> hashmap = new HashMap();
    static final HashMap<Player, ArrayList<Block>> cfrozen = new HashMap();
    static final HashMap<String, ArrayList<Block>> offline = new HashMap();
    public final Logger logger = Logger.getLogger("Minecraft");

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Plugin(), this);
        this.logger.info("PlayerFreezer has been enabled!");
    }

    public void onDisable() {
        this.logger.info("PlayerFreezer has been disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("cfreeze")) {
            Player p = (Player) sender;
            if (p.hasPermission("freeze.allow")) {
                if (args.length < 1) {
                    p.sendMessage(ChatColor.DARK_RED + "Usage: /cfreeze <player>");
                    return true;
                }
                Player t = p.getServer().getPlayer(args[0]);
                String onlinecheck = t + "";
                if (onlinecheck.equals("null")) {
                    p.sendMessage(ChatColor.RED + "Player " + args[0] + " not found");
                    return true;
                }
                String pl = p.getDisplayName();
                String tn = t.getDisplayName();
                if (p.hasPermission("freeze.allow")) {
                    if (args.length == 1) {
                        cfrozen.put(t, null);
                        Location loc = t.getEyeLocation();
                        t.playSound(loc, Sound.PORTAL_TRAVEL, 100.0F, 0.0F);
                        t.setWalkSpeed(-1.0E-012F);
                        t.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, -5));
                        t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 32440, 4));
                        t.setGameMode(GameMode.ADVENTURE);
                        t.setCanPickupItems(false);
                        t.sendMessage("§3You have been clean frozen by an admin");
                        t.setFoodLevel(6);
                        t.eject();
                        this.logger.info(tn + " has been cleanfrozen");
                    }
                    else {
                        Bukkit.broadcast("§4" + p + " Tried to cfreeze " + t, "freeze.allow");
                        this.logger.info(pl + " tried to freeze " + tn);
                    }
                }
            }
        }
        if (label.equalsIgnoreCase("freeze")) {
            Player p = (Player) sender;
            if (!p.hasPermission("freeze.allow")) {
                p.sendMessage(ChatColor.RED + "You do not have permission to perform this command");
                return true;
            }
            if (p.hasPermission("freeze.allow")) {
                if (args.length < 1) {
                    p.sendMessage(ChatColor.DARK_RED + "Usage: /freeze <player>");
                    return true;
                }
                final Player t = p.getServer().getPlayer(args[0]);
                String onlinecheck = t + "";
                if (onlinecheck.equals("null")) {
                    p.sendMessage(ChatColor.RED + "Player " + args[0] + " not found");
                    return true;
                }
                t.setFallDistance(2.0F);
                final Location loc = t.getLocation();
                Location locd = t.getEyeLocation();
                String tn = t.getDisplayName();
                ItemStack milk = new ItemStack(Material.MILK_BUCKET, 1);
                ItemStack bedrock = new ItemStack(Material.BEDROCK, 1);
                ItemStack bucket = new ItemStack(Material.BUCKET, 1);
                PlayerInventory pi = t.getInventory();
                if (p.hasPermission("freeze.allow")) {
                    if (args.length != 1) {
                        p.sendMessage(ChatColor.DARK_RED + "Usage: /freeze <player>");
                    }
                    else if (args.length == 1) {
                        t.setDisplayName("§9[Frozen] " + tn + "§f");
                        while (p.getInventory().contains(milk)) {
                            p.getInventory().removeItem(new ItemStack[]{milk});
                            p.getInventory().addItem(new ItemStack[]{bucket});
                        }
                        Block locdb = locd.getBlock();
                        Block locab = locdb.getRelative(BlockFace.UP);
                        Location local = locab.getLocation();
                        Material locam = locab.getType();
                        if (locam == Material.STATIONARY_WATER) {
                            local.getBlock().setType(Material.AIR);
                        }
                        Block locf = loc.getBlock();
                        Block locfb = locf.getRelative(BlockFace.DOWN);
                        Location locfbl = locfb.getLocation();
                        Material locfbm = locfb.getType();

                        Block east = locf.getRelative(BlockFace.EAST);
                        Block west = locf.getRelative(BlockFace.WEST);
                        Block north = locf.getRelative(BlockFace.NORTH);
                        Block south = locf.getRelative(BlockFace.SOUTH);

                        Location eastl = east.getLocation();
                        Location westl = west.getLocation();
                        Location northl = north.getLocation();
                        Location southl = south.getLocation();

                        Material eastm = east.getType();
                        Material westm = west.getType();
                        Material northm = north.getType();
                        Material southm = south.getType();

                        Block feet = loc.getBlock();
                        Material feetm = feet.getType();
                        if ((eastm == Material.IRON_DOOR_BLOCK) || (eastm == Material.TRAP_DOOR) || (eastm == Material.WOOD_DOOR) || (eastm == Material.WOOD_BUTTON) || (eastm == Material.STONE_BUTTON) || (eastm == Material.LEVER) || (eastm == Material.DIODE_BLOCK_OFF) || (eastm == Material.TORCH) || (eastm == Material.REDSTONE_TORCH_OFF) || (eastm == Material.REDSTONE_WIRE) || (eastm == Material.REDSTONE_TORCH_ON) || (eastm == Material.DIODE_BLOCK_OFF)) {
                            east.breakNaturally();
                            p.sendMessage(ChatColor.YELLOW + "Freezing space obstructed, broke " + eastm);
                            eastl.getBlock().setType(Material.ICE);
                        }
                        else if (eastm != Material.AIR) {
                            east.breakNaturally();
                            eastl.getBlock().setType(Material.ICE);
                        }
                        else {
                            eastl.getBlock().setType(Material.ICE);
                        }
                        if ((westm == Material.IRON_DOOR_BLOCK) || (westm == Material.TRAP_DOOR) || (westm == Material.WOOD_DOOR) || (westm == Material.WOOD_BUTTON) || (westm == Material.STONE_BUTTON) || (westm == Material.LEVER) || (westm == Material.DIODE_BLOCK_OFF) || (westm == Material.TORCH) || (westm == Material.REDSTONE_TORCH_OFF) || (westm == Material.REDSTONE_WIRE) || (westm == Material.REDSTONE_TORCH_ON) || (westm == Material.DIODE_BLOCK_OFF)) {
                            west.breakNaturally();
                            p.sendMessage(ChatColor.YELLOW + "Freezing space obstructed, broke " + westm);
                            westl.getBlock().setType(Material.ICE);
                        }
                        else if (westm != Material.AIR) {
                            west.breakNaturally();
                            westl.getBlock().setType(Material.ICE);
                        }
                        else {
                            westl.getBlock().setType(Material.ICE);
                        }
                        if ((northm == Material.IRON_DOOR_BLOCK) || (northm == Material.TRAP_DOOR) || (northm == Material.WOOD_DOOR) || (northm == Material.WOOD_BUTTON) || (northm == Material.STONE_BUTTON) || (northm == Material.LEVER) || (northm == Material.DIODE_BLOCK_OFF) || (northm == Material.TORCH) || (northm == Material.REDSTONE_TORCH_OFF) || (northm == Material.REDSTONE_WIRE) || (northm == Material.REDSTONE_TORCH_ON) || (northm == Material.DIODE_BLOCK_OFF)) {
                            north.breakNaturally();
                            p.sendMessage(ChatColor.YELLOW + "Freezing space obstructed, broke " + northm);
                            northl.getBlock().setType(Material.ICE);
                        }
                        else if (northm != Material.AIR) {
                            north.breakNaturally();
                            northl.getBlock().setType(Material.ICE);
                        }
                        else {
                            northl.getBlock().setType(Material.ICE);
                        }
                        if ((southm == Material.IRON_DOOR_BLOCK) || (southm == Material.TRAP_DOOR) || (southm == Material.WOOD_DOOR) || (southm == Material.WOOD_BUTTON) || (southm == Material.STONE_BUTTON) || (southm == Material.LEVER) || (southm == Material.DIODE_BLOCK_OFF) || (southm == Material.TORCH) || (southm == Material.REDSTONE_TORCH_OFF) || (southm == Material.REDSTONE_WIRE) || (southm == Material.REDSTONE_TORCH_ON) || (southm == Material.DIODE_BLOCK_OFF)) {
                            south.breakNaturally();
                            p.sendMessage(ChatColor.YELLOW + "Freezing space obstructed, broke " + southm);
                            southl.getBlock().setType(Material.ICE);
                        }
                        else if (southm != Material.AIR) {
                            p.sendMessage(ChatColor.YELLOW + "Freezing space obstructed, broke " + southm);
                            south.breakNaturally();
                            southl.getBlock().setType(Material.ICE);
                        }
                        else {
                            southl.getBlock().setType(Material.ICE);
                        }
                        t.teleport(locd);
                        if (locfbm == Material.AIR) {
                            t.teleport(locd);
                            locfbl.getBlock().setType(Material.BEDROCK);
                        }
                        if (locfbm == Material.WATER) {
                            t.sendBlockChange(locfbl, Material.ICE, (byte) 0);
                        }
                        if (locfbm == Material.STATIONARY_WATER) {
                            t.sendBlockChange(locfbl, Material.ICE, (byte) 0);
                        }
                        t.playSound(locd, Sound.PORTAL_TRAVEL, 100.0F, 0.0F);
                        t.playEffect(locd, Effect.RECORD_PLAY, 2267);
                        t.setWalkSpeed(-1.0E-012F);
                        t.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, -5));
                        t.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 32440, 4));
                        t.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 32440, 4));
                        t.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 32440, 4));
                        pi.setHelmet(bedrock);
                        t.setGameMode(GameMode.ADVENTURE);
                        t.setCanPickupItems(false);
                        p.sendMessage("§b" + tn + "§b has been successfully frozen");
                        t.sendMessage("§3You have been frozen by an admin");
                        t.setFoodLevel(6);
                        t.eject();
                        this.logger.info(tn + " has been frozen");
                        Block b = loc.getBlock();
                        final double bly = b.getY();
                        double blx = b.getX();
                        double blz = b.getZ();
                        final double ablx = blx + 0.5D;
                        final double ablz = blz + 0.5D;
                        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                            public void run() {
                                Start.hashmap.put(t, null);
                                t.sendBlockChange(loc, Material.WEB, (byte) 0);
                                t.teleport(new Location(t.getWorld(), ablx, bly, ablz));
                                t.setRemainingAir(0);
                            }
                        }, 40L);
                        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                            public void run() {
                                Location loce = t.getEyeLocation();
                                loce.getBlock().setType(Material.ICE);
                            }
                        }, 50L);
                    }
                }
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (label.equalsIgnoreCase("unfreeze")) {
            Player p = (Player) sender;
            if (!p.hasPermission("freeze.allow")) {
                p.sendMessage(ChatColor.RED + "You do not have permission to perform this command");
                return true;
            }
            if (p.hasPermission("freeze.allow")) {
                if (args.length < 1) {
                    p.sendMessage(ChatColor.DARK_RED + "Usage: /unfreeze <player>");
                    return true;
                }
                Player t = p.getServer().getPlayer(args[0]);
                String onlinecheck = t + "";
                if (onlinecheck.equals("null")) {
                    p.sendMessage(ChatColor.RED + "Player " + args[0] + " not found");
                    return true;
                }
                String pl = p.getDisplayName();

                String tn = t.getDisplayName();
                String rtn = t.getName();
                hashmap.remove(t);
                cfrozen.remove(t);
                ItemStack air = new ItemStack(Material.AIR, 1);
                ItemStack bedrock = new ItemStack(Material.BEDROCK, 1);
                PlayerInventory pi = t.getInventory();
                if (p.hasPermission("freeze.allow")) {
                    if (args.length == 1) {
                        t.setDisplayName(rtn);
                        if (p.getInventory().contains(bedrock)) {
                            p.getInventory().removeItem(new ItemStack[]{bedrock});
                        }
                        Location loc = t.getLocation();


                        Block locf = loc.getBlock();

                        Block east = locf.getRelative(BlockFace.EAST);
                        Block west = locf.getRelative(BlockFace.WEST);
                        Block north = locf.getRelative(BlockFace.NORTH);
                        Block south = locf.getRelative(BlockFace.SOUTH);

                        Location eastl = east.getLocation();
                        Location westl = west.getLocation();
                        Location northl = north.getLocation();
                        Location southl = south.getLocation();

                        eastl.getBlock().setType(Material.AIR);
                        westl.getBlock().setType(Material.AIR);
                        northl.getBlock().setType(Material.AIR);
                        southl.getBlock().setType(Material.AIR);

                        Block locdb = loc.getBlock();
                        Block locab = locdb.getRelative(BlockFace.DOWN);
                        Location local = locab.getLocation();
                        Material locam = locab.getType();
                        Location locd = t.getEyeLocation();
                        Block locdeb = locd.getBlock();
                        Block locdu = locdeb.getRelative(BlockFace.UP);
                        Location locdul = locdu.getLocation();
                        Material locdum = locdeb.getType();
                        if (locdum == Material.ICE) {
                            t.sendBlockChange(locdul, Material.AIR, (byte) 0);
                        }
                        if (locam == Material.BEDROCK) {
                            local.getBlock().setType(Material.AIR);
                        }
                        t.sendBlockChange(loc, Material.AIR, (byte) 0);
                        t.sendBlockChange(locd, Material.AIR, (byte) 0);
                        t.playEffect(loc, Effect.RECORD_PLAY, 5555);
                        t.setWalkSpeed(0.2F);
                        t.removePotionEffect(PotionEffectType.JUMP);
                        t.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        t.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                        t.removePotionEffect(PotionEffectType.HEAL);
                        t.removePotionEffect(PotionEffectType.INVISIBILITY);
                        t.setGameMode(GameMode.SURVIVAL);
                        t.setCanPickupItems(true);
                        pi.setHelmet(air);
                        while (pi.contains(Material.BEDROCK)) {
                            pi.removeItem(new ItemStack[]{bedrock});
                        }
                        p.sendMessage("§6" + t.getName() + "§6 has been successfully thawed");
                        t.sendMessage("§6You have been thawed by an admin");
                        t.setFoodLevel(20);
                        t.eject();
                        this.logger.info(t.getName() + " has been unfrozen");
                        locd.getBlock().setType(Material.AIR);
                        Block bfb = loc.getBlock();
                        BlockState bf = bfb.getState();
                        bf.update();
                    }
                    else {
                        p.sendMessage(ChatColor.DARK_RED + "Usage: /unfreeze <player>");
                    }
                }
                else {
                    p.sendMessage(ChatColor.RED + "You do not have permission to run this command");
                    Bukkit.broadcast("§4 " + p + " Tried to unfreeze " + t, "freeze.allow");
                    this.logger.info(pl + " tried to unfreeze " + tn);
                }
            }
        }
        return true;
    }
}



