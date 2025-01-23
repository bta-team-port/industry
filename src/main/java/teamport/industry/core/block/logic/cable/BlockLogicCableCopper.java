package teamport.industry.core.block.logic.cable;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.energy.electric.api.WireProperties;
import teamport.industry.core.block.logic.base.BlockLogicCableBase;
import teamport.industry.core.item.IndItems;

public class BlockLogicCableCopper extends BlockLogicCableBase {
    public BlockLogicCableCopper(int id, WireProperties properties) {
        super(id, properties);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        return new ItemStack[]{IndItems.COPPER_CABLE.getDefaultStack()};
    }
}
