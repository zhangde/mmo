package com.tongwan.common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhangde
 * 
 * @date 2014年1月9日
 */
public class FileX {
	/**
	 * 读取文本文件的全部内容
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static final String readAll(final File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\r\n");
		}
		br.close();

		br = null;
		return sb.toString();
	}
	/**
	 * 读取文本文件的全部内容
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static final String readAll(final String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = "";
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\r\n");
		}
		br.close();

		br = null;
		return sb.toString();
	}
	/**
	 * 创建新文本文件
	 * @param file 文件路径
	 * @param fileContent 文件内容 
	 */
	public static final void newFile(final String file, final String fileContent) {

		try {
			String filePath = file;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();

			myFilePath = null;
			resultFile = null;
			myFile = null;
			strContent = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
