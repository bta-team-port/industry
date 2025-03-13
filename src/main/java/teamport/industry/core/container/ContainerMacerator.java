package teamport.industry.core.container;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotFurnace;
import teamport.industry.core.block.entity.TileEntityMacerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for the macerator tile entity
 * @author Cookie
 * @date 2025-03-10
 */
public class ContainerMacerator extends Container {
    TileEntityMacerator tileEntity;
    private int energy;
    private int machineTime;

    public ContainerMacerator(InventoryPlayer inventory, TileEntityMacerator tileEntity) {
        this.tileEntity = tileEntity;

        // 0 - input, 1 - battery, 2 - output
        addSlot(new Slot(tileEntity, 0, 56, 17));
        addSlot(new Slot(tileEntity, 1, 56, 53));
        addSlot(new SlotFurnace(inventory.player, tileEntity, 2, 116, 35));

        // PLAYER INVENTORY //
        // Main Inventory
        for (int xSlots = 0; xSlots < 3; ++xSlots) {
            for (int ySlots = 0; ySlots < 9; ySlots++) {
                addSlot(new Slot(inventory, ySlots + xSlots * 9 + 9, 8 + ySlots * 18, 84 + xSlots * 18));
            }
        }

        // Hotbar
        for (int hotbar = 0; hotbar < 9; hotbar++) {
            addSlot(new Slot(inventory, hotbar, 8 + hotbar * 18, 142));
        }
    }

    @Override
    public void updateInventory() {
        super.updateInventory();

        for (ICrafting crafter : crafters) {
            if (energy != tileEntity.getEnergy()) {
                crafter.updateCraftingInventoryInfo(this, 0, (int) tileEntity.getEnergy());
            }

            if (machineTime != tileEntity.getMachineTime()) {
                crafter.updateCraftingInventoryInfo(this, 1, tileEntity.getMachineTime());
            }
        }
    }

    @Override
    public void updateClientProgressBar(int id, int value) {
        if (id == 0) {
            tileEntity.setEnergy(value);
        }

        if (id == 1) {
            tileEntity.setMachineTime(value);
        }
    }

    @Override
    public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return new ArrayList<>();
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return tileEntity.canInteractWith(player);
    }
}
