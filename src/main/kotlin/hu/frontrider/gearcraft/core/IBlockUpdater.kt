package hu.frontrider.gearcraft.core

import net.minecraft.block.BlockState

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

interface IBlockUpdater {

   fun updateBlockState(world: World, left: BlockPos, right: BlockPos, thizState: BlockState, thizPos: BlockPos, leftSide: Direction, rightSide: Direction)
}