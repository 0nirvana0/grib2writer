/*
 * Copyright 1998-2015 John Caron and University Corporation for Atmospheric Research/Unidata
 *
 *  Portions of this software were developed by the Unidata Program at the
 *  University Corporation for Atmospheric Research.
 *
 *  Access and use of this software shall impose the following obligations
 *  and understandings on the user. The user is granted the right, without
 *  any fee or cost, to use, copy, modify, alter, enhance and distribute
 *  this software, and any derivative works thereof, and its supporting
 *  documentation for any purpose whatsoever, provided that this entire
 *  notice appears in all copies of the software, derivative works and
 *  supporting documentation.  Further, UCAR requests that the user credit
 *  UCAR/Unidata in any publications that result from the use of this
 *  software or in any product that includes this software. The names UCAR
 *  and/or Unidata, however, may not be used in any advertising or publicity
 *  to endorse or promote any products or commercial entity unless specific
 *  written permission is obtained from UCAR/Unidata. The user also
 *  understands that UCAR/Unidata is not obligated to provide the user with
 *  any support, consulting, training or assistance of any kind with regard
 *  to the use, operation and performance of this software nor to provide
 *  the user with any updates, revisions, new versions or "bug fixes."
 *
 *  THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 *  INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 *  FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 *  NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 *  WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.lq.grib2.section;

import java.io.DataOutputStream;
import java.io.IOException;

/**
	Section5 数据表示段
	字节范围	类型	描述	内容
	1-4	INT	该段消息体长度	21
	5	CHAR	该段消息体标记	固定值：5
	6-7	SHORT	该数据段内的格点数	
	10-11	INT	数据模版号	固定值0：使用数据模版Data Representation Template 5.0
	12-15	FLOAT	参考值	
	16-17	SHORT	二进制比例因子	
	18-19	SHORT	十进制比例因子	
	20	CHAR	Simple压码每个包的字节个数	
	21	CHAR	原数据值的类型	固定为0：原始数据为浮点数
 */
public class Section5 {
	/**Section5
		dataPoints
	 */
	public static int length = 21; // 1-4 INT 该段消息体长度 21
	public static char section = 5;// 5 CHAR 该段消息体标记 固定值：5
	// private short dataPoints;// 6-7 SHORT 该数据段内的格点数
	// private short reserve8=0;//8-9
	private int dataPoints;// 6-9
	private short dataTemplate = 0;// SHORT? 10-11 INT 数据模版号 固定值0：使用数据模版Data Representation Template 5.0
	public static float referenceValue = 0;// 12-15 FLOAT 参考值
	public static short binaryScaleFactor = 0;// 16-17 SHORT 二进制比例因子
	public static short decimalScaleFactor = 2;// 18-19 SHORT 十进制比例因子
	public static char numberOfBits = 16;// 20 CHAR Simple压码每个包的字节个数
	private char originalType = 0;// 21 CHAR 原数据值的类型 固定为0：原始数据为浮点数

	public short getDataTemplate() {
		return dataTemplate;
	}

	public void setDataPoints(int dataPoints) {
		this.dataPoints = dataPoints;
	}

	public void setDataTemplate(short dataTemplate) {
		this.dataTemplate = dataTemplate;
	}

	public static int getLength() {
		return length;
	}

	public static char getSection() {
		return section;
	}

	public int getDataPoints() {
		return dataPoints;
	}

	public void write(DataOutputStream out) throws IOException {
		try {
			out.writeInt(length);
			out.writeByte(section);
			out.writeInt(getDataPoints());
			out.writeShort(getDataTemplate());
			out.writeFloat(referenceValue);
			out.writeShort(binaryScaleFactor);
			out.writeShort(decimalScaleFactor);
			out.writeByte(numberOfBits);
			out.writeByte(originalType);//21

			out.flush();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
}
