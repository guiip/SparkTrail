package com.github.dsh105.sparktrail.particle;

import com.github.dsh105.sparktrail.SparkTrail;
import com.github.dsh105.sparktrail.config.options.ConfigOptions;
import com.github.dsh105.sparktrail.data.EffectCreator;
import com.github.dsh105.sparktrail.data.EffectHandler;
import com.github.dsh105.sparktrail.logger.Logger;
import com.github.dsh105.sparktrail.particle.Effect;
import com.github.dsh105.sparktrail.particle.type.*;
import com.github.dsh105.sparktrail.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Project by DSH105
 */

public class EffectHolder extends BukkitRunnable {

	private HashSet<Effect> effects = new HashSet<Effect>();
	private BukkitTask task = null;
	protected EffectType effectType;
	protected EffectDetails details;

	public World world;
	public int locX;
	public int locY;
	public int locZ;

	public EffectHolder(EffectType effectType) {
		this.effectType = effectType;
		this.details = new EffectDetails(effectType);
	}

	public void setEffects(HashSet<Effect> effects) {
		this.effects = effects;
	}

	public HashSet<Effect> getEffects() {
		return this.effects;
	}

	public EffectType getEffectType() {
		return this.effectType;
	}

	public void addEffect(ParticleType particleType) {
		if (this.effectType == EffectType.PLAYER) {
			ParticleDetails pd = new ParticleDetails(particleType);
			pd.setPlayer(this.details.playerName, this.details.mobUuid);
			this.effects.add(EffectCreator.createEffect(this, particleType, pd.getDetails()));
			return;
		}
		else if (this.effectType == EffectType.LOCATION) {
			ParticleDetails pd = new ParticleDetails(particleType);
			this.effects.add(EffectCreator.createEffect(this, particleType, pd.getDetails()));
			return;
		}
		else if (this.effectType == EffectType.MOB) {
			ParticleDetails pd = new ParticleDetails(particleType);
			pd.setMob(this.details.mobUuid);
			this.effects.add(EffectCreator.createEffect(this, particleType, pd.getDetails()));
			return;
		}
	}

	public void addEffect(ParticleDetails particleDetails) {
		this.effects.add(EffectCreator.createEffect(this, particleDetails.getParticleType(), particleDetails.getDetails()));
	}

	public void removeEffect(ParticleType particleType) {
		Iterator<Effect> i = this.effects.iterator();
		while (i.hasNext()) {
			Effect e = i.next();
			if (e.getParticleType().equals(particleType)) {
				i.remove();
			}
		}
	}

	public void removeEffect(ParticleDetails particleDetails) {
		Iterator<Effect> i = this.effects.iterator();
		while (i.hasNext()) {
			Effect e = i.next();
			if (e.getParticleType().equals(particleDetails.getParticleType())) {
				ParticleType pt = e.getParticleType();
				if (pt == ParticleType.BLOCKBREAK) {
					if (particleDetails.blockId == ((BlockBreak) e).idValue && particleDetails.blockMeta == ((BlockBreak) e).metaValue) {
						i.remove();
					}
				}
				else if (pt == ParticleType.FIREWORK) {
					i.remove();
				}
				else if (pt == ParticleType.NOTE) {
					if (particleDetails.noteType.equals(((Note) e).noteType)) {
						i.remove();
					}
				}
				else if (pt == ParticleType.POTION) {
					if (particleDetails.potionType.equals(((Potion) e).potionType)) {
						i.remove();
					}
				}
				else if (pt == ParticleType.SMOKE) {
					if (particleDetails.smokeType.equals(((Smoke) e).smokeType)) {
						i.remove();
					}
				}
				else if (pt == ParticleType.SWIRL) {
					if (particleDetails.swirlType.equals(((Swirl) e).swirlType)) {
						i.remove();
					}
				}
				else {
					i.remove();
				}
			}
		}
	}

	public Location getLocation() {
		return new Location(this.world, this.locX, this.locY, this.locZ);
	}

	public void updateLocation(Location l) {
		this.updateLocation(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
	}

	public void updateLocation(World world, int x, int y, int z) {
		this.world = world;
		this.locX = x;
		this.locY = y;
		this.locZ = z;
	}

	public EffectDetails getDetails() {
		return this.details;
	}

	public void start() {
		this.task = this.runTaskTimer(SparkTrail.getInstance(), 0L, ConfigOptions.instance.maxTick);
	}

	public void run() {
		update();
		Iterator<Effect> i = this.effects.iterator();
		while (i.hasNext()) {
			Effect e = i.next();
			if (e.getParticleType().getIncompatibleTypes().contains(this.effectType)) {
				i.remove();
				continue;
			}
			if (ConfigOptions.instance.maxTick % e.getParticleType().getFrequency() == 0) {
				e.play();
			}
		}
	}

	private void update() {
		if (this.effectType == EffectType.PLAYER) {
			Player p = Bukkit.getPlayerExact(this.details.playerName);
			if (p == null) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered missing player (Name: " + this.details.playerName + "). Removing particle effect.", true);
				EffectHandler.getInstance().remove(this);
				return;
			}
			Location l = p.getLocation();
			if (!this.world.equals(l.getWorld())) {
				this.world = l.getWorld();
			}
			if (!(this.locX == l.getBlockX())) {
				this.locX = l.getBlockX();
			}
			if (!(this.locY == l.getBlockY())) {
				this.locY = l.getBlockY();
			}
			if (!(this.locZ == l.getBlockZ())) {
				this.locZ = l.getBlockZ();
			}
		}
		else if (this.effectType == EffectType.MOB) {
			Entity e = null;
			for (Entity entity : this.world.getEntities()) {
				if (entity.getUniqueId().equals(this.details.mobUuid)) {
					e = entity;
				}
			}
			if (e == null) {
				Logger.log(Logger.LogLevel.WARNING, "Encountered missing entity (UUID: " + this.details.mobUuid + "). Removing particle effect. Maybe it despawned?", true);
				EffectHandler.getInstance().remove(this);
				return;
			}
			Location l = e.getLocation();
			if (!this.world.equals(l.getWorld())) {
				this.world = l.getWorld();
			}
			if (!(this.locX == l.getBlockX())) {
				this.locX = l.getBlockX();
			}
			if (!(this.locY == l.getBlockY())) {
				this.locY = l.getBlockY();
			}
			if (!(this.locZ == l.getBlockZ())) {
				this.locZ = l.getBlockZ();
			}
		}
	}

	public void stop() {
		this.task.cancel();
	}

	public enum EffectType {

		LOCATION, PLAYER, MOB;
	}
}