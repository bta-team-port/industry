package teamport.industry.core.block.entity;

import net.minecraft.core.block.Block;
import sunsetsatellite.catalyst.energy.electric.base.TileEntityElectricConductor;
import teamport.industry.core.block.logic.base.BlockLogicCableBase;
import teamport.industry.core.interfaces.IVoltageHealth;

import java.util.Random;

/**
 * Logic for cables.
 * @author sunsetsatellite
 * @date 2025-01-20
 */

public class TileEntityCable extends TileEntityElectricConductor implements IVoltageHealth {
	private int health = 100;

	@Override
	public void init(Block block) {
		properties = ((BlockLogicCableBase) block).properties;
		voltageRating = properties.getMaterial().getMaxVoltage().maxVoltage;
		ampRating = (long) properties.getSize() * properties.getMaterial().getDefaultAmps();
	}

	@Override
	public void onOvercurrent() {

	}

	@Override
	public void onOvervoltage(long voltage) {
		industry$modifyHealth(-2);
	}

	@Override
	public int industry$getHealth() {
		return health;
	}

	@Override
	public void industry$setHealth(int amount) {
		health = amount;
	}

	@Override
	public void industry$modifyHealth(int amount) {
		health += amount;
	}

	@Override
	public void tick() {
		super.tick();

		Random rand = worldObj.rand;
		if (industry$getHealth() <= 50 && industry$getHealth() > 0) {
			for(int i = 0; i < 3; ++i) {
				double dX = (double)x + (double)rand.nextFloat();
				double dY = (double)y + (rand.nextFloat() * 0.5F) + (double)0.5F;
				double dZ = (double)z + (double)rand.nextFloat();
				worldObj.spawnParticle("flame", dX, dY, dZ, 0, 0.1, 0, 0);
				worldObj.spawnParticle("largesmoke", dX, dY, dZ, 0, 0.2, 0, 0);
			}
		} else if (industry$getHealth() <= 0) {
			worldObj.createExplosion(null, x, y, z, 1);
		}

		if (industry$getHealth() < 100) {
			industry$modifyHealth(1);
		}
	}
}

