package teamport.industry.core.block.entity;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.sound.SoundCategory;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.IFluidIO;
import sunsetsatellite.catalyst.energy.electric.api.IElectricItem;
import sunsetsatellite.catalyst.energy.electric.base.TileEntityElectricGenerator;
import sunsetsatellite.catalyst.fluids.api.IFluidInventory;
import sunsetsatellite.catalyst.fluids.api.IFluidTransfer;
import sunsetsatellite.catalyst.fluids.api.IMassFluidInventory;
import sunsetsatellite.catalyst.fluids.impl.tiles.TileEntityFluidPipe;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import teamport.industry.core.block.logic.base.BlockLogicElectric;
import teamport.industry.core.item.IndItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Logic for the geothermal generator.
 * @author cookie
 * @date 2025-01-21
 */
public class TileEntityGeothermalGenerator extends TileEntityElectricGenerator
        implements IInventory, IFluidInventory, IFluidTransfer, IFluidIO {
    public ItemStack[] invSlots = new ItemStack[2];
    public FluidStack[] fluidSlots = new FluidStack[1];
    private final int transferSpeed = 20;
    private int soundLoop = 0;

    public int[] fluidCapacity = new int[1];
    public ArrayList<ArrayList<BlockFluid>> acceptedFluids = new ArrayList<>(fluidSlots.length);
    public HashMap<Direction, Connection> fluidConnections = new HashMap<>();
    public HashMap<Direction, Integer> activeFluidSlots = new HashMap<>();
    public boolean active;

    @Override
    public void init(Block block) {
        super.init(block);

        fluidCapacity[0] = 24000;
        maxAmpsOut = 1;
        maxAmpsIn = 0;
        maxVoltageIn = 0;
        maxVoltageOut = getTier((BlockLogicElectric) block).maxVoltage;
        capacity = 20;

        for (Direction dir : Direction.values()) {
            fluidConnections.put(dir, Connection.NONE);
            activeFluidSlots.put(dir, 0);
        }

        for (FluidStack ignored : fluidSlots) {
            acceptedFluids.add(new ArrayList<>(1));
        }

        acceptedFluids.get(0).add((BlockFluid) Block.fluidLavaFlowing);
        acceptedFluids.get(0).add((BlockFluid) Block.fluidLavaStill);
    }

    public void moveFluids(Direction dir, TileEntityFluidPipe tile) {
        int activeSlot = activeFluidSlots.get(dir);
        if (activeSlot == -1) {
            return;
        }
        if (fluidConnections.get(dir) == Connection.BOTH || fluidConnections.get(dir) == Connection.OUTPUT) {
            if(getFluidInSlot(activeSlot) != null) {
                give(dir);
            }
        } else if(fluidConnections.get(dir) == Connection.BOTH || fluidConnections.get(dir) == Connection.INPUT) {
            if (tile.getFluidInSlot(0) != null) {
                take(tile.getFluidInSlot(0),dir);
            }
        }
    }

    public void extractFluids(){
        for (Map.Entry<Direction, Connection> e : fluidConnections.entrySet()) {
            Direction dir = e.getKey();
            TileEntity tile = dir.getTileEntity(worldObj,this);
            if (tile instanceof TileEntityFluidPipe) {
                moveFluids(dir, (TileEntityFluidPipe) tile);
                ((TileEntityFluidPipe) tile).rememberTicks = 100;
            }
        }
    }

    @Override
    public void tick() {
        extractFluids();
        super.tick();

        boolean hasFluid = getFluidInSlot(0) != null;

        if (getEnergy() - 20 >= 0) {
            ItemStack stack = invSlots[0];
            if (stack != null && stack.getItem() instanceof IElectricItem) {
                IElectricItem batt = (IElectricItem) stack.getItem();

                if (batt.getEnergy(stack) + 20 <= batt.getCapacity(stack)) {
                    internalRemoveEnergy(20);
                    batt.charge(stack, 20);
                }
            }
        }

        if (soundLoop >= 60 || (energy >= capacity && soundLoop > 0)) {
            soundLoop = 0;
            worldObj.markBlockNeedsUpdate(x, y, z);
        }

        if (invSlots[1] != null) {
            if (getFluidInSlot(0) == null) {
                if (invSlots[1].getItem() == IndItems.LAVA_CELL) {
                    insertFluid(0, new FluidStack((BlockFluid) Block.fluidLavaStill, 1000));
                    invSlots[1].stackSize -= 1;
                    onInventoryChanged();
                } else if (invSlots[1].getItem() == Item.bucketLava) {
                    insertFluid(0, new FluidStack((BlockFluid) Block.fluidLavaStill, 1000));
                    invSlots[1] = new ItemStack(Item.bucket);
                    onInventoryChanged();
                }
            } else {
                if (getFluidInSlot(0).amount + 1000 <= fluidCapacity[0]) {
                    if (invSlots[1].getItem() == IndItems.LAVA_CELL) {
                        insertFluid(0, new FluidStack((BlockFluid) Block.fluidLavaStill, 1000));
                        invSlots[1].stackSize -= 1;
                        onInventoryChanged();
                    } else if (invSlots[1].getItem() == Item.bucketLava) {
                        insertFluid(0, new FluidStack((BlockFluid) Block.fluidLavaStill, 1000));
                        invSlots[1] = new ItemStack(Item.bucket);
                        onInventoryChanged();
                    }
                }
            }

            if (invSlots[1].stackSize <= 0) {
                invSlots[1] = null;
            }
        }

        if (hasFluid && energy + 20 <= capacity) {
            internalAddEnergy(20);
            fluidSlots[0].amount -= 1;
            active = true;

            if (soundLoop++ <= 0) {
                worldObj.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "industry.GeothermalLoop", 0.3f, 1);
                worldObj.markBlockNeedsUpdate(x, y, z);
            }

            if (fluidSlots[0].amount <= 0) {
                fluidSlots[0] = null;
            }
        } else {
            active = false;
        }
    }

    public int getRemainingFluidTime(int i) {
        return fluidCapacity[0] == 0 ? 0 : fluidSlots[0].amount * i / fluidCapacity[0];
    }

    public int getEnergyScaled(int i) {
        return getCapacity() == 0 ? 0 : (int) (getEnergy() * i / getCapacity());
    }

    public int getCurrentFluidAmount() {
        return getFluidInSlot(0) == null ? 0 : getFluidInSlot(0).amount;
    }

    public void setEnergy(int amount) {
        energy = amount;
    }

    @Override
    public int getSizeInventory() {
        return invSlots.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return invSlots[slot];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (invSlots[i] != null) {
            if (invSlots[i].stackSize <= j) {
                ItemStack contents = invSlots[i];

                invSlots[i] = null;
                return contents;
            } else {
                ItemStack splitStack = invSlots[i].splitStack(j);
                if (invSlots[i].stackSize <= 0) invSlots[i] = null;

                return splitStack;
            }
        }

        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        invSlots[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
            itemStack.stackSize = getInventoryStackLimit();

        onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "Industry_GeothermalGenerator";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return worldObj.getBlockTileEntity(x, y, z) == this && player.distanceToSqr(x + 0.5, y + 0.5, z + 0.5) <= 64;
    }

    @Override
    public void sortInventory() {

    }

    @Override
    public int getActiveFluidSlotForSide(Direction dir) {
        return 0;
    }

    @Override
    public Connection getFluidIOForSide(Direction dir) {
        return fluidConnections.get(dir);
    }

    @Override
    public void setFluidIOForSide(Direction dir, Connection con) {
        fluidConnections.put(dir, con);
    }

    @Override
    public void onOvervoltage(long voltage) {

    }

    @Override
    public boolean canInsertFluid(int slot, FluidStack fluidStack) {
        if (getFluidInSlot(slot) != null) {
            if (!getFluidInSlot(slot).isFluidEqual(fluidStack)) {
                return false;
            }
        }

        return Math.min(fluidStack.amount, getRemainingCapacity(slot)) > 0;
    }

    @Override
    public FluidStack getFluidInSlot(int slot) {
        return fluidSlots[slot];
    }

    @Override
    public int getFluidCapacityForSlot(int slot) {
        return fluidCapacity[slot];
    }

    @Override
    public ArrayList<BlockFluid> getAllowedFluidsForSlot(int slot) {
        return acceptedFluids.get(slot);
    }

    @Override
    public void setFluidInSlot(int slot, FluidStack fluid) {
        if (fluid == null || fluid.amount == 0 || fluid.liquid == null) {
            fluidSlots[slot] = null;
            onFluidInventoryChanged();
            return;
        }

        if (acceptedFluids.get(slot).contains(fluid.liquid) || acceptedFluids.get(slot).isEmpty()) {
            fluidSlots[slot] = fluid;
            onFluidInventoryChanged();
        }
    }

    @Override
    public FluidStack insertFluid(int slot, FluidStack fluidStack) {
        FluidStack stack = fluidSlots[slot];
        FluidStack split = fluidStack.splitStack(Math.min(fluidStack.amount, getRemainingCapacity(slot)));

        if (stack != null && split.amount > 0) {
            fluidSlots[slot].amount += split.amount;
        } else {
            fluidSlots[slot] = split;
        }

        return fluidStack;
    }

    @Override
    public int getRemainingCapacity(int slot) {
        if (fluidSlots[slot] == null) {
            return fluidCapacity[slot];
        }
        return fluidCapacity[slot] - fluidSlots[slot].amount;
    }

    @Override
    public int getFluidInventorySize() {
        return fluidCapacity.length;
    }

    @Override
    public void onFluidInventoryChanged() {
        if (this.worldObj != null) {
            this.worldObj.updateTileEntityChunkAndSendToPlayer(this.x, this.y, this.z, this);
        }
    }


    @Override
    public int getTransferSpeed() {
        return transferSpeed;
    }

    @Override
    public void take(FluidStack fluidStack, Direction dir) {
        if (fluidConnections.get(dir) == Connection.INPUT || fluidConnections.get(dir) == Connection.BOTH) {
            TileEntity tile = dir.getTileEntity(worldObj, this);

            if (tile instanceof IFluidInventory && tile instanceof IFluidIO) {
                IFluidInventory fluidInv = ((IFluidInventory) tile);
                IFluidIO fluidIO = (IFluidIO) tile;

                if (fluidIO.getFluidIOForSide(dir.getOpposite()) == Connection.OUTPUT || fluidIO.getFluidIOForSide(dir.getOpposite()) == Connection.BOTH) {
                    int maxFlow = Math.min(transferSpeed, fluidInv.getTransferSpeed());
                    if (activeFluidSlots.get(dir) == -1) {
                        return;
                    }

                    if (acceptedFluids.get(activeFluidSlots.get(dir)).contains(fluidStack.getLiquid())) {
                        int maxAmount = Math.min(fluidStack.amount, maxFlow);
                        if (canInsertFluid(activeFluidSlots.get(dir), new FluidStack(fluidStack.liquid, maxAmount))) {
                            FluidStack transferablePortion = fluidStack.splitStack(maxAmount);
                            if (fluidSlots[activeFluidSlots.get(dir)] == null) {
                                fluidSlots[activeFluidSlots.get(dir)] = transferablePortion;
                            } else {
                                fluidSlots[activeFluidSlots.get(dir)].amount += transferablePortion.amount;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void take(@NotNull FluidStack fluidStack, Direction dir, int slot) {
        if (fluidConnections.get(dir) == Connection.INPUT || fluidConnections.get(dir) == Connection.BOTH) {
            TileEntity tile = dir.getTileEntity(worldObj, this);

            if (tile instanceof IFluidInventory) {
                IFluidInventory fluidInv = ((IFluidInventory) tile);
                IFluidIO fluidIO = (IFluidIO) tile;

                if (fluidIO.getFluidIOForSide(dir.getOpposite()) == Connection.OUTPUT || fluidIO.getFluidIOForSide(dir.getOpposite()) == Connection.BOTH) {
                    int maxFlow = Math.min(transferSpeed, fluidInv.getTransferSpeed());
                    if (slot == -1) return;

                    if (acceptedFluids.get(slot).contains(fluidStack.getLiquid())) {
                        int maxAmount = Math.min(fluidStack.amount, maxFlow);
                        if (canInsertFluid(slot, new FluidStack(fluidStack.liquid, maxAmount))) {
                            FluidStack transferablePortion = fluidStack.splitStack(maxAmount);
                            if (fluidSlots[slot] == null) {
                                fluidSlots[slot] = transferablePortion;
                            } else {
                                fluidSlots[slot].amount += transferablePortion.amount;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void give(Direction dir) {
        int slot = activeFluidSlots.get(dir);
        if (slot == -1) {
            return;
        }

        FluidStack fluidStack = fluidSlots[slot];
        if (fluidConnections.get(dir) == Connection.OUTPUT || fluidConnections.get(dir) == Connection.BOTH) {
            TileEntity tile = dir.getTileEntity(worldObj, this);

            if (tile instanceof IFluidInventory && tile instanceof IFluidIO) {
                IFluidInventory fluidInv = ((IFluidInventory) tile);
                IFluidIO fluidIO = (IFluidIO) tile;

                if (fluidIO.getFluidIOForSide(dir.getOpposite()) == Connection.INPUT || fluidIO.getFluidIOForSide(dir.getOpposite()) == Connection.BOTH) {
                    int maxFlow = Math.min(transferSpeed, fluidInv.getTransferSpeed());

                    if (tile instanceof IMassFluidInventory) {
                        IMassFluidInventory massFluidInv = (IMassFluidInventory) tile;
                        if (fluidStack.isFluidEqual(massFluidInv.getFilter(dir.getOpposite())) || massFluidInv.getFilter(dir.getOpposite()) == null) {
                            int maxAmount = Math.min(fluidStack.amount, maxFlow);
                            if (massFluidInv.canInsertFluid(new FluidStack(fluidStack.liquid, maxAmount))) {
                                FluidStack transferablePortion = fluidStack.splitStack(maxAmount);
                                massFluidInv.insertFluid(transferablePortion);
                            }
                        }
                    } else {
                        int otherSlot = fluidIO.getActiveFluidSlotForSide(dir.getOpposite());
                        if (otherSlot == -1) {
                            return;
                        }

                        if (fluidInv.getAllowedFluidsForSlot(otherSlot).contains(fluidStack.getLiquid())) {
                            int maxAmount = Math.min(fluidStack.amount, maxFlow);
                            if (fluidInv.canInsertFluid(otherSlot, new FluidStack(fluidStack.liquid, maxAmount))) {
                                FluidStack transferablePortion = fluidStack.splitStack(maxAmount);
                                fluidInv.insertFluid(otherSlot, transferablePortion);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void give(Direction dir, int slot, int otherSlot){
        if(slot == -1) {
            return;
        }

        FluidStack fluidStack = fluidSlots[slot];
        if (fluidConnections.get(dir) == Connection.OUTPUT || fluidConnections.get(dir) == Connection.BOTH) {
            TileEntity tile = dir.getTileEntity(worldObj,this);

            if (tile instanceof IFluidInventory && tile instanceof IFluidIO) {
                IFluidInventory fluidInv = ((IFluidInventory) tile);
                IFluidIO fluidIO = (IFluidIO) tile;

                if (fluidIO.getFluidIOForSide(dir.getOpposite()) == Connection.INPUT || fluidIO.getFluidIOForSide(dir.getOpposite()) == Connection.BOTH) {
                    int maxFlow = Math.min(transferSpeed, fluidInv.getTransferSpeed());
                    if (tile instanceof IMassFluidInventory) {
                        IMassFluidInventory massFluidInv = (IMassFluidInventory) tile;
                        if (fluidStack.isFluidEqual(massFluidInv.getFilter(dir.getOpposite())) || massFluidInv.getFilter(dir.getOpposite()) == null) {
                            int maxAmount = Math.min(fluidStack.amount, maxFlow);
                            if (massFluidInv.canInsertFluid(new FluidStack(fluidStack.liquid,maxAmount))) {
                                FluidStack transferablePortion = fluidStack.splitStack(maxAmount);
                                massFluidInv.insertFluid(transferablePortion);
                            }
                        }
                    } else {
                        if(otherSlot == -1) {
                            return;
                        }
                        if(fluidInv.getAllowedFluidsForSlot(otherSlot).contains(fluidStack.getLiquid())){
                            int maxAmount = Math.min(fluidStack.amount, maxFlow);
                            if (fluidInv.canInsertFluid(otherSlot,new FluidStack(fluidStack.liquid,maxAmount))) {
                                FluidStack transferablePortion = fluidStack.splitStack(maxAmount);
                                fluidInv.insertFluid(otherSlot,transferablePortion);
                            }
                        }
                    }
                }
            }
        }
    }
}
