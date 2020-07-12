package me.zabzabdoda.Weapons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Gun {

	private ItemStack type;
	private int clipSize, reloadTime, spread, speed, num, ammoType;
	private double bulletCooldown, damage;
	private float volume, pitch;
	private long currentBulletTime;
	private boolean auto,canReload;
	private Sound sound;
	private int amountShot;
	private Plugin plugin;

	public Gun(ItemStack m16Item, int clipSize, int reloadTime, int spread, int speed, double bulletCooldown, int num,
			double damage, boolean auto, Sound sound, float volume, float pitch, int ammoType) {
		this.type = m16Item;
		this.clipSize = clipSize;
		this.reloadTime = reloadTime;
		this.spread = spread;
		this.speed = speed;
		this.bulletCooldown = bulletCooldown;
		this.num = num;
		this.damage = damage;
		this.auto = auto;
		this.volume = volume;
		this.pitch = pitch;
		this.sound = sound;
		this.ammoType = ammoType;
		this.canReload = true;

	}

	public void shoot(Player player, Plugin plugin) {
		// System.out.println("CurrentBulletTime: " + currentBulletTime);
		// System.out.println("SystemTime: " + System.currentTimeMillis());
		this.plugin = plugin;
		System.out.println(amountShot);
		if (Weapons.getAmmoAmount(player, ammoType) > 1) {
			if (amountShot < clipSize - num) {

				if (currentBulletTime < System.currentTimeMillis()) {
					for (int i = 0; i < num; i++) {
						if (auto) {
							new BukkitRunnable() {
								@Override
								public void run() {
									Weapons.useAmmo(player, ammoType);
									amountShot++;
									Location playerLoc = new Location(player.getWorld(), player.getLocation().getX(),
											player.getEyeHeight() + player.getLocation().getY(),
											player.getLocation().getZ(), player.getLocation().getYaw(),
											player.getLocation().getPitch());
									Arrow arrow = player.getWorld().spawnArrow(playerLoc, playerLoc.getDirection(),
											speed, spread);
									arrow.setShooter(player);
									player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
									cancel();
								}
							}.runTaskTimer(plugin, i + 2, 5);
						} else {
							Weapons.useAmmo(player, ammoType);
							Location playerLoc = new Location(player.getWorld(), player.getLocation().getX(),
									player.getEyeHeight() + player.getLocation().getY(), player.getLocation().getZ(),
									player.getLocation().getYaw(), player.getLocation().getPitch());
							Arrow arrow = player.getWorld().spawnArrow(playerLoc, playerLoc.getDirection(), speed,
									spread);
							arrow.setShooter(player);
							player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
						}

					}
					currentBulletTime = (long) (System.currentTimeMillis() + (bulletCooldown * 1000));
				}

			} else {
				if(canReload) {
					canReload = false;
					reload();
				}
			}
		}
	}

	public int getNum() {
		return num;
	}

	public double getDamage() {
		return damage;
	}

	public void reload() {
		new BukkitRunnable() {
			@Override
			
			public void run() {
				amountShot = 0;
				canReload = true;
			}
		}.runTaskLater(plugin, reloadTime*20);
	}
	
	public void updateName(ItemStack item, String name,Player player) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name + " (" + (clipSize - amountShot) + "/" + Weapons.getAmmoAmount(player, ammoType) + ")");
		item.setItemMeta(meta);
	}
	
}
