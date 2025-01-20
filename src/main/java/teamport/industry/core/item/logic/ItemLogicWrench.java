package teamport.industry.core.item.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.industry.core.block.IndBlockTags;
import teamport.industry.core.block.entity.TileEntityPipe;
import teamport.industry.core.block.logic.BlockLogicPipe;

/**
 * Item logic for the wrench
 * @author Cookie
 * @date 2024-12-24
 */
public class ItemLogicWrench extends Item {
    public ItemLogicWrench(String name, int id) {
        super(name, id);
        setMaxDamage(63);
        setMaxStackSize(1);
    }

    @Override
    public boolean onUseItemOnBlock(ItemStack itemstack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        Block block = world.getBlock(blockX, blockY, blockZ);
        int meta = world.getBlockMetadata(blockX, blockY, blockZ);

        if (block.hasTag(IndBlockTags.REQUIRES_WRENCH)) {
            itemstack.damageItem(1, player);

            block.dropBlockWithCause(world, EnumDropCause.PROPER_TOOL, blockX, blockY, blockZ, meta, null);
            world.setBlockWithNotify(blockX, blockY, blockZ, 0);
            world.playSoundEffect(player,
                    SoundCategory.ENTITY_SOUNDS,
                    player.x,
                    player.y,
                    player.z,
                    "industry.wrench",
                    0.4f,
                    1.0f);

            return true;
        }

        if (block instanceof BlockLogicPipe) {
            TileEntity tileEntity = world.getBlockTileEntity(blockX, blockY, blockZ);
            if (tileEntity instanceof TileEntityPipe) {
                world.playSoundEffect(player,
                        SoundCategory.ENTITY_SOUNDS,
                        player.x,
                        player.y,
                        player.z,
                        "industry.wrench",
                        0.4f,
                        1.0f);
            }
        }

        return false;
    }
}
