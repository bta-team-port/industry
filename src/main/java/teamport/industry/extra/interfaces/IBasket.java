package teamport.industry.extra.interfaces;

import net.minecraft.core.item.ItemStack;

public interface IBasket {
    ItemStack industry$getFirstItemAsStack();

    void industry$removeContentsFromFirstSlot(int amount);
}
