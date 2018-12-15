package hu.frontrider.gearcraft.core.traits.producer

import hu.frontrider.gearcraft.api.BlockStates
import hu.frontrider.gearcraft.api.traits.ITooltipped
import hu.frontrider.gearcraft.api.traits.power.IGearPowered
import hu.frontrider.gearcraft.core.util.ChatFormat
import hu.frontrider.gearcraft.core.util.formatTranslate
import net.minecraft.block.BlockState
import net.minecraft.state.property.IntegerProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

open class PoweredEngine(val power: Int, val outputSide: Direction, val powerState: IntegerProperty) : IGearPowered, ITooltipped {
    override fun setTooltip(tooltip: MutableList<String>) {
        tooltip.add(formatTranslate("gearcraft.tooltip.requires_pulse", ChatFormat.RED))
    }

    override fun getGearPower(world: World?, blockPos: BlockPos?, blockState: BlockState?, side: Direction?): Int {
        if (getGearStrength(world, blockPos, blockState, side) > 0)
            return power
        return 0
    }

    override fun getGearStrength(world: World?, blockPos: BlockPos?, blockState: BlockState?, side: Direction?): Int {
        val stage = blockState!!.get(BlockStates.POWERED)
        return if (stage) 4 else 0

    }

    override fun isSideSupported(world: World?, blockPos: BlockPos?, blockState: BlockState?, side: Direction?): Boolean {
        return side == outputSide
    }
}

class StagedPoweredEngine(power: Int, outputSide: Direction,powerState: IntegerProperty) : PoweredEngine(power, outputSide,powerState) {
    override fun getGearStrength(world: World?, blockPos: BlockPos?, blockState: BlockState?, side: Direction?): Int {
        val stage = blockState!!.get(powerState)
        return if (stage==1) 4 else 0
    }
}