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
  Section3 网格定义段
	字节范围	类型	描述	内容
	1-4	INT	该段消息长度	72
	5	CHAR	该段标号	固定值：3
	6	CHAR	网格定义来源	固定值0：格点通过 Code Table 3.1格式进行定义
	7-10	INT	格点数据个数	
	11	CHAR	Optionallist of numbers个数	固定值为0：没有使用 Optionallist of numbers
	12	CHAR	定义的	固定值为0：没有Optional list of numbers, 也不使用 interpretation
	13-14	SHORT	格点定义模版	固定值为0：格点定义模版固定使用 3.0，等经纬度格点投影
	15	CHAR	地球模型参数	固定值为6：地球半径为 6,371,229.0米
	16	CHAR	地球模型参数	固定值为0：
	17-20	INT	地球模型参数	固定值为0：
	21	CHAR	地球模型参数	固定值为0：
	22-25	INT	地球模型参数	固定值为0：
	26	CHAR	地球模型参数	固定值为0：
	27-30	INT	地球模型参数	固定值为0：
	31-34	INT	横向格点数	
	35-38	INT	纵向格点数	
	39-42	INT	基本角度单位	固定值为1
	43-46	INT	基本角度份数	固定值为1000000
	47-50	INT	纬度起始点位置	值为：起始纬度* 1000000
	51-54	INT	经度起始点位置	值为：起始经度* 1000000
	55	CHAR	经度和纬度方向的增量信息	值为48
	56-59	INT	纬度终止点位置	值为：终止纬度* 1000000
	60-63	INT	经度终止点位置	值为：终止纬度* 1000000
	64-67	INT	经度方向的格距	值为：经度格距* 1000000
	68-71	INT	纬度方向的格距	值为：纬度格距* 1000000
	72	CHAR	数据的扫描方向	值64表示：经度方向为从小到大，纬度方向为从小到大
 */
public class Section3 {
	/*
	 * Section3 int numberPoints; int nx, ny la1 lo1 la2 lo2 deltaX deltaY;
	 */
	public static final int length = 72; // octets 1-4 (Length of GDS)
	public static final char section = 3; // octet 5
	private char gridFrom = 6;// 6 CHAR 网格定义来源 固定值0：
	private int numberPoints;// 7-10 INT 格点数据个数
	private char optionallist = 0;// 11 CHAR Optionallist of numbers个数 固定值为0：
	private char reserve = 0;// 12 CHAR 定义的 固定值为0：没有Optional
	private short templateNumber = 0;// octets 13-14

	private char earthShape = 6;// 15 CHAR 地球模型参数 固定值为6：地球半径为 6,371,229.0米
	private char earthRadiusFactor = 0;// 16 CHAR 地球模型参数 固定值为0：
	private int earthRadiusValue = 0;// 17-20 INT 地球模型参数 固定值为0：
	private char reserve21 = 0;// 21 CHAR 地球模型参数 固定值为0：
	private int majorAxis = 0; // 22-25 INT 地球模型参数 固定值为0：
	private char reserve26 = 0; // 26 CHAR 地球模型参数 固定值为0：
	private int minorAxis = 0; // 27-30 INT 地球模型参数 固定值为0：

	private int nx;// 31-34 INT 横向格点数
	private int ny;// 35-38 INT 纵向格点数
	private int basicAngle = 1;// 39-42 INT 基本角度单位 固定值为1
	private int basicAngleSubdivisions = 1000000;// 43-46 INT 基本角度份数 固定值为1000000

	private float la1; // 47-50 INT 纬度起始点位置 值为：起始纬度* 1000000
	private float lo1;// 51-54 INT 经度起始点位置 值为：起始经度* 1000000

	private char flags = 48; // 55 CHAR 经度和纬度方向的增量信息 值为48
	private float la2;// 56-59 INT 纬度终止点位置 值为：终止纬度* 1000000
	private float lo2;// 60-63 INT 经度终止点位置 值为：终止纬度* 1000000
	private float deltaX;// 64-67 INT 经度方向的格距 值为：经度格距* 1000000
	private float deltaY;// 68-71 INT 纬度方向的格距 值为：纬度格距* 1000000
	private char scanMode = 64; // 72 CHAR 数据的扫描方向 值64表示：经度方向为从小到大，纬度方向为从小到大

	public char getGridFrom() {
		return gridFrom;
	}

	public void setGridFrom(char gridFrom) {
		this.gridFrom = gridFrom;
	}

	public int getNumberPoints() {
		return numberPoints;
	}

	public void setNumberPoints(int numberPoints) {
		this.numberPoints = numberPoints;
	}

	public char getOptionallist() {
		return optionallist;
	}

