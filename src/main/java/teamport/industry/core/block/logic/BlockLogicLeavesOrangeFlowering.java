package teamport.industry.core.block.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.IBonemealable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.season.Seasons;
import org.jetbrains.annotations.Nullable;
import teamport.industry.core.item.IndItems;

import java.util.Random;

public class BlockLogicLeavesOrangeFlowering extends BlockLogicLeavesOrange implements IBonemealable {
    public static final int MASK_GROWTH_DATA = 0b1111_0000;
    public static final int MASK_GROWTH_STATE = 0b0000_0001;

    public BlockLogicLeavesOrangeFlowering(Block<?> block) {
        super(block);
    }

    @Override
    public boolean onBonemealUsed(ItemStack stack, @Nullable Player player, World world, int blockX, int blockY, int blockZ, Side side, double d, double e) {
        int meta = world.getBlockMetadata(blockX, blockY, blockZ);
        if (getLeavesGrowthRate(meta) != 0) {
            return false;
        } else {
            if (!world.isClientSide) {
                if (world.getSeasonManager().getCurrentSeason() != Seasons.OVERWORLD_FALL) {
                    return true;
                }

                world.setBlockMetadataWithNotify(blockX, blockY, blockZ, setLeavesGrowthRate(meta, 1));
                if (player == null || player.getGamemode().consumeBlocks()) {
                    --stack.stackSize;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack @Nullable [] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        int growthRate = getLeavesGrowthRate(meta);
        if (dropCause != EnumDropCause.PICK_BLOCK && dropCause != EnumDropCause.SILK_TOUCH) {
            return growthRate == 0 ? null : new ItemStack[]{new ItemStack(IndItems.FOOD_ORANGE, world.rand.nextInt(3) + 1)};
        } else {
            return new ItemStack[]{new ItemStack(block)};
        }
    }

    @Override
    public void onBlockLeftClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        onBlockRightClicked(world, x, y, z, player, side, xHit, yHit);
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        return this.harvest(world, x, y, z, player);
    }

    @Override
    public void onActivatorInteract(World world, int x, int y, int z, TileEntityActivator activator, Direction direction) {
        harvest(world, x, y, z, null);
    }

    public boolean harvest(World world, int x, int y, int z, @Nullable Player player) {
        int meta = world.getBlockMetadata(x, y, z);
        int growthRate = getLeavesGrowthRate(meta);
        if (growthRate > 0) {
            if (player != null) {
                world.playSoundAtEntity(player, player, "item.pickup", 1.0F, 1.0F);
            } else {
                world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "item.pickup", 1.0F, 1.0F);
            }

            if (!world.isClientSide) {
                this.dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, meta, null, null);
            }

            world.setBlockMetadataWithNotify(x, y, z, setLeavesGrowthRate(meta, 0));
            world.scheduleBlockUpdate(x, y, z, block.id(), this.tickDelay());

            return true;
        }

        return false;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        int meta = world.getBlockMetadata(x, y, z);
        int growthRate = getLeavesGrowthRate(meta);
        if (world.getSeasonManager().getCurrentSeason() == Seasons.OVERWORLD_FALL) {
            if (rand.nextInt(20) == 0 && growthRate == 0) {
                world.setBlockMetadataWithNotify(x, y, z, setLeavesGrowthRate(meta, 1));
                world.scheduleBlockUpdate(x, y, z, block.id(), this.tickDelay());
            }
        } else if (growthRate > 0) {
            world.setBlockMetadataWithNotify(x, y, z, meta & 0b1111);
            world.scheduleBlockUpdate(x, y, z, block.id(), this.tickDelay());
        }
    }

    public static int getLeavesGrowthRate(int meta) {
        return (meta & MASK_GROWTH_DATA) >> 4;
    }

    public static int setLeavesGrowthRate(int meta, int rate) {
        return (meta & ~MASK_GROWTH_DATA) | ((rate << 4) & MASK_GROWTH_DATA);
    }
}
