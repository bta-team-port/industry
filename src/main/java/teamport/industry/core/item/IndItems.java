package teamport.industry.core.item;

import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.item.Item;
import sunsetsatellite.catalyst.energy.electric.api.VoltageTier;
import teamport.industry.client.model.item.ItemModelBattery;
import teamport.industry.core.IndConfig;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.item.logic.ItemLogicBatteryBase;
import teamport.industry.core.item.logic.ItemLogicCable;
import teamport.industry.core.item.logic.ItemLogicCell;
import teamport.industry.core.item.logic.ItemLogicWrench;
import turniplabs.halplibe.helper.ItemBuilder;

import static teamport.industry.Industry.MOD_ID;

/**
 * Items registration and creation
 * @author Cookie
 * @date 2024-12-24
 */
public class IndItems {
    private static int baseID = IndConfig.cfg.getInt("IDs.startingItemID");
    private static int nextID() {
        return baseID++;
    }

    public static final Item RAW_COPPER_ORE;
    public static final Item RAW_TIN_ORE;
    public static final Item RAW_URANIUM;

    public static final Item DUST_COPPER;
    public static final Item DUST_TIN;
    public static final Item DUST_COAL;
    public static final Item DUST_IRON;
    public static final Item DUST_GOLD;
    public static final Item DUST_BRONZE;
    public static final Item DUST_STEEL;

    public static final Item INGOT_COPPER;
    public static final Item INGOT_TIN;
    public static final Item INGOT_URANIUM;
    public static final Item INGOT_BRONZE;
    public static final Item INGOT_MIXED;

    public static final Item COPPER_CABLE;
    public static final Item INSULATED_COPPER_CABLE;

    public static final Item WRENCH;
    public static final Item RE_BATTERY;

    public static final Item EMPTY_CELL;
    public static final Item WATER_CELL;
    public static final Item LAVA_CELL;
    public static final Item OIL_CELL;
    public static final Item COOLANT_CELL;
    public static final Item URANIUM_CELL;
    public static final Item DEPLETED_URANIUM_CELL;

    static {
        RAW_COPPER_ORE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/raw_ore/ore_raw_copper")
                .build(new Item("raw.copper", nextID()));

        RAW_TIN_ORE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/raw_ore/ore_raw_tin")
                .build(new Item("raw.tin", nextID()));

        RAW_URANIUM = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/raw_ore/ore_raw_uranium")
                .build(new Item("raw.uranium", nextID()));

        DUST_COPPER = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/dust/dust_copper")
                .build(new Item("dust.copper", nextID()));
        DUST_TIN = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/dust/dust_tin")
                .build(new Item("dust.tin", nextID()));
        DUST_COAL = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/dust/dust_coal")
                .build(new Item("dust.coal", nextID()));
        DUST_IRON = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/dust/dust_iron")
                .build(new Item("dust.iron", nextID()));
        DUST_GOLD = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/dust/dust_gold")
                .build(new Item("dust.gold", nextID()));
        DUST_BRONZE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/dust/dust_bronze")
                .build(new Item("dust.bronze", nextID()));
        DUST_STEEL = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/dust/dust_steel")
                .build(new Item("dust.steel", nextID()));

        INGOT_COPPER = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/ingot/ingot_copper")
                .build(new Item("ingot.copper", nextID()));

        INGOT_TIN = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/ingot/ingot_tin")
                .build(new Item("ingot.tin", nextID()));

        INGOT_URANIUM = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/ingot/ingot_uranium")
                .build(new Item("ingot.uranium", nextID()));

        INGOT_BRONZE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/ingot/ingot_bronze")
                .build(new Item("ingot.bronze", nextID()));

        INGOT_MIXED = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/ingot/ingot_mixed")
                .build(new Item("ingot.mixed", nextID()));

        COPPER_CABLE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cable/copper/copper_raw")
                .build(new ItemLogicCable("copper_cable", nextID(), IndBlocks.COPPER_CABLE));

        INSULATED_COPPER_CABLE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cable/copper/copper_insulated")
                .build(new ItemLogicCable("insulated_copper_cable", nextID(), IndBlocks.INSULATED_COPPER_CABLE));

        WRENCH = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/tool/tool_wrench")
                .setItemModel(i -> new ItemModelStandard(i, MOD_ID).setFull3D())
                .build(new ItemLogicWrench("wrench", nextID()));

        RE_BATTERY = new ItemBuilder(MOD_ID)
                .setItemModel(item -> new ItemModelBattery(item, MOD_ID, "industry:item/battery/rechargeable/"))
                .build(new ItemLogicBatteryBase("re.battery", nextID(), VoltageTier.LV));

        EMPTY_CELL = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cell/empty_cell")
                .build(new ItemLogicCell("cell.empty", nextID()));

        WATER_CELL = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cell/water_cell")
                .build(new Item("cell.water", nextID()));

        LAVA_CELL = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cell/lava_cell")
                .build(new Item("cell.lava", nextID()));

        OIL_CELL = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cell/oil_cell")
                .build(new Item("cell.oil", nextID()));

        COOLANT_CELL = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cell/coolant_cell")
                .setStackSize(1)
                .build(new Item("cell.coolant", nextID()));

        URANIUM_CELL = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cell/uranium_cell")
                .setStackSize(1)
                .build(new Item("cell.uranium", nextID()));

        DEPLETED_URANIUM_CELL = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/cell/depleted_uranium_cell")
                .build(new Item("cell.depleted.uranium", nextID()));
    }
}
