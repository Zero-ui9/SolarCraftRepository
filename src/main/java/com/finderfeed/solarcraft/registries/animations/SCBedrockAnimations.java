package com.finderfeed.solarcraft.registries.animations;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.other.delayed_getter.DelayedObjectGetter;
import com.finderfeed.solarcraft.local_library.other.delayed_getter.DelayedObjectGetterManager;
import net.minecraft.resources.ResourceLocation;

public class SCBedrockAnimations {

    public static final DelayedObjectGetterManager<Animation> ANIMATIONS = new DelayedObjectGetterManager<>();

    public static final DelayedObjectGetter<Animation> ULDERA_CRYSTAL_IDLE = ANIMATIONS.define(new ResourceLocation(SolarCraft.MOD_ID,"uldera_crystal_idle"));
}
