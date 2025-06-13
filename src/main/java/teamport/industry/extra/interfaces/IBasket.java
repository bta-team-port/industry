package teamport.industry.extra.interfaces;

import net.minecraft.core.block.entity.TileEntityBasket;
import net.minecraft.core.item.ItemStack;

import java.util.Map;

public interface IBasket {
    ItemStack industry$getFirstItemAsStack();

    void industry$removeContentsFromFirstSlot(int amount);

    Map<TileEntityBasket.BasketEntry, Integer> industry$getContents();
}
