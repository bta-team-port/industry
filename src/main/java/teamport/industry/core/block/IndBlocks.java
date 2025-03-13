package teamport.industry.core.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.sound.BlockSounds;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.MpGuiEntry;
import sunsetsatellite.catalyst.energy.electric.api.VoltageTier;
import teamport.industry.client.gui.*;
import teamport.industry.client.model.block.*;
import teamport.industry.core.IndConfig;
import teamport.industry.core.IndWireProperties;
import teamport.industry.core.block.entity.*;
import teamport.industry.core.block.logic.base.BlockLogicCableBase;
import teamport.industry.core.block.logic.cable.BlockLogicCableCopper;
import teamport.industry.core.block.logic.cable.BlockLogicCableInsulatedCopper;
import teamport.industry.core.block.logic.machine.*;
import teamport.industry.core.block.logic.ore.BlockLogicCopperOre;
import teamport.industry.core.block.logic.ore.BlockLogicTinOre;
import teamport.industry.core.block.logic.ore.BlockLogicUraniumOre;
import teamport.industry.core.container.*;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.industry.Industry.MOD_ID;

/**
 * Blocks registration and creation
 * @author Cookie
 * @date 2024-12-24
 */
public class IndBlocks {
    private static int baseID = IndConfig.cfg.getInt("IDs.startingBlockID");
    private static int nextID() {
        return baseID++;
    }

    public static final Block ORE_COPPER_STONE;
    public static final Block ORE_COPPER_BASALT;
    public static final Block ORE_COPPER_LIMESTONE;
    public static final Block ORE_COPPER_GRANITE;

    public static final Block ORE_TIN_STONE;
    public static final Block ORE_TIN_BASALT;
    public static final Block ORE_TIN_LIMESTONE;
    public static final Block ORE_TIN_GRANITE;

    public static final Block ORE_URANIUM_STONE;
    public static final Block ORE_URANIUM_BASALT;
    public static final Block ORE_URANIUM_LIMESTONE;
    public static final Block ORE_URANIUM_GRANITE;

    public static final Block BLOCK_OF_COPPER;
    public static final Block BLOCK_OF_TIN;
    public static final Block BLOCK_OF_BRONZE;

    public static final BlockLogicCableBase COPPER_CABLE;
    public static final BlockLogicCableBase INSULATED_COPPER_CABLE;

    public static final Block MACHINE_BLOCK;
    public static final Block GENERATOR;
    public static final Block GEOTHERMAL_GENERATOR;
    public static final Block SOLAR_PANEL;

    public static final Block BATBOX;

    public static final Block MACERATOR;

    static {
        // BUILDERS //
        BlockBuilder oreBuilder = new BlockBuilder(MOD_ID)
                .setHardness(3)
                .setResistance(5)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.CAVES_CUT_THROUGH, BlockTags.MINEABLE_BY_PICKAXE);

