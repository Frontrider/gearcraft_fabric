package hu.frontrider.gearcraft.core.traits

import net.minecraft.util.math.Direction

class SideManager {

    fun getSidesForTop(facing: Direction): List<Direction> {
        val ordinal = facing.ordinal
        return Direction.values().filter {
            it.ordinal != ordinal
        }

    }
}