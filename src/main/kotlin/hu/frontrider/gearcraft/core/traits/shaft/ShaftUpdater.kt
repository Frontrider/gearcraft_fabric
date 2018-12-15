package hu.frontrider.gearcraft.core.traits.shaft

import hu.frontrider.gearcraft.api.BlockStates
import hu.frontrider.gearcraft.api.traits.power.IGearPowered
import hu.frontrider.gearcraft.core.IBlockUpdater
import hu.frontrider.gearcraft.core.util.cap
import net.minecraft.block.BlockState
import net.minecraft.state.property.IntegerProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class ShaftUpdater(val power:Int,val powerState:IntegerProperty): IBlockUpdater {


   override fun updateBlockState(world: World, left: BlockPos, right: BlockPos,
                                  thizState: BlockState, thizPos: BlockPos, leftSide: Direction, rightSide: Direction) {
        val leftBlock = world.getBlockState(left)
        val rightBlock = world.getBlockState(right)

        val poweredLeft = leftBlock.block
        val poweredRight = rightBlock.block

        var rightPower = -1
        var leftPower = -1
        var leftStrength = 0
        var rightStrength = 0

        if (poweredLeft is IGearPowered) {
            leftPower = (poweredLeft as IGearPowered).getGearPower(world, left, world.getBlockState(left), leftSide).cap(power)

            leftStrength = (poweredLeft as IGearPowered).getGearStrength(world, left, world.getBlockState(left), leftSide).cap(4)
        }
        if (poweredRight is IGearPowered) {
            rightPower = (poweredRight as IGearPowered).getGearPower(world, right, world.getBlockState(right), rightSide).cap(power)
            rightStrength = (poweredRight as IGearPowered).getGearStrength(world, right, world.getBlockState(right), rightSide).cap(4)
        }
        val thizPower = thizState.get(powerState)

        if (leftPower <= 0 && rightPower <= 0) {
            world.setBlockState(thizPos, thizState.with(powerState, 0))
            return
        }

        var newPower = 0;
        if (leftPower >= rightPower && leftPower >= power && leftStrength > 0) {
            if (thizPower == leftStrength - 1)
                return
            newPower = leftStrength - 1
        }
        if (leftPower <= rightPower && rightPower >= power && rightStrength > 0) {
            if (thizPower == rightStrength - 1)
                return
            newPower = rightStrength - 1

        }

        if (newPower != power) {
            world.setBlockState(thizPos, thizState.with(powerState, newPower))

            world.blockTickScheduler.schedule(left,poweredLeft, 10)
            world.blockTickScheduler.schedule(right, poweredRight, 10)
        }
    }

    fun update(blockState: BlockState, world: World, blockPos: BlockPos) {
        val axis = blockState.get(Properties.AXIS_XYZ)!!

        when (axis) {
            Direction.Axis.Y -> {
                updateBlockState(world, blockPos.up(), blockPos.down(), blockState, blockPos, Direction.DOWN, Direction.UP)
            }
            Direction.Axis.Z -> {
                updateBlockState(world, blockPos.north(), blockPos.south(), blockState, blockPos, Direction.SOUTH, Direction.NORTH)
            }
            Direction.Axis.X -> {
                updateBlockState(world, blockPos.east(), blockPos.west(), blockState, blockPos, Direction.WEST, Direction.EAST)
            }
        }
    }
}