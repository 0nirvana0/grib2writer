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
	 Section1 标识段
	详细描述见表2-2。
	表 2-2 Setcion1 标识段
	字节范围	类型	描述	内容
	1-4	INT	该段消息长度	21
	5	CHAR	该段标号	固定值：1
	6-7	SHORT	中心点	固定值为38：北京
	8-9	SHORT	子中心点	默认值0
	10	CHAR	GRIB主表版本号	
	11	CHAR	GRIB本地表版本号	固定值为0：没有本地表
	12	CHAR	参考时间的意义	固定值为1：表示起报时间
	13-14	SHORT	年	
	15	CHAR	月	
	16	CHAR	日	
	17	CHAR	时	
	18	CHAR	分	
	19	CHAR	秒	
	20	CHAR	产品状态	
	21	CHAR	数据类型	固定为1：预报产品
 */
public class Section1 {
	/*
	 * Section1 short year; char month, day, hour, minute, second;
	 */
	public static final int length = 21; // 1-4 INT 该段消息长度 21
	public static final char section = 1; // 5 CHAR 该段标号 固定值：1
	private short center_id = 38; // 6-7 SHORT 中心点 固定值为38：北京
	private short subcenter_id = 0;// 8-9 SHORT 子中心点 默认值0
	private char master_table_version = 1; // 10 CHAR GRIB主表版本号
	private char local_table_version = 0; // 11 CHAR GRIB本地表版本号 固定值为0：没有本地表
	private char significanceOfRT = 1; // 12 CHAR 参考时间的意义 固定值为1：表示起报时间
	private short year;// 13-14 SHORT 年
	private char month, day, hour, minute, second; // octets 13-19
	private char productionStatus = 0;// 20 CHAR 产品状态
	private char processedDataType = 1;// 21 CHAR 数据类型 固定为1：预报产品

	public short getCenter_id() {
		return center_id;
	}

	public void setCenter_id(short center_id) {
		this.center_id = center_id;
	}

	public short getSubcenter_id() {
		return subcenter_id;
	}

	public void setSubcenter_id(short subcenter_id) {
		this.subcenter_id = subcenter_id;
	}

	public char getMaster_table_version() {
		return master_table_version;
	}

	public void setMaster_table_version(char master_table_version) {
		this.master_table_version = master_table_version;
	}

	public char getLocal_table_version() {
		return local_table_version;
	}

	public void setLocal_table_version(char local_table_version) {
		this.local_table_version = local_table_version;
	}

	public char getSignificanceOfRT() {
		return significanceOfRT;
	}

	public void setSignificanceOfRT(char significanceOfRT) {
		this.significanceOfRT = significanceOfRT;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public char getMonth() {
		return month;
	}

	public void setMonth(char month) {
		this.month = month;
	}

	public char getDay() {
		return day;
	}

	public void setDay(char day) {
		this.day = day;
	}

	public char getHour() {
		return hour;
	}

	public void setHour(char hour) {
		this.hour = hour;
	}

	public char getMinute() {
		return minute;
	}

	public void setMinute(char minute) {
		this.minute = minute;
	}

	public char getSecond() {
		return second;
	}

	public void setSecond(char second) {
		this.second = second;
	}

	public char getProductionStatus() {
		return productionStatus;
	}

	public void setProductionStatus(char productionStatus) {
		this.productionStatus = productionStatus;
	}

	public char getProcessedDataType() {
		return processedDataType;
	}

	public void setProcessedDataType(char processedDataType) {
		this.processedDataType = processedDataType;
	}

	public void write(DataOutputStream out) throws IOException {
		try {
			out.writeInt(length);// 1-4
			out.writeByte(section);// 5
			out.writeShort(getCenter_id());// 6-7
			out.writeShort(getSubcenter_id());// 8-9
			out.writeByte(getMaster_table_version());// 10
			out.writeByte(getLocal_table_version());// 11
			out.writeByte(getSignificanceOfRT());// 12
			out.writeShort(getYear());// 13-14
			out.writeByte(getMonth());
			out.writeByte(getDay());
			out.writeByte(getHour());
			out.writeByte(getMinute());
			out.writeByte(getSecond());
			out.writeByte(getProductionStatus());
			out.writeByte(getProcessedDataType());// 21

			out.flush();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
}
