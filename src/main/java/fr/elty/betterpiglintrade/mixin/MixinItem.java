package fr.elty.betterpiglintrade.mixin;

import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IForgeItem.class)
public interface MixinItem {

    @Overwrite(remap = false)
    default boolean isPiglinCurrency(ItemStack stack)
    {
        Item item = stack.getItem();
        return item == PiglinTasks.field_234444_a_ || item == Items.GOLD_BLOCK || item == Items.GOLDEN_APPLE ||
               item == Items.GOLDEN_AXE || item == Items.GOLDEN_HELMET || item == Items.GOLDEN_CHESTPLATE ||
               item == Items.GOLDEN_LEGGINGS || item == Items.GOLDEN_BOOTS || item == Items.GOLDEN_HOE ||
               item == Items.GOLDEN_PICKAXE || item == Items.GOLDEN_SWORD || item == Items.GOLDEN_SHOVEL;
    }

}
