package teamport.industry.client.model.block;

import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import teamport.industry.core.block.logic.pipe.BlockLogicPipeBase;
import teamport.industry.extra.interfaces.IBasket;

public class BlockModelPipeWooden extends BlockModelPipeBase {
    private final IconCoordinate connectedTexture = TextureRegistry.getTexture("industry:block/pipe/wooden_input");
    private final IconCoordinate baseTexture = TextureRegistry.getTexture("industry:block/pipe/wooden");
    private IconCoordinate currentTexture = baseTexture;

    public BlockModelPipeWooden(Block<BlockLogicPipeBase> block) {
        super(block);
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

        TileEntity northTile = renderBlocks.blockAccess.getTileEntity(x + Direction.NORTH.getOffsetX(),
                y + Direction.NORTH.getOffsetY(),
                z + Direction.NORTH.getOffsetZ());

        TileEntity southTile = renderBlocks.blockAccess.getTileEntity(x + Direction.SOUTH.getOffsetX(),
                y + Direction.SOUTH.getOffsetY(),
                z + Direction.SOUTH.getOffsetZ());

        TileEntity eastTile = renderBlocks.blockAccess.getTileEntity(x + Direction.EAST.getOffsetX(),
                y + Direction.EAST.getOffsetY(),
                z + Direction.EAST.getOffsetZ());

        TileEntity westTile = renderBlocks.blockAccess.getTileEntity(x + Direction.WEST.getOffsetX(),
                y + Direction.WEST.getOffsetY(),
                z + Direction.WEST.getOffsetZ());

        TileEntity upTile = renderBlocks.blockAccess.getTileEntity(x + Direction.UP.getOffsetX(),
                y + Direction.UP.getOffsetY(),
                z + Direction.UP.getOffsetZ());

        TileEntity downTile = renderBlocks.blockAccess.getTileEntity(x + Direction.DOWN.getOffsetX(),
                y + Direction.DOWN.getOffsetY(),
                z + Direction.DOWN.getOffsetZ());

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
            if (upTile instanceof Container || upTile instanceof IBasket) {
                currentTexture = connectedTexture;
            }

            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.25, 0.75, 1.0, 0.75));
            setRenderSide(Side.TOP, false);
            setRenderSide(Side.BOTTOM, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = baseTexture;
        }

        if (connectDown) {
            if (downTile instanceof Container || downTile instanceof IBasket) currentTexture = connectedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.0, 0.25, 0.75, 0.25, 0.75));
            setRenderSide(Side.TOP, false);
            setRenderSide(Side.BOTTOM, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = baseTexture;
        }

        if (connectEast) {
            if (eastTile instanceof Container || eastTile instanceof IBasket) currentTexture = connectedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.75, 0.25, 0.25, 1.0, 0.75, 0.75));
            setRenderSide(Side.EAST, false);
            setRenderSide(Side.WEST, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = baseTexture;
        }

        if (connectWest) {
            if (westTile instanceof Container || westTile instanceof IBasket) currentTexture = connectedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.0, 0.25, 0.25, 0.25, 0.75, 0.75));
            setRenderSide(Side.EAST, false);
            setRenderSide(Side.WEST, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = baseTexture;
        }

        if (connectNorth) {
            if (northTile instanceof Container || northTile instanceof IBasket) currentTexture = connectedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.0, 0.75, 0.75, 0.25));
            setRenderSide(Side.NORTH, false);
            setRenderSide(Side.SOUTH, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = baseTexture;
        }

        if (connectSouth) {
            if (southTile instanceof Container || southTile instanceof IBasket) currentTexture = connectedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.75, 0.75, 0.75, 1.0));
            setRenderSide(Side.NORTH, false);
            setRenderSide(Side.SOUTH, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = baseTexture;
        }

        return true;
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        return currentTexture;
    }
}
