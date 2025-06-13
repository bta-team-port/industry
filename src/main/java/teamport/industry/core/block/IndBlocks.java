package teamport.industry.core.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import sunsetsatellite.catalyst.core.util.DataInitializer;
import teamport.industry.Industry;
import teamport.industry.core.IndConfig;
import teamport.industry.core.block.logic.*;
import teamport.industry.core.block.logic.pipe.BlockLogicPipeBase;
import teamport.industry.core.block.logic.pipe.BlockLogicPipeIron;
import teamport.industry.core.block.logic.pipe.BlockLogicPipeWood;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.BlockInitEntrypoint;

public class IndBlocks extends DataInitializer implements BlockInitEntrypoint {
    private static int baseID = IndConfig.cfg.getInt("IDs.startingBlockID");
    private static int nextID() {
        return baseID++;
    }

    public static Block<BlockLogicLogRubberwood> LOG_RUBBERWOOD;
    public static Block<BlockLogicSaplingRubberwood> SAPLING_RUBBERWOOD;
    public static Block<BlockLogicLeavesRubberwood> LEAVES_RUBBERWOOD;
    public static Block<BlockLogicSaplingOrange> SAPLING_ORANGE;
    public static Block<BlockLogicLeavesOrange> LEAVES_ORANGE;
    public static Block<BlockLogicLeavesOrangeFlowering> LEAVES_ORANGE_FLOWERING;

    public static Block<BlockLogicOreCopper> ORE_COPPER_STONE;
    public static Block<BlockLogicOreCopper> ORE_COPPER_BASALT;
    public static Block<BlockLogicOreCopper> ORE_COPPER_LIMESTONE;
    public static Block<BlockLogicOreCopper> ORE_COPPER_GRANITE;
    public static Block<BlockLogicOreCopper> ORE_COPPER_PERMAFROST;
    public static Block<BlockLogicOreTin> ORE_TIN_STONE;
    public static Block<BlockLogicOreTin> ORE_TIN_BASALT;
    public static Block<BlockLogicOreTin> ORE_TIN_LIMESTONE;
    public static Block<BlockLogicOreTin> ORE_TIN_GRANITE;
    public static Block<BlockLogicOreTin> ORE_TIN_PERMAFROST;
    public static Block<BlockLogicOreUranium> ORE_URANIUM_STONE;
    public static Block<BlockLogicOreUranium> ORE_URANIUM_BASALT;
    public static Block<BlockLogicOreUranium> ORE_URANIUM_LIMESTONE;
    public static Block<BlockLogicOreUranium> ORE_URANIUM_GRANITE;
    public static Block<BlockLogicOreUranium> ORE_URANIUM_PERMAFROST;

    public static Block<BlockLogicPipeBase> PIPE_WOODEN;
    public static Block<BlockLogicPipeBase> PIPE_BASALT;
    public static Block<BlockLogicPipeBase> PIPE_STONE;
    public static Block<BlockLogicPipeBase> PIPE_LIMESTONE;
    public static Block<BlockLogicPipeBase> PIPE_GRANITE;
    public static Block<BlockLogicPipeBase> PIPE_NETHERRACK;
    public static Block<BlockLogicPipeBase> PIPE_PERMAFROST;
    public static Block<BlockLogicPipeBase> PIPE_IRON;

    @Override
    public void init() {
        if (initialized) return;

        Industry.LOGGER.info("Initializing blocks...");

        LOG_RUBBERWOOD = new BlockBuilder(Industry.MOD_ID)
                .setHardness(2)
                .setBlockSound(BlockSounds.WOOD)
                .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
                .setTicking(true)
                .build("log.rubberwood", nextID(), BlockLogicLogRubberwood::new)
                .withDisabledNeighborNotifyOnMetadataChange();

        SAPLING_RUBBERWOOD = new BlockBuilder(Industry.MOD_ID)
                .setBlockSound(BlockSounds.GRASS)
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR)
                .build("sapling.rubberwood", nextID(), BlockLogicSaplingRubberwood::new)
                .withDisabledNeighborNotifyOnMetadataChange();

