package com.marth7th.solidarytinker.util;

public class MathUtil {
    /**
     *
     * @param number 输入的数字
     * @return 保留的小数部分,
     * <br>如-2.3直接得到-0.3
     * <br>2.1直接得到0.1
     */
    public static double getDecimal(double number) {
        return number - (int)number;
    }

    /**
     *
     * @param number 输入的数字
     * @param limit 保留的位数
     * @return 限位后的数
     * <br>如MathUtil.limitsNumber(3.14159,3),则返回3.141
     */
    public static double limitsNumber(float number,float limit){
        return Math.round(number * 10 * limit) /(10 * limit);
    }
}
