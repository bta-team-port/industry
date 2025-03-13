package teamport.industry.client.model.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Direction;
import sunsetsatellite.catalyst.energy.electric.api.IElectric;
import sunsetsatellite.catalyst.energy.electric.api.IElectricWire;
import teamport.industry.core.block.logic.base.BlockLogicCableBase;

/**
 * Client model renderer for the raw cables (4x4)
 * @author Cookie
 * @date 2024-12-24
 */
@Environment(EnvType.CLIENT)
public class BlockModelCable extends BlockModelStandard<BlockLogicCableBase> {
    public BlockModelCable(Block block) {
        super(block);
    }

    @Override
    public boolean render(Tessellator tessellator, int x, int y, int z) {
        float width = 0.125F;
        float halfWidth = (1.0F - width) / 2.0F;

        boolean connectNorth = block.canConnectTo(renderBlocks.blockAccess,
                x + Direction.NORTH.getOffsetX(),
                y + Direction.NORTH.getOffsetY(),
                z + Direction.NORTH.getOffsetZ());

        boolean connectSouth = block.canConnectTo(renderBlocks.blockAccess,
                x + Direction.SOUTH.getOffsetX(),
                y + Direction.SOUTH.getOffsetY(),
                z + Direction.SOUTH.getOffsetZ());

        boolean connectEast = block.canConnectTo(renderBlocks.blockAccess,
                x + Direction.EAST.getOffsetX(),
                y + Direction.EAST.getOffsetY(),
                z + Direction.EAST.getOffsetZ());

        boolean connectWest = block.canConnectTo(renderBlocks.blockAccess,
                x + Direction.WEST.getOffsetX(),
                y + Direction.WEST.getOffsetY(),
                z + Direction.WEST.getOffsetZ());

        boolean connectUp = block.canConnectTo(renderBlocks.blockAccess,
                x + Direction.UP.getOffsetX(),
                y + Direction.UP.getOffsetY(),
                z + Direction.UP.getOffsetZ());

        boolean connectDown = block.canConnectTo(renderBlocks.blockAccess,
                x + Direction.DOWN.getOffsetX(),
                y + Direction.DOWN.getOffsetY(),
                z + Direction.DOWN.getOffsetZ());

        // Base bounds
        block.setBlockBounds(halfWidth, halfWidth, halfWidth,
                halfWidth + width, halfWidth + width, halfWidth + width);
        renderStandardBlock(tessellator, block, x, y, z);

        // Connection bounds
        if (connectEast) {
            block.setBlockBounds(halfWidth + width, halfWidth, halfWidth,
                    1.0F, halfWidth + width, halfWidth + width);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if (connectWest) {
            block.setBlockBounds(0.0F, halfWidth, halfWidth,
                    halfWidth, halfWidth + width, halfWidth + width);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if (connectUp) {
            block.setBlockBounds(halfWidth, halfWidth + width, halfWidth,
                    halfWidth + width, 1.0F, halfWidth + width);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if (connectDown) {
            block.setBlockBounds(halfWidth, 0.0F, halfWidth,
                    halfWidth + width, halfWidth, halfWidth + width);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if (connectSouth) {
            block.setBlockBounds(halfWidth, halfWidth, halfWidth + width,
                    halfWidth + width, halfWidth + width, 1.0F);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        if (connectNorth) {
            block.setBlockBounds(halfWidth, halfWidth, 0.0F, halfWidth + width, halfWidth + width, halfWidth);
            renderStandardBlock(tessellator, block, x, y, z);
        }

        block.setBlockBounds(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);

        return true;
    }
}
