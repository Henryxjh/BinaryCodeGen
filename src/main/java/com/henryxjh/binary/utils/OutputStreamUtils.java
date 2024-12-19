package com.henryxjh.binary.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * 输出流工具
 */
public class OutputStreamUtils {
    /**
     * 将输出流转为base64字符串
     * @param oStream 输出流
     * @return base64字符串
     */
    public static String outputStreamToBase64(OutputStream oStream) {
        return Base64.getEncoder().encodeToString(outputStreamToByteArray(oStream));
    }

    /**
     * 将输出流输出到文件
     * @param oStream 输出流
     * @param destFile 目标文件
     * @return 输出是否成功，失败时打印失败原因
     */
    public static boolean outputStringToFile(OutputStream oStream, File destFile) {
        try (FileOutputStream fos = new FileOutputStream(destFile);) {
            fos.write(((ByteArrayOutputStream) oStream).toByteArray());
            fos.close();
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将输出流转为{@code byte[]}
     * @param oStream 输出流
     * @return {@code byte[]}
     */
    public static byte[] outputStreamToByteArray(OutputStream oStream) {
        return ((ByteArrayOutputStream) oStream).toByteArray();
    }
}
