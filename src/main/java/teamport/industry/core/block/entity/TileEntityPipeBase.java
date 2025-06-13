package teamport.industry.core.block.entity;

import com.mojang.nbt.tags.ByteTag;
import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.Tag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketTileEntityData;
import net.minecraft.core.player.inventory.container.Container;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.TickTimer;
import sunsetsatellite.catalyst.core.util.io.IItemIO;
import sunsetsatellite.catalyst.core.util.vector.Vec3f;
import teamport.industry.core.block.logic.pipe.BlockLogicPipeBase;
import teamport.industry.extra.interfaces.IBasket;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class TileEntityPipeBase extends TileEntity {
    private final List<PipeItem> contents = new ArrayList<>();
    private final Random random = new Random();
    public Map<Direction, Boolean> noConnectDirections = new HashMap<>();
    public Map<Direction, Boolean> restrictDirections = new HashMap<>();
    public static int EXTRACT_TICKS = 20*3;
    public int extractAmount;
    public int transferSpeed;
    private final TickTimer extractTimer = new TickTimer(this, this::extractItem, EXTRACT_TICKS, true);

    /**
     * Class initializer
     * @param extractAmount The number of items extracted
     * @param transferSpeed How fast the items are transferred
     */
    public TileEntityPipeBase(int extractAmount, int transferSpeed) {
        this.extractAmount = extractAmount;
        this.transferSpeed = transferSpeed;
        initializeDirections();
    }

    /**
     * Class initializer -
     * Extract amount defaulted to 0 -
     * Transfer Speed defaulted to 1
     */
    public TileEntityPipeBase() {
        extractAmount = 0;
        transferSpeed = 1;
        initializeDirections();
    }

    /**
     * This method is used by the initializers to
     * put default directions in.
     */
    private void initializeDirections() {
        for (Direction dir : Direction.values()) {
            noConnectDirections.put(dir, false);
            restrictDirections.put(dir, false);
        }
    }

    /**
     * This method checks if a given material is a stone type.
     * @param material The given material of the pipe
     * @return Returns true if the material IS NOT a stone type.
     */
    private boolean matIsNotStoneType(Material material) {
        return material != Material.stone &&
                material != Material.basalt &&
                material != Material.limestone &&
                material != Material.granite &&
                material != Material.netherrack &&
                material != Material.permafrost;
    }

    /**
     * This method handles getting the surrounding blocks, based on
     * the Direction and the TileEntity.
     * @return Returns the surrounding blocks (Map)
     */
    public Map<Direction, TileEntity> getSurroundings() {
        // We start with a world check, so IntelliJ shuts up.
        if (worldObj == null) return null;

        // Now make a 'surroundings' hashmap and get every direction.
        // Then we check for tiles in each direction and continue the check
        // if any are null.
        Map<Direction, TileEntity> surroundings = new HashMap<>();
        for(Direction direction : Direction.values()) {
            TileEntity tile = direction.getTileEntity(worldObj, this);
            if (tile == null) continue;

            // Now we check if the tile is a basket or container, and
            // if so, we add it to the 'surroundings' map.
            // Otherwise, if it's a pipe class, we check if it's in 'NoDirections' (aka the dir blocklist)
            if (tile instanceof Container || tile instanceof IBasket) {
                if (!noConnectDirections.get(direction)) {
                    surroundings.put(direction, tile);
                }
            } else if (tile instanceof TileEntityPipeBase) {
                if (noConnectDirections.get(direction)) {
                    continue;
                }

                // Now if we make it this far, we make a BlockLogic var from the block pos.
                // We make a material var as well. Then we check the material for if it's a stone-type.
                // If the pipe IS NOT a stone type, we add it to the 'surroundings' map and return.
                BlockLogicPipeBase blockLogicPipeBase = worldObj.getBlockLogic(x, y, z, BlockLogicPipeBase.class);
                if (blockLogicPipeBase == null) continue;

                Material material = blockLogicPipeBase.getMaterial();
                if (matIsNotStoneType(material)) {
                    surroundings.put(direction, tile);
                    continue;
                }

                // However, if the pipe IS a stone-type, we check the surrounding
                // pipes for stone-type as well. Only non-stone can connect to stone!
                Block<?> block = worldObj.getBlock(tile.x, tile.y, tile.z);
                if (block != null) {
                    if (block.getLogic() instanceof BlockLogicPipeBase) {
                        BlockLogicPipeBase otherPipe = (BlockLogicPipeBase) block.getLogic();
                        if (matIsNotStoneType(otherPipe.getMaterial()) || material == otherPipe.getMaterial()) {
                            surroundings.put(direction, tile);
                        }
                    }
                }
            }
        }

        return surroundings;
    }

    /**
     * This method will drop items if the pipe contains
     * an item, and the next pipe doesn't exist.
     * @param item PipeItem stack
     * @param iter An iterator for PipeItem stacks
     */
    public void dropItem(PipeItem item, Iterator<PipeItem> iter) {
        // Two 'if' checks - one if the pipeItem exists,
        // and another to see if the world exists. Mainly to shut-up IntelliJ.
        if (!contents.contains(item)) return;
        if (worldObj == null) return;

        // Now we get the exit vector, and divide it by two.
        // Then we get an offset by adding 0.5.
        Vec3f dirVec = item.exit.getVecF().divide(2);
        Vec3f offset = new Vec3f(x,y,z).add(dirVec).add(0.5);

        // Now, we create a new item entity from the pipe Item, and set its
        // position to the offset, as well as adding a bit of momentum.
        // Then we spawn it into the world itself.
        EntityItem entityitem = new EntityItem(worldObj, offset.x, offset.y, offset.z, item.stack);
        entityitem.setPos(offset.x + dirVec.x * 2, offset.y + dirVec.y * 2, offset.z + dirVec.z * 2);
        entityitem.xd += 0.0025 + dirVec.x / 4;
        entityitem.yd += 0.0025 + dirVec.y / 4;
        entityitem.zd += 0.0025 + dirVec.z / 4;
        worldObj.entityJoinedWorld(entityitem);

        // Now if the stack iterator is NOT null, we set it to null.
        // Otherwise, we just remove it from the contents.
        if (iter != null) {
            iter.remove();
        } else {
            contents.remove(item);
        }
    }

    /**
     * This method handles extracting items from containers, baskets,
     * and pipes.
     */
    public void extractItem() {
        // First we get the surrounding blocks and filter out pipes.
        // We also return if the entryList is empty.
        Map<Direction, TileEntity> surroundings = getSurroundings();
        if (surroundings == null) return;

        List<Map.Entry<Direction, TileEntity>> entryList = surroundings
                .entrySet()
                .stream()
                .filter((E) -> !(E.getValue() instanceof TileEntityPipeBase))
                .collect(Collectors.toList());

        if (entryList.isEmpty()) return;

        // Now we check the entry direction randomly.
        // Again, if it's null, we return.
        Direction entry = entryList.get(random.nextInt(entryList.size())).getKey();
        if (entry == null) return;

        // Now check if the entry tile is a container, and get its inventory.
        // If the inventory is null, return. Otherwise, we split the stack by
        // the extract amount.
        TileEntity entryTile = surroundings.get(entry);
        if (entryTile instanceof Container) {
            Container inv = ((Container) entryTile);
            ItemStack stack = null;
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack invItem = inv.getItem(i);
                if (invItem == null) continue;

                stack = invItem.splitStack(extractAmount);

                if (invItem.stackSize >= extractAmount) {
                    invItem.splitStack(extractAmount);

                    if (invItem.stackSize <= 0) inv.setItem(i, null);
                } else inv.setItem(i, null);

                break;
            }

            if (stack == null) return;

            // Now we filter out the exit direction, and make sure the key is
            // NOT the entry. If it's also empty, we return.
            List<Map.Entry<Direction, TileEntity>> exitList = surroundings
                    .entrySet()
                    .stream()
                    .filter((E) -> E.getKey() != entry)
                    .collect(Collectors.toList());

            if (exitList.isEmpty()) return;

            // Then we pick a random exit direction, and make sure it exists.
            // If it ALL passes, we set the pipe's item to the extracted item.
            Direction exit;
            exit = pickRandomExitDirection(exitList, stack);
            if (exit == null) return;

            TileEntity exitTile = surroundings.get(exit);
            if (!(exitTile instanceof IItemIO) && !(exitTile instanceof Container) && !(exitTile instanceof TileEntityPipeBase))
                return;

            PipeItem pipeItem = new PipeItem(stack, entry, exit);
            contents.add(pipeItem);

            // Now we check if the entry tile is a basket instead.
            // This required a mixin to work, and we haven't added insertion. (yet!)
            // The method is the same as above, extract the first item.
        } else if (entryTile instanceof IBasket) {
            IBasket inv = ((IBasket) entryTile);
            ItemStack stack = null;
            for (int i = 0; i < inv.industry$getContents().size(); i++) {
                if (inv.industry$getFirstItemAsStack() != null) {
                    stack = inv.industry$getFirstItemAsStack().copy().splitStack(extractAmount);
                    inv.industry$removeContentsFromFirstSlot(1);
                    break;
                }
            }

            if (stack == null) return;

            // Now we filter out the entry direction from the exit list.
            // If it's empty, we return.
            List<Map.Entry<Direction, TileEntity>> exitList = surroundings
                    .entrySet()
                    .stream()
                    .filter((E) -> E.getKey() != entry)
                    .collect(Collectors.toList());

            if (exitList.isEmpty()) return;

            // Now select the exit direction randomly via another function.
            // If it's null - you guessed it - we return!
            // Then we check if the exit exists, and if not - return!
            Direction exit = pickRandomExitDirection(exitList, stack);
            if (exit == null) return;

            TileEntity exitTile = surroundings.get(exit);
            if (!(exitTile instanceof IItemIO) && !(exitTile instanceof Container) && !(exitTile instanceof TileEntityPipeBase))
                return;

            // Now we add the basket's item to the pipe.
            PipeItem pipeItem = new PipeItem(stack, entry, exit);
            contents.add(pipeItem);
        }
    }

    /**
     * This method will pick a random exit direction from a pipe.
     * @param exitList A list consisting of map, with key Direction and value TileEntity
     * @param stack ItemStack
     * @return Returns a direction that can be exited from
     */
    private Direction pickRandomExitDirection(List<Map.Entry<Direction, TileEntity>> exitList, ItemStack stack) {
        // First we make a new arrayList of blocked directions.
        List<Direction> blockedDirs = new ArrayList<>();

        // Now we move into a for loop of the exit list.
        // If the list DOES NOT have a value of IItemIO or IS NOT a container,
        // we continue the for loop.
        for (Map.Entry<Direction, TileEntity> exitEntry : exitList) {
            if (!(exitEntry.getValue() instanceof IItemIO) && !(exitEntry.getValue() instanceof Container)) continue;

            // Now if we find one, and IT IS an instance of IItemIO,
            // we make a var of the exit entry value and then make a switch check for
            // if the exit entry key set to 'Input' or 'Both'. If so, we return it.
            if (exitEntry.getValue() instanceof IItemIO) {
                IItemIO io = (IItemIO) exitEntry.getValue();
                switch (io.getItemIOForSide(exitEntry.getKey().getOpposite())) {
                    case INPUT:
                    case BOTH:
                        return exitEntry.getKey();
                }
            } else {
                // Now we check scan through the exit container's inventory.
                // Each 'if' check after that checks for not null, and then a value.
                // We check if the item is equal, and continue. Then we check for
                // if the stacksize is too large, either for the inv or the item itself.
                Container inv = (Container) exitEntry.getValue();
                for (int i = 0; i < inv.getContainerSize(); i++) {
                    ItemStack invStack = inv.getItem(i);
                    if (invStack != null && !invStack.isItemEqual(stack))
                        continue;

                    if (invStack != null && invStack.stackSize + stack.stackSize > inv.getMaxStackSize())
                        continue;

                    if (invStack != null && invStack.stackSize + stack.stackSize > invStack.getMaxStackSize(inv))
                        continue;

                    // If we pass all checks, return the exit entry key.
                    return exitEntry.getKey();
                }
            }

            // If we get to this point, add the exit entry key to the
            // blocked directions.
            blockedDirs.add(exitEntry.getKey());
        }

        // Now we check for each and every restricted direction entry.
        // If we have the value, and the blocked dirs DOES NOT contain
        // the key, we add the key.
        for (Map.Entry<Direction, Boolean> entry : restrictDirections.entrySet()) {
            Direction key = entry.getKey();
            Boolean value = entry.getValue();
            if (value && !blockedDirs.contains(key)) blockedDirs.add(key);
        }

        // Now we check through the "no connection directions" entries.
        // Again, if we have the value and blocked dirs DOES NOT, we add it.
        for (Map.Entry<Direction, Boolean> entry : noConnectDirections.entrySet()) {
            Direction key = entry.getKey();
            Boolean value = entry.getValue();
            if (value && !blockedDirs.contains(key)) blockedDirs.add(key);
        }

        // Now we filter the blocked directions, and check if it contains the
        // direction. If the exit list is empty, we return null.
        // If size is equal to one, we return the exit list key at pos one (0).
        // Otherwise, we return a random exit list key.
        exitList = exitList
                .stream()
                .filter((E)->!(blockedDirs.contains(E.getKey())))
                .collect(Collectors.toList());

        if (exitList.isEmpty()) return null;
        if (exitList.size() == 1) return exitList.get(0).getKey();
        return exitList.get(random.nextInt(exitList.size())).getKey();
    }

    @Override
    public Packet getDescriptionPacket() {
        return new PacketTileEntityData(this);
    }

    @Override
    public void tick() {
        super.tick();

        // Check if the world is null, to shut up IntelliJ.
        if (worldObj == null) return;

        // First we check for if the block has a direction or neighboring signal.
        // If either are true, and the extract amount is above zero,
        // we start an extract timer! If the value is below or equal to zero,
        // we run the super method 'setChanged'
        // TODO - Replace this with some form of mechanical power. (or normal power)
        boolean blockPowered = worldObj.hasDirectSignal(x, y, z) || worldObj.hasNeighborSignal(x, y, z);
        if (extractAmount > 0 && blockPowered) extractTimer.tick();
        if (extractTimer.value <= 0) setChanged();

        // Now we remove the contents if the PipeItem stack is null.
        // Then, we iterate through the contents. If the insert timer is
        // paused, we drop the stack.
        contents.removeIf ((P)->P.stack == null);
        final Iterator<PipeItem> iter = contents.iterator();
        while(iter.hasNext()) {
            PipeItem next = iter.next();

            if (next.insertTimer.isPaused()) dropItem(next, iter);
        }

        // Now we find the first item, and run the insert timer if it's present.
        contents.stream()
                .findFirst()
                .ifPresent(item -> item.insertTimer.tick());
    }


    /**
     * A method handling pipes accepting items from other pipes.
     * @param dirEntry The entry direction
     * @param item The pipe item to accept
     * @param pipeTile The pipe tile entity
     */
    private void acceptItem(Direction dirEntry, PipeItem item, TileEntityPipeBase pipeTile) {
        // So for this method we start off by getting the surroundings,
        // and making lists of the pipe entries and directions.
        // Then we check for if the directions contain the entry. If not, return.
        Map<Direction, TileEntity> surroundings = getSurroundings();
        List<Map.Entry<Direction, TileEntity>> entryList = surroundings
                .entrySet()
                .stream()
                .filter((E) -> E.getValue() instanceof TileEntityPipeBase)
                .collect(Collectors.toList());

        List<Direction> directions = entryList
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (!directions.contains(dirEntry)) return;

        // Now we filter out the surroundings for the exits.
        // If it's empty, we return. Otherwise, pick a random exit dir
        // from the exit list. if THAT'S null, then we also return.
        List<Map.Entry<Direction, TileEntity>> exitList = surroundings
                .entrySet()
                .stream()
                .filter((E) -> E.getKey() != dirEntry)
                .collect(Collectors.toList());

        if(exitList.isEmpty()) return;
        Direction exit = pickRandomExitDirection(exitList, item.stack);
        if (exit == null) return;

        // Now we validate the exit tile and run three checks.
        // If it's not an IItemIO, Container, or Pipe, we return.
        // Otherwise, we remove the item from the contents, and then
        // add it to the next pipe as a new pipe item.
        TileEntity exitTile = surroundings.get(exit);
        if (!(exitTile instanceof IItemIO) &&
                !(exitTile instanceof Container) &&
                !(exitTile instanceof TileEntityPipeBase)) return;

        pipeTile.contents.remove(item);
        contents.add(new PipeItem(item.stack, dirEntry,exit));
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        super.writeToNBT(tag);
        CompoundTag items = new CompoundTag();
        CompoundTag restrict = new CompoundTag();
        CompoundTag noConnect = new CompoundTag();

        for (Map.Entry<Direction, Boolean> entry : restrictDirections.entrySet()) {
            Direction key = entry.getKey();
            Boolean value = entry.getValue();
            restrict.putBoolean(key.getName(), value);
        }

        for (Map.Entry<Direction, Boolean> entry : noConnectDirections.entrySet()) {
            Direction key = entry.getKey();
            Boolean value = entry.getValue();
            noConnect.putBoolean(key.getName(), value);
        }

        for (int i = 0; i < contents.size(); i++) {
            CompoundTag itemNbt = new CompoundTag();
            PipeItem item = contents.get(i);
            item.writeToNBT(itemNbt);
            items.put(String.valueOf(i),itemNbt);
        }

        tag.put("NoConnect", noConnect);
        tag.put("Restrictions", restrict);
        tag.put("Items", items);
        tag.putInt("ExtractAmount", extractAmount);
        tag.putInt("TransferSpeed", transferSpeed);
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        super.readFromNBT(tag);
        CompoundTag items = tag.getCompound("Items");
        CompoundTag restrict = tag.getCompound("Restrictions");
        CompoundTag noConnect = tag.getCompound("NoConnect");

        contents.clear();
        for (Tag<?> value : items.getValues()) {
            if(value instanceof CompoundTag){
                CompoundTag itemNbt = (CompoundTag) value;
                PipeItem item = new PipeItem(itemNbt);
                contents.add(item);
            }
        }

        for (Tag<?> value : restrict.getValues()) {
            if(value instanceof ByteTag) {
                restrictDirections.replace(Direction.getFromName(value.getTagName()), ((Byte) value.getValue()) == 1);
            }
        }

        for (Tag<?> value : noConnect.getValues()) {
            if(value instanceof ByteTag) {
                noConnectDirections.replace(Direction.getFromName(value.getTagName()), ((Byte) value.getValue()) == 1);
            }
        }

        extractAmount = tag.getInteger("ExtractAmount");
        transferSpeed = tag.getInteger("TransferSpeed");
    }

    public List<PipeItem> getContents() {
        return contents;
    }

    /**
     * The (internal) class handling PipeItem functions and vals/vars.
     */
    public class PipeItem {
        private final ItemStack stack;
        private final Direction entry;
        private final Direction exit;
        private final TickTimer insertTimer = new TickTimer(this, this::insertItem, transferSpeed, false);

        /**
         * Class initializer.
         * @param stack ItemStack
         * @param entry Entry Direction (Catalyst)
         * @param exit Exit Direction (Catalyst)
         */
        public PipeItem(ItemStack stack, Direction entry, Direction exit) {
            this.stack = stack;
            this.entry = entry;
            this.exit = exit;
        }

        /**
         * Class initializer - Compound Tag edition
         * @param tag The compound tag to check
         */
        public PipeItem(CompoundTag tag) {
            this.stack = ItemStack.readItemStackFromNbt(tag.getCompound("stack"));
            this.entry = Direction.getDirectionFromSide(tag.getInteger("entry"));
            this.exit = Direction.getDirectionFromSide(tag.getInteger("exit"));
            insertTimer.value = tag.getInteger("ticks");
        }

        public void writeToNBT(CompoundTag compoundTag) {
            CompoundTag stackNbt = new CompoundTag();
            stack.writeToNBT(stackNbt);

            compoundTag.putInt("entry",entry.getSideNumber());
            compoundTag.putInt("exit",exit.getSideNumber());
            compoundTag.putInt("ticks", insertTimer.value);
            compoundTag.putCompound("stack",stackNbt);
        }

        /**
         * A helper method to get the pipe stack
         * @return Returns the pipe stack
         */
        public ItemStack getStack() {
            return stack;
        }

        /**
         * A helper method to get the pipe entry direction
         * @return Returns the pipe entry direction
         */
        public Direction getEntry() {
            return entry;
        }

        /**
         * A helper method to get the pipe exit direction
         * @return Returns the pipe exit direction
         */
        public Direction getExit() {
            return exit;
        }

        /**
         * A helper method to get the current tick of the
         * pipe's insertion timer
         * @return Returns the current insert timer tick value.
         */
        public int getTicks() {
            return insertTimer.value;
        }

        /**
         * The method to handle inserting an item into a pipe.
         */
        public void insertItem() {
            // IMMEDIATELY we check for the world, because IntelliJ is dumb.
            if (worldObj == null) return;

            // Now we get the tile entity (this) from the exit direction,
            // and set the entry to the opposite direction.
            TileEntity tileEntity = exit.getTileEntity(worldObj, TileEntityPipeBase.this);
            Direction entry = exit.getOpposite();

            // Now we check for if the tile is a container, or a pipe.
            // If it's a container we make a var for it and slot one (0).
            // TODO - Make the starting slot customizable. Maybe via the wrench?
            if ((tileEntity instanceof Container)) {
                Container inv = (Container) tileEntity;
                int slot = 0;

                // Now while the stack size is ABOVE zero we will get the
                // ItemStack at the slot, and check if it's null.
                // If so we set the item and remove it from the contents,
                // and break the loop.
                while (stack.stackSize > 0) {
                    if (slot >= inv.getContainerSize()) break;

                    ItemStack tileStack = inv.getItem(slot);
                    if (tileStack == null) {
                        inv.setItem(slot, stack);
                        contents.remove(this);
                        break;
                    }

                    // Otherwise if the stack is EQUAL to the stack, we get the remainder
                    // when it's subtracted. If the stack size is below or equal to zero we
                    // will increase the slot number and continue.
                    if (tileStack.isItemEqual(stack)) {
                        int remainder = Math.min(tileStack.getMaxStackSize() - tileStack.stackSize, stack.stackSize);
                        if(remainder <= 0) {
                            slot++;
                            continue;
                        }

                        // Now we will subtract the stack size from the remainder,
                        // and add the remainder to the tile's stack.
                        stack.stackSize -= remainder;
                        tileStack.stackSize += remainder;
                    }
                    slot++;
                }

                // Now we check for if the stack size is equal to or below zero. If so,
                // we remove it completely from this pipe stack.
                if (stack.stackSize <= 0) contents.remove(this);

                // If the pipe IS NOT a container, but a pipe, we will
                // just accept the item.
            } else if (tileEntity instanceof TileEntityPipeBase)
                ((TileEntityPipeBase) tileEntity).acceptItem(entry, this, TileEntityPipeBase.this);
        }
    }
}