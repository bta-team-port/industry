package teamport.industry.core.block.entity;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.core.util.TickTimer;

/**
 * Base Tile Entity for the pipes (CURRENTLY UNUSED)
 * @author Cookie
 * @date 2025-01-14
 */
public class TileEntityPipe extends TileEntity {
    private ItemStack contents = null;
    public int EXTRACT_TICKS = 20;
    private final TickTimer extractTimer = new TickTimer(this, this::tickItemMovement, EXTRACT_TICKS, true);
    public int ticksRan = 0;

    public TileEntityPipe() {

    }

    public ItemStack getContents() {
        return contents;
    }

/*
    private void tickExtract() {
        for (Direction dir : Direction.values()) {
            TileEntity facingTile = dir.getTileEntity(worldObj, this);

            if (facingTile instanceof TileEntityPipe && connections.get(dir) == Connection.INPUT) {
                connections.put(dir, Connection.BOTH);
                connections.put(dir.getOpposite(), Connection.INPUT);
            }

            if (facingTile instanceof TileEntityPipe && connections.get(dir) == Connection.OUTPUT) {
                connections.put(dir, Connection.BOTH);
                connections.put(dir.getOpposite(), Connection.OUTPUT);
            }

            if (facingTile instanceof TileEntityPipe) {
                if (connections.get(dir) == Connection.BOTH) {
                    ItemStack provided = provide(dir, contents);
                    if (provided != null) {
                        ItemStack received = ((TileEntityPipe) facingTile).receive(dir.getOpposite(), provided);

                        if (received != null) {
                            ((TileEntityPipe) facingTile).receive(dir.getOpposite(), provided);
                            provide(dir, received);
                        }
                    }
                }
            } else if (facingTile instanceof IInventory) {
                if (connections.get(dir) == Connection.INPUT) {
                    for (int i = 0; i < ((IInventory) facingTile).getSizeInventory(); i++) {
                        ItemStack received = ((IInventory) facingTile).getStackInSlot(i);

                        if (received != null && contents == null) {
                            receive(dir, received);
                            ((IInventory) facingTile).setInventorySlotContents(i, null);
                        }
                    }
                } else if (connections.get(dir) == Connection.OUTPUT) {
                    int slot = 0;
                    ItemStack stackInSlot = null;
                    ItemStack provided = provide(dir, contents);

                    if (provided != null && provided.stackSize > 0) {
                        for (int i = 0; i < ((IInventory) facingTile).getSizeInventory(); i++) {
                            slot = i;
                            stackInSlot = ((IInventory) facingTile).getStackInSlot(slot);

                            if (stackInSlot == null) {
                                break;
                            } else {
                                if (provided != null && provided.itemID == stackInSlot.itemID &&
                                        provided.getMetadata() == stackInSlot.getMetadata()) {
                                    if (stackInSlot.stackSize + provided.stackSize <= stackInSlot.getMaxStackSize()) {
                                        stackInSlot.stackSize += provided.stackSize;

                                        provided = null;
                                    } else {
                                        int newStackSize = provided.stackSize + stackInSlot.stackSize;
                                        provided.stackSize = newStackSize - stackInSlot.getMaxStackSize();
                                        stackInSlot.stackSize = stackInSlot.getMaxStackSize();
                                    }
                                }

                                if (slot == ((IInventory) facingTile).getSizeInventory()) {
                                    EntityItem entityItem = new EntityItem(worldObj, x, y + 0.5, z, provided);
                                    worldObj.entityJoinedWorld(entityItem);
                                    break;
                                }
                            }
                        }

                        if (stackInSlot == null) {
                            ((IInventory) facingTile).setInventorySlotContents(slot, provided);
                        } else {
                            if (provided != null && provided.itemID == stackInSlot.itemID &&
                                    provided.getMetadata() == stackInSlot.getMetadata()) {
                                if (stackInSlot.stackSize + provided.stackSize <= stackInSlot.getMaxStackSize()) {
                                    stackInSlot.stackSize += provided.stackSize;
                                    ((IInventory) facingTile).setInventorySlotContents(slot, stackInSlot);
                                } else {
                                    ((IInventory) facingTile).setInventorySlotContents(slot + 1, provided);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
*/

    private void tickItemMovement() {

    }

    @Override
    public void tick() {
        extractTimer.tick();

        if (ticksRan == 0) {
            ticksRan = worldObj.rand.nextInt(360);
        }

        ++ticksRan;
    }
}
