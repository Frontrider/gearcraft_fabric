package hu.frontrider.gearcraft.core

import hu.frontrider.gearcraft.core.traits.AxisTrait
import hu.frontrider.gearcraft.core.traits.FacingBlock
import hu.frontrider.gearcraft.core.traits.InvertedBlock
import hu.frontrider.gearcraft.core.traits.WaterLoggedTrait

object TraitInstances {

    val invertedState = InvertedBlock()
    val facingState = FacingBlock()
    val axisTrait = AxisTrait()
    val waterLoggedTrait = WaterLoggedTrait()
}