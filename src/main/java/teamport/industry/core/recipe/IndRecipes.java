package teamport.industry.core.recipe;

import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.ItemStack;
import teamport.industry.Industry;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.recipe.entry.RecipeEntryMacerator;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

/**
 * Recipe registration and creation
 * @author Cookie
 * @date 2024-12-23
 */
public class IndRecipes implements RecipeEntrypoint {
    public static RecipeNamespace INDUSTRY;
    public static RecipeGroup<RecipeEntryMacerator> MACERATOR;

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
        INDUSTRY = new RecipeNamespace();
        MACERATOR = new RecipeGroup<>(new RecipeSymbol(new ItemStack(IndBlocks.MACERATOR)));

        RecipeBuilder.addItemsToGroup(Industry.MOD_ID,
                "copper_ores",
                IndBlocks.ORE_COPPER_BASALT,
                IndBlocks.ORE_COPPER_LIMESTONE,
                IndBlocks.ORE_COPPER_STONE,
                IndBlocks.ORE_COPPER_GRANITE);

        RecipeBuilder.addItemsToGroup(Industry.MOD_ID,
                "tin_ores",
                IndBlocks.ORE_TIN_BASALT,
                IndBlocks.ORE_TIN_LIMESTONE,
                IndBlocks.ORE_TIN_STONE,
                IndBlocks.ORE_TIN_GRANITE);

        RecipeBuilder.addItemsToGroup(Industry.MOD_ID,
                "uranium_ores",
                IndBlocks.ORE_URANIUM_BASALT,
                IndBlocks.ORE_URANIUM_LIMESTONE,
                IndBlocks.ORE_URANIUM_STONE,
                IndBlocks.ORE_URANIUM_GRANITE);
    }

    public void load() {
        Registries.RECIPE_TYPES.register("industry:macerator", RecipeEntryMacerator.class);
        DataLoader.loadRecipesFromFile("/assets/industry/recipes/macerator.json");
        Industry.LOGGER.info("{} recipes in {} groups.", INDUSTRY.getAllRecipes().size(), INDUSTRY.size());
    }

    public void registerNamespaces() {
        INDUSTRY.register("macerator", MACERATOR);
        Registries.RECIPES.register("industry", INDUSTRY);
    }
}
