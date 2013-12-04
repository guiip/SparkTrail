package io.github.dsh105.sparktrail.particle;

import io.github.dsh105.sparktrail.particle.type.Critical;
import io.github.dsh105.sparktrail.particle.type.Potion;
import io.github.dsh105.sparktrail.particle.type.Smoke;
import io.github.dsh105.sparktrail.particle.type.Swirl;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;

import java.util.UUID;


public class ParticleDetails {

    private ParticleType particleType;
    private UUID uuid;
    private String playerName;

    public Integer blockId = 1;
    public Integer blockMeta = 0;
    public Critical.CriticalType criticalType = Critical.CriticalType.NORMAL;
    public FireworkEffect fireworkEffect = FireworkEffect.builder().withColor(Color.WHITE).with(FireworkEffect.Type.BALL).withFade(Color.WHITE).build();
    //public Note.NoteType noteType = Note.NoteType.GREEN;
    public Potion.PotionType potionType = Potion.PotionType.AQUA;
    public Smoke.SmokeType smokeType = Smoke.SmokeType.BLACK;
    public Swirl.SwirlType swirlType = Swirl.SwirlType.WHITE;

    public ParticleDetails(ParticleType particleType) {
        this.particleType = particleType;
    }

    public void setMob(UUID uuid) {
        this.uuid = uuid;
    }

    public void setPlayer(String name, UUID uuid) {
        this.uuid = uuid;
        this.playerName = name;
    }

    public ParticleType getParticleType() {
        return this.particleType;
    }

    public Object[] getDetails() {
        Object[] o = new Object[]{};
        if (particleType == ParticleType.BLOCKBREAK) {
            return new Object[]{this.blockId, this.blockMeta};
        }
        if (particleType == ParticleType.CRITICAL) {
            return new Object[]{this.criticalType};
        } else if (particleType == ParticleType.FIREWORK) {
            return new Object[]{this.fireworkEffect};
        }
        /*else if (particleType == ParticleType.NOTE) {
            return new Object[] {this.noteType};
		}*/
        else if (particleType == ParticleType.POTION) {
            return new Object[]{this.potionType};
        } else if (particleType == ParticleType.SMOKE) {
            return new Object[]{this.smokeType};
        } else if (particleType == ParticleType.SWIRL) {
            return new Object[]{this.swirlType, this.uuid};
        }
        return o;
    }
}