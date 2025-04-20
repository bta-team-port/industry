package teamport.industry.core.block.entity;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.TickTimer;
import sunsetsatellite.catalyst.core.util.io.IItemIO;
import sunsetsatellite.catalyst.core.util.vector.Vec3f;
import teamport.industry.extra.interfaces.IBasket;

import java.util.*;
import java.util.stream.Collectors;

public class TileEntityPipe extends TileEntity {
    private final List<PipeItem> contents = new ArrayList<>();
    private final Random random = new Random();
    public Map<Direction, Boolean> noConnectDirections = new HashMap<>();
    public Map<Direction, Boolean> restrictDirections = new HashMap<>();
    public static int TRANSFER_TICKS = 20*3;
    public static int EXTRACT_TICKS = 20*2;
    private final TickTimer extractTimer = new TickTimer(this, this::extractItem, EXTRACT_TICKS, true);

    public TileEntityPipe() {
        for (Direction dir : Direction.values()) {
            restrictDirections.put(dir,false);
            noConnectDirections.put(dir,false);
        }
    }

    public boolean addItem(ItemStack stack, Direction entry){
        Map<Direction, TileEntity> surroundings = getSurroundings();
        List<Map.Entry<Direction, TileEntity>> exitList = surroundings.entrySet().stream().filter((E) -> E.getKey() != entry).collect(Collectors.toList());
        if(exitList.isEmpty()){
            return false;
        }

        Direction exit = null;
        //select the exit direction based on mode
        exit = pickRandomExitDirection(exitList,stack);
        if(exit == null){
            return false;
        }

        //check if the exit tile exists and is correct
        TileEntity exitTile = surroundings.get(exit);
        if (!(exitTile instanceof IItemIO) && !(exitTile instanceof Container) && !(exitTile instanceof TileEntityPipe)) {
            return false;
        }

        //add item to conduit
        PipeItem pipeItem = new PipeItem(stack, entry, exit);
        contents.add(pipeItem);
        return true;
    }

    private Map<Direction, TileEntity> getSurroundings() {
        if (worldObj == null || worldObj.isClientSide) {
            return null;
        }

        Map<Direction, TileEntity> surroundings = new HashMap<>();
        for(Direction direction : Direction.values()) {
            TileEntity tile = direction.getTileEntity(worldObj, this);
            if (tile != null) {
                if (tile instanceof TileEntityPipe || tile instanceof Container || tile instanceof IBasket) {
                    if (!noConnectDirections.get(direction)) {
                        surroundings.put(direction, tile);
                    }
                }
            }
        }

        return surroundings;
    }

    public void dropItem(PipeItem item, Iterator<PipeItem> iter) {
        if(contents.contains(item)){
            Vec3f dirVec = item.exit.getVecF().divide(2);
            Vec3f offset = new Vec3f(x,y,z).add(dirVec).add(0.5);
            EntityItem entityitem = new EntityItem(worldObj, (float) offset.x, (float) offset.y, (float) offset.z, item.stack);
            float multiplier = 0.05F;
            entityitem.xd = dirVec.x * multiplier;
            entityitem.yd = dirVec.y * multiplier;
            entityitem.zd = dirVec.z * multiplier;
            worldObj.entityJoinedWorld(entityitem);
            if(iter != null){
                iter.remove();
            } else {
                contents.remove(item);
            }
        }
    }

    public void extractItem() {
        //get surroundings blocks, filter out item conduits
        Map<Direction, TileEntity> surroundings = getSurroundings();
        List<Map.Entry<Direction, TileEntity>> entryList = surroundings.entrySet().stream().filter((E) -> !(E.getValue() instanceof TileEntityPipe)).collect(Collectors.toList());
        if (entryList.isEmpty()) {
            return;
        }
        Direction entry = null;
        //select the entry direction randomly
        entry = entryList.get(random.nextInt(entryList.size())).getKey();
        if (entry == null) {
            return;
        }
        //conduits can only extract from tiles that implement IItemIO for now
        TileEntity entryTile = surroundings.get(entry);
        if (entryTile instanceof Container) {
            Container inv = ((Container) entryTile);
            //connection check

            for (int i = 0; i < inv.getContainerSize(); i++) {
                if (inv.getItem(i) != null) {
                    ItemStack stack = inv.getItem(i).copy();
                    if (stack.stackSize >= 8) {
                        stack = stack.splitStack(8);
                    } else {
                        stack = stack.splitStack(stack.stackSize);
                    }
                    Direction finalEntry = entry;
                    //filter out the entry direction
                    List<Map.Entry<Direction, TileEntity>> exitList = surroundings.entrySet().stream().filter((E) -> E.getKey() != finalEntry).collect(Collectors.toList());
                    if (exitList.isEmpty()) {
                        return;
                    }
                    Direction exit = null;
                    //select the exit direction based on mode
                    exit = pickRandomExitDirection(exitList, stack);
                    if (exit == null) {
                        return;
                    }
                    //check if the exit tile exists and is correct
                    TileEntity exitTile = surroundings.get(exit);
                    if (!(exitTile instanceof IItemIO) && !(exitTile instanceof Container) && !(exitTile instanceof TileEntityPipe)) {
                        return;
                    }
                    //add item to conduit
                    PipeItem pipeItem = new PipeItem(stack, entry, exit);
                    contents.add(pipeItem);
                    if (inv.getItem(i).stackSize <= 0) {
                        inv.setItem(i, null);
                    }
                }
            }
        }
    }


