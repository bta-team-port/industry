package teamport.industry.core.container;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import teamport.industry.core.block.entity.TileEntityBatBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for the batbox tile entity
 * @author Cookie
 * @date 2025-01-23
 */
public class ContainerBatBox extends Container {
    private TileEntityBatBox tileEntity;
    private int energy;

    public ContainerBatBox(InventoryPlayer inventory, TileEntityBatBox tileEntity) {
        this.tileEntity = tileEntity;

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
    public void updateInventory() {
        super.updateInventory();

        for (ICrafting crafter : crafters) {
            if (energy != tileEntity.getEnergy()) {
                crafter.updateCraftingInventoryInfo(this, 0, (int) tileEntity.getEnergy());
            }
        }
    }

    @Override
    public void updateClientProgressBar(int id, int value) {
        if (id == 0) {
            tileEntity.setEnergy(value);
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
