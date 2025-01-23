package teamport.industry.client.model.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import teamport.industry.core.block.entity.TileEntityGenerator;
import teamport.industry.core.block.entity.TileEntityGeothermalGenerator;

@Environment(EnvType.CLIENT)
public class BlockModelGeothermalGenerator extends BlockModelHorizontalRotation<Block> {
    private final IconCoordinate front;
    private final IconCoordinate frontAlternate;

    public BlockModelGeothermalGenerator(Block block, String front, String frontAlternate) {
        super(block);
        this.front = TextureRegistry.getTexture(front);
        this.frontAlternate = TextureRegistry.getTexture(frontAlternate);
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {

        TileEntity tileEntity = blockAccess.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityGeothermalGenerator) {
            atlasIndices[Side.NORTH.getId()] = ((TileEntityGeothermalGenerator) tileEntity).active ? frontAlternate : front;
        }

        return super.getBlockTexture(blockAccess, x, y, z, side);
    }
}
