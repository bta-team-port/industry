package teamport.industry.core.item;

import net.minecraft.core.item.Item;
import teamport.industry.client.model.item.ItemModelBattery;
import teamport.industry.core.IndConfig;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.item.logic.ItemLogicBatteryBase;
import teamport.industry.core.item.logic.ItemLogicCable;
import teamport.industry.core.item.logic.ItemLogicWrench;
import turniplabs.halplibe.helper.ItemBuilder;

import static teamport.industry.Industry.MOD_ID;

/*
 * ===========================================================================
 * File: IndItems.java
 * Brief: Items registration and creation
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class IndItems {
    private static int baseID = IndConfig.cfg.getInt("IDs.startingItemID");
    private static int nextID() {
        return baseID++;
    }

    public static final Item RAW_COPPER_ORE;
    public static final Item RAW_TIN_ORE;
    public static final Item RAW_URANIUM;
    public static final Item COPPER_CABLE;
    public static final Item INSULATED_COPPER_CABLE;
    public static final Item WRENCH;
    public static final Item RE_BATTERY;

    static {
        RAW_COPPER_ORE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/raw_ore/ore_raw_copper")
                .build(new Item("raw_copper_ore", nextID()));

        RAW_TIN_ORE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/raw_ore/ore_raw_tin")
                .build(new Item("raw_tin_ore", nextID()));

        RAW_URANIUM = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/raw_ore/ore_raw_uranium")
                .build(new Item("raw_uranium", nextID()));

        COPPER_CABLE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cable/copper/copper_raw")
                .build(new ItemLogicCable("copper_cable", nextID(), IndBlocks.COPPER_CABLE, "32"));

        INSULATED_COPPER_CABLE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cable/copper/copper_insulated")
                .build(new ItemLogicCable("insulated_copper_cable", nextID(), IndBlocks.INSULATED_COPPER_CABLE, "32"));

        WRENCH = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/tool/tool_wrench")
                .build(new ItemLogicWrench("wrench", nextID()));

        RE_BATTERY = new ItemBuilder(MOD_ID)
                .setItemModel(item -> new ItemModelBattery(item, MOD_ID, "industry:item/battery/rechargeable/"))
                .build(new ItemLogicBatteryBase("re.battery", nextID(), 10000, 32, 32));
    }
}
