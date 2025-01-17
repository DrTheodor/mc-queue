package dev.drtheo.queue.api.util.structure;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public interface QueueStructureBlockInfo {
    BlockPos queue$pos();
    BlockState queue$state();
    NbtCompound queue$nbt();
}
