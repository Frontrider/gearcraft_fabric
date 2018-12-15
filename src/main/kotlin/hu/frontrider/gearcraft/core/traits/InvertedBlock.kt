package hu.frontrider.gearcraft.core.traits

import hu.frontrider.gearcraft.api.BlockStates
import hu.frontrider.gearcraft.api.BlockStates.INVERTED
import hu.frontrider.gearcraft.api.traits.BlockStateManager
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory

class InvertedBlock : BlockStateManager {
    override fun getState(builder: StateFactory.Builder<Block, BlockState>): StateFactory.Builder<Block, BlockState> {
        return builder.with(BlockStates.INVERTED)
    }

    override fun getPlacementState(context: ItemPlacementContext, state: BlockState): BlockState {

        val player = context.player ?: return state.with(INVERTED, false)

        return state.with(INVERTED, player.isSneaking)
    }
}