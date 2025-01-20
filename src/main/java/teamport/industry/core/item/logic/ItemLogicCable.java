package teamport.industry.core.item.logic;

import net.minecraft.core.item.ItemPlaceable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import sunsetsatellite.catalyst.core.util.ICustomDescription;
import sunsetsatellite.catalyst.energy.electric.api.VoltageTier;
import sunsetsatellite.catalyst.energy.electric.api.WireMaterial;
import teamport.industry.core.block.logic.base.BlockLogicCable;

/**
 * Item logic for cables
 * @author Cookie, sunsetsatellite
 * @date 2024-12-24
 */
public class ItemLogicCable extends ItemPlaceable implements ICustomDescription {

    public BlockLogicCable cableBlock;

    public ItemLogicCable(String name, int id, BlockLogicCable blockToPlace) {
        super(name, id, blockToPlace);
        this.cableBlock = blockToPlace;
    }


    @Override
    public String getDescription(ItemStack stack) {
        WireMaterial mat = cableBlock.properties.getMaterial();
        VoltageTier voltage = mat.getMaxVoltage();
        String superconductor = cableBlock.properties.isSuperconductor() ? TextFormatting.MAGENTA + voltage.name()+" Superconductor\n" : "";
        return superconductor+String.format("%sMax Voltage: %s%dV %s(%s%s%s)\n%sMax Current: %s%dA\n%sVoltage Drop: %s%dV%s/block\n",
                TextFormatting.LIGHT_GRAY,
                TextFormatting.LIME, voltage.maxVoltage, TextFormatting.LIGHT_GRAY, voltage.textColor, voltage.name(), TextFormatting.LIGHT_GRAY,
                TextFormatting.LIGHT_GRAY,
                TextFormatting.ORANGE, cableBlock.properties.getSize() * mat.getDefaultAmps(),
                TextFormatting.LIGHT_GRAY,
                TextFormatting.RED, mat.getLossPerBlock(), TextFormatting.LIGHT_GRAY
        );
    }
}
