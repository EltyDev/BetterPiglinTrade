package fr.venodez.betterpiglintrade.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.List;

@Mixin(PiglinTasks.class)
public abstract class MixinPiglinTasks {


    @Invoker("func_234475_a_")
    public static void throwItems(PiglinEntity p_234475_0_, List<ItemStack> p_234475_1_) {
        throw  new AssertionError();
    }

    @Invoker("func_234524_k_")
    public static List<ItemStack> getBarterResponseItems(PiglinEntity p_234524_0_) {
        throw  new AssertionError();
    }

    private static IRecipe getRecipe(ItemStack itemStack) {
        Collection<IRecipe<?>> recipes = Minecraft.getInstance().world.getRecipeManager().getRecipes();
        for (IRecipe recipe : recipes) {
            if (recipe.getRecipeOutput().isItemEqual(itemStack))
                return recipe;
        }
        return null;
    }

    private static int getGoldCost(ItemStack itemStack) {
        int cost = 0;
        IRecipe recipe = getRecipe(itemStack);
        if (recipe == null)
            return cost;
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        for (Object ingredient : ingredients) {
            ItemStack[] matchingStacks = ((Ingredient) ingredient).getMatchingStacks();
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

    @Inject(method = "func_234477_a_", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/monster/piglin/PiglinTasks;func_234475_a_(Lnet/minecraft/entity/monster/piglin/PiglinEntity;Ljava/util/List;)V"), remap = false, locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void stopHoldingOffHandItem(PiglinEntity p_234477_0_, boolean p_234477_1_, CallbackInfo ci, ItemStack itemstack) {
        if (itemstack.getItem() != Items.GOLD_INGOT) {
            int cost = getGoldCost(itemstack);
            for (int i = 0; i < cost - 1; ++i)
                throwItems(p_234477_0_, getBarterResponseItems(p_234477_0_));
            p_234477_0_.func_233665_g_(itemstack);
        }
    }

}
