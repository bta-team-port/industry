package teamport.industry.core.recipe;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.ItemStack;
import teamport.industry.core.recipe.entry.RecipeEntryMachine;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class IndRecipes implements RecipeEntrypoint {
    public static RecipeNamespace INDUSTRY;
    public static RecipeGroup<RecipeEntryCrafting<?,?>> WORKBENCH;
    public static RecipeGroup<RecipeEntryMachine> MACERATOR;

    @Override
    public void onRecipesReady() {
        resetGroups();
        registerNamespaces();
        load();
    }

    @Override
    public void initNamespaces() {
        resetGroups();
        registerNamespaces();
    }

    public void resetGroups() {
        Registries.RECIPES.unregister("signalindustries");
        WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Blocks.WORKBENCH)));
        INDUSTRY = new RecipeNamespace();
    }

    public void load() {
        DataLoader.loadRecipesFromFile("/assets/industry/recipes/workbench_items.json");
    }

    public void registerNamespaces() {
        INDUSTRY.register("workbench", WORKBENCH);
        Registries.RECIPES.register("industry", INDUSTRY);
    }
}
