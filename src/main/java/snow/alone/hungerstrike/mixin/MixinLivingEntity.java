package snow.alone.hungerstrike.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
	@Shadow
	public abstract boolean hasStatusEffect(StatusEffect effect);

	@Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
	public void disableSprintingIfHunger(boolean sprinting, CallbackInfo ci) {
		if (sprinting && hasStatusEffect(StatusEffects.HUNGER)) {
			ci.cancel();
		}
	}
}