package io.github.how_bout_no.outvoted.mixin;

import io.github.how_bout_no.outvoted.item.WildfireHelmetItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MendingEnchantment.class)
public class MixinMendingEnchantment extends Enchantment {
    protected MixinMendingEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, EnchantmentTarget.BREAKABLE, slotTypes);
    }

    public boolean isAcceptableItem(ItemStack stack) {
        System.out.println(stack.getItem());
        return !(stack.getItem() instanceof WildfireHelmetItem) && this.type.isAcceptableItem(stack.getItem());
    }
}