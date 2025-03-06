package teamport.industry.core.block.entity;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryFurnace;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.sound.SoundCategory;
import sunsetsatellite.catalyst.energy.electric.api.IElectricItem;

import java.util.List;

public class TileEntityMacerator extends TileEntityMachine implements IInventory {
    public boolean active;
    public ItemStack[] invSlots = new ItemStack[3];
    private int machineTime = 0;
    private int soundLoop = 0;
    private final int maxMachineTime = 400;

    @Override
    public void init(Block block) {
        super.init(block);
        capacity = 1200;
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

        // Sound loop
        if (soundLoop >= 300 || (!hasEnergy && soundLoop > 0)) {
            soundLoop = 0;
            worldObj.markBlockNeedsUpdate(x, y, z);
        }

        if (worldObj != null && !worldObj.isClientSide) {
            // Item processing (slot 0 and slot 2)
            if (hasEnergy) {
                if (soundLoop++ <= 0) {
                    worldObj.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "industry.MaceratorOp", 0.3f, 1);
                    worldObj.markBlockNeedsUpdate(x, y, z);
                }

                if (machineTime == 0 && canProcess()) {
                    ++machineTime;
                    ++soundLoop;

                    if (machineTime >= maxMachineTime) {
                        machineTime = 0;
                        processItem();
                    }
                }
            } else {
                machineTime = 0;
            }
        }
    }

    private boolean canProcess() {
        if (invSlots[0] == null) return false;

        List<RecipeEntryFurnace> list = Registries.RECIPES.getAllFurnaceRecipes();
        ItemStack itemstack = null;

        for(RecipeEntryFurnace recipeEntryBase : list) {
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
            List<RecipeEntryFurnace> list = Registries.RECIPES.getAllFurnaceRecipes();
            ItemStack itemstack = null;

            for(RecipeEntryFurnace recipeEntryBase : list) {
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
        return getCapacity() == 0 ? 0 : (int) (getEnergy() * i / getCapacity());
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
}
