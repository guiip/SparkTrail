package io.github.dsh105.sparktrail.particle.type;

import io.github.dsh105.sparktrail.particle.EffectHolder;
import io.github.dsh105.sparktrail.particle.PacketEffect;
import io.github.dsh105.sparktrail.particle.ParticleType;


public class Snowball extends PacketEffect {

    public Snowball(EffectHolder effectHolder, ParticleType particleType) {
        super(effectHolder, particleType);
    }

    @Override
    public String getNmsName() {
        return "snowballpoof";
    }

    @Override
    public float getSpeed() {
        return 1F;
    }

    @Override
    public int getParticleAmount() {
        return 20;
    }
}