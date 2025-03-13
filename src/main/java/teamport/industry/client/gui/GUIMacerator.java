package teamport.industry.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import teamport.industry.core.block.entity.TileEntityMacerator;
import teamport.industry.core.container.ContainerMacerator;

/**
 * Client gui for the macerator tile entity
 * @author Cookie
 * @date 2025-03-10
 */
@Environment(EnvType.CLIENT)
public class GUIMacerator extends GuiContainer {
    private final TileEntityMacerator tileEntity;

    public GUIMacerator(InventoryPlayer inventory, TileEntityMacerator tileEntity) {
        super(new ContainerMacerator(inventory, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f) {
        int texture = mc.renderEngine.getTexture("/assets/industry/textures/gui/macerator.png");
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(texture);

        int scrnX = (width - xSize) / 2;
        int scrnY = (height - ySize) / 2;
        drawTexturedModalRect(scrnX, scrnY, 0, 0, xSize, ySize);

        if (tileEntity.getEnergy() > 0) {
            int energyHeight = tileEntity.getEnergyScaled(14);
            drawTexturedModalRect(scrnX + 56, scrnY + 36, 176, 0, 14, energyHeight);
        }

        if (tileEntity.getMachineTime() > 0) {
            int progressWidth = tileEntity.getMachineProgressScaled(24);
            drawTexturedModalRect(scrnX + 79, scrnY + 34, 176, 14, progressWidth, 17);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        super.drawGuiContainerForegroundLayer();
        I18n i18n = I18n.getInstance();
        fontRenderer.drawString(i18n.translateKey("industry.gui.macerator.label.macerator"), 60, 6, 4210752);
        fontRenderer.drawString(i18n.translateKey("industry.gui.macerator.label.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
}
