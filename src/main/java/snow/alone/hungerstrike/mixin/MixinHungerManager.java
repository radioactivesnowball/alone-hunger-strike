package snow.alone.hungerstrike.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class MixinHungerManager {
	@Shadow
	private int foodTickTimer;

	@Inject(method = "update", at = @At("HEAD"), cancellable = true)
	public void removeExhaustion(PlayerEntity player, CallbackInfo ci) {
		final World world = player.getWorld();

		// Reimplement natural generation but make it independent of the player's food level
		final boolean regeneration = world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
		if (regeneration && player.canFoodHeal()) {
			this.foodTickTimer++;
			if (this.foodTickTimer >= 10) {
				player.heal(1.0F);
				this.foodTickTimer = 0;
			}
		}

		ci.cancel();
	}

	@ModifyExpressionValue(
		method = "readNbt",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;contains(Ljava/lang/String;I)Z")
	)
	public boolean preventLoadingNbt(boolean original) {
		return false;
	}
}