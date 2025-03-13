package teamport.industry.core.block.logic.machine;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.energy.electric.api.VoltageTier;
import teamport.industry.Industry;
import teamport.industry.core.block.IndBlocks;
import teamport.industry.core.block.entity.TileEntityMacerator;
import teamport.industry.core.block.logic.base.BlockLogicElectric;

public class BlockLogicMacerator extends BlockLogicElectric {
    public BlockLogicMacerator(String key, int id, VoltageTier tier) {
        super(key, id, Material.metal, tier);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityMacerator();
    }

    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityMacerator) {
            for (ItemStack stack : ((TileEntityMacerator) tileEntity).invSlots) {
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
                        float multi = 0.05F;
                        entityItem.xd = (float) world.rand.nextGaussian() * multi;
                        entityItem.yd = (float) world.rand.nextGaussian() * multi + 0.2F;
                        entityItem.zd = (float) world.rand.nextGaussian() * multi;

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
        } else if (tileEntity instanceof TileEntityMacerator) {
            Catalyst.displayGui(player, tileEntity, ((TileEntityMacerator) tileEntity).getInvName());
        }

        return true;
    }

    @Override
    public ItemStack[] getBreakResult(World world, @NotNull EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
            case PROPER_TOOL:
                return new ItemStack[]{new ItemStack(IndBlocks.MACERATOR)};
            default:
                return new ItemStack[]{new ItemStack(IndBlocks.MACHINE_BLOCK)};
        }
    }

    @Override
    public String getDescription(ItemStack stack) {
        return String.format("%sMax Voltage %sIN%s: %s%dV %s(%s%s%s)\n%sEnergy Capacity: %s%dJ\n",
                TextFormatting.LIGHT_GRAY, TextFormatting.RED, TextFormatting.LIGHT_GRAY,
                TextFormatting.LIME, tier.maxVoltage, TextFormatting.LIGHT_GRAY, tier.textColor, tier.name(), TextFormatting.LIGHT_GRAY,
                TextFormatting.LIGHT_GRAY, TextFormatting.YELLOW, 800
        );
    }
}
