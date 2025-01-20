package teamport.industry.core.block.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.TickTimer;
import sunsetsatellite.catalyst.energy.api.IEnergyItem;
import sunsetsatellite.catalyst.energy.impl.TileEntityEnergyConductor;

/*
 * ===========================================================================
 * File: TileEntityEnergyConductorDamageable.java
 * Brief: Tile Entity for damageable energy conductors
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class TileEntityEnergyConductorDamageable extends TileEntityEnergyConductor {
    private int maxMachineHealth;
    private int maxCharge;
    public int health;
    public int healthTick;
    public boolean lastDamage = false;
    public TickTimer lastDamageMemory = new TickTimer(this, this::clearLastDamage, 20, true);

    //      UNITS       //
    //  0004 - MIVLT    //
    //  0032 - LOVLT    //
    //  0128 - MEVLT    //
    //  0512 - HIVLT    //
    //  2048 - EXVLT    //

    public TileEntityEnergyConductorDamageable() {
        super();
        setMaxMachineHealth(20);
        setMaxCharge(0);
        setMaxReceive(2048);

        health = getMaxMachineHealth();
        healthTick = 4;
    }

    public void clearLastDamage() {
        lastDamage = false;
    }

    public int getMaxMachineHealth() {
        return maxMachineHealth;
    }

    public void setMaxMachineHealth(int maxMachineHealth) {
        this.maxMachineHealth = maxMachineHealth;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void setMaxCharge(int maxCharge) {
        this.maxCharge = maxCharge;
    }

    public void modifyHealth(int amount) {
        if (health + amount > getMaxMachineHealth()) {
            health = getMaxMachineHealth();
        } else if (health - amount <= 0) {
            health = 0;
        } else {
            health += amount;
        }
    }

    @Override
    public int receive(Direction dir, int amount, boolean test) {
        if (amount > getMaxCharge()) {
            if (--healthTick <= 0) {
                healthTick = 4;
                lastDamage = true;

                modifyHealth(-2);
            }
        }

        return super.receive(dir, amount, test);
    }

    public int receive(ItemStack stack, int amount, boolean test) {
        if (stack.getItem() instanceof IEnergyItem) {
            int received = Math.min(capacity - energy, Math.min(maxReceive, amount));
            int provided = ((IEnergyItem)stack.getItem()).provide(stack, amount, true);
            int actual = Math.min(provided, received);

            if (!test) {
                if (actual > maxCharge) {
                    actual = maxCharge;
                }

                modifyEnergy(actual);
                ((IEnergyItem)stack.getItem()).provide(stack, actual, false);
            }

            return actual;
        }
        return 0;
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        super.writeToNBT(tag);

        tag.putInt("Health", health);
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        super.readFromNBT(tag);

        health = tag.getInteger("Health");
    }

    @Override
    public void tick() {
        super.tick();
        if (energy > 0) {
            System.out.println("NRG");
        }
        lastDamageMemory.tick();

        if (health < getMaxMachineHealth()) {
            double randX = x + worldObj.rand.nextDouble();
            double randY = y + worldObj.rand.nextDouble();
            double randZ = z + worldObj.rand.nextDouble();

            worldObj.spawnParticle("smoke", randX, randY + 0.22, randZ, 0.0, 0.05, 0.0, 0);
            worldObj.spawnParticle("flame", randX, randY + 0.22, randZ, 0.0, 0.05, 0.0, 0);
            
            if (worldObj.rand.nextInt(40) == 0) {
                worldObj.playSoundEffect(null, 
                        SoundCategory.WORLD_SOUNDS,
                        (float)x + 0.5F,
                        (float)y + 0.5F,
                        (float)z + 0.5F, 
                        "fire.fire", 
                        1.0F + worldObj.rand.nextFloat(),
                        worldObj.rand.nextFloat() * 0.7F + 0.3F);
            }
        }

        if (health <= 0) {
            worldObj.setBlockWithNotify(x, y, z, 0);
            worldObj.newExplosion(null, x, y, z, 1, false, true);

            if (worldObj.rand.nextInt(2) == 0) {
                worldObj.setBlockWithNotify(x, y, z, Block.fire.id);
            }
        }

        if (!lastDamage) {
            modifyHealth(2);
        }
    }
}
