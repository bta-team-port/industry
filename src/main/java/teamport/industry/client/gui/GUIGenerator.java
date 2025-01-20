package teamport.industry.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import teamport.industry.core.block.entity.TileEntityGenerator;
import teamport.industry.core.container.ContainerGenerator;

/**
 * Client gui for the Generator tile entity
 * @author Cookie
 * @date 2025-01-14
 */
@Environment(EnvType.CLIENT)
public class GUIGenerator extends GuiContainer {
    TileEntityGenerator tileEntity;

    public GUIGenerator(InventoryPlayer inventory, TileEntityGenerator tileEntity) {
        super(new ContainerGenerator(inventory, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f) {
        int texture = mc.renderEngine.getTexture("/assets/industry/textures/gui/generator.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(texture);

        int scrnX = (width - xSize) / 2;
        int scrnY = (height - ySize) / 2;
        drawTexturedModalRect(scrnX, scrnY, 0, 0, xSize, ySize);

        if (tileEntity.getCurrentBurnTime() > 0) {
            int fireHeight = tileEntity.getRemainingBurnTimeScaled(14);
            drawTexturedModalRect(scrnX + 66, scrnY + 37 + 12 - fireHeight, 176, 12 - fireHeight, 14, fireHeight);
        }

        if (tileEntity.getEnergy() > 0) {
            int energyWidth = tileEntity.getEnergyScaled(24);
            drawTexturedModalRect(scrnX + 94, scrnY + 35, 176, 14, energyWidth, 17);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        I18n i18n = I18n.getInstance();
        fontRenderer.drawString(i18n.translateKey("industry.gui.generator.label.generator"), 60, 6, 4210752);

        fontRenderer.drawString(i18n.translateKey("industry.gui.generator.label.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
}
