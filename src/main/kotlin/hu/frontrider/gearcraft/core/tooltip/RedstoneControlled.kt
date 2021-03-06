package hu.frontrider.gearcraft.core.tooltip

import hu.frontrider.gearcraft.core.util.ChatFormat
import hu.frontrider.gearcraft.core.util.formatTranslate
import hu.frontrider.gearcraft.api.traits.ITooltipped

class RedstoneControlled(val unlocalisedMessage:String): ITooltipped {
    override fun setTooltip(tooltip: MutableList<String>) {
        tooltip.add(formatTranslate("gearcraft.redstone_controlled", ChatFormat.DARK_RED))
        tooltip.add(formatTranslate(unlocalisedMessage, ChatFormat.DARK_RED))
    }
}