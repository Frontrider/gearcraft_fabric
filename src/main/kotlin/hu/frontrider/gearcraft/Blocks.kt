package hu.frontrider.gearcraft

import hu.frontrider.gearcraft.blocks.CreativeGearbox
import hu.frontrider.gearcraft.blocks.GearboxBlock
import hu.frontrider.gearcraft.blocks.ShaftBlock
import hu.frontrider.gearcraft.blocks.machine.BlockBreaker
import hu.frontrider.gearcraft.blocks.machine.BlockDismantler
import hu.frontrider.gearcraft.core.BlockBreakingHelper
import net.fabricmc.fabric.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.MaterialColor
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

val creativeGearbox = CreativeGearbox(FabricBlockSettings.of(Material.STONE).setMapColor(MaterialColor.BLACK).setStrength(-1f, -1f).build())
