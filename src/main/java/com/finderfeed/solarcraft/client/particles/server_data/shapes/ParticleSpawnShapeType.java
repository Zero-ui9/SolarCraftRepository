package com.finderfeed.solarcraft.client.particles.server_data.shapes;

import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.BurstAttackParticleShape;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.SphereParticleShape;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Consumer;
import java.util.function.Function;

public enum ParticleSpawnShapeType {
    BURST_ATTACK_SHAPE(BurstAttackParticleShape.SERIALIZER),
    SPHERE_SHAPE(SphereParticleShape.SERIALIZER);

    private ParticleSpawnShapeSerializer<?> serializer;
    ParticleSpawnShapeType(ParticleSpawnShapeSerializer<?> serializer){
        this.serializer = serializer;
    }

    public ParticleSpawnShapeSerializer<?> getSerializer() {
        return serializer;
    }
}