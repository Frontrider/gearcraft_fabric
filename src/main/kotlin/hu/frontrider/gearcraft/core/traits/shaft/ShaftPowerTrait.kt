package hu.frontrider.gearcraft.core.traits.shaft

import hu.frontrider.gearcraft.api.traits.BlockStateManager
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory
import net.minecraft.state.property.IntegerProperty

/**
 * manages the amount of power flowing through the shafts.
 * */
class ShaftPowerTrait(val powerState: Int) : BlockStateManager {

    companion object {
        val Power4 = IntegerProperty.create("torque", 0, 4)
        val Power8 = IntegerProperty.create("torque", 0, 8)
        val Power16 = IntegerProperty.create("torque", 0, 16)
        val Power32 = IntegerProperty.create("torque", 0, 32)
    }

    override fun getState(builder: StateFactory.Builder<Block, BlockState>): StateFactory.Builder<Block, BlockState> {
        return builder.with(
                when (powerState) {
                    8 -> Power8
                    16 -> Power16
                    32 -> Power32
                    else -> Power4
                }
        )
    }

    override fun getPlacementState(context: ItemPlacementContext, state: BlockState): BlockState {
        return state.with(when (powerState) {
            8 -> Power8
            16 -> Power16
            32 -> Power32
            else -> Power4
        }, 0)
    }
}