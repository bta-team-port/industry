package teamport.industry.client.model.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicAxisAligned;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.util.phys.AABB;
import teamport.industry.core.block.logic.BlockLogicLogRubberwood;

@Environment(EnvType.CLIENT)
public class BlockModelLogRubberwood extends BlockModelStandard<BlockLogicLogRubberwood> {
    private final IconCoordinate resinOverlay = TextureRegistry.getTexture("industry:block/log/rubberwood/overlay_resin");
    private final IconCoordinate resinTreetapOverlay = TextureRegistry.getTexture("industry:block/log/rubberwood/overlay_tap_resin");
    private final IconCoordinate tappedOverlay = TextureRegistry.getTexture("industry:block/log/rubberwood/overlay_tapped");
    private final IconCoordinate tappedTreetapOverlay = TextureRegistry.getTexture("industry:block/log/rubberwood/overlay_tap_tapped");

    public BlockModelLogRubberwood(Block<BlockLogicLogRubberwood> block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        super.render(tessellator, x, y, z);
        int meta = renderBlocks.blockAccess.getBlockMetadata(x, y, z);
        Axis axis = BlockLogicAxisAligned.metaToAxis(meta & BlockLogicLogRubberwood.MASK_AXIS);
        switch (axis) {
            case Y:
                renderBlocks.uvRotateEast = 0;
                renderBlocks.uvRotateWest = 0;
                renderBlocks.uvRotateSouth = 0;
                renderBlocks.uvRotateNorth = 0;
                break;
            case Z:
                renderBlocks.uvRotateSouth = 1;
                renderBlocks.uvRotateNorth = 1;
                break;
            case X:
                renderBlocks.uvRotateEast = 1;
                renderBlocks.uvRotateWest = 1;
                renderBlocks.uvRotateTop = 1;
                renderBlocks.uvRotateBottom = 1;
                break;
        }

        float brightness = 1.0F;

        AABB bounds = this.block.getBounds();
        if ((meta & BlockLogicLogRubberwood.MASK_RESIN) != 0) {
            if ((meta & BlockLogicLogRubberwood.MASK_TAPPED) == 0) {
                if ((meta & BlockLogicLogRubberwood.MASK_TREETAP) == 0) {
                    renderBlocks.overrideBlockTexture = resinOverlay;
                } else {
                    renderBlocks.overrideBlockTexture = resinTreetapOverlay;
                }
            } else if ((meta & BlockLogicLogRubberwood.MASK_TREETAP) == 0) {
                renderBlocks.overrideBlockTexture = tappedOverlay;
            } else {
                renderBlocks.overrideBlockTexture = tappedTreetapOverlay;
            }
        }

        if (axis == Axis.X) {
            switch ((meta & BlockLogicLogRubberwood.MASK_RESIN_SIDE) >> 2) {
                case 0:
                    tessellator.setColorOpaque_F(0.5F * brightness, 0.5F * brightness, 0.5F * brightness);
                    renderBottomFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
                case 1:
                    tessellator.setColorOpaque_F(brightness, brightness, brightness);
                    renderTopFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
                case 2:
                    tessellator.setColorOpaque_F(0.8F * brightness, 0.8F * brightness, 0.8F * brightness);
                    this.renderNorthFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
                case 3:
                    tessellator.setColorOpaque_F(0.8F * brightness, 0.8F * brightness, 0.8F * brightness);
                    this.renderSouthFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
            }
        } else if (axis == Axis.Y) {
            switch ((meta & BlockLogicLogRubberwood.MASK_RESIN_SIDE) >> 2) {
                case 0:
                    tessellator.setColorOpaque_F(0.8F * brightness, 0.8F * brightness, 0.8F * brightness);
                    this.renderNorthFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
                case 1:
                    tessellator.setColorOpaque_F(0.8F * brightness, 0.8F * brightness, 0.8F * brightness);
                    this.renderSouthFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
                case 2:
                    tessellator.setColorOpaque_F(0.6F * brightness, 0.6F * brightness, 0.6F * brightness);
                    this.renderWestFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
                case 3:
                    tessellator.setColorOpaque_F(0.6F * brightness, 0.6F * brightness, 0.6F * brightness);
                    this.renderEastFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
            }
        } else if (axis == Axis.Z) {
            switch ((meta & BlockLogicLogRubberwood.MASK_RESIN_SIDE) >> 2) {
                case 0:
                    tessellator.setColorOpaque_F(0.5F * brightness, 0.5F * brightness, 0.5F * brightness);
                    renderBottomFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
                case 1:
                    tessellator.setColorOpaque_F(brightness, brightness, brightness);
                    renderTopFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
                case 2:
                    tessellator.setColorOpaque_F(0.6F * brightness, 0.6F * brightness, 0.6F * brightness);
                    this.renderWestFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
                case 3:
                    tessellator.setColorOpaque_F(0.6F * brightness, 0.6F * brightness, 0.6F * brightness);
                    this.renderEastFace(tessellator, bounds, x, y, z, renderBlocks.overrideBlockTexture);
                    break;
            }
        }

        renderBlocks.overrideBlockTexture = null;

        this.resetRenderBlocks();
        return true;
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        return 6 * (data & BlockLogicLogRubberwood.MASK_AXIS) + side.getId() >= Sides.orientationLookUpXYZAligned.length ? TextureRegistry.getTexture("minecraft:block/grass_top") : super.getBlockTextureFromSideAndMetadata(Side.getSideById(Sides.orientationLookUpXYZAligned[6 * (data & BlockLogicLogRubberwood.MASK_AXIS) + side.getId()]), data);
    }
}
