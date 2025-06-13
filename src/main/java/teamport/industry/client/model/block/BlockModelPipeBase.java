package teamport.industry.client.model.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import teamport.industry.core.block.logic.pipe.BlockLogicPipeBase;

@Environment(EnvType.CLIENT)
public class BlockModelPipeBase extends BlockModelStandard<BlockLogicPipeBase> {
    public BlockModelPipeBase(Block<BlockLogicPipeBase> block) {
        super(block);
    }

    protected void setBounds(AABB bounds, AABB shape) {
        bounds.set(shape.minX, shape.minY, shape.minZ, shape.maxX, shape.maxY, shape.maxZ);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        AABB bounds = block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z);

        boolean connectUp = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.UP.getOffsetX(),
                y + Direction.UP.getOffsetY(),
                z + Direction.UP.getOffsetZ());

        boolean connectDown = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.DOWN.getOffsetX(),
                y + Direction.DOWN.getOffsetY(),
                z + Direction.DOWN.getOffsetZ());

        boolean connectEast = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.EAST.getOffsetX(),
                y + Direction.EAST.getOffsetY(),
                z + Direction.EAST.getOffsetZ());

        boolean connectWest = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.WEST.getOffsetX(),
                y + Direction.WEST.getOffsetY(),
                z + Direction.WEST.getOffsetZ());

        boolean connectNorth = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.NORTH.getOffsetX(),
                y + Direction.NORTH.getOffsetY(),
                z + Direction.NORTH.getOffsetZ());

        boolean connectSouth = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + Direction.SOUTH.getOffsetX(),
                y + Direction.SOUTH.getOffsetY(),
                z + Direction.SOUTH.getOffsetZ());

        // Base bounds
        setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.25, 0.75, 0.75, 0.75));
        setRenderSide(Side.TOP, !connectUp);
        setRenderSide(Side.BOTTOM, !connectDown);
        setRenderSide(Side.EAST, !connectEast);
        setRenderSide(Side.WEST, !connectWest);
        setRenderSide(Side.NORTH, !connectNorth);
        setRenderSide(Side.SOUTH, !connectSouth);
        renderStandardBlock(tessellator, bounds, x, y, z);
        resetRenderBlocks();

        if (connectUp) {
            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.25, 0.75, 1.0, 0.75));
            setRenderSide(Side.TOP, false);
            setRenderSide(Side.BOTTOM, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();
        }

        if (connectDown) {
            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.0, 0.25, 0.75, 0.25, 0.75));
            setRenderSide(Side.TOP, false);
            setRenderSide(Side.BOTTOM, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();
        }

        if (connectEast) {
            setBounds(bounds, AABB.getTemporaryBB(0.75, 0.25, 0.25, 1.0, 0.75, 0.75));
            setRenderSide(Side.EAST, false);
            setRenderSide(Side.WEST, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();
        }

        if (connectWest) {
            setBounds(bounds, AABB.getTemporaryBB(0.0, 0.25, 0.25, 0.25, 0.75, 0.75));
            setRenderSide(Side.EAST, false);
            setRenderSide(Side.WEST, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();
        }

        if (connectNorth) {
            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.0, 0.75, 0.75, 0.25));
            setRenderSide(Side.NORTH, false);
            setRenderSide(Side.SOUTH, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();
        }

        if (connectSouth) {
            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.75, 0.75, 0.75, 1.0));
            setRenderSide(Side.NORTH, false);
            setRenderSide(Side.SOUTH, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();
        }

        return true;
    }
}
