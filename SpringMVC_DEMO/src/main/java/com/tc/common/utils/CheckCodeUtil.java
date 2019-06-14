package com.tc.common.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *	生成验证码图片工具类
 */
public final class CheckCodeUtil {

    /**
     * 验证码组成字符
     */
    private static final char[] chars = {
            '2', '3', '4', '5', '6','7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
            'S','T','U','V','W','X','Y','Z'
    };

    /**
     * 验证码个数
     */
    private static final int SIZE = 4;
    /**
     * 干扰线的条数
     */
    private static final int LINES = 5;
    /**
     * 验证码图片的宽度
     */
    private static final int WIDTH = 90;
    /**
     * 验证码图片的高度
     */
    private static final int HEIGHT = 30;
    /**
     * 字体的大小
     */
    private static final int FONT_SIZE = 30;

    /**
     * 创建一个验证码图片，其中Map的key封装了
     * 验证码字符串，Map的value封装了验证码图片。
     */
    public static Map<String, BufferedImage> createImage() {
        StringBuffer sb = new StringBuffer();//存储验证码字符
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_RGB);//存储验证码图片
        Graphics graphic = image.getGraphics();//获取画布
        graphic.setColor(Color.LIGHT_GRAY);//设置画笔颜色
        graphic.fillRect(0, 0, WIDTH, HEIGHT);//用灰色填充了背景
        Random ran = new Random();
        FontMetrics fm = graphic.getFontMetrics();//获取了字体的详细信息
        // 画随机字符
        for (int i = 1; i <= SIZE; i++) {
            int r = ran.nextInt(chars.length);
            graphic.setColor(getRandomColor());
            graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
            graphic.drawString(chars[r] + "", (i - 1) * WIDTH / SIZE,
                    (HEIGHT+fm.getHeight()) / 2);
            sb.append(chars[r]);// 将字符保存，存入Session
        }
        // 画干扰线
        for (int i = 1; i <= LINES; i++) {
            graphic.setColor(getRandomColor());
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT),
                    ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
        }
        Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
        map.put(sb.toString(), image);
        return map;
    }

    /**
     * 获取随机颜色
     * @return
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(186), ran.nextInt(186),
                ran.nextInt(186));
        return color;
    }

    /**
     * 获取图片的jpeg个格式的输入流
     * @param image
     * @return
     * @throws java.io.IOException
     */
    public static InputStream getInputStream(BufferedImage image)
            throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //图片压缩
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
        encoder.encode(image);
        byte[] imageBts = bos.toByteArray();
        InputStream in = new ByteArrayInputStream(imageBts);
        return in;
    }
}
