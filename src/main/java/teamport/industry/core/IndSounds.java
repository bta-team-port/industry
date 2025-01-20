package teamport.industry.core;

import turniplabs.halplibe.helper.SoundHelper;

import static teamport.industry.Industry.MOD_ID;

/**
 * Sound registration
 * @author Cookie
 * @date 2024-12-24
 */
public class IndSounds {
    public static void registerSounds() {
        SoundHelper.addSound(MOD_ID, "CompressorOp.ogg");

        SoundHelper.addSound(MOD_ID, "ElectroFurnaceLoop.ogg");
        SoundHelper.addSound(MOD_ID, "ElectroFurnaceStart.ogg");
        SoundHelper.addSound(MOD_ID, "ElectroFurnaceStop.ogg");

        SoundHelper.addSound(MOD_ID, "ElectrolyzerLoop.ogg");

        SoundHelper.addSound(MOD_ID, "ExtractorOp.ogg");

        SoundHelper.addSound(MOD_ID, "GeneratorLoop.ogg");
        SoundHelper.addSound(MOD_ID, "GeothermalLoop.ogg");

        SoundHelper.addSound(MOD_ID, "InterruptOne.ogg");

        SoundHelper.addSound(MOD_ID, "IronFurnaceOp.ogg");

        SoundHelper.addSound(MOD_ID, "MaceratorOp.ogg");

        SoundHelper.addSound(MOD_ID, "MassFabLoop.ogg");
        SoundHelper.addSound(MOD_ID, "MassFabScrapSolo.ogg");

        SoundHelper.addSound(MOD_ID, "NuclearReactorGeigerHigh.ogg");
        SoundHelper.addSound(MOD_ID, "NuclearReactorGeigerMed.ogg");
        SoundHelper.addSound(MOD_ID, "NuclearReactorGeigerLow.ogg");
        SoundHelper.addSound(MOD_ID, "NuclearReactorLoop.ogg");

        SoundHelper.addSound(MOD_ID, "RecyclerOp.ogg");

        SoundHelper.addSound(MOD_ID, "TerraformerGenericLoop.ogg");

        SoundHelper.addSound(MOD_ID, "Treetap.ogg");

        SoundHelper.addSound(MOD_ID, "WatermillLoop.ogg");
        SoundHelper.addSound(MOD_ID, "WindGenLoop.ogg");

        SoundHelper.addSound(MOD_ID, "wrench.ogg");
    }
}
