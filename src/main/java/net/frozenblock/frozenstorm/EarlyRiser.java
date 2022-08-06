package net.frozenblock.frozenstorm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String skullType = remapper.mapClassName("intermediary", "net.minecraft.class_2484$class_2486");
        ClassTinkerers.enumBuilder(skullType).addEnum("FROZEN_STORM").build();
    }
}
