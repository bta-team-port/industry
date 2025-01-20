package teamport.industry.core.block.logic;

import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.industry.core.block.entity.TileEntityCopperCable;
import teamport.industry.core.item.IndItems;

/*
 * ===========================================================================
 * File: BlockLogicInsulatedCopperCable.java
 * Brief: Block logic for the insulated copper cables
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class BlockLogicInsulatedCopperCable extends BlockTileEntity {
    public BlockLogicInsulatedCopperCable(String key, int id) {
        super(key, id, Material.decoration);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityCopperCable();
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        return new ItemStack[]{new ItemStack(IndItems.INSULATED_COPPER_CABLE)};
    }
}