        LEAVES_RUBBERWOOD = new BlockBuilder(Industry.MOD_ID)
                .setHardness(0.2F)
                .setBlockSound(BlockSounds.GRASS)
                .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS)
                .build("leaves.rubberwood", nextID(), BlockLogicLeavesRubberwood::new)
                .withLightBlock(1)
                .withDisabledNeighborNotifyOnMetadataChange();

        SAPLING_ORANGE = new BlockBuilder(Industry.MOD_ID)
                .setBlockSound(BlockSounds.GRASS)
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR)
                .build("sapling.orange", nextID(), BlockLogicSaplingOrange::new)
                .withDisabledNeighborNotifyOnMetadataChange();

        LEAVES_ORANGE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(0.2F)
                .setBlockSound(BlockSounds.GRASS)
                .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS)
                .build("leaves.orange", nextID(), BlockLogicLeavesOrange::new)
                .withLightBlock(1)
                .withDisabledNeighborNotifyOnMetadataChange();

        LEAVES_ORANGE_FLOWERING = new BlockBuilder(Industry.MOD_ID)
                .setHardness(0.2F)
                .setBlockSound(BlockSounds.GRASS)
                .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS)
                .build("leaves.orange.flowering", nextID(), BlockLogicLeavesOrangeFlowering::new)
                .withLightBlock(1)
                .withDisabledNeighborNotifyOnMetadataChange();

        ORE_COPPER_STONE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.copper.stone", nextID(), b -> new BlockLogicOreCopper(b, Blocks.STONE, Material.stone));
        ORE_COPPER_BASALT = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.copper.basalt", nextID(), b -> new BlockLogicOreCopper(b, Blocks.BASALT, Material.basalt));
        ORE_COPPER_LIMESTONE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.copper.limestone", nextID(), b -> new BlockLogicOreCopper(b, Blocks.LIMESTONE, Material.limestone));
        ORE_COPPER_GRANITE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.copper.granite", nextID(), b -> new BlockLogicOreCopper(b, Blocks.GRANITE, Material.granite));
        ORE_COPPER_PERMAFROST = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.PERMAFROST)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.copper.permafrost", nextID(), b -> new BlockLogicOreCopper(b, Blocks.PERMAFROST, Material.permafrost));

        ORE_TIN_STONE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.tin.stone", nextID(), b -> new BlockLogicOreTin(b, Blocks.STONE, Material.stone));
        ORE_TIN_BASALT = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.tin.basalt", nextID(), b -> new BlockLogicOreTin(b, Blocks.BASALT, Material.basalt));
        ORE_TIN_LIMESTONE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.tin.limestone", nextID(), b -> new BlockLogicOreTin(b, Blocks.LIMESTONE, Material.limestone));
        ORE_TIN_GRANITE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.tin.granite", nextID(), b -> new BlockLogicOreTin(b, Blocks.GRANITE, Material.granite));
        ORE_TIN_PERMAFROST = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.PERMAFROST)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.tin.permafrost", nextID(), b -> new BlockLogicOreTin(b, Blocks.PERMAFROST, Material.permafrost));

        ORE_URANIUM_STONE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.uranium.stone", nextID(), b -> new BlockLogicOreUranium(b, Blocks.STONE, Material.stone));
        ORE_URANIUM_BASALT = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.uranium.basalt", nextID(), b -> new BlockLogicOreUranium(b, Blocks.BASALT, Material.basalt));
        ORE_URANIUM_LIMESTONE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.uranium.limestone", nextID(), b -> new BlockLogicOreUranium(b, Blocks.LIMESTONE, Material.limestone));
        ORE_URANIUM_GRANITE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.uranium.granite", nextID(), b -> new BlockLogicOreUranium(b, Blocks.GRANITE, Material.granite));
        ORE_URANIUM_PERMAFROST = new BlockBuilder(Industry.MOD_ID)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setBlockSound(BlockSounds.PERMAFROST)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("ore.uranium.permafrost", nextID(), b -> new BlockLogicOreUranium(b, Blocks.PERMAFROST, Material.permafrost));

        PIPE_WOODEN = new BlockBuilder(Industry.MOD_ID)
                .setHardness(1.0F)
                .setBlockSound(BlockSounds.WOOD)
                .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
                .build("pipe.wooden", nextID(), BlockLogicPipeWood::new);

        PIPE_BASALT = new BlockBuilder(Industry.MOD_ID)
                .setHardness(1.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("pipe.basalt", nextID(), b -> new BlockLogicPipeBase(b, Material.basalt));

        PIPE_STONE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(1.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("pipe.stone", nextID(), b -> new BlockLogicPipeBase(b, Material.stone));

        PIPE_LIMESTONE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(1.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("pipe.limestone", nextID(), b -> new BlockLogicPipeBase(b, Material.limestone));

        PIPE_GRANITE = new BlockBuilder(Industry.MOD_ID)
                .setHardness(1.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("pipe.granite", nextID(), b -> new BlockLogicPipeBase(b, Material.granite));

        PIPE_NETHERRACK = new BlockBuilder(Industry.MOD_ID)
                .setHardness(1.0F)
                .setBlockSound(BlockSounds.STONE)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("pipe.netherrack", nextID(), b -> new BlockLogicPipeBase(b, Material.netherrack));

        PIPE_PERMAFROST = new BlockBuilder(Industry.MOD_ID)
                .setHardness(1.0F)
                .setBlockSound(BlockSounds.PERMAFROST)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("pipe.permafrost", nextID(), b -> new BlockLogicPipeBase(b, Material.permafrost));

        PIPE_IRON = new BlockBuilder(Industry.MOD_ID)
                .setHardness(1.0F)
                .setBlockSound(BlockSounds.METAL)
                .setTags(BlockTags.MINEABLE_BY_PICKAXE)
                .build("pipe.iron", nextID(), BlockLogicPipeIron::new);

        setInitialized(true);
    }

    @Override
    public void afterBlockInit() {
        init();
    }
}
