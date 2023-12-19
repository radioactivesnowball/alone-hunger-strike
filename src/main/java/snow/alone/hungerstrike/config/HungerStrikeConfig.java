package snow.alone.hungerstrike.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import snow.alone.hungerstrike.HungerStrike;

public final class HungerStrikeConfig {
	public static final String SPRINTING = "sprinting";
	public static final String REGEN = "regen";
	public static final String FOOD = "food";
	public static final String MISC = "misc";

	public static ConfigClassHandler<HungerStrikeConfig> HANDLER = ConfigClassHandler
		.createBuilder(HungerStrikeConfig.class)
		.id(new Identifier(HungerStrike.ID, "config"))
		.serializer(config -> GsonConfigSerializerBuilder.create(config)
			.setPath(FabricLoader.getInstance().getConfigDir().resolve("alone-hunger-strike.json"))
			.build()
		)
		.build();

	@SerialEntry
	@AutoGen(category = SPRINTING)
	@CustomDescription("Should sprinting be disabled?")
	@Boolean
	public boolean disableSprinting = false;

	@SerialEntry
	@AutoGen(category = SPRINTING)
	@CustomDescription("Should sprinting be disabled when the player has the hunger status effect?")
	@Boolean
	public boolean disableSprintingOnHunger = true;

	@SerialEntry
	@AutoGen(category = REGEN)
	@CustomDescription("How many ticks per half heart natural regeneration.")
	@IntField
	public int regenTickRate = 10;

	@SerialEntry
	@AutoGen(category = FOOD)
	@CustomDescription("Should food regeneration be disabled?")
	@Boolean
	public boolean disableFoodRegen = false;

	@SerialEntry
	@AutoGen(category = FOOD)
	@CustomDescription("The multiplier applied to a food's value when consumed. Default: 0.5x = 1/2")
	@FloatField
	public float foodValueMultiplier = 0.5F;

	@SerialEntry
	@AutoGen(category = FOOD)
	@CustomDescription("Allow food consumption even if the player's health bar is full?")
	@Boolean
	public boolean alwaysAllowConsumption = false;

	@SerialEntry
	@AutoGen(category = MISC)
	@CustomDescription("Should the hunger bar display be disabled?")
	@Boolean
	public boolean disableDisplay = true;

	public static HungerStrikeConfig getInstance() {
		return HANDLER.instance();
	}
}