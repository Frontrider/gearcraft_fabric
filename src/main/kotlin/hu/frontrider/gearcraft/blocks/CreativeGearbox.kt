package hu.frontrider.gearcraft.blocks

import hu.frontrider.gearcraft.api.traits.ITooltipped
import hu.frontrider.gearcraft.api.traits.power.IGearPowered
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class CreativeGearbox(builder: Settings) :Block(builder), IGearPowered, ITooltipped {
    override fun getGearPower(world: World?, blockPos: BlockPos?, blockState: BlockState?, side: Direction?): Int {
        //the closest we can get to infinity
        return Integer.MAX_VALUE
    }

    override fun getGearStrength(world: World?, blockPos: BlockPos?, blockState: BlockState?, side:Direction?): Int {
        //the closest we can get to infinity
        return Integer.MAX_VALUE
    }

    override fun isSideSupported(world: World?, blockPos: BlockPos?, blockState: BlockState?, side: Direction?): Boolean {
        return true
    }

    init {

    }

    override fun setTooltip(tooltip: MutableList<String>) {
    }


}
