package teamport.industry.client.model.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.vector.Vec3f;
import teamport.industry.core.block.entity.TileEntityPipe;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class TileEntityRendererPipe extends TileEntityRenderer<TileEntityPipe> {
    public EntityItem renderEntity = new EntityItem(null);
    private Random rand = new Random();

    @Override
    public void doRender(Tessellator tessellator, TileEntityPipe tileEntity, double x, double y, double z, float partialTick) {
        for (TileEntityPipe.PipeItem content : tileEntity.getContents()) {
            Direction begin = content.getEntry();
            Direction end = content.getExit();
            Vec3f beginVec = begin.getVecF();
            Axis beginAxis = begin.getAxis();
            Vec3f endVec = end.getVecF();
            Axis endAxis = end.getAxis();
            double v = 0;
            boolean positive = (begin == Direction.Z_POS || begin == Direction.Y_POS || begin == Direction.X_POS);
            v = Catalyst.map(content.getTicks(), TileEntityPipe.TRANSFER_TICKS, 0, 1, -1);
            Vec3f base = new Vec3f(0.5d);
            Vec3f pos = new Vec3f(x, y, z);
            Vec3f offset = new Vec3f();

            float lerped = 0;
            switch (beginAxis) {
                case X:
                    lerped = MathHelper.lerp((float) beginVec.x, (float) base.x, (float) v);
                    if(!positive){
                        lerped = (float) Catalyst.map(lerped, -2.5f,0.5f,-0.5f,0.5f);
                    }
                    offset.x += lerped;
                    offset.y += base.y;
                    offset.z += base.z;
                    break;
                case Y:
                    lerped = MathHelper.lerp((float) beginVec.y, (float) base.y, (float) v);
                    if(!positive){
                        lerped = (float) Catalyst.map(lerped, -2.5f,0.5f,-0.5f,0.5f);
                    }
                    offset.x += base.x;
                    offset.y += lerped;
                    offset.z += base.z;
                    break;
                case Z:
                    lerped = MathHelper.lerp((float) beginVec.z, (float) base.z, (float) v);
                    if(!positive){
                        lerped = (float) Catalyst.map(lerped, -2.5f,0.5f,-0.5f,0.5f);
                    }

                    offset.x += base.x;
                    offset.y += base.y;
                    offset.z += lerped;
                    break;
                default:
                    break;
            }

            Vec3f p = pos.copy().add(offset);

            if (!tileEntity.getContents().isEmpty()) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)p.x, (float) p.y, (float) p.z);
                this.renderEntity.world = tileEntity.worldObj;
                this.renderEntity.x = this.renderEntity.xo = tileEntity.x + 0.5;
                this.renderEntity.y = this.renderEntity.yo = tileEntity.y + 0.5;
                this.renderEntity.z = this.renderEntity.zo = tileEntity.z + 0.5;

                float lightLevel = Minecraft.getMinecraft().currentWorld.getLightBrightness(tileEntity.x, tileEntity.y, tileEntity.z);
                if (Global.accessor.isFullbrightEnabled()) {
                    lightLevel = 1.0F;
                }

                ItemStack stack = tileEntity.getContents().get(0).getStack();
                BlockModel.setRenderBlocks(EntityRenderDispatcher.instance.itemRenderer.renderBlocksInstance);
                GL11.glEnable(32826);
                GL11.glEnable(3042);
                ItemModelDispatcher.getInstance().getDispatch(stack).renderAsItemEntity(Tessellator.instance, this.renderEntity, rand, stack, 1, 0, lightLevel, partialTick);
                GL11.glDisable(3042);
                GL11.glDisable(32826);
                GL11.glPopMatrix();

                this.renderEntity.world = null;
            }
        }
    }
}
