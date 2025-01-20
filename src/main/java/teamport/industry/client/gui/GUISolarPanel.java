package teamport.industry.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import teamport.industry.core.block.entity.TileEntitySolarPanel;
import teamport.industry.core.container.ContainerSolarPanel;

/*
 * ===========================================================================
 * File: GUISolarPanel.java
 * Brief: Client gui for the solar panel tile entity
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */
@Environment(EnvType.CLIENT)
public class GUISolarPanel extends GuiContainer {
    private final TileEntitySolarPanel tileEntity;

    public GUISolarPanel(InventoryPlayer inventory, TileEntitySolarPanel tileEntity) {
        super(new ContainerSolarPanel(inventory, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f) {
        int texture = mc.renderEngine.getTexture("/assets/industry/textures/gui/solarpanel.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(texture);

        int scrnX = (width - xSize) / 2;
        int scrnY = (height - ySize) / 2;
        drawTexturedModalRect(scrnX, scrnY, 0, 0, xSize, ySize);

        if (tileEntity.isDayAndClear()) {
            drawTexturedModalRect(scrnX + 80, scrnY + 45, 176, 0, 14, 14);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        I18n i18n = I18n.getInstance();
        fontRenderer.drawString(i18n.translateKey("industry.gui.solarpanel.label.solarpanel"), 60, 6, 4210752);

        fontRenderer.drawString(i18n.translateKey("industry.gui.solarpanel.label.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
}
