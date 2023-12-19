package snow.alone.hungerstrike.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snow.alone.hungerstrike.HungerStrike;
import snow.alone.hungerstrike.config.HungerStrikeConfig;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
	@Shadow
	public abstract boolean hasStatusEffect(StatusEffect effect);

	@Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
	public void disableSprinting(boolean sprinting, CallbackInfo ci) {
		final HungerStrikeConfig config = HungerStrikeConfig.getInstance();
		if (sprinting && (config.disableSprinting || disableSprintingOnHunger(config))) {
			ci.cancel();
		}
	}

	@Unique
	private boolean disableSprintingOnHunger(HungerStrikeConfig config) {
		return config.disableSprintingOnHunger && hasStatusEffect(StatusEffects.HUNGER);
	}
}