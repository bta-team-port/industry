package teamport.industry.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorCustom;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.*;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.Side;
import teamport.industry.Industry;
import teamport.industry.client.model.block.*;
import teamport.industry.client.model.block.entity.TileEntityRendererPipe;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.block.entity.TileEntityPipeBase;
import teamport.industry.core.item.IndItems;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

@Environment(EnvType.CLIENT)
public class IndModels implements ModelEntrypoint {
    @Override
    public void initBlockModels(BlockModelDispatcher dispatcher) {
        dispatcher.addDispatch(new BlockModelLogRubberwood(IndBlocks.LOG_RUBBERWOOD)
                .setTex(BlockModelStandard.BLOCK_TEXTURES, "industry:block/log/rubberwood/top", Side.TOP, Side.BOTTOM)
                .setTex(BlockModelStandard.BLOCK_TEXTURES, "industry:block/log/rubberwood/side", Side.NORTH, Side.SOUTH, Side.EAST, Side.WEST));

        dispatcher.addDispatch(new BlockModelLeaves<>(IndBlocks.LEAVES_RUBBERWOOD, "minecraft:block/leaves/oak"));

        dispatcher.addDispatch(new BlockModelCrossedSquares<>(IndBlocks.SAPLING_RUBBERWOOD)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/sapling/rubberwood"));

        dispatcher.addDispatch(new BlockModelCrossedSquares<>(IndBlocks.SAPLING_ORANGE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/sapling/orange"));

        dispatcher.addDispatch(new BlockModelLeaves<>(IndBlocks.LEAVES_ORANGE, "industry:block/leaves/orange"));

        dispatcher.addDispatch(new BlockModelLeavesOrange(IndBlocks.LEAVES_ORANGE_FLOWERING));

        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_COPPER_STONE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/copper/stone")
                .setAllTextures(BlockModelStandard.RETRO_BLOCK_TEXTURES, "industry:block/ore/copper/stone_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_COPPER_BASALT)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/copper/basalt"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_COPPER_LIMESTONE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/copper/limestone"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_COPPER_GRANITE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/copper/granite"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_COPPER_PERMAFROST)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/copper/permafrost"));

        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_TIN_STONE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/tin/stone")
                .setAllTextures(BlockModelStandard.RETRO_BLOCK_TEXTURES, "industry:block/ore/tin/stone_retro"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_TIN_BASALT)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/tin/basalt"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_TIN_LIMESTONE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/tin/limestone"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_TIN_GRANITE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/tin/granite"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_TIN_PERMAFROST)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/tin/permafrost"));

        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_URANIUM_STONE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/uranium/stone")
                .setAllTextures(BlockModelStandard.OVERBRIGHT_TEXTURES, "industry:block/ore/uranium/overlay")
                .setAllTextures(BlockModelStandard.RETRO_BLOCK_TEXTURES, "industry:block/ore/uranium/stone_retro")
                .setAllTextures(BlockModelStandard.RETRO_OVERBRIGHT_TEXTURES, "industry:block/ore/uranium/overlay"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_URANIUM_BASALT)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/uranium/basalt")
                .setAllTextures(BlockModelStandard.OVERBRIGHT_TEXTURES, "industry:block/ore/uranium/overlay"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_URANIUM_LIMESTONE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/uranium/limestone")
                .setAllTextures(BlockModelStandard.OVERBRIGHT_TEXTURES, "industry:block/ore/uranium/overlay"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_URANIUM_GRANITE)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/uranium/granite")
                .setAllTextures(BlockModelStandard.OVERBRIGHT_TEXTURES, "industry:block/ore/uranium/overlay"));
        dispatcher.addDispatch(new BlockModelStandard<>(IndBlocks.ORE_URANIUM_PERMAFROST)
                .setAllTextures(BlockModelStandard.BLOCK_TEXTURES, "industry:block/ore/uranium/permafrost")
                .setAllTextures(BlockModelStandard.OVERBRIGHT_TEXTURES, "industry:block/ore/uranium/overlay"));

        dispatcher.addDispatch(new BlockModelPipeWooden(IndBlocks.PIPE_WOODEN)
                .setAllTextures(0, "industry:block/pipe/wooden")
                .withCustomItemBounds(0.25, 0.25, 0.25, 0.75, 0.75, 0.75));
        dispatcher.addDispatch(new BlockModelPipeBase(IndBlocks.PIPE_BASALT)
                .setAllTextures(0, "industry:block/pipe/basalt")
                .withCustomItemBounds(0.25, 0.25, 0.25, 0.75, 0.75, 0.75));
        dispatcher.addDispatch(new BlockModelPipeBase(IndBlocks.PIPE_STONE)
                .setAllTextures(0, "industry:block/pipe/stone")
                .withCustomItemBounds(0.25, 0.25, 0.25, 0.75, 0.75, 0.75));
        dispatcher.addDispatch(new BlockModelPipeBase(IndBlocks.PIPE_LIMESTONE)
                .setAllTextures(0, "industry:block/pipe/limestone")
                .withCustomItemBounds(0.25, 0.25, 0.25, 0.75, 0.75, 0.75));
        dispatcher.addDispatch(new BlockModelPipeBase(IndBlocks.PIPE_GRANITE)
                .setAllTextures(0, "industry:block/pipe/granite")
                .withCustomItemBounds(0.25, 0.25, 0.25, 0.75, 0.75, 0.75));
        dispatcher.addDispatch(new BlockModelPipeBase(IndBlocks.PIPE_NETHERRACK)
                .setAllTextures(0, "industry:block/pipe/netherrack")
                .withCustomItemBounds(0.25, 0.25, 0.25, 0.75, 0.75, 0.75));
        dispatcher.addDispatch(new BlockModelPipeBase(IndBlocks.PIPE_PERMAFROST)
                .setAllTextures(0, "industry:block/pipe/permafrost")
                .withCustomItemBounds(0.25, 0.25, 0.25, 0.75, 0.75, 0.75));
        dispatcher.addDispatch(new BlockModelPipeIron(IndBlocks.PIPE_IRON)
                .setAllTextures(0, "industry:block/pipe/iron")
                .withCustomItemBounds(0.25, 0.25, 0.25, 0.75, 0.75, 0.75));
    }

    @Override
    public void initItemModels(ItemModelDispatcher dispatcher) {
        ModelHelper.setItemModel(IndItems.TREE_TAP, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.TREE_TAP, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/tool/treetap");
            itemModel.setFull3D();
            itemModel.setRotateWhenRendering();
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.STICKY_RESIN, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.STICKY_RESIN, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/sticky_resin");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.RUBBER, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.RUBBER, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/rubber");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.FOOD_ORANGE, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.FOOD_ORANGE, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/food/orange");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.FOOD_JOFFO_CAKE, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.FOOD_JOFFO_CAKE, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/food/joffo_cake");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.ORE_RAW_COPPER, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.ORE_RAW_COPPER, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/ore_raw/copper");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.ORE_RAW_TIN, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.ORE_RAW_TIN, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/ore_raw/tin");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.ORE_RAW_URANIUM, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.ORE_RAW_URANIUM, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/ore_raw/uranium");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.DUST_COPPER, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.DUST_COPPER, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/dust/copper");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.DUST_TIN, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.DUST_TIN, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/dust/tin");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.DUST_BRONZE, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.DUST_BRONZE, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/dust/bronze");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.DUST_COAL, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.DUST_COAL, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/dust/coal");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.DUST_IRON, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.DUST_IRON, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/dust/iron");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.DUST_GOLD, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.DUST_GOLD, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/dust/gold");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.DUST_STEEL, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.DUST_STEEL, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/dust/steel");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.INGOT_COPPER, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.INGOT_COPPER, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/ingot/copper");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.INGOT_TIN, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.INGOT_TIN, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/ingot/tin");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.INGOT_BRONZE, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.INGOT_BRONZE, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/ingot/bronze");
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.INGOT_URANIUM, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.INGOT_URANIUM, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/ingot/uranium");
            itemModel.setFullBright();
            return itemModel;
        });

        ModelHelper.setItemModel(IndItems.TOOL_WRENCH, () -> {
            ItemModelStandard itemModel = new ItemModelStandard(IndItems.TOOL_WRENCH, Industry.MOD_ID);
            itemModel.icon = TextureRegistry.getTexture("industry:item/tool/wrench");
            itemModel.setFull3D();
            return itemModel;
        });
    }

    @Override
    public void initEntityModels(EntityRenderDispatcher dispatcher) {

    }

    @Override
    public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {
        ModelHelper.setTileEntityModel(TileEntityPipeBase.class, TileEntityRendererPipe::new);
    }

    @Override
    public void initBlockColors(BlockColorDispatcher dispatcher) {
        dispatcher.addDispatch(IndBlocks.LEAVES_RUBBERWOOD, new BlockColorCustom(Colorizers.pine));

        dispatcher.addDispatch(IndBlocks.LEAVES_ORANGE, new BlockColorCustom(Colorizers.thorn));

        dispatcher.addDispatch(IndBlocks.LEAVES_ORANGE_FLOWERING, new BlockColorCustom(Colorizers.thorn));
    }
}
