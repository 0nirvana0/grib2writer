package com.lq.grib2.writer;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lq.grib2.section.Section0;
import com.lq.grib2.section.Section1;
import com.lq.grib2.section.Section2;
import com.lq.grib2.section.Section3;
import com.lq.grib2.section.Section4;
import com.lq.grib2.section.Section5;
import com.lq.grib2.section.Section6;
import com.lq.grib2.section.Section7;
import com.lq.grib2.section.Section8;

public class Grib2Writer {
	private String location;

	private short year;
	private char month, day, hour, minute, second;
	private int nx;
	private int ny;
	private float la1;
	private float lo1;
	private float la2;
	private float lo2;
	private float deltaX;
	private float deltaY;
	private int numberPoints;

	private int recordSetSize;
	private List<ProductDefinition> products = new ArrayList<ProductDefinition>();
	private DataOutputStream out = null;

	private Grib2Writer(String location) {
		this.location = location;
	}

	public static Grib2Writer createNew(String location) {
		return new Grib2Writer(location);
	}

	/**
	 * 设置起报时间
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public void setReferenceDate(int year, int month, int day, int hour, int minute, int second) {
		this.year = (short) year;
		this.month = (char) month;
		this.day = (char) day;
		this.hour = (char) hour;
		this.minute = (char) minute;
		this.second = (char) second;
	}

	public void setGridDefinition(int nx, int ny, float lo1, float la1, float lo2, float la2) {
		this.nx = nx;
		this.ny = ny;
		this.la1 = la1;
		this.lo1 = lo1;
		this.la2 = la2;
		this.lo2 = lo2;
		this.deltaX = (lo2 - lo1) / (nx - 1);
		this.deltaY = (la2 - la1) / (ny - 1);
		this.numberPoints = nx * ny;
	}

	public void create() throws IOException {
		File gribFile = new File(location);
		if (gribFile.exists()) {
			gribFile.delete();
		}
		this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(location, true)));

	};

	public void write(int parameterCategory, int parameterNumber, int statistical, int forecastTime, float[] data) {
		ProductDefinition productDefinition = new ProductDefinition(parameterCategory, parameterNumber, statistical, forecastTime, data);
		write(productDefinition);
	}

	public void write(ProductDefinition productDefinition) {
		products.add(productDefinition);
	}

	private long getSection0MsgLen(Section7 section7) {
		long section0MsgLen = Section0.length + Section1.length + Section2.length + Section3.length + recordSetSize * (Section4.length + Section5.length + Section6.length + section7.getLength()) + Section8.length;
		return section0MsgLen;
	}

	public void close() throws IOException {
		this.recordSetSize = products.size();

		Section0 section0 = new Section0();
		Section1 section1 = new Section1();
		Section2 section2 = new Section2();
		Section3 section3 = new Section3();
		Section4 section4 = new Section4();
		Section5 section5 = new Section5();
		Section6 section6 = new Section6();
		Section7 section7 = new Section7(numberPoints);
		Section8 section8 = new Section8();
		// section0-----------------------------------------------------------------
		section0.setMessageLength(getSection0MsgLen(section7));
		section0.wirte(out);
		// section1-----------------------------------------------------------------
		section1.setYear(year);
		section1.setMonth(month);
		section1.setDay(day);
		section1.setHour(hour);
		section1.setMinute(minute);
		section1.setSecond(second);
		section1.write(out);
		// section2-----------------------------------------------------------------
		section2.wirte(out);
		// section3-----------------------------------------------------------------
		section3.setNumberPoints(numberPoints);
		section3.setNx(nx);
		section3.setNy(ny);
		section3.setLa1(la1);
		section3.setLo1(lo1);
		section3.setLa2(la2);
		section3.setLo2(lo2);
		section3.setDeltaX(deltaX);
		section3.setDeltaY(deltaY);
		section3.write(out);

		for (int i = 0; i < recordSetSize; i++) {

			// section4-----------------------------------------------------------------
			char c = products.get(i).getParameterCategory();
			char n = products.get(i).getParameterNumber();
			char s = products.get(i).getStatistical();
			int f = products.get(i).getForecastTime();
			section4.setParameterCategory(c);
			section4.setParameterNumber(n);
			section4.setStatistical(s);
			section4.setForecastTime(f);

			section4.write(out);
			// section5-----------------------------------------------------------------
			section5.setDataPoints(numberPoints);
			section5.write(out);
			// section6-----------------------------------------------------------------
			section6.write(out);
			/**
			 * Section7 float[] data;
			 */
			section7.setData(products.get(i).getData());
			section7.write(out);
		}
		section8.write(out);
		// close
		if (this.out != null) {
			this.out.close();
		}
	}

}
