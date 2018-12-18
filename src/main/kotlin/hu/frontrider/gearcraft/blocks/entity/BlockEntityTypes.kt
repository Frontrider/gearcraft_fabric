package hu.frontrider.gearcraft.blocks.entity

import com.mojang.datafixers.DataFixUtils
import com.mojang.datafixers.types.Type
import hu.frontrider.gearcraft.Gearcraft.MODID
import net.minecraft.SharedConstants
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.datafixers.Schemas
import net.minecraft.datafixers.TypeReferences
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager

object BlockEntityTypes {
    private val LOGGER = LogManager.getLogger()

    val ShaftEntiy = create("$MODID:shaft", BlockEntityType.Builder.create {ShaftBlockEntity()})

    fun <T :BlockEntity> create(var0:String, var1: BlockEntityType.Builder<T>): BlockEntityType<*> {
        var dataFixer: Type<*>? = null

        try {
            dataFixer = Schemas.getFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().worldVersion)).getChoiceType(TypeReferences.BLOCK_ENTITY, var0)
        } catch (var4: IllegalStateException) {
            if (SharedConstants.isDevelopment) {
                throw var4
            }

            LOGGER.warn("No data fixer registered for block entity {}", var0)
        }

        return Registry.register<BlockEntityType<T>>(Registry.BLOCK_ENTITY, var0, var1.method_11034(dataFixer)) as BlockEntityType<*>
    }


}