package teamport.industry.core.block.entity;

import net.minecraft.core.block.Block;
import sunsetsatellite.catalyst.energy.electric.base.TileEntityElectricConductor;
import teamport.industry.core.block.logic.base.BlockLogicCable;

/**
 * Logic for cables.
 * @author sunsetsatellite
 * @date 2025-01-20
 */

public class TileEntityCable extends TileEntityElectricConductor {

	@Override
	public void init(Block block) {
		properties = ((BlockLogicCable) block).properties;
		voltageRating = properties.getMaterial().getMaxVoltage().maxVoltage;
		ampRating = (long) properties.getSize() * properties.getMaterial().getDefaultAmps();
	}

	@Override
	public void onOvercurrent() {

	}

	@Override
	public void onOvervoltage(long voltage) {

	}
}

