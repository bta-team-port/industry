package teamport.industry.core.block.entity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.sound.SoundCategory;
import sunsetsatellite.catalyst.energy.electric.api.IElectricItem;
import sunsetsatellite.catalyst.energy.electric.base.TileEntityElectricGenerator;
import teamport.industry.core.block.logic.base.BlockLogicElectric;
import teamport.industry.core.block.logic.machine.BlockLogicGenerator;

/**
 * Logic for the generator.
 * @author sunsetsatellite
 * @date 2025-01-20
 */
public class TileEntityGenerator extends TileEntityElectricGenerator implements IInventory {

    public ItemStack[] invSlots = new ItemStack[2];

    private int currentBurnTime = 0;
    private int maxBurnTime = 0;

    private int soundLoop = 0;

    //change properties as you see fit, these are placeholders
    @Override
    public void init(Block block) {
        super.init(block);
        maxAmpsOut = 1;
        maxAmpsIn = 0;
        maxVoltageIn = 0;
        maxVoltageOut = getTier((BlockLogicElectric) block).maxVoltage;
        capacity = 4000;
    }

    @Override
    public void onOvervoltage(long voltage) {
        //kaboom goes here
    }

    @Override
    public void tick() {
        super.tick();

        boolean isBurning = currentBurnTime > 0;
        boolean hasCapacity = energy < capacity;

        if (isBurning) {
            --currentBurnTime;
            internalAddEnergy(3);

            if (soundLoop++ <= 0) {
                worldObj.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "industry.GeneratorLoop", 0.3f, 1);
            }
        }

        if (soundLoop >= 60 || (!isBurning && soundLoop > 0)) {
            soundLoop = 0;
        }

        if (worldObj != null && !worldObj.isClientSide) {
            int meta = worldObj.getBlockMetadata(x, y, z);
            if (meta <= 5 && isBurning) {
                BlockLogicGenerator.updateBlockMetadata(worldObj, x, y, z, true);
                onInventoryChanged();
            } else if (meta > 5 && !isBurning) {
                BlockLogicGenerator.updateBlockMetadata(worldObj, x, y, z, false);
                onInventoryChanged();
            }

            if (!isBurning && hasCapacity) {
                setBurnTimes(getBurnTimeFromItem(invSlots[1]));

                if (invSlots[1] != null) {
                    if (invSlots[1].getItem() == Item.bucketLava) {
                        invSlots[1] = new ItemStack(Item.bucket);
                    } else {
                        --invSlots[1].stackSize;

                        if (invSlots[1].stackSize <= 0) {
                            invSlots[1] = null;
                        }
                    }
                }
            }
        }
    }

    @Override
    public long internalChangeEnergy(long difference) {
        ItemStack stack = invSlots[0];
        if (stack == null || !(stack.getItem() instanceof IElectricItem)) {
            return super.internalChangeEnergy(difference);
        }
        IElectricItem batt = (IElectricItem) stack.getItem();

        averageEnergyTransfer.increment(worldObj,difference);
        return batt.charge(stack, difference);
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
        return "Industry_Generator";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return worldObj.getBlockTileEntity(x, y, z) == this && player.distanceToSqr(x + 0.5, y + 0.5, z + 0.5) <= 64;
    }

    @Override
    public void sortInventory() {

    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        super.writeToNBT(tag);
        tag.putInt("BurnTimes", currentBurnTime);

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
        maxBurnTime = currentBurnTime = tag.getInteger("BurnTimes");

        for (int i = 0; i < listTag.tagCount(); i++) {
            CompoundTag slotTag = (CompoundTag) listTag.tagAt(i);
            int slot = slotTag.getInteger("Slots");

            if (slot >= 0 && slot < invSlots.length)
                invSlots[slot] = ItemStack.readItemStackFromNbt(slotTag);
        }
    }

    public int getCurrentBurnTime() {
        return currentBurnTime;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    public void setBurnTimes(int burnTimes) {
        maxBurnTime = currentBurnTime = burnTimes;
    }

    private int getBurnTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelFurnace.instance.getFuelYield(itemStack.getItem().id);
    }

    public int getRemainingBurnTimeScaled(int i) {
        return this.maxBurnTime == 0 ? 0 : this.currentBurnTime * i / this.maxBurnTime;
    }

    public int getEnergyScaled(int i) {
        return getCapacity() == 0 ? 0 : (int) (getEnergy() * i / getCapacity());
    }

}
