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
                .build(new Item("raw_copper_ore", nextID()));

        RAW_TIN_ORE = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/raw_ore/ore_raw_tin")
                .build(new Item("raw_tin_ore", nextID()));

        RAW_URANIUM = new ItemBuilder(MOD_ID)
                .setIcon("industry:item/raw_ore/ore_raw_uranium")
                .build(new Item("raw_uranium", nextID()));

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
