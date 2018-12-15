package hu.frontrider.gearcraft.core.traits.shaft

import hu.frontrider.gearcraft.api.traits.power.ITransmission
import net.minecraft.block.BlockState
import net.minecraft.state.property.IntegerProperty
import net.minecraft.state.property.Properties.AXIS_XYZ
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class ShaftPower(val power:Int,val powerState:IntegerProperty): ITransmission {

    override fun getGearPower(world: World?, blockPos: BlockPos, blockState: BlockState, side: Direction): Int {
        if(!isSideSupported(world,blockPos,blockState,side) && getGearStrength(world, blockPos, blockState, side)>0)
            return 0
        return power
    }

    override fun getGearStrength(world: World?, blockPos: BlockPos?, blockState: BlockState, side:Direction): Int {
        return blockState.get(powerState)
    }

    override fun isSideSupported(world: World?, blockPos: BlockPos?, blockState: BlockState, side: Direction): Boolean {
        return side.axis == blockState.get(AXIS_XYZ)
    }



}