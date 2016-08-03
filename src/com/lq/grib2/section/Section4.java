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
	Section4 产品定义段
	字节范围	类型	描述	内容
	1-4	INT	该段消息体长度	58
	5	CHAR	该段消息体标记	固定值：4
	6-7	SHORT	产品定义的后面没有坐标定义信息	
	8-9	SHORT	产品模版编号	固定值为8：使用Product Definition Template 4.8模版
	10	CHAR	产品的分类	
	11	CHAR	产品的编号	
	12	CHAR	生成方法	固定值为2: Forecast预报产品
	13	CHAR		固定值为0
	14	CHAR		固定值为0
	15-16	SHORT	在起报时间后，需cut-off的数据时间小时部分	固定值为0
	17	CHAR	在起报时间后，需cut-off的数据时间分钟部分	固定值为0
	18	CHAR	时间范围的单位	固定值为1：1小时
	19-22	INT	相对于起报时间，预报时间的时间数	
	23	CHAR	预报数据的层次单位	固定值为1：地面或水平面
	24	CHAR	层次值	固定值为0
	25-28	INT	层次值	固定值为0
	29	CHAR	第二层次	固定值为255
	30	CHAR	层次值	固定值为0
	31-34	INT	层次值	固定值为0
	35-36	SHORT	预报结束时间年	
	37	CHAR	预报结束时间月	
	38	CHAR	预报结束时间日	
	39	CHAR	预报结束时间时	
	40	CHAR	预报结束时间分	
	41	CHAR	预报结束时间秒	
	42	CHAR	时间范围的数量	固定为1
	43-46	INT	缺测数据总个数	
	47	CHAR	统计处理方式	
	48	CHAR	时间增量类型	固定值为2：对于同一起报时间的多个连续的预报，预报时间是递增的
	49	CHAR	预报时间范围单位	固定为1：小时
	50-53	INT	时间范围长度	以49字节为单位
	54	CHAR	递增时间单位	固定为1：小时
	55-58	INT	连续预报时间段的时间增量
*/
public class Section4 {
	/*
	 * Section4 char parameterCategory parameterNumber int forecastTime char statistical
	 */
	public static int length = 58; // 1-4 INT 该段消息体长度 58
	public static char section = 4; // 5 CHAR 该段消息体标记 固定值：4
	private short reserve = 0; // 6-7 SHORT 产品定义的后面没有坐标定义信息
	private short templateNumber = 8;// 8-9 SHORT 产品模版编号 固定值为8：使用Product Definition Template 4.8模版
	private char parameterCategory;// 10 CHAR 产品的分类
	private char parameterNumber; // 11 CHAR 产品的编号
	private char significanceOfRT = 2;// 12 CHAR 生成方法 固定值为2: Forecast预报产品
	private char backProcessId = 0; // 13 CHAR 固定值为0
	private char genProcessId = 0;// 14 CHAR 固定值为0
	private short hoursAfterCutoff = 0; // 15-16 SHORT 在起报时间后，需cut-off的数据时间小时部分 固定值为0
	private char minutesAfterCutoff = 0;// 17 CHAR 在起报时间后，需cut-off的数据时间分钟部分 固定值为0
	private char timeUnit = 1;// 18 CHAR 时间范围的单位 固定值为1：1小时
	private int forecastTime;// 19-22 INT 相对于起报时间，预报时间的时间数
	private char levelType1 = 1;// 23 CHAR 预报数据的层次单位 固定值为1：地面或水平面
	private char levelValue1 = 0;// 24 CHAR 层次值 固定值为0
	private int levelValueInt1 = 0;// 25-28 INT 层次值 固定值为0
	private char levelType2 = 255;// 29 CHAR 第二层次 固定值为255
	private char levelValue2 = 0;// 30 CHAR 层次值 固定值为0
	private int levelValueInt2 = 0;// 31-34 INT 层次值 固定值为0
	private short year;// 35-36 SHORT 预报结束时间年
	private char month;// 37 CHAR 预报结束时间月
	private char day;// 38 CHAR 预报结束时间日
	private char hour;// 39 CHAR 预报结束时间时
	private char minute;// 40 CHAR 预报结束时间分
	private char second;// 41 CHAR 预报结束时间秒
	private char validsNum = 1;// 42 CHAR 时间范围的数量 固定为1
	private int missDateNum = 0;// 43-46 INT 缺测数据总个数
	private char statistical;// 47 CHAR 统计处理方式
	private char timeAddType = 2;// 48 CHAR 时间增量类型 固定值为2：对于同一起报时间的多个连续的预报，预报时间是递增的
	private char productTimeUnit = 1;// 49 CHAR 预报时间范围单位 固定为1：小时
	private int timeLength;// 50-53 INT 时间范围长度 以49字节为单位
	private char timeAddUnit = 1;// 54 CHAR 递增时间单位 固定为1：小时
	private int timeAddValue;// 55-58 INT 连续预报时间段的时间增量

	public short getReserve() {
		return reserve;
	}

	public short getTemplateNumber() {
		return templateNumber;
	}

	public char getParameterCategory() {
		return parameterCategory;
	}

	public char getParameterNumber() {
		return parameterNumber;
	}

	public char getSignificanceOfRT() {
		return significanceOfRT;
	}

	public char getBackProcessId() {
		return backProcessId;
	}

	public char getGenProcessId() {
		return genProcessId;
	}

	public short getHoursAfterCutoff() {
		return hoursAfterCutoff;
	}

	public char getMinutesAfterCutoff() {
		return minutesAfterCutoff;
	}

	public char getTimeUnit() {
		return timeUnit;
	}