    private Direction pickRandomExitDirection(List<Map.Entry<Direction, TileEntity>> exitList, ItemStack stack){
        List<Direction> blockedDirs = new ArrayList<>();
        for (Map.Entry<Direction, TileEntity> exitEntry : exitList) {
            if(exitEntry.getValue() instanceof IItemIO || exitEntry.getValue() instanceof Container){
                if(exitEntry.getValue() instanceof IItemIO){
                    IItemIO io = (IItemIO) exitEntry.getValue();
                    if(io.getItemIOForSide(exitEntry.getKey().getOpposite()) == Connection.INPUT || io.getItemIOForSide(exitEntry.getKey().getOpposite()) == Connection.BOTH){
                        return exitEntry.getKey();
                    } else {
                        blockedDirs.add(exitEntry.getKey());
                    }
                } else {
                    Container inv = (Container) exitEntry.getValue();
                    for (int i = 0; i < inv.getContainerSize(); i++) {
                        if(inv.getItem(i) == null){
                            return exitEntry.getKey();
                        } else if (inv.getItem(i).isItemEqual(stack) && inv.getItem(i).stackSize+stack.stackSize <= inv.getMaxStackSize() && inv.getItem(i).stackSize+stack.stackSize <= inv.getItem(i).getMaxStackSize(inv)) {
                            return exitEntry.getKey();
                        }
                    }
                    blockedDirs.add(exitEntry.getKey());
                }
            }
        }
        restrictDirections.forEach((D,B)->{
            if(B && !blockedDirs.contains(D)){
                blockedDirs.add(D);
            }
        });
        noConnectDirections.forEach((D,B)->{
            if(B && !blockedDirs.contains(D)){
                blockedDirs.add(D);
            }
        });
        exitList = exitList.stream().filter((E)->!(blockedDirs.contains(E.getKey()))).collect(Collectors.toList());
        if(exitList.isEmpty()){
            return null;
        } else if (exitList.size() == 1) {
            return exitList.get(0).getKey();
        }
        return exitList.get(random.nextInt(exitList.size())).getKey();
    }

    @Override
    public void tick() {
        super.tick();
        
        for (Direction dir : Direction.values()) {
            if (dir.getTileEntity(worldObj, this) instanceof Container) {
                Container inv = ((Container) dir.getTileEntity(worldObj, this));
                for (PipeItem pipeItem : contents) {
                    pipeItem.insertItem();
                    break;
                }
            }
        }

        extractTimer.tick();
        contents.removeIf((P)->P.stack == null);
        final Iterator<PipeItem> iter = contents.iterator();
        while(iter.hasNext()){
            PipeItem next = iter.next();

            if(next.insertTimer.isPaused()) {
                dropItem(next, iter);
            }
        }

        for (PipeItem pipeItem : contents.toArray(new PipeItem[0])) {
            pipeItem.insertTimer.tick();
        }
    }


    private void acceptItem(Direction entry, PipeItem item, TileEntityPipe pipe){
        //get surroundings and keep only surrounding pipes
        Map<Direction, TileEntity> surroundings = getSurroundings();
        List<Map.Entry<Direction, TileEntity>> entryList = surroundings.entrySet().stream().filter((E) -> E.getValue() instanceof TileEntityPipe).collect(Collectors.toList());
        List<Direction> directions = entryList.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        //validate entry direction
        if(directions.contains(entry)){
            //filter out the entry direction
            List<Map.Entry<Direction, TileEntity>> exitList = surroundings.entrySet().stream().filter((E) -> E.getKey() != entry).collect(Collectors.toList());
            if(exitList.isEmpty()){
                return;
            }
            Direction exit = null;
            exit = pickRandomExitDirection(exitList, item.stack);

            if(exit == null){
                return;
            }
            //validate exit tile and transfer item
            TileEntity exitTile = surroundings.get(exit);
            if (!(exitTile instanceof IItemIO) && !(exitTile instanceof Container) && !(exitTile instanceof TileEntityPipe)) {
                return;
            }

            pipe.contents.remove(item);
            contents.add(new PipeItem(item.stack,entry,exit));
        }
    }

    public List<PipeItem> getContents() {
        return contents;
    }

    public class PipeItem {
        private final ItemStack stack;
        private final Direction entry;
        private final Direction exit;
        private final TickTimer insertTimer = new TickTimer(this, this::insertItem, TRANSFER_TICKS, false);

        public PipeItem(ItemStack stack, Direction entry, Direction exit) {
            this.stack = stack;
            this.entry = entry;
            this.exit = exit;
        }

        public ItemStack getStack() {
            return stack;
        }

        public Direction getEntry() {
            return entry;
        }

        public Direction getExit() {
            return exit;
        }

        public int getTicks() {
            return insertTimer.value;
        }

        public void insertItem() {
            TileEntity tileEntity = exit.getTileEntity(worldObj, TileEntityPipe.this);
            Direction entry = exit.getOpposite();
            if ((tileEntity instanceof Container)) {
                Container inv = ((Container) tileEntity);
                int slot = 0;
                while (stack.stackSize > 0) {
                    if(slot >= inv.getContainerSize()){
                        break;
                    }

                    ItemStack tileStack = inv.getItem(slot);
                    if(tileStack == null) {
                        inv.setItem(slot,stack);
                        contents.remove(this);
                        break;
                    } else if(tileStack.isItemEqual(stack)){
                        int remainder = Math.min(tileStack.getMaxStackSize() - tileStack.stackSize, stack.stackSize);
                        if(remainder <= 0) {
                            slot++;
                            continue;
                        }
                        stack.stackSize -= remainder;
                        tileStack.stackSize += remainder;
                    }
                    slot++;
                }
                if(stack.stackSize <= 0){
                    contents.remove(this);
                }
            } else if(tileEntity instanceof TileEntityPipe) {
                ((TileEntityPipe) tileEntity).acceptItem(entry,this,TileEntityPipe.this);
            }
        }
    }
}