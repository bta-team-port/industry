package teamport.industry.core.item.logic;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemPlaceable;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.core.util.ICustomDescription;

/*
 * ===========================================================================
 * File: ItemLogicCable.java
 * Brief: Item logic for the cables
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class ItemLogicCable extends ItemPlaceable implements ICustomDescription {
    private final String cableVoltage;

    public ItemLogicCable(String name, int id, Block blockToPlace, String cableVoltage) {
        super(name, id, blockToPlace);
        this.cableVoltage = cableVoltage;
    }

    @Override
    public String getDescription(ItemStack itemStack) {
        return String.format("§8Max Voltage: %sE", cableVoltage);
    }
}
