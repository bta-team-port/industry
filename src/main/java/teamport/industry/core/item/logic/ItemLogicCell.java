package teamport.industry.core.item.logic;

import net.minecraft.core.HitResult;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import teamport.industry.core.item.IndItems;

import java.util.Objects;

public class ItemLogicCell extends Item {
    public ItemLogicCell(String name, int id) {
        super(name, id);
    }

    public static boolean useCell(EntityPlayer player, ItemStack itemToGive) {
        if (Objects.requireNonNull(player.inventory.getCurrentItem()).stackSize <= 1) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, itemToGive);
            return true;
        } else {
            player.inventory.insertItem(itemToGive, true);
            if (itemToGive.stackSize < 1) {
                player.inventory.getCurrentItem().consumeItem(player);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer player) {
        float f = 1.0F;
        float xRot = player.xRotO + (player.xRot - player.xRotO) * f;
        float yRot = player.yRotO + (player.yRot - player.yRotO) * f;
        double xO = player.xo + (player.x - player.xo) * (double)f;
        float yOff = player instanceof EntityPlayerMP ? player.getHeightOffset() : 0.0F;
        double yO = player.yo + (player.y - player.yo) + (double)yOff;
        double zO = player.zo + (player.z - player.zo) * (double)f;
        Vec3d Vec3DCreate = Vec3d.createVector(xO, yO, zO);

        float yCosDegreeToRad = MathHelper.cos(-yRot * 0.01745329F - (float)Math.PI);
        float ySinDegreeToRad = MathHelper.sin(-yRot * 0.01745329F - (float)Math.PI);
        float xCosDegreeToRad = -MathHelper.cos(-xRot * 0.01745329F);
        float xSinDegreeToRad = MathHelper.sin(-xRot * 0.01745329F);
        float ySinXCos = ySinDegreeToRad * xCosDegreeToRad;
        float yCosXCos = yCosDegreeToRad * xCosDegreeToRad;
        double reachDistance = player.getGamemode().getBlockReachDistance();
        Vec3d vec3DAdd = Vec3DCreate.addVector((double)ySinXCos * reachDistance, (double)xSinDegreeToRad * reachDistance, (double)yCosXCos * reachDistance);
        HitResult hitResult = world.checkBlockCollisionBetweenPoints(Vec3DCreate, vec3DAdd, true);

        if (hitResult != null) {
            if (hitResult.hitType == HitResult.HitType.TILE) {
                int _x = hitResult.x;
                int _y = hitResult.y;
                int _z = hitResult.z;
                if (!world.canMineBlock(player, _x, _y, _z)) {
                    return itemstack;
                }

                if (world.getBlockMaterial(_x, _y, _z) == Material.water && world.getBlockMetadata(_x, _y, _z) == 0) {
                    if (useCell(player, new ItemStack(IndItems.WATER_CELL))) {
                        world.setBlockWithNotify(_x, _y, _z, 0);
                        player.swingItem();
                    }
                } else if (world.getBlockMaterial(_x, _y, _z) == Material.lava && world.getBlockMetadata(_x, _y, _z) == 0) {
                    if (useCell(player, new ItemStack(IndItems.LAVA_CELL))) {
                        world.setBlockWithNotify(_x, _y, _z, 0);
                        player.swingItem();
                    }
                }
            }
        }

        return itemstack;
    }
}
