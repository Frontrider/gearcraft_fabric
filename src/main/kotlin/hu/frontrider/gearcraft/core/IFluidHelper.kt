package hu.frontrider.gearcraft.core

import net.minecraft.block.BlockState


interface IFluidHelper {
    fun getWaterValue(blockState: BlockState): Int
}