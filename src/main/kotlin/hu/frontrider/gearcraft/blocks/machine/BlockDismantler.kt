package hu.frontrider.gearcraft.blocks.machine

import hu.frontrider.gearcraft.api.recipes.IDismantlerRecipe
import hu.frontrider.gearcraft.core.util.CraftingInventory
import net.minecraft.block.BlockState
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties.FACING
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class BlockDismantler(builder: Settings, power: Int) :
        BlockBreaker(builder, power) {

    //changed the tick method, so now it will do the recipes recipe, instead of breaking it normally.
    override fun scheduledTick(state: BlockState, world: World, pos: BlockPos, rand: Random?) {
        if (world.isRemote)
            return
        val value = state.get(FACING)
        val targetPower = powerConsumer.getInvertedTargetPower(world, pos, value.opposite)

        if (world.isAir(pos.offset(value.opposite)))
            return

        if (targetPower >= power) {
            val offset = pos.offset(value.opposite)
            val targetState = world.getBlockState(offset)

            val itemStack = ItemStack(targetState.block.item)
            val craftingInventory = CraftingInventory()
            craftingInventory.setInvStack(0,itemStack)
            CraftingInventory(1)

            val recipe = world.recipeManager.values().firstOrNull {
                if (it is IDismantlerRecipe) {
                    return@firstOrNull it.matches(craftingInventory,world)
                }
                return@firstOrNull false
            }?:return

            val result = recipe.craft(craftingInventory)

            world.breakBlock(offset, false)
            world.spawnEntity(ItemEntity(world,pos.x+.5,pos.y+.5,pos.z+.5,result))
        }
    }
}