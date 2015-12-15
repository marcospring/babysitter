package com.zhangk.babysitter.utils.common.code;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRCodeTest {
	private static int DEFAULT_WIDTH;
	private static int UNIT_WIDTH = 10;

	public static void main(String args[]) throws Exception {
		createImg();

	}

	public static void createImg() {
		Qrcode qrcode = new Qrcode();
		// 错误修正容量
		// L水平 7%的字码可被修正
		// M水平 15%的字码可被修正
		// Q水平 25%的字码可被修正
		// H水平 30%的字码可被修正
		// QR码有容错能力，QR码图形如果有破损，仍然可以被机器读取内容，最高可以到7%~30%面积破损仍可被读取。
		// 相对而言，容错率愈高，QR码图形面积愈大。所以一般折衷使用15%容错能力。
		qrcode.setQrcodeErrorCorrect('M');/* L','M','Q','H' */
		qrcode.setQrcodeEncodeMode('B');/* "N","A" or other */
		qrcode.setQrcodeVersion(5);/* 0-20 */

		String testString = "http://www.baidu.com";

		byte[] buff = null;
		try {
			buff = testString.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		boolean[][] bRect = qrcode.calQrcode(buff);
		DEFAULT_WIDTH = bRect.length * UNIT_WIDTH;

		BufferedImage bi = new BufferedImage(DEFAULT_WIDTH, DEFAULT_WIDTH,
				BufferedImage.TYPE_INT_RGB);
		// int unitWidth = DEFAULT_WIDTH / bRect.length;

		// createGraphics
		Graphics2D g = bi.createGraphics();

		// set background
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, DEFAULT_WIDTH, DEFAULT_WIDTH);
		g.setColor(Color.BLACK);

		if (buff.length > 0 && buff.length < 123) {

			for (int i = 0; i < bRect.length; i++) {

				for (int j = 0; j < bRect.length; j++) {
					if (bRect[j][i]) {
						g.fillRect(j * UNIT_WIDTH, i * UNIT_WIDTH,
								UNIT_WIDTH - 1, UNIT_WIDTH - 1);
					}
				}
			}
		}

		g.dispose();
		bi.flush();

		String FilePath = "D:/QRCode.png";
		File f = new File(FilePath);

		try {
			ImageIO.write(bi, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Create QRCode finished!");
	}
}
