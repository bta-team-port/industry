package teamport.industry.core.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.tag.Tag;

/*
 * ===========================================================================
 * File: IndBlockTags.java
 * Brief: Block tag creation
 * Author: Cookie
 * Date: 2024-12-24
 * ===========================================================================
 */

public class IndBlockTags {
    public static Tag<Block> REQUIRES_WRENCH = Tag.of("requires_wrench");
    public static Tag<Block> BROKEN_BY_WIRECUTTERS = Tag.of("broken_by_wirecutters");

    public IndBlockTags() {
    }
}
