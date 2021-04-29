package io.github.how_bout_no.outvoted.init;

import io.github.how_bout_no.outvoted.Outvoted;
import io.github.how_bout_no.outvoted.recipe.RepairCostRecipe;
import io.github.how_bout_no.outvoted.recipe.WildfireShieldDecorationRecipe;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(Outvoted.MOD_ID, Registry.RECIPE_SERIALIZER_KEY);

    public static final RegistrySupplier<SpecialRecipeSerializer<RepairCostRecipe>> REPAIR_COST = RECIPES.register("repair_cost", () -> new SpecialRecipeSerializer<>(RepairCostRecipe::new));
    public static final RegistrySupplier<SpecialRecipeSerializer<WildfireShieldDecorationRecipe>> SHIELD_DECO = RECIPES.register("wildfire_shield_deco", () -> new SpecialRecipeSerializer<>(WildfireShieldDecorationRecipe::new));
}