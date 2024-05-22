package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.config.SCItemConfig;
import com.finderfeed.solarcraft.content.entities.projectiles.SolarGodBowProjectile;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftSword;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.IUpgradable;
import com.finderfeed.solarcraft.registries.SCConfigs;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.world.item.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SolarGodSword extends RareSolarcraftSword implements IUpgradable {

    public static final int UPGRADE_COUNT = 4;

    public SolarGodSword(Tier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_,fragmentSupplier);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack inhand = player.getItemInHand(hand);
            int level = getItemLevel(inhand);
            if (level >= 2) {
                SolarGodBowProjectile projectile = new SolarGodBowProjectile(SCEntityTypes.SOLAR_GOD_BOW_PROJECTILE.get(),world);
                projectile.setPos(player.position().add(0, player.getEyeHeight(), 0));
                if (level >= 3){
                    projectile.setDamage(SCConfigs.ITEMS.solarGodSwordProjectileDamage + SCConfigs.ITEMS.solarGodSwordProjectileDamageBonus);
                }else{
                    projectile.setDamage(SCConfigs.ITEMS.solarGodSwordProjectileDamage);
                }
                if (level >= 4){
                    projectile.setExplosionPower(SCConfigs.ITEMS.solarGodSwordProjectileExplosionPower);
                }
                projectile.setDeltaMovement(player.getLookAngle().multiply(2,2,2));
                projectile.setOwner(player);
                projectile.setProjectileLevel(0);
                world.addFreshEntity(projectile);
                player.getCooldowns().addCooldown(this,SCConfigs.ITEMS.solarGodSwordCooldown);
                return InteractionResultHolder.success(player.getItemInHand(hand));
            }


        }
        return super.use(world,player,hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> text, TooltipFlag flag) {
        addComponents(stack,text);
        super.appendHoverText(stack, world, text, flag);
    }

    @Override
    public String getUpgradeTagString() {
        return SolarCraftTags.SOLAR_GOD_SWORD_TAG;
    }

    @Override
    public int getMaxUpgrades() {
        return UPGRADE_COUNT;
    }

    @Override
    public List<Component> getUpgradeDescriptions() {
        return List.of(
                Component.translatable("solarcraft.solar_god_sword_level_2","%.1f".formatted(SCConfigs.ITEMS.solarGodSwordMagicDamageBonus)),
                Component.translatable("solarcraft.solar_god_sword_level_3",
                        "%.1f".formatted(SCConfigs.ITEMS.solarGodSwordProjectileDamage),
                        "%d".formatted(SCConfigs.ITEMS.solarGodSwordCooldown / 20)),
                Component.translatable("solarcraft.solar_god_sword_level_4","%.1f".formatted(SCConfigs.ITEMS.solarGodSwordProjectileDamageBonus)),
                Component.translatable("solarcraft.solar_god_sword_level_5")
        );
    }
}
