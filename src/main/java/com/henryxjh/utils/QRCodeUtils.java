package com.henryxjh.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码生成工具
 */
public class QRCodeUtils {
    /**
     * 生成正方形二维码的{@code BufferedImage}
     * 
     * @param bytes           数据的byte[]
     * @param side            二维码的边长
     * @param frontColor      前景色，建议使用0xrrggbb的方式定义
     * @param backgroundColor 背景色，建议使用0xrrggbb的方式定义
     * @param encodeHints     二维码生成设置，传入{@code null}或空map使用默认设置：1像素边框，M(15%)级别纠错，系统默认字符集
     * @return 包含二维码数据的{@code BufferedImage}
     * @throws WriterException 生成时出现错误
     */
    public static BufferedImage getBufferedImage(byte[] bytes, int side, int frontColor, int backgroundColor,
            Map<EncodeHintType, Object> encodeHints) throws WriterException {
        return getBufferedImage(bytes, side, side, frontColor, backgroundColor, encodeHints);
    }

    /**
     * 生成二维码的{@code BufferedImage}
     * 
     * @param bytes           数据的byte[]
     * @param width           二维码的宽度
     * @param height          二维码的高度
     * @param frontColor      前景色，建议使用0xrrggbb的方式定义
     * @param backgroundColor 背景色，建议使用0xrrggbb的方式定义
     * @param encodeHints     二维码生成设置，传入{@code null}或空map使用默认设置：1像素边框，M(15%)级别纠错，系统默认字符集
     * @return 包含二维码数据的{@code BufferedImage}
     * @throws WriterException 生成时出现错误
     */
    public static BufferedImage getBufferedImage(byte[] bytes, int width, int height, int frontColor,
            int backgroundColor, Map<EncodeHintType, Object> encodeHints) throws WriterException {
        if (encodeHints == null) {
            encodeHints = new HashMap<>();
        }
        if (encodeHints.size() == 0) {
            encodeHints.put(EncodeHintType.MARGIN, 1);
            encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            encodeHints.put(EncodeHintType.CHARACTER_SET, Charset.defaultCharset().displayName());
        }
        BitMatrix bitMatrix = new MultiFormatWriter().encode(new String(bytes), BarcodeFormat.QR_CODE, width, height,
                encodeHints);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? frontColor : backgroundColor);
        return bufferedImage;
    }

    /**
     * 创建正方形二维码并输出到流
     * @param content 需要生成的数据的byte[]
     * @param side 二维码图片边长
     * @param frontColor 前景色，建议使用0xrrggbb的方式定义
     * @param backgroundColor 背景色，建议使用0xrrggbb的方式定义
     * @param encodeHints 二维码生成设置，传入{@code null}或空map使用默认设置：1像素边框，M(15%)级别纠错，系统默认字符集
     * @param oStream 需要输出到的流
     * @return 生成成功输出{@code trur}，生成失败输出{@code false}
     */
    public static boolean createCodeToOutputStream(byte[] content, int side, int frontColor, int backgroundColor, Map<EncodeHintType, Object> encodeHints, OutputStream oStream) {
        if (content == null || content.length == 0)
            return false;

        try {
            BufferedImage bufferedImage = getBufferedImage(content, side, frontColor, backgroundColor, encodeHints);
            ImageIO.write(bufferedImage, "png", oStream);
            return true;
        } catch (WriterException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建二维码并输出到流
     * @param content 需要生成的数据的byte[]
     * @param width 二维码图片宽度
     * @param height 二维码图片高度
     * @param frontColor 前景色，建议使用0xrrggbb的方式定义
     * @param backgroundColor 背景色，建议使用0xrrggbb的方式定义
     * @param encodeHints 二维码生成设置，传入{@code null}或空map使用默认设置：1像素边框，M(15%)级别纠错，系统默认字符集
     * @param oStream 需要输出到的流
     * @return 生成成功输出{@code trur}，生成失败输出{@code false}
     */
    public static boolean createCodeToOutputStream(byte[] content, int width, int height, int frontColor, int backgroundColor, Map<EncodeHintType, Object> encodeHints, OutputStream oStream) {
        if (content == null || content.length == 0)
            return false;

        try {
            BufferedImage bufferedImage = getBufferedImage(content, width, height, frontColor, backgroundColor, encodeHints);
            ImageIO.write(bufferedImage, "png", oStream);
            return true;
        } catch (WriterException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
