package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.magic_items.items.isters.ShieldOfSolarGodISTER;
import com.finderfeed.solarforge.magic_items.items.primitive.RareSolarcraftShieldItem;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.TooltipFlag;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.ShieldItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.client.model.pipeline.VertexBufferConsumer;

import javax.annotation.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item.Properties;

public class ShieldOfSolarGod extends RareSolarcraftShieldItem {



    public ShieldOfSolarGod(Properties p_i48470_1_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48470_1_,fragmentSupplier);
    }




    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity player, int idk) {

        if (!world.isClientSide) {
            float damage = 0;
            int usingTime = (72000 - idk)/20;
            if (usingTime >= 5 && usingTime < 10){
                damage = 3.5f;
            }else if (usingTime >= 10 && usingTime < 15) {
                damage = 5.5f;
            }else if (usingTime >= 15 && usingTime < 20){
                damage = 7.5f;
            }else if (usingTime >= 20 && usingTime < 30){
                damage = 9.5f;
            }else if (usingTime >= 30){
                damage = 12;
            }

            List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, new AABB(-5, -3, -5, 5, 3, 5)
                    .move(player.position().x , player.position().y , player.position().z ));
            list.remove(player);
            for (LivingEntity ent : list) {
                if (damage >= 7.5) {
                    Vec3 vec = player.position();
                    Vec3 vecPosTarget = ent.position();
                    Vec3 velocity = new Vec3(vecPosTarget.x- vec.x,vecPosTarget.y- vec.y,vecPosTarget.z- vec.z).normalize();
                    ent.push(velocity.x*4,velocity.y*4,velocity.z*4);
                }

                ent.hurt(DamageSource.mobAttack(player).bypassArmor().setMagic(), damage);
                ent.setSecondsOnFire(5);
                ((ServerLevel)world).sendParticles(ParticleTypes.FLAME,ent.getX(),ent.getY()+0.5f,ent.getZ(),20,0,0.02,0,0.1);


            }
            if (damage == 12){
                ((ServerLevel)world).playSound(null,player, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.AMBIENT,0.3f,0.5f);
            }
           // stack.getTagElement("current_damage").putInt("damage_", 0);

        }

        super.releaseUsing(stack, world, player, idk);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslatableComponent("solarforge.solar_shield_of_god").withStyle(ChatFormatting.GOLD));
        p_77624_3_.add(new TextComponent("5-10 : 3.5").withStyle(ChatFormatting.GOLD));
        p_77624_3_.add(new TextComponent("10-15 : 5.5").withStyle(ChatFormatting.GOLD));
        p_77624_3_.add(new TextComponent("15-20 : 7.5").withStyle(ChatFormatting.GOLD));
        p_77624_3_.add(new TextComponent("20-30 : 9.5").withStyle(ChatFormatting.GOLD));
        p_77624_3_.add(new TextComponent("30+ : 12").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {

        float damage = 0;
        int usingTime = (72000 - count)/20;
        if (usingTime >= 5 && usingTime < 10){
            damage = 3.5f;
        }else if (usingTime >= 10 && usingTime < 15) {
            damage = 5.5f;
        }else if (usingTime >= 15 && usingTime < 20){
            damage = 7.5f;
        }else if (usingTime >= 20 && usingTime < 30){
            damage = 9.5f;
        }else if (usingTime >= 30){
            damage = 12;
        }
        ((Player)player).displayClientMessage(new TextComponent("-"+ (int) Math.floor((float) (72000 - count) / 20) +"->"+damage+"-").withStyle(ChatFormatting.GOLD),true);
        super.onUsingTick(stack, player, count);
    }


    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(RenderProp.INSTANCE);

    }
}

class RenderProp implements IItemRenderProperties{

    public static RenderProp INSTANCE = new RenderProp();

    @Override
    public Font getFont(ItemStack stack) {
        return Minecraft.getInstance().font;
    }

    @Override
    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
        return new ShieldOfSolarGodISTER(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    }
}
