package me.zabzabdoda.Weapons;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Weapons extends JavaPlugin implements Listener {
	public static Server server;
	public static Gun M16, Mac10, Rifle, Shotgun, Huge, Pistol, Glock, Deagle, SilencedPistol, NewtonLauncher;
	public static ItemStack C4, KnifeItem, ArmorItem, RadarItem, NewtonLauncherItem;
	public static ItemStack M16Item, SilencedPistolItem, Mac10Item, RifleItem, ShotgunItem, HugeItem, PistolItem,
			DeagleItem, GlockItem;
	private ItemMeta NewtonLauncherMeta, RadarMeta, KnifeMeta, ArmorMeta, M16Meta, SilencedPistolMeta, Mac10Meta,
			RifleMeta, ShotgunMeta, HugeMeta, PistolMeta, DeagleMeta, GlockMeta, C4Meta;
	private World currentWorld;
	private Inventory C4Inv;
	private ArrayList<ArmorStand> c4List;
	private ArrayList<String> fileText;

	static {
		server = Bukkit.getServer();
	}

	public Weapons() {
		c4List = new ArrayList<ArmorStand>();
	}

	public void onEnable() {
		Weapons.server.getPluginManager().registerEvents((Listener) this, (Plugin) this);
		currentWorld = Bukkit.getWorlds().get(0);

		C4 = new ItemStack(Material.CREEPER_HEAD);
		(this.C4Meta = C4.getItemMeta()).setDisplayName(ChatColor.RED + "C4");
		C4.setItemMeta(C4Meta);

		M16Item = new ItemStack(Material.STONE_PICKAXE);
		(this.M16Meta = M16Item.getItemMeta()).setDisplayName(ChatColor.GRAY + "M16");
		M16Meta.setUnbreakable(true);
		M16Item.setItemMeta(M16Meta);
		M16 = new Gun(M16Item, 20, 2, 2, 4, 0.25f, 1, 4.6, false, Sound.ENTITY_WITHER_HURT, 4, 2, 2);

		Mac10Item = new ItemStack(Material.GOLDEN_SHOVEL);
		(this.Mac10Meta = Mac10Item.getItemMeta()).setDisplayName(ChatColor.GRAY + "Mac10");
		Mac10Meta.setUnbreakable(true);
		Mac10Item.setItemMeta(Mac10Meta);
		Mac10 = new Gun(Mac10Item, 30, 2, 6, 4, 0f, 2, 2.4, true, Sound.ENTITY_WITHER_SHOOT, 4, 1.5f, 2);

		RifleItem = new ItemStack(Material.DIAMOND_HOE);
		(this.RifleMeta = RifleItem.getItemMeta()).setDisplayName(ChatColor.GRAY + "Rifle");
		RifleMeta.setUnbreakable(true);
		RifleItem.setItemMeta(RifleMeta);
		Rifle = new Gun(RifleItem, 10, 2, 0, 4, 1.5f, 1, 10, false, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 4, 1.5f,
				3);

		ShotgunItem = new ItemStack(Material.DIAMOND_PICKAXE);
		(this.ShotgunMeta = ShotgunItem.getItemMeta()).setDisplayName(ChatColor.GRAY + "Shotgun");
		ShotgunMeta.setUnbreakable(true);
		ShotgunItem.setItemMeta(ShotgunMeta);
		Shotgun = new Gun(ShotgunItem, 8, 2, 6, 4, 0.75f, 5, 4, false, Sound.ENTITY_GENERIC_EXPLODE, 4, 1.2f, 5);

		HugeItem = new ItemStack(Material.GOLDEN_PICKAXE);
		(this.HugeMeta = HugeItem.getItemMeta()).setDisplayName(ChatColor.GRAY + "H.U.G.E. 249");
		HugeMeta.setUnbreakable(true);
		HugeItem.setItemMeta(HugeMeta);
		Huge = new Gun(HugeItem, 64, 2, 10, 4, 0f, 8, 1, true, Sound.ENTITY_BLAZE_SHOOT, 4, 2, 2);

		PistolItem = new ItemStack(Material.IRON_SHOVEL);
		(this.PistolMeta = PistolItem.getItemMeta()).setDisplayName(ChatColor.GRAY + "Pistol");
		PistolMeta.setUnbreakable(true);
		PistolItem.setItemMeta(PistolMeta);
		Pistol = new Gun(PistolItem, 10, 2, 2, 4, 0.5f, 1, 5, false, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 4, 2, 1);

		DeagleItem = new ItemStack(Material.WOODEN_SHOVEL);
		(this.DeagleMeta = DeagleItem.getItemMeta()).setDisplayName(ChatColor.GRAY + "Deagle");
		DeagleMeta.setUnbreakable(true);
		DeagleItem.setItemMeta(DeagleMeta);
		Deagle = new Gun(DeagleItem, 10, 2, 2, 4, 0.75f, 1, 7.4, false, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 4, 0.5f, 4);

		GlockItem = new ItemStack(Material.STONE_SHOVEL);
		(this.GlockMeta = GlockItem.getItemMeta()).setDisplayName(ChatColor.GRAY + "Glock");
		GlockMeta.setUnbreakable(true);
		GlockItem.setItemMeta(GlockMeta);
		Glock = new Gun(GlockItem, 20, 2, 2, 4, 0f, 3, 3, true, Sound.ENTITY_ENDER_DRAGON_HURT, 4, 2, 1);

		SilencedPistolItem = new ItemStack(Material.IRON_AXE);
		(this.SilencedPistolMeta = SilencedPistolItem.getItemMeta()).setDisplayName(ChatColor.RED + "Silenced Pistol");
		SilencedPistolMeta.setUnbreakable(true);
		SilencedPistolItem.setItemMeta(SilencedPistolMeta);
		SilencedPistol = new Gun(SilencedPistolItem, 10, 2, 2, 4, 0.5f, 1, 6, false, Sound.ENTITY_ENDER_DRAGON_HURT, 0,
				1, 1);

		KnifeItem = new ItemStack(Material.DIAMOND_SWORD);
		(this.KnifeMeta = KnifeItem.getItemMeta()).setDisplayName(ChatColor.RED + "Knife");
		KnifeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
				new AttributeModifier("AttackDamage", 9, AttributeModifier.Operation.ADD_NUMBER));
		KnifeMeta.setUnbreakable(true);
		KnifeItem.setItemMeta(KnifeMeta);

		ArmorItem = new ItemStack(Material.IRON_PICKAXE);
		(this.ArmorMeta = ArmorItem.getItemMeta()).setDisplayName(ChatColor.RED + "Body Armor");
		ArmorMeta.setUnbreakable(true);
		ArmorItem.setItemMeta(ArmorMeta);

		RadarItem = new ItemStack(Material.DIAMOND_SHOVEL);
		(this.RadarMeta = RadarItem.getItemMeta()).setDisplayName(ChatColor.RED + "Radar");
		RadarMeta.setUnbreakable(true);
		RadarItem.setItemMeta(RadarMeta);

		NewtonLauncherItem = new ItemStack(Material.IRON_SWORD);
		(this.NewtonLauncherMeta = NewtonLauncherItem.getItemMeta()).setDisplayName(ChatColor.RED + "Newton Launcher");
		NewtonLauncherMeta.setUnbreakable(true);
		NewtonLauncherItem.setItemMeta(NewtonLauncherMeta);
		NewtonLauncher = new Gun(NewtonLauncherItem, 20, 2, 2, 4, 0f, 1, 2, false, Sound.ENTITY_ENDER_DRAGON_HURT, 4, 2,
				-1);

	}

	public void onDisable() {
		Weapons.server.broadcastMessage("Loot plugin is ending");
	}

	public Inventory C4Setup(Player player, boolean traitor, boolean isArmed) {
		C4Inv = Bukkit.createInventory(null, 9);

		ItemStack redWire = new ItemStack(Material.RED_WOOL, 1);
		ItemMeta redWireMeta = redWire.getItemMeta();
		redWireMeta.setDisplayName(ChatColor.RED + "Red Wire");
		redWire.setItemMeta(redWireMeta);
		C4Inv.setItem(2, redWire);

		ItemStack BlueWire = new ItemStack(Material.BLUE_WOOL, 1);
		ItemMeta BlueWireMeta = BlueWire.getItemMeta();
		BlueWireMeta.setDisplayName(ChatColor.BLUE + "Blue Wire");
		BlueWire.setItemMeta(BlueWireMeta);
		C4Inv.setItem(3, BlueWire);

		ItemStack GreenWire = new ItemStack(Material.GREEN_WOOL, 1);
		ItemMeta GreenWireMeta = GreenWire.getItemMeta();
		GreenWireMeta.setDisplayName(ChatColor.GREEN + "Green Wire");
		GreenWire.setItemMeta(GreenWireMeta);
		C4Inv.setItem(4, GreenWire);

		ItemStack YellowWire = new ItemStack(Material.YELLOW_WOOL, 1);
		ItemMeta YellowWireMeta = YellowWire.getItemMeta();
		YellowWireMeta.setDisplayName(ChatColor.RED + "Yellow Wire");
		YellowWire.setItemMeta(YellowWireMeta);
		C4Inv.setItem(5, YellowWire);

		ItemStack BlackWire = new ItemStack(Material.BLACK_WOOL, 1);
		ItemMeta BlackWireMeta = BlackWire.getItemMeta();
		BlackWireMeta.setDisplayName(ChatColor.BLACK + "Black Wire");
		BlackWire.setItemMeta(BlackWireMeta);
		C4Inv.setItem(6, BlackWire);
		return C4Inv;
	}

	/**
	 * 
	 * @param type - type of ammo 1=pistol,2=machinegun,3=sniper,4=deagle,5=shotgun
	 * @param p    - player
	 */
	public static int getAmmoAmount(Player p, int type) {
		if (type == 1) { // pistol
			return p.getInventory().getItem(9).getAmount();
		}
		if (type == 2) { // machinegun
			return p.getInventory().getItem(10).getAmount();
		}
		if (type == 3) {// sniper
			return p.getInventory().getItem(11).getAmount();
		}
		if (type == 4) {// deagle
			return p.getInventory().getItem(12).getAmount();
		}
		if (type == 5) {// shotgun
			return p.getInventory().getItem(13).getAmount();
		}
		return -1;
	}

	public static void useAmmo(Player p, int type) {
		if (type != -1) {
			if (type == 1) { // pistol
				if (p.getInventory().getItem(9).getAmount() > 1) {
					p.getInventory().getItem(9).setAmount(p.getInventory().getItem(9).getAmount() - 1);
				}
			} else if (type == 2) { // machinegun
				if (p.getInventory().getItem(10).getAmount() > 1) {
					p.getInventory().getItem(10).setAmount(p.getInventory().getItem(10).getAmount() - 1);
				}
			} else if (type == 3) {// sniper
				if (p.getInventory().getItem(11).getAmount() > 1) {
					p.getInventory().getItem(11).setAmount(p.getInventory().getItem(11).getAmount() - 1);
				}
			} else if (type == 4) {// deagle
				if (p.getInventory().getItem(12).getAmount() > 1) {
					p.getInventory().getItem(12).setAmount(p.getInventory().getItem(12).getAmount() - 1);
				}
			} else if (type == 5) {// shotgun
				if (p.getInventory().getItem(13).getAmount() > 1) {
					p.getInventory().getItem(13).setAmount(p.getInventory().getItem(13).getAmount() - 1);
				}
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player && cmd.getName().equalsIgnoreCase("weapon")) {
			Player player = (Player) sender;
			if (args.length == 0) {
				sender.sendMessage("Use - /weapon help - for help about the plugin.");
				return true;
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("give")) {
					if (args[1].equalsIgnoreCase("m16")) {
						player.getInventory().addItem(M16Item);
					} else if (args[1].equalsIgnoreCase("mac10")) {
						player.getInventory().addItem(Mac10Item);
					} else if (args[1].equalsIgnoreCase("rifle")) {
						player.getInventory().addItem(RifleItem);
					} else if (args[1].equalsIgnoreCase("shotgun")) {
						player.getInventory().addItem(ShotgunItem);
					} else if (args[1].equalsIgnoreCase("huge")) {
						player.getInventory().addItem(HugeItem);
					} else if (args[1].equalsIgnoreCase("pistol")) {
						player.getInventory().addItem(PistolItem);
					} else if (args[1].equalsIgnoreCase("glock")) {
						player.getInventory().addItem(GlockItem);
					} else if (args[1].equalsIgnoreCase("deagle")) {
						player.getInventory().addItem(DeagleItem);
					} else if (args[1].equalsIgnoreCase("C4")) {
						player.getInventory().addItem(C4);
					} else if (args[1].equalsIgnoreCase("SilencedPistol")) {
						player.getInventory().addItem(SilencedPistolItem);
					} else if (args[1].equalsIgnoreCase("Knife")) {
						player.getInventory().addItem(KnifeItem);
					} else if (args[1].equalsIgnoreCase("Armor")) {
						player.getInventory().addItem(ArmorItem);
					} else if (args[1].equalsIgnoreCase("Radar")) {
						player.getInventory().addItem(RadarItem);
					} else if (args[1].equalsIgnoreCase("NewtonLauncher")) {
						player.getInventory().addItem(NewtonLauncherItem);
					}
					player.getInventory().addItem();
					return true;
				}

			} else if (args.length == 3) {

			} else if (args.length == 3) {

			}
			sender.sendMessage(ChatColor.RED + "Incorrrect usage, type '/weapon help' for help about the plugin");
			return false;
		}
		return false;
	}

	@EventHandler
	public void arrowLand(ProjectileHitEvent e) {
		if (e.getHitEntity() instanceof Player) {
			Player player = (Player) e.getHitEntity();
			// player.damage(M16.getDamage());

		}
		e.getEntity().remove();
	}

	@EventHandler
	public void playerDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getDamager();
			//System.out.println(arrow.getShooter().getClass());
			if (arrow.getShooter() instanceof Player) {
				Player player = (Player) arrow.getShooter();
				ItemStack item = player.getInventory().getItemInMainHand();
				//System.out.println(item.getItemMeta().getDisplayName());
				if (item.getItemMeta().getDisplayName().contains("M16")) {
					//System.out.println(M16.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					double distance = Math.abs(y - y2);
					if (distance <= 0.5D) {
						e.setDamage(M16.getDamage() * 2);
					} else {
						e.setDamage(M16.getDamage());
					}
				}
				if (item.getItemMeta().getDisplayName().contains("Mac10")) {
					//System.out.println(Mac10.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					double distance = Math.abs(y - y2);
					if (distance <= 0.5D) {
						e.setDamage(Mac10.getDamage() * 2);
					} else {
						e.setDamage(Mac10.getDamage());
					}
				}
				if (item.getItemMeta().getDisplayName().contains("Rifle")) {
					//System.out.println(Rifle.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					double distance = Math.abs(y - y2);
					if (distance <= 0.5D) {
						e.setDamage(Rifle.getDamage() * 2);
					} else {
						e.setDamage(Rifle.getDamage());
					}
				}
				if (item.getItemMeta().getDisplayName().contains("Shotgun")) {
					//System.out.println(Shotgun.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					double distance = Math.abs(y - y2);
					if (distance <= 0.5D) {
						e.setDamage(Shotgun.getDamage() * 2);
					} else {
						e.setDamage(Shotgun.getDamage());
					}
				}
				if (item.getItemMeta().getDisplayName().contains("H.U.G.E.")) {
					//System.out.println(Huge.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					double distance = Math.abs(y - y2);
					if (distance <= 0.5D) {
						e.setDamage(Huge.getDamage() * 2);
					} else {
						e.setDamage(Huge.getDamage());
					}
				}
				if (item.getItemMeta().getDisplayName().contains("Pistol")) {
					//System.out.println(Pistol.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					double distance = Math.abs(y - y2);
					if (distance <= 0.5D) {
						e.setDamage(Pistol.getDamage() * 2);
					} else {
						e.setDamage(Pistol.getDamage());
					}
				}
				if (item.getItemMeta().getDisplayName().contains("Deagle")) {
					//System.out.println(Deagle.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					double distance = Math.abs(y - y2);
					if (distance <= 0.5D) {
						e.setDamage(Deagle.getDamage() * 2);
					} else {
						e.setDamage(Deagle.getDamage());
					}
				}
				if (item.getItemMeta().getDisplayName().contains("Glock")) {
					//System.out.println(Glock.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					double distance = Math.abs(y - y2);
					if (distance <= 0.5D) {
						e.setDamage(Glock.getDamage() * 2);
					} else {
						e.setDamage(Glock.getDamage());
					}
				}
				if (item.getItemMeta().getDisplayName().contains("Silenced Pistol")) {
					//System.out.println(SilencedPistol.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					double distance = Math.abs(y - y2);
					if (distance <= 0.5D) {
						e.setDamage(SilencedPistol.getDamage() * 2);
					} else {
						e.setDamage(SilencedPistol.getDamage());
					}
				}
				if (item.getItemMeta().getDisplayName().contains("Newton Launcher")) {
					//System.out.println(NewtonLauncher.getDamage());
					double y = arrow.getLocation().getY();
					double y2 = ((LivingEntity) e.getEntity()).getEyeLocation().getY();
					e.setDamage(NewtonLauncher.getDamage());
					Vector vector = new Vector(arrow.getVelocity().getX() * 2, (arrow.getVelocity().getY() + 2) * 2,
							arrow.getVelocity().getZ() * 2);
					e.getEntity().setVelocity(vector);
				}
			}
			e.getDamager().remove();

		}

	}

	@EventHandler
	public void interactEntity(PlayerInteractAtEntityEvent e) {
		// System.out.print(e.getRightClicked());
		if (e.getRightClicked() instanceof ArmorStand) {
			e.setCancelled(true);
			ArmorStand as = (ArmorStand) e.getRightClicked();
			if (as.getHelmet().equals(new ItemStack(Material.CREEPER_HEAD))) {

				// Open bomb menu
				Inventory inventory = C4Setup(e.getPlayer(), true, false);
				e.getPlayer().openInventory(inventory);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerInteract(PlayerInteractEvent e) {
		Action action = e.getAction();
		if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) { // auto
			// if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.RED
			// + "C4")) {
			// e.getPlayer().getWorld().getBlockAt(e.getPlayer().getLocation()).setType(Material.CREEPER_HEAD);

			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("M16")) {
				M16.shoot(e.getPlayer(), this);
				Huge.updateName(e.getPlayer().getInventory().getItemInMainHand(),ChatColor.GRAY+"M16",e.getPlayer());
			}
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Mac10")) {
				Mac10.shoot(e.getPlayer(), this);
				Huge.updateName(e.getPlayer().getInventory().getItemInMainHand(),ChatColor.GRAY+"Mac10",e.getPlayer());
			}
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Rifle")) {
				Rifle.shoot(e.getPlayer(), this);
				Huge.updateName(e.getPlayer().getInventory().getItemInMainHand(),ChatColor.GRAY+"Rifle",e.getPlayer());
			}
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Shotgun")) {
				Shotgun.shoot(e.getPlayer(), this);
				Shotgun.shoot(e.getPlayer(), this);
				Shotgun.shoot(e.getPlayer(), this);
				Shotgun.shoot(e.getPlayer(), this);
				Shotgun.shoot(e.getPlayer(), this);
				Huge.updateName(e.getPlayer().getInventory().getItemInMainHand(),ChatColor.GRAY+"Shotgun",e.getPlayer());
			}
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("H.U.G.E. 249")) {
				Huge.shoot(e.getPlayer(), this);
				Huge.updateName(e.getPlayer().getInventory().getItemInMainHand(),ChatColor.GRAY+"H.U.G.E. 249",e.getPlayer());
			}
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Glock")) {
				Glock.shoot(e.getPlayer(), this);
				Huge.updateName(e.getPlayer().getInventory().getItemInMainHand(),ChatColor.GRAY+"Glock",e.getPlayer());
			}
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Silenced Pistol")) {
				SilencedPistol.shoot(e.getPlayer(), this);
				Huge.updateName(e.getPlayer().getInventory().getItemInMainHand(),ChatColor.RED+"Silenced Pistol",e.getPlayer());
			}
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Pistol")) {
				Pistol.shoot(e.getPlayer(), this);
				Huge.updateName(e.getPlayer().getInventory().getItemInMainHand(),ChatColor.GRAY+"Pistol",e.getPlayer());
			}
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Deagle")) {
				Deagle.shoot(e.getPlayer(), this);
				Huge.updateName(e.getPlayer().getInventory().getItemInMainHand(),ChatColor.GRAY+"Deagle",e.getPlayer());
			}

			if (e.getPlayer().getInventory().getItemInMainHand().equals(NewtonLauncherItem)) {
				NewtonLauncher.shoot(e.getPlayer(), this);
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(RadarItem)) {
				// e.getPlayer().getInventory().clear(e.getPlayer().getInventory().getHeldItemSlot());
				new BukkitRunnable() {
					@Override
					public void run() {
						e.getPlayer().sendMessage(ChatColor.GRAY + "Radar: ");
						for (Player player : Bukkit.getServer().getOnlinePlayers()) {

							e.getPlayer()
									.sendMessage(ChatColor.GRAY + "" + player.getLocation().getBlockX() + ", "
											+ player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ()
											+ ", ");
						}
					}
				}.runTaskTimer(this, 0, 800);
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(ArmorItem)) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 1000000, 2));
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 3, 0));
				e.getPlayer().getInventory().clear(e.getPlayer().getInventory().getHeldItemSlot());
			}
		}else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) { 
			if (e.getPlayer().getInventory().getItemInMainHand().equals(M16Item)) {
				M16.reload();
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(Mac10Item)) {
				Mac10.reload();
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(RifleItem)) {
				Rifle.reload();
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(ShotgunItem)) {
				Shotgun.reload();
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(HugeItem)) {
				Huge.reload();
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(GlockItem)) {
				Glock.reload();
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(PistolItem)) {
				Pistol.reload();
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(DeagleItem)) {
				Deagle.reload();
			}
			if (e.getPlayer().getInventory().getItemInMainHand().equals(SilencedPistolItem)) {
				SilencedPistol.reload();
			}
		}

	}

	@EventHandler
	public void sneak(PlayerToggleSneakEvent e) {

		if (e.isSneaking() && e.getPlayer().getInventory().getItemInMainHand().equals(M16Item)
				|| e.getPlayer().getInventory().getItemInMainHand().equals(Mac10Item)
				|| e.getPlayer().getInventory().getItemInMainHand().equals(RifleItem)
				|| e.getPlayer().getInventory().getItemInMainHand().equals(HugeItem)
				|| e.getPlayer().getInventory().getItemInMainHand().equals(ShotgunItem)
				|| e.getPlayer().getInventory().getItemInMainHand().equals(DeagleItem)
				|| e.getPlayer().getInventory().getItemInMainHand().equals(PistolItem)
				|| e.getPlayer().getInventory().getItemInMainHand().equals(GlockItem)) {

			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 3));
		}
		if (!e.isSneaking()) {
			e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
		}
	}

	@EventHandler
	public void eggThrow(PlayerEggThrowEvent event) {
		event.setHatching(false);
		final Item grenade = event.getPlayer().getWorld().dropItem(event.getEgg().getLocation(),
				new ItemStack(Material.EGG));
		grenade.setVelocity(event.getPlayer().getEyeLocation().getDirection());
		grenade.getLocation().add(event.getEgg().getVelocity());
		grenade.getWorld().playSound(grenade.getLocation(), Sound.ENTITY_CREEPER_HURT, 1, 1);
		grenade.setPickupDelay(41);
		new BukkitRunnable() {
			@Override
			public void run() {
				grenade.getWorld().createExplosion(grenade.getLocation(), 4, false);
				grenade.remove();
			}
		}.runTaskLater(this, 40);

	}

	public ArmorStand getNearestC4(Player player) {
		double distance = 1000000;
		ArmorStand closest = null;
		Location playerLoc = player.getLocation();
		for (ArmorStand as : c4List) {
			if (playerLoc.distance(as.getLocation()) < distance) {
				distance = playerLoc.distance(as.getLocation());
				closest = as;
			}
		}
		return closest;
	}

	public void explodeC4(ArmorStand as) {
		as.setCustomName(ChatColor.RED + "Boom!");
	}

	public void disarmC4(ArmorStand as) {
		as.setCustomName(ChatColor.BLUE + "Disarmed");
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) (event.getWhoClicked());
		int slot = event.getSlot();
		//System.out.println(slot);
		if (event.getInventory().equals(C4Inv)) {
			// player.sendMessage("You Clicked " + slot);
			player.closeInventory();
			if (slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6) {
				ArmorStand as = getNearestC4(player);
				if (as != null) {
					int rand = randomNum(1, 5);
					if (rand == 5) {
						explodeC4(as);
					} else {
						disarmC4(as);
					}
				}
			}
			event.setCancelled(true);
		}

	}

	public void setupBomb(ArmorStand as) {
		new BukkitRunnable() {
			int timer = 45;

			@Override
			public void run() {

				if (as.getCustomName().contains("Boom") || timer == 0) {
					as.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, as.getLocation(), 100, 15, 5, 15);
					as.getWorld().playSound(as.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 0.5f);
					as.getWorld().playSound(as.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 1f);
					as.getWorld().playSound(as.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 1.7f);
					as.getWorld().playSound(as.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 0.8f);
					as.getWorld().playSound(as.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 1.3f);
					c4List.remove(as);
					for (Entity entity : as.getWorld().getEntities()) {
						if (entity instanceof LivingEntity) {
							LivingEntity LE = (LivingEntity) entity;
							if (LE.getLocation().distance(as.getLocation()) <= 15) {
								LE.damage(30);
							} else if (LE.getLocation().distance(as.getLocation()) <= 30) {
								LE.damage(15);
							}
						}
					}
					as.remove();
					cancel();

				} else if (as.getCustomName().contains("Disarmed")) {
					c4List.remove(as);
					as.remove();
					cancel();

				} else {
					timer--;
					if (timer < 10) {
						as.setCustomName(ChatColor.RED + "C4: 00:0" + timer);
					} else {
						as.setCustomName(ChatColor.RED + "C4: 00:" + timer);
					}
					as.getWorld().playSound(as.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 3, 2);
				}
			}
		}.runTaskTimer(this, 0, 20);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {

		if (e.getItemDrop().getItemStack().equals(C4)) {
			e.getItemDrop().setPickupDelay(50);
			new BukkitRunnable() {
				@Override
				public void run() {
					Location loc = new Location(e.getPlayer().getWorld(), e.getItemDrop().getLocation().getX(),
							e.getItemDrop().getLocation().getY() - 1.5, e.getItemDrop().getLocation().getZ());
					ArmorStand as = e.getPlayer().getWorld().spawn(loc, ArmorStand.class);
					e.getItemDrop().remove();
					as.setHelmet(new ItemStack(Material.CREEPER_HEAD));
					as.setCustomName(ChatColor.RED + "C4: 00:45");
					as.setCustomNameVisible(true);
					as.setAI(false);
					as.setInvulnerable(true);
					as.setGravity(false);
					as.setVisible(false);
					as.setCustomNameVisible(true);
					e.getPlayer().getScoreboard().getTeam("Traitor").addEntry(as.getCustomName());
					// bukkit runnable
					c4List.add(as);
					setupBomb(as);

				}
			}.runTaskLater(this, 40);

		}
	}

	private static int randomNum(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public void helpMenu(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + "Help Menu for LootGenerator plugin");
		sender.sendMessage(ChatColor.YELLOW + "------------------------------");
		sender.sendMessage(ChatColor.YELLOW + "Commands:");
		sender.sendMessage(
				ChatColor.YELLOW + "/weapon help " + ChatColor.GRAY + "-" + ChatColor.WHITE + " Shows you this menu.");
		sender.sendMessage(
				ChatColor.YELLOW + "/weapon give " + ChatColor.GRAY + "-" + ChatColor.WHITE + " Gives you a weapon.");
		sender.sendMessage(
				ChatColor.YELLOW + "/weapon list " + ChatColor.GRAY + "-" + ChatColor.WHITE + " Lists the weapons.");

	}
}
