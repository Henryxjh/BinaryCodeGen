package com.henryxjh.binary;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.henryxjh.binary.utils.OutputStreamUtils;
import com.henryxjh.binary.utils.QRCodeUtils;

public class Main {
    /** 前景色白色，0x000000 */
    private static final int FRONT_COLOR = 0;
    /** 背景色黑色，0xffffff */
    private static final int BACKGROUND_COLOR = 16777215;

    @Option(name = "-o", usage = "输出文件名")
    private File outputFile = null;

    @Option(name = "-b64", usage = "是否打印图片base64")
    private boolean printB64 = false;

    @Option(name = "-t", usage = "指定生成的图形类型", required = true)
    private BinaryTypes type = null;

    @Option(name = "-s", usage = "指定图形内容字符串", required = true)
    private String inputString = null;

    @Option(name = "-l", usage = "指定输出的图片文件的边长")
    private int size = 512;

    public static void main(String[] args) {
        new Main().doMain(args);
    }

    private void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(Arrays.asList(args));

            if (printB64 == false && outputFile == null) {
                System.err.println("至少选择base64或文件一种输出方式");
                System.exit(2);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            switch (type) {
                case QRCODE:
                    if (!QRCodeUtils.createCodeToOutputStream(inputString.getBytes(), size, FRONT_COLOR,
                            BACKGROUND_COLOR, null,
                            baos)) {
                        System.err.println("生成失败");
                        System.exit(3);
                    }
                    break;
            }

            if (printB64)
                System.out.println(OutputStreamUtils.outputStreamToBase64(baos));

            if (outputFile != null)
                if (!OutputStreamUtils.outputStringToFile(baos, outputFile)) {
                    System.err.println("文件输出失败");
                    System.exit(4);
                } else
                    System.exit(0);
        } catch (CmdLineException e) {
            if (inputString == null && type == null) {
                System.out.println("当前版本0.1，仅支持生成二维码");
                System.out.println("Usage:");
                parser.printUsage(System.out);
                System.exit(0);
            }
            System.err.println(e.getMessage());
            System.err.println("当前版本0.1，仅支持生成二维码");
            System.err.println("Usage:");
            parser.printUsage(System.err);
            System.exit(1);
        }
    }
}