	public int getForecastTime() {
		return forecastTime;
	}

	public char getLevelType1() {
		return levelType1;
	}

	public char getLevelValue1() {
		return levelValue1;
	}

	public int getLevelValueInt1() {
		return levelValueInt1;
	}

	public char getLevelType2() {
		return levelType2;
	}

	public char getLevelValue2() {
		return levelValue2;
	}

	public int getLevelValueInt2() {
		return levelValueInt2;
	}

	public short getYear() {
		return year;
	}

	public char getMonth() {
		return month;
	}

	public char getDay() {
		return day;
	}

	public char getHour() {
		return hour;
	}

	public char getMinute() {
		return minute;
	}

	public char getSecond() {
		return second;
	}

	public char getValidsNum() {
		return validsNum;
	}

	public int getMissDateNum() {
		return missDateNum;
	}

	public char getStatistical() {
		return statistical;
	}

	public char getTimeAddType() {
		return timeAddType;
	}

	public char getProductTimeUnit() {
		return productTimeUnit;
	}

	public int getTimeLength() {
		return timeLength;
	}

	public char getTimeAddUnit() {
		return timeAddUnit;
	}

	public int getTimeAddValue() {
		return timeAddValue;
	}

	public void setReserve(short reserve) {
		this.reserve = reserve;
	}

	public void setTemplateNumber(short templateNumber) {
		this.templateNumber = templateNumber;
	}

	public void setParameterCategory(char parameterCategory) {
		this.parameterCategory = parameterCategory;
	}

	public void setParameterNumber(char parameterNumber) {
		this.parameterNumber = parameterNumber;
	}

	public void setSignificanceOfRT(char significanceOfRT) {
		this.significanceOfRT = significanceOfRT;
	}

	public void setBackProcessId(char backProcessId) {
		this.backProcessId = backProcessId;
	}

	public void setGenProcessId(char genProcessId) {
		this.genProcessId = genProcessId;
	}

	public void setHoursAfterCutoff(short hoursAfterCutoff) {
		this.hoursAfterCutoff = hoursAfterCutoff;
	}

	public void setMinutesAfterCutoff(char minutesAfterCutoff) {
		this.minutesAfterCutoff = minutesAfterCutoff;
	}

	public void setTimeUnit(char timeUnit) {
		this.timeUnit = timeUnit;
	}

	public void setForecastTime(int forecastTime) {
		this.forecastTime = forecastTime;
	}

	public void setLevelType1(char levelType1) {
		this.levelType1 = levelType1;
	}

	public void setLevelValue1(char levelValue1) {
		this.levelValue1 = levelValue1;
	}

	public void setLevelValueInt1(int levelValueInt1) {
		this.levelValueInt1 = levelValueInt1;
	}

	public void setLevelType2(char levelType2) {
		this.levelType2 = levelType2;
	}

	public void setLevelValue2(char levelValue2) {
		this.levelValue2 = levelValue2;
	}

	public void setLevelValueInt2(int levelValueInt2) {
		this.levelValueInt2 = levelValueInt2;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public void setMonth(char month) {
		this.month = month;
	}

	public void setDay(char day) {
		this.day = day;
	}

	public void setHour(char hour) {
		this.hour = hour;
	}

	public void setMinute(char minute) {
		this.minute = minute;
	}

	public void setSecond(char second) {
		this.second = second;
	}

	public void setValidsNum(char validsNum) {
		this.validsNum = validsNum;
	}

	public void setMissDateNum(int missDateNum) {
		this.missDateNum = missDateNum;
	}

	public void setStatistical(char statistical) {
		this.statistical = statistical;
	}

	public void setTimeAddType(char timeAddType) {
		this.timeAddType = timeAddType;
	}

	public void setProductTimeUnit(char productTimeUnit) {
		this.productTimeUnit = productTimeUnit;
	}

	public void setTimeLength(int timeLength) {
		this.timeLength = timeLength;
	}

	public void setTimeAddUnit(char timeAddUnit) {
		this.timeAddUnit = timeAddUnit;
	}

	public void setTimeAddValue(int timeAddValue) {
		this.timeAddValue = timeAddValue;
	}

	public void write(DataOutputStream out) throws IOException {
		try {
			out.writeInt(length);
			out.writeByte(section);
			out.writeShort(getReserve());
			out.writeShort(getTemplateNumber());
			out.writeByte(getParameterCategory());
			out.writeByte(getParameterNumber());
			out.writeByte(getSignificanceOfRT());
			out.writeByte(getBackProcessId());
			out.writeByte(getGenProcessId());
			out.writeShort(getHoursAfterCutoff());
			out.writeByte(getMinutesAfterCutoff());
			out.writeByte(getTimeUnit());
			out.writeInt(getForecastTime());
			out.writeByte(getLevelType1());
			out.writeByte(getLevelValue1());
			out.writeInt(getLevelValueInt1());
			out.writeByte(getLevelType2());
			out.writeByte(getLevelValue2());
			out.writeInt(getLevelValueInt2());
			out.writeShort(getYear());
			out.writeByte(getMonth());
			out.writeByte(getDay());
			out.writeByte(getHour());
			out.writeByte(getMinute());
			out.writeByte(getSecond());
			out.writeByte(getValidsNum());
			out.writeInt(getMissDateNum());
			out.writeByte(getStatistical());
			out.writeByte(getTimeAddType());
			out.writeByte(getProductTimeUnit());
			out.writeInt(getTimeLength());
			out.writeByte(getTimeAddUnit());
			out.writeInt(getTimeAddValue());// 58
			out.flush();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

}
