package teamport.industry.core.block.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.industry.core.item.IndItems;

/*
 * ===========================================================================
 * File: BlockLogicCopperOre.java
 * Brief: Block logic for the copper ores
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class BlockLogicCopperOre extends Block {
    public BlockLogicCopperOre(String key, int id) {
        super(key, id, Material.stone);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            case EXPLOSION:
            case PROPER_TOOL:
                return new ItemStack[]{new ItemStack(IndItems.RAW_COPPER_ORE)};
            default:
                return null;
        }
    }
}
