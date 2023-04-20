package fr.venodez.betterpiglintrade.mixin;

import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IForgeItem.class)
public interface MixinItem {

    @Overwrite(remap = false)
    default boolean isPiglinCurrency(ItemStack stack)
    {
        Item item = stack.getItem();
        return item == PiglinAi.BARTERING_ITEM || item == Items.GOLD_BLOCK || item == Items.GOLDEN_APPLE ||
               item == Items.GOLDEN_AXE || item == Items.GOLDEN_HELMET || item == Items.GOLDEN_CHESTPLATE ||
               item == Items.GOLDEN_LEGGINGS || item == Items.GOLDEN_BOOTS || item == Items.GOLDEN_HOE ||
               item == Items.GOLDEN_PICKAXE || item == Items.GOLDEN_SWORD || item == Items.GOLDEN_SHOVEL;
    }

}
