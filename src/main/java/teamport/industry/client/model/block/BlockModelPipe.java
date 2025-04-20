package teamport.industry.client.model.block;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import teamport.industry.core.block.logic.BlockLogicPipe;

public class BlockModelPipe extends BlockModelStandard<BlockLogicPipe> {
    public BlockModelPipe(Block<BlockLogicPipe> block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        float width = 0.5F;
        float halfWidth = (1.0F - width) / 2.0F;

        boolean connectNorth = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.NORTH.getOffsetX(),
                y + Direction.NORTH.getOffsetY(),
                z + Direction.NORTH.getOffsetZ());

        boolean connectSouth = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.SOUTH.getOffsetX(),
                y + Direction.SOUTH.getOffsetY(),
                z + Direction.SOUTH.getOffsetZ());

        boolean connectEast = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.EAST.getOffsetX(),
                y + Direction.EAST.getOffsetY(),
                z + Direction.EAST.getOffsetZ());

        boolean connectWest = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.WEST.getOffsetX(),
                y + Direction.WEST.getOffsetY(),
                z + Direction.WEST.getOffsetZ());

        boolean connectUp = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.UP.getOffsetX(),
                y + Direction.UP.getOffsetY(),
                z + Direction.UP.getOffsetZ());

        boolean connectDown = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.DOWN.getOffsetX(),
                y + Direction.DOWN.getOffsetY(),
                z + Direction.DOWN.getOffsetZ());

        // Base bounds
        block.getBoundsRaw().set(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
        setRenderSide(Side.TOP, !connectUp);
        setRenderSide(Side.BOTTOM, !connectDown);
        setRenderSide(Side.EAST, !connectEast);
        setRenderSide(Side.WEST, !connectWest);
        setRenderSide(Side.NORTH, !connectNorth);
        setRenderSide(Side.SOUTH, !connectSouth);
        renderStandardBlock(tessellator, block.getBoundsRaw(), x, y, z);
        resetRenderBlocks();

        // Connection bounds
        if (connectEast) {
            block.getBoundsRaw().set(halfWidth + width, halfWidth, halfWidth, 1.0F, halfWidth + width, halfWidth + width);
            setRenderSide(Side.EAST, false);
            setRenderSide(Side.WEST, false);
            renderStandardBlock(tessellator, block.getBoundsRaw(), x, y, z);
            resetRenderBlocks();
        }

        if (connectWest) {
            block.getBoundsRaw().set(0.0F, halfWidth, halfWidth, halfWidth, halfWidth + width, halfWidth + width);
            setRenderSide(Side.EAST, false);
            setRenderSide(Side.WEST, false);
            renderStandardBlock(tessellator, block.getBoundsRaw(), x, y, z);
            resetRenderBlocks();
        }

        if (connectUp) {
            block.getBoundsRaw().set(halfWidth, halfWidth + width, halfWidth, halfWidth + width, 1.0F, halfWidth + width);
            setRenderSide(Side.TOP, false);
            setRenderSide(Side.BOTTOM, false);
            renderStandardBlock(tessellator, block.getBoundsRaw(), x, y, z);
            resetRenderBlocks();
        }

        if (connectDown) {
            block.getBoundsRaw().set(halfWidth, 0.0F, halfWidth, halfWidth + width, halfWidth, halfWidth + width);
            setRenderSide(Side.TOP, false);
            setRenderSide(Side.BOTTOM, false);
            renderStandardBlock(tessellator, block.getBoundsRaw(), x, y, z);
            resetRenderBlocks();
        }

        if (connectSouth) {
            block.getBoundsRaw().set(halfWidth, halfWidth, halfWidth + width, halfWidth + width, halfWidth + width, 1.0F);
            setRenderSide(Side.NORTH, false);
            setRenderSide(Side.SOUTH, false);
            renderStandardBlock(tessellator, block.getBoundsRaw(), x, y, z);
            resetRenderBlocks();
        }

        if (connectNorth) {block.getBoundsRaw().set(halfWidth, halfWidth, 0.0F, halfWidth + width, halfWidth + width, halfWidth);
            setRenderSide(Side.NORTH, false);
            setRenderSide(Side.SOUTH, false);
            renderStandardBlock(tessellator, block.getBoundsRaw(), x, y, z);
            resetRenderBlocks();
        }

        block.getBoundsRaw().set(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
        resetRenderBlocks();
        return true;
    }
}
