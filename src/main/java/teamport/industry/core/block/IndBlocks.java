package teamport.industry.core.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.sound.BlockSounds;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.MpGuiEntry;
import teamport.industry.client.gui.GUIGenerator;
import teamport.industry.client.gui.GUISolarPanel;
import teamport.industry.client.model.block.BlockModelCable;
import teamport.industry.client.model.block.BlockModelGenerator;
import teamport.industry.client.model.block.BlockModelInsulatedCable;
import teamport.industry.core.IndConfig;
import teamport.industry.core.block.entity.TileEntityCopperCable;
import teamport.industry.core.block.entity.TileEntityEnergyConductorDamageable;
import teamport.industry.core.block.entity.TileEntityGenerator;
import teamport.industry.core.block.entity.TileEntitySolarPanel;
import teamport.industry.core.block.logic.*;
import teamport.industry.core.container.ContainerGenerator;
import teamport.industry.core.container.ContainerSolarPanel;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.industry.Industry.MOD_ID;

/*
 * ===========================================================================
 * File: IndBlocks.java
 * Brief: Blocks registration and creation
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
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

    public static final Block COPPER_CABLE;
    public static final Block INSULATED_COPPER_CABLE;

    public static final Block MACHINE_BLOCK;
    public static final Block GENERATOR;
    public static final Block SOLAR_PANEL;

    static {
        // BUILDERS //
        BlockBuilder oreBuilder = new BlockBuilder(MOD_ID)
                .setHardness(3)
                .setResistance(5)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.CAVES_CUT_THROUGH, BlockTags.MINEABLE_BY_PICKAXE);

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

        COPPER_CABLE = new BlockBuilder(MOD_ID)
                .setHardness(1)
                .setBlockModel(BlockModelCable::new)
                .setBlockSound(BlockSounds.METAL)
                .setTags(BlockTags.BROKEN_BY_FLUIDS, IndBlockTags.BROKEN_BY_WIRECUTTERS, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .setTextures("industry:block/cable/copper/copper_raw")
                .build(new BlockLogicCopperCable("copper_cable", nextID()));

        INSULATED_COPPER_CABLE = new BlockBuilder(MOD_ID)
                .setBlockModel(BlockModelInsulatedCable::new)
                .setBlockSound(BlockSounds.CLOTH)
                .setHardness(1)
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.MINEABLE_BY_PICKAXE, IndBlockTags.BROKEN_BY_WIRECUTTERS, BlockTags.NOT_IN_CREATIVE_MENU)
                .setTextures("industry:block/cable/copper/copper_insulated")
                .build(new BlockLogicInsulatedCopperCable("insulated_copper_cable", nextID()));

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
                .build(new BlockLogicGenerator("generator", nextID()));

        SOLAR_PANEL = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.METAL)
                .setHardness(3.5f)
                .setTags(IndBlockTags.REQUIRES_WRENCH)
                .setTopTexture("industry:block/generator/solar/top")
                .setSideTextures("industry:block/generator/solar/side")
                .setBottomTexture("industry:block/generator/solar/bottom")
                .build(new BlockLogicSolarPanel("solar_panel", nextID()));

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

        // TILE ENTITIES //
        EntityHelper.createTileEntity(TileEntityEnergyConductorDamageable.class, "Industry_EnergyConductorDamageable");
        EntityHelper.createTileEntity(TileEntityCopperCable.class, "Industry_CopperCable");
        EntityHelper.createTileEntity(TileEntityGenerator.class, "Industry_Generator");
        EntityHelper.createTileEntity(TileEntitySolarPanel.class, "Industry_SolarPanel");

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
    }
}
