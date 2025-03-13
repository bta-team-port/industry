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
import teamport.industry.core.block.entity.TileEntityMacerator;

/**
 * Client model renderer for the macerator entity.
 * @author Cookie
 * @date 2025-03-11
 */
@Environment(EnvType.CLIENT)
public class BlockModelMacerator extends BlockModelHorizontalRotation<Block> {
    private final IconCoordinate top;
    private final IconCoordinate topAlternate;

    public BlockModelMacerator(Block block, String top, String topAlternate) {
        super(block);
        this.top = TextureRegistry.getTexture(top);
        this.topAlternate = TextureRegistry.getTexture(topAlternate);
    }

    @Override
    public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {

        TileEntity tileEntity = blockAccess.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityMacerator) {
            atlasIndices[Side.TOP.getId()] = ((TileEntityMacerator) tileEntity).active ? topAlternate : top;
        }

        return super.getBlockTexture(blockAccess, x, y, z, side);
    }
}
