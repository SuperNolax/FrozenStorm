package net.frozenblock.frozenstorm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.api.ModInitializer;
import net.frozenblock.frozenstorm.registry.RegisterBlocks;
import net.frozenblock.frozenstorm.registry.RegisterEntities;
import net.frozenblock.frozenstorm.registry.RegisterItems;
import net.minecraft.block.SkullBlock;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrozenStorm implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("frozenstorm");
	public static final String MOD_ID = "frozenstorm";

	public static final SkullBlock.Type FROZEN_SKULL_TYPE = ClassTinkerers.getEnum(SkullBlock.Type.class, "FROZEN_STORM");

	@Override
	public void onInitialize() {
		LOGGER.info("Frozen Storm is initializing");
		RegisterEntities.init();
		RegisterItems.init();
		RegisterBlocks.init();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
