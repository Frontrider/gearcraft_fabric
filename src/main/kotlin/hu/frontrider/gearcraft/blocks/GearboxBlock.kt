package hu.frontrider.gearcraft.blocks

import hu.frontrider.gearcraft.api.traits.power.IGearPowered
import hu.frontrider.gearcraft.api.traits.ITooltipped
import hu.frontrider.gearcraft.core.TraitInstances.facingState
import hu.frontrider.gearcraft.core.TraitInstances.invertedState
import hu.frontrider.gearcraft.core.tooltip.PowerTooltip
import hu.frontrider.gearcraft.core.traits.gearbox.GearboxPower
import hu.frontrider.gearcraft.core.tooltip.InvertedToolTip
import hu.frontrider.gearcraft.core.tooltip.MultiTooltip
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.render.block.BlockRenderLayer
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateFactory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class GearboxBlock(builder: Block.Settings, val power: Int) :
        Block(builder),
        IGearPowered by GearboxPower(power),
        ITooltipped by MultiTooltip(PowerTooltip(power), InvertedToolTip("gearcraft.gearbox.invertmessage")) {

    override fun appendProperties(var1: StateFactory.Builder<Block, BlockState>) {
        super.appendProperties(invertedState.getState(facingState.getState(var1)))
    }

    override fun getPlacementState(var1: ItemPlacementContext): BlockState {
        return facingState.getPlacementState(var1,
                invertedState.getPlacementState(var1,
                        stateFactory.defaultState))
    }

    override fun neighborUpdate(state: BlockState?, world: World, pos: BlockPos?, var4: Block?, var5: BlockPos?) {
        world.blockTickScheduler.schedule(pos, this, 10)
    }

    override fun onPlaced(world: World, pos: BlockPos, var3: BlockState?, var4: LivingEntity?, var5: ItemStack?) {
        world.blockTickScheduler.schedule(pos, this, 10)
    }

    override fun scheduledTick(var1: BlockState?, var2: World, var3: BlockPos, var4: Random?) {
        var2.updateNeighborsAlways(var3,this)
    }

    override fun getRenderLayer(): BlockRenderLayer {
        return BlockRenderLayer.CUTOUT
    }

    override fun isFullBoundsCubeForCulling(var1: BlockState?): Boolean {
        return true
    }
}