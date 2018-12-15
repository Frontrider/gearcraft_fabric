package hu.frontrider.gearcraft.api;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntegerProperty;

public class BlockStates {

    public static final BooleanProperty INVERTED = BooleanProperty.create("inverted");
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final IntegerProperty POWER4 = IntegerProperty.create("power",0,3);
}
