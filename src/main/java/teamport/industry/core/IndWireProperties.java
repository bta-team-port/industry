package teamport.industry.core;

import sunsetsatellite.catalyst.energy.electric.api.WireProperties;

/**
 * Definitions for wire/cable properties.
 * @author sunsetsatellite
 * @date 2025-01-20
 */
public class IndWireProperties {

    //change properties as you see fit, these are placeholders
    public static WireProperties COPPER = new WireProperties(
            1,
            false,
            false,
            IndWireMaterials.COPPER
    );

    public static WireProperties COPPER_INSULATED = new WireProperties(
            1,
            true,
            false,
            IndWireMaterials.COPPER
    );

}
