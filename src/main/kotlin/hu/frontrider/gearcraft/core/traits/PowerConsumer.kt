package hu.frontrider.gearcraft.core.traits

import hu.frontrider.gearcraft.api.traits.power.ITransmission
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

open class PowerConsumer {

    fun getTargetPower(world: World, pos: BlockPos, side: Direction): Int {
        val targetState = world.getBlockState(pos)
        val targetBlock = targetState.block as? ITransmission ?: return 0

        if (targetBlock.getGearStrength(world, pos, world.getBlockState(pos), side) > 0 && targetBlock.isSideSupported(world, pos, world.getBlockState(pos), side)) {
            return targetBlock.getGearPower(world, pos, world.getBlockState(pos), side)
        }
        return 0
    }

    fun getInvertedTargetPower(world: World, blockPos: BlockPos, facing: Direction): Int {
        var total = 0

        for (it in Direction.values()) {
            if(it != facing)
                total+=getTargetPower(world, blockPos.offset(it),it.opposite)
        }
        return total
    }

    fun getPowerForSides(world: World, blockPos: BlockPos, facing: Array<Direction>): Int {
        var total = 0

        for (it in Direction.values()) {
            if(facing.contains(it))
                total+=getTargetPower(world, blockPos.offset(it),it.opposite)
        }
        return total
    }
}