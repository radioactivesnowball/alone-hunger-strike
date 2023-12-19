package snow.alone.hungerstrike.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import snow.alone.hungerstrike.config.HungerStrikeConfig;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
	// Slice after the Profiler.swap("food") call and select an integer constant with the value of 10
	@ModifyExpressionValue(
		method = "renderStatusBars",
		slice = @Slice(from = @At(
			value = "INVOKE_STRING",
			target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V",
			args = "ldc=food"
		)),
		at = @At(value = "CONSTANT", ordinal = 0, args = "intValue=10")
	)
	public int removeHungerBar(int original) {
		return HungerStrikeConfig.getInstance().disableDisplay ? -1 : original;
	}
}