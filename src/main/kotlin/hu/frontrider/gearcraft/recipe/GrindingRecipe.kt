package hu.frontrider.gearcraft.recipe

import com.google.gson.JsonObject
import hu.frontrider.gearcraft.Gearcraft
import hu.frontrider.gearcraft.api.recipes.IDismantlerRecipe
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.*
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

class GrindingRecipe(private val source: Ingredient,private val result: ItemStack) : Recipe,IDismantlerRecipe {

    override fun getPreviewInputs(): DefaultedList<Ingredient> {
        val inputs = super.getPreviewInputs()
        inputs.add(source)
        return inputs
    }

    override fun craft(var1: Inventory): ItemStack {
        val invStack = var1.getInvStack(0)
        if (invStack.amount == 0) return ItemStack.EMPTY

        invStack.addAmount(-1)
        return result.copy()
    }

    override fun getId(): Identifier {
        return Identifier(Gearcraft.MODID, "simple_grinding")

    }

    override fun fits(var1: Int, var2: Int): Boolean {
        return true
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return Serialiser()
    }

    override fun getOutput(): ItemStack {
        return result
    }

    override fun matches(inventory: Inventory, var2: World?): Boolean {
        if (inventory.invSize != 1) return false

        return source.matches(inventory.getInvStack(0))
    }

    class Serialiser() : RecipeSerializer<GrindingRecipe> {
        override fun getId(): String {
            return Gearcraft.MODID + ":simple_grinding"
        }

        override fun write(packet: PacketByteBuf, recipe: GrindingRecipe) {
            recipe.source.write(packet)
            packet.writeItemStack(recipe.result)
        }

        override fun read(var1: Identifier, json: JsonObject): GrindingRecipe {
            json.apply {

                val inputIngredient =if (JsonHelper.isArray(json, "input")) {
                    Ingredient.fromJson(JsonHelper.getArray(json, "input"))
                } else {
                    Ingredient.fromJson(JsonHelper.getObject(json, "input"))
                }

                val result = this.get("result")
                val identifier = Identifier.create(result.asString)
                if (Registry.ITEM.contains(identifier)) {
                    val item = Registry.ITEM[identifier]
                    return GrindingRecipe(inputIngredient, ItemStack(item))
                }
                throw InvalidIdentifierException(result.asString)
            }
        }

        override fun read(var1: Identifier?, packet: PacketByteBuf): GrindingRecipe {
            val ingredient = Ingredient.fromPacket(packet)
            val result = packet.readItemStack()

            return GrindingRecipe(ingredient, result)
        }
    }
}