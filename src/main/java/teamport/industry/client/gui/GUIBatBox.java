package teamport.industry.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import teamport.industry.core.block.entity.TileEntityBatBox;
import teamport.industry.core.container.ContainerBatBox;

/**
 * Client gui for the batbox tile entity
 * @author Cookie
 * @date 2025-01-23
 */
@Environment(EnvType.CLIENT)
public class GUIBatBox extends GuiContainer {
    private final TileEntityBatBox tileEntity;

    public GUIBatBox(InventoryPlayer inventory, TileEntityBatBox tileEntity) {
        super(new ContainerBatBox(inventory, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f) {
        int texture = mc.renderEngine.getTexture("/assets/industry/textures/gui/batbox.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(texture);

        int scrnX = (width - xSize) / 2;
        int scrnY = (height - ySize) / 2;
        drawTexturedModalRect(scrnX, scrnY, 0, 0, xSize, ySize);

        if (tileEntity.getEnergy() > 0) {
            int energyWidth = tileEntity.getEnergyScaled(24);
            drawTexturedModalRect(scrnX + 94, scrnY + 35, 176, 14, energyWidth, 17);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        I18n i18n = I18n.getInstance();
        fontRenderer.drawString(i18n.translateKey("industry.gui.batbox.label.batbox"), 72, 6, 4210752);
        fontRenderer.drawString(String.valueOf(tileEntity.getEnergy()), 91, 24, 4210752);
        fontRenderer.drawString(String.valueOf(tileEntity.getCapacity()), 91, 56, 4210752);
        fontRenderer.drawString(i18n.translateKey("industry.gui.batbox.label.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
}
