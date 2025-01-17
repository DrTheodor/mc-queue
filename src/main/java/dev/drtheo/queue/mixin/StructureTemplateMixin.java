package dev.drtheo.queue.mixin;

import dev.drtheo.queue.api.util.structure.QueueStructureBlockInfo;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StructureTemplate.StructureBlockInfo.class)
public class StructureTemplateMixin implements QueueStructureBlockInfo {

    @Shadow @Final public BlockPos pos;

    @Shadow @Final public BlockState state;

    @Shadow @Final public NbtCompound nbt;

    @Override
    public BlockPos queue$pos() {
        return this.pos;
    }

    @Override
    public BlockState queue$state() {
        return this.state;
    }

    @Override
    public NbtCompound queue$nbt() {
        return this.nbt;
    }
}
