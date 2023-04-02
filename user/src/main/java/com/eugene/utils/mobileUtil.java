package com.eugene.utils;

/**
 * @Description 手机号操作工具类
 * @Author eugene
 * @Data 2023/4/1 13:31
 */
public class mobileUtil {
    /**
     * 生成随机手机号码的方法
     */
    public static String randomMobile() {
        // 拼接后面的10位
        StringBuilder behind = new StringBuilder();
        int number[] = {3, 4, 5, 7, 8};  //号码第二位仅有这些
        for (int i = 0; i < 1; i++) {
            int randNumber = (int) (Math.random() * 1000) % 5;
            behind.append(String.valueOf(number[randNumber]));
        }
        //添加后面9位数
        for (int i = 0; i < 9; i++) {
            int randNumber = (int) (Math.random() * 1000) % 10;
            behind.append(randNumber);
        }
        // 首位为“1” 组合并打印
        return String.format("1" + behind);
    }
}
