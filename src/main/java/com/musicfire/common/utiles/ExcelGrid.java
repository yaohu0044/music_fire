package com.musicfire.common.utiles;

public class ExcelGrid {
	private int top=0;	
	private int left=0;
	private int width=0;
	private int heigth=0;
	private String label;

	/**
	 * 定义行列和内容
	 * @param top 行位置
	 * @param left 列位置
	 * @param label 标签
	 */
	public ExcelGrid(int left, int top, String label) {
		super();
		this.top = top;
		this.left = left;
		this.label = label;
	}
	/**
	 * 定义行列类容和占用宽度高度
	 * @param left 列位置
	 * @param top	行位置
	 * @param width 宽度
	 * @param heigth 高度
	 * @param label 标签
	 */
	public ExcelGrid(int left, int top, int width, int heigth, String label) {
		super();
		this.top = top;
		this.left = left;
		this.width = width;
		this.heigth = heigth;
		this.label = label;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeigth() {
		return heigth;
	}
	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
