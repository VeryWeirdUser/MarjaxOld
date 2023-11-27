package me.margiux.miniutils.gui.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.margiux.miniutils.module.setting.*;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Field extends Widget {
    public final static Predicate<String> INT_PREDICATE = (e) -> {
        if (Objects.equals(e, "")) return true;
        try {
            Integer.valueOf(e);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    };
    public final static Predicate<String> LONG_PREDICATE = (e) -> {
        if (Objects.equals(e, "")) return true;
        try {
            Long.valueOf(e);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    };
    public final static Predicate<String> FLOAT_PREDICATE = (e) -> {
        if (Objects.equals(e, "")) return true;
        try {
            Float.valueOf(e);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    };
    public final static Predicate<String> DOUBLE_PREDICATE = (e) -> {
        if (Objects.equals(e, "")) return true;
        try {
            Double.valueOf(e);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    };
    public final static Predicate<String> STRING_PREDICATE = Objects::nonNull;
    public FieldSetting<?> setting;
    private TextRenderer textRenderer;
    private int maxLength = 32;
    private final boolean drawsBackground = true;
    private boolean selecting;
    private int firstCharacterIndex;
    private int selectionStart;
    private int selectionEnd;
    private Predicate<String> textPredicate = STRING_PREDICATE;
    private final BiFunction<String, Integer, OrderedText> renderTextProvider = (string, firstCharacterIndex) -> OrderedText.styledForwardsVisitedString(string, Style.EMPTY);

    public void setText(String text) {
        if (!this.textPredicate.test(text)) {
            return;
        }
        setting.parseAndSetValue(text.length() > this.maxLength ? text.substring(0, this.maxLength) : text);
        this.setCursorToEnd();
        this.setSelectionEnd(this.selectionStart);
    }

    public String getText() {
        return setting.getStringValue();
    }

    public String getSelectedText() {
        int i = Math.min(this.selectionStart, this.selectionEnd);
        int j = Math.max(this.selectionStart, this.selectionEnd);
        return this.setting.getStringValue().substring(i, j);
    }

    public void write(String text) {
        String string2;
        String string;
        int l;
        int i = Math.min(this.selectionStart, this.selectionEnd);
        int j = Math.max(this.selectionStart, this.selectionEnd);
        int k = this.maxLength - this.setting.getStringValue().length() - (i - j);
        if (k < (l = (string = SharedConstants.stripInvalidChars(text)).length())) {
            string = string.substring(0, k);
            l = k;
        }
       if (!this.textPredicate.test(string2 = new StringBuilder(this.setting.getStringValue()).replace(i, j, string).toString())) {
            return;
        }
        this.setting.parseAndSetValue(string2);
        this.setSelectionStart(i + l);
        this.setSelectionEnd(this.selectionStart);
    }

    private void erase(int offset) {
        if (Screen.hasControlDown()) {
            this.eraseWords(offset);
        } else {
            this.eraseCharacters(offset);
        }
    }

    public void eraseWords(int wordOffset) {
        if (this.setting.getStringValue().isEmpty()) {
            return;
        }
        if (this.selectionEnd != this.selectionStart) {
            this.write("");
            return;
        }
        this.eraseCharacters(this.getWordSkipPosition(wordOffset) - this.selectionStart);
    }

    public void eraseCharacters(int characterOffset) {
        int k;
        if (this.setting.getStringValue().isEmpty()) {
            return;
        }
        if (this.selectionEnd != this.selectionStart) {
            this.write("");
            return;
        }
        int i = this.getCursorPosWithOffset(characterOffset);
        int j = Math.min(i, this.selectionStart);
        if (j == (k = Math.max(i, this.selectionStart))) {
            return;
        }
        String string = new StringBuilder(this.setting.getStringValue()).delete(j, k).toString();
        if (!this.textPredicate.test(string)) {
            return;
        }
        this.setting.parseAndSetValue(string);
        this.setCursor(j);
    }

    public int getWordSkipPosition(int wordOffset) {
        return this.getWordSkipPosition(wordOffset, this.getCursor());
    }

    private int getWordSkipPosition(int wordOffset, int cursorPosition) {
        return this.getWordSkipPosition(wordOffset, cursorPosition, true);
    }

    private int getWordSkipPosition(int wordOffset, int cursorPosition, boolean skipOverSpaces) {
        int i = cursorPosition;
        boolean bl = wordOffset < 0;
        int j = Math.abs(wordOffset);
        for (int k = 0; k < j; ++k) {
            if (bl) {
                while (skipOverSpaces && i > 0 && this.setting.getStringValue().charAt(i - 1) == ' ') {
                    --i;
                }
                while (i > 0 && this.setting.getStringValue().charAt(i - 1) != ' ') {
                    --i;
                }
                continue;
            }
            int l = this.setting.getStringValue().length();
            if ((i = this.setting.getStringValue().indexOf(32, i)) == -1) {
                i = l;
                continue;
            }
            while (skipOverSpaces && i < l && this.setting.getStringValue().charAt(i) == ' ') {
                ++i;
            }
        }
        return i;
    }

    public void moveCursor(int offset) {
        this.setCursor(this.getCursorPosWithOffset(offset));
    }

    private int getCursorPosWithOffset(int offset) {
        return Util.moveCursor(this.setting.getStringValue(), this.selectionStart, offset);
    }

    public void setCursor(int cursor) {
        this.setSelectionStart(cursor);
        if (!this.selecting) {
            this.setSelectionEnd(this.selectionStart);
        }
    }

    public void setSelectionStart(int cursor) {
        this.selectionStart = MathHelper.clamp(cursor, 0, this.setting.getStringValue().length());
    }

    public void setCursorToStart() {
        this.setCursor(0);
    }

    public void setCursorToEnd() {
        this.setCursor(this.setting.getStringValue().length());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.isActive()) {
            return false;
        }
        this.selecting = Screen.hasShiftDown();
        if (Screen.isSelectAll(keyCode)) {
            this.setCursorToEnd();
            this.setSelectionEnd(0);
            return true;
        }
        if (Screen.isCopy(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            return true;
        }
        if (Screen.isPaste(keyCode)) {
            this.write(MinecraftClient.getInstance().keyboard.getClipboard());
            return true;
        }
        if (Screen.isCut(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            this.write("");

            return true;
        }
        return switch (keyCode) {
            case 263 -> {
                if (Screen.hasControlDown()) {
                    this.setCursor(this.getWordSkipPosition(-1));
                } else {
                    this.moveCursor(-1);
                }
                yield true;
            }
            case 262 -> {
                if (Screen.hasControlDown()) {
                    this.setCursor(this.getWordSkipPosition(1));
                } else {
                    this.moveCursor(1);
                }
                yield true;
            }
            case 259 -> {
                this.selecting = false;
                this.erase(-1);
                this.selecting = Screen.hasShiftDown();
                yield true;
            }
            case 261 -> {
                this.selecting = false;
                this.erase(1);
                this.selecting = Screen.hasShiftDown();
                yield true;
            }
            case 268 -> {
                this.setCursorToStart();
                yield true;
            }
            case 269 -> {
                this.setCursorToEnd();
                yield true;
            }
            default -> false;
        };
    }

    public boolean isActive() {
        return this.isVisible() && this.isFocused();
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (!this.isActive()) {
            return false;
        }
        if (SharedConstants.isValidChar(chr)) {
            this.write(Character.toString(chr));

            return true;
        }
        return false;
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        this.textRenderer = MinecraftClient.getInstance().textRenderer;
        boolean bl;
        if (!this.isVisible()) {
            return;
        }
        bl = mouseX >= (double) this.x && mouseX < (double) (this.x + this.width) && mouseY >= (double) this.y && mouseY < (double) (this.y + this.height);
        this.setTextFieldFocused(bl);
        if (this.isFocused() && bl && button == 0) {
            int i = MathHelper.floor(mouseX) - this.x;
            if (this.drawsBackground) {
                i -= 4;
            }
            String string = this.textRenderer.trimToWidth(this.setting.getStringValue().substring(this.firstCharacterIndex), this.getInnerWidth());
            this.setCursor(this.textRenderer.trimToWidth(string, i).length() + this.firstCharacterIndex);
        }
    }

    public void setTextFieldFocused(boolean focused) {
        this.setFocused(focused);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.textRenderer = MinecraftClient.getInstance().textRenderer;
        int i;
        if (!this.isVisible()) {
            return;
        }
        if (this.drawsBackground()) {
            i = this.isFocused() ? 0xFF00CCFF : 0xFF000033;
            TextFieldWidget.fill(matrices, this.x, this.y, this.x + this.width, this.y + this.height, i);
            TextFieldWidget.fill(matrices, this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, 0xFF00004d);
        }
        int j = this.selectionStart - this.firstCharacterIndex;
        int k = this.selectionEnd - this.firstCharacterIndex;
        String string = this.textRenderer.trimToWidth(this.setting.getStringValue().substring(this.firstCharacterIndex), this.getInnerWidth());
        boolean bl = j >= 0 && j <= string.length();
        int l = this.drawsBackground ? this.x + 4 : this.x;
        int m = this.drawsBackground ? this.y + (this.height - 8) / 2 : this.y;
        int n = l;
        if (k > string.length()) {
            k = string.length();
        }
        int editableColor = 0xE0E0E0;
        if (!string.isEmpty()) {
            String string2 = bl ? string.substring(0, j) : string;
            n = this.textRenderer.drawWithShadow(matrices, this.renderTextProvider.apply(string2, this.firstCharacterIndex), (float) n, (float) m, editableColor);
        }
        boolean bl3 = this.selectionStart < this.setting.getStringValue().length() || this.setting.getStringValue().length() >= this.getMaxLength();
        int o = n;
        if (!bl) {
            o = j > 0 ? l + this.width : l;
        } else if (bl3) {
            --o;
            --n;
        }
        if (!string.isEmpty() && bl && j < string.length()) {
            this.textRenderer.drawWithShadow(matrices, this.renderTextProvider.apply(string.substring(j), this.selectionStart), (float) n, (float) m, editableColor);
        }
        if (isFocused()) {
            if (bl3) {
                DrawableHelper.fill(matrices, o, m - 1, o + 1, m + 1 + this.textRenderer.fontHeight, -3092272);
            } else {
                this.textRenderer.drawWithShadow(matrices, "_", (float) o, (float) m, editableColor);
            }
        }
        if (k != j) {
            int p = l + this.textRenderer.getWidth(string.substring(0, k));
            this.drawSelectionHighlight(o, m - 1, p - 1, m + 1 + this.textRenderer.fontHeight);
        }
    }

    private void drawSelectionHighlight(int x1, int y1, int x2, int y2) {
        int i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }
        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }
        if (x2 > this.x + this.width) {
            x2 = this.x + this.width;
        }
        if (x1 > this.x + this.width) {
            x1 = this.x + this.width;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(0.0f, 0.0f, 1.0f, 1.0f);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        bufferBuilder.vertex(x1, y2, 0.0).next();
        bufferBuilder.vertex(x2, y2, 0.0).next();
        bufferBuilder.vertex(x2, y1, 0.0).next();
        bufferBuilder.vertex(x1, y1, 0.0).next();
        tessellator.draw();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        if (this.setting.getStringValue().length() > maxLength) {
            this.setting.parseAndSetValue(this.setting.getStringValue().substring(0, maxLength));
        }
    }

    private int getMaxLength() {
        return this.maxLength;
    }

    public int getCursor() {
        return this.selectionStart;
    }

    private boolean drawsBackground() {
        return this.drawsBackground;
    }

    @Override
    public boolean changeFocus(boolean lookForwards) {
        if (!this.visible) return false;
        return super.changeFocus(lookForwards);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.visible && mouseX >= (double) this.x && mouseX < (double) (this.x + this.width) && mouseY >= (double) this.y && mouseY < (double) (this.y + this.height);
    }

    public int getInnerWidth() {
        return this.drawsBackground() ? this.width - 8 : this.width;
    }

    public void setSelectionEnd(int index) {
        int i = this.setting.getStringValue().length();
        this.selectionEnd = MathHelper.clamp(index, 0, i);
        if (this.textRenderer != null) {
            if (this.firstCharacterIndex > i) {
                this.firstCharacterIndex = i;
            }
            int j = this.getInnerWidth();
            String string = this.textRenderer.trimToWidth(this.setting.getStringValue().substring(this.firstCharacterIndex), j);
            int k = string.length() + this.firstCharacterIndex;
            if (this.selectionEnd == this.firstCharacterIndex) {
                this.firstCharacterIndex -= this.textRenderer.trimToWidth(this.setting.getStringValue(), j, true).length();
            }
            if (this.selectionEnd > k) {
                this.firstCharacterIndex += this.selectionEnd - k;
            } else if (this.selectionEnd <= this.firstCharacterIndex) {
                this.firstCharacterIndex -= this.firstCharacterIndex - this.selectionEnd;
            }
            this.firstCharacterIndex = MathHelper.clamp(this.firstCharacterIndex, 0, i);
        }
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setTextPredicate(Predicate<String> predicate) {
        this.textPredicate = predicate;
    }

    public Field(int width, int height, String name, String description) {
        super(width, height, name, description);
    }

    public Field(int width, int height, String name, String description, FieldSetting<?> setting) {
        this(width, height, name, description);
        if (setting instanceof StringSetting) setTextPredicate(STRING_PREDICATE);
        else if (setting instanceof IntegerSetting) setTextPredicate(INT_PREDICATE);
        else if (setting instanceof DoubleSetting) setTextPredicate(DOUBLE_PREDICATE);
        else if (setting instanceof LongSetting) setTextPredicate(LONG_PREDICATE);
        else if (setting instanceof FloatSetting) setTextPredicate(FLOAT_PREDICATE);
        this.setting = setting;
    }
}