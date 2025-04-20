package teamport.industry.extra.mixin;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityBasket;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.industry.extra.interfaces.IBasket;

import java.util.Map;
import java.util.Optional;

@Mixin(value = TileEntityBasket.class, remap = false)
public abstract class TileEntityBasketMixin extends TileEntity implements IBasket {
    @Shadow @Final private Map<TileEntityBasket.BasketEntry, Integer> contents;

    @Shadow protected abstract void updateNumUnits();

    @Override
    public ItemStack industry$getFirstItemAsStack() {
        for(Map.Entry<TileEntityBasket.BasketEntry, Integer> entry : this.contents.entrySet()) {
            TileEntityBasket.BasketEntry basketEntry = entry.getKey();

            if (basketEntry != null) {
                return new ItemStack(basketEntry.id, 1, basketEntry.metadata, basketEntry.tag);
            }
        }

        return null;
    }

    @Override
    public void industry$removeContentsFromFirstSlot(int amount) {
        Optional<Item> item = this.contents.keySet().stream().findFirst().map(TileEntityBasket.BasketEntry::getItem);
        if (item.isPresent()) {
             int stacksize = this.contents.get(this.contents.keySet().stream().findFirst().get());
             if (stacksize > amount) {
                 this.contents.put(this.contents.keySet().stream().findFirst().get(), stacksize - amount);
             } else {
                 this.contents.remove(this.contents.keySet().stream().findFirst().get());
             }

            updateNumUnits();
            worldObj.notifyBlockChange(this.x, this.y, this.z, Blocks.BASKET.id());
        }
    }
}
