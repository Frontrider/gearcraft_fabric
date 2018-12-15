package hu.frontrider.gearcraft.core.traits.gearbox

import hu.frontrider.gearcraft.api.BlockStates
import hu.frontrider.gearcraft.api.traits.power.IGearPowered
import hu.frontrider.gearcraft.core.traits.PowerConsumer
import net.minecraft.block.BlockState
import net.minecraft.state.property.Properties


import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class GearboxPower(val power: Int) : IGearPowered,PowerConsumer() {
    override fun isSideSupported(world: World, pos: BlockPos, blockState: BlockState, side: Direction): Boolean {
        val state = world.getBlockState(pos)

        val inverted = state.get(BlockStates.INVERTED)
        val facing = state.get(Properties.FACING)
        return if (inverted) {
            facing == side
        } else {
            facing != side
        }
    }

    override fun getGearStrength(iBlockAccess: World, blockPos: BlockPos, state: BlockState, side:Direction): Int {
        val strength = getGearPower(iBlockAccess, blockPos, state,side)
        return if (strength != 0)
            4
        else
            0
    }

    override fun getGearPower(iBlockAccess: World, blockPos: BlockPos, state: BlockState, side:Direction): Int {

        val inverted = state.get(BlockStates.INVERTED)
        val facing = state.get(Properties.FACING)

        return if (inverted) {
            if(facing != side)
                return 0
            val required = getTargetPower(iBlockAccess, blockPos.offset(facing), facing)
            val total = getInvertedTargetPower(iBlockAccess, blockPos, facing)

            if (total >= required) total else 0
        } else {
            if(facing == side)
                return 0
            val total = getTargetPower(iBlockAccess, blockPos.offset(facing), facing)
            val required = getInvertedTargetPower(iBlockAccess, blockPos, facing)
            if (total >= required)
                total
            else
                0
        }
    }


}