        BlockBuilder materialBlockBuilder = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.METAL)
                .setHardness(5)
                .setResistance(10)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE);

        // BLOCKS //
        ORE_COPPER_STONE = oreBuilder
                .setTextures("industry:block/ore/copper/ore_copper_stone")
                .build(new BlockLogicCopperOre("ore_copper_stone", nextID()));
        ORE_COPPER_BASALT = oreBuilder
                .setTextures("industry:block/ore/copper/ore_copper_basalt")
                .build(new BlockLogicCopperOre("ore_copper_basalt", nextID()));
        ORE_COPPER_LIMESTONE = oreBuilder
                .setTextures("industry:block/ore/copper/ore_copper_limestone")
                .build(new BlockLogicCopperOre("ore_copper_limestone", nextID()));
        ORE_COPPER_GRANITE = oreBuilder
                .setTextures("industry:block/ore/copper/ore_copper_granite")
                .build(new BlockLogicCopperOre("ore_copper_granite", nextID()));

        ORE_TIN_STONE = oreBuilder
                .setTextures("industry:block/ore/tin/ore_tin_stone")
                .build(new BlockLogicTinOre("ore_tin_stone", nextID()));
        ORE_TIN_BASALT = oreBuilder
                .setTextures("industry:block/ore/tin/ore_tin_basalt")
                .build(new BlockLogicTinOre("ore_tin_basalt", nextID()));
        ORE_TIN_LIMESTONE = oreBuilder
                .setTextures("industry:block/ore/tin/ore_tin_limestone")
                .build(new BlockLogicTinOre("ore_tin_limestone", nextID()));
        ORE_TIN_GRANITE = oreBuilder
                .setTextures("industry:block/ore/tin/ore_tin_granite")
                .build(new BlockLogicTinOre("ore_tin_granite", nextID()));

        ORE_URANIUM_STONE = oreBuilder
                .setTextures("industry:block/ore/uranium/ore_uranium_stone")
                .build(new BlockLogicUraniumOre("ore_uranium_stone", nextID()));
        ORE_URANIUM_BASALT = oreBuilder
                .setTextures("industry:block/ore/uranium/ore_uranium_basalt")
                .build(new BlockLogicUraniumOre("ore_uranium_basalt", nextID()));
        ORE_URANIUM_LIMESTONE = oreBuilder
                .setTextures("industry:block/ore/uranium/ore_uranium_limestone")
                .build(new BlockLogicUraniumOre("ore_uranium_limestone", nextID()));
        ORE_URANIUM_GRANITE = oreBuilder
                .setTextures("industry:block/ore/uranium/ore_uranium_granite")
                .build(new BlockLogicUraniumOre("ore_uranium_granite", nextID()));

        BLOCK_OF_COPPER = materialBlockBuilder
                .setTopTexture("industry:block/block_copper/top")
                .setSideTextures("industry:block/block_copper/side")
                .setBottomTexture("industry:block/block_copper/bottom")
                .build(new Block("block.copper", nextID(), Material.metal));

        BLOCK_OF_TIN = materialBlockBuilder
                .setTopTexture("industry:block/block_tin/top")
                .setSideTextures("industry:block/block_tin/side")
                .setBottomTexture("industry:block/block_tin/bottom")
                .build(new Block("block.tin", nextID(), Material.metal));

        BLOCK_OF_BRONZE = materialBlockBuilder
                .setTopTexture("industry:block/block_bronze/top")
                .setSideTextures("industry:block/block_bronze/side")
                .setBottomTexture("industry:block/block_bronze/bottom")
                .build(new Block("block.bronze", nextID(), Material.metal));

        COPPER_CABLE = new BlockBuilder(MOD_ID)
                .setHardness(1)
                .setBlockModel(BlockModelCable::new)
                .setBlockSound(BlockSounds.METAL)
                .setTags(BlockTags.BROKEN_BY_FLUIDS, IndBlockTags.BROKEN_BY_WIRECUTTERS, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .setTextures("industry:block/cable/copper/copper_raw")
                .build(new BlockLogicCableCopper(nextID(), IndWireProperties.COPPER));

        INSULATED_COPPER_CABLE = new BlockBuilder(MOD_ID)
                .setBlockModel(BlockModelInsulatedCable::new)
                .setBlockSound(BlockSounds.CLOTH)
                .setHardness(1)
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.MINEABLE_BY_PICKAXE, IndBlockTags.BROKEN_BY_WIRECUTTERS, BlockTags.NOT_IN_CREATIVE_MENU)
                .setTextures("industry:block/cable/copper/copper_insulated")
                .build(new BlockLogicCableInsulatedCopper(nextID(), IndWireProperties.COPPER_INSULATED));

        MACHINE_BLOCK = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.METAL)
                .setHardness(3.5f)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .setTextures("industry:block/machine/machine_block")
                .build(new Block("machine_block", nextID(), Material.metal));

        GENERATOR = new BlockBuilder(MOD_ID)
                .setBlockModel(b -> new BlockModelGenerator(b,
                        "industry:block/generator/coal/idle_front",
                        "industry:block/generator/coal/active_front"))
                .setBlockSound(BlockSounds.METAL)
                .setHardness(3.5f)
                .setTags(IndBlockTags.REQUIRES_WRENCH)
                .setBottomTexture("industry:block/generator/coal/bottom")
                .setEastWestTextures("industry:block/generator/coal/side")
                .setNorthTexture("industry:block/generator/coal/idle_front")
                .setSouthTexture("industry:block/generator/coal/side")
                .setTopTexture("industry:block/generator/coal/top")
                .build(new BlockLogicGenerator("generator", nextID(), VoltageTier.LV));

        GEOTHERMAL_GENERATOR = new BlockBuilder(MOD_ID)
                .setBlockModel(b -> new BlockModelGeothermalGenerator(b,
                        "industry:block/generator/geothermal/idle_front",
                        "industry:block/generator/geothermal/active_front"))
                .setBlockSound(BlockSounds.METAL)
                .setHardness(3.5f)
                .setTags(IndBlockTags.REQUIRES_WRENCH)
                .setBottomTexture("industry:block/generator/geothermal/bottom")
                .setEastWestTextures("industry:block/generator/geothermal/side")
                .setNorthTexture("industry:block/generator/geothermal/idle_front")
                .setSouthTexture("industry:block/generator/geothermal/side")
                .setTopTexture("industry:block/generator/geothermal/top")
                .build(new BlockLogicGeothermalGenerator("geogenerator", nextID(), VoltageTier.LV));

        SOLAR_PANEL = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.METAL)
                .setHardness(3.5f)
                .setTags(IndBlockTags.REQUIRES_WRENCH)
                .setTopTexture("industry:block/generator/solar/top")
                .setSideTextures("industry:block/generator/solar/side")
                .setBottomTexture("industry:block/generator/solar/bottom")
                .build(new BlockLogicSolarPanel("solar_panel", nextID(), VoltageTier.LV));

        BATBOX = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(3.5f)
                .setTags(BlockTags.MINEABLE_BY_AXE)
                .setTopBottomTextures("industry:block/batbox/side")
                .setSideTextures("industry:block/batbox/front")
                .build(new BlockLogicBatBox("batbox", nextID(), VoltageTier.LV));

        MACERATOR = new BlockBuilder(MOD_ID)
                .setBlockModel(b -> new BlockModelMacerator(b,
                        "industry:block/machine/macerator/idle_top",
                        "industry:block/machine/macerator/active_top"))
                .setBlockSound(BlockSounds.METAL)
                .setHardness(3.5f)
                .setTags(IndBlockTags.REQUIRES_WRENCH)
                .setBottomTexture("industry:block/machine/macerator/bottom")
                .setEastWestTextures("industry:block/machine/macerator/side")
                .setNorthTexture("industry:block/machine/macerator/front")
                .setSouthTexture("industry:block/machine/macerator/side")
                .setTopTexture("industry:block/machine/macerator/idle_top")
                .build(new BlockLogicMacerator("macerator", nextID(), VoltageTier.LV));

        // MINING LEVELS //
        ItemToolPickaxe.miningLevels.put(ORE_COPPER_STONE, 1);
        ItemToolPickaxe.miningLevels.put(ORE_COPPER_BASALT, 1);
        ItemToolPickaxe.miningLevels.put(ORE_COPPER_LIMESTONE, 1);
        ItemToolPickaxe.miningLevels.put(ORE_COPPER_GRANITE, 1);
        ItemToolPickaxe.miningLevels.put(ORE_TIN_STONE, 1);
        ItemToolPickaxe.miningLevels.put(ORE_TIN_BASALT, 1);
        ItemToolPickaxe.miningLevels.put(ORE_TIN_LIMESTONE, 1);
        ItemToolPickaxe.miningLevels.put(ORE_TIN_GRANITE, 1);

        ItemToolPickaxe.miningLevels.put(ORE_URANIUM_STONE, 2);
        ItemToolPickaxe.miningLevels.put(ORE_URANIUM_BASALT, 2);
        ItemToolPickaxe.miningLevels.put(ORE_URANIUM_LIMESTONE, 2);
        ItemToolPickaxe.miningLevels.put(ORE_URANIUM_GRANITE, 2);
        ItemToolPickaxe.miningLevels.put(MACHINE_BLOCK, 2);
        ItemToolPickaxe.miningLevels.put(BLOCK_OF_COPPER, 2);
        ItemToolPickaxe.miningLevels.put(BLOCK_OF_TIN, 2);
        ItemToolPickaxe.miningLevels.put(BLOCK_OF_BRONZE, 2);

        // TILE ENTITIES //
        EntityHelper.createTileEntity(TileEntityCable.class, "Industry_Cable");
        EntityHelper.createTileEntity(TileEntityGenerator.class, "Industry_Generator");
        EntityHelper.createTileEntity(TileEntityGeothermalGenerator.class, "Industry_GeothermalGenerator");
        EntityHelper.createTileEntity(TileEntitySolarPanel.class, "Industry_SolarPanel");
        EntityHelper.createTileEntity(TileEntityBatBox.class, "Industry_BatBox");
        EntityHelper.createTileEntity(TileEntityMacerator.class, "Industry_Macerator");

        // CATALYST GUI //
        Catalyst.GUIS.register("Industry_Generator", new MpGuiEntry(
                TileEntityGenerator.class,
                GUIGenerator.class,
                ContainerGenerator.class)
        );
        Catalyst.GUIS.register("Industry_SolarPanel", new MpGuiEntry(
                TileEntitySolarPanel.class,
                GUISolarPanel.class,
                ContainerSolarPanel.class)
        );
        Catalyst.GUIS.register("Industry_GeothermalGenerator", new MpGuiEntry(
                TileEntityGeothermalGenerator.class,
                GUIGeothermalGenerator.class,
                ContainerGeothermalGenerator.class)
        );
        Catalyst.GUIS.register("Industry_BatBox", new MpGuiEntry(
                TileEntityBatBox.class,
                GUIBatBox.class,
                ContainerBatBox.class
        ));
        Catalyst.GUIS.register("Industry_Macerator", new MpGuiEntry(
                TileEntityMacerator.class,
                GUIMacerator.class,
                ContainerMacerator.class
        ));
    }
}
