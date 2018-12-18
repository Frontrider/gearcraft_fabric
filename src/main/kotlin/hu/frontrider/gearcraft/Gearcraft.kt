package hu.frontrider.gearcraft

import hu.frontrider.gearcraft.blocks.GearboxBlock
import hu.frontrider.gearcraft.blocks.ShaftBlock
import hu.frontrider.gearcraft.blocks.machine.BlockBreaker
import hu.frontrider.gearcraft.blocks.machine.BlockDismantler
import hu.frontrider.gearcraft.recipe.GrindingRecipe
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.block.BlockItem
import net.minecraft.recipe.RecipeSerializers
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object Gearcraft : ModInitializer {
    val MODID = "gearcraft"
    override fun onInitialize() {

        registerItemBlock(creativeGearbox, "creative_gearbox")

        arrayOf("birch", "spruce", "oak", "dark_oak", "jungle").forEach {
            registerItemBlock(ShaftBlock(FabricBlockSettings.of(Material.WOOD).build(), 4), "${it}_shaft")
            registerItemBlock(GearboxBlock(FabricBlockSettings.of(Material.WOOD).build(), 4), "${it}_gearbox")
            registerItemBlock(BlockBreaker(FabricBlockSettings.of(Material.WOOD).build(), 2), "${it}_breaker")
            registerItemBlock(BlockDismantler(FabricBlockSettings.of(Material.WOOD).build(), 2), "${it}_dismantler")
        }
        arrayOf("stone", "andesite", "diorite", "granite").forEach {
            registerItemBlock(ShaftBlock(FabricBlockSettings.of(Material.STONE).build(), 8), "${it}_shaft")
            registerItemBlock(GearboxBlock(FabricBlockSettings.of(Material.STONE).build(), 8), "${it}_gearbox")
            registerItemBlock(BlockBreaker(FabricBlockSettings.of(Material.STONE).build(), 4), "${it}_breaker")
            registerItemBlock(BlockDismantler(FabricBlockSettings.of(Material.STONE).build(), 4), "${it}_dismantler")
        }

        arrayOf("black","blue","brown","cyan","gray","green","light_blue","light_gray","lime","magenta","red","orange","pink","white","yellow").forEach {
            registerItemBlock(ShaftBlock(FabricBlockSettings.of(Material.METAL).build(), 16), "${it}_iron_shaft")
            registerItemBlock(GearboxBlock(FabricBlockSettings.of(Material.METAL).build(), 16), "${it}_iron_gearbox")
            registerItemBlock(BlockBreaker(FabricBlockSettings.of(Material.METAL).build(), 6), "${it}_iron_breaker")
            registerItemBlock(BlockDismantler(FabricBlockSettings.of(Material.METAL).build(), 6), "${it}_iron_dismantler")
        }

        Registry.ITEM.register(Identifier(MODID, "wooden_gear"), Item(Item.Settings().itemGroup(ItemGroup.MATERIALS)))
        Registry.ITEM.register(Identifier(MODID, "stone_gear"), Item(Item.Settings().itemGroup(ItemGroup.MATERIALS)))
        Registry.ITEM.register(Identifier(MODID, "iron_gear"), Item(Item.Settings().itemGroup(ItemGroup.MATERIALS)))
        Registry.ITEM.register(Identifier(MODID, "gold_gear"), Item(Item.Settings().itemGroup(ItemGroup.MATERIALS)))
        Registry.ITEM.register(Identifier(MODID, "diamond_gear"), Item(Item.Settings().itemGroup(ItemGroup.MATERIALS)))
        Registry.ITEM.register(Identifier(MODID, "obsidian_gear"), Item(Item.Settings().itemGroup(ItemGroup.MATERIALS)))

        Registry.ITEM.register(Identifier(MODID, "stone_dust"), Item(Item.Settings().itemGroup(ItemGroup.MATERIALS)))

        RecipeSerializers.register(GrindingRecipe.Serialiser())
    }

    fun registerItemBlock(block: Block, name: String) {
        Registry.BLOCK.register(Identifier(MODID, name), block)
        Registry.ITEM.register(
                Identifier(MODID, name),
                BlockItem(block,
                        Item.Settings().itemGroup(ItemGroup.DECORATIONS))
        )
    }
}