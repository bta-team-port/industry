package teamport.industry.core.block.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.enums.PlacementMode;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.item.IndItems;

import java.util.Random;

public class BlockLogicLogRubberwood extends BlockLogicLog {
    public static final int MASK_AXIS = 0b0000_0011;
    public static final int MASK_RESIN_SIDE = 0b0000_1100;
    public static final int MASK_RESIN = 0b0001_0000;
    public static final int MASK_TAPPED = 0b0010_0000;
    public static final int MASK_TREETAP = 0b0100_0000;
    private final int[] axisIsX = new int[]{0, 1, 2, 3};
    private final int[] axisIsY = new int[]{2, 3, 4, 5};
    private final int[] axisIsZ = new int[]{0, 1, 4, 5};

    public BlockLogicLogRubberwood(Block<?> block) {
        super(block);
    }

    public void onBlockPlacedByMob(World world, int x, int y, int z, @NotNull Side side, Mob mob, double xPlaced, double yPlaced) {
        Axis axis = mob.getPlacementDirection(side, PlacementMode.SIDE).getAxis();
        world.setBlockMetadataWithNotify(x, y, z, axisToMeta(axis) & MASK_AXIS);
    }

    public void onBlockPlacedOnSide(World world, int x, int y, int z, @NotNull Side side, double xPlaced, double yPlaced) {
        Axis axis = side.getAxis();
        world.setBlockMetadataWithNotify(x, y, z, (axisToMeta(axis) & MASK_AXIS));
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        if (world == null || world.isClientSide) {
            return false;
        }

        if (!logHasResin(world, x, y, z)) {
            return false;
        }

        ItemStack heldStack = player.getHeldItem();

        int meta = world.getBlockMetadata(x, y, z);
        int resinSide = getLogResinSide(world, x, y, z);
        Axis axis = metaToAxis(meta & MASK_AXIS);

        // Held item check
        // If it's a treetap and the log doesn't have one, then it sets the state and gives it a treetap.
        // OTHERWISE, if the tree has a treetap and the player is NOT sneaking...
        // Spawn up to 3 resin and set tapped state. Else if the player IS sneaking...
        // Set treetap state to false and drop the item if the player's gamemode consumes blocks.
        if (heldStack != null) {
            if (heldStack.itemID == IndItems.TREE_TAP.id && !logHasATreetap(world, x, y, z)) {
                switch (axis) {
                    case X:
                        if (side == Side.getSideById(axisIsX[resinSide])) {
                            setLogTreetapState(world, x, y, z, true);
                            player.swingItem();
                            heldStack.consumeItem(player);
                            return true;
                        }
                        break;
                    case Y:
                        if (side == Side.getSideById(axisIsY[resinSide])) {
                            setLogTreetapState(world, x, y, z, true);
                            player.swingItem();
                            heldStack.consumeItem(player);
                            return true;
                        }
                        break;
                    case Z:
                        if (side == Side.getSideById(axisIsZ[resinSide])) {
                            setLogTreetapState(world, x, y, z, true);
                            player.swingItem();
                            heldStack.consumeItem(player);
                            return true;
                        }
                        break;
                }

                return true;
            }
        } else {
            if (logHasATreetap(world, x, y, z)) {
                if (player.isSneaking()) {
                    setLogTreetapState(world, x, y, z, false);

                    if (player.getGamemode().consumeBlocks()) {
                        world.dropItem(x, y, z, new ItemStack(IndItems.TREE_TAP));
                    }

                } else if (!logIsTappedOfResin(world, x, y, z)) {
                    setLogResinTapState(world, x, y, z, true);
                    world.dropItem(x, y, z, new ItemStack(IndItems.STICKY_RESIN, world.rand.nextInt(3) + 1));
                    world.playSoundEffect(null,
                            SoundCategory.WORLD_SOUNDS,
                            x,
                            y,
                            z,
                            "industry:tool.treetap",
                            1.0F,
                            (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);

                    world.scheduleBlockUpdate(x, y, z, block.id(), world.rand.nextInt(3000) + 3000);
                } else if (logIsTappedOfResin(world, x, y, z)) {
                    if (world.rand.nextInt(12) == 0) {
                        world.dropItem(x, y, z, new ItemStack(IndItems.STICKY_RESIN, world.rand.nextInt(3) + 1));
                        world.playSoundEffect(null,
                                SoundCategory.WORLD_SOUNDS,
                                x,
                                y,
                                z,
                                "industry:tool.treetap",
                                1.0F,
                                (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
                    }

                    if (world.rand.nextInt(6) == 0) {
                        setLogResinState(world, x, y, z, false);
                        setLogTreetapState(world, x, y, z, false);
                        world.dropItem(x, y, z, new ItemStack(IndItems.TREE_TAP));
                    }
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (logHasResin(world, x, y, z)) {
            if (logIsTappedOfResin(world, x, y, z)) {
                setLogResinTapState(world, x, y, z, false);
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        world.scheduleBlockUpdate(x, y, z, block.id(), world.rand.nextInt(3000) + 3000);
    }

    public void setLogResinSide(World world, int x, int y, int z, int side) {
        int currentMeta = world.getBlockMetadata(x, y, z);
        int newMeta = (currentMeta & ~MASK_RESIN_SIDE) | ((side << 2) & MASK_RESIN_SIDE);
        world.setBlockMetadataWithNotify(x, y, z, newMeta);
    }

    public int getLogResinSide(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return (meta & MASK_RESIN_SIDE) >> 2;
    }

    public void setLogResinState(World world, int x, int y, int z, boolean flag) {
        int meta = world.getBlockMetadata(x, y, z);
        if (flag) {
            world.setBlockMetadataWithNotify(x, y, z, (meta | MASK_RESIN));
        } else {
            world.setBlockMetadataWithNotify(x, y, z, (meta & ~MASK_RESIN));
        }
    }

    public boolean logHasResin(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return (meta & MASK_RESIN) != 0;
    }

    public void setLogResinTapState(World world, int x, int y, int z, boolean flag) {
        int meta = world.getBlockMetadata(x, y, z);
        if (!logHasResin(world, x, y, z)) {
            return;
        }

        if (flag) {
            world.setBlockMetadataWithNotify(x, y, z, (meta & ~MASK_AXIS) | (meta | MASK_TAPPED));
        } else {
            world.setBlockMetadataWithNotify(x, y, z, (meta & ~MASK_TAPPED));
        }
    }

    public boolean logIsTappedOfResin(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return logHasResin(world, x, y, z) && (meta & MASK_TAPPED) != 0;
    }

    public void setLogTreetapState(World world, int x, int y, int z, boolean flag) {
        int meta = world.getBlockMetadata(x, y, z);
        if (!logHasResin(world, x, y, z)) {
            return;
        }

        if (flag) {
            world.setBlockMetadataWithNotify(x, y, z, (meta & ~MASK_AXIS) | (meta | MASK_TREETAP));
        } else {
            world.setBlockMetadataWithNotify(x, y, z, (meta & ~MASK_TREETAP));
        }
    }

    public boolean logHasATreetap(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return logHasResin(world, x, y, z) && (meta & MASK_TREETAP) != 0;
    }

    @Override
    public ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        if (logHasResin(world, x, y, z)) {
            if (logHasATreetap(world, x, y, z)) {
                return new ItemStack[]{IndItems.TREE_TAP.getDefaultStack(), IndItems.STICKY_RESIN.getDefaultStack(), IndBlocks.LOG_RUBBERWOOD.getDefaultStack()};
            } else {
                return new ItemStack[]{IndItems.STICKY_RESIN.getDefaultStack(), IndBlocks.LOG_RUBBERWOOD.getDefaultStack()};
            }
        }
        return new ItemStack[]{IndBlocks.LOG_RUBBERWOOD.getDefaultStack()};
    }
}
