package teamport.industry.core.block.logic;

import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.ICustomDescription;
import teamport.industry.Industry;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.block.entity.TileEntityGenerator;

/*
 * ===========================================================================
 * File: BlockLogicGenerator.java
 * Brief: Block logic for the generator
 * Author: Cookie
 * Date: 2025-01-14
 * ===========================================================================
 */

public class BlockLogicGenerator extends BlockTileEntityRotatable implements ICustomDescription {
    public BlockLogicGenerator(String key, int id) {
        super(key, id, Material.metal);
    }


    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityGenerator();
    }

    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityGenerator) {
            for (ItemStack stack : ((TileEntityGenerator) tileEntity).invSlots) {
                if (stack != null) {
                    float randX = world.rand.nextFloat() * 0.8F + 0.1F;
                    float randY = world.rand.nextFloat() * 0.8F + 0.1F;
                    float randZ = world.rand.nextFloat() * 0.8F + 0.1F;

                    while (stack.stackSize > 0) {
                        int randStackSize = world.rand.nextInt(21) + 10;
                        if (randStackSize > stack.stackSize) {
                            randStackSize = stack.stackSize;
                        }

                        stack.stackSize -= randStackSize;
                        EntityItem entityItem = new EntityItem(world,
                                (float) x + randX,
                                (float) y + randY,
                                (float) z + randZ,
                                new ItemStack(stack.itemID,
                                        randStackSize,
                                        stack.getMetadata()));
                        float mult = 0.05F;
                        entityItem.xd = (float) world.rand.nextGaussian() * mult;
                        entityItem.yd = (float) world.rand.nextGaussian() * mult + 0.2F;
                        entityItem.zd = (float) world.rand.nextGaussian() * mult;

                        world.entityJoinedWorld(entityItem);
                    }
                }
            }
        }

        super.onBlockRemoved(world, x, y, z, data);
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity == null || tileEntity.isInvalid()) {
            world.setBlockWithNotify(x, y, z, 0);
            Industry.LOGGER.error("Tile entity at {}, {}, {} was null or invalid and has been removed!", x, y, z);
        } else if (tileEntity instanceof TileEntityGenerator) {
            Catalyst.displayGui(player, tileEntity, ((TileEntityGenerator) tileEntity).getInvName());
        }

        return true;
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
            case PROPER_TOOL:
                return new ItemStack[]{new ItemStack(IndBlocks.GENERATOR)};
            default:
                return new ItemStack[]{new ItemStack(IndBlocks.MACHINE_BLOCK)};
        }
    }

    @Override
    public String getDescription(ItemStack itemStack) {
        return "Capacity: 4000E\nOutput: 32E";
    }

    public static void updateBlockMetadata(World world, int x, int y, int z, boolean active) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, active ? meta + 6 : meta - 6);
    }
}
