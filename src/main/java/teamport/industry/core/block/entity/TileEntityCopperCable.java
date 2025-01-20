package teamport.industry.core.block.entity;

import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;

/*
 * ===========================================================================
 * File: TilEntityCopperCable.java
 * Brief: Tile Entity for the Copper Cables
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class TileEntityCopperCable extends TileEntityEnergyConductorDamageable {
    public TileEntityCopperCable() {
        super();
        setMaxCharge(32);
        setMaxProvide(32);

        for (Direction dir : Direction.values()) {
            setConnection(dir, Connection.BOTH);
        }
    }
}
