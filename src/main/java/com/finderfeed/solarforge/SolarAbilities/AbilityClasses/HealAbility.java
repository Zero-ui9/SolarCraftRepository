package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

public class HealAbility extends AbstractAbility{
    public HealAbility() {
        super("solar_heal",250,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.ZETA,200)
        .addRunicEnergy(RunicEnergy.Type.URBA,350)
        .addRunicEnergy(RunicEnergy.Type.ARDO,400));
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        super.cast(entity, world);
        if (allowed) {
            entity.heal(4);
        }

    }
}