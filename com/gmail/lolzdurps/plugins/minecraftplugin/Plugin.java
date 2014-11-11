package com.gmail.lolzdurps.plugins.minecraftplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Plugin
        implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        ItemStack bedrock = new ItemStack(Material.BEDROCK, 1);
        ItemStack air = new ItemStack(Material.AIR, 1);
        PlayerInventory pi = p.getInventory();
        if (Start.hashmap.containsKey(p)) {
            Location from = event.getFrom();
            Location to = event.getTo();
            if ((from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ())) {
                p.teleport(from);
            }
            pi.setHelmet(air);
            pi.setHelmet(bedrock);
            p.removePotionEffect(PotionEffectType.JUMP);
            p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            p.removePotionEffect(PotionEffectType.HEAL);
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, -5));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 32440, 4));
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 32440, 4));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 32440, 4));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 32440, 4));
        }
        if (Start.cfrozen.containsKey(p)) {
            Location loc = p.getLocation();
            p.teleport(loc);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (Start.hashmap.containsKey(p)) {
            p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 32440, 4));
            event.setCancelled(true);
            p.closeInventory();
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (Start.hashmap.containsKey(p)) {
            event.setCancelled(true);
            p.closeInventory();
        }
    }

    @EventHandler
    public void onDropItemEvent(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        ItemStack bedrock = new ItemStack(Material.BEDROCK, 1);
        ItemStack air = new ItemStack(Material.AIR, 1);
        PlayerInventory pi = p.getInventory();
        if (Start.hashmap.containsKey(p)) {
            pi.setHelmet(air);
            pi.setHelmet(bedrock);
            event.setCancelled(true);
            p.closeInventory();
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (Start.hashmap.containsKey(p)) {
            event.setCancelled(true);
            p.closeInventory();
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity p = event.getDamager();
        if (Start.hashmap.containsKey(p)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        String op = p.getName();
        if (Start.hashmap.containsKey(p)) {
            Start.offline.put(op, null);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String op = p.getName();
        if (Start.offline.containsKey(op)) {
            Start.hashmap.put(p, null);
            Start.offline.remove(op);
        }
    }
}



