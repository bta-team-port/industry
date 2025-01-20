package teamport.industry.core.block.logic;

import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import teamport.industry.core.block.entity.TileEntityPipe;

/*
 * ===========================================================================
 * File: BlockLogicPipe.java
 * Brief: Block logic for the pipes (CURRENTLY UNUSED)
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class BlockLogicPipe extends BlockTileEntity {
    public BlockLogicPipe(String key, int id, Material material) {
        super(key, id, material);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityPipe();
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
