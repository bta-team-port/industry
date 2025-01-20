package teamport.industry.core.block.logic;

import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import teamport.industry.core.block.entity.TileEntityCopperCable;
import teamport.industry.core.block.entity.TileEntityEnergyConductorDamageable;
import teamport.industry.core.item.IndItems;

/*
 * ===========================================================================
 * File: BlockLogicCopperCable.java
 * Brief: Block logic for the raw copper cables
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class BlockLogicCopperCable extends BlockTileEntity {
    public BlockLogicCopperCable(String key, int id) {
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
        return new ItemStack[]{new ItemStack(IndItems.COPPER_CABLE)};
    }

    // TODO - Merge this and insulated cables into one class, maybe using metadata or tile entity checks.
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityEnergyConductorDamageable) {
            if (((TileEntityEnergyConductorDamageable) tileEntity).energy > 0) {
                entity.hurt(null, 2, DamageType.FIRE);
            }
        }
    }
}
