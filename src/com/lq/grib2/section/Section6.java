package com.lq.grib2.section;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 Section6 位图段
	详细描述见表2-7。
	表 2-7 Setcion6 位图段
	字节范围	类型	描述	内容
	1-4	INT	该段消息体长度	6
	5	CHAR	该段消息体标记	固定值：6
	6	CHAR	Bit-map指标	固定值为255：没有Bit-map数据段
 */
public class Section6 {
	public static final int length = 6;// 1-4 INT 该段消息体长度 6
	public static final char section = 6;// 5 CHAR 该段消息体标记 固定值：6
	private char bitMapIndicator = 255;// 6 CHAR Bit-map指标 固定值为255：没有Bit-map数据段

	public char getBitMapIndicator() {
		return bitMapIndicator;
	}

	public void setBitMapIndicator(char bitMapIndicator) {
		this.bitMapIndicator = bitMapIndicator;
	}

	public void write(DataOutputStream out) throws IOException {
		try {
			out.writeInt(length);
			out.writeByte(section);
			out.writeByte(getBitMapIndicator());//6

			out.flush();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
}
