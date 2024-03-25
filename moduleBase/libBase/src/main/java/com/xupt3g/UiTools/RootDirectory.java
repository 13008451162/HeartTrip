package com.xupt3g.UiTools;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.UiTools.RootDirectory
 *
 * @author: shallew
 * @data:2024/1/27 0:27
 * @about: TODO 返回网络请求的根路径
 */
public class RootDirectory {

    private static String Root = "http://101.126.65.165:8888";

    /**
     *
     * @return 返回网络请求的根路径
     * TODO 全局可用的网络请求根路径 （使用Retrofit时）
     */
    public static String getRootDirectory() {
        return "http://10.0.2.2";
    }

    public static String getNetRoot(){
        return Root;
    }
}
