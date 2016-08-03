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
	字节范围	类型	描述	内容
	1-4	INT	该段消息体长度	5+nn
	5	CHAR	该段消息体标记	固定值：7
	6-nn	FLOAT	压码后的格点数据	
 */
public class Section7 {
	/**
	 * Section7 float[] data; 
	 * 不写 dataNum
	 */
	/*
	 * Section7 float[] data;
	 */
	private static final int length = 5; // 1-4 INT 该段消息体长度 5+nn
	public static final char section = 7; // 5 CHAR 该段消息体标记 固定值：7
	private float[] data;// 6-nn FLOAT 压码后的格点数据
	private int numberPoints;

	/**
	 * 
	 * @param dataNum 数据集的个数
	 */
	public Section7(int numberPoints) {
		this.numberPoints = numberPoints;
	}

	/**
	 * 
	 * @return Section7 消息体长度,  即返回一个数据集的长度
	 */
	public int getLength() {
		return length + this.numberPoints * (Section5.numberOfBits / 8);
	}

	public void setData(float[] data) {
		this.data = data;
	}

	public void write(DataOutputStream out) throws IOException {
		try {
			out.writeInt(getLength());// 1-4
			out.writeByte(section);// 5
			float R = Section5.referenceValue;
			float EE = (float) Math.pow(2.0, Section5.binaryScaleFactor);
			float DD = (float) Math.pow(10, Section5.decimalScaleFactor);
			short v;
			for (int i = 0; i < data.length; i++) {
				v = (short) ((data[i] * DD - R) / EE);
				out.writeShort(v);
			}
			out.flush();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
}
