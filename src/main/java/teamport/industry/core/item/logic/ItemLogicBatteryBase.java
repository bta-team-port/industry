package teamport.industry.core.item.logic;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.core.util.ICustomDescription;
import sunsetsatellite.catalyst.energy.api.IEnergyItem;

/*
 * ===========================================================================
 * File: ItemLogicBatteryBase.java
 * Brief: Base Item Logic for the battery items
 * Author: Cookie
 * Date: 2025-01-19
 * ===========================================================================
 */

public class ItemLogicBatteryBase extends Item implements IEnergyItem, ICustomDescription {
    private final int capacity;
    private final int maxReceive;
    private final int maxProvide;
    public ItemLogicBatteryBase(String name, int id, int capacity, int maxReceive, int maxProvide) {
        super(name, id);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxProvide = maxProvide;
        setMaxStackSize(1);
    }

    @Override
    public int provide(ItemStack itemStack, int amount, boolean test) {
        int provided = Math.min(getEnergy(itemStack), Math.min(maxProvide, amount));
        if (!test) {
            modifyEnergy(itemStack, -provided);
        }

        return provided;
    }

    @Override
    public int receive(ItemStack itemStack, int amount, boolean test) {
        int received = Math.min(capacity - getEnergy(itemStack), Math.min(maxReceive, amount));
        if (!test) {
            modifyEnergy(itemStack, received);
        }

        return received;
    }

    @Override
    public int getEnergy(ItemStack itemStack) {
        return itemStack.getData().getInteger("energy");
    }

    @Override
    public int getCapacity(ItemStack itemStack) {
        if (!itemStack.getData().containsKey("capacity")) {
            itemStack.getData().putInt("capacity", capacity);
            return capacity;
        } else {
            return itemStack.getData().getInteger("capacity");
        }
    }

    @Override
    public int getMaxReceive(ItemStack itemStack) {
        if (!itemStack.getData().containsKey("maxReceive")) {
            itemStack.getData().putInt("maxReceive", maxReceive);
            return maxReceive;
        } else {
            return itemStack.getData().getInteger("maxReceive");
        }
    }

    @Override
    public int getMaxProvide(ItemStack itemStack) {
        return maxProvide;
    }

    @Override
    public void modifyEnergy(ItemStack itemStack, int amount) {
        if (itemStack.getData().getInteger("energy") + amount > getCapacity(itemStack)) {
            itemStack.getData().putInt("energy", getCapacity(itemStack));
        } else if (itemStack.getData().getInteger("energy") + amount < 0) {
            itemStack.getData().putInt("energy", 0);
        } else {
            itemStack.getData().putInt("energy", getEnergy(itemStack) + amount);
        }
    }

    @Override
    public String getDescription(ItemStack itemStack) {
        return String.format("§8Energy: %sE / %sE%n§8Receives: %sE / Provides: %sE",
                getEnergy(itemStack),
                getCapacity(itemStack),
                getMaxReceive(itemStack),
                getMaxProvide(itemStack));
    }


}
