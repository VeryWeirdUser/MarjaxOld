package me.margiux.miniutils.mixin;

import me.margiux.miniutils.access.IChatHud;
import me.margiux.miniutils.event.ChatReceiveMessageEvent;
import me.margiux.miniutils.event.EventManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin implements IChatHud {
    @Shadow public abstract int getWidth();

    @Shadow public abstract double getChatScale();

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract boolean isChatFocused();

    @Shadow private int scrolledLines;

    @Shadow private boolean hasUnreadNewMessages;

    @Shadow public abstract void scroll(int scroll);

    @Shadow @Final private List<ChatHudLine.Visible> visibleMessages;

    @Shadow @Final private List<ChatHudLine> messages;

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V")
    private void onAddMessage(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        ChatReceiveMessageEvent event = new ChatReceiveMessageEvent(message);

        EventManager.fireEvent(event);
    }

    @Override
    public void addHiddenMessage(String message) {
        addHiddenMessage(Text.of(message));
    }

    @Override
    public void addHiddenMessage(Text message) {
        int ticks = this.client.inGameHud.getTicks();
        boolean refresh = false;
        int i = MathHelper.floor((double)this.getWidth() / this.getChatScale());

        List<OrderedText> list = ChatMessages.breakRenderedChatMessageLines(message, i, this.client.textRenderer);
        boolean bl = this.isChatFocused();

        for(int j = 0; j < list.size(); ++j) {
            OrderedText orderedText = (OrderedText)list.get(j);
            if (bl && this.scrolledLines > 0) {
                this.hasUnreadNewMessages = true;
                this.scroll(1);
            }

            boolean bl2 = j == list.size() - 1;
            this.visibleMessages.add(0, new ChatHudLine.Visible(ticks, orderedText, null, bl2));
        }

        while(this.visibleMessages.size() > 100) {
            this.visibleMessages.remove(this.visibleMessages.size() - 1);
        }

        if (!refresh) {
            this.messages.add(0, new ChatHudLine(ticks, message, null, null));

            while(this.messages.size() > 100) {
                this.messages.remove(this.messages.size() - 1);
            }
        }
    }
}