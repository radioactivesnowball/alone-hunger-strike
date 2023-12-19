package snow.alone.hungerstrike.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import snow.alone.hungerstrike.config.HungerStrikeConfig;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
	@Shadow public abstract boolean canFoodHeal();

	protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Redirect(
		method = "eatFood",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/HungerManager;eat(Lnet/minecraft/item/Item;Lnet/minecraft/item/ItemStack;)V"
		)
	)
	public void giveHealthForFood(HungerManager instance, Item item, ItemStack stack) {
		final HungerStrikeConfig config = HungerStrikeConfig.getInstance();
		if (!config.disableFoodRegen && item.isFood()) {
			//noinspection DataFlowIssue
			this.heal(item.getFoodComponent().getHunger() * config.foodValueMultiplier);
		}
	}

	@Redirect(
		method = "canConsume",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;isNotFull()Z")
	)
	public boolean makeFoodConsumable(HungerManager instance) {
		return HungerStrikeConfig.getInstance().alwaysAllowConsumption || canFoodHeal();
	}
}