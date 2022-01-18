package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.primitive.RareSolarcraftBlockItem;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class RadiantCoreBlockItem extends RareSolarcraftBlockItem {
    public RadiantCoreBlockItem(Block p_40565_, Properties p_40566_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_40565_, p_40566_,fragmentSupplier);
    }

//    @Override
//    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
////        if (!p_41405_.isClientSide && p_41406_ instanceof Player pl){
////            Helpers.fireProgressionEvent(pl, Progression.RADIANT_LAND);
////        }
//        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
//    }
}