	public void setOptionallist(char optionallist) {
		this.optionallist = optionallist;
	}

	public char getReserve() {
		return reserve;
	}

	public void setReserve(char reserve) {
		this.reserve = reserve;
	}

	public short getTemplateNumber() {
		return templateNumber;
	}

	public void setTemplateNumber(short templateNumber) {
		this.templateNumber = templateNumber;
	}

	public char getEarthShape() {
		return earthShape;
	}

	public void setEarthShape(char earthShape) {
		this.earthShape = earthShape;
	}

	public char getEarthRadiusFactor() {
		return earthRadiusFactor;
	}

	public void setEarthRadiusFactor(char earthRadiusFactor) {
		this.earthRadiusFactor = earthRadiusFactor;
	}

	public int getEarthRadiusValue() {
		return earthRadiusValue;
	}

	public void setEarthRadiusValue(int earthRadiusValue) {
		this.earthRadiusValue = earthRadiusValue;
	}

	public char getReserve21() {
		return reserve21;
	}

	public void setReserve21(char reserve21) {
		this.reserve21 = reserve21;
	}

	public int getMajorAxis() {
		return majorAxis;
	}

	public void setMajorAxis(int majorAxis) {
		this.majorAxis = majorAxis;
	}

	public char getReserve26() {
		return reserve26;
	}

	public void setReserve26(char reserve26) {
		this.reserve26 = reserve26;
	}

	public int getMinorAxis() {
		return minorAxis;
	}

	public void setMinorAxis(int minorAxis) {
		this.minorAxis = minorAxis;
	}

	public int getNx() {
		return nx;
	}

	public void setNx(int nx) {
		this.nx = nx;
	}

	public int getNy() {
		return ny;
	}

	public void setNy(int ny) {
		this.ny = ny;
	}

	public int getBasicAngle() {
		return basicAngle;
	}

	public void setBasicAngle(int basicAngle) {
		this.basicAngle = basicAngle;
	}

	public int getBasicAngleSubdivisions() {
		return basicAngleSubdivisions;
	}

	public void setBasicAngleSubdivisions(int basicAngleSubdivisions) {
		this.basicAngleSubdivisions = basicAngleSubdivisions;
	}

	public int getLa1() {
		return (int) (la1 * 1000000);
	}

	public void setLa1(float la1) {
		this.la1 = la1;
	}

	public int getLo1() {
		return (int) (lo1 * 1000000);
	}

	public void setLo1(float lo1) {
		this.lo1 = lo1;
	}

	public char getFlags() {
		return flags;
	}

	public void setFlags(char flags) {
		this.flags = flags;
	}

	public int getLa2() {
		return (int) (la2 * 1000000);
	}

	public void setLa2(float la2) {
		this.la2 = la2;
	}

	public int getLo2() {
		return (int) (lo2 * 1000000);
	}

	public void setLo2(float lo2) {
		this.lo2 = lo2;
	}

	public int getDeltaX() {
		return (int) (deltaX * 1000000);
	}

	public void setDeltaX(float deltaX) {
		this.deltaX = deltaX;
	}

	public int getDeltaY() {
		return (int) (deltaY * 1000000);
	}

	public void setDeltaY(float deltaY) {
		this.deltaY = deltaY;
	}

	public char getScanMode() {
		return scanMode;
	}

	public void setScanMode(char scanMode) {
		this.scanMode = scanMode;
	}

	public void write(DataOutputStream out) throws IOException {
		try {
			out.writeInt(length);
			out.writeByte(section);
			out.writeByte(getGridFrom());
			out.writeInt(getNumberPoints());// 7-10
			out.writeByte(getOptionallist());
			out.writeByte(getReserve());
			out.writeShort(getTemplateNumber());// octets 13-14
			out.writeByte(getEarthShape());
			out.writeByte(getEarthRadiusFactor());// 16
			out.writeInt(getEarthRadiusValue());// 17-20 INT 地球模型参数 固定值为0：
			out.writeByte(getReserve21());
			out.writeInt(getMajorAxis());// 22-25
			out.writeByte(getReserve26());
			out.writeInt(getMinorAxis());
			out.writeInt(getNx());// 31-34
			out.writeInt(getNy());
			out.writeInt(getBasicAngle());// 39-42
			out.writeInt(getBasicAngleSubdivisions());
			out.writeInt(getLa1());
			out.writeInt(getLo1());// 51-54
			out.writeByte(getFlags());
			out.writeInt(getLa2());
			out.writeInt(getLo2());// 60-63
			out.writeInt(getDeltaX());
			out.writeInt(getDeltaY());
			out.writeByte(getScanMode());// 72

			out.flush();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
}
