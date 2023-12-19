package snow.alone.hungerstrike;

import net.fabricmc.api.ModInitializer;
import snow.alone.hungerstrike.config.HungerStrikeConfig;

public final class HungerStrike implements ModInitializer {
	public static final String ID = "alone-hunger-strike";

	@Override
	public void onInitialize() {
		HungerStrikeConfig.HANDLER.load();
	}
}
