package com.finderfeed.solarforge.events.hotkey_use_event;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForgeClientRegistry;

import com.finderfeed.solarforge.client.screens.ThreeDStructureViewScreen;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.packet_handler.packets.ResetAllAbilitiesPacket;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.CastAbilityPacket;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen.SolarForgeAbilityConfigScreen;
import com.finderfeed.solarforge.packet_handler.packets.RequestAbilityScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.event.InputEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class HotkeyEventListener {



    @SubscribeEvent
    public static void ListenToEvent(final InputEvent.KeyInputEvent event){


        if (SolarForgeClientRegistry.FIRST_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SolarForgePacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(1));

        }
        if (SolarForgeClientRegistry.SECOND_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SolarForgePacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(2));

        }
        if (SolarForgeClientRegistry.THIRD_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SolarForgePacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(3));

        }
        if (SolarForgeClientRegistry.FORTH_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SolarForgePacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(4));

        }
        if (SolarForgeClientRegistry.ADMIN_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SolarForgePacketHandler.INSTANCE.sendToServer(new ResetAllAbilitiesPacket());

        }
        if (SolarForgeClientRegistry.OPEN_GUI_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            Minecraft.getInstance().setScreen(new SolarForgeAbilityConfigScreen());

        }

        if (SolarForgeClientRegistry.GUI_ABILITY_BUY_SCREEN.isDown() && event.getAction() == GLFW.GLFW_PRESS){
            SolarForgePacketHandler.INSTANCE.sendToServer(new RequestAbilityScreen(false));
        }


    }


}
