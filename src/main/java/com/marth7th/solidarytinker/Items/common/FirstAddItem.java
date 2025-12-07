package com.marth7th.solidarytinker.Items.common;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FirstAddItem extends Item {

    public FirstAddItem(Properties pProperties) {
        super(pProperties.durability(3250));
    }
    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 144000;
    }
    private boolean needHeal(Player player){
        return player.getHealth()<player.getMaxHealth();
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if(needHeal(pPlayer)){
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
        else {
            pPlayer.displayClientMessage(Component.translatable("tooltip.firstadd.fullhealth").withStyle(style -> style.withColor(0xffaaff)),true);
            return InteractionResultHolder.fail(itemstack);
        }
    }
    @Override
    public void onUsingTick(ItemStack stack, LivingEntity living, int count) {
        if (living instanceof Player player) {
            int useTime = getUseDuration(stack) - count;
            int durabilityCost=  Math.min(Math.round(player.getMaxHealth() * 0.1f),Math.round(player.getMaxHealth()-player.getHealth())) ;
            if (useTime > 60) {
                int activeUseTime = useTime - 60;
                if (activeUseTime % 20 == 0) {
                    if (stack.getDamageValue() < stack.getMaxDamage() - 1) {
                        boolean shouldStop=!needHeal(player)&&player.getAbsorptionAmount()>=player.getMaxHealth() * 0.1f;
                        if(shouldStop){
                            player.stopUsingItem();
                            return;
                        }
                        player.heal(player.getMaxHealth() * 0.1f);
                        player.level.playSound(null,player.getOnPos(), SoundEvents.CAT_EAT, SoundSource.AMBIENT,1,2);
                        stack.hurtAndBreak(durabilityCost, player, (player1) -> player1.broadcastBreakEvent(player.getUsedItemHand()));
                        if(!needHeal(player)&&player.getAbsorptionAmount()<player.getMaxHealth() * 0.1f){
                            player.setAbsorptionAmount(player.getMaxHealth() * 0.1f);
                        }
                    } else {
                        player.stopUsingItem();
                    }
                }
            }
        }
    }
    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        return pStack;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return pStack.getDamageValue() < pStack.getMaxDamage();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("长按3秒后启动,启动期间持续恢复血量直到满为止,可以溢出10%最大生命作为伤害吸收").withStyle(style -> style.withColor(0x97f2ff)));
        pTooltipComponents.add(Component.literal("骗你的,其实它不回体力").withStyle(style -> style.withColor(0xa6ff6a)));
        pTooltipComponents.add(Component.literal("当前耐久" + (this.getMaxDamage(pStack)-this.getDamage(pStack)) + "/" + this.getMaxDamage(pStack)).withStyle(style -> style.withColor(0xff9166)));
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }
}
