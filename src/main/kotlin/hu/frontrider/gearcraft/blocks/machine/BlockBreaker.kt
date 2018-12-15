package hu.frontrider.gearcraft.blocks.machine

import hu.frontrider.gearcraft.core.TraitInstances
import hu.frontrider.gearcraft.core.traits.PowerConsumer
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks.AIR
import net.minecraft.client.render.block.BlockRenderLayer
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.MiningToolItem
import net.minecraft.state.StateFactory
import net.minecraft.state.property.Properties.FACING
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

open class BlockBreaker(builder: Settings,val power: Int//, val tools: Array<MiningToolItem>
                        ) : Block(builder) {

    protected val powerConsumer = PowerConsumer()

    override fun appendProperties(var1: StateFactory.Builder<Block, BlockState>) {
        TraitInstances.facingState.getState(var1)
    }


    override fun getRenderLayer(): BlockRenderLayer {
        return BlockRenderLayer.CUTOUT
    }


    override fun getPlacementState(var1: ItemPlacementContext): BlockState {
        return TraitInstances.facingState.getPlacementState(var1, stateFactory.defaultState)
    }


    override fun neighborUpdate(state: BlockState?, world: World, pos: BlockPos?, var4: Block?, var5: BlockPos?) {
        world.blockTickScheduler.schedule(pos, this, 10)
    }

    override fun scheduledTick(state: BlockState, world: World, pos: BlockPos, rand: Random?) {
        val value = state.get(FACING)
        val targetPower = powerConsumer.getInvertedTargetPower(world, pos, value.opposite)
        if (targetPower >= power) {
            val offset = pos.offset(value.opposite)
            if(world.isAir(offset))
                return

            world.breakBlock(offset, true)
           /*
            val targetState = world.getBlockState(offset)

            tools.forEach {
                if(it.isEffectiveOn(targetState)){
                    world.breakBlock(offset, 2)
                    return
                }
            }*/
        }
    }
}
