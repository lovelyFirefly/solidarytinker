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
        // 统一入口方法（智能适配参数）
        public static Component getColorfulText(String translatableText, String append, int[] colors, int step, int durationMs, boolean isTranslatable) {
            return DistExecutor.unsafeRunForDist(
                    () -> () -> buildGradientText(translatableText, append, colors, step, durationMs, isTranslatable),
                    () -> () -> Component.translatable(translatableText)
            );
        }
        private static MutableComponent buildGradientText(String textKey, @Nullable String append, int[] colors, int step, int durationMs, boolean isTranslatable) {
            // 基础参数预处理
            String safeAppend = append != null ? append : "";
            String localizedText = isTranslatable
                    ? Language.getInstance().getOrDefault(textKey)
                    : textKey;
            String fullText = localizedText + safeAppend;

            // 生成渐变颜色数组
            int[] gradientColors = generateLinearGradient(colors, step);
            int cycleLength = 2 * (gradientColors.length - 1);
            long timestamp = System.currentTimeMillis();

            // 统一字符处理逻辑
            MutableComponent result = Component.empty();
            for (int i = 0; i < fullText.length(); i++) {
                int progress = (i + (int) (timestamp / durationMs)) % cycleLength;
                int colorIndex = (gradientColors.length - 1) - Math.abs(progress - (gradientColors.length - 1));

                result.append(
                        Component.literal(String.valueOf(fullText.charAt(i)))
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
                    // 将余数分配给最后一段,防止数组不能被整除从而突然几把的黑一下
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
                // 更新下一段起始的实际位置
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
            // 参数校验与预处理
            Preconditions.checkArgument(colors.length >= 1, "至少需要指定一个基础颜色");
            String fullText = getLocalizedText(textKey, isTranslatable) + Optional.ofNullable(append).orElse("");

            // 生成呼吸周期颜色数组
            int baseColor = colors[0];
            int[] breathColors = generateRGBBreathWave(baseColor, totalSteps);

            // 计算当前颜色相位
            long cyclePosition = System.currentTimeMillis() % durationMs;
            int colorIndex = (int) (cyclePosition * totalSteps / durationMs) % totalSteps;

            // 构建组件
            return buildColoredComponents(fullText, breathColors[colorIndex]);
        }

        private static String getLocalizedText(String key, boolean translatable) {
            return translatable ? Language.getInstance().getOrDefault(key) : key;
        }

        private static MutableComponent buildColoredComponents(String text, int color) {
            MutableComponent component = Component.empty();
            TextColor textColor = TextColor.fromRgb(color & 0xFFFFFF); // 确保去除alpha通道
            for (char c : text.toCharArray()) {
                component.append(
                        Component.literal(String.valueOf(c))
                                .setStyle(Style.EMPTY.withColor(textColor))
                );
            }
            return component;
        }

        private static int[] generateRGBBreathWave(int baseColor, int totalSteps) {
            // 提取RGB分量
            int r = (baseColor >> 16) & 0xFF;
            int g = (baseColor >> 8) & 0xFF;
            int b = baseColor & 0xFF;

            int[] wave = new int[totalSteps];
            for (int i = 0; i < totalSteps; i++) {
                // 使用正弦波控制亮度 (0.2 ~ 1.0)
                float ratio = 0.8f * (float) Math.abs(Math.sin(Math.PI * i / totalSteps)) + 0.2f;
                // 应用亮度系数并限制范围
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