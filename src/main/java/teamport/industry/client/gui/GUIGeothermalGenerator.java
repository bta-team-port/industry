package teamport.industry.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import teamport.industry.core.block.entity.TileEntityGeothermalGenerator;
import teamport.industry.core.container.ContainerGeothermalGenerator;

/**
 * Client gui for the geothermal generator tile entity
 * @author Cookie
 * @date 2025-01-22
 */
@Environment(EnvType.CLIENT)
public class GUIGeothermalGenerator extends GuiContainer {
    private final TileEntityGeothermalGenerator tileEntity;

    public GUIGeothermalGenerator(InventoryPlayer inventory, TileEntityGeothermalGenerator tileEntity) {
        super(new ContainerGeothermalGenerator(inventory, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f) {
        int texture = mc.renderEngine.getTexture("/assets/industry/textures/gui/geogenerator.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(texture);

        int scrnX = (width - xSize) / 2;
        int scrnY = (height - ySize) / 2;
        drawTexturedModalRect(scrnX, scrnY, 0, 0, xSize, ySize);

        if (tileEntity.getCurrentFluidAmount() > 0) {
            int fluidTime = tileEntity.getRemainingFluidTime(14);
            drawTexturedModalRect(scrnX + 66, scrnY + 37 + 12 - fluidTime, 176, 12 - fluidTime, 14, fluidTime);
        }

        if (tileEntity.getEnergy() > 0) {
            int energyWidth = tileEntity.getEnergyScaled(24);
            drawTexturedModalRect(scrnX + 88, scrnY + 34, 176, 14, energyWidth, 17);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        I18n i18n = I18n.getInstance();
        fontRenderer.drawString(i18n.translateKey("industry.gui.geogenerator.label.generator"), 30, 6, 4210752);

        fontRenderer.drawString(i18n.translateKey("industry.gui.geogenerator.label.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
}
