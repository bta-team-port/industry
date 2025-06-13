package teamport.industry.client.model.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import sunsetsatellite.catalyst.core.util.Direction;
import teamport.industry.core.block.entity.TileEntityPipeIron;
import teamport.industry.core.block.logic.pipe.BlockLogicPipeBase;

import static net.minecraft.core.util.helper.Direction.*;

@Environment(EnvType.CLIENT)
public class BlockModelPipeIron extends BlockModelPipeBase {
    private final IconCoordinate sealedTexture = TextureRegistry.getTexture("industry:block/pipe/iron_sealed");
    private final IconCoordinate openTexture = TextureRegistry.getTexture("industry:block/pipe/iron");
    private IconCoordinate currentTexture = sealedTexture;

    public BlockModelPipeIron(Block<BlockLogicPipeBase> block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        AABB bounds = block.getBlockBoundsFromState(renderBlocks.blockAccess, x, y, z);

        TileEntity currentTile = renderBlocks.blockAccess.getTileEntity(x, y, z);
        Direction currOutputDir = null;
        if (currentTile instanceof TileEntityPipeIron)
            currOutputDir = ((TileEntityPipeIron) currentTile).getOutputDirection();

        boolean connectUp = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + UP.getOffsetX(),
                y + UP.getOffsetY(),
                z + UP.getOffsetZ());

        boolean connectDown = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + DOWN.getOffsetX(),
                y + DOWN.getOffsetY(),
                z + DOWN.getOffsetZ());

        boolean connectEast = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + EAST.getOffsetX(),
                y + EAST.getOffsetY(),
                z + EAST.getOffsetZ());

        boolean connectWest = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + WEST.getOffsetX(),
                y + WEST.getOffsetY(),
                z + WEST.getOffsetZ());

        boolean connectNorth = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + NORTH.getOffsetX(),
                y + NORTH.getOffsetY(),
                z + NORTH.getOffsetZ());

        boolean connectSouth = block.getLogic().canConnectTo(renderBlocks.blockAccess,
                x + SOUTH.getOffsetX(),
                y + SOUTH.getOffsetY(),
                z + SOUTH.getOffsetZ());

        TileEntity northTile = renderBlocks.blockAccess.getTileEntity(x + NORTH.getOffsetX(),
                y + NORTH.getOffsetY(),
                z + NORTH.getOffsetZ());

        TileEntity southTile = renderBlocks.blockAccess.getTileEntity(x + SOUTH.getOffsetX(),
                y + SOUTH.getOffsetY(),
                z + SOUTH.getOffsetZ());

        TileEntity eastTile = renderBlocks.blockAccess.getTileEntity(x + EAST.getOffsetX(),
                y + EAST.getOffsetY(),
                z + EAST.getOffsetZ());

        TileEntity westTile = renderBlocks.blockAccess.getTileEntity(x + WEST.getOffsetX(),
                y + WEST.getOffsetY(),
                z + WEST.getOffsetZ());

        TileEntity upTile = renderBlocks.blockAccess.getTileEntity(x + UP.getOffsetX(),
                y + UP.getOffsetY(),
                z + UP.getOffsetZ());

        TileEntity downTile = renderBlocks.blockAccess.getTileEntity(x + DOWN.getOffsetX(),
                y + DOWN.getOffsetY(),
                z + DOWN.getOffsetZ());

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
            currentTexture = (currOutputDir == Direction.Y_POS) ? openTexture : sealedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.25, 0.75, 1.0, 0.75));
            setRenderSide(Side.TOP, false);
            setRenderSide(Side.BOTTOM, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = sealedTexture;
        }

        if (connectDown) {
            currentTexture = (currOutputDir == Direction.Y_NEG) ? openTexture : sealedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.0, 0.25, 0.75, 0.25, 0.75));
            setRenderSide(Side.TOP, false);
            setRenderSide(Side.BOTTOM, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = sealedTexture;
        }

        if (connectEast) {
            currentTexture = (currOutputDir == Direction.X_POS) ? openTexture : sealedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.75, 0.25, 0.25, 1.0, 0.75, 0.75));
            setRenderSide(Side.EAST, false);
            setRenderSide(Side.WEST, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = sealedTexture;
        }

        if (connectWest) {
            currentTexture = (currOutputDir == Direction.X_NEG) ? openTexture : sealedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.0, 0.25, 0.25, 0.25, 0.75, 0.75));
            setRenderSide(Side.EAST, false);
            setRenderSide(Side.WEST, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = sealedTexture;
        }

        if (connectNorth) {
            currentTexture = (currOutputDir == Direction.Z_NEG ? openTexture : sealedTexture);

            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.0, 0.75, 0.75, 0.25));
            setRenderSide(Side.NORTH, false);
            setRenderSide(Side.SOUTH, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = sealedTexture;
        }

        if (connectSouth) {
            currentTexture = (currOutputDir == Direction.Z_POS) ? openTexture : sealedTexture;

            setBounds(bounds, AABB.getTemporaryBB(0.25, 0.25, 0.75, 0.75, 0.75, 1.0));
            setRenderSide(Side.NORTH, false);
            setRenderSide(Side.SOUTH, false);
            renderStandardBlock(tessellator, bounds, x, y, z);
            resetRenderBlocks();

            currentTexture = sealedTexture;
        }

        return true;
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        return currentTexture;
    }
}
