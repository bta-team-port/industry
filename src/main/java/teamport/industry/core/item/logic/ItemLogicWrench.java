package teamport.industry.core.item.logic;

import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.industry.core.block.entity.TileEntityPipeIron;

import java.util.Random;

public class ItemLogicWrench extends Item {
    public ItemLogicWrench(String translationKey, String namespaceId, int id) {
        super(translationKey, namespaceId, id);
        setMaxStackSize(1);
    }

    @Override
    public boolean onUseItemOnBlock(ItemStack itemstack, Player player, @NotNull World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        if (player.isSneaking()) return false;

        if (world.getTileEntity(blockX, blockY, blockZ) instanceof TileEntityPipeIron) {
            TileEntityPipeIron pipeTile = (TileEntityPipeIron) world.getTileEntity(blockX, blockY, blockZ);
            pipeTile.rotateOutput();

            player.swingItem();
            world.playSoundEffect(player,
                    SoundCategory.WORLD_SOUNDS,
                    blockX,
                    blockY,
                    blockZ,
                    "industry:tool.wrench",
                    0.3f,
                    1.0f);

            return true;
        }

        return false;
    }

    @Override
    public void onUseByActivator(ItemStack itemStack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, Direction direction) {
        blockX += direction.getOffsetX();
        blockY += direction.getOffsetY();
        blockZ += direction.getOffsetZ();

        if (world.getTileEntity(blockX, blockY, blockZ) instanceof TileEntityPipeIron) {
            TileEntityPipeIron pipeTile = (TileEntityPipeIron) world.getTileEntity(blockX, blockY, blockZ);
            pipeTile.rotateOutput();

            world.playSoundEffect(null,
                    SoundCategory.WORLD_SOUNDS,
                    blockX,
                    blockY,
                    blockZ,
                    "industry:tool.wrench",
                    0.3f,
                    1.0f);
        }
    }
}
