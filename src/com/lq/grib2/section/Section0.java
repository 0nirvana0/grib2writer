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

/**	Section0 指示段
	字节范围	类型	描述	内容
	1-4	CHAR*4	GRIB2消息起始标记	固定内容“GRIB”
	5-6	SHORT	保留	0
	7	CHAR	学科	0
	8	CHAR	GRIB版本	2
	9-16	LONG	消息的总长度*/
public class Section0 {
	/*
	 * Section0 messageLength
	 */
	public static final int length = 16;
	private static final byte[] GRIB2 = new byte[] { 'G', 'R', 'I', 'B' };
	private short reserve = 0;// 5-6
	private char discipline = 0;// 7 CHAR 学科 0
	private char edition = 2; // 8 =1
	private long messageLength = -1;// 9-16=8

	public short getReserve() {
		return reserve;
	}

	public void setReserve(short reserve) {
		this.reserve = reserve;
	}

	public char getDiscipline() {
		return discipline;
	}

	public void setDiscipline(char discipline) {
		this.discipline = discipline;
	}

	public char getEdition() {
		return edition;
	}

	public void setEdition(char edition) {
		this.edition = edition;
	}

	public long getMessageLength() {
		return messageLength;
	}

	public void setMessageLength(long messageLength) {
		this.messageLength = messageLength;
	}

	public static byte[] getGrib2() {
		return GRIB2;
	}

	public void wirte(DataOutputStream out) throws IOException {
		try {
			out.write(GRIB2);// 1-4
			out.writeShort(getReserve());// 5-6
			out.writeByte(getDiscipline());// 7
			out.writeByte(getEdition());// 8
			messageLength = getMessageLength();
			if (messageLength >= 0) {
				out.writeLong(getMessageLength());
			} else {
				throw new IOException("请设置 Section0  messageLength");
			}
			out.flush();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
}
