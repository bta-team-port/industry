package teamport.industry.core.container;

/*
 * ===========================================================================
 * File: ContainerGenerator.java
 * Brief: Container for the Generator tile entity
 * Author: Cookie
 * Date: 2025-01-14
 * ===========================================================================
 */

import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.catalyst.energy.impl.ContainerEnergy;
import teamport.industry.core.block.entity.TileEntityGenerator;

public class ContainerGenerator extends ContainerEnergy {
    private final TileEntityGenerator tileEntity;
    private int currentBurnTime;
    private int maxBurnTime;

    public ContainerGenerator(InventoryPlayer inventory, TileEntityGenerator tileEntity) {
        this.tileEntity = tileEntity;
        tile = tileEntity;

        addSlot(new Slot(tileEntity, 0, 65, 17));
        addSlot(new Slot(tileEntity, 1, 65, 53));

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
    public boolean isUsableByPlayer(EntityPlayer player) {
        return tileEntity.canInteractWith(player);
    }

    @Override
    public void updateInventory() {
        super.updateInventory();

        for(ICrafting crafter : this.crafters) {
            if (currentBurnTime != tileEntity.getCurrentBurnTime()) {
                crafter.updateCraftingInventoryInfo(this, 0, tileEntity.getCurrentBurnTime());
            }

            if (maxBurnTime != tileEntity.getMaxBurnTime()) {
                crafter.updateCraftingInventoryInfo(this, 1, tileEntity.getMaxBurnTime());
            }
        }

        currentBurnTime = tileEntity.getCurrentBurnTime();
        maxBurnTime = tileEntity.getMaxBurnTime();
    }

    @Override
    public void updateClientProgressBar(int id, int value) {
        if (id == 0 || id == 1) {
            tileEntity.setBurnTimes(value);
        }
    }
}
