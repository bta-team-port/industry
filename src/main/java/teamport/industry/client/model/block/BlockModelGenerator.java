package teamport.industry.client.model.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;

@Environment(EnvType.CLIENT)
public class BlockModelGenerator extends BlockModelHorizontalRotation<Block> {
    private final IconCoordinate front;
    private final IconCoordinate frontAlternate;

    public BlockModelGenerator(Block block, String front, String frontAlternate) {
        super(block);
        this.front = TextureRegistry.getTexture(front);
        this.frontAlternate = TextureRegistry.getTexture(frontAlternate);
    }

    @Override
    public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
        int index = Sides.orientationLookUpHorizontal[6 * Math.min(data, 5) + side.getId()];
        atlasIndices[Side.NORTH.getId()] = data > 5 ? frontAlternate : front;

        return this.atlasIndices[index];
    }
}
