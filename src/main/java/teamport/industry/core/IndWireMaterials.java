package teamport.industry.core;

import sunsetsatellite.catalyst.energy.electric.api.VoltageTier;
import sunsetsatellite.catalyst.energy.electric.api.WireMaterial;

/**
 * Definitions for materials that can have wires or cables made out of them.
 * @author sunsetsatellite
 * @date 2025-01-20
 */
public class IndWireMaterials {

    //change properties as you see fit, these are placeholders
    public static WireMaterial COPPER = new WireMaterial(
            "Copper",
            "material.industry.wire.copper",
            0xFFFFFF,
            1,
            VoltageTier.LV,
            1,
            1085 //actual melting point of copper in °C lol (as placeholder)
    );
}
