package teamport.industry.core.block.logic.base;

import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.CatalystEnergy;
import sunsetsatellite.catalyst.core.util.ICustomDescription;
import sunsetsatellite.catalyst.core.util.network.NetworkComponent;
import sunsetsatellite.catalyst.core.util.network.NetworkType;
import sunsetsatellite.catalyst.energy.electric.api.IVoltageTiered;
import sunsetsatellite.catalyst.energy.electric.api.VoltageTier;
import teamport.industry.core.block.IndBlocks;

/**
 * Abstract base class for any electric machine.
 * @date 2025-01-20
 * @author sunsetsatellite
 */
public abstract class BlockLogicElectric extends BlockTileEntityRotatable implements NetworkComponent, IVoltageTiered, ICustomDescription {

    public final VoltageTier tier;

    public BlockLogicElectric(String key, int id, Material material, VoltageTier tier) {
        super(key, id, material);
        this.tier = tier;
        withTags(CatalystEnergy.WIRES_CONNECT);
    }

    @Override
    public NetworkType getType() {
        return NetworkType.ELECTRIC;
    }

    @Override
    public VoltageTier getTier() {
        return tier;
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
            case PROPER_TOOL:
                return new ItemStack[]{new ItemStack(this)};
            default:
                return new ItemStack[]{new ItemStack(IndBlocks.MACHINE_BLOCK)};
        }
    }
}
