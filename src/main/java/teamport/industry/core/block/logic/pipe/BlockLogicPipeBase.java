package teamport.industry.core.block.logic.pipe;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.NotNull;
import teamport.industry.core.block.entity.TileEntityPipeBase;
import teamport.industry.extra.interfaces.IBasket;

import java.util.ArrayList;
import java.util.Iterator;

public class BlockLogicPipeBase extends BlockLogic {
    public BlockLogicPipeBase(Block<?> block, Material material) {
        super(block, material);
        block.withEntity(() -> new TileEntityPipeBase(0, 20 * 3));
        setBlockBounds(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);
    }

    public boolean canConnectTo(@NotNull WorldSource worldSource, int x, int y, int z) {
        Block<?> b = worldSource.getBlock(x, y, z);
        if (b == null) return false;
        TileEntity tile = worldSource.getTileEntity(x, y, z);
        if (!(tile instanceof TileEntityPipeBase || tile instanceof IBasket || tile instanceof Container)) {
            return false;
        }

        // If this is a stone-type pipe, and we're connecting to another pipe, check material compatibility.
        if (isStoneType(material) && tile instanceof TileEntityPipeBase) {
            Block<?> block = worldSource.getBlock(x, y, z);
            if (block != null) {
                if (block.getLogic() instanceof BlockLogicPipeBase) {
                    BlockLogicPipeBase otherPipe = (BlockLogicPipeBase) block.getLogic();

                    // Allow connection if:
                    // 1. The other pipe is not a stone type.
                    // 2. The other pipe is the exact same material type.
                    return !isStoneType(otherPipe.material) || material == otherPipe.material;
                }
            }
        }
        return true;
    }


    private boolean isStoneType(Material material) {
        return material == Material.stone ||
                material == Material.basalt ||
                material == Material.limestone ||
                material == Material.granite ||
                material == Material.netherrack ||
                material == Material.permafrost;
    }

    @Override
    public void setBlockBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        bounds.set(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean isCubeShaped() {
        return false;
    }

    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
        TileEntityPipeBase tile = (TileEntityPipeBase) world.getTileEntity(x, y, z);
        final Iterator<TileEntityPipeBase.PipeItem> iter = tile.getContents().iterator();

        if (iter.hasNext()) {
            tile.dropItem(iter.next(), iter);
        }
    }

    @Override
    public AABB getBlockBoundsFromState(WorldSource world, int x, int y, int z) {
        return super.getBlockBoundsFromState(world, x, y, z);
    }

    @Override
    public void getCollidingBoundingBoxes(World world, int x, int y, int z, AABB aabb, ArrayList<AABB> aabbList) {
        boolean connectUp = canConnectTo(world,
                x + Direction.UP.getOffsetX(),
                y + Direction.UP.getOffsetY(),
                z + Direction.UP.getOffsetZ());

        boolean connectDown = canConnectTo(world,
                x + Direction.DOWN.getOffsetX(),
                y + Direction.DOWN.getOffsetY(),
                z + Direction.DOWN.getOffsetZ());

        boolean connectEast = canConnectTo(world,
                x + Direction.EAST.getOffsetX(),
                y + Direction.EAST.getOffsetY(),
                z + Direction.EAST.getOffsetZ());

        boolean connectWest = canConnectTo(world,
                x + Direction.WEST.getOffsetX(),
                y + Direction.WEST.getOffsetY(),
                z + Direction.WEST.getOffsetZ());

        boolean connectNorth = canConnectTo(world,
                x + Direction.NORTH.getOffsetX(),
                y + Direction.NORTH.getOffsetY(),
                z + Direction.NORTH.getOffsetZ());

        boolean connectSouth = canConnectTo(world,
                x + Direction.SOUTH.getOffsetX(),
                y + Direction.SOUTH.getOffsetY(),
                z + Direction.SOUTH.getOffsetZ());

        // Center bounding box
        addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(
                x + 0.25,
                y + 0.25,
                z + 0.25,
                x + 0.75,
                y + 0.75,
                z + 0.75), aabbList);

        if (connectUp) {
            addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(
                    x + 0.25,
                    y + 0.75,
                    z + 0.25,
                    x + 0.75,
                    y + 1.0,
                    z + 0.75), aabbList);
        }

        if (connectDown) {
            addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(
                    x + 0.25,
                    y + 0.0,
                    z + 0.25,
                    x + 0.75,
                    y + 0.25,
                    z + 0.75), aabbList);
        }

        if (connectEast) {
            addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(
                    x + 0.75,
                    y + 0.25,
                    z + 0.25,
                    x + 0.75,
                    y + 0.75,
                    z + 1.0), aabbList);
        }

        if (connectWest) {
            addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(
                    x + 0.0,
                    y + 0.25,
                    z + 0.25,
                    x + 0.25,
                    y + 0.75,
                    z + 0.75), aabbList);
        }

        if (connectNorth) {
            addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(
                    x + 0.25,
                    y + 0.25,
                    z + 0.75,
                    x + 0.75,
                    y + 0.75,
                    z + 1.0), aabbList);
        }

        if (connectSouth) {
            addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(
                    x + 0.25,
                    y + 0.25,
                    z + 0.0,
                    x + 0.75,
                    y + 0.75,
                    z + 0.25), aabbList);
        }
    }
}
