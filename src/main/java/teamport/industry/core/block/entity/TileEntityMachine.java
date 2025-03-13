package teamport.industry.core.block.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.network.NetworkComponentTile;
import sunsetsatellite.catalyst.core.util.network.NetworkPath;
import sunsetsatellite.catalyst.energy.electric.api.IElectric;
import sunsetsatellite.catalyst.energy.electric.base.TileEntityElectricBase;
import sunsetsatellite.catalyst.energy.electric.base.TileEntityElectricConductor;
import teamport.industry.core.block.logic.base.BlockLogicElectric;
import teamport.industry.core.interfaces.IVoltageHealth;

import java.util.Random;

/**
 * Logic for the batbox
 * @author Cookie
 * @date 2025-02-28
 */
public class TileEntityMachine extends TileEntityElectricBase implements IVoltageHealth {
    private int health = 100;
    private final Random rand = new Random();
    public boolean active = false;

    @Override
    public void init(Block block) {
        super.init(block);

        maxAmpsOut = 1;
        maxAmpsIn = 1;
        maxVoltageIn = getTier((BlockLogicElectric) block).maxVoltage;
    }

    @Override
    public void tick() {
        super.tick();

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

        ampsUsing = 0;
        for (Direction dir : Direction.values()) {
            receiveEnergy(dir, getMaxInputAmperage());
        }
    }

    @Override
    public boolean canReceive(@NotNull Direction dir) {
        return true;
    }

    // Code copied from TileEntityEnergyClass
    @Override
    public long receiveEnergy(@NotNull Direction dir, long amperage) {
        long remainingCapacity = getCapacityRemaining();
        long willUseAmps = 0;
        TileEntity tile = dir.getTileEntity(worldObj,this);
        if (tile instanceof TileEntityElectricConductor) {
            TileEntityElectricConductor wire = (TileEntityElectricConductor) tile;

            //for every known path
            for (NetworkPath path : energyNet.getPathData(wire.getPosition())) {
                long pathLoss = 0;
                //ignore itself or non-electric components in the path
                if (path.target == this || !(path.target instanceof IElectric)) {
                    continue;
                }
                IElectric dest = (IElectric) path.target;

                //receive/provide check
                if (dest.canProvide(path.targetDirection.getOpposite())) {
                    if (canReceive(dir)) {
                        //get max voltage from destination
                        //limit amps to maximum available from dest
                        long voltage = dest.getMaxOutputVoltage();
                        amperage = Math.min(amperage, (dest.getMaxOutputAmperage() - dest.getAmpsCurrentlyUsed()));
                        //calculate path loss
                        //voltage drop
                        long pathVoltage = voltage - pathLoss;
                        boolean pathBroken = false;
                        //handle wires with insufficient voltage rating
                        for (NetworkComponentTile pathTile : path.path) {
                            if (pathTile instanceof TileEntityElectricConductor) {
                                TileEntityElectricConductor pathWire = (TileEntityElectricConductor) pathTile;
                                if (pathWire.getVoltageRating() < voltage) {
                                    pathWire.onOvervoltage(voltage);
                                    pathBroken = true;
                                    pathVoltage = Math.min(pathWire.getVoltageRating(), pathVoltage);
                                    break;
                                }
                            }
                        }
                        if(pathBroken) continue;

                        if(pathVoltage > 0) {
                            //handle device over-voltage
                            if(pathVoltage > getMaxInputVoltage()) {
                                onOvervoltage(pathVoltage);
                                return Math.max(amperage, getMaxInputAmperage() - ampsUsing); //short circuit amperage
                            }

                            if(remainingCapacity >= pathVoltage) {
                                //calculate real current draw
                                willUseAmps = Math.min(remainingCapacity / pathVoltage, Math.min(amperage, getMaxInputAmperage() - ampsUsing));
                                if(willUseAmps > 0) {
                                    long willUseEnergy = pathVoltage * willUseAmps;
                                    if(dest.getEnergy() >= willUseEnergy){

                                        //set current in wires
                                        for (NetworkComponentTile pathTile : path.path) {
                                            if (pathTile instanceof TileEntityElectricConductor) {
                                                TileEntityElectricConductor pathWire = (TileEntityElectricConductor) pathTile;
                                                long voltageTraveled = voltage;
                                                voltageTraveled -= pathWire.getProperties().getMaterial().getLossPerBlock();
                                                if (voltageTraveled <= 0) break;
                                                pathWire.incrementAmperage(willUseAmps);
                                            }
                                        }

                                        //finish energy transfer
                                        addAmpsToUse(willUseAmps);
                                        internalAddEnergy(willUseEnergy);
                                        dest.internalRemoveEnergy(willUseEnergy);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return willUseAmps; //return amps used
    }

    @Override
    public void onOvervoltage(long voltage) {
        industry$modifyHealth(-2);
    }

    public void setEnergy(int amount) {
        if (amount <= getCapacity()) {
            energy = amount;
        }
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
        if (health + amount <= 100) {
            health += amount;
        }
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        super.writeToNBT(tag);
        tag.putInt("Health", industry$getHealth());
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        super.readFromNBT(tag);
        industry$setHealth(tag.getInteger("Health"));
    }
}
