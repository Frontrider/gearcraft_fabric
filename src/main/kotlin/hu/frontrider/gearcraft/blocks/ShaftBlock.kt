package hu.frontrider.gearcraft.blocks

import hu.frontrider.gearcraft.api.BlockStates
import hu.frontrider.gearcraft.api.traits.power.ITransmission
import hu.frontrider.gearcraft.core.TraitInstances
import hu.frontrider.gearcraft.core.TraitInstances.axisTrait
import hu.frontrider.gearcraft.core.TraitInstances.waterLoggedTrait
import hu.frontrider.gearcraft.core.traits.shaft.ShaftPower
import hu.frontrider.gearcraft.core.traits.shaft.ShaftPowerTrait
import hu.frontrider.gearcraft.core.traits.shaft.ShaftUpdater
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.state.StateFactory
import net.minecraft.state.property.IntegerProperty
import net.minecraft.state.property.Properties
import net.minecraft.state.property.Properties.AXIS_XYZ
import net.minecraft.state.property.Properties.WATERLOGGED
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*

class ShaftBlock(builder: Block.Settings, val power: IntegerProperty) :
        Block(builder),
        ITransmission by ShaftPower(power.values.last(), BlockStates.POWER4) {

    companion object {
        val alignedY = VoxelShapes.cube(0.3125, 0.0, 0.3125, 0.6875, 1.0, 0.6875)
        val alignedX = VoxelShapes.cube(0.0, 0.3125, 0.3125, 1.0, 0.6875, 0.6875)
        val alignedZ = VoxelShapes.cube(0.3125, 0.3125, 0.0, 0.6875, 0.6875, 1.0)
    }

    val updater = ShaftUpdater(power.values.last(), BlockStates.POWER4)

    override fun appendProperties(builder: StateFactory.Builder<Block, BlockState>) {
        super.appendProperties(
                waterLoggedTrait.getState(
                        axisTrait.getState(builder)))
    }

    override fun neighborUpdate(p0: BlockState, p1: World, p2: BlockPos, p3: Block, p4: BlockPos) {
        super.neighborUpdate(p0, p1, p2, p3, p4)
        updater.update(p0, p1, p2)
    }

    override fun onPlaced(world: World, p1: BlockPos, p2: BlockState, p3: LivingEntity?, p4: ItemStack?) {
        super.onPlaced(world, p1, p2, p3, p4)
        updater.update(p2, world, p1)
    }

    override fun isFullBoundsCubeForCulling(var1: BlockState?): Boolean {
        return false
    }

    override fun getBoundingShape(state: BlockState, var2: BlockView?, var3: BlockPos?): VoxelShape {
        val axis = state.get(AXIS_XYZ)
        return when (axis) {
            Direction.Axis.X -> alignedX
            Direction.Axis.Y -> alignedY
            else -> alignedZ
        }
    }

    override fun applyRotation(state: BlockState, rotation: Rotation): BlockState {
        return when (rotation) {
            Rotation.ROT_90, Rotation.ROT_270 -> when (state.get(AXIS_XYZ)) {
                Direction.Axis.X -> state.with(AXIS_XYZ, Direction.Axis.Z)
                Direction.Axis.Z -> state.with(AXIS_XYZ, Direction.Axis.X)
                else -> state
            }
            else -> state
        }
    }


    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        return waterLoggedTrait.getPlacementState(context,
                axisTrait.getPlacementState(context,defaultState))
    }

    override fun randomDisplayTick(stateIn: BlockState, worldIn: World, pos: BlockPos, rand: Random) {
        if (stateIn.get(BlockStates.POWER4) > 0) {
            val d0 = pos.x.toDouble() + 0.5
            val d1 = pos.y.toDouble()
            val d2 = pos.z.toDouble() + 0.5
            var d4 = rand.nextDouble() * 0.6 - 0.2
            if (rand.nextBoolean()) {
                d4 *= -1
            }

            worldIn.addParticle(ParticleTypes.CRIT, d0 + d4, d1 + rand.nextInt(16) / 16, d2 + d4, 0.0, 0.0, 0.0)
            worldIn.addParticle(ParticleTypes.CRIT, d0 + d4, d1 + rand.nextInt(16) / 16, d2 + d4, 0.0, 0.0, 0.0)
            worldIn.addParticle(ParticleTypes.CRIT, d0 + d4, d1 + rand.nextInt(16) / 16, d2 + d4, 0.0, 0.0, 0.0)
        }
    }

}