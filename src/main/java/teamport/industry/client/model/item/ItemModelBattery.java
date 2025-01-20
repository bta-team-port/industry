package teamport.industry.client.model.item;

import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.catalyst.energy.api.IEnergyItem;

public class ItemModelBattery extends ItemModelStandard {
    private final String texturePath;
    public ItemModelBattery(Item item, String namespace, String texturePath) {
        super(item, namespace);
        this.texturePath = texturePath;
    }

    @Override
    public @NotNull IconCoordinate getIcon(@Nullable Entity entity, ItemStack itemStack) {
        if (itemStack.getItem() instanceof IEnergyItem) {
            int energy = ((IEnergyItem) itemStack.getItem()).getEnergy(itemStack);
            int capacity = ((IEnergyItem) itemStack.getItem()).getCapacity(itemStack);

            if (energy >= capacity) {
                return TextureRegistry.getTexture(texturePath + "4");
            } else if (energy >= capacity * 0.75) {
                return TextureRegistry.getTexture(texturePath + "3");
            } else if (energy >= capacity * 0.5 && energy < capacity * 0.75) {
                return TextureRegistry.getTexture(texturePath + "2");
            } else if (energy >= capacity * 0.25 && energy < capacity * 0.5) {
                return TextureRegistry.getTexture(texturePath + "1");
            } else {
                return TextureRegistry.getTexture(texturePath + "0");
            }
        }
        return TextureRegistry.getTexture(texturePath + "0");
    }
}
