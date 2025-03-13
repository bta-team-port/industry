package teamport.industry.core.block.entity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.sound.SoundCategory;
import sunsetsatellite.catalyst.energy.electric.api.IElectricItem;
import teamport.industry.core.recipe.IndRecipes;
import teamport.industry.core.recipe.entry.RecipeEntryMacerator;

import java.util.List;

/**
 * Client gui for the macerator tile entity
 * @author Cookie
 * @date 2025-03-10
 */
public class TileEntityMacerator extends TileEntityMachine implements IInventory {
    public boolean active;
    public ItemStack[] invSlots = new ItemStack[3];
    private int machineTime = 0;
    private int soundLoop = 0;
    private final int maxMachineTime = 400;

    @Override
    public void init(Block block) {
        super.init(block);
        capacity = 800;
    }

    @Override
    public void tick() {
        super.tick();

        active = machineTime > 0;
        boolean hasEnergy = energy > 0;

        // Battery energy consumption (slot 1)
        if (getEnergy() + 10 <= getCapacity()) {
            ItemStack stack = invSlots[1];
            if (stack != null && stack.getItem() instanceof IElectricItem) {
                IElectricItem batt = (IElectricItem) stack.getItem();

                if (batt.getEnergy(stack) - 10 >= 0) {
                    internalAddEnergy(10);
                    batt.discharge(stack, 10);
                }
            }
        }

        if (worldObj != null && !worldObj.isClientSide) {
            // Sound loop
            if (soundLoop >= 300 || (!canProcess() && soundLoop > 0)) {
                soundLoop = 0;
                worldObj.markBlockNeedsUpdate(x, y, z);
            }

            // This is needed so the texture updates. Only called once at tick 2 to prevent lag.
            if (machineTime == 2) {
                worldObj.markBlockNeedsUpdate(x, y, z);
            }

            // Item processing (slot 0 and slot 2)
            if (hasEnergy) {
                if (machineTime == 0 && canProcess()) {
                    ++machineTime;
                }

                if (machineTime > 0 && canProcess()) {
                    internalRemoveEnergy(2);
                    ++machineTime;

                    if (soundLoop++ <= 0) {
                        worldObj.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "industry.MaceratorOp", 0.3f, 1);
                        worldObj.markBlockNeedsUpdate(x, y, z);
                    }

                    if (machineTime >= maxMachineTime) {
                        machineTime = 0;
                        processItem();
                    }
                } else {
                    machineTime = 0;
                }
            } else {
                machineTime = 0;
            }
        }
    }

    private boolean canProcess() {
        if (invSlots[0] == null) return false;
        if (getEnergy() <= 0) return false;

        List<RecipeEntryMacerator> list = IndRecipes.MACERATOR.getAllRecipes();
        ItemStack itemstack = null;

        for(RecipeEntryMacerator recipeEntryBase : list) {
            if (recipeEntryBase != null && recipeEntryBase.matches(this.invSlots[0]))
                itemstack = recipeEntryBase.getOutput();
        }

        if (itemstack == null) return false;
        if (this.invSlots[2] == null) return true;
        if (!this.invSlots[2].isItemEqual(itemstack)) return false;
        if (this.invSlots[2].stackSize < this.getInventoryStackLimit() &&
                this.invSlots[2].stackSize < this.invSlots[2].getMaxStackSize()) return true;
        else return this.invSlots[2].stackSize < itemstack.getMaxStackSize();
    }

    private void processItem() {
        if (this.canProcess()) {
            List<RecipeEntryMacerator> list = IndRecipes.MACERATOR.getAllRecipes();
            ItemStack itemstack = null;

            for(RecipeEntryMacerator recipeEntryBase : list) {
                if (recipeEntryBase != null && recipeEntryBase.matches(this.invSlots[0]))
                    itemstack = recipeEntryBase.getOutput();
            }

            if (this.invSlots[2] == null && itemstack != null) this.invSlots[2] = itemstack.copy();
            else if (this.invSlots[2] != null && itemstack != null && this.invSlots[2].itemID == itemstack.itemID) {
                ItemStack var10000 = this.invSlots[2];
                var10000.stackSize += itemstack.stackSize;
            }

            --this.invSlots[0].stackSize;
            if (this.invSlots[0].stackSize <= 0) this.invSlots[0] = null;

        }
    }

    public int getMachineProgressScaled(int i) {
        return machineTime == 0 ? 0 : machineTime * i / maxMachineTime;
    }

    public int getEnergyScaled(int i) {
        return getEnergy() == 0 ? 0 : (int) (getEnergy() * i / getCapacity());
    }

    public int getMachineTime() {
        return machineTime;
    }

    public void setMachineTime(int amount) {
        machineTime = amount;
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
        return "Industry_Macerator";
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
        tag.putInt("MachineTime", machineTime);

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
        machineTime = tag.getInteger("MachineTime");
        ListTag listTag = tag.getList("Items");
        invSlots = new ItemStack[getSizeInventory()];

        for (int i = 0; i < listTag.tagCount(); i++) {
            CompoundTag slotTag = (CompoundTag) listTag.tagAt(i);
            int slot = slotTag.getInteger("Slots");

            if (slot >= 0 && slot < invSlots.length) {
                invSlots[slot] = ItemStack.readItemStackFromNbt(slotTag);
            }
        }
    }
}
