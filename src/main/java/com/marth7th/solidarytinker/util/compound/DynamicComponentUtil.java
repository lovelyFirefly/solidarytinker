package com.marth7th.solidarytinker.util.compound;

import net.minecraft.locale.Language;
import net.minecraft.network.chat.*;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;

/**
 * @author firefly
 * <h4>一个专门用于生成RGB颜色字的类,其他非public均为逻辑处理,无需调用</h4>
 */
public class DynamicComponentUtil {
    public static class scrollColorfulText{
        // 统一入口方法（智能适配参数）
        public static Component getColorfulText(String translatableText,String append,int[] colors,int step,int durationMs) {
            return DistExecutor.unsafeRunForDist(
                    () -> () -> buildGradientText(translatableText, append, colors,step,durationMs),
                    () -> () -> Component.translatable(translatableText)
            );
        }

        private static MutableComponent buildGradientText(String textKey, @Nullable String append, int[] colors, int step,int durationMs) {
            String localizedText = Language.getInstance().getOrDefault(textKey);
            String safeAppend = append != null ? append : "";
            String fullText = localizedText + safeAppend;
            int[] gradientColors = generateLinearGradient(colors, step);
            int cycleLength = 2 * (gradientColors.length - 1);
            long timestamp = System.currentTimeMillis();
            MutableComponent result = Component.empty();
            for (int i = 0; i < fullText.length(); i++) {
                int progress = (i + (int) (timestamp / durationMs)) % cycleLength;
                int colorIndex = (gradientColors.length - 1) - Math.abs(progress - (gradientColors.length - 1));
                char currentChar = (i < localizedText.length())
                        ? localizedText.charAt(i)
                        : safeAppend.charAt(i - localizedText.length());
                result.append(Component.literal(String.valueOf(currentChar))
                        .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(gradientColors[colorIndex]))));
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
    public static class BreathColorfulText{
        public static Component getColorfulText(String text, String append, int baseColor, int breathDuration) {
            return DistExecutor.unsafeRunForDist(
                    () -> () -> buildBreathText(text, append,baseColor,breathDuration),
                    () -> () -> Component.translatable(text)
            );
        }
        // 呼吸灯效果生成方法
        private static Component buildBreathText(String text, String append, int baseColor, int breathDuration) {
            MutableComponent component = Component.literal("");

            // 计算呼吸周期（0~1正弦波动）
            long timestamp = System.currentTimeMillis();
            double phase = (timestamp % breathDuration) / (double) breathDuration;
            float brightness = (float) (0.5 * Math.sin(2 * Math.PI * phase) + 0.5); // 亮度范围[0,1]

            // 动态调整颜色亮度
            for (int i = 0; i < text.length(); i++) {
                int breathColor = adjustColorBrightness(baseColor, brightness);
                component.append(Component.literal(String.valueOf(text.charAt(i)))
                        .withStyle(Style.EMPTY.withColor(breathColor)));
            }

            return component.append(append);
        }

        // 颜色亮度调整工具方法
        private static int adjustColorBrightness(int color, float brightness) {
            // 分解ARGB通道
            int alpha = (color >> 24) & 0xFF;
            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = color & 0xFF;

            // 应用亮度系数（HSV的V值调整）
            red = (int) (red * brightness);
            green = (int) (green * brightness);
            blue = (int) (blue * brightness);

            return (alpha << 24) | (red << 16) | (green << 8) | blue;
        }
    }
}