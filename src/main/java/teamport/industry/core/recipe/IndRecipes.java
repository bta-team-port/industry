package teamport.industry.core.recipe;

import teamport.industry.Industry;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

/**
 * Recipe registration and creation
 * @author Cookie
 * @date 2024-12-23
 */
public class IndRecipes implements RecipeEntrypoint {

    @Override
    public void onRecipesReady() {

    }

    @Override
    public void initNamespaces() {
        RecipeBuilder.initNameSpace(Industry.MOD_ID);
    }
}
