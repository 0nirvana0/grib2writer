package com.lq.grib2.writer;

public class ProductDefinition {

	private char parameterCategory;// c
	private char parameterNumber;// n
	private char statistical;// s
	private int forecastTime;
	private float[] data;

	public char getParameterCategory() {
		return parameterCategory;
	}

	public char getParameterNumber() {
		return parameterNumber;
	}

	public char getStatistical() {
		return statistical;
	}

	public int getForecastTime() {
		return forecastTime;
	}

	public float[] getData() {
		return data;
	}

	public void setParameterCategory(char parameterCategory) {
		this.parameterCategory = parameterCategory;
	}

	public void setParameterNumber(char parameterNumber) {
		this.parameterNumber = parameterNumber;
	}

	public void setStatistical(char statistical) {
		this.statistical = statistical;
	}

	public void setForecastTime(int forecastTime) {
		this.forecastTime = forecastTime;
	}

	public void setData(float[] data) {
		this.data = data;
	}

	public ProductDefinition(int parameterCategory, int parameterNumber, int statistical, int forecastTime, float[] data) {
		super();
		this.parameterCategory = (char) parameterCategory;
		this.parameterNumber = (char) parameterNumber;
		this.statistical = (char) statistical;
		this.forecastTime = forecastTime;
		this.data = data;
	}

}
