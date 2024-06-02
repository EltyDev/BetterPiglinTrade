package fr.elty.betterpiglintrade.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.List;

@Mixin(PiglinAi.class)
public abstract class MixinPiglinAi {


    @Invoker("throwItems")
    public static void throwItems(Piglin p_34861_, List<ItemStack> p_34862_) {
        throw  new AssertionError();
    }

    @Invoker("getBarterResponseItems")
    public static List<ItemStack> getBarterResponseItems(Piglin p_34997_) {
        throw  new AssertionError();
    }

    private static Recipe getRecipe(Level level, ItemStack itemStack) {
        Collection<Recipe<?>> recipes = level.getRecipeManager().getRecipes();
        for (Recipe recipe : recipes) {
            if (recipe.getResultItem().equals(itemStack, false))
                return recipe;
        }
        return null;
    }

    private static int getGoldCost(Level level, ItemStack itemStack) {
        int cost = 0;
        Recipe recipe = getRecipe(level, itemStack);
        if (recipe == null)
            return cost;
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        for (Object ingredient : ingredients) {
            ItemStack[] matchingStacks = ((Ingredient) ingredient).getItems();
            for (ItemStack ingredientItem : matchingStacks) {
                Item item = ingredientItem.getItem();
                if (Items.GOLD_INGOT.equals(item))
                    ++cost;
                else if (Items.GOLD_BLOCK.equals(item))
                    cost += 9;
            }
        }
        return cost;
    }

    @Inject(method = "stopHoldingOffHandItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/piglin/PiglinAi;throwItems(Lnet/minecraft/world/entity/monster/piglin/Piglin;Ljava/util/List;)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void stopHoldingOffHandItem(Piglin p_34868_, boolean p_34869_, CallbackInfo ci, ItemStack itemstack) {
        if (itemstack.getItem() != Items.GOLD_INGOT) {
            int cost = getGoldCost(p_34868_.getLevel(), itemstack);
            for (int i = 0; i < cost - 1; ++i)
                throwItems(p_34868_, getBarterResponseItems(p_34868_));
            p_34868_.equipItemIfPossible(itemstack);
        }
    }

}
