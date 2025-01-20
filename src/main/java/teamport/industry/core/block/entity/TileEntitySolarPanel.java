package teamport.industry.core.block.entity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.weather.Weather;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.energy.api.IEnergyItem;
import sunsetsatellite.catalyst.energy.impl.TileEntityEnergyConductor;

/*
 * ===========================================================================
 * File: TileEntitySolarPanel.java
 * Brief: Tile Entity for the Solar Panels
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class TileEntitySolarPanel extends TileEntityEnergyConductor implements IInventory {
    public ItemStack[] invSlots = new ItemStack[1];

    public TileEntitySolarPanel() {
        setCapacity(1);
        setMaxProvide(1);
        setMaxReceive(0);

        for (Direction dir : Direction.values()) {
            setConnection(dir, dir == Direction.Y_POS ? Connection.NONE : Connection.OUTPUT);
        }
    }

    public boolean isDayAndClear() {
        return worldObj.canBlockSeeTheSky(x, y + 1, z) && worldObj.getCurrentWeather() == Weather.overworldClear && worldObj.isDaytime();
    }

    @Override
    public void tick() {
        super.tick();

        if (invSlots[0] != null && invSlots[0].getItem() instanceof IEnergyItem) {
            provide(invSlots[0], 1, false);
        }

        if (isDayAndClear()) {
            modifyEnergy(1);
        }
    }

    @Override
    public int getSizeInventory() {
        return invSlots.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return invSlots[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (invSlots[i] != null) {
            if (invSlots[i].stackSize <= j) {
                ItemStack contents = invSlots[i];

                invSlots[i] = null;
                return contents;
            } else {
                ItemStack splitStack = invSlots[i].splitStack(j);
                if (invSlots[i].stackSize <= 0) invSlots[i] = null;

                return splitStack;
            }
        }

        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        invSlots[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
            itemStack.stackSize = getInventoryStackLimit();

        onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "Industry_SolarPanel";
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return worldObj.getBlockTileEntity(x, y, z) == this &&
                player.distanceToSqr(x + 0.5, y + 0.5, z + 0.5) <= 64;
    }

    @Override
    public void sortInventory() {

    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        super.writeToNBT(tag);

        ListTag listTag = new ListTag();
        for (int i = 0; i < invSlots.length; i++) {
            if (invSlots[i] != null) {
                CompoundTag slotTag = new CompoundTag();

                slotTag.putInt("Slots", i);
                invSlots[i].writeToNBT(slotTag);
                listTag.addTag(slotTag);
            }
        }
        tag.put("Items", listTag);
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        super.readFromNBT(tag);
        ListTag listTag = tag.getList("Items");
        invSlots = new ItemStack[getSizeInventory()];

        for (int i = 0; i < listTag.tagCount(); i++) {
            CompoundTag slotTag = (CompoundTag) listTag.tagAt(i);
            int slot = slotTag.getInteger("Slots");

            if (slot >= 0 && slot < invSlots.length)
                invSlots[slot] = ItemStack.readItemStackFromNbt(slotTag);
        }
    }
}
