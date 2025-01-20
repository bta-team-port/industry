package teamport.industry.core.block.logic.ore;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.industry.core.item.IndItems;

/**
 * Block logic for the uranium ores
 * @author Cookie
 * @date 2024-12-24
 */
public class BlockLogicUraniumOre extends Block {
    public BlockLogicUraniumOre(String key, int id) {
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
                return new ItemStack[]{new ItemStack(IndItems.RAW_URANIUM)};
            default:
                return null;
        }
    }
}
