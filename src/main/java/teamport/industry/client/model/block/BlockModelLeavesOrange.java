package teamport.industry.client.model.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelLeaves;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import teamport.industry.core.block.logic.BlockLogicLeavesOrangeFlowering;

@Environment(EnvType.CLIENT)
public class BlockModelLeavesOrange extends BlockModelLeaves<BlockLogicLeavesOrangeFlowering> {
    private final IconCoordinate grownOverlay = TextureRegistry.getTexture("industry:block/leaves/orange_overlay");
    private final IconCoordinate floweringOverlay = TextureRegistry.getTexture("industry:block/leaves/orange_flowering_overlay");

    public BlockModelLeavesOrange(Block<BlockLogicLeavesOrangeFlowering> block) {
        super(block, "industry:block/leaves/orange");
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        super.render(tessellator, x, y, z);
        int growthRate = BlockLogicLeavesOrangeFlowering.getLeavesGrowthRate(renderBlocks.blockAccess.getBlockMetadata(x, y, z));
        renderBlocks.overrideBlockTexture = growthRate > 0 ? grownOverlay : floweringOverlay;

        renderStandardBlock(tessellator, this.block.getBoundsRaw(), x, y, z, 1.0F, 1.0F, 1.0F);
        renderBlocks.overrideBlockTexture = null;

        resetRenderBlocks();
        return true;
    }

    public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
        super.renderBlockOnInventory(tessellator, metadata, brightness, alpha, lightmapCoordinate);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        AABB bounds = block.getBoundsRaw();
        IconCoordinate grownCoord = grownOverlay;
        GL11.glColor4f(brightness, brightness, brightness, alpha);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderBottomFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, grownCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderTopFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, grownCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, grownCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, grownCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, grownCoord);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, grownCoord);
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }
}
