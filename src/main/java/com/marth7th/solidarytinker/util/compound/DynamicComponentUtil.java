package com.marth7th.solidarytinker.util.compound;

import com.google.common.base.Preconditions;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.*;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author firefly
 * <h4>一个专门用于生成RGB颜色字的类,其他非public均为逻辑处理,无需调用</h4>
 */
public class DynamicComponentUtil {
    public static class scrollColorfulText {
        public static Component getColorfulText(String translatableText, String append, int[] colors, int step, int durationMs, boolean isTranslatable) {
            return DistExecutor.unsafeRunForDist(
                    () -> () -> buildGradientText(translatableText, append, colors, step, durationMs, isTranslatable),
                    () -> () -> Component.translatable(translatableText)
            );
        }
        private static MutableComponent buildGradientText(String textKey, @Nullable String append, int[] colors, int step, int durationMs, boolean isTranslatable) {
            String safeAppend = append != null ? append : "";
            String localizedText = isTranslatable
                    ? Language.getInstance().getOrDefault(textKey)
                    : textKey;
            String fullText = localizedText + safeAppend;

            int[] gradientColors = generateLinearGradient(colors, step);
            int maxIndex = gradientColors.length - 1;
            int cycleLength = 2 * maxIndex;
            long timestamp = System.currentTimeMillis();

            MutableComponent result = Component.empty();
            for (int i = 0; i < fullText.length(); i++) {
                long timeOffset = durationMs > 0 ? (timestamp / durationMs) : 0;
                int progress = (int) ((i + timeOffset) % cycleLength);
                int colorIndex = maxIndex - Math.abs(progress - maxIndex);
                if (colorIndex < 0) {
                    colorIndex = 0;
                } else if (colorIndex > maxIndex) {
                    colorIndex = maxIndex;
                }
                result.append(Component.literal(String.valueOf(fullText.charAt(i)))
                        .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(gradientColors[colorIndex])))
                );
            }
            return result;
        }

        private static int[] generateLinearGradient(int[] colors, int totalSteps) {
            int[] gradient = new int[totalSteps];
            int segments = colors.length - 1;
            int stepsPerSegment = totalSteps / segments;
            int remainder = totalSteps % segments;
            int startIndex = 0;
            for (int i = 0; i < segments; i++) {
                int currentSteps = stepsPerSegment;
                if (i == segments - 1) {
                    currentSteps += remainder;
                }
                int startColor = colors[i];
                int endColor = colors[i + 1];
                float r1 = (startColor >> 16) & 0xFF;
                float g1 = (startColor >> 8) & 0xFF;
                float b1 = startColor & 0xFF;
                float r2 = (endColor >> 16) & 0xFF;
                float g2 = (endColor >> 8) & 0xFF;
                float b2 = endColor & 0xFF;
                for (int j = 0; j < currentSteps; j++) {
                    float t = j / (float) (currentSteps - 1);
                    int r = (int) (r1 + (r2 - r1) * t);
                    int g = (int) (g1 + (g2 - g1) * t);
                    int b = (int) (b1 + (b2 - b1) * t);
                    int index = startIndex + j;
                    if (index < totalSteps) {
                        gradient[index] = (r << 16) | (g << 8) | b;
                    }
                }
                startIndex += currentSteps;
            }
            return gradient;
        }
    }

    public static class BreathColorfulText {
        public static Component getColorfulText(String textKey, @Nullable String append, int[] colors, int totalSteps, int durationMs, boolean isTranslatable) {
            return DistExecutor.unsafeRunForDist(
                    () -> () -> buildBreathText(textKey, append, colors, totalSteps, durationMs,isTranslatable),
                    () -> () -> Component.translatable(textKey)
            );
        }

        private static MutableComponent buildBreathText(String textKey, @Nullable String append, int[] colors, int totalSteps, int durationMs, boolean isTranslatable) {
            Preconditions.checkArgument(colors.length >= 1, "至少需要指定一个基础颜色");
            String fullText = getLocalizedText(textKey, isTranslatable) + Optional.ofNullable(append).orElse("");
            int baseColor = colors[0];
            int[] breathColors = generateRGBBreathWave(baseColor, totalSteps);
            long cyclePosition = System.currentTimeMillis() % durationMs;
            int colorIndex = (int) (cyclePosition * totalSteps / durationMs) % totalSteps;
            if (colorIndex < 0 || colorIndex >= totalSteps) {
                colorIndex = 0;
            }
            return buildColoredComponents(fullText, breathColors[colorIndex]);
        }

        private static String getLocalizedText(String key, boolean translatable) {
            return translatable ? Language.getInstance().getOrDefault(key) : key;
        }

        private static MutableComponent buildColoredComponents(String text, int color) {
            MutableComponent component = Component.empty();
            TextColor textColor = TextColor.fromRgb(color & 0xFFFFFF);
            for (char c : text.toCharArray()) {
                component.append(
                        Component.literal(String.valueOf(c))
                                .setStyle(Style.EMPTY.withColor(textColor))
                );
            }
            return component;
        }

        private static int[] generateRGBBreathWave(int baseColor, int totalSteps) {
            int r = (baseColor >> 16) & 0xFF;
            int g = (baseColor >> 8) & 0xFF;
            int b = baseColor & 0xFF;
            int[] wave = new int[totalSteps];
            for (int i = 0; i < totalSteps; i++) {
                float ratio = 0.8f * (float) Math.abs(Math.sin(Math.PI * i / totalSteps)) + 0.2f;
                int dr = clamp((int) (r * ratio), 0, 255);
                int dg = clamp((int) (g * ratio), 0, 255);
                int db = clamp((int) (b * ratio), 0, 255);
                wave[i] = (dr << 16) | (dg << 8) | db;
            }
            return wave;
        }
        private static int clamp(int value, int min, int max) {
            return Math.max(min, Math.min(max, value));
        }
    }
}