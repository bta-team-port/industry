package teamport.industry.core.block.logic.base;

import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.core.util.ConduitCapability;
import sunsetsatellite.catalyst.core.util.IConduitBlock;
import sunsetsatellite.catalyst.core.util.ISideInteractable;
import sunsetsatellite.catalyst.core.util.network.NetworkComponent;
import sunsetsatellite.catalyst.core.util.network.NetworkType;
import sunsetsatellite.catalyst.energy.electric.api.WireProperties;
import sunsetsatellite.catalyst.energy.electric.base.TileEntityElectricConductor;
import teamport.industry.core.block.entity.TileEntityCable;

/**
 * Base class for all cables and wires.
 * @date 2025-01-20
 * @author sunsetsatellite
 */
public class BlockLogicCable extends BlockTileEntity implements NetworkComponent, IConduitBlock, ISideInteractable {

    public final WireProperties properties;

    public BlockLogicCable(int id, WireProperties properties) {
        super("null", id, Material.decoration);
        this.properties = properties;
    }

    public WireProperties getProperties() {
        return properties;
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
    public NetworkType getType() {
        return NetworkType.ELECTRIC;
    }

    @Override
    public ConduitCapability getConduitCapability() {
        return ConduitCapability.ELECTRIC;
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityCable();
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if(!properties.isInsulated()){
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityElectricConductor){
                if (((TileEntityElectricConductor) tileEntity).getAverageAmpLoad() > 0) {
                    entity.hurt(null, 2, DamageType.FIRE);
                }
            }
        }
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        return new ItemStack[]{new ItemStack(this)};
    }
}
