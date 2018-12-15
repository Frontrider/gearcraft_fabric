package hu.frontrider.gearcraft.core.traits.producer

import hu.frontrider.gearcraft.core.IFluidHelper
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FluidBlock
import net.minecraft.state.property.IntegerProperty

class FluidBlockHelper(private val block: Block, val condition: (Int) -> Boolean, private val property: IntegerProperty = FluidBlock.field_11278) : IFluidHelper {
    override fun getWaterValue(blockState: BlockState): Int {
        if (blockState.block != block) {
            return 0
        }
        var level = 1
        if (blockState.properties.contains(property))
            level = blockState.get(property)

        return if (condition(level))
            4
        else 0
    }
}