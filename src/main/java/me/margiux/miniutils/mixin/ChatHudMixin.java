package me.margiux.miniutils.mixin;

import me.margiux.miniutils.event.ChatReceiveMessageEvent;
import me.margiux.miniutils.event.EventManager;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V")
    private void onAddMessage(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        ChatReceiveMessageEvent event = new ChatReceiveMessageEvent(message);

        EventManager.fireEvent(event);
    }
}