package teamport.industry.core.block.entity;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import sunsetsatellite.catalyst.core.util.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TileEntityPipeIron extends TileEntityPipeBase {
    private Direction outputDirection = null;

    public TileEntityPipeIron() {
        super(0, 20 * 3);
    }

    /**
     *  This method handles updating the iron pipe's restricted directions.
     */
    public void updateRestrictions() {
        // World null check so IntelliJ shuts up.
        if (worldObj == null) return;

        // So first we get the surrounding directions.
        // If it's null, we return. Otherwise, we get the CONNECTED directions.
        // We also clear the current restrictions before the rest of the method.
        Map<Direction, TileEntity> surroundings = getSurroundings();
        if (surroundings == null) return;

        List<Direction> connectedDirs = new ArrayList<>(surroundings.keySet());
        for (Direction dir : Direction.values()) restrictDirections.put(dir, false);

        // Now we check if the "outputDirection" var is null. If it is, and
        // the connected directions are empty, keep it null.
        // Else we set it to the first dir. (0)
        if (outputDirection == null || !connectedDirs.contains(outputDirection))
            outputDirection = connectedDirs.isEmpty() ? null : connectedDirs.get(0);

        // Finally, we check if the connection direction is equal to
        // the output direction. If not, we set it to restrict.
        for (Direction dir : connectedDirs) if (dir != outputDirection) restrictDirections.put(dir, true);
        worldObj.markBlockNeedsUpdate(x, y, z);
    }

    /**
     * This method handles rotating the "outputDirection" var.
     */
    public void rotateOutput() {
        // We start by getting the surrounding, and checking if they're null.
        // We return if so.
        Map<Direction, TileEntity> surroundings = getSurroundings();
        if (surroundings == null) return;

        // Now we check if the connected directions size is equal to or
        // below one. If so, return.
        List<Direction> connectedDirs = new ArrayList<>(surroundings.keySet());
        if (connectedDirs.size() <= 1) return;

        // Now we get the connected direction index for the output.
        // Then we get the next index by adding one to the current index
        // and comparing it to the current connections size.
        // Then we set the output to the next index.
        int currentIndex = connectedDirs.indexOf(outputDirection);
        int nextIndex = (currentIndex + 1) % connectedDirs.size();
        outputDirection = connectedDirs.get(nextIndex);

        // Finally, we re-update the restrictions and call a change method.
        updateRestrictions();
        worldObj.markBlockNeedsUpdate(x, y, z);
    }

    /**
     * This method handles getting the output direction.
     * @return Returns the output direction
     */
    public Direction getOutputDirection() {
        return outputDirection;
    }

    /**
     * This method handles setting the output direction.
     * @param outputDirection The new direction to set.
     */
    public void setOutputDirection(Direction outputDirection) {
        this.outputDirection = outputDirection;
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        super.writeToNBT(tag);
        if (outputDirection != null) tag.putInt("OutputDir", outputDirection.getSideNumber());
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        super.readFromNBT(tag);
        if (tag.getString("OutputDir") != null)
            outputDirection = Direction.getDirectionFromSide(tag.getInteger("OutputDir"));

        updateRestrictions();
    }
}
