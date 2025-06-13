package teamport.industry.core.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import sunsetsatellite.catalyst.core.util.DataInitializer;
import teamport.industry.Industry;
import teamport.industry.core.IndConfig;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.item.logic.ItemLogicWrench;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.util.ItemInitEntrypoint;

public class IndItems extends DataInitializer implements ItemInitEntrypoint {
    private static int baseID = IndConfig.cfg.getInt("IDs.startingItemID");
    private static int nextID() {
        return baseID++;
    }

    public static Item STICKY_RESIN;
    public static Item RUBBER;
    public static Item TREE_TAP;
    public static Item FOOD_ORANGE;
    public static Item FOOD_JOFFO_CAKE;
    public static Item ORE_RAW_COPPER;
    public static Item ORE_RAW_TIN;
    public static Item ORE_RAW_URANIUM;
    public static Item DUST_COPPER;
    public static Item DUST_TIN;
    public static Item DUST_BRONZE;
    public static Item DUST_COAL;
    public static Item DUST_IRON;
    public static Item DUST_GOLD;
    public static Item DUST_STEEL;
    public static Item INGOT_COPPER;
    public static Item INGOT_TIN;
    public static Item INGOT_BRONZE;
    public static Item INGOT_URANIUM;
    public static Item TOOL_WRENCH;

    @Override
    public void init() {
        if (initialized) {
            return;
        }

        STICKY_RESIN = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("resin", "industry:item/resin", nextID()));

        RUBBER = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("rubber", "industry:item/rubber", nextID()));

        TREE_TAP = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("tool.treetap", "industry:item/tool_treetap", nextID()).setMaxStackSize(1));

        FOOD_ORANGE = new ItemBuilder(Industry.MOD_ID)
                .build(new ItemFood("food.orange", "industry:item/food_orange", nextID(), 2, 2, false, 6));

        FOOD_JOFFO_CAKE = new ItemBuilder(Industry.MOD_ID)
                .build(new ItemFood("food.joffocake", "industry:item/food_joffo_cake", nextID(), 1, 0, false, 12));

        ORE_RAW_COPPER = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("ore.raw.copper", "industry:item/ore_raw_copper", nextID()));

        ORE_RAW_TIN = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("ore.raw.tin", "industry:item/ore_raw_tin", nextID()));

        ORE_RAW_URANIUM = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("ore.raw.uranium", "industry:item/ore_raw_uranium", nextID()));

        DUST_COPPER = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("dust.copper", "industry:item/dust_copper", nextID()));

        DUST_TIN = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("dust.tin", "industry:item/dust_tin", nextID()));

        DUST_BRONZE = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("dust.bronze", "industry:item/dust_bronze", nextID()));

        DUST_COAL = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("dust.coal", "industry:item/dust_coal", nextID()));

        DUST_IRON = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("dust.iron", "industry:item/dust_iron", nextID()));

        DUST_GOLD = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("dust.gold", "industry:item/dust_gold", nextID()));

        DUST_STEEL = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("dust.steel", "industry:item/dust_steel", nextID()));

        INGOT_COPPER = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("ingot.copper", "industry:item/ingot_copper", nextID()));

        INGOT_TIN = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("ingot.tin", "industry:item/ingot_tin", nextID()));

        INGOT_BRONZE = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("ingot.bronze", "industry:item/ingot_bronze", nextID()));

        INGOT_URANIUM = new ItemBuilder(Industry.MOD_ID)
                .build(new Item("ingot.uranium", "industry:item/ingot_uranium", nextID()));

        TOOL_WRENCH = new ItemBuilder(Industry.MOD_ID)
                .build(new ItemLogicWrench("tool.wrench", "industry:item/tool/wrench", nextID()));

        setInitialized(true);
    }


    @Override
    public void afterItemInit() {
        init();

        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_COPPER_STONE, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_COPPER_BASALT, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_COPPER_LIMESTONE, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_COPPER_GRANITE, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_COPPER_PERMAFROST, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_TIN_STONE, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_TIN_BASALT, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_TIN_LIMESTONE, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_TIN_GRANITE, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_TIN_PERMAFROST, 1);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_URANIUM_STONE, 2);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_URANIUM_BASALT, 2);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_URANIUM_LIMESTONE, 2);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_URANIUM_GRANITE, 2);
        ItemToolPickaxe.miningLevels.put(IndBlocks.ORE_URANIUM_PERMAFROST, 2);
    }
}
