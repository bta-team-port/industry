package teamport.industry.core.block.entity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.weather.Weather;
import sunsetsatellite.catalyst.energy.electric.api.IElectricItem;
import sunsetsatellite.catalyst.energy.electric.base.TileEntityElectricGenerator;
import teamport.industry.core.block.logic.base.BlockLogicElectric;

/**
 * Logic for the solar panel.
 * @author sunsetsatellite
 * @date 2025-01-20
 */
public class TileEntitySolarPanel extends TileEntityElectricGenerator implements IInventory {

    public ItemStack[] invSlots = new ItemStack[1];

    //change properties as you see fit, these are placeholders
    @Override
    public void init(Block block) {
        super.init(block);
        maxAmpsOut = 1;
        maxAmpsIn = 0;
        maxVoltageIn = 0;
        maxVoltageOut = getTier((BlockLogicElectric) block).maxVoltage;
        capacity = getTier((BlockLogicElectric) block).maxVoltage * 64L;
    }

    public boolean isDayAndClear() {
        return worldObj.canBlockSeeTheSky(x, y + 1, z) && worldObj.getCurrentWeather() == Weather.overworldClear && worldObj.isDaytime();
    }

    @Override
    public void tick() {
        super.tick();

        if (getEnergy() - 10 >= 0) {
            ItemStack stack = invSlots[0];
            if (stack != null && stack.getItem() instanceof IElectricItem) {
                IElectricItem batt = (IElectricItem) stack.getItem();

                if (batt.getEnergy(stack) + 10 <= batt.getCapacity(stack)) {
                    internalRemoveEnergy(10);
                    batt.charge(stack, 10);
                }
            }
        }

        if (isDayAndClear()) {
            internalAddEnergy(1);
        }
    }

    @Override
    public void onOvervoltage(long voltage) {
        //kaboom goes here
